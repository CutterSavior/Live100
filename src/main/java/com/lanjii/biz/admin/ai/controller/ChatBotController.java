package com.lanjii.biz.admin.ai.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * AI 智能问答
 *
 * @author lanjii
 */
@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatBotController {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    /**
     * 流式问答
     */
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
