package com.lanjii.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lanjii.sys.dao.SysDictTypeDao;
import com.lanjii.system.api.dto.SysDictTypeDTO;
import com.lanjii.sys.entity.SysDictData;
import com.lanjii.sys.entity.SysDictType;
import com.lanjii.system.api.vo.SysDictDataVO;
import com.lanjii.system.api.vo.SysDictTypeVO;
import com.lanjii.sys.service.SysDictDataService;
import com.lanjii.sys.service.SysDictTypeService;
import com.lanjii.common.enums.IsEnabledEnum;
import com.lanjii.common.exception.BizException;
import com.lanjii.framework.mp.base.BaseServiceImpl;
import com.lanjii.common.response.ResultCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * 系统字典类型表(SysDictType)表服务实现类
 *
 * @author lanjii
 */
@Service("sysDictTypeService")
@RequiredArgsConstructor
public class SysDictTypeServiceImpl extends BaseServiceImpl<SysDictTypeDao, SysDictType> implements SysDictTypeService {

    private final SysDictDataService sysDictDataService;

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

    @Override
    public void removeByIdNew(Long id) {
        SysDictType dictType = getById(id);

        LambdaQueryWrapper<SysDictData> dataQueryWrapper = new LambdaQueryWrapper<>();
        dataQueryWrapper.eq(SysDictData::getDictType, dictType.getTypeCode());
        sysDictDataService.remove(dataQueryWrapper);
        sysDictDataService.clearCache();
        removeById(id);
    }

    @Override
    public List<SysDictDataVO> getEnabledDataByType(String typeCode) {

        LambdaQueryWrapper<SysDictType> typeQueryWrapper = new LambdaQueryWrapper<>();
        typeQueryWrapper.eq(SysDictType::getTypeCode, typeCode);
        SysDictType dictType = getOne(typeQueryWrapper);

        if (dictType == null) {
            throw BizException.validationError(ResultCode.NOT_FOUND, "字典类型不存在");
        }


        List<SysDictData> dataList = sysDictDataService.getEnabledDictDataByType(typeCode);
        return dataList.stream().map(SysDictData.INSTANCE::toVo).toList();
    }

    @Override
    public List<SysDictDataVO> getAllEnabledData() {

        LambdaQueryWrapper<SysDictType> typeQueryWrapper = new LambdaQueryWrapper<>();
        typeQueryWrapper.eq(SysDictType::getIsEnabled, IsEnabledEnum.ENABLED.getCode());
        typeQueryWrapper.select(SysDictType::getTypeCode);
        List<SysDictType> enabledTypes = list(typeQueryWrapper);


        if (enabledTypes.isEmpty()) {
            return Collections.emptyList();
        }

        List<String> enabledTypeCodes = enabledTypes.stream()
                .map(SysDictType::getTypeCode)
                .toList();

        LambdaQueryWrapper<SysDictData> dataQueryWrapper = new LambdaQueryWrapper<>();
        dataQueryWrapper.in(SysDictData::getDictType, enabledTypeCodes);
        dataQueryWrapper.eq(SysDictData::getIsEnabled, IsEnabledEnum.ENABLED.getCode());
        dataQueryWrapper.orderByAsc(SysDictData::getDictType, SysDictData::getSortOrder);
        List<SysDictData> dataList = sysDictDataService.list(dataQueryWrapper);

        return dataList.stream().map(SysDictData.INSTANCE::toVo).toList();
    }
}

