package com.lanjii.biz.admin.ai.model.vo;

import com.lanjii.core.base.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * AI 角色与提示词配置 VO
 *
 * @author lanjii
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AiRolePromptVO extends BaseVO {

    private Long id;

    /**
     * 角色名称
     */
    private String roleName;


    /**
     * 角色描述
     */
    private String description;

    /**
     * 是否启用（1-启用 0-禁用）
     */
    private Integer isEnabled;

    /**
     * 系统提示词
     */
    private String systemPrompt;

    /**
     * 是否启用 RAG（1-启用 0-禁用）
     */
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
     * 相似度阈值
     */
    private Double similarityThreshold;

    /**
     * 自定义过滤表达式
     */
    private String customFilter;


    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新人
     */
    private String updateBy;
}
