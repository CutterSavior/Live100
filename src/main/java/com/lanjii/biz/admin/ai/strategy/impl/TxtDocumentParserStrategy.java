package com.lanjii.biz.admin.ai.strategy.impl;

import com.lanjii.biz.admin.ai.strategy.DocumentParserStrategy;
import com.lanjii.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.reader.TextReader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * TXT文本文档解析策略
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
        try {
            TextReader textReader = new TextReader(resource);

            StringBuilder content = new StringBuilder();
            textReader.get().forEach(document -> {
                content.append(document.getText()).append("\n");
            });
            
            return content.toString().trim();
        } catch (Exception e) {
            log.error("解析TXT文件失败", e);
            throw new BizException("解析TXT文件失败: " + e.getMessage());
        }
    }
}
