package com.lanjii.framework.ai.strategy.impl;

import com.lanjii.common.exception.BizException;
import com.lanjii.framework.ai.strategy.DocumentParserStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * PDF 文档解析策略
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
        PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(resource);

        StringBuilder content = new StringBuilder();
        pdfReader.get().forEach(document -> {
            content.append(document.getText()).append("\n");
        });

        return content.toString().trim();
    }
}
