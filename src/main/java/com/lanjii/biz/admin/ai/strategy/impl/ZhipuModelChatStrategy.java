package com.lanjii.biz.admin.ai.strategy.impl;

import com.lanjii.biz.admin.ai.model.entity.AiModelConfig;
import com.lanjii.biz.admin.ai.strategy.ModelChatStrategy;
import com.lanjii.common.exception.BizException;
import com.lanjii.core.resp.ResultCode;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
import org.springframework.ai.zhipuai.ZhiPuAiChatOptions;
import org.springframework.ai.zhipuai.api.ZhiPuAiApi;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Set;

/**
 * 智谱 Zhipu 提供商的 ChatModel 构建策略。
 *  *
 *  * @author lanjii
 */
@Component
public class ZhipuModelChatStrategy implements ModelChatStrategy {

    private static final Set<String> SUPPORTED_PROVIDERS = Set.of(
            "zhipu"
    );

    @Override
    public boolean supports(String provider) {
        return provider != null && SUPPORTED_PROVIDERS.contains(provider.toLowerCase());
    }

    @Override
    public ChatModel buildChatModel(AiModelConfig config) {
        ZhiPuAiApi zhiPuAiApi = buildZhiPuAiApi(config);
        ZhiPuAiChatOptions defaultOptions = buildOptions(config);

        return new ZhiPuAiChatModel(zhiPuAiApi, defaultOptions);
    }

    private ZhiPuAiApi buildZhiPuAiApi(AiModelConfig config) {
        String apiKey = config.getApiKey();
        if (!StringUtils.hasText(apiKey)) {
            throw new BizException(ResultCode.BAD_REQUEST, "Zhipu API Key 未配置");
        }

        String baseUrl = StringUtils.hasText(config.getApiEndpoint())
                ? config.getApiEndpoint()
                : null;

        return ZhiPuAiApi.builder()
                .baseUrl(baseUrl)
                .apiKey(apiKey)
                .build();
    }

    private ZhiPuAiChatOptions buildOptions(AiModelConfig config) {
        ZhiPuAiChatOptions.Builder optionsBuilder = ZhiPuAiChatOptions.builder()
                .model(config.getModelId());

        if (config.getTemperature() != null) {
            optionsBuilder.temperature(config.getTemperature());
        }
        if (config.getTopP() != null) {
            optionsBuilder.topP(config.getTopP());
        }
        if (config.getMaxTokens() != null) {
            optionsBuilder.maxTokens(config.getMaxTokens());
        }
        // 当前 Spring AI ZhiPuAiChatOptions 未暴露 frequencyPenalty / presencePenalty 专用方法，
        // 若后续版本支持，可在此补充映射。

        return optionsBuilder.build();
    }
}
