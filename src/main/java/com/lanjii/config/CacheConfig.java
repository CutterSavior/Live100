package com.lanjii.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.lanjii.biz.admin.system.model.entity.SysConfig;
import com.lanjii.biz.admin.system.model.entity.SysDictData;
import com.lanjii.security.AuthUser;
import com.lanjii.biz.admin.system.model.vo.UserSessionInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    /**
     * 字典数据缓存
     */
    @Bean("dictDataCache")
    public Cache<String, SysDictData> dictDataCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(24, TimeUnit.HOURS)
                .maximumSize(1000)
                .recordStats()
                .build();
    }


    /**
     * 系统配置缓存
     * Key: configKey, Value: SysConfig
     */
    @Bean("configCache")
    public Cache<String, SysConfig> configCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(7, TimeUnit.DAYS)
                .maximumSize(1000)
                .recordStats()
                .build();
    }

    /**
     * 用户会话缓存
     */
    @Bean("userSessionCache")
    public Cache<String, UserSessionInfo> userSessionCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(24, TimeUnit.HOURS)
                .maximumSize(50000)
                .recordStats()
                .build();
    }

    /**
     * 用户信息缓存
     * Key: username, Value: AuthUser
     */
    @Bean("userInfoCache")
    public Cache<String, AuthUser> userInfoCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(24, TimeUnit.HOURS)
                .maximumSize(10000)
                .recordStats()
                .build();
    }

    /**
     * 验证码缓存
     * Key: captchaKey, Value: code
     */
    @Bean("captchaCache")
    public Cache<String, String> captchaCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .maximumSize(10000)
                .recordStats()
                .build();
    }

}