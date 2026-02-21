package com.lanjii.framework.ai.model;

import lombok.Builder;
import lombok.Data;

/**
 * 聊天模型配置
 *
 * @author lanjii
 */
@Data
@Builder
public class ChatModelConfig {

    /**
     * API 提供商标识
     */
    private String apiProvider;

    /**
     * 模型标识
     */
    private String modelId;

    /**
     * API Key
     */
    private String apiKey;

    /**
     * API 端点
     */
    private String apiEndpoint;

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
     * 频率惩罚
     */
    private Double frequencyPenalty;

    /**
     * 存在惩罚
     */
    private Double presencePenalty;

    /**
     * 超时时间（秒）
     */
    private Integer timeoutSeconds;
}
