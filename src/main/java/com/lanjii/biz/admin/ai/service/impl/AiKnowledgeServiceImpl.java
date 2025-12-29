package com.lanjii.biz.admin.ai.service.impl;

import com.lanjii.biz.admin.ai.dao.AiKnowledgeDao;
import com.lanjii.biz.admin.ai.model.dto.AiKnowledgeDTO;
import com.lanjii.biz.admin.ai.model.entity.AiKnowledge;
import com.lanjii.biz.admin.ai.model.vo.AiKnowledgeVO;
import com.lanjii.biz.admin.ai.service.AiKnowledgeService;
import com.lanjii.common.exception.BizException;
import com.lanjii.core.base.BaseServiceImpl;
import com.lanjii.core.resp.ResultCode;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * AI知识库表(AiKnowledge)表服务实现类
 *
 * @author lanjii
 */
@RequiredArgsConstructor
@Service("aiKnowledgeService")
public class AiKnowledgeServiceImpl extends BaseServiceImpl<AiKnowledgeDao, AiKnowledge> implements AiKnowledgeService {

    private final VectorStore vectorStore;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveNew(AiKnowledgeDTO dto) {
        AiKnowledge entity = AiKnowledge.INSTANCE.toEntity(dto);
        save(entity);
        vectorStore.add(Collections.singletonList(new Document(String.valueOf(entity.getId()), entity.toString(), new HashMap<>())));
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
        vectorStore.delete(List.of(String.valueOf(id)));
        AiKnowledge entity = AiKnowledge.INSTANCE.toEntity(dto);
        entity.setId(id);
        updateById(entity);
        vectorStore.add(List.of(new Document(String.valueOf(entity.getId()), entity.toString(), new HashMap<>())));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByIdNew(Long id) {
        AiKnowledge entity = getById(id);
        if (entity == null) {
            throw new BizException(ResultCode.NOT_FOUND, "数据不存在");
        }
        removeById(id);
        vectorStore.delete(List.of(String.valueOf(id)));
    }
}
