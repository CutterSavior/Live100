package com.lanjii.ai.service.impl;

import com.lanjii.ai.dao.AiKnowledgeDao;
import com.lanjii.ai.api.dto.AiKnowledgeDTO;
import com.lanjii.ai.entity.AiKnowledge;
import com.lanjii.ai.api.vo.AiKnowledgeVO;
import com.lanjii.ai.service.AiKnowledgeService;
import com.lanjii.ai.service.AiMetadataFieldService;
import com.lanjii.common.exception.BizException;
import com.lanjii.common.util.JacksonUtils;
import com.lanjii.framework.mp.base.BaseServiceImpl;
import com.lanjii.common.response.ResultCode;
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

        Set<String> newKeys = AiMetadataFieldServiceImpl.extractMetadataKeys(entity.getMetadataJson());
        save(entity);

        // 更新元数据字段使用次数
        if (!newKeys.isEmpty()) {
            aiMetadataFieldService.incrementUseCount(newKeys);
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
        Set<String> oldKeys = AiMetadataFieldServiceImpl.extractMetadataKeys(existEntity.getMetadataJson());
        AiKnowledge entity = AiKnowledge.INSTANCE.toEntity(dto);
        entity.setId(id);
        Set<String> newKeys = AiMetadataFieldServiceImpl.extractMetadataKeys(entity.getMetadataJson());

        // old 有、new 没有的 key：useCount -1
        Set<String> toDecrement = new HashSet<>(oldKeys);
        toDecrement.removeAll(newKeys);
        if (!toDecrement.isEmpty()) {
            aiMetadataFieldService.decrementUseCount(toDecrement);
        }

        // new 有、old 没有的 key：useCount +1
        Set<String> toIncrement = new HashSet<>(newKeys);
        toIncrement.removeAll(oldKeys);
        if (!toIncrement.isEmpty()) {
            aiMetadataFieldService.incrementUseCount(toIncrement);
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
        Set<String> oldKeys = AiMetadataFieldServiceImpl.extractMetadataKeys(entity.getMetadataJson());
        if (!oldKeys.isEmpty()) {
            aiMetadataFieldService.decrementUseCount(oldKeys);
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

        // 重新计算元数据使用次数
        Map<String, Long> metadataUsageMap = new HashMap<>();
        for (AiKnowledge k : list) {
            Set<String> keys = AiMetadataFieldServiceImpl.extractMetadataKeys(k.getMetadataJson());
            keys.forEach(key -> metadataUsageMap.merge(key, 1L, Long::sum));
        }
        aiMetadataFieldService.recalculateUseCount(metadataUsageMap);
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

}
