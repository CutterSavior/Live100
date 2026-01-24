package com.lanjii.biz.admin.system.service;

import com.lanjii.biz.admin.system.model.dto.SysDictTypeDTO;
import com.lanjii.biz.admin.system.model.entity.SysDictType;
import com.lanjii.biz.admin.system.model.vo.SysDictDataVO;
import com.lanjii.biz.admin.system.model.vo.SysDictTypeVO;
import com.lanjii.core.base.BaseService;

import java.util.List;

/**
 * 字典类型表(SysDictType)表服务接口
 *
 * @author lanjii
 */
public interface SysDictTypeService extends BaseService<SysDictType> {

    /**
     * 根据ID更新字典类型
     *
     * @param id  字典类型ID
     * @param dto 字典类型DTO
     */
    void updateByIdNew(Long id, SysDictTypeDTO dto);

    /**
     * 保存字典类型
     *
     * @param dto 字典类型DTO
     */
    void saveNew(SysDictTypeDTO dto);

    /**
     * 根据ID获取字典类型详情
     *
     * @param id 字典类型ID
     * @return 字典类型视图对象
     */
    SysDictTypeVO getByIdNew(Long id);

    /**
     * 校验字典类型编码唯一性；当存在冲突时抛出业务异常。
     *
     * @param typeCode  待校验的类型编码
     * @param excludeId 排除的记录ID（更新时传入当前ID，新增传null）
     */
    void assertTypeCodeUnique(String typeCode, Long excludeId);

    /**
     * 根据 ID 删除字典
     *
     * @param id 字典类型ID
     */
    void removeByIdNew(Long id);

    /**
     * 获取启用的字典数据
     *
     * @param typeCode 字典类型编码
     * @return 启用的字典数据列表
     */
    List<SysDictDataVO> getEnabledDataByType(String typeCode);

    /**
     * 获取所有启用的字典数据
     *
     * @return 字典数据列表
     */
    List<SysDictDataVO> getAllEnabledData();
}

