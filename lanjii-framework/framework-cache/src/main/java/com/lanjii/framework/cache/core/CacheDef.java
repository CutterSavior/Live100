package com.lanjii.framework.cache.core;

import java.time.Duration;

/**
 * 缓存定义
 * <p>
 * 用于业务模块定义自己的缓存配置
 *
 * @author lanjii
 */
public class CacheDef {

    /**
     * 缓存名称
     */
    private final String name;

    /**
     * 过期时间
     */
    private final Duration ttl;

    /**
     * 最大容量 (仅 Local 模式有效)
     */
    private final long maxSize;

    /**
     * 是否按租户隔离（默认true）
     */
    private final boolean tenantIsolated;

    private CacheDef(String name, Duration ttl, long maxSize, boolean tenantIsolated) {
        this.name = name;
        this.ttl = ttl;
        this.maxSize = maxSize;
        this.tenantIsolated = tenantIsolated;
    }

    /**
     * 创建缓存定义
     *
     * @param name 缓存名称
     * @param ttl  过期时间
     * @return CacheDef
     */
    public static CacheDef of(String name, Duration ttl) {
        return new CacheDef(name, ttl, 1000L, true);
    }

    /**
     * 创建缓存定义
     *
     * @param name    缓存名称
     * @param ttl     过期时间
     * @param maxSize 最大容量
     * @return CacheDef
     */
    public static CacheDef of(String name, Duration ttl, long maxSize) {
        return new CacheDef(name, ttl, maxSize, true);
    }

    /**
     * 创建缓存定义
     *
     * @param name           缓存名称
     * @param ttl            过期时间
     * @param tenantIsolated 是否租户隔离
     * @return CacheDef
     */
    public static CacheDef of(String name, Duration ttl, boolean tenantIsolated) {
        return new CacheDef(name, ttl, 1000L, tenantIsolated);
    }

    /**
     * 创建缓存定义
     *
     * @param name           缓存名称
     * @param ttl            过期时间
     * @param maxSize        最大容量
     * @param tenantIsolated 是否租户隔离
     * @return CacheDef
     */
    public static CacheDef of(String name, Duration ttl, long maxSize, boolean tenantIsolated) {
        return new CacheDef(name, ttl, maxSize, tenantIsolated);
    }

    public String getName() {
        return name;
    }

    public Duration getTtl() {
        return ttl;
    }

    public long getMaxSize() {
        return maxSize;
    }

    public boolean isTenantIsolated() {
        return tenantIsolated;
    }
}
