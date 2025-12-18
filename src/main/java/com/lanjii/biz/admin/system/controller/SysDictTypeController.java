package com.lanjii.biz.admin.system.controller;


import com.github.pagehelper.PageHelper;
import com.lanjii.common.enums.BusinessTypeEnum;
import com.lanjii.core.annotation.Log;
import com.lanjii.core.base.PageParam;
import com.lanjii.core.base.support.PageData;
import com.lanjii.core.resp.R;
import com.lanjii.biz.admin.system.model.dto.SysDictDataDTO;
import com.lanjii.biz.admin.system.model.dto.SysDictTypeDTO;
import com.lanjii.biz.admin.system.model.entity.SysDictData;
import com.lanjii.biz.admin.system.model.entity.SysDictType;
import com.lanjii.biz.admin.system.service.SysDictDataService;
import com.lanjii.biz.admin.system.service.SysDictTypeService;
import com.lanjii.common.util.PageDataUtils;
import com.lanjii.biz.admin.system.model.vo.SysDictDataVO;
import com.lanjii.biz.admin.system.model.vo.SysDictTypeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典管理/字典类型
 *
 * @author lanjii
 */
@RestController
@RequestMapping("/admin/sys/dict-types")
@RequiredArgsConstructor
public class SysDictTypeController {

    private final SysDictTypeService sysDictTypeService;
    private final SysDictDataService sysDictDataService;

    /**
     * 通过编码查询字典数据
     *
     * @param typeCode 字典类型编码
     */
    @PreAuthorize("hasAuthority('sys:dict-type:data')")
    @GetMapping("/{typeCode}/data")
    public R<PageData<SysDictDataVO>> typeCodeData(@PathVariable String typeCode, PageParam pageParam, SysDictDataDTO filter) {
        filter.setDictType(typeCode);
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<SysDictData> sysDictDataList = sysDictDataService.listByFilter(filter);
        return R.success(PageDataUtils.make(sysDictDataList, SysDictData.INSTANCE));
    }

    /**
     * 查询详情
     *
     * @param id 数据 ID
     */
    @PreAuthorize("hasAuthority('sys:dict-type:view')")
    @GetMapping("/{id}")
    public R<SysDictTypeVO> getById(@PathVariable Long id) {
        return R.success(sysDictTypeService.getByIdNew(id));
    }

    /**
     * 分页查询
     *
     * @param pageParam 分页查询参数
     * @param filter    查询条件
     */
    @PreAuthorize("hasAuthority('sys:dict-type:page')")
    @GetMapping
    public R<PageData<SysDictTypeVO>> page(PageParam pageParam, SysDictTypeDTO filter) {
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<SysDictType> sysDictTypeList = sysDictTypeService.listByFilter(filter);
        return R.success(PageDataUtils.make(sysDictTypeList, SysDictType.INSTANCE));
    }

    /**
     * 保存
     *
     * @param dto 保存的对象
     */
    @Log(title = "字典类型管理", businessType = BusinessTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sys:dict-type:save')")
    @PostMapping
    public R<Void> save(@Validated @RequestBody SysDictTypeDTO dto) {
        sysDictTypeService.saveNew(dto);
        return R.success();
    }

    /**
     * 更新
     *
     * @param id  数据ID
     * @param dto 更新的对象
     */
    @Log(title = "字典类型管理", businessType = BusinessTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:dict-type:update')")
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @Validated @RequestBody SysDictTypeDTO dto) {
        sysDictTypeService.updateByIdNew(id, dto);
        return R.success();
    }

    /**
     * 删除
     *
     * @param id 数据 ID
     */
    @Log(title = "字典类型管理", businessType = BusinessTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:dict-type:delete')")
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        sysDictTypeService.removeById(id);
        return R.success();
    }

}
