package com.lanjii.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.lanjii.core.annotation.MultiRequestBody;
import com.lanjii.core.base.support.PageData;
import com.lanjii.core.base.support.PageParam;
import com.lanjii.core.enums.ResultCode;
import com.lanjii.core.obj.R;
import com.lanjii.util.AuthUtils;
import com.lanjii.util.LocalCacheUtils;
import com.lanjii.util.PageDataUtils;
import com.lanjii.model.dto.ChangePwdDto;
import com.lanjii.model.dto.SysUserDto;
import com.lanjii.model.entity.SysUser;
import com.lanjii.service.impl.SysUserServiceImpl;
import com.lanjii.model.vo.SysUserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lizheng
 * @since 2023-08-28
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/sys-user")
public class SysUserController {

    public static final String PWD = "123456";
    private final SysUserServiceImpl sysUserService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/switchStatus")
    public R<String> switchStatus(@MultiRequestBody Long userId) {
        SysUser user = sysUserService.getById(userId);
        user.setStatus(user.getStatus() == 1 ? 0 : 1);
        sysUserService.updateById(user);
        return R.success();
    }

    /**
     * 重置密码
     */
    @PostMapping("/resetPwd")
    public R<String> resetPwd(@MultiRequestBody Long userId) {
        SysUser user = sysUserService.getById(userId);
        user.setPassword(passwordEncoder.encode(PWD));
        sysUserService.updateById(user);
        LocalCacheUtils.validate(LocalCacheUtils.CacheType.OTHER, "auth:" + user.getUserName());
        return R.success();
    }

    /**
     * 修改密码
     */
    @PostMapping("/changePwd")
    public R<String> changePwd(@Validated @RequestBody ChangePwdDto changePwdDto) {

        Long userId = AuthUtils.getCurrentUser().getSysUser().getId();
        SysUser user = sysUserService.getById(userId);
        if (!passwordEncoder.matches(changePwdDto.getOldPwd(), user.getPassword())) {
            return R.fail(ResultCode.WRONG_PASSWORD);
        }
        user.setPassword(passwordEncoder.encode(changePwdDto.getNewPwd()));
        sysUserService.updateById(user);
        LocalCacheUtils.validate(LocalCacheUtils.CacheType.OTHER, "auth:" + user.getUserName());
        return R.success();
    }

    /**
     * 获取当前用户信息
     */
    @GetMapping("/current")
    public SysUserVo current() {
        SysUser sysUser = AuthUtils.getCurrentUser().getSysUser();
        return SysUser.INSTANCE.modelToVo(sysUser);
    }

    @PostMapping("/list")
    public R<PageData<SysUserVo>> list(@MultiRequestBody PageParam pageParam, @MultiRequestBody SysUserDto sysUserFilter) {
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<SysUser> list = sysUserService.getListByFilter(sysUserFilter);
        return R.success(PageDataUtils.make(list, SysUser.INSTANCE));
    }

    @GetMapping("/query")
    public R<SysUserVo> query(@RequestParam Long id) {
        return R.success(sysUserService.getByIdNew(id));
    }

    @PostMapping("/save")
    public R<SysUser> save(@MultiRequestBody SysUserDto sysUserDto) {
        if (sysUserDto.getId() == 1) {
            return R.fail("超级管理员禁止操作");
        }
        String userName = sysUserDto.getUserName();
        LambdaQueryWrapper<SysUser> query = new LambdaQueryWrapper<>();
        query.eq(SysUser::getUserName, userName);
        if (sysUserService.count(query) > 0) {
            return R.fail("用户名已存在");
        }
        sysUserService.saveOrUpdateNew(sysUserDto);
        return R.success();
    }

    @PostMapping("/update")
    public R<SysUser> update(@MultiRequestBody @Validated SysUserDto sysUserDto) {
        SysUser originalSysUser = sysUserService.getById(sysUserDto.getId());
        if (originalSysUser == null) {
            return R.fail(ResultCode.DATA_NOT_EXIST);
        }
        sysUserService.saveOrUpdateNew(sysUserDto);
        return R.success();
    }

    @GetMapping("/del")
    public R<SysUser> delete(@RequestParam Long id) {
        if (id == 1) {
            return R.fail("超级管理员禁止删除");
        }
        if (Objects.equals(AuthUtils.getCurrentUser().getSysUser().getId(), id)) {
            return R.fail("禁止删除自己");
        }
        sysUserService.removeById(id);
        return R.success();
    }
}
