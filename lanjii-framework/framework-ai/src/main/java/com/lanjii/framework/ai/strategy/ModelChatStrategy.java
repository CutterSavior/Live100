package com.lanjii.framework.ai.strategy;

import com.lanjii.framework.ai.model.ChatModelConfig;
import org.springframework.ai.chat.model.ChatModel;

/**
 * 模型聊天策略接口 - 不同 AI 提供商的 ChatModel 构建策略
 *
 * @author lanjii
 */
public interface ModelChatStrategy {

    /**
     * 是否支持当前 provider 标识
     *
     * @param provider 提供商标识
     * @return true-支持 false-不支持
     */
    boolean supports(String provider);

    /**
     * 使用指定的模型配置构建底层 ChatModel
     *
     * @param config 模型配置
     * @return ChatModel 实例
     */
    ChatModel buildChatModel(ChatModelConfig config);
}
