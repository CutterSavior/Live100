
package com.lanjii.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lanjii.core.base.BaseModel;
import com.lanjii.core.base.BaseModelMapper;
import com.lanjii.model.vo.SysRoleVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 角色表
 *
 * @author lizheng
 * @date 2024-09-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysRole extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 角色名
     */
    private String name;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 排序
     */
    private Integer sort;


    @Mapper
    public interface SysRoleModelMapper extends BaseModelMapper<SysRoleVo, SysRole> {

        @Override
        @Mapping(target = "statusName", expression = "java(getDictLabel(model.getStatus(),\"status\"))")
        SysRoleVo modelToVo(SysRole model);

    }

    public static final SysRoleModelMapper INSTANCE = Mappers.getMapper(SysRoleModelMapper.class);

}
