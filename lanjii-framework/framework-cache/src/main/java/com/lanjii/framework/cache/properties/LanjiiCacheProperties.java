package com.lanjii.framework.cache.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * 缓存配置属性
 * <p>
 * 只配置全局默认值，具体缓存由业务模块通过 CacheDef 自行定义
 *
 * @author lanjii
 */
@Data
@ConfigurationProperties(prefix = "lanjii.cache")
public class LanjiiCacheProperties {

    /**
     * 缓存类型
     */
    private CacheType type = CacheType.LOCAL;

    /**
     * 是否允许缓存 Null 值 (防止缓存穿透)
     */
    private boolean cacheNullValues = true;

    /**
     * 默认过期时间，默认 1 小时
     */
    private Duration defaultTtl = Duration.ofHours(1);

    /**
     * 默认最大容量 (仅 Local 模式有效)，默认 1000
     */
    private long defaultMaxSize = 1000L;

    /**
     * 缓存类型枚举
     */
    public enum CacheType {
        /**
         * 本地缓存 (Caffeine)
         */
        LOCAL,
        /**
         * 分布式缓存 (Redis)
         */
        REDIS
    }
}
