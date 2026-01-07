package com.lanjii.biz.admin.ai.service.impl;

import com.lanjii.biz.admin.ai.model.entity.AiModelConfig;
import com.lanjii.biz.admin.ai.model.entity.AiRolePrompt;
import com.lanjii.biz.admin.ai.service.AiModelConfigService;
import com.lanjii.biz.admin.ai.service.AiRolePromptService;
import com.lanjii.biz.admin.ai.service.ChatService;
import com.lanjii.biz.admin.ai.service.DynamicChatClientFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * 聊天业务实现
 *
 * @author lanjii
 */
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final DynamicChatClientFactory dynamicChatClientFactory;
    private final AiModelConfigService aiModelConfigService;
    private final AiRolePromptService aiRolePromptService;
    private final VectorStore vectorStore;

    @Override
    public Flux<String> chatStream(String message, String conversationId) {

        ChatClient chatClient = dynamicChatClientFactory.buildDefaultClient();

        AiModelConfig config = aiModelConfigService.getDefaultConfig();
        AiRolePrompt rolePrompt = null;
        boolean enableRag = true;
        if (config != null && config.getRoleId() != null) {
            rolePrompt = aiRolePromptService.getById(config.getRoleId());
            if (rolePrompt != null && rolePrompt.getIsRagEnabled() != null) {
                enableRag = rolePrompt.getIsRagEnabled() == 1;
            }
        }

        var prompt = chatClient.prompt()
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId));

        String finalUserInput = message;

        if (enableRag && rolePrompt != null) {
            // 构建检索请求
            int topK = rolePrompt.getTopK() != null && rolePrompt.getTopK() > 0 ? rolePrompt.getTopK() : 5;
            Double threshold = rolePrompt.getSimilarityThreshold();

            SearchRequest.Builder builder = SearchRequest.builder()
                    .query(message)
                    .topK(topK);
            if (threshold != null) {
                builder = builder.similarityThreshold(threshold);
            }

            // 元数据过滤
            if (StringUtils.isNotBlank(rolePrompt.getCustomFilter())) {
                builder = builder.filterExpression(rolePrompt.getCustomFilter());
            }

            SearchRequest searchRequest = builder.build();

            var docs = vectorStore.similaritySearch(searchRequest);

            StringBuilder contextBuilder = new StringBuilder();
            for (Document doc : docs) {
                if (contextBuilder.length() > 0) {
                    contextBuilder.append("\n\n---\n\n");
                }
                String content = doc.getText();
                if (content == null) {
                    content = doc.toString();
                }
                contextBuilder.append(content);
            }

            String context = contextBuilder.toString();

            String template = rolePrompt.getRagTemplate();
            if (StringUtils.isNotBlank(template)) {
                finalUserInput = template
                        .replace("{context}", context)
                        .replace("{query}", message);
            }
        }

        return prompt.user(finalUserInput)
                .stream()
                .content();
    }
}
