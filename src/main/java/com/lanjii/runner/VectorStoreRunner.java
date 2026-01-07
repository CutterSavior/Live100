package com.lanjii.runner;

import com.lanjii.biz.admin.ai.service.AiKnowledgeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 向量数据加载
 *
 * @author lanjii
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class VectorStoreRunner implements CommandLineRunner {

    private final AiKnowledgeService aiKnowledgeService;

    @Override
    public void run(String... args) throws Exception {
        // 启动时根据当前知识库内容与元数据重建一次向量数据
        aiKnowledgeService.rebuildAllVectors();
    }
}
