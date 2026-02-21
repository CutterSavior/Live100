package com.lanjii.ai.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lanjii.ai.api.dto.AiModelConfigDTO;
import com.lanjii.ai.api.vo.AiModelConfigVO;
import com.lanjii.framework.mp.base.TenantBaseEntity;
import com.lanjii.framework.mp.base.BaseEntityMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * AI 模型配置表
 *
 * @author lanjii
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ai_model_config")
public class AiModelConfig extends TenantBaseEntity {

    public static final AiModelConfigMapper INSTANCE = Mappers.getMapper(AiModelConfigMapper.class);
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 配置名称
     */
    private String configName;
    /**
     * API 提供商
     */
    private String apiProvider;
    /**
     * 模型标识
     */
    private String modelId;
    /**
     * 是否启用（1-启用 0-禁用）
     */
    private Integer isEnabled;
    /**
     * 是否默认（1-是 0-否）
     */
    private Integer isDefault;
    /**
     * 绑定角色与提示词ID（可为空）
     */
    private Long roleId;
    /**
     * API Key
     */
    private String apiKey;
    /**
     * API 端点
     */
    private String apiEndpoint;
    /**
     * 描述
     */
    private String description;
    /**
     * 温度
     */
    private Double temperature;
    /**
     * Top P
     */
    private Double topP;
    /**
     * 最大 Token 数
     */
    private Integer maxTokens;
    /**
     * 超时时间（秒）
     */
    private Integer timeoutSeconds;
    /**
     * 频率惩罚
     */
    private Double frequencyPenalty;
    /**
     * 存在惩罚
     */
    private Double presencePenalty;
    /**
     * 停止序列（按行分隔）
     */
    private String stopSequences;
    /**
     * 重试次数
     */
    private Integer retryCount;

    @Mapper
    public interface AiModelConfigMapper extends BaseEntityMapper<AiModelConfig, AiModelConfigVO, AiModelConfigDTO> {

        @Mapping(target = "apiKey", expression = "java(com.lanjii.common.util.MaskUtils.maskApiKey(entity.getApiKey()))")
        AiModelConfigVO toVo(AiModelConfig entity);

        AiModelConfigMapper INSTANCE = Mappers.getMapper(AiModelConfigMapper.class);
    }
}
