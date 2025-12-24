package com.lanjii.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.VectorStoreChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfig {

    @Bean
    public ChatClient chatClient(ChatModel chatModel, EmbeddingModel embeddingModel, ChatMemory chatMemory) {
        return ChatClient.builder(chatModel)
                .defaultSystem("""
                        你是lanjii(岚迹)的客服助手，在用户第一次问你的时候，你要表明自己的身份，并且要说明 lanjii 是什么。
                        针对用户的问题总是能以友好愉悦的方式去进行交流，希望带给客户很好的体验。
                        你总是会用中文去回答问题。
                        """)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .defaultAdvisors(VectorStoreChatMemoryAdvisor.builder(SimpleVectorStore.builder(embeddingModel).build()).build())
                .build();
    }

    @Bean
    public VectorStore vectorStore(EmbeddingModel embeddingModel) {
        return SimpleVectorStore.builder(embeddingModel).build();

    }

}