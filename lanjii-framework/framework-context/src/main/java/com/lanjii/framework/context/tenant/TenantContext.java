package com.lanjii.framework.context.tenant;

/**
 * 租户上下文
 *
 * @author lanjii
 */
public final class TenantContext {

    private static final ThreadLocal<Long> TENANT_ID = new ThreadLocal<>();

    private TenantContext() {
    }

    /**
     * 设置租户ID
     */
    public static void setTenantId(Long tenantId) {
        TENANT_ID.set(tenantId);
    }

    /**
     * 获取租户ID
     */
    public static Long getTenantId() {
        return TENANT_ID.get();
    }

    /**
     * 清除上下文
     */
    public static void clear() {
        TENANT_ID.remove();
    }

}
