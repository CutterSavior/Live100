package com.lanjii.system.api.vo;

import com.lanjii.common.base.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 菜单管理（SysMenu) VO
 *
 * @author lanjii
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysMenuVO extends BaseVO {

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
    private String name;

    /**
     * 菜单类型??-目录??-菜单??-按钮??
     */
    private Integer type;

    /**
     * 路由路径(前端路由或外链URL)
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 显示顺序
     */
    private Integer sortOrder;

/**
     * 是否可见??-隐藏??-显示??
     */
    private Integer isVisible;

    /**
     * 是否启用??启用 0禁用??
     */
    private Integer isEnabled;

    /**
     * 是否外链??-否，1-是）
     */
    private Integer isExt;

    /**
     * 打开方式??-内嵌??-新窗口）
     */
    private Integer openMode;

    /**
     * 是否页面缓存??-否，1-是）
     */
    private Integer isKeepAlive;
    /**
     * 显示范围（0-全部，1-租户，2-平台）
     */
    private Integer scope;

    /**
     * 子菜??
     */
    private List<SysMenuVO> children;
}
