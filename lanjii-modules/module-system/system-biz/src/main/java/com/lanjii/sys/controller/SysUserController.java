package com.lanjii.sys.controller;


import com.github.pagehelper.PageHelper;
import com.lanjii.sys.entity.SysUser;
import com.lanjii.system.api.dto.SysUserDTO;
import com.lanjii.system.api.dto.UpdateProfileDTO;
import com.lanjii.system.api.vo.SysUserVO;
import com.lanjii.sys.service.SysUserService;
import com.lanjii.framework.log.annotation.Log;
import com.lanjii.common.support.PageParam;
import com.lanjii.common.support.PageData;
import com.lanjii.common.enums.BusinessTypeEnum;
import com.lanjii.common.response.R;
import com.lanjii.framework.web.util.ExcelUtils;
import com.lanjii.framework.mp.base.PageDataUtils;
import com.lanjii.framework.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户管理
 *
 * @author lanjii
 */
@RestController
@RequestMapping("/admin/sys/users")
@RequiredArgsConstructor
public class SysUserController {

    private final SysUserService sysUserService;

    /**
     * 获取当前登录用户信息
     */
    @GetMapping("/me")
    public R<SysUserVO> getCurrentUser() {
        Long userId = SecurityUtils.getCurrentUserId();
        SysUserVO vo = sysUserService.getByIdNew(userId);
        return R.success(vo);
    }

    /**
     * 修改当前用户自己的信息
     */
    @Log(title = "修改个人信息", businessType = BusinessTypeEnum.UPDATE)
    @PutMapping("/me")
    public R<Void> updateProfile(@Validated @RequestBody UpdateProfileDTO dto) {
        Long userId = SecurityUtils.getCurrentUserId();
        sysUserService.updateProfile(userId, dto);
        return R.success();
    }

    /**
     * 查询详情
     *
     * @param id 数据 ID
     */
    @PreAuthorize("hasAuthority('sys:user:view')")
    @GetMapping("/{id}")
    public R<SysUserVO> getById(@PathVariable Long id) {
        SysUserVO vo = sysUserService.getByIdNew(id);
        return R.success(vo);
    }

    /**
     * 分页查询
     *
     * @param pageParam 分页查询参数
     * @param filter    查询条件
     */
    @PreAuthorize("hasAuthority('sys:user:page')")
    @GetMapping
    public R<PageData<SysUserVO>> page(PageParam pageParam, SysUserDTO filter) {
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<SysUser> sysUserList = sysUserService.listByFilter(filter);
        return R.success(PageDataUtils.make(sysUserList, SysUser.INSTANCE));
    }

    /**
     * 保存
     *
     * @param dto 保存的对象
     */
    @Log(title = "用户管理", businessType = BusinessTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sys:user:save')")
    @PostMapping
    public R<Void> save(@Validated @RequestBody SysUserDTO dto) {
        sysUserService.saveNew(dto);
        return R.success();
    }

    /**
     * 更新
     *
     * @param id  数据ID
     * @param dto 更新的对象
     */
    @Log(title = "用户管理", businessType = BusinessTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:user:update')")
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @Validated @RequestBody SysUserDTO dto) {
        sysUserService.updateByIdNew(id, dto);
        return R.success();
    }

    /**
     * 删除
     *
     * @param id 数据 ID
     */
    @Log(title = "用户管理", businessType = BusinessTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:user:delete')")
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        sysUserService.removeByIdNew(id);
        return R.success();
    }

    /**
     * 导出用户数据
     *
     * @param filter 查询条件
     */
    @PreAuthorize("hasAuthority('sys:user:export')")
    @GetMapping("/export")
    public void exportUsers(SysUserDTO filter) {
        try {
            List<SysUser> userList = sysUserService.listByFilter(filter);
            List<SysUserVO> voList = SysUser.INSTANCE.toVo(userList);
            ExcelUtils.exportExcel("用户列表", "用户数据", SysUserVO.class, voList);
        } catch (Exception e) {
            throw new RuntimeException("导出用户信息失败", e);
        }
    }

    /**
     * 重置用户密码
     *
     * @param id 用户ID
     */
    @Log(title = "重置用户密码", businessType = BusinessTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:user:reset-pwd')")
    @PutMapping("/{id}/reset-pwd")
    public R<Void> resetPassword(@PathVariable Long id) {
        sysUserService.resetPassword(id);
        return R.success();
    }

    /**
     * 切换用户状态
     *
     * @param id 用户ID
     */
    @Log(title = "切换用户状态", businessType = BusinessTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:user:status')")
    @PutMapping("/{id}/status")
    public R<Void> toggleStatus(@PathVariable Long id) {
        sysUserService.toggleStatus(id);
        return R.success();
    }

}
