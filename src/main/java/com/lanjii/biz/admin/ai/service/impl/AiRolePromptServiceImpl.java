package com.lanjii.biz.admin.ai.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lanjii.biz.admin.ai.dao.AiModelConfigDao;
import com.lanjii.biz.admin.ai.dao.AiRolePromptDao;
import com.lanjii.biz.admin.ai.model.dto.AiRolePromptDTO;
import com.lanjii.biz.admin.ai.model.entity.AiModelConfig;
import com.lanjii.biz.admin.ai.model.entity.AiRolePrompt;
import com.lanjii.biz.admin.ai.model.vo.AiRolePromptVO;
import com.lanjii.biz.admin.ai.service.AiRolePromptService;
import com.lanjii.common.exception.BizException;
import com.lanjii.core.base.BaseServiceImpl;
import com.lanjii.core.resp.ResultCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.lanjii.biz.admin.ai.model.entity.AiRolePrompt.INSTANCE;

/**
 * AI 角色与提示词配置 Service 实现
 *
 * @author lanjii
 */
@Service("aiRolePromptService")
@RequiredArgsConstructor
public class AiRolePromptServiceImpl extends BaseServiceImpl<AiRolePromptDao, AiRolePrompt>
        implements AiRolePromptService {

    private final AiModelConfigDao aiModelConfigDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveNew(AiRolePromptDTO dto) {
        AiRolePrompt entity = INSTANCE.toEntity(dto);
        save(entity);
    }

    @Override
    public AiRolePromptVO getByIdNew(Long id) {
        AiRolePrompt entity = getById(id);
        if (entity == null) {
            throw new BizException(ResultCode.NOT_FOUND, "角色与提示词配置不存在");
        }
        return INSTANCE.toVo(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateByIdNew(Long id, AiRolePromptDTO dto) {
        AiRolePrompt exist = getById(id);
        if (exist == null) {
            throw new BizException(ResultCode.NOT_FOUND, "角色与提示词配置不存在");
        }
        AiRolePrompt entity = INSTANCE.toEntity(dto);
        entity.setId(id);
        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByIdNew(Long id) {
        AiRolePrompt exist = getById(id);
        if (exist == null) {
            throw new BizException(ResultCode.NOT_FOUND, "角色与提示词配置不存在");
        }

        // 检查是否被启用的模型配置使用
        Long count = aiModelConfigDao.selectCount(
                Wrappers.<AiModelConfig>lambdaQuery()
                        .eq(AiModelConfig::getRoleId, id)
                        .eq(AiModelConfig::getIsEnabled, 1)
        );
        if (count > 0) {
            throw new BizException(ResultCode.BAD_REQUEST, "该角色与提示词正在被启用的模型配置使用，无法删除");
        }

        removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void toggleEnabled(Long id) {
        AiRolePrompt exist = getById(id);
        if (exist == null) {
            throw new BizException(ResultCode.NOT_FOUND, "角色与提示词配置不存在");
        }
        Integer current = exist.getIsEnabled() == null ? 1 : exist.getIsEnabled();
        int nextStatus = current != null && current == 1 ? 0 : 1;

        // 如果是停用操作，检查是否被启用的模型配置使用
        if (nextStatus == 0) {
            Long count = aiModelConfigDao.selectCount(
                    Wrappers.<AiModelConfig>lambdaQuery()
                            .eq(AiModelConfig::getRoleId, id)
                            .eq(AiModelConfig::getIsEnabled, 1)
            );
            if (count > 0) {
                throw new BizException(ResultCode.BAD_REQUEST, "该角色与提示词正在被启用的模型配置使用，无法停用");
            }
        }

        exist.setIsEnabled(nextStatus);
        updateById(exist);
    }

}
