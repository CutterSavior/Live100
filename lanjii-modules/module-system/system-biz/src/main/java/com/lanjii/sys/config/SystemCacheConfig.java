package com.lanjii.sys.config;

import com.lanjii.framework.cache.core.CacheRegistry;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

/**
 * system 模块缓存配置
 * <p>
 * 在应用启动时将本模块的缓存定义注册到 CacheRegistry
 *
 * @author lanjii
 */
@Configuration
@RequiredArgsConstructor
public class SystemCacheConfig {

    private final CacheRegistry cacheRegistry;

    @PostConstruct
    public void registerCaches() {
        cacheRegistry.registerAll(
                SystemCacheConstants.DICT_DATA,
                SystemCacheConstants.SYS_CONFIG,
                SystemCacheConstants.USER_SESSION,
                SystemCacheConstants.USER_INFO,
                SystemCacheConstants.CAPTCHA
        );
    }
}
