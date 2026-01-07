package com.lanjii.biz.admin.ai.service;

import reactor.core.publisher.Flux;

/**
 * 聊天业务 Service，封装模型选择、角色与提示词和 RAG 逻辑。
 *
 * @author lanjii
 */
public interface ChatService {

    /**
     * 基于当前默认模型配置和绑定的角色与提示词，执行一次流式聊天。
     *
     * @param message        用户原始问题
     * @param conversationId 会话ID
     * @return 模型的流式输出
     */
    Flux<String> chatStream(String message, String conversationId);
}
