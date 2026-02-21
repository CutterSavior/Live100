package com.lanjii.ai.api.dto;

import com.lanjii.common.annotation.QueryCondition;
import com.lanjii.common.enums.QueryType;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * AI文件知识库DTO
 *
 * @author lanjii
 */
@Data
public class AiFileKnowledgeDTO {

    /**
     * 文件
     */
    private MultipartFile file;

    /**
     * 文件名称
     */
    @QueryCondition(type = QueryType.LIKE)
    private String fileName;

    /**
     * 元数据 JSON 字符串
     */
    private String metadataJson;
}
