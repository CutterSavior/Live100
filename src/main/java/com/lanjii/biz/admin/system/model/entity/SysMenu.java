package com.lanjii.biz.admin.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lanjii.biz.admin.system.model.dto.SysMenuDTO;
import com.lanjii.biz.admin.system.model.vo.SysMenuVO;
import com.lanjii.core.base.BaseEntity;
import com.lanjii.core.base.BaseEntityMapper;
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
@EqualsAndHashCode(callSuper = true)
@TableName("sys_menu")
public class SysMenu extends BaseEntity<SysMenu> {

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

    @Mapper
    public interface SysMenuMapper extends BaseEntityMapper<SysMenu, SysMenuVO, SysMenuDTO> {

        @Mapping(target = "children", ignore = true)
        @Mapping(target = "isEnabledLabel", expression = "java(dictValueToLabel(entity.getIsEnabled(),\"STATUS\"))")
        @Mapping(target = "isVisibleLabel", expression = "java(dictValueToLabel(entity.getIsVisible(),\"VISIBLE\"))")
        SysMenuVO toVo(SysMenu entity);

        @Mapping(target = "ancestors", ignore = true)
        SysMenu toEntity(SysMenuDTO dto);

    }

    public static final SysMenuMapper INSTANCE = Mappers.getMapper(SysMenuMapper.class);

}