package com.lanjii.biz.admin.ai.service.impl;

import com.lanjii.biz.admin.ai.dao.AiKnowledgeDao;
import com.lanjii.biz.admin.ai.model.dto.AiKnowledgeDTO;
import com.lanjii.biz.admin.ai.model.entity.AiKnowledge;
import com.lanjii.biz.admin.ai.model.entity.AiMetadataField;
import com.lanjii.biz.admin.ai.model.vo.AiKnowledgeVO;
import com.lanjii.biz.admin.ai.service.AiKnowledgeService;
import com.lanjii.biz.admin.ai.service.AiMetadataFieldService;
import com.lanjii.common.exception.BizException;
import com.lanjii.common.util.JacksonUtils;
import com.lanjii.core.base.BaseServiceImpl;
import com.lanjii.core.resp.ResultCode;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * AI知识库表(AiKnowledge)表服务实现类
 *
 * @author lanjii
 */
@RequiredArgsConstructor
@Service("aiKnowledgeService")
public class AiKnowledgeServiceImpl extends BaseServiceImpl<AiKnowledgeDao, AiKnowledge> implements AiKnowledgeService {

    private final VectorStore vectorStore;
    private final AiMetadataFieldService aiMetadataFieldService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveNew(AiKnowledgeDTO dto) {
        AiKnowledge entity = AiKnowledge.INSTANCE.toEntity(dto);

        Set<String> newKeys = extractMetadataKeys(entity.getMetadataJson());
        save(entity);

        // 更新元数据字段使用次数
        if (!newKeys.isEmpty()) {
            incrementMetadataUseCount(newKeys);
        }

        // 将知识库内容与元数据写入向量库
        Map<String, Object> metadata = JacksonUtils.toMap(entity.getMetadataJson(), String.class, Object.class);
        vectorStore.add(Collections.singletonList(new Document(String.valueOf(entity.getId()), entity.getContent(), metadata)));
    }

