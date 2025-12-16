package com.lanjii.biz.admin.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lanjii.biz.admin.system.model.dto.SysRoleDTO;
import com.lanjii.biz.admin.system.model.vo.SysRoleVO;
import com.lanjii.core.base.BaseEntity;
import com.lanjii.core.base.BaseEntityMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 角色表(SysRole)表实体类
 *
 * @author lanjii
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role")
public class SysRole extends BaseEntity<SysRole> {

    /**
     * 角色ID
     */
    private Long id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色编码
     */
    private String code;

    /**
     * 显示顺序
     */
    private Integer sortOrder;

    /**
     * 是否启用（1启用 0禁用）
     */
    private Integer isEnabled;

    /**
     * 备注
     */
    private String remark;


    @Mapper
    public interface SysRoleMapper extends BaseEntityMapper<SysRole, SysRoleVO, SysRoleDTO> {

        @Mapping(target = "isEnabledLabel", expression = "java(dictValueToLabel(entity.getIsEnabled(),\"STATUS\"))")
        SysRoleVO toVo(SysRole entity);

    }

    public static final SysRoleMapper INSTANCE = Mappers.getMapper(SysRoleMapper.class);


}
