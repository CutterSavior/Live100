package com.lanjii.model.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色和菜单的关联表
 * </p>
 *
 * @author lizheng
 * @since 2023-08-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysRoleMenuRel extends Model {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单主键
     */
    private Long menuId;

    /**
     * 角色主键
     */
    private Long roleId;


}
