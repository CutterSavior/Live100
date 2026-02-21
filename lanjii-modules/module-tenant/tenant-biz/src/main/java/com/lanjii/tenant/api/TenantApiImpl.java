package com.lanjii.tenant.api;

import com.lanjii.tenant.api.vo.SysTenantVO;
import com.lanjii.tenant.entity.SysTenant;
import com.lanjii.tenant.service.TenantPackageService;
import com.lanjii.tenant.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 租户模块API实现类
 *
 * @author lanjii
 */
@Service
@RequiredArgsConstructor
public class TenantApiImpl implements TenantApi {

    private final TenantService tenantService;
    private final TenantPackageService tenantPackageService;

    @Override
    public SysTenantVO getTenantByCode(String tenantCode) {
        SysTenant tenant = tenantService.getByTenantCode(tenantCode);
        return SysTenant.INSTANCE.toVo(tenant);
    }

    @Override
    public List<Long> getMenuIdsByPackageId(Long packageId) {
        return tenantPackageService.getMenuIdsByPackageId(packageId);
    }

    @Override
    public SysTenantVO getById(Long tenantId) {
        SysTenant tenant = tenantService.getById(tenantId);
        return SysTenant.INSTANCE.toVo(tenant);
    }

}
