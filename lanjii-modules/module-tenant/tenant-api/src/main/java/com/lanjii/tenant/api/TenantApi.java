package com.lanjii.tenant.api;

import com.lanjii.tenant.api.vo.SysTenantVO;

import java.util.List;

/**
 * 租户模块对外API接口
 *
 * @author lanjii
 */
public interface TenantApi {

    /**
     * 根据租户编码获取租户信息
     *
     * @param tenantCode 租户编码
     * @return 租户信息
     */
    SysTenantVO getTenantByCode(String tenantCode);

    /**
     * 根据套餐ID获取菜单ID列表
     *
     * @param packageId 套餐ID
     * @return 菜单ID列表
     */
    List<Long> getMenuIdsByPackageId(Long packageId);

    /**
     * 根据 ID 获取租户信息
     *
     * @param tenantId 租户 ID
     * @return 租户信息
     */
    SysTenantVO getById(Long tenantId);
}
