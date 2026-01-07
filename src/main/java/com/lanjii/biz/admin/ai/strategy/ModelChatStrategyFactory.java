package com.lanjii.biz.admin.ai.strategy;

import com.lanjii.common.exception.BizException;
import com.lanjii.core.resp.ResultCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 模型聊天策略工厂，根据 provider 选择对应实现，
 *
 * @author lanjii
 */
@Component
@RequiredArgsConstructor
public class ModelChatStrategyFactory {

    private final List<ModelChatStrategy> strategies;

    public ModelChatStrategy getStrategy(String provider) {
        return strategies.stream()
                .filter(s -> s.supports(provider))
                .findFirst()
                .orElseThrow(() -> BizException.validationError(
                        ResultCode.BAD_REQUEST,
                        "暂不支持的模型提供商: " + provider
                ));
    }
}
