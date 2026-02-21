package com.lanjii.system.api;

/**
 * 系统模块对外API接口
 *
 * @author lanjii
 */
public interface SystemApi {

    /**
     * 创建租户默认管理员
     *
     * @param tenantId   租户ID
     * @param tenantCode 租户编码
     */
    void createTenantAdmin(Long tenantId, String tenantCode);
}
