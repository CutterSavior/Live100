package com.lanjii.framework.security.config;

import com.lanjii.framework.security.handler.SecurityExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Security 自动配置
 *
 * @author lanjii
 */
@AutoConfiguration
public class LanjiiSecurityAutoConfiguration {

    @Bean
    public SecurityExceptionHandler securityExceptionHandler() {
        return new SecurityExceptionHandler();
    }
}
