package com.lanjii.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lanjii.core.base.BaseServiceImpl;
import com.lanjii.dao.SysRoleMapper;
import com.lanjii.dao.SysRoleMenuRelMapper;
import com.lanjii.model.entity.SysMenu;
import com.lanjii.model.entity.SysRole;
import com.lanjii.model.entity.SysRoleMenuRel;
import com.lanjii.service.ISysMenuService;
import com.lanjii.service.ISysRoleService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色表服务实现类
 *
 * @author lizheng
 * @date 2024-09-28
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    private final SysRoleMapper sysRoleMapper;
    private final SysRoleMenuRelMapper sysRoleMenuRelMapper;
    private final SysRoleMenuRelServiceImpl sysRoleMenuRelService;
    private final ISysMenuService sysMenuService;

    /**
     * 为角色分配菜单
     *
     * @param id      角色 ID
     * @param menuIds 菜单 ID 集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignMenu(Long id, List<Long> menuIds) {
        LambdaQueryWrapper<SysRoleMenuRel> query = new LambdaQueryWrapper<>();
        query.eq(SysRoleMenuRel::getRoleId, id);
        sysRoleMenuRelMapper.delete(query);
        if (CollectionUtils.isEmpty(menuIds)) {
            return;
        }
        List<SysRoleMenuRel> relations = menuIds.stream().map(menuId -> {
            SysRoleMenuRel rel = new SysRoleMenuRel();
            rel.setRoleId(id);
            rel.setMenuId(menuId);
            return rel;
        }).collect(Collectors.toList());
        sysRoleMenuRelService.saveBatch(relations);
    }

    /**
     * 获取角色下的所有菜单
     *
     * @param id 角色标识
     * @return 菜单标识集合
     */
    @Override
    public List<Long> listMenuIds(Long id) {
        return listMenuIds(Collections.singletonList(id));
    }

    /**
     * 获取角色下的所有菜单
     *
     * @param ids 角色标识
     * @return 菜单标识集合
     */
    @Override
    public List<Long> listMenuIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        LambdaQueryWrapper<SysRoleMenuRel> query = new LambdaQueryWrapper<>();
        // 超级管理员给全部菜单
        if (isAdmin(ids)) {
            List<SysMenu> menus = sysMenuService.list();
            return menus.stream().map(SysMenu::getId).collect(Collectors.toList());
        }
        query.in(SysRoleMenuRel::getRoleId, ids);
        List<SysRoleMenuRel> sysRoleMenuRels = sysRoleMenuRelMapper.selectList(query);
        if (CollectionUtils.isEmpty(sysRoleMenuRels)) {
            return Collections.emptyList();
        }
        return sysRoleMenuRels.stream().map(SysRoleMenuRel::getMenuId).collect(Collectors.toList());
    }

    public boolean isAdmin(SysRole role) {
        return isAdmin(Collections.singletonList(role.getId()));
    }

    public boolean isAdmin(List<Long> ids) {
        return ids.contains(1L);
    }
}
