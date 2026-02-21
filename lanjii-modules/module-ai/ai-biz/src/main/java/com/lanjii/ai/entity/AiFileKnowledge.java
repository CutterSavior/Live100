package com.lanjii.ai.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lanjii.ai.api.dto.AiFileKnowledgeDTO;
import com.lanjii.ai.api.vo.AiFileKnowledgeVO;
import com.lanjii.framework.mp.base.TenantBaseEntity;
import com.lanjii.framework.mp.base.BaseEntityMapper;
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
public class AiFileKnowledge extends TenantBaseEntity {

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
     * 元数据JSON
     */
    private String metadataJson;

    @Mapper
    public interface AiFileKnowledgeMapper extends BaseEntityMapper<AiFileKnowledge, AiFileKnowledgeVO, AiFileKnowledgeDTO> {
        AiFileKnowledgeMapper INSTANCE = Mappers.getMapper(AiFileKnowledgeMapper.class);

        @org.mapstruct.Mapping(target = "fileSizeLabel", expression = "java(com.lanjii.framework.web.util.FileUtils.formatFileSize(entity.getFileSize()))")
        AiFileKnowledgeVO toVo(AiFileKnowledge entity);
    }
}
