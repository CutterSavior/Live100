package com.lanjii.biz.admin.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 角色菜单关联表(SysRoleMenu)表实体类
 *
 * @author lanjii
 */
@Data
@TableName("sys_role_menu")
public class SysRoleMenu {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 菜单ID
     */
    private Long menuId;

}
