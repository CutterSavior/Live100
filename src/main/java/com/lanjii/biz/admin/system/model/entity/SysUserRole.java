package com.lanjii.biz.admin.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 用户角色关联表(SysUserRole)表实体类
 *
 * @author lanjii
 */
@Data
@TableName("sys_user_role")
public class SysUserRole {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;

}
