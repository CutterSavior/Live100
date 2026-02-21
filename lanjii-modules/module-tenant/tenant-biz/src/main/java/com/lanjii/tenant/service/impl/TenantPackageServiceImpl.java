package com.lanjii.tenant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanjii.common.exception.BizException;
import com.lanjii.tenant.dao.SysTenantPackageDao;
import com.lanjii.tenant.entity.SysTenantPackage;
import com.lanjii.tenant.api.dto.SysTenantPackageDTO;
import com.lanjii.tenant.api.vo.SysTenantPackageVO;
import com.lanjii.tenant.service.TenantPackageService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 租户套餐服务实现类
 *
 * @author lanjii
 */
@Service
public class TenantPackageServiceImpl extends ServiceImpl<SysTenantPackageDao, SysTenantPackage> implements TenantPackageService {

    @Override
    public List<Long> getMenuIdsByPackageId(Long packageId) {
        if (packageId == null) {
            return Collections.emptyList();
        }
        SysTenantPackage pkg = this.getById(packageId);
        if (pkg == null || pkg.getMenuIds() == null || pkg.getMenuIds().isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.stream(pkg.getMenuIds().split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }

    @Override
    public List<SysTenantPackageVO> listPackages(SysTenantPackageDTO dto) {
        LambdaQueryWrapper<SysTenantPackage> wrapper = new LambdaQueryWrapper<>();
        if (dto.getPackageName() != null) {
            wrapper.like(SysTenantPackage::getPackageName, dto.getPackageName());
        }
        if (dto.getStatus() != null) {
            wrapper.eq(SysTenantPackage::getStatus, dto.getStatus());
        }
        wrapper.orderByDesc(SysTenantPackage::getCreateTime);

        List<SysTenantPackage> list = this.list(wrapper);
        return list.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public SysTenantPackageVO getByIdNew(Long id) {
        SysTenantPackage pkg = this.getById(id);
        return convertToVO(pkg);
    }

    @Override
    public void saveNew(SysTenantPackageDTO dto) {
        SysTenantPackage pkg = new SysTenantPackage();
        pkg.setPackageName(dto.getPackageName());
        pkg.setStatus(dto.getStatus());
        pkg.setRemark(dto.getRemark());
        if (dto.getMenuIds() != null && !dto.getMenuIds().isEmpty()) {
            pkg.setMenuIds(dto.getMenuIds().stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(",")));
        }
        this.save(pkg);
    }

    @Override
    public void updateByIdNew(Long id, SysTenantPackageDTO dto) {
        SysTenantPackage pkg = this.getById(id);
        if (pkg == null) {
            throw new BizException("套餐不存在");
        }

        pkg.setPackageName(dto.getPackageName());
        pkg.setStatus(dto.getStatus());
        pkg.setRemark(dto.getRemark());
        if (dto.getMenuIds() != null) {
            pkg.setMenuIds(dto.getMenuIds().stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(",")));
        }
        this.updateById(pkg);
    }

    private SysTenantPackageVO convertToVO(SysTenantPackage pkg) {
        if (pkg == null) {
            return null;
        }
        SysTenantPackageVO vo = new SysTenantPackageVO();
        BeanUtils.copyProperties(pkg, vo);
        vo.setMenuIdList(getMenuIdsByPackageId(pkg.getId()));
        return vo;
    }

}
