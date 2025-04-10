package com.lanjii.util;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

/**
 * Caffeine缓存工具类（静态工具类实现）
 *
 * @author lizheng
 * @date 2025-04-07
 */
public final class LocalCacheUtils {

    private LocalCacheUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * 获取指定类型的缓存实例
     */
    private static Cache<String, Object> getCache(CacheType cacheType) {
        switch (cacheType) {
            case CAPTCHA:
                return CacheHolder.CAPTCHA_CACHE;
            case USER:
                return CacheHolder.USER_CACHE;
            case DICT:
                return CacheHolder.DICT_CACHE;
            case CONFIG:
                return CacheHolder.CONFIG_CACHE;
            case OTHER:
                return CacheHolder.OTHER_CACHE;
            case ONLINE_USER:
                return CacheHolder.ONLINE_USER_CACHE;
            default:
                throw new IllegalArgumentException("Unknown cache type: " + cacheType);
        }
    }

    /**
     * 定义缓存类型
     */
    @Getter
    public enum CacheType {
        CAPTCHA(60 * 3, 1000),
        ONLINE_USER(3 * 60, 1000),
        USER(30 * 60, 10000),
        DICT(12 * 60 * 60, 5000),
        CONFIG(12 * 60 * 60, 1000),
        OTHER(60 * 60, 10000);

        private final int ttl; // 过期时间(秒)
        private final int maxSize; // 最大数量

        CacheType(int ttl, int maxSize) {
            this.ttl = ttl;
            this.maxSize = maxSize;
        }

    }

    /**
     * 构建缓存实例
     */
    private static Cache<String, Object> buildCache(CacheType cacheType) {
        return Caffeine.newBuilder()
                .expireAfterWrite(cacheType.getTtl(), TimeUnit.SECONDS)
                .maximumSize(cacheType.getMaxSize())
                .recordStats()
                .build();
    }

    private static class CacheHolder {
        private static final Cache<String, Object> CAPTCHA_CACHE = buildCache(CacheType.CAPTCHA);
        private static final Cache<String, Object> USER_CACHE = buildCache(CacheType.USER);
        private static final Cache<String, Object> DICT_CACHE = buildCache(CacheType.DICT);
        private static final Cache<String, Object> CONFIG_CACHE = buildCache(CacheType.CONFIG);
        private static final Cache<String, Object> OTHER_CACHE = buildCache(CacheType.OTHER);
        private static final Cache<String, Object> ONLINE_USER_CACHE = buildCache(CacheType.ONLINE_USER);
    }

    /**
     * 放入缓存
     *
     * @param cacheType 缓存类型
     * @param key       缓存键
     * @param value     缓存值
     */
    public static void put(CacheType cacheType, String key, Object value) {
        getCache(cacheType).put(key, value);
    }

    /**
     * 获取缓存值
     *
     * @param cacheType 缓存类型
     * @param key       缓存键
     * @param <T>       返回值类型
     * @return 缓存值
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(CacheType cacheType, String key) {
        return (T) getCache(cacheType).getIfPresent(key);
    }

    /**
     * 删除缓存
     *
     * @param cacheType 缓存类型
     * @param key       缓存键
     */
    public static void invalidate(CacheType cacheType, String key) {
        getCache(cacheType).invalidate(key);
    }

    /**
     * 清空缓存
     *
     * @param cacheType 缓存类型
     */
    public static void clear(CacheType cacheType) {
        getCache(cacheType).invalidateAll();
    }

    /**
     * 获取缓存统计信息
     *
     * @param cacheType 缓存类型
     * @return 缓存统计信息
     */
    public static CacheStats stats(CacheType cacheType) {
        return getCache(cacheType).stats();
    }

    /**
     * 获取缓存预估大小
     *
     * @param cacheType 缓存类型
     * @return 缓存大小
     */
    public static long estimatedSize(CacheType cacheType) {
        return getCache(cacheType).estimatedSize();
    }

}