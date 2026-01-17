package com.lanjii.biz.admin.ai.strategy.impl;

import com.lanjii.biz.admin.ai.strategy.DocumentParserStrategy;
import com.lanjii.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.reader.JsonReader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * JSON文档解析策略
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
        try {
            JsonReader jsonReader = new JsonReader(resource);
            
            // 读取所有内容并拼接
            StringBuilder content = new StringBuilder();
            jsonReader.get().forEach(document -> {
                content.append(document.getText()).append("\n");
            });
            
            return content.toString().trim();
        } catch (Exception e) {
            log.error("解析JSON文件失败", e);
            throw new BizException("解析JSON文件失败: " + e.getMessage());
        }
    }
}
