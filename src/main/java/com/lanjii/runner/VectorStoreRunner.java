package com.lanjii.runner;

import com.lanjii.biz.admin.ai.model.entity.AiKnowledge;
import com.lanjii.biz.admin.ai.service.AiKnowledgeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 向量数据加载
 *
 * @author lanjii
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class VectorStoreRunner implements CommandLineRunner {

    private final VectorStore vectorStore;
    private final AiKnowledgeService aiKnowledgeService;

    @Override
    public void run(String... args) throws Exception {

        List<AiKnowledge> list = aiKnowledgeService.list();
        List<Document> documents = list.stream().map(aiKnowledge -> {
            return new Document(String.valueOf(aiKnowledge.getId()), aiKnowledge.toString(), new HashMap<>());
        }).collect(Collectors.toList());
        vectorStore.add(documents);
    }
}
