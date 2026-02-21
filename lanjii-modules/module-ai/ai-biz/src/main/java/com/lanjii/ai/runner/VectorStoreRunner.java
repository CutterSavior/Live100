package com.lanjii.ai.runner;

import com.lanjii.ai.service.AiFileKnowledgeService;
import com.lanjii.ai.service.AiKnowledgeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
    private final AiFileKnowledgeService aiFileKnowledgeService;

    /**
     * 是否在应用启动时自动重建向量
     * 默认为 false，需要时可在 application.yml 中配置为 true
     */
    @Value("${lanjii.ai.vector.rebuild-on-startup:false}")
    private boolean rebuildOnStartup;

    @Override
    public void run(String... args) throws Exception {
        if (rebuildOnStartup) {
            log.warn("检测到配置项 lanjii.ai.vector.rebuild-on-startup=true，开始重建所有向量...");
            aiKnowledgeService.rebuildAllVectors();
            aiFileKnowledgeService.rebuildAllVectors();
            log.info("向量重建完成");
        } else {
            log.info("自动重建向量未开启");
        }
    }
}
