package com.lanjii.ai.service;

import com.lanjii.ai.api.dto.AiFileKnowledgeDTO;
import com.lanjii.ai.entity.AiFileKnowledge;
import com.lanjii.ai.api.vo.AiFileKnowledgeVO;
import com.lanjii.framework.mp.base.BaseService;

/**
 * AI文件知识库表(AiFileKnowledge)表服务接口
 *
 * @author lanjii
 */
public interface AiFileKnowledgeService extends BaseService<AiFileKnowledge> {

    /**
     * 保存文件知识库信息（上传文件、解析并向量化）
     *
     * @param dto 文件知识库DTO
     */
    void saveNew(AiFileKnowledgeDTO dto);

    /**
     * 根据ID获取文件知识库详情
     *
     * @param id 文件知识库ID
     * @return 文件知识库视图对象
     */
    AiFileKnowledgeVO getByIdNew(Long id);

    /**
     * 根据ID更新文件知识库信息
     *
     * @param id  文件知识库ID
     * @param dto 文件知识库DTO
     */
    void updateByIdNew(Long id, AiFileKnowledgeDTO dto);

    /**
     * 根据ID删除文件知识库
     *
     * @param id 文件知识库ID
     */
    void removeByIdNew(Long id);

    /**
     * 重建所有文件知识库的向量数据
     */
    void rebuildAllVectors();

    /**
     * 重建单条文件知识库的向量数据
     *
     * @param id 文件知识库ID
     */
    void rebuildVectorById(Long id);
}
