package com.lanjii.biz.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lanjii.biz.admin.system.dao.SysDictTypeDao;
import com.lanjii.biz.admin.system.model.dto.SysDictTypeDTO;
import com.lanjii.biz.admin.system.model.vo.SysDictTypeVO;
import com.lanjii.core.base.BaseServiceImpl;
import com.lanjii.core.resp.ResultCode;
import com.lanjii.biz.admin.system.model.entity.SysDictType;
import com.lanjii.common.exception.BizException;
import com.lanjii.biz.admin.system.service.SysDictTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 系统字典类型表(SysDictType)表服务实现类
 *
 * @author lanjii
 */
@Service("sysDictTypeService")
@RequiredArgsConstructor
public class SysDictTypeServiceImpl extends BaseServiceImpl<SysDictTypeDao, SysDictType> implements SysDictTypeService {

    @Override
    public void updateByIdNew(Long id, SysDictTypeDTO dto) {
        SysDictType originalDictType = getById(id);
        if (originalDictType == null) {
            throw BizException.validationError(ResultCode.NOT_FOUND, "字典类型不存在");
        }

        assertTypeCodeUnique(dto.getTypeCode(), id);

        SysDictType entity = SysDictType.INSTANCE.toEntity(dto);
        entity.setId(id);
        updateById(entity);
    }

    @Override
    public void saveNew(SysDictTypeDTO dto) {
        assertTypeCodeUnique(dto.getTypeCode(), null);
        SysDictType entity = SysDictType.INSTANCE.toEntity(dto);
        save(entity);
    }

    @Override
    public SysDictTypeVO getByIdNew(Long id) {
        SysDictType entity = getById(id);
        return SysDictType.INSTANCE.toVo(entity);
    }

    @Override
    public void assertTypeCodeUnique(String typeCode, Long excludeId) {
        LambdaQueryWrapper<SysDictType> queryWrapper = new LambdaQueryWrapper<SysDictType>()
                .eq(SysDictType::getTypeCode, typeCode);
        if (excludeId != null) {
            queryWrapper.ne(SysDictType::getId, excludeId);
        }
        if (baseMapper.exists(queryWrapper)) {
            throw BizException.validationError(ResultCode.CONFLICT, "字典类型编码已存在");
        }
    }
}

