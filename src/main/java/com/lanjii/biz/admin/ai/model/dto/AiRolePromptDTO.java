package com.lanjii.biz.admin.ai.model.dto;

import com.lanjii.core.annotation.QueryCondition;
import com.lanjii.core.enums.QueryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * AI 角色与提示词配置 DTO
 *
 * @author lanjii
 */
@Data
public class AiRolePromptDTO {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    @QueryCondition(type = QueryType.LIKE)
    private String roleName;


    /**
     * 角色描述
     */
    private String description;

    /**
     * 是否启用（1-启用 0-禁用）
     */
    @NotNull(message = "是否启用不能为空")
    @QueryCondition
    private Integer isEnabled;

    /**
     * 系统提示词
     */
    @NotBlank(message = "系统提示词不能为空")
    private String systemPrompt;

    /**
     * 是否启用 RAG（1-启用 0-禁用）
     */
    @QueryCondition
    private Integer isRagEnabled;

    /**
     * RAG 提示词模板
     */
    private String ragTemplate;

    /**
     * 检索文档数量 topK
     */
    private Integer topK;

    /**
     * 相似度阈值（0-1）
     */
    private Double similarityThreshold;

    /**
     * 自定义过滤表达式
     */
    private String customFilter;
}
