package com.lanjii.sys.service;

import com.lanjii.sys.entity.SysRole;
import com.lanjii.system.api.dto.SysRoleDTO;
import com.lanjii.system.api.vo.SysRoleVO;
import com.lanjii.framework.mp.base.BaseService;

import java.util.List;

/**
 * 系统角色表(SysRole)表服务接口
 *
 * @author lanjii
 */
public interface SysRoleService extends BaseService<SysRole> {
    
    /**
     * 根据ID更新角色信息
     *
     * @param id  角色ID
     * @param dto 角色DTO
     */
    void updateByIdNew(Long id, SysRoleDTO dto);
    
    /**
     * 保存角色信息
     *
     * @param dto 角色DTO
     */
    void saveNew(SysRoleDTO dto);

    /**
     * 根据ID获取角色详情
     *
     * @param id 角色ID
     * @return 角色视图对象
     */
    SysRoleVO getByIdNew(Long id);

    /**
     * 获取角色已分配的菜单ID列表
     *
     * @param roleId 角色ID
     * @return 菜单ID列表
     */
    List<Long> getRoleMenuIds(Long roleId);

    /**
     * 为角色分配菜单权限
     *
     * @param roleId 角色ID
     * @param menuIds 菜单ID列表
     */
    void assignMenusToRole(Long roleId, List<Long> menuIds);
}

