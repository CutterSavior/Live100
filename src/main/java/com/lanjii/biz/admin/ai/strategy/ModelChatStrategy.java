package com.lanjii.biz.admin.ai.strategy;

import com.lanjii.biz.admin.ai.model.entity.AiModelConfig;
import org.springframework.ai.chat.model.ChatModel;

/**
 * 模型聊天策略接口
 *
 * @author lanjii
 */
public interface ModelChatStrategy {

    /**
     * 是否支持当前 provider 标识（如 openai、deepseek、zhipu 等）。
     */
    boolean supports(String provider);

    /**
     * 使用指定的模型配置构建底层 ChatModel。
     */
    ChatModel buildChatModel(AiModelConfig config);
}
