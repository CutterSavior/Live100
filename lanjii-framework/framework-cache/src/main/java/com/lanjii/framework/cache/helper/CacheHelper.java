package com.lanjii.framework.cache.helper;

import com.lanjii.framework.cache.core.CacheDef;
import com.lanjii.framework.cache.core.CacheRegistry;
import com.lanjii.framework.context.tenant.TenantContext;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * 缓存辅助工具类
 * <p>
 * 提供 Spring Cache 接口未覆盖的扩展能力，如遍历、全量清空等
 *
 * @author lanjii
 */
@Component
@RequiredArgsConstructor
public class CacheHelper {

    private final CacheManager cacheManager;
    private final CacheRegistry cacheRegistry;

    /**
     * 获取指定缓存的所有值 (仅 Local 模式可用)
     * <p>
     * 注意：此方法依赖底层实现，Redis 模式下不支持
     *
     * @param cacheName 缓存名称
     * @param type      值的类型
     * @return 缓存中的所有值集合
     */
    @SuppressWarnings("unchecked")
    public <T> Collection<T> getAllValues(String cacheName, Class<T> type) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            return Collections.emptyList();
        }

        Object nativeCache = cache.getNativeCache();
        if (nativeCache instanceof com.github.benmanes.caffeine.cache.Cache) {
            com.github.benmanes.caffeine.cache.Cache<Object, Object> caffeineCache =
                    (com.github.benmanes.caffeine.cache.Cache<Object, Object>) nativeCache;

            // 检查缓存是否需要租户隔离
            CacheDef cacheDef = cacheRegistry.get(cacheName).orElse(null);
            boolean tenantIsolated = cacheDef == null || cacheDef.isTenantIsolated();

            if (tenantIsolated) {
                // 租户隔离：根据租户ID前缀过滤
                Long tenantId = TenantContext.getTenantId();
                String tenantPrefix = (tenantId != null ? tenantId.toString() : "0") + ":";

                return caffeineCache.asMap().entrySet().stream()
                        .filter(entry -> entry.getKey().toString().startsWith(tenantPrefix))
                        .map(entry -> (T) entry.getValue())
                        .collect(Collectors.toList());
            } else {
                // 不需要租户隔离：返回所有数据
                return (Collection<T>) caffeineCache.asMap().values();
            }
        }

        // Redis 模式下暂不支持，需要使用 SCAN 命令实现
        throw new UnsupportedOperationException("getAllValues is only supported in LOCAL mode");
    }

    /**
     * 清空指定缓存的所有数据
     *
     * @param cacheName 缓存名称
     */
    public void clearAll(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        }
    }

    /**
     * 获取缓存实例
     *
     * @param cacheName 缓存名称
     * @return Cache 实例
     */
    public Cache getCache(String cacheName) {
        return cacheManager.getCache(cacheName);
    }
}
