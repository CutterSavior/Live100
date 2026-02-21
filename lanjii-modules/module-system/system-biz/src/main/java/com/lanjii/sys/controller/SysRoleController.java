package com.lanjii.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.lanjii.common.enums.BusinessTypeEnum;
import com.lanjii.framework.log.annotation.Log;
import com.lanjii.common.support.PageParam;
import com.lanjii.common.support.PageData;
import com.lanjii.common.response.R;
import com.lanjii.sys.entity.SysRole;
import com.lanjii.system.api.dto.RoleMenuAssignDTO;
import com.lanjii.system.api.dto.SysRoleDTO;
import com.lanjii.common.enums.IsEnabledEnum;
import com.lanjii.sys.service.SysRoleService;
import com.lanjii.framework.mp.base.PageDataUtils;
import com.lanjii.system.api.vo.SysRoleVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色管理
 *
 * @author lanjii
 */
@RestController
@RequestMapping("/admin/sys/roles")
@RequiredArgsConstructor
public class SysRoleController {

    private final SysRoleService sysRoleService;

    /**
     * 查询详情
     *
     * @param id 数据 ID
     */
    @PreAuthorize("hasAuthority('sys:role:view')")
    @GetMapping("/{id}")
    public R<SysRoleVO> getById(@PathVariable Long id) {
        return R.success(sysRoleService.getByIdNew(id));
    }

    /**
     * 查询所有
     */
    @PreAuthorize("hasAuthority('sys:role:list')")
    @GetMapping("all")
    public R<List<SysRoleVO>> all() {
        LambdaQueryWrapper<SysRole> sysRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysRoleLambdaQueryWrapper.eq(SysRole::getIsEnabled, IsEnabledEnum.ENABLED.getCode());
        List<SysRole> sysRoleList = sysRoleService.list(sysRoleLambdaQueryWrapper);
        return R.success(SysRole.INSTANCE.toVo(sysRoleList));
    }

    /**
     * 分页查询
     *
     * @param pageParam 分页查询参数
     * @param filter 查询条件
     */
    @PreAuthorize("hasAuthority('sys:role:page')")
    @GetMapping
    public R<PageData<SysRoleVO>> page(PageParam pageParam, SysRoleDTO filter) {
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<SysRole> sysRoleList = sysRoleService.listByFilter(filter);
        return R.success(PageDataUtils.make(sysRoleList, SysRole.INSTANCE));
    }

    /**
     * 保存
     *
     * @param dto 保存的对象
     */
    @Log(title = "角色管理", businessType = BusinessTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sys:role:save')")
    @PostMapping
    public R<Void> save(@Validated @RequestBody SysRoleDTO dto) {
        sysRoleService.saveNew(dto);
        return R.success();
    }

    /**
     * 更新
     *
     * @param dto 更新的对象
     */
    @Log(title = "角色管理", businessType = BusinessTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:role:update')")
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @Validated @RequestBody SysRoleDTO dto) {
        sysRoleService.updateByIdNew(id, dto);
        return R.success();
    }

    /**
     * 删除
     *
     * @param id 数据 ID
     */
    @Log(title = "角色管理", businessType = BusinessTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:role:delete')")
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        sysRoleService.removeById(id);
        return R.success();
    }

    /**
     * 获取角色已分配的菜单ID列表
     *
     * @param roleId 角色ID
     * @return 菜单ID列表
     */
    @PreAuthorize("hasAuthority('sys:role:permission')")
    @GetMapping("/{roleId}/menus")
    public R<List<Long>> getRoleMenus(@PathVariable Long roleId) {
        List<Long> menuIds = sysRoleService.getRoleMenuIds(roleId);
        return R.success(menuIds);
    }

    /**
     * 为角色分配菜单权限
     *
     * @param roleId 角色ID
     * @param dto 菜单分配DTO
     * @return 操作结果
     */
    @Log(title = "分配角色权限", businessType = BusinessTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:role:permission')")
    @PostMapping("/{roleId}/menus")
    public R<Void> assignMenusToRole(@PathVariable Long roleId, @Validated @RequestBody RoleMenuAssignDTO dto) {
        sysRoleService.assignMenusToRole(roleId, dto.getMenuIds());
        return R.success();
    }
}
