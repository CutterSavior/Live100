package com.lanjii.biz.admin.ai.service;

import com.lanjii.biz.admin.ai.model.dto.AiModelConfigDTO;
import com.lanjii.biz.admin.ai.model.entity.AiModelConfig;
import com.lanjii.biz.admin.ai.model.vo.AiModelConfigVO;
import com.lanjii.core.base.BaseService;

/**
 * AI 模型配置 Service
 *
 * @author lanjii
 */
public interface AiModelConfigService extends BaseService<AiModelConfig> {

    /**
     * 保存模型配置
     *
     * @param dto 模型配置DTO
     */
    void saveNew(AiModelConfigDTO dto);

    /**
     * 根据ID获取模型配置详情
     *
     * @param id 配置ID
     * @return 模型配置VO
     */
    AiModelConfigVO getByIdNew(Long id);

    /**
     * 根据ID更新模型配置
     *
     * @param id  配置ID
     * @param dto 模型配置DTO
     */
    void updateByIdNew(Long id, AiModelConfigDTO dto);

    /**
     * 根据ID删除模型配置
     *
     * @param id 配置ID
     */
    void removeByIdNew(Long id);

    /**
     * 将指定配置设为默认
     */
    void setDefault(Long id);

    /**
     * 切换启用状态。
     */
    void toggleEnabled(Long id, Integer enabled);

    /**
     * 获取当前启用的默认模型配置
     */
    AiModelConfig getDefaultConfig();
}
