package com.lanjii.biz.admin.ai.strategy.impl;

import com.lanjii.biz.admin.ai.model.entity.AiModelConfig;
import com.lanjii.biz.admin.ai.strategy.ModelChatStrategy;
import com.lanjii.common.exception.BizException;
import com.lanjii.core.resp.ResultCode;
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

    private static final Set<String> SUPPORTED_PROVIDERS = Set.of(
            "deepseek" // 也可以在前端把硅基流这类代理标识为 deepseek
    );

    @Override
    public boolean supports(String provider) {
        return provider != null && SUPPORTED_PROVIDERS.contains(provider.toLowerCase());
    }

    @Override
    public ChatModel buildChatModel(AiModelConfig config) {
        DeepSeekApi deepSeekApi = buildDeepSeekApi(config);
        DeepSeekChatOptions defaultOptions = buildOptions(config);

        return DeepSeekChatModel.builder()
                .deepSeekApi(deepSeekApi)
                .defaultOptions(defaultOptions)
                .build();
    }

    private DeepSeekApi buildDeepSeekApi(AiModelConfig config) {
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

    private DeepSeekChatOptions buildOptions(AiModelConfig config) {
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
