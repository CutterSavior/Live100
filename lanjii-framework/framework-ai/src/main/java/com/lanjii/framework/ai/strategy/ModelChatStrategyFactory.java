package com.lanjii.framework.ai.strategy;

import com.lanjii.common.exception.BizException;
import com.lanjii.common.response.ResultCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 模型聊天策略工厂，根据 provider 选择对应实现
 *
 * @author lanjii
 */
@Component
@RequiredArgsConstructor
public class ModelChatStrategyFactory {

    private final List<ModelChatStrategy> strategies;

    /**
     * 根据提供商获取对应的策略
     *
     * @param provider 提供商标识
     * @return 对应的策略实现
     */
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
