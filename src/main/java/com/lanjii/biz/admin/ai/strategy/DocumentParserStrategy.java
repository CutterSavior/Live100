package com.lanjii.biz.admin.ai.strategy;

import org.springframework.core.io.Resource;

/**
 * 文档解析策略接口
 *
 * @author lanjii
 */
public interface DocumentParserStrategy {

    /**
     * 是否支持该文件类型
     *
     * @param fileType 文件类型
     * @return true-支持 false-不支持
     */
    boolean support(String fileType);

    /**
     * 解析文档内容
     *
     * @param resource 文件资源
     * @return 解析后的文本内容
     */
    String parse(Resource resource);
}
