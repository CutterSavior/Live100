package com.lanjii.ai.service;

import com.lanjii.ai.api.dto.AiMetadataFieldDTO;
import com.lanjii.ai.entity.AiMetadataField;
import com.lanjii.ai.api.vo.AiMetadataFieldVO;
import com.lanjii.framework.mp.base.BaseService;

import java.util.Map;
import java.util.Set;

/**
 * AI 元数据字段 Service
 *
 * @author lanjii
 */
public interface AiMetadataFieldService extends BaseService<AiMetadataField> {

    /**
     * 保存元数据字段
     *
     * @param dto 元数据字段DTO
     */
    void saveNew(AiMetadataFieldDTO dto);

    /**
     * 根据ID获取元数据字段详情
     *
     * @param id 字段ID
     * @return 元数据字段VO
     */
    AiMetadataFieldVO getByIdNew(Long id);

    /**
     * 根据ID更新元数据字段
     *
     * @param id  字段ID
     * @param dto 元数据字段DTO
     */
    void updateByIdNew(Long id, AiMetadataFieldDTO dto);

    /**
     * 根据ID删除元数据字段
     *
     * @param id 字段ID
     */
    void removeByIdNew(Long id);

    /**
     * 增加元数据字段的使用次数
     *
     * @param fieldNames 字段名集合
     */
    void incrementUseCount(Set<String> fieldNames);

    /**
     * 减少元数据字段的使用次数
     *
     * @param fieldNames 字段名集合
     */
    void decrementUseCount(Set<String> fieldNames);

    /**
     * 重新计算所有元数据字段的使用次数
     *
     * @param metadataUsageMap 元数据使用情况统计表（key: fieldName, value: useCount）
     */
    void recalculateUseCount(Map<String, Long> metadataUsageMap);
}
