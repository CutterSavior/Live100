package com.lanjii.tenant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lanjii.tenant.entity.SysTenantPackage;
import com.lanjii.tenant.api.dto.SysTenantPackageDTO;
import com.lanjii.tenant.api.vo.SysTenantPackageVO;

import java.util.List;

/**
 * 租户套餐服务接口
 *
 * @author lanjii
 */
public interface TenantPackageService extends IService<SysTenantPackage> {

    /**
     * 根据套餐ID获取菜单ID列表
     */
    List<Long> getMenuIdsByPackageId(Long packageId);

    /**
     * 获取套餐列表
     */
    List<SysTenantPackageVO> listPackages(SysTenantPackageDTO dto);

    /**
     * 根据ID获取套餐详情
     */
    SysTenantPackageVO getByIdNew(Long id);

    /**
     * 新增套餐
     */
    void saveNew(SysTenantPackageDTO dto);

    /**
     * 更新套餐
     */
    void updateByIdNew(Long id, SysTenantPackageDTO dto);

}
