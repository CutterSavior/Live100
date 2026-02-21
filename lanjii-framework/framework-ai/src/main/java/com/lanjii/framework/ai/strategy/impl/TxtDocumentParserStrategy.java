package com.lanjii.framework.ai.strategy.impl;

import com.lanjii.common.exception.BizException;
import com.lanjii.framework.ai.strategy.DocumentParserStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.reader.TextReader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * TXT 文本文档解析策略
 *
 * @author lanjii
 */
@Slf4j
@Component
public class TxtDocumentParserStrategy implements DocumentParserStrategy {

    @Override
    public boolean support(String fileType) {
        return "txt".equalsIgnoreCase(fileType);
    }

    @Override
    public String parse(Resource resource) {
        TextReader textReader = new TextReader(resource);

        StringBuilder content = new StringBuilder();
        textReader.get().forEach(document -> {
            content.append(document.getText()).append("\n");
        });

        return content.toString().trim();
    }
}
