package com.lanjii.tenant.controller;

import com.lanjii.common.enums.BusinessTypeEnum;
import com.lanjii.common.response.R;
import com.lanjii.framework.log.annotation.Log;
import com.lanjii.tenant.api.dto.SysTenantPackageDTO;
import com.lanjii.tenant.api.vo.SysTenantPackageVO;
import com.lanjii.tenant.service.TenantPackageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 租户套餐管理控制器
 *
 * @author lanjii
 */
@RestController
@RequestMapping("/admin/tenant/package")
@RequiredArgsConstructor
public class TenantPackageController {

    private final TenantPackageService tenantPackageService;

    /**
     * 查询套餐列表
     */
    @PreAuthorize("hasAuthority('tenant:package:list')")
    @GetMapping
    public R<List<SysTenantPackageVO>> list(SysTenantPackageDTO dto) {
        List<SysTenantPackageVO> list = tenantPackageService.listPackages(dto);
        return R.success(list);
    }

    /**
     * 获取套餐详情
     */
    @PreAuthorize("hasAuthority('tenant:package:query')")
    @GetMapping("/{id}")
    public R<SysTenantPackageVO> getById(@PathVariable Long id) {
        SysTenantPackageVO vo = tenantPackageService.getByIdNew(id);
        return R.success(vo);
    }

    /**
     * 新增套餐
     */
    @Log(title = "新增租户套餐", businessType = BusinessTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('tenant:package:add')")
    @PostMapping
    public R<Void> add(@Valid @RequestBody SysTenantPackageDTO dto) {
        tenantPackageService.saveNew(dto);
        return R.success();
    }

    /**
     * 修改套餐
     */
    @Log(title = "修改租户套餐", businessType = BusinessTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('tenant:package:edit')")
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @Valid @RequestBody SysTenantPackageDTO dto) {
        tenantPackageService.updateByIdNew(id, dto);
        return R.success();
    }

    /**
     * 删除套餐
     */
    @Log(title = "删除租户套餐", businessType = BusinessTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('tenant:package:remove')")
    @DeleteMapping("/{id}")
    public R<Void> remove(@PathVariable Long id) {
        tenantPackageService.removeById(id);
        return R.success();
    }

}
