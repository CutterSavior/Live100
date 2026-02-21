package com.lanjii.framework.mp.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.lanjii.framework.context.req.BizReqContextHolder;
import com.lanjii.framework.context.tenant.TenantContext;
import com.lanjii.framework.mp.tenant.TenantProperties;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * 自动填充配置
 *
 * @author lanjii
 */
@Configuration
@EnableConfigurationProperties(TenantProperties.class)
public class MetaObjectHandlerConfiguration {

    /**
     * 自动填充配置
     */
    @Bean
    public MetaObjectHandler metaObjectHandler(TenantProperties tenantProperties) {
        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
                this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());

                String username = BizReqContextHolder.getContext().getUsername();

                this.strictInsertFill(metaObject, "createBy", String.class, username);
                this.strictUpdateFill(metaObject, "updateBy", String.class, username);

                // 自动填充租户ID，平台管理员默认为0
                if (tenantProperties.isEnabled()) {
                    Long tenantId = TenantContext.getTenantId();
                    this.strictInsertFill(metaObject, "tenantId", Long.class, tenantId != null ? tenantId : 0L);
                }
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                String username = BizReqContextHolder.getContext().getUsername();

                this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
                this.strictUpdateFill(metaObject, "updateBy", String.class, username);
            }
        };
    }
}
