package com.lanjii.biz.admin.ai.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import javax.validation.constraints.Size;

/**
 * AI知识库DTO
 *
 * @author lanjii
 */
@Data
public class AiKnowledgeDTO {

    /**
     * 知识库标题
     */
    @NotBlank(message = "标题不能为空")
    @Size(max = 200, message = "标题长度不能超过200个字符")
    private String title;

    /**
     * 知识库内容
     */
    @NotBlank(message = "内容不能为空")
    private String content;

    /**
     * 元数据 JSON 字符串（可选）
     * 由前端根据元数据字段配置动态构造
     */
    private String metadataJson;
}
