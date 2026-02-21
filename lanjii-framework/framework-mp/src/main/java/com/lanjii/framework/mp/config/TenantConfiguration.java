package com.lanjii.framework.mp.config;

import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.lanjii.framework.mp.tenant.TenantHandler;
import com.lanjii.framework.mp.tenant.TenantProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 多租户配置
 *
 * @author lanjii
 */
@Configuration
@EnableConfigurationProperties(TenantProperties.class)
public class TenantConfiguration {

    /**
     * 多租户插件
     */
    @Bean
    @ConditionalOnProperty(prefix = "lanjii.tenant", name = "enabled", havingValue = "true")
    public TenantLineInnerInterceptor tenantLineInnerInterceptor(TenantProperties tenantProperties) {
        TenantHandler tenantHandler = new TenantHandler(tenantProperties);
        return new TenantLineInnerInterceptor(tenantHandler);
    }
}
