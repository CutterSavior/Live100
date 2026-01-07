package com.lanjii.biz.admin.ai.strategy.impl;

import com.lanjii.biz.admin.ai.model.entity.AiModelConfig;
import com.lanjii.biz.admin.ai.strategy.ModelChatStrategy;
import com.lanjii.common.exception.BizException;
import com.lanjii.core.resp.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Set;

/**
 * OpenAI 的 ChatModel 构建策略。
 *
 * @author lanjii
 */
@Slf4j
@Component
public class OpenAiModelChatStrategy implements ModelChatStrategy {

    private static final Set<String> SUPPORTED_PROVIDERS = Set.of(
            "openai"      // 直连 OpenAI 或 OpenAI 官方代理
    );

    @Override
    public boolean supports(String provider) {
        return provider != null && SUPPORTED_PROVIDERS.contains(provider.toLowerCase());
    }

    @Override
    public ChatModel buildChatModel(AiModelConfig config) {
        OpenAiApi openAiApi = buildOpenAiApi(config);
        OpenAiChatOptions defaultOptions = buildOptions(config);

        return OpenAiChatModel.builder()
                .openAiApi(openAiApi)
                .defaultOptions(defaultOptions)
                .build();
    }

    private OpenAiApi buildOpenAiApi(AiModelConfig config) {
        String apiKey = config.getApiKey();
        if (!StringUtils.hasText(apiKey)) {
            throw new BizException(ResultCode.BAD_REQUEST, "OpenAI API Key 未配置");
        }

        String baseUrl = StringUtils.hasText(config.getApiEndpoint())
                ? config.getApiEndpoint()
                : "https://api.openai.com/v1";

        return OpenAiApi.builder()
                .baseUrl(baseUrl)
                .apiKey(apiKey)
                .build();
    }

    private OpenAiChatOptions buildOptions(AiModelConfig config) {
        OpenAiChatOptions.Builder optionsBuilder = OpenAiChatOptions.builder()
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
