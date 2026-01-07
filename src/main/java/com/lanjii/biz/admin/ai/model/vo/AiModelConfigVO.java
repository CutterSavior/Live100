package com.lanjii.biz.admin.ai.model.vo;

import com.lanjii.core.base.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * AI 模型配置 VO
 *
 * @author lanjii
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AiModelConfigVO extends BaseVO {

    private Long id;

    private String configName;

    private String apiProvider;

    private String modelId;

    private Integer isEnabled;

    private Integer isDefault;

    private Long roleId;

    private String apiEndpoint;

    private String description;

    private Double temperature;

    private Double topP;

    private String apiKey;

    private Integer maxTokens;

    private Integer timeoutSeconds;

    private Double frequencyPenalty;

    private Double presencePenalty;

    private String stopSequences;

    private Integer retryCount;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String createBy;

    private String updateBy;
}
