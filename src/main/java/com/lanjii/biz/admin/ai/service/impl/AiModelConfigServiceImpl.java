package com.lanjii.biz.admin.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.lanjii.biz.admin.ai.dao.AiModelConfigDao;
import com.lanjii.biz.admin.ai.model.dto.AiModelConfigDTO;
import com.lanjii.biz.admin.ai.model.entity.AiModelConfig;
import com.lanjii.biz.admin.ai.model.vo.AiModelConfigVO;
import com.lanjii.biz.admin.ai.service.AiModelConfigService;
import com.lanjii.common.exception.BizException;
import com.lanjii.core.base.BaseServiceImpl;
import com.lanjii.core.resp.ResultCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.lanjii.biz.admin.ai.model.entity.AiModelConfig.INSTANCE;

/**
 * AI 模型配置 Service 实现
 *
 * @author lanjii
 */
@Service("aiModelConfigService")
@RequiredArgsConstructor
public class AiModelConfigServiceImpl extends BaseServiceImpl<AiModelConfigDao, AiModelConfig>
        implements AiModelConfigService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveNew(AiModelConfigDTO dto) {
        AiModelConfig entity = INSTANCE.toEntity(dto);
        entity.setIsDefault(0);
        save(entity);
    }

    @Override
    public AiModelConfigVO getByIdNew(Long id) {
        AiModelConfig entity = getById(id);
        if (entity == null) {
            throw new BizException(ResultCode.NOT_FOUND, "模型配置不存在");
        }
        return INSTANCE.toVo(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateByIdNew(Long id, AiModelConfigDTO dto) {
        AiModelConfig exist = getById(id);
        if (exist == null) {
            throw new BizException(ResultCode.NOT_FOUND, "模型配置不存在");
        }
        if (dto.getApiKey() != null && dto.getApiKey().contains("*")) {
            dto.setApiKey(exist.getApiKey());
        }
        AiModelConfig entity = INSTANCE.toEntity(dto);
        entity.setId(id);
        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByIdNew(Long id) {
        AiModelConfig exist = getById(id);
        if (exist == null) {
            throw new BizException(ResultCode.NOT_FOUND, "模型配置不存在");
        }
        if (exist.getIsDefault() != null && exist.getIsDefault() == 1) {
            throw new BizException(ResultCode.BAD_REQUEST, "默认配置不能被删除，请先设置其他配置为默认");
        }
        removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setDefault(Long id) {
        AiModelConfig exist = getById(id);
        if (exist == null) {
            throw new BizException(ResultCode.NOT_FOUND, "模型配置不存在");
        }
        if (exist.getIsEnabled() == null || exist.getIsEnabled() != 1) {
            throw new BizException(ResultCode.BAD_REQUEST, "只能将已启用的模型配置设为默认");
        }
        // 先清空所有默认（只更新 isDefault = 1 的记录）
        LambdaUpdateWrapper<AiModelConfig> clearWrapper = new LambdaUpdateWrapper<>();
        clearWrapper.eq(AiModelConfig::getIsDefault, 1)
                .set(AiModelConfig::getIsDefault, 0);
        this.update(clearWrapper);

        // 设置当前为默认
        LambdaUpdateWrapper<AiModelConfig> setWrapper = new LambdaUpdateWrapper<>();
        setWrapper.eq(AiModelConfig::getId, id)
                .set(AiModelConfig::getIsDefault, 1);
        this.update(setWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void toggleEnabled(Long id, Integer enabled) {
        AiModelConfig exist = getById(id);
        if (exist == null) {
            throw new BizException(ResultCode.NOT_FOUND, "模型配置不存在");
        }
        // 如果要禁用，且该配置是默认配置，则不允许禁用
        if (enabled != null && enabled == 0 && exist.getIsDefault() != null && exist.getIsDefault() == 1) {
            throw new BizException(ResultCode.BAD_REQUEST, "默认配置不能被禁用，请先设置其他配置为默认");
        }
        LambdaUpdateWrapper<AiModelConfig> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(AiModelConfig::getId, id)
                .set(AiModelConfig::getIsEnabled, enabled);
        this.update(wrapper);
    }

    @Override
    public AiModelConfig getDefaultConfig() {
        return this.lambdaQuery()
                .eq(AiModelConfig::getIsDefault, 1)
                .eq(AiModelConfig::getIsEnabled, 1)
                .one();
    }

}
