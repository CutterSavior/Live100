package com.lanjii.biz.admin.ai.strategy.impl;

import com.lanjii.biz.admin.ai.strategy.DocumentParserStrategy;
import com.lanjii.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * PDF文档解析策略
 *
 * @author lanjii
 */
@Slf4j
@Component
public class PdfDocumentParserStrategy implements DocumentParserStrategy {

    @Override
    public boolean support(String fileType) {
        return "pdf".equalsIgnoreCase(fileType);
    }

    @Override
    public String parse(Resource resource) {
        try {
            PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(resource);

            StringBuilder content = new StringBuilder();
            pdfReader.get().forEach(document -> {
                content.append(document.getText()).append("\n");
            });
            
            return content.toString().trim();
        } catch (Exception e) {
            log.error("解析PDF文件失败", e);
            throw new BizException("解析PDF文件失败: " + e.getMessage());
        }
    }
}
