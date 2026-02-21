package com.lanjii.ai.controller;

import com.github.pagehelper.PageHelper;
import com.lanjii.ai.api.dto.AiModelConfigDTO;
import com.lanjii.ai.entity.AiModelConfig;
import com.lanjii.ai.api.vo.AiModelConfigVO;
import com.lanjii.ai.service.AiModelConfigService;
import com.lanjii.common.enums.BusinessTypeEnum;
import com.lanjii.framework.mp.base.PageDataUtils;
import com.lanjii.framework.log.annotation.Log;
import com.lanjii.common.support.PageParam;
import com.lanjii.common.support.PageData;
import com.lanjii.common.response.R;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AI 模型配置管理
 *
 * @author lanjii
 */
@RestController
@RequestMapping("/admin/ai/model-config")
@RequiredArgsConstructor
public class AiModelConfigController {

    private final AiModelConfigService aiModelConfigService;

    /**
     * 分页查询模型配置列表
     */
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('ai:model-config:page')")
    public R<PageData<AiModelConfigVO>> page(PageParam pageParam, AiModelConfigDTO filter) {
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<AiModelConfig> list = aiModelConfigService.listByFilter(filter);
        return R.success(PageDataUtils.make(list, AiModelConfig.INSTANCE));
    }

    /**
     * 根据ID获取配置详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ai:model-config:view')")
    public R<AiModelConfigVO> getById(@PathVariable Long id) {
        return R.success(aiModelConfigService.getByIdNew(id));
    }

    /**
     * 新增模型配置
     */
    @PostMapping
    @PreAuthorize("hasAuthority('ai:model-config:save')")
    @Log(title = "新增模型配置", businessType = BusinessTypeEnum.INSERT)
    public R<Void> save(@RequestBody @Validated AiModelConfigDTO dto) {
        aiModelConfigService.saveNew(dto);
        return R.success();
    }

    /**
     * 修改模型配置
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ai:model-config:update')")
    @Log(title = "修改模型配置", businessType = BusinessTypeEnum.UPDATE)
    public R<Void> updateById(@PathVariable Long id, @RequestBody @Validated AiModelConfigDTO dto) {
        aiModelConfigService.updateByIdNew(id, dto);
        return R.success();
    }

    /**
     * 删除模型配置
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ai:model-config:delete')")
    @Log(title = "删除模型配置", businessType = BusinessTypeEnum.DELETE)
    public R<Void> removeById(@PathVariable Long id) {
        aiModelConfigService.removeByIdNew(id);
        return R.success();
    }

    /**
     * 将指定配置设为默认
     */
    @PostMapping("/{id}/default")
    @PreAuthorize("hasAuthority('ai:model-config:default')")
    @Log(title = "设置默认模型配置", businessType = BusinessTypeEnum.UPDATE)
    public R<Void> setDefault(@PathVariable Long id) {
        aiModelConfigService.setDefault(id);
        return R.success();
    }

    /**
     * 切换启用状态
     */
    @PutMapping("/{id}/enabled")
    @PreAuthorize("hasAuthority('ai:model-config:toggle')")
    @Log(title = "切换模型配置启用状态", businessType = BusinessTypeEnum.UPDATE)
    public R<Void> toggleEnabled(@PathVariable Long id, @RequestParam Integer enabled) {
        aiModelConfigService.toggleEnabled(id, enabled);
        return R.success();
    }
}
