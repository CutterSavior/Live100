package com.lanjii.service;

import com.lanjii.core.base.IBaseService;
import com.lanjii.model.entity.SysRole;

import java.util.List;

/**
 * 角色表服务类
 *
 * @author lizheng
 * @date 2024-09-28
 */
public interface ISysRoleService extends IBaseService<SysRole> {

    /**
     * 为角色分配菜单
     *
     * @param id      角色 ID
     * @param menuIds 菜单 ID 集合
     */
    void assignMenu(Long id, List<Long> menuIds);

    /**
     * 获取角色下的所有菜单
     *
     * @param id 角色标识
     * @return 菜单标识集合
     */
    List<Long> listMenuIds(Long id);

    /**
     * 获取角色下的所有菜单
     *
     * @param ids 角色标识集合
     * @return 菜单标识集合
     */
    List<Long> listMenuIds(List<Long> ids);
}