package com.lanjii.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * VO 对象
 *
 * @author lizheng
 * @date 2024-10-09
 */
@Data
public class SysMenuVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 菜单名
     */
    private String name;

    /**
     * 父菜单
     */
    private Long parentId;

    /**
     * 图标
     */
    private String icon;

    /**
     * 路由名称
     */
    private String routeName;

    /**
     * 路由路径
     */
    private String routePath;

    /**
     * 外部链接
     */
    private String extLink;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 菜单类型 （1 - 菜单 、2 - 按钮、3 - 外链）
     */
    private Integer type;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 资源列表
     */
    private List<Long> resourceIds;

}
