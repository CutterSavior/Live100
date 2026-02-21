package com.lanjii.sys.service;

import com.lanjii.sys.entity.SysDictData;
import com.lanjii.system.api.dto.SysDictDataDTO;
import com.lanjii.system.api.vo.SysDictDataVO;
import com.lanjii.framework.mp.base.BaseService;

import java.util.List;

/**
 * 字典数据表(SysDictData)表服务接口
 *
 * @author lanjii
 */
public interface SysDictDataService extends BaseService<SysDictData> {

    /**
     * 根据字典类型和字典值获取字典标签
     *
     * @param dictCode  字典类型
     * @param dictValue 字典值
     * @return 字典标签
     */
    String getLabel(String dictCode, Integer dictValue);

    /**
     * 根据ID更新字典数据
     *
     * @param id  字典数据ID
     * @param dto 字典数据DTO
     */
    void updateByIdNew(Long id, SysDictDataDTO dto);

    /**
     * 保存字典数据
     *
     * @param dto 字典数据DTO
     */
    void saveNew(SysDictDataDTO dto);

    /**
     * 根据ID获取字典数据详情
     *
     * @param id 字典数据ID
     * @return 字典数据视图对象
     */
    SysDictDataVO getByIdNew(Long id);

    /**
     * 清除字典数据缓存
     */
    void clearCache();

    /**
     * 获取所有启用的字典数据
     *
     * @return 字典数据列表
     */
    List<SysDictData> getAllEnabledDictData();

    /**
     * 根据字典类型获取所有启用的字典数据
     *
     * @param dictType 字典类型编码
     * @return 字典数据列表
     */
    List<SysDictData> getEnabledDictDataByType(String dictType);

}
