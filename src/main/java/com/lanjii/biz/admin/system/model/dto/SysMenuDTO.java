package com.lanjii.biz.admin.system.model.dto;

import com.lanjii.core.annotation.QueryCondition;
import com.lanjii.core.annotation.SortField;
import com.lanjii.core.base.BaseDTO;
import com.lanjii.core.enums.QueryType;
import com.lanjii.core.enums.SortOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单表(SysMenu) DTO
 *
 * @author lanjii
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysMenuDTO extends BaseDTO {

    /**
     * 菜单ID
     */
    private Long id;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 菜单名称
     */
    @QueryCondition(type = QueryType.LIKE)
    private String name;

    /**
     * 菜单类型（1-目录，2-菜单，3-按钮）
     */
    @QueryCondition
    private Integer type;

    /**
     * 路由路径(前端路由或外链URL)
     */
    @QueryCondition(type = QueryType.LIKE)
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 权限标识
     */
    @QueryCondition(type = QueryType.LIKE)
    private String permission;

    /**
     * 菜单图标
     */
    private String icon;


    /**
     * 是否可见（0-隐藏，1-显示）
     */
    private Integer isVisible;

    /**
     * 是否启用（1启用 0禁用）
     */
    @QueryCondition
    private Integer isEnabled;

    /**
     * 是否外链（0-否，1-是）
     */
    private Integer isExt;

    /**
     * 打开方式（0-内嵌，1-新窗口）
     */
    private Integer openMode;

    /**
     * 是否页面缓存（0-否，1-是）
     */
    private Integer isKeepAlive;

}
