package com.lanjii.biz.admin.system.model.vo;

import com.lanjii.core.base.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 菜单表(SysMenu) VO
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
     * 菜单类型（1-目录，2-菜单，3-按钮）
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
     * 是否可见（0-隐藏，1-显示）
     */
    private Integer isVisible;

    /**
     * 是否可见
     */
    private String isVisibleLabel;

    /**
     * 是否启用（1启用 0禁用）
     */
    private Integer isEnabled;

    /**
     * 是否启用
     */
    private String isEnabledLabel;

    /**
     * 是否外链（0-否，1-是）
     */
    private Integer isExt;

    /**
     * 打开方式（0-内嵌，1-新窗口）
     */
    private Integer openMode;

    /**
     * 子菜单
     */
    private List<SysMenuVO> children;
}
