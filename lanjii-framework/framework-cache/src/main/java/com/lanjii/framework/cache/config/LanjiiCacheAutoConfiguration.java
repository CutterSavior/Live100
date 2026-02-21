package com.lanjii.framework.cache.config;

import com.lanjii.framework.cache.core.CacheRegistry;
import com.lanjii.framework.cache.properties.LanjiiCacheProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

/**
 * 缓存自动配置
 *
 * @author lanjii
 */
@AutoConfiguration(afterName = "com.lanjii.framework.mp.config.LanjiiMybatisAutoConfiguration",
        beforeName = "com.lanjii.framework.security.config.LanjiiSecurityAutoConfiguration")
@EnableCaching
@EnableConfigurationProperties(LanjiiCacheProperties.class)
public class LanjiiCacheAutoConfiguration {

    /**
     * 缓存注册表
     * 业务模块可注入该 Bean 来注册自己的缓存定义
     */
    @Bean
    @ConditionalOnMissingBean
    public CacheRegistry cacheRegistry() {
        return new CacheRegistry();
    }

    /**
     * Local 模式 - Caffeine CacheManager
     * 默认启用（当 type 为 LOCAL 或未配置时）
     * 支持动态创建缓存，首次 getCache 时根据 CacheRegistry 或默认值创建
     */
    @Bean
    @ConditionalOnProperty(name = "lanjii.cache.type", havingValue = "LOCAL", matchIfMissing = true)
    public CacheManager caffeineCacheManager(CacheRegistry cacheRegistry, LanjiiCacheProperties properties) {
        return new TenantAwareCaffeineCacheManager(cacheRegistry, properties);
    }
}
