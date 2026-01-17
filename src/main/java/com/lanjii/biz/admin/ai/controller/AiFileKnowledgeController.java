package com.lanjii.biz.admin.ai.controller;

import com.github.pagehelper.PageHelper;
import com.lanjii.biz.admin.ai.model.dto.AiFileKnowledgeDTO;
import com.lanjii.biz.admin.ai.model.entity.AiFileKnowledge;
import com.lanjii.biz.admin.ai.model.vo.AiFileKnowledgeVO;
import com.lanjii.biz.admin.ai.service.AiFileKnowledgeService;
import com.lanjii.common.enums.BusinessTypeEnum;
import com.lanjii.common.util.PageDataUtils;
import com.lanjii.core.annotation.Log;
import com.lanjii.core.base.PageParam;
import com.lanjii.core.base.support.PageData;
import com.lanjii.core.resp.R;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AI文件知识库管理
 *
 * @author lanjii
 */
@RestController
@RequestMapping("/admin/ai/file-knowledge")
@RequiredArgsConstructor
public class AiFileKnowledgeController {

    private final AiFileKnowledgeService aiFileKnowledgeService;

    /**
     * 分页查询文件知识库列表
     */
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('ai:file-knowledge:page')")
    public R<PageData<AiFileKnowledgeVO>> page(PageParam pageParam, AiFileKnowledgeDTO filter) {
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<AiFileKnowledge> list = aiFileKnowledgeService.listByFilter(filter);
        return R.success(PageDataUtils.make(list, AiFileKnowledge.INSTANCE));
    }

    /**
     * 根据ID获取文件知识库详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ai:file-knowledge:view')")
    public R<AiFileKnowledgeVO> getById(@PathVariable Long id) {
        AiFileKnowledgeVO vo = aiFileKnowledgeService.getByIdNew(id);
        return R.success(vo);
    }

    /**
     * 新增文件知识库
     */
    @PostMapping
    @PreAuthorize("hasAuthority('ai:file-knowledge:save')")
    @Log(title = "新增文件知识库", businessType = BusinessTypeEnum.INSERT)
    public R<Void> save(@ModelAttribute AiFileKnowledgeDTO dto) {
        aiFileKnowledgeService.saveNew(dto);
        return R.success();
    }

    /**
     * 修改文件知识库
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ai:file-knowledge:update')")
    @Log(title = "修改文件知识库", businessType = BusinessTypeEnum.UPDATE)
    public R<Void> updateById(@PathVariable Long id, @ModelAttribute AiFileKnowledgeDTO dto) {
        aiFileKnowledgeService.updateByIdNew(id, dto);
        return R.success();
    }

    /**
     * 删除文件知识库
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ai:file-knowledge:delete')")
    @Log(title = "删除文件知识库", businessType = BusinessTypeEnum.DELETE)
    public R<Void> removeById(@PathVariable Long id) {
        aiFileKnowledgeService.removeByIdNew(id);
        return R.success();
    }

    /**
     * 重建所有文件知识库向量数据
     */
    @PostMapping("/rebuild-vectors")
    @PreAuthorize("hasAuthority('ai:file-knowledge:rebuild')")
    @Log(title = "重建所有文件知识库向量", businessType = BusinessTypeEnum.OTHER)
    public R<Void> rebuildVectors() {
        aiFileKnowledgeService.rebuildAllVectors();
        return R.success();
    }

    /**
     * 重建单条文件知识库向量数据
     */
    @PostMapping("/{id}/rebuild-vector")
    @PreAuthorize("hasAuthority('ai:file-knowledge:rebuild')")
    @Log(title = "重建文件知识库向量", businessType = BusinessTypeEnum.OTHER)
    public R<Void> rebuildVectorById(@PathVariable Long id) {
        aiFileKnowledgeService.rebuildVectorById(id);
        return R.success();
    }
}
