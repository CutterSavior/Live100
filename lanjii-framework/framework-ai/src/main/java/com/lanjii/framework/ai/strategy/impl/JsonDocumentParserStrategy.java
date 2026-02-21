package com.lanjii.framework.ai.strategy.impl;

import com.lanjii.framework.ai.strategy.DocumentParserStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.reader.JsonReader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * JSON 文档解析策略
 *
 * @author lanjii
 */
@Slf4j
@Component
public class JsonDocumentParserStrategy implements DocumentParserStrategy {

    @Override
    public boolean support(String fileType) {
        return "json".equalsIgnoreCase(fileType);
    }

    @Override
    public String parse(Resource resource) {
        JsonReader jsonReader = new JsonReader(resource);

        StringBuilder content = new StringBuilder();
        jsonReader.get().forEach(document -> {
            content.append(document.getText()).append("\n");
        });

        return content.toString().trim();
    }
}
