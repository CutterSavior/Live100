package com.lanjii.ai.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lanjii.ai.api.dto.AiRolePromptDTO;
import com.lanjii.ai.api.vo.AiRolePromptVO;
import com.lanjii.framework.mp.base.TenantBaseEntity;
import com.lanjii.framework.mp.base.BaseEntityMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * AI 角色与提示词配置表
 *
 * @author lanjii
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ai_role_prompt")
public class AiRolePrompt extends TenantBaseEntity {

    public static final AiRolePromptMapper INSTANCE = Mappers.getMapper(AiRolePromptMapper.class);
    /**
     * 主键ID
     */
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
     * 系统提示词（System Prompt）
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
     * 相似度阈值（0-1）
     */
    private Double similarityThreshold;
    /**
     * 自定义过滤表达式
     */
    private String customFilter;

    @Mapper
    public interface AiRolePromptMapper extends BaseEntityMapper<AiRolePrompt, AiRolePromptVO, AiRolePromptDTO> {
        AiRolePromptMapper INSTANCE = Mappers.getMapper(AiRolePromptMapper.class);
    }
}
