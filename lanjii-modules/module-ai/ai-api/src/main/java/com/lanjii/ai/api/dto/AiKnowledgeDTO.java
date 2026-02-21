package com.lanjii.ai.api.dto;

import com.lanjii.common.annotation.QueryCondition;
import com.lanjii.common.enums.QueryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

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
    @QueryCondition(type = QueryType.LIKE)
    private String title;

    /**
     * 知识库内容
     */
    @NotBlank(message = "内容不能为空")
    private String content;

    /**
     * 元数据 JSON 字符串
     */
    private String metadataJson;
}
