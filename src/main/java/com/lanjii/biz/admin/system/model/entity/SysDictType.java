package com.lanjii.biz.admin.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lanjii.biz.admin.system.model.dto.SysDictTypeDTO;
import com.lanjii.biz.admin.system.model.vo.SysDictTypeVO;
import com.lanjii.core.base.BaseEntity;
import com.lanjii.core.base.BaseEntityMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 字典类型表(SysDictType)表实体类
 *
 * @author lanjii
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dict_type")
public class SysDictType extends BaseEntity<SysDictType> {

    /**
     * 字典主键
     */
    private Long id;

    /**
     * 字典名称
     */
    private String typeName;

    /**
     * 字典类型编码
     */
    private String typeCode;

    /**
     * 是否启用（1启用 0禁用）
     */
    private Integer isEnabled;

    /**
     * 备注
     */
    private String remark;

    @Mapper
    public interface SysDictTypeMapper extends BaseEntityMapper<SysDictType, SysDictTypeVO, SysDictTypeDTO> {

        @Mapping(target = "isEnabledLabel", expression = "java(dictValueToLabel(entity.getIsEnabled(),\"STATUS\"))")
        SysDictTypeVO toVo(SysDictType entity);
    }

    public static final SysDictType.SysDictTypeMapper INSTANCE = Mappers.getMapper(SysDictType.SysDictTypeMapper.class);

}
