package com.lanjii.biz.admin.ai.service.impl;

import com.lanjii.biz.admin.ai.dao.AiMetadataFieldDao;
import com.lanjii.biz.admin.ai.model.dto.AiMetadataFieldDTO;
import com.lanjii.biz.admin.ai.model.entity.AiMetadataField;
import com.lanjii.biz.admin.ai.model.vo.AiMetadataFieldVO;
import com.lanjii.biz.admin.ai.service.AiMetadataFieldService;
import com.lanjii.common.exception.BizException;
import com.lanjii.core.base.BaseServiceImpl;
import com.lanjii.core.resp.ResultCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
