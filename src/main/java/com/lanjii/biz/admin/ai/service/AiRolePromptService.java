package com.lanjii.biz.admin.ai.service;

import com.lanjii.biz.admin.ai.model.dto.AiRolePromptDTO;
import com.lanjii.biz.admin.ai.model.entity.AiRolePrompt;
import com.lanjii.biz.admin.ai.model.vo.AiRolePromptVO;
import com.lanjii.core.base.BaseService;

/**
 * AI 角色与提示词配置 Service
 *
 * @author lanjii
 */
public interface AiRolePromptService extends BaseService<AiRolePrompt> {

    /**
     * 保存角色与提示词
     *
     * @param dto 角色与提示词DTO
     */
    void saveNew(AiRolePromptDTO dto);

    /**
     * 根据ID获取角色与提示词详情
     *
     * @param id 角色ID
     * @return 角色与提示词VO
     */
    AiRolePromptVO getByIdNew(Long id);

    /**
     * 根据ID更新角色与提示词
     *
     * @param id  角色ID
     * @param dto 角色与提示词DTO
     */
    void updateByIdNew(Long id, AiRolePromptDTO dto);

    /**
     * 根据ID删除角色与提示词
     *
     * @param id 角色ID
     */
    void removeByIdNew(Long id);

    /**
     * 切换启用状态
     */
    void toggleEnabled(Long id);
}
