package com.lanjii.runner;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 向量数据加载
 *
 * @author lanjii
 */
@Component
@RequiredArgsConstructor
public class VectorStoreRunner implements CommandLineRunner {

    private final VectorStore vectorStore;

    @Override
    public void run(String... args) throws Exception {
        List<Document> documents = List.of(
                new Document("lanjii（岚迹）是开箱即用的 RBAC 权限管理系统。后端基于 Spring Boot3 构建， 集成了 JWT 认证、Spring Security 6、MyBatis-Plus\u200B 和 WebSocket\u200B 等核心技术。前端使用了 Vue3 +Vite + Element Plus + Pinia 构建。它是一个简洁、高效、无过多依赖的项目，支持按钮级别的权限控制，使用简单，开箱即用。")
        );
        vectorStore.add(documents);
    }
}
