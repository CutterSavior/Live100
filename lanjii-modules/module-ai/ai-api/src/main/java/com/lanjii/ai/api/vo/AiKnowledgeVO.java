package com.lanjii.ai.api.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * AI知识库VO
 *
 * @author lanjii
 */
@Data
public class AiKnowledgeVO {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 知识库标题
     */
    private String title;

    /**
     * 知识库内容
     */
    private String content;

    /**
     * 元数据 JSON 字符串
     */
    private String metadataJson;

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
