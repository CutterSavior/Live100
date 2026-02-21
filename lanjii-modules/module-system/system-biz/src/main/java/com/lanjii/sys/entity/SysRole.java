package com.lanjii.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lanjii.sys.base.AdminBaseMapper;
import com.lanjii.system.api.dto.SysRoleDTO;
import com.lanjii.system.api.vo.SysRoleVO;
import com.lanjii.framework.mp.base.TenantBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 角色表(SysRole)表实体类
 *
 * @author lanjii
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role")
public class SysRole extends TenantBaseEntity {

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
    public interface SysRoleMapper extends AdminBaseMapper<SysRole, SysRoleVO, SysRoleDTO> {

    }

    public static final SysRoleMapper INSTANCE = Mappers.getMapper(SysRoleMapper.class);

}
