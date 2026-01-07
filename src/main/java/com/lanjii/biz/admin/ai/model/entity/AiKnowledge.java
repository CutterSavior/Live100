package com.lanjii.biz.admin.ai.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lanjii.biz.admin.ai.model.dto.AiKnowledgeDTO;
import com.lanjii.biz.admin.ai.model.vo.AiKnowledgeVO;
import com.lanjii.core.base.BaseEntity;
import com.lanjii.core.base.BaseEntityMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * AI知识库表(AiKnowledge)表实体类
 *
 * @author lanjii
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("ai_knowledge")
public class AiKnowledge extends BaseEntity<AiKnowledge> {

    public static final AiKnowledgeMapper INSTANCE = Mappers.getMapper(AiKnowledgeMapper.class);
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
     * 元数据 JSON
     */
    private String metadataJson;

    @Mapper
    public interface AiKnowledgeMapper extends BaseEntityMapper<AiKnowledge, AiKnowledgeVO, AiKnowledgeDTO> {
        AiKnowledgeMapper INSTANCE = Mappers.getMapper(AiKnowledgeMapper.class);
    }
}
