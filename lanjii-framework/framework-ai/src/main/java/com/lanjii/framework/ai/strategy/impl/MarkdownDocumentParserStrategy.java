package com.lanjii.framework.ai.strategy.impl;

import com.lanjii.framework.ai.strategy.DocumentParserStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.reader.markdown.MarkdownDocumentReader;
import org.springframework.ai.reader.markdown.config.MarkdownDocumentReaderConfig;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * Markdown 文档解析策略
 *
 * @author lanjii
 */
@Slf4j
@Component
public class MarkdownDocumentParserStrategy implements DocumentParserStrategy {

    @Override
    public boolean support(String fileType) {
        return "md".equalsIgnoreCase(fileType) ||
                "markdown".equalsIgnoreCase(fileType);
    }

    @Override
    public String parse(Resource resource) {
        MarkdownDocumentReader markdownReader = new MarkdownDocumentReader(resource, MarkdownDocumentReaderConfig.defaultConfig());

        StringBuilder content = new StringBuilder();
        markdownReader.get().forEach(document -> {
            content.append(document.getText()).append("\n");
        });

        return content.toString().trim();
    }
}
