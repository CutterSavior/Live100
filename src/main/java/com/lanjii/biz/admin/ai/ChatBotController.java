package com.lanjii.biz.admin.ai;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatBotController {

    @Autowired
    private ChatClient chatClient;
    @Autowired
    private VectorStore vectorStore;

    @PostConstruct
    public void loadStore() {
        List<Document> documents = List.of(
                new Document("lanjii是开箱即用的 RBAC 权限管理系统。后端基于 Spring Boot3 构建， 集成了 JWT 认证、Spring Security 6、MyBatis-Plus\u200B 和 WebSocket\u200B 等核心技术。前端使用了 Vue3 +Vite + Element Plus + Pinia 构建。它是一个简洁、高效、无过多依赖的项目，支持按钮级别的权限控制，使用简单，开箱即用。")
        );
        vectorStore.add(documents);
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chatStream(String message, String conversationId) {


        return chatClient.prompt()
                .advisors(
                        a -> a.param(ChatMemory.CONVERSATION_ID, conversationId)
                )
                .advisors(QuestionAnswerAdvisor.builder(vectorStore).build())
                .user(message)
                .stream()
                .content();
    }

}
