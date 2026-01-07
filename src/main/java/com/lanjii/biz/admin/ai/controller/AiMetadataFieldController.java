package com.lanjii.biz.admin.ai.controller;

import com.github.pagehelper.PageHelper;
import com.lanjii.biz.admin.ai.model.dto.AiMetadataFieldDTO;
import com.lanjii.biz.admin.ai.model.entity.AiMetadataField;
import com.lanjii.biz.admin.ai.model.vo.AiMetadataFieldVO;
import com.lanjii.biz.admin.ai.service.AiMetadataFieldService;
import com.lanjii.common.enums.BusinessTypeEnum;
import com.lanjii.common.util.PageDataUtils;
import com.lanjii.core.annotation.Log;
import com.lanjii.core.base.PageParam;
import com.lanjii.core.base.support.PageData;
import com.lanjii.core.resp.R;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AI 元数据字段配置管理
 *
 * @author lanjii
 */
@RestController
@RequestMapping("/admin/ai/metadata-field")
@RequiredArgsConstructor
public class AiMetadataFieldController {

    private final AiMetadataFieldService aiMetadataFieldService;

    /**
     * 分页查询 元数据字段 列表
     */
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('ai:metadata-field:page')")
    public R<PageData<AiMetadataFieldVO>> page(PageParam pageParam, AiMetadataFieldDTO filter) {
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<AiMetadataField> list = aiMetadataFieldService.listByFilter(filter);
        return R.success(PageDataUtils.make(list, AiMetadataField.INSTANCE));
    }

    /**
     * 根据ID获取详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ai:metadata-field:view')")
    public R<AiMetadataFieldVO> getById(@PathVariable Long id) {
        return R.success(aiMetadataFieldService.getByIdNew(id));
    }

    /**
     * 新增 元数据字段
     */
    @PostMapping
    @PreAuthorize("hasAuthority('ai:metadata-field:save')")
    @Log(title = "新增元数据字段", businessType = BusinessTypeEnum.INSERT)
    public R<Void> save(@RequestBody @Validated AiMetadataFieldDTO dto) {
        aiMetadataFieldService.saveNew(dto);
        return R.success();
    }

    /**
     * 修改 元数据字段
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ai:metadata-field:update')")
    @Log(title = "修改元数据字段", businessType = BusinessTypeEnum.UPDATE)
    public R<Void> updateById(@PathVariable Long id, @RequestBody @Validated AiMetadataFieldDTO dto) {
        aiMetadataFieldService.updateByIdNew(id, dto);
        return R.success();
    }

    /**
     * 删除 元数据字段
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ai:metadata-field:delete')")
    @Log(title = "删除元数据字段", businessType = BusinessTypeEnum.DELETE)
    public R<Void> removeById(@PathVariable Long id) {
        aiMetadataFieldService.removeByIdNew(id);
        return R.success();
    }
}
