package com.lanjii.biz.admin.ai.service.impl;

import com.lanjii.biz.admin.ai.dao.AiMetadataFieldDao;
import com.lanjii.biz.admin.ai.model.dto.AiMetadataFieldDTO;
import com.lanjii.biz.admin.ai.model.entity.AiMetadataField;
import com.lanjii.biz.admin.ai.model.vo.AiMetadataFieldVO;
import com.lanjii.biz.admin.ai.service.AiMetadataFieldService;
import com.lanjii.common.exception.BizException;
import com.lanjii.common.util.JacksonUtils;
import com.lanjii.core.base.BaseServiceImpl;
import com.lanjii.core.resp.ResultCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.lanjii.biz.admin.ai.model.entity.AiMetadataField.INSTANCE;

/**
 * AI 元数据字段 Service 实现
 *
 * @author lanjii
 */
@Service("aiMetadataFieldService")
@RequiredArgsConstructor
public class AiMetadataFieldServiceImpl extends BaseServiceImpl<AiMetadataFieldDao, AiMetadataField>
        implements AiMetadataFieldService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveNew(AiMetadataFieldDTO dto) {
        AiMetadataField entity = INSTANCE.toEntity(dto);
        entity.setUseCount(0L);
        save(entity);
    }

    @Override
    public AiMetadataFieldVO getByIdNew(Long id) {
        AiMetadataField entity = getById(id);
        if (entity == null) {
            throw new BizException(ResultCode.NOT_FOUND, "元数据字段不存在");
        }
        return INSTANCE.toVo(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateByIdNew(Long id, AiMetadataFieldDTO dto) {
        AiMetadataField exist = getById(id);
        if (exist == null) {
            throw new BizException(ResultCode.NOT_FOUND, "元数据字段不存在");
        }
        AiMetadataField entity = INSTANCE.toEntity(dto);
        entity.setId(id);
        entity.setUseCount(exist.getUseCount());
        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByIdNew(Long id) {
        AiMetadataField exist = getById(id);
        if (exist == null) {
            throw new BizException(ResultCode.NOT_FOUND, "元数据字段不存在");
        }
        removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void incrementUseCount(Set<String> fieldNames) {
        if (fieldNames == null || fieldNames.isEmpty()) {
            return;
        }

        List<AiMetadataField> fields = lambdaQuery()
                .in(AiMetadataField::getFieldName, fieldNames)
                .list();

        if (fields == null || fields.isEmpty()) {
            return;
        }

        fields.forEach(field -> {
            long current = field.getUseCount() == null ? 0L : field.getUseCount();
            field.setUseCount(current + 1);
        });

        updateBatchById(fields);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void decrementUseCount(Set<String> fieldNames) {
        if (fieldNames == null || fieldNames.isEmpty()) {
            return;
        }

        List<AiMetadataField> fields = lambdaQuery()
                .in(AiMetadataField::getFieldName, fieldNames)
                .list();

        if (fields == null || fields.isEmpty()) {
            return;
        }

        fields.forEach(field -> {
            long current = field.getUseCount() == null ? 0L : field.getUseCount();
            field.setUseCount(Math.max(current - 1, 0L));
        });

        updateBatchById(fields);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recalculateUseCount(Map<String, Long> metadataUsageMap) {
        List<AiMetadataField> allFields = list();
        if (allFields == null || allFields.isEmpty()) {
            return;
        }

        allFields.forEach(field -> {
            Long count = metadataUsageMap.getOrDefault(field.getFieldName(), 0L);
            field.setUseCount(count);
        });

        updateBatchById(allFields);
    }

    /**
     * 从 metadataJson 中解析出所有非空的 key 集合
     */
    public static Set<String> extractMetadataKeys(String metadataJson) {
        Map<String, Object> map = JacksonUtils.toMap(metadataJson, String.class, Object.class);
        if (map == null || map.isEmpty()) {
            return Collections.emptySet();
        }

        Set<String> result = new HashSet<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Object value = entry.getValue();
            if (isValidValue(value)) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    /**
     * 判断元数据值是否有效
     */
    private static boolean isValidValue(Object value) {
        if (value == null) {
            return false;
        }

        // 字符串：trim后非空
        if (value instanceof String) {
            return !((String) value).trim().isEmpty();
        }

        return true;
    }
}
