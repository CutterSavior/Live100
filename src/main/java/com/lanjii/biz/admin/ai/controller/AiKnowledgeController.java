package com.lanjii.biz.admin.ai.controller;

import com.github.pagehelper.PageHelper;
import com.lanjii.biz.admin.ai.model.dto.AiKnowledgeDTO;
import com.lanjii.biz.admin.ai.model.entity.AiKnowledge;
import com.lanjii.biz.admin.ai.model.vo.AiKnowledgeVO;
import com.lanjii.biz.admin.ai.service.AiKnowledgeService;
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
 * AI知识库管理
 *
 * @author lanjii
 */
@RestController
@RequestMapping("/admin/ai/knowledge")
@RequiredArgsConstructor
public class AiKnowledgeController {

    private final AiKnowledgeService aiKnowledgeService;

    /**
     * 分页查询知识库列表
     */
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('ai:knowledge:page')")
    public R<PageData<AiKnowledgeVO>> page(PageParam pageParam, AiKnowledgeDTO filter) {
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<AiKnowledge> list = aiKnowledgeService.listByFilter(filter);
        return R.success(PageDataUtils.make(list, AiKnowledge.INSTANCE));
    }

    /**
     * 根据ID获取知识库详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ai:knowledge:view')")
    public R<AiKnowledgeVO> getById(@PathVariable Long id) {
        AiKnowledgeVO vo = aiKnowledgeService.getByIdNew(id);
        return R.success(vo);
    }

    /**
     * 新增知识库
     */
    @PostMapping
    @PreAuthorize("hasAuthority('ai:knowledge:save')")
    @Log(title = "新增知识库", businessType = BusinessTypeEnum.INSERT)
    public R<Void> save(@RequestBody @Validated AiKnowledgeDTO dto) {
        aiKnowledgeService.saveNew(dto);
        return R.success();
    }

    /**
     * 修改知识库
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ai:knowledge:update')")
    @Log(title = "修改知识库", businessType = BusinessTypeEnum.UPDATE)
    public R<Void> updateById(@PathVariable Long id, @RequestBody @Validated AiKnowledgeDTO dto) {
        aiKnowledgeService.updateByIdNew(id, dto);
        return R.success();
    }

    /**
     * 删除知识库
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ai:knowledge:delete')")
    @Log(title = "删除知识库", businessType = BusinessTypeEnum.DELETE)
    public R<Void> removeById(@PathVariable Long id) {
        aiKnowledgeService.removeByIdNew(id);
        return R.success();
    }

    /**
     * 重建所有知识库向量数据
     */
    @PostMapping("/rebuild-vectors")
    @PreAuthorize("hasAuthority('ai:knowledge:rebuild')")
    @Log(title = "重建所有知识库向量", businessType = BusinessTypeEnum.OTHER)
    public R<Void> rebuildVectors() {
        aiKnowledgeService.rebuildAllVectors();
        return R.success();
    }

    /**
     * 重建单条知识库向量数据
     */
    @PostMapping("/{id}/rebuild-vector")
    @PreAuthorize("hasAuthority('ai:knowledge:rebuild')")
    @Log(title = "重建知识库向量", businessType = BusinessTypeEnum.OTHER)
    public R<Void> rebuildVectorById(@PathVariable Long id) {
        aiKnowledgeService.rebuildVectorById(id);
        return R.success();
    }
}
