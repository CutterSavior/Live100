package com.lanjii.biz.admin.ai.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lanjii.biz.admin.ai.model.dto.AiFileKnowledgeDTO;
import com.lanjii.biz.admin.ai.model.vo.AiFileKnowledgeVO;
import com.lanjii.core.base.BaseEntity;
import com.lanjii.core.base.BaseEntityMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * AI文件知识库表(AiFileKnowledge)表实体类
 *
 * @author lanjii
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("ai_file_knowledge")
public class AiFileKnowledge extends BaseEntity<AiFileKnowledge> {

    public static final AiFileKnowledgeMapper INSTANCE = Mappers.getMapper(AiFileKnowledgeMapper.class);

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件类型（pdf/markdown/txt/html/doc/docx等）
     */
    private String fileType;

    /**
     * 文件存储路径
     */
    private String filePath;

    /**
     * 物理全路径
     */
    private String fullPath;

    /**
     * 文件大小（字节）
     */
    private Long fileSize;

    /**
     * 元数据 JSON
     */
    private String metadataJson;

    @Mapper
    public interface AiFileKnowledgeMapper extends BaseEntityMapper<AiFileKnowledge, AiFileKnowledgeVO, AiFileKnowledgeDTO> {
        AiFileKnowledgeMapper INSTANCE = Mappers.getMapper(AiFileKnowledgeMapper.class);

        @org.mapstruct.Mapping(target = "fileSizeLabel", expression = "java(com.lanjii.common.util.FileUtils.formatFileSize(entity.getFileSize()))")
        AiFileKnowledgeVO toVo(AiFileKnowledge entity);
    }
}
