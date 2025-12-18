package com.lanjii.biz.admin.system.controller;

import com.github.pagehelper.PageHelper;
import com.lanjii.common.enums.BusinessTypeEnum;
import com.lanjii.core.annotation.Log;
import com.lanjii.core.base.PageParam;
import com.lanjii.core.base.support.PageData;
import com.lanjii.core.resp.R;
import com.lanjii.biz.admin.system.model.dto.SysMenuDTO;
import com.lanjii.biz.admin.system.model.entity.SysMenu;
import com.lanjii.security.AuthUser;
import com.lanjii.biz.admin.system.service.SysMenuService;
import com.lanjii.common.util.PageDataUtils;
import com.lanjii.common.util.SecurityUtils;
import com.lanjii.common.util.TreeUtils;
import com.lanjii.biz.admin.system.model.vo.SysMenuVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StopWatch;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单管理
 *
 * @author lanjii
 */
@Slf4j
@RestController
@RequestMapping("/admin/sys/menus")
@RequiredArgsConstructor
public class SysMenuController {

    private final SysMenuService sysMenuService;

    /**
     * 获取当前用户的菜单树
     */
    @GetMapping("/user")
    public R<List<SysMenuVO>> getUserMenus() {
        AuthUser currentUser = SecurityUtils.getCurrentUser();
        List<SysMenuVO> menuTree = sysMenuService.getUserMenuTree(currentUser.getUserId(), currentUser.getIsAdmin());
        return R.success(menuTree);
    }

    /**
     * 树形菜单（管理员查看所有菜单）
     */
    @PreAuthorize("hasAuthority('sys:menu:tree')")
    @GetMapping("/tree")
    public R<List<SysMenuVO>> tree() {
        List<SysMenu> menuList = sysMenuService.list();
        List<SysMenuVO> voList = SysMenu.INSTANCE.toVo(menuList);
        List<SysMenuVO> tree = TreeUtils.buildTree(voList);
        return R.success(tree);
    }

    /**
     * 查询详情
     *
     * @param id 数据 ID
     */
    @PreAuthorize("hasAuthority('sys:menu:view')")
    @GetMapping("/{id}")
    public R<SysMenuVO> getById(@PathVariable Long id) {
        return R.success(sysMenuService.getByIdNew(id));
    }

    /**
     * 分页查询
     *
     * @param pageParam 分页查询参数
     * @param filter 查询条件
     */
    @PreAuthorize("hasAuthority('sys:menu:page')")
    @GetMapping
    public R<PageData<SysMenuVO>> page(PageParam pageParam, SysMenuDTO filter) {
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<SysMenu> sysMenuList = sysMenuService.listByFilter(filter);
        return R.success(PageDataUtils.make(sysMenuList, SysMenu.INSTANCE));
    }

    /**
     * 保存
     *
     * @param dto 保存的对象
     */
    @Log(title = "菜单管理", businessType = BusinessTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sys:menu:save')")
    @PostMapping
    public R<Void> save(@Validated @RequestBody SysMenuDTO dto) {
        sysMenuService.saveNew(dto);
        return R.success();
    }

    /**
     * 更新
     *
     * @param id 数据ID
     * @param dto 更新的对象
     */
    @Log(title = "菜单管理", businessType = BusinessTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:menu:update')")
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @Validated @RequestBody SysMenuDTO dto) {
        sysMenuService.updateByIdNew(id, dto);
        return R.success();
    }

    /**
     * 删除菜单及其所有子菜单
     *
     * @param id 数据 ID
     */
    @Log(title = "菜单管理", businessType = BusinessTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:menu:delete')")
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        sysMenuService.deleteMenuWithChildren(id);
        return R.success();
    }
}