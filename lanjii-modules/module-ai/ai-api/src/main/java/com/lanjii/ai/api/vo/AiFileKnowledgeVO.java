package com.lanjii.ai.api.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * AI文件知识库VO
 *
 * @author lanjii
 */
@Data
public class AiFileKnowledgeVO {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件存储路径
     */
    private String filePath;

    /**
     * 文件大小（字节）
     */
    private Long fileSize;

    /**
     * 文件大小标签（格式化后）
     */
    private String fileSizeLabel;

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