    @Override
    public AiKnowledgeVO getByIdNew(Long id) {
        AiKnowledge entity = getById(id);
        if (entity == null) {
            throw new BizException(ResultCode.NOT_FOUND, "数据不存在");
        }
        return AiKnowledge.INSTANCE.toVo(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateByIdNew(Long id, AiKnowledgeDTO dto) {
        AiKnowledge existEntity = getById(id);
        if (existEntity == null) {
            throw new BizException(ResultCode.NOT_FOUND, "数据不存在");
        }

        // 计算元数据 key 集合的变化
        Set<String> oldKeys = extractMetadataKeys(existEntity.getMetadataJson());
        AiKnowledge entity = AiKnowledge.INSTANCE.toEntity(dto);
        entity.setId(id);
        Set<String> newKeys = extractMetadataKeys(entity.getMetadataJson());

        // old 有、new 没有的 key：useCount -1
        Set<String> toDecrement = new HashSet<>(oldKeys);
        toDecrement.removeAll(newKeys);
        if (!toDecrement.isEmpty()) {
            decrementMetadataUseCount(toDecrement);
        }

        // new 有、old 没有的 key：useCount +1
        Set<String> toIncrement = new HashSet<>(newKeys);
        toIncrement.removeAll(oldKeys);
        if (!toIncrement.isEmpty()) {
            incrementMetadataUseCount(toIncrement);
        }

        // 删除旧向量
        vectorStore.delete(List.of(String.valueOf(id)));
        updateById(entity);

        // 重新写入向量库
        Map<String, Object> metadata = JacksonUtils.toMap(entity.getMetadataJson(), String.class, Object.class);
        vectorStore.add(List.of(new Document(String.valueOf(entity.getId()), entity.getContent(), metadata)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByIdNew(Long id) {
        AiKnowledge entity = getById(id);
        if (entity == null) {
            throw new BizException(ResultCode.NOT_FOUND, "数据不存在");
        }

        // 删除知识条目前，所有使用到的元数据字段 useCount -1
        Set<String> oldKeys = extractMetadataKeys(entity.getMetadataJson());
        if (!oldKeys.isEmpty()) {
            decrementMetadataUseCount(oldKeys);
        }

        removeById(id);
        vectorStore.delete(List.of(String.valueOf(id)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rebuildAllVectors() {
        List<AiKnowledge> list = list();
        if (list == null || list.isEmpty()) {
            return;
        }

        List<String> ids = list.stream()
                .map(k -> String.valueOf(k.getId()))
                .collect(Collectors.toList());
        vectorStore.delete(ids);

        List<Document> documents = list.stream().map(entity -> {
            Map<String, Object> metadata = JacksonUtils.toMap(entity.getMetadataJson(), String.class, Object.class);
            return new Document(String.valueOf(entity.getId()), entity.getContent(), metadata);
        }).collect(Collectors.toList());
        vectorStore.add(documents);

        recalculateAllMetadataUseCount();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rebuildVectorById(Long id) {
        AiKnowledge entity = getById(id);
        if (entity == null) {
            throw new BizException(ResultCode.NOT_FOUND, "知识库数据不存在");
        }

        // 删除旧向量
        vectorStore.delete(List.of(String.valueOf(id)));

        // 重新写入向量库
        Map<String, Object> metadata = JacksonUtils.toMap(entity.getMetadataJson(), String.class, Object.class);
        vectorStore.add(List.of(new Document(String.valueOf(entity.getId()), entity.getContent(), metadata)));
    }

    /**
     * 从 metadataJson 中解析出所有非空的 key 集合
     */
    private Set<String> extractMetadataKeys(String metadataJson) {
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
    private boolean isValidValue(Object value) {
        if (value == null) {
            return false;
        }

        // 字符串：trim后非空
        if (value instanceof String) {
            return !((String) value).trim().isEmpty();
        }

        return true;
    }

    /**
     * 元数据字段使用次数 +1（批量操作）
     */
    private void incrementMetadataUseCount(Set<String> fieldNames) {
        if (fieldNames == null || fieldNames.isEmpty()) {
            return;
        }

        // 批量查询
        List<AiMetadataField> fields = aiMetadataFieldService.lambdaQuery()
                .in(AiMetadataField::getFieldName, fieldNames)
                .list();

        if (fields == null || fields.isEmpty()) {
            return;
        }

        // 批量更新 useCount
        fields.forEach(field -> {
            long current = field.getUseCount() == null ? 0L : field.getUseCount();
            field.setUseCount(current + 1);
        });

        // 批量更新数据库
        aiMetadataFieldService.updateBatchById(fields);
    }

    /**
     * 元数据字段使用次数 -1
     */
    private void decrementMetadataUseCount(Set<String> fieldNames) {
        if (fieldNames == null || fieldNames.isEmpty()) {
            return;
        }

        // 批量查询
        List<AiMetadataField> fields = aiMetadataFieldService.lambdaQuery()
                .in(AiMetadataField::getFieldName, fieldNames)
                .list();

        if (fields == null || fields.isEmpty()) {
            return;
        }

        // 批量更新 useCount
        fields.forEach(field -> {
            long current = field.getUseCount() == null ? 0L : field.getUseCount();
            field.setUseCount(Math.max(current - 1, 0L));
        });

        // 批量更新数据库
        aiMetadataFieldService.updateBatchById(fields);
    }

    /**
     * 全量重算所有元数据字段使用次数
     */
    private void recalculateAllMetadataUseCount() {
        // 统计所有知识的元数据使用情况
        List<AiKnowledge> knowledges = list();
        if (knowledges == null || knowledges.isEmpty()) {
            // 如果没有知识条目，将所有字段useCount设为0
            List<AiMetadataField> allFields = aiMetadataFieldService.list();
            if (allFields != null && !allFields.isEmpty()) {
                allFields.forEach(field -> field.setUseCount(0L));
                aiMetadataFieldService.updateBatchById(allFields);
            }
            return;
        }

        // 统计每个字段被多少条知识使用
        Map<String, Long> counter = new HashMap<>();
        for (AiKnowledge k : knowledges) {
            Set<String> keys = extractMetadataKeys(k.getMetadataJson());
            keys.forEach(key -> counter.merge(key, 1L, Long::sum));
        }

        // 批量查询所有元数据字段
        List<AiMetadataField> allFields = aiMetadataFieldService.list();
        if (allFields == null || allFields.isEmpty()) {
            return;
        }

        // 批量更新 useCount
        allFields.forEach(field -> {
            Long count = counter.getOrDefault(field.getFieldName(), 0L);
            field.setUseCount(count);
        });

        aiMetadataFieldService.updateBatchById(allFields);
    }
}
