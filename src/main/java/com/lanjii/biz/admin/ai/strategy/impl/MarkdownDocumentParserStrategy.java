package com.lanjii.biz.admin.ai.strategy.impl;

import com.lanjii.biz.admin.ai.strategy.DocumentParserStrategy;
import com.lanjii.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.reader.markdown.MarkdownDocumentReader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * Markdown文档解析策略
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
        try {
            // MarkdownDocumentReader 接受 String 路径
            MarkdownDocumentReader markdownReader = new MarkdownDocumentReader(resource.getFile().getAbsolutePath());
            
            // 读取所有内容并拼接
            StringBuilder content = new StringBuilder();
            markdownReader.get().forEach(document -> {
                content.append(document.getText()).append("\n");
            });
            
            return content.toString().trim();
        } catch (Exception e) {
            log.error("解析Markdown文件失败", e);
            throw new BizException("解析Markdown文件失败: " + e.getMessage());
        }
    }
}
