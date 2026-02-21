package com.lanjii.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lanjii.sys.dao.SysDictDataDao;
import com.lanjii.system.api.dto.SysDictDataDTO;
import com.lanjii.system.api.vo.SysDictDataVO;
import com.lanjii.framework.mp.base.BaseServiceImpl;
import com.lanjii.common.response.ResultCode;
import com.lanjii.sys.entity.SysDictData;
import com.lanjii.common.enums.IsEnabledEnum;
import com.lanjii.common.exception.BizException;
import com.lanjii.sys.service.SysDictDataService;
import com.lanjii.sys.config.SystemCacheConstants;
import com.lanjii.framework.cache.helper.CacheHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 字典数据表(SysDictData)表服务实现类
 *
 * @author lanjii
 */
@Service("sysDictDataService")
@RequiredArgsConstructor
public class SysDictDataServiceImpl extends BaseServiceImpl<SysDictDataDao, SysDictData> implements SysDictDataService {

    private final CacheManager cacheManager;
    private final CacheHelper cacheHelper;

    private Cache getDictDataCache() {
        return cacheManager.getCache(SystemCacheConstants.DICT_DATA.getName());
    }

    @Override
    public String getLabel(String dictCode, Integer dictValue) {
        String key = dictCode + ":" + dictValue;
        Cache cache = getDictDataCache();
        
        // 先从缓存中获取字典数据
        SysDictData dictData = cache.get(key, SysDictData.class);
        if (dictData != null) {
            return dictData.getDictLabel();
        }

        // 缓存中没有，从数据库查询
        LambdaQueryWrapper<SysDictData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDictData::getDictType, dictCode);
        queryWrapper.eq(SysDictData::getDictValue, dictValue);
        queryWrapper.eq(SysDictData::getIsEnabled, IsEnabledEnum.ENABLED.getCode());
        SysDictData sysDictData = getOne(queryWrapper);

        String label = sysDictData != null ? sysDictData.getDictLabel() : null;

        // 存入缓存
        if (sysDictData != null) {
            cache.put(key, sysDictData);
        }
        return label;
    }

    @Override
    public void updateByIdNew(Long id, SysDictDataDTO dto) {
        SysDictData originalDictData = getById(id);
        if (originalDictData == null) {
            throw BizException.validationError(ResultCode.NOT_FOUND, "字典数据不存在");
        }

        // 校验字典键值是否重复
        validateDictValueUnique(dto.getDictType(), dto.getDictValue(), id);

        SysDictData entity = SysDictData.INSTANCE.toEntity(dto);
        entity.setId(id);
        updateById(entity);

        // 清除相关缓存
        getDictDataCache().evict(originalDictData.getDictType() + ":" + originalDictData.getDictValue());
    }

    @Override
    public void saveNew(SysDictDataDTO dto) {
        // 校验字典键值是否重复
        validateDictValueUnique(dto.getDictType(), dto.getDictValue(), null);
        
        SysDictData entity = SysDictData.INSTANCE.toEntity(dto);
        save(entity);

        // 清除相关缓存
        getDictDataCache().evict(entity.getDictType() + ":" + entity.getDictValue());
    }

    @Override
    public SysDictDataVO getByIdNew(Long id) {
        SysDictData entity = getById(id);
        return SysDictData.INSTANCE.toVo(entity);
    }

    /**
     * 校验字典键值在同一类型下是否唯一
     *
     * @param dictType  字典类型编码
     * @param dictValue 字典键值
     * @param excludeId 排除的ID（编辑时使用，排除当前记录）
     */
    private void validateDictValueUnique(String dictType, Integer dictValue, Long excludeId) {
        LambdaQueryWrapper<SysDictData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDictData::getDictType, dictType);
        queryWrapper.eq(SysDictData::getDictValue, dictValue);
        
        // 编辑时排除当前记录
        if (excludeId != null) {
            queryWrapper.ne(SysDictData::getId, excludeId);
        }
        
        long count = count(queryWrapper);
        if (count > 0) {
            throw BizException.validationError(ResultCode.CONFLICT, 
                String.format("字典类型[%s]下已存在键值[%d]，不能重复", dictType, dictValue));
        }
    }

    @Override
    public void clearCache() {
        cacheHelper.clearAll(SystemCacheConstants.DICT_DATA.getName());
    }

    @Override
    public List<SysDictData> getAllEnabledDictData() {
        LambdaQueryWrapper<SysDictData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDictData::getIsEnabled, IsEnabledEnum.ENABLED.getCode());
        queryWrapper.orderByAsc(SysDictData::getDictType, SysDictData::getSortOrder);
        return list(queryWrapper);
    }

    @Override
    public List<SysDictData> getEnabledDictDataByType(String dictType) {
        LambdaQueryWrapper<SysDictData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDictData::getDictType, dictType);
        queryWrapper.eq(SysDictData::getIsEnabled, IsEnabledEnum.ENABLED.getCode());
        queryWrapper.orderByAsc(SysDictData::getSortOrder);
        return list(queryWrapper);
    }

}
