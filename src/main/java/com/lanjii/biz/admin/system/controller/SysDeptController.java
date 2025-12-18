package com.lanjii.biz.admin.system.controller;


import com.github.pagehelper.PageHelper;
import com.lanjii.common.enums.BusinessTypeEnum;
import com.lanjii.core.annotation.Log;
import com.lanjii.core.base.PageParam;
import com.lanjii.core.base.support.PageData;
import com.lanjii.core.resp.R;
import com.lanjii.biz.admin.system.model.dto.SysDeptDTO;
import com.lanjii.biz.admin.system.model.entity.SysDept;
import com.lanjii.biz.admin.system.service.SysDeptService;
import com.lanjii.common.util.PageDataUtils;
import com.lanjii.common.util.TreeUtils;
import com.lanjii.biz.admin.system.model.vo.SysDeptVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门管理
 *
 * @author lanjii
 */
@RestController
@RequestMapping("/admin/sys/depts")
@RequiredArgsConstructor
public class SysDeptController {

    private final SysDeptService sysDeptService;

    /**
     * 树形部门
     */
    @PreAuthorize("hasAuthority('sys:dept:tree')")
    @GetMapping("tree")
    public R<List<SysDeptVO>> tree(SysDeptDTO filter) {
        List<SysDept> deptList = sysDeptService.listByFilter(filter);
        List<SysDeptVO> deptVOList = SysDept.INSTANCE.toVo(deptList);
        List<SysDeptVO> tree = TreeUtils.buildTree(deptVOList);
        return R.success(tree);
    }

    /**
     * 查询详情
     *
     * @param id 数据 ID
     */
    @PreAuthorize("hasAuthority('sys:dept:view')")
    @GetMapping("/{id}")
    public R<SysDeptVO> getById(@PathVariable Long id) {
        return R.success(sysDeptService.getByIdNew(id));
    }

    /**
     * 分页查询
     *
     * @param pageParam 分页查询参数
     * @param filter    查询条件
     */
    @PreAuthorize("hasAuthority('sys:dept:page')")
    @GetMapping
    public R<PageData<SysDeptVO>> page(PageParam pageParam, SysDeptDTO filter) {
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<SysDept> sysDeptList = sysDeptService.listByFilter(filter);
        return R.success(PageDataUtils.make(sysDeptList, SysDept.INSTANCE));
    }

    /**
     * 保存
     *
     * @param dto 保存的对象
     */
    @Log(title = "部门管理", businessType = BusinessTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sys:dept:save')")
    @PostMapping
    public R<Void> save(@Validated @RequestBody SysDeptDTO dto) {
        sysDeptService.saveNew(dto);
        return R.success();
    }

    /**
     * 更新
     *
     * @param id  数据ID
     * @param dto 更新的对象
     */
    @Log(title = "部门管理", businessType = BusinessTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:dept:update')")
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @Validated @RequestBody SysDeptDTO dto) {
        sysDeptService.updateByIdNew(id, dto);
        return R.success();
    }

    /**
     * 删除
     *
     * @param id 数据 ID
     */
    @Log(title = "部门管理", businessType = BusinessTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:dept:delete')")
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        sysDeptService.deleteDeptWithChildren(id);
        return R.success();
    }
}
