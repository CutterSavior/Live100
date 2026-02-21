package com.lanjii.tenant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanjii.common.exception.BizException;
import com.lanjii.system.api.SystemApi;
import com.lanjii.tenant.dao.SysTenantDao;
import com.lanjii.tenant.dao.SysTenantPackageDao;
import com.lanjii.tenant.entity.SysTenant;
import com.lanjii.tenant.entity.SysTenantPackage;
import com.lanjii.tenant.api.dto.SysTenantDTO;
import com.lanjii.tenant.api.vo.SysTenantVO;
import com.lanjii.tenant.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 租户服务实现类
 *
 * @author lanjii
 */
@Service
@RequiredArgsConstructor
public class TenantServiceImpl extends ServiceImpl<SysTenantDao, SysTenant> implements TenantService {

    private final SysTenantPackageDao tenantPackageDao;
    private final SystemApi systemApi;

    @Override
    public SysTenant getByTenantCode(String tenantCode) {
        return this.getOne(new LambdaQueryWrapper<SysTenant>()
                .eq(SysTenant::getTenantCode, tenantCode));
    }

    @Override
    public boolean checkTenantValid(Long tenantId) {
        SysTenant tenant = this.getById(tenantId);
        if (tenant == null) {
            return false;
        }
        // 检查状态
        if (tenant.getStatus() != 1) {
            return false;
        }
        // 检查过期时间
        if (tenant.getExpireTime() != null && tenant.getExpireTime().isBefore(LocalDateTime.now())) {
            return false;
        }
        return true;
    }

    @Override
    public List<SysTenantVO> listTenants(SysTenantDTO dto) {
        LambdaQueryWrapper<SysTenant> wrapper = new LambdaQueryWrapper<>();
        if (dto.getTenantCode() != null) {
            wrapper.like(SysTenant::getTenantCode, dto.getTenantCode());
        }
        if (dto.getTenantName() != null) {
            wrapper.like(SysTenant::getTenantName, dto.getTenantName());
        }
        if (dto.getStatus() != null) {
            wrapper.eq(SysTenant::getStatus, dto.getStatus());
        }
        wrapper.orderByDesc(SysTenant::getCreateTime);

        List<SysTenant> list = this.list(wrapper);
        return list.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public SysTenantVO getByIdNew(Long id) {
        SysTenant tenant = this.getById(id);
        return convertToVO(tenant);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveNew(SysTenantDTO dto) {
        // 校验租户编码唯一性
        SysTenant exists = this.getByTenantCode(dto.getTenantCode());
        if (exists != null) {
            throw new BizException("租户编码已存在");
        }

        SysTenant tenant = new SysTenant();
        BeanUtils.copyProperties(dto, tenant);
        this.save(tenant);

        // 调用系统模块API创建租户默认管理员
        systemApi.createTenantAdmin(tenant.getId(), tenant.getTenantCode());
    }

    @Override
    public void updateByIdNew(Long id, SysTenantDTO dto) {
        SysTenant tenant = this.getById(id);
        if (tenant == null) {
            throw new BizException("租户不存在");
        }

        // 如果修改了编码，校验唯一性
        if (dto.getTenantCode() != null && !dto.getTenantCode().equals(tenant.getTenantCode())) {
            SysTenant exists = this.getByTenantCode(dto.getTenantCode());
            if (exists != null) {
                throw new BizException("租户编码已存在");
            }
        }

        BeanUtils.copyProperties(dto, tenant);
        tenant.setId(id);
        this.updateById(tenant);
    }

    private SysTenantVO convertToVO(SysTenant tenant) {
        if (tenant == null) {
            return null;
        }
        SysTenantVO vo = new SysTenantVO();
        BeanUtils.copyProperties(tenant, vo);
        // 填充套餐名称
        if (tenant.getPackageId() != null) {
            SysTenantPackage pkg = tenantPackageDao.selectById(tenant.getPackageId());
            if (pkg != null) {
                vo.setPackageName(pkg.getPackageName());
            }
        }
        return vo;
    }

}
