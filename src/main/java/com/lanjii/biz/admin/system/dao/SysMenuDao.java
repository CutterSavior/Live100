package com.lanjii.biz.admin.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lanjii.biz.admin.system.model.entity.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统菜单表(SysMenu)数据访问层
 *
 * @author lanjii
 */
public interface SysMenuDao extends BaseMapper<SysMenu> {

    /**
     * 查询所有权限字符
     *
     * @return 权限字符列表
     */
    List<String> selectAllPermissions();

    /**
     * 根据用户ID查询权限字符
     *
     * @param userId 用户ID
     * @return 权限字符列表
     */
    List<String> selectPermissionsByUserId(@Param("userId") Long userId);

    /**
     * 根据用户ID查询菜单列表
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<SysMenu> selectMenusByUserId(@Param("userId") Long userId);

    /**
     * 根据父级ID查询子菜单
     *
     * @param parentId 父级菜单ID
     * @return 子菜单列表
     */
    List<SysMenu> selectChildrenByParentId(@Param("parentId") Long parentId);

    /**
     * 根据菜单ID查询所有子孙菜单（使用ancestors字段）
     *
     * @param menuId 菜单ID
     * @return 所有子孙菜单列表
     */
    List<SysMenu> selectDescendantsByMenuId(@Param("menuId") Long menuId);

}