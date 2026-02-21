package com.lanjii.framework.mp.tenant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 多租户配置属性
 *
 * @author lanjii
 */
@Data
@ConfigurationProperties(prefix = "lanjii.tenant")
public class TenantProperties {
    /**
     * 是否启用多租户
     */
    private boolean enabled = false;
    /**
     * 租户字段名
     */
    private String column = "tenant_id";
    /**
     * 忽略租户过滤的表
     */
    private List<String> ignoreTables = new ArrayList<>();
}