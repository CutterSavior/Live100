package com.lanjii.biz.admin.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.lanjii.common.enums.BusinessTypeEnum;
import com.lanjii.core.annotation.Log;
import com.lanjii.core.base.PageParam;
import com.lanjii.core.base.support.PageData;
import com.lanjii.core.resp.R;
import com.lanjii.biz.admin.system.model.dto.SysPostDTO;
import com.lanjii.biz.admin.system.model.entity.SysPost;
import com.lanjii.common.enums.IsEnabledEnum;
import com.lanjii.biz.admin.system.service.SysPostService;
import com.lanjii.common.util.PageDataUtils;
import com.lanjii.biz.admin.system.model.vo.SysPostVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 岗位管理
 *
 * @author lanjii
 */
@RestController
@RequestMapping("/admin/sys/posts")
@RequiredArgsConstructor
public class SysPostController {

    private final SysPostService sysPostService;

    /**
     * 查询详情
     *
     * @param id 数据 ID
     */
    @PreAuthorize("hasAuthority('sys:post:view')")
    @GetMapping("/{id}")
    public R<SysPostVO> getById(@PathVariable Long id) {
        return R.success(sysPostService.getByIdNew(id));
    }

    /**
     * 查询所有
     */
    @PreAuthorize("hasAuthority('sys:post:list')")
    @GetMapping("all")
    public R<List<SysPostVO>> all() {
        LambdaQueryWrapper<SysPost> sysRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysRoleLambdaQueryWrapper.eq(SysPost::getIsEnabled, IsEnabledEnum.ENABLED.getCode());
        List<SysPost> sysRoleList = sysPostService.list(sysRoleLambdaQueryWrapper);
        return R.success(SysPost.INSTANCE.toVo(sysRoleList));
    }

    /**
     * 分页查询
     *
     * @param pageParam 分页查询参数
     * @param filter 查询条件
     */
    @PreAuthorize("hasAuthority('sys:post:page')")
    @GetMapping
    public R<PageData<SysPostVO>> page(PageParam pageParam, SysPostDTO filter) {
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<SysPost> sysPostList = sysPostService.listByFilter(filter);
        return R.success(PageDataUtils.make(sysPostList, SysPost.INSTANCE));
    }

    /**
     * 保存
     *
     * @param dto 保存的对象
     */
    @Log(title = "岗位管理", businessType = BusinessTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sys:post:save')")
    @PostMapping
    public R<Void> save(@Validated @RequestBody SysPostDTO dto) {
        sysPostService.saveNew(dto);
        return R.success();
    }

    /**
     * 更新
     */
    @Log(title = "岗位管理", businessType = BusinessTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:post:update')")
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @Validated @RequestBody SysPostDTO dto) {
        sysPostService.updateByIdNew(id, dto);
        return R.success();
    }

    /**
     * 删除
     *
     * @param id 数据 ID
     */
    @Log(title = "岗位管理", businessType = BusinessTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:post:delete')")
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        sysPostService.removeById(id);
        return R.success();
    }
}
