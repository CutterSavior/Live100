package com.lanjii.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lanjii.system.api.dto.SysMenuDTO;
import com.lanjii.system.api.vo.SysMenuVO;
import com.lanjii.framework.mp.base.BaseEntity;
import com.lanjii.sys.base.AdminBaseMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 菜单表(SysMenu)表实体类
 *
 * @author lanjii
 */
@Data
@TableName("sys_menu")
@EqualsAndHashCode(of = "id", callSuper = false)
public class SysMenu extends BaseEntity {

    /**
     * 菜单ID
     */
    private Long id;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 祖级列表(逗号分隔)
     */
    private String ancestors;

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
     * 是否启用（1启用 0禁用）
     */
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

    /**
     * 显示范围（0-全部，1-租户，2-平台）
     */
    private Integer scope;

    @Mapper
    public interface SysMenuMapper extends AdminBaseMapper<SysMenu, SysMenuVO, SysMenuDTO> {

        @Mapping(target = "children", ignore = true)
        SysMenuVO toVo(SysMenu entity);

        @Mapping(target = "ancestors", ignore = true)
        SysMenu toEntity(SysMenuDTO dto);

    }

    public static final SysMenuMapper INSTANCE = Mappers.getMapper(SysMenuMapper.class);

}
