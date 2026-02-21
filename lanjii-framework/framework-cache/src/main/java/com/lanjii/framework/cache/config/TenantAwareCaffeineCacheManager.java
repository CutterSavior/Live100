package com.lanjii.framework.cache.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.lanjii.framework.cache.core.CacheDef;
import com.lanjii.framework.cache.core.CacheRegistry;
import com.lanjii.framework.cache.properties.LanjiiCacheProperties;
import com.lanjii.framework.context.tenant.TenantContext;
import org.springframework.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCacheManager;

import java.util.concurrent.Callable;

/**
 * 支持多租户隔离的 Caffeine Cache Manager
 *
 * @author lanjii
 */
public class TenantAwareCaffeineCacheManager extends CaffeineCacheManager {

    private final CacheRegistry cacheRegistry;
    private final LanjiiCacheProperties properties;

    public TenantAwareCaffeineCacheManager(CacheRegistry cacheRegistry, LanjiiCacheProperties properties) {
        this.cacheRegistry = cacheRegistry;
        this.properties = properties;
        // 允许动态创建缓存
        this.setAllowNullValues(properties.isCacheNullValues());
    }

    @Override
    public Cache getCache(String name) {
        Cache delegate = super.getCache(name);

        // 如果缓存不存在，根据 CacheRegistry 或默认值动态创建
        if (delegate == null) {
            delegate = createAndRegisterCache(name);
        }

        if (delegate == null) {
            return null;
        }

        // 检查是否需要租户隔离
        CacheDef cacheDef = cacheRegistry.get(name).orElse(null);
        boolean tenantIsolated = cacheDef.isTenantIsolated();

        if (tenantIsolated) {
            return new TenantAwareCache(delegate);
        }
        return delegate;
    }

    /**
     * 动态创建并注册缓存
     */
    private synchronized Cache createAndRegisterCache(String name) {
        // 双重检查
        Cache existing = super.getCache(name);
        if (existing != null) {
            return existing;
        }

        // 从 CacheRegistry 获取缓存定义，或使用默认值
        CacheDef cacheDef = cacheRegistry.get(name).orElse(null);

        Caffeine<Object, Object> builder = Caffeine.newBuilder();
        if (cacheDef != null) {
            builder.expireAfterWrite(cacheDef.getTtl())
                    .maximumSize(cacheDef.getMaxSize());
        } else {
            builder.expireAfterWrite(properties.getDefaultTtl())
                    .maximumSize(properties.getDefaultMaxSize());
        }

        com.github.benmanes.caffeine.cache.Cache<Object, Object> caffeineCache = builder.build();
        this.registerCustomCache(name, caffeineCache);

        return super.getCache(name);
    }

    /**
     * 租户感知的 Cache 装饰器
     * 对所有 Key 操作自动添加租户 ID 前缀
     */
    static class TenantAwareCache implements Cache {
        private final Cache delegate;

        public TenantAwareCache(Cache delegate) {
            this.delegate = delegate;
        }

        /**
         * 创建带租户前缀的 Key
         */
        private Object createTenantKey(Object key) {
            Long tenantId = TenantContext.getTenantId();
            String prefix = (tenantId != null) ? tenantId.toString() : "0";
            return prefix + ":" + key;
        }

        @Override
        public String getName() {
            return delegate.getName();
        }

        @Override
        public Object getNativeCache() {
            return delegate.getNativeCache();
        }

        @Override
        public ValueWrapper get(Object key) {
            return delegate.get(createTenantKey(key));
        }

        @Override
        public <T> T get(Object key, Class<T> type) {
            return delegate.get(createTenantKey(key), type);
        }

        @Override
        public <T> T get(Object key, Callable<T> valueLoader) {
            return delegate.get(createTenantKey(key), valueLoader);
        }

        @Override
        public void put(Object key, Object value) {
            delegate.put(createTenantKey(key), value);
        }

        @Override
        public void evict(Object key) {
            delegate.evict(createTenantKey(key));
        }

        @Override
        public boolean evictIfPresent(Object key) {
            return delegate.evictIfPresent(createTenantKey(key));
        }

        @Override
        public void clear() {
            delegate.clear();
        }

        @Override
        public boolean invalidate() {
            return delegate.invalidate();
        }

        @Override
        public ValueWrapper putIfAbsent(Object key, Object value) {
            return delegate.putIfAbsent(createTenantKey(key), value);
        }
    }
}
