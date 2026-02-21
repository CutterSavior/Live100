package com.lanjii.ai.controller;

import com.github.pagehelper.PageHelper;
import com.lanjii.ai.api.dto.AiRolePromptDTO;
import com.lanjii.ai.entity.AiRolePrompt;
import com.lanjii.ai.api.vo.AiRolePromptVO;
import com.lanjii.ai.service.AiRolePromptService;
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
 * AI 角色与提示词管理
 *
 * @author lanjii
 */
@RestController
@RequestMapping("/admin/ai/role-prompt")
@RequiredArgsConstructor
public class AiRolePromptController {

    private final AiRolePromptService aiRolePromptService;

    /**
     * 分页查询 角色与提示词 列表
     */
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('ai:role-prompt:page')")
    public R<PageData<AiRolePromptVO>> page(PageParam pageParam, AiRolePromptDTO filter) {
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<AiRolePrompt> list = aiRolePromptService.listByFilter(filter);
        return R.success(PageDataUtils.make(list, AiRolePrompt.INSTANCE));
    }

    /**
     * 查询所有启用的 角色与提示词
     */
    @GetMapping("/all")
    public R<List<AiRolePromptVO>> allEnabled() {
        List<AiRolePrompt> list = aiRolePromptService.lambdaQuery()
                .eq(AiRolePrompt::getIsEnabled, 1)
                .list();
        return R.success(AiRolePrompt.INSTANCE.toVo(list));
    }

    /**
     * 根据ID获取详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ai:role-prompt:view')")
    public R<AiRolePromptVO> getById(@PathVariable Long id) {
        return R.success(aiRolePromptService.getByIdNew(id));
    }

    /**
     * 新增 角色与提示词
     */
    @PostMapping
    @PreAuthorize("hasAuthority('ai:role-prompt:save')")
    @Log(title = "新增角色与提示词", businessType = BusinessTypeEnum.INSERT)
    public R<Void> save(@RequestBody @Validated AiRolePromptDTO dto) {
        aiRolePromptService.saveNew(dto);
        return R.success();
    }

    /**
     * 修改 角色与提示词
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ai:role-prompt:update')")
    @Log(title = "修改角色与提示词", businessType = BusinessTypeEnum.UPDATE)
    public R<Void> updateById(@PathVariable Long id, @RequestBody @Validated AiRolePromptDTO dto) {
        aiRolePromptService.updateByIdNew(id, dto);
        return R.success();
    }

    /**
     * 删除 角色与提示词
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ai:role-prompt:delete')")
    @Log(title = "删除角色与提示词", businessType = BusinessTypeEnum.DELETE)
    public R<Void> removeById(@PathVariable Long id) {
        aiRolePromptService.removeByIdNew(id);
        return R.success();
    }

    /**
     * 切换启用状态
     */
    @PostMapping("/{id}/toggle")
    @PreAuthorize("hasAuthority('ai:role-prompt:toggle')")
    @Log(title = "切换角色与提示词启用状态", businessType = BusinessTypeEnum.UPDATE)
    public R<Void> toggleEnabled(@PathVariable Long id) {
        aiRolePromptService.toggleEnabled(id);
        return R.success();
    }

}
