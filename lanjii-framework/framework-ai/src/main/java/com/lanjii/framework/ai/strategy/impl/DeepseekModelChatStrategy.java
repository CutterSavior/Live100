package com.lanjii.framework.ai.strategy.impl;

import com.lanjii.common.exception.BizException;
import com.lanjii.common.response.ResultCode;
import com.lanjii.framework.ai.model.ChatModelConfig;
import com.lanjii.framework.ai.strategy.ModelChatStrategy;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.ai.deepseek.DeepSeekChatOptions;
import org.springframework.ai.deepseek.api.DeepSeekApi;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Set;

/**
 * DeepSeek 提供商的 ChatModel 构建策略
 *
 * @author lanjii
 */
@Component
public class DeepseekModelChatStrategy implements ModelChatStrategy {

    @Override
    public boolean supports(String provider) {
        return "deepseek".equalsIgnoreCase(provider);
    }

    @Override
    public ChatModel buildChatModel(ChatModelConfig config) {
        DeepSeekApi deepSeekApi = buildDeepSeekApi(config);
        DeepSeekChatOptions defaultOptions = buildOptions(config);

        return DeepSeekChatModel.builder()
                .deepSeekApi(deepSeekApi)
                .defaultOptions(defaultOptions)
                .build();
    }

    private DeepSeekApi buildDeepSeekApi(ChatModelConfig config) {
        String apiKey = config.getApiKey();
        if (!StringUtils.hasText(apiKey)) {
            throw new BizException(ResultCode.BAD_REQUEST, "DeepSeek API Key 未配置");
        }

        String baseUrl = StringUtils.hasText(config.getApiEndpoint())
                ? config.getApiEndpoint()
                : "https://api.deepseek.com";

        return DeepSeekApi.builder()
                .baseUrl(baseUrl)
                .apiKey(apiKey)
                .build();
    }

    private DeepSeekChatOptions buildOptions(ChatModelConfig config) {
        DeepSeekChatOptions.Builder optionsBuilder = DeepSeekChatOptions.builder()
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
        if (config.getFrequencyPenalty() != null) {
            optionsBuilder.frequencyPenalty(config.getFrequencyPenalty());
        }
        if (config.getPresencePenalty() != null) {
            optionsBuilder.presencePenalty(config.getPresencePenalty());
        }

        return optionsBuilder.build();
    }
}
