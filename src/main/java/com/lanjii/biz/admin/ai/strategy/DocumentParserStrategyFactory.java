package com.lanjii.biz.admin.ai.strategy;

import com.lanjii.common.exception.BizException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 文档解析策略工厂
 *
 * @author lanjii
 */
@Component
@RequiredArgsConstructor
public class DocumentParserStrategyFactory {

    private final List<DocumentParserStrategy> strategies;

    /**
     * 根据文件类型获取对应的解析策略
     *
     * @param fileType 文件类型
     * @return 文档解析策略
     */
    public DocumentParserStrategy getStrategy(String fileType) {
        return strategies.stream()
                .filter(strategy -> strategy.support(fileType))
                .findFirst()
                .orElseThrow(() -> new BizException("不支持的文件类型: " + fileType));
    }

    /**
     * 检查是否支持该文件类型
     *
     * @param fileType 文件类型
     * @return true-支持 false-不支持
     */
    public boolean isSupportedFileType(String fileType) {
        return strategies.stream()
                .anyMatch(strategy -> strategy.support(fileType));
    }
}
