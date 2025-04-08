package com.lanjii.model.dto;

import com.lanjii.core.annotation.Where;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * DTO 对象
 *
 * @author lizheng
 * @date 2024-10-09
 */
@Data
public class SysMenuDto implements Serializable {

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
    @Where(column = "type")
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
