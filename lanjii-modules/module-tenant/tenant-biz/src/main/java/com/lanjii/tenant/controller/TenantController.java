package com.lanjii.tenant.controller;

import com.lanjii.common.enums.BusinessTypeEnum;
import com.lanjii.common.response.R;
import com.lanjii.framework.log.annotation.Log;
import com.lanjii.tenant.api.dto.SysTenantDTO;
import com.lanjii.tenant.api.vo.SysTenantVO;
import com.lanjii.tenant.service.TenantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 租户管理控制器
 *
 * @author lanjii
 */
@RestController
@RequestMapping("/admin/tenant")
@RequiredArgsConstructor
public class TenantController {

    private final TenantService tenantService;

    /**
     * 查询租户列表
     */
    @PreAuthorize("hasAuthority('tenant:list')")
    @GetMapping
    public R<List<SysTenantVO>> list(SysTenantDTO dto) {
        List<SysTenantVO> list = tenantService.listTenants(dto);
        return R.success(list);
    }

    /**
     * 获取租户详情
     */
    @PreAuthorize("hasAuthority('tenant:query')")
    @GetMapping("/{id}")
    public R<SysTenantVO> getById(@PathVariable Long id) {
        SysTenantVO vo = tenantService.getByIdNew(id);
        return R.success(vo);
    }

    /**
     * 新增租户
     */
    @Log(title = "新增租户", businessType = BusinessTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('tenant:add')")
    @PostMapping
    public R<Void> add(@Valid @RequestBody SysTenantDTO dto) {
        tenantService.saveNew(dto);
        return R.success();
    }

    /**
     * 修改租户
     */
    @Log(title = "修改租户", businessType = BusinessTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('tenant:edit')")
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @Valid @RequestBody SysTenantDTO dto) {
        tenantService.updateByIdNew(id, dto);
        return R.success();
    }

    /**
     * 删除租户
     */
    @Log(title = "删除租户", businessType = BusinessTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('tenant:remove')")
    @DeleteMapping("/{id}")
    public R<Void> remove(@PathVariable Long id) {
        tenantService.removeById(id);
        return R.success();
    }

}
