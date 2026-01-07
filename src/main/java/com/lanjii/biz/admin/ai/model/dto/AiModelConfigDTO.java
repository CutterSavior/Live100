package com.lanjii.biz.admin.ai.model.dto;

import com.lanjii.core.annotation.QueryCondition;
import com.lanjii.core.enums.QueryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * AI 模型配置 DTO
 *
 * @author lanjii
 */
@Data
public class AiModelConfigDTO {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 配置名称
     */
    @NotBlank(message = "配置名称不能为空")
    @QueryCondition(type = QueryType.LIKE)
    private String configName;

    /**
     * API 提供商
     */
    @NotBlank(message = "API 提供商不能为空")
    @QueryCondition
    private String apiProvider;

    /**
     * 模型标识
     */
    @NotBlank(message = "模型标识不能为空")
    @QueryCondition(type = QueryType.LIKE)
    private String modelId;

    /**
     * 是否启用（1-启用 0-禁止）
     */
    @NotNull(message = "是否启用不能为空")
    @QueryCondition
    private Integer isEnabled;

    /**
     * 绑定角色与提示词ID
     */
    @QueryCondition
    private Long roleId;

    /**
     * API Key
     */
    @NotBlank(message = "API Key 不能为空")
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
     * 停止序列
     */
    private String stopSequences;

    /**
     * 重试次数
     */
    private Integer retryCount;
}
