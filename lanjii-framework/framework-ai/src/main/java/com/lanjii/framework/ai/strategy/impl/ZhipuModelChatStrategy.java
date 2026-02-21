package com.lanjii.framework.ai.strategy.impl;

import com.lanjii.common.exception.BizException;
import com.lanjii.common.response.ResultCode;
import com.lanjii.framework.ai.model.ChatModelConfig;
import com.lanjii.framework.ai.strategy.ModelChatStrategy;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
import org.springframework.ai.zhipuai.ZhiPuAiChatOptions;
import org.springframework.ai.zhipuai.api.ZhiPuAiApi;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Set;

/**
 * 智谱 Zhipu 提供商的 ChatModel 构建策略
 *
 * @author lanjii
 */
@Component
public class ZhipuModelChatStrategy implements ModelChatStrategy {

    private static final Set<String> SUPPORTED_PROVIDERS = Set.of(

    );

    @Override
    public boolean supports(String provider) {
        return "zhipu".equalsIgnoreCase(provider);
    }

    @Override
    public ChatModel buildChatModel(ChatModelConfig config) {
        ZhiPuAiApi zhiPuAiApi = buildZhiPuAiApi(config);
        ZhiPuAiChatOptions defaultOptions = buildOptions(config);

        return new ZhiPuAiChatModel(zhiPuAiApi, defaultOptions);
    }

    private ZhiPuAiApi buildZhiPuAiApi(ChatModelConfig config) {
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

    private ZhiPuAiChatOptions buildOptions(ChatModelConfig config) {
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
        // 当前 Spring AI ZhiPuAiChatOptions 未暴露 frequencyPenalty / presencePenalty 专用方法

        return optionsBuilder.build();
    }
}
