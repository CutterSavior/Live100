package com.lanjii.tenant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lanjii.tenant.entity.SysTenant;
import com.lanjii.tenant.api.dto.SysTenantDTO;
import com.lanjii.tenant.api.vo.SysTenantVO;

import java.util.List;

/**
 * 租户服务接口
 *
 * @author lanjii
 */
public interface TenantService extends IService<SysTenant> {

    /**
     * 根据租户编码获取租户
     */
    SysTenant getByTenantCode(String tenantCode);

    /**
     * 校验租户是否有效
     */
    boolean checkTenantValid(Long tenantId);

    /**
     * 获取租户列表
     */
    List<SysTenantVO> listTenants(SysTenantDTO dto);

    /**
     * 根据ID获取租户详情
     */
    SysTenantVO getByIdNew(Long id);

    /**
     * 新增租户
     */
    void saveNew(SysTenantDTO dto);

    /**
     * 更新租户
     */
    void updateByIdNew(Long id, SysTenantDTO dto);

}
