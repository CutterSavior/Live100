package com.lanjii.biz.admin.system.service;

import com.lanjii.biz.admin.system.model.dto.SysMenuDTO;
import com.lanjii.biz.admin.system.model.entity.SysMenu;
import com.lanjii.biz.admin.system.model.vo.SysMenuVO;
import com.lanjii.core.base.BaseService;

import java.util.List;

/**
 * 系统菜单表(SysMenu)表服务接口
 *
 * @author lanjii
 */
public interface SysMenuService extends BaseService<SysMenu> {

    /**
     * 根据ID更新菜单信息
     *
     * @param id  菜单ID
     * @param dto 菜单DTO
     */
    void updateByIdNew(Long id, SysMenuDTO dto);

    /**
     * 保存菜单信息
     *
     * @param dto 菜单DTO
     */
    void saveNew(SysMenuDTO dto);

    /**
     * 根据ID获取菜单详情
     *
     * @param id 菜单ID
     * @return 菜单视图对象
     */
    SysMenuVO getByIdNew(Long id);

    /**
     * 获取当前用户的菜单树
     *
     * @param userId  用户ID
     * @param isAdmin 是否管理员
     * @return 菜单树
     */
    List<SysMenuVO> getUserMenuTree(Long userId, Integer isAdmin);

    /**
     * 获取当前用户的权限字符列表
     *
     * @param userId  用户ID
     * @param isAdmin 是否管理员
     * @return 权限字符列表
     */
    List<String> getUserPermissions(Long userId, Integer isAdmin);

    /**
     * 递归删除菜单及其所有子菜单
     *
     * @param id 菜单ID
     */
    void deleteMenuWithChildren(Long id);

    /**
     * 获取携带父节点的菜单树
     *
     * @param filter 过滤对象
     * @return 菜单树
     */
    List<SysMenuVO> treeByFilterWithParent(SysMenuDTO filter);
}

