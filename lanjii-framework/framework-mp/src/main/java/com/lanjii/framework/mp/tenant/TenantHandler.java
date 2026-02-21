package com.lanjii.framework.mp.tenant;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.lanjii.framework.context.tenant.TenantContext;
import lombok.RequiredArgsConstructor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NullValue;

/**
 * 租户行处理器
 *
 * @author lanjii
 */
@RequiredArgsConstructor
public class TenantHandler implements TenantLineHandler {

    /**
     * 平台管理员租户ID
     */
    public static final Long PLATFORM_TENANT_ID = 0L;

    private final TenantProperties tenantProperties;

    @Override
    public Expression getTenantId() {
        Long tenantId = TenantContext.getTenantId();
        if (tenantId == null) {
            return new NullValue();
        }
        return new LongValue(tenantId);
    }

    @Override
    public String getTenantIdColumn() {
        return tenantProperties.getColumn();
    }

    @Override
    public boolean ignoreTable(String tableName) {
        return tenantProperties.getIgnoreTables().stream()
                .anyMatch(t -> t.equalsIgnoreCase(tableName));
    }
}