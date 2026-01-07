package com.lanjii.biz.admin.ai.service;

import com.lanjii.biz.admin.ai.model.entity.AiModelConfig;
import com.lanjii.biz.admin.ai.model.entity.AiRolePrompt;
import com.lanjii.biz.admin.ai.strategy.ModelChatStrategy;
import com.lanjii.biz.admin.ai.strategy.ModelChatStrategyFactory;
import com.lanjii.common.exception.BizException;
import com.lanjii.core.resp.ResultCode;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.VectorStoreChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

/**
 * 根据数据库中的模型配置动态构建 {@link ChatClient}，用于实际聊天时选择默认模型。
 *
 * @author lanjii
 */
@Component
@RequiredArgsConstructor
public class DynamicChatClientFactory {

    private final AiModelConfigService aiModelConfigService;
    private final AiRolePromptService aiRolePromptService;
    private final ModelChatStrategyFactory modelChatStrategyFactory;
    private final ChatMemory chatMemory;
    private final VectorStore vectorStore;

    /**
     * 构建默认模型
     */
    public ChatClient buildDefaultClient() {
        AiModelConfig config = aiModelConfigService.getDefaultConfig();
        if (config == null) {
            throw new BizException(ResultCode.NOT_FOUND, "未找到默认启用的模型配置，请先在模型配置中设置默认模型");
        }
        ModelChatStrategy strategy = modelChatStrategyFactory.getStrategy(config.getApiProvider());
        ChatModel chatModel = strategy.buildChatModel(config);

        String systemPrompt = """
                你是lanjii(岚迹)的客服助手，在用户第一次问你的时候，你要表明自己的身份，并且要说明 lanjii 是什么。
                针对用户的问题总是能以友好愉悦的方式去进行交流，希望带给客户很好的体验。
                你总是会用中文去回答问题。
                """;

        if (config.getRoleId() != null) {
            AiRolePrompt rolePrompt = aiRolePromptService.getById(config.getRoleId());
            if (rolePrompt != null && rolePrompt.getSystemPrompt() != null && !rolePrompt.getSystemPrompt().isEmpty()) {
                systemPrompt = rolePrompt.getSystemPrompt();
            }
        }

        return ChatClient.builder(chatModel)
                .defaultSystem(systemPrompt)
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        VectorStoreChatMemoryAdvisor.builder(vectorStore).build()
                )
                .build();
    }

}
