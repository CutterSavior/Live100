package com.lanjii.biz.admin.ai.service;

import com.lanjii.biz.admin.ai.model.dto.AiKnowledgeDTO;
import com.lanjii.biz.admin.ai.model.entity.AiKnowledge;
import com.lanjii.biz.admin.ai.model.vo.AiKnowledgeVO;
import com.lanjii.core.base.BaseService;

/**
 * AI知识库表(AiKnowledge)表服务接口
 *
 * @author lanjii
 */
public interface AiKnowledgeService extends BaseService<AiKnowledge> {

    /**
     * 保存知识库信息
     *
     * @param dto 知识库DTO
     */
    void saveNew(AiKnowledgeDTO dto);

    /**
     * 根据ID获取知识库详情
     *
     * @param id 知识库ID
     * @return 知识库视图对象
     */
    AiKnowledgeVO getByIdNew(Long id);

    /**
     * 根据ID更新知识库信息
     *
     * @param id  知识库ID
     * @param dto 知识库DTO
     */
    void updateByIdNew(Long id, AiKnowledgeDTO dto);

    /**
     * 根据ID删除知识库
     *
     * @param id 知识库ID
     */
    void removeByIdNew(Long id);

    /**
     * 重建所有知识库的向量数据
     */
    void rebuildAllVectors();

    /**
     * 重建单条知识库的向量数据
     *
     * @param id 知识库ID
     */
    void rebuildVectorById(Long id);
}
