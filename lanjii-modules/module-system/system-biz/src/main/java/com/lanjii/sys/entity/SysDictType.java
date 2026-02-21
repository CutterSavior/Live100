package com.lanjii.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lanjii.sys.base.AdminBaseMapper;
import com.lanjii.system.api.dto.SysDictTypeDTO;
import com.lanjii.system.api.vo.SysDictTypeVO;
import com.lanjii.framework.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 字典类型表(SysDictType)表实体类
 *
 * @author lanjii
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dict_type")
public class SysDictType extends BaseEntity {

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
    public interface SysDictTypeMapper extends AdminBaseMapper<SysDictType, SysDictTypeVO, SysDictTypeDTO> {

    }

    public static final SysDictType.SysDictTypeMapper INSTANCE = Mappers.getMapper(SysDictType.SysDictTypeMapper.class);

}
