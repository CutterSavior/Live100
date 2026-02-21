package com.lanjii.sys.controller;


import com.github.pagehelper.PageHelper;
import com.lanjii.common.enums.BusinessTypeEnum;
import com.lanjii.framework.log.annotation.Log;
import com.lanjii.common.support.PageParam;
import com.lanjii.common.support.PageData;
import com.lanjii.common.response.R;
import com.lanjii.sys.entity.SysDictData;
import com.lanjii.system.api.dto.SysDictDataDTO;
import com.lanjii.sys.service.SysDictDataService;
import com.lanjii.framework.mp.base.PageDataUtils;
import com.lanjii.system.api.vo.SysDictDataVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典管理/字典数据
 *
 * @author lanjii
 */
@RestController
@RequestMapping("/admin/sys/dict-data")
@RequiredArgsConstructor
public class SysDictDataController {

    private final SysDictDataService sysDictDataService;

    /**
     * 查询详情
     *
     * @param id 数据 ID
     */
    @PreAuthorize("hasAuthority('sys:dict-data:view')")
    @GetMapping("/{id}")
    public R<SysDictDataVO> getById(@PathVariable Long id) {
        return R.success(sysDictDataService.getByIdNew(id));
    }

    /**
     * 分页查询
     *
     * @param pageParam 分页查询参数
     * @param filter 查询条件
     */
    @PreAuthorize("hasAuthority('sys:dict-data:page')")
    @GetMapping
    public R<PageData<SysDictDataVO>> page(PageParam pageParam, SysDictDataDTO filter) {
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<SysDictData> sysDictDataList = sysDictDataService.listByFilter(filter);
        return R.success(PageDataUtils.make(sysDictDataList, SysDictData.INSTANCE));
    }

    /**
     * 保存
     *
     * @param dto 保存的对象
     */
    @Log(title = "字典数据管理", businessType = BusinessTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('sys:dict-data:save')")
    @PostMapping
    public R<Void> save(@Validated @RequestBody SysDictDataDTO dto) {
        sysDictDataService.saveNew(dto);
        return R.success();
    }

    /**
     * 更新
     *
     * @param id 数据ID
     * @param dto 更新的对象
     */
    @Log(title = "字典数据管理", businessType = BusinessTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:dict-data:update')")
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @Validated @RequestBody SysDictDataDTO dto) {
        sysDictDataService.updateByIdNew(id, dto);
        return R.success();
    }

    /**
     * 删除
     *
     * @param id 数据 ID
     */
    @Log(title = "字典数据管理", businessType = BusinessTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('sys:dict-data:delete')")
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        sysDictDataService.removeById(id);
        return R.success();
    }

    /**
     * 清除字典数据缓存
     */
    @Log(title = "清除字典缓存", businessType = BusinessTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('sys:dict-data:cache')")
    @PostMapping("/cache/clear")
    public R<Void> clearCache() {
        sysDictDataService.clearCache();
        return R.success();
    }

}
