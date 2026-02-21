package com.lanjii.ai.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lanjii.ai.api.dto.AiKnowledgeDTO;
import com.lanjii.ai.api.vo.AiKnowledgeVO;
import com.lanjii.framework.mp.base.TenantBaseEntity;
import com.lanjii.framework.mp.base.BaseEntityMapper;
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
public class AiKnowledge extends TenantBaseEntity {

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
     * 元数据JSON
     */
    private String metadataJson;

    @Mapper
    public interface AiKnowledgeMapper extends BaseEntityMapper<AiKnowledge, AiKnowledgeVO, AiKnowledgeDTO> {
        AiKnowledgeMapper INSTANCE = Mappers.getMapper(AiKnowledgeMapper.class);
    }
}
