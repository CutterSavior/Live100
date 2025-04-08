package com.lanjii.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lanjii.core.base.BaseServiceImpl;
import com.lanjii.model.entity.*;
import com.lanjii.util.ModelUtils;
import com.lanjii.dao.*;
import com.lanjii.model.dto.SysMenuDto;
import com.lanjii.service.ISysMenuService;
import com.lanjii.model.vo.SysMenuVo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lizheng
 * @since 2023-08-28
 */
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    private final SysUserRoleRelMapper sysUserRoleRelMapper;
    private final SysRoleMenuRelMapper sysRoleMenuRelMapper;
    private final SysMenuResourceRelServiceImpl sysMenuResourceRelService;
    private final SysUserMapper sysUserMapper;
    private final SysMenuResourceRelMapper sysMenuResourceRelMapper;
    private final SysResourceMapper sysResourceMapper;

    @Override
    public List<SysMenu> getByUserId(Long userId) {
        return getByUserId(userId, false);
    }

    @Override
    public List<SysMenu> getByUserId(Long userId, Boolean onlyMenu) {
        SysUser sysUser = sysUserMapper.selectById(userId);
        // 查询用户的角色
        LambdaQueryWrapper<SysUserRoleRel> query = new LambdaQueryWrapper<>();
        query.eq(SysUserRoleRel::getUserId, userId);
        List<SysUserRoleRel> sysUserRoleRels = sysUserRoleRelMapper.selectList(query);
        if (CollectionUtils.isEmpty(sysUserRoleRels)) {
            return Collections.emptyList();
        }
        // 查询角色关联的菜单
        List<Long> roleIds = sysUserRoleRels.stream().map(SysUserRoleRel::getRoleId).collect(Collectors.toList());
        if (sysUser.getIsAdmin() == 1) {
            // 如果是超级管理员返回所有的菜单
            return getByAdmin(onlyMenu);
        }
        LambdaQueryWrapper<SysRoleMenuRel> roleMenuRelquery = new LambdaQueryWrapper<>();
        roleMenuRelquery.in(SysRoleMenuRel::getRoleId, roleIds);
        List<SysRoleMenuRel> sysRoleMenuRels = sysRoleMenuRelMapper.selectList(roleMenuRelquery);
        if (CollectionUtils.isEmpty(sysRoleMenuRels)) {
            return Collections.emptyList();
        }
        // 通过菜单 id 获取菜单列表
        List<Long> menuIds = sysRoleMenuRels.stream().map(SysRoleMenuRel::getMenuId).collect(Collectors.toList());
        LambdaQueryWrapper<SysMenu> menuQuery = new LambdaQueryWrapper<>();
        menuQuery.in(SysMenu::getId, menuIds);
        menuQuery.eq(SysMenu::getStatus, true);
        menuQuery.orderByAsc(SysMenu::getSort);
        return this.list(menuQuery);
    }

    @Override
    public List<String> getResourceByUserId(Long userId) {
        List<SysMenu> menus = getByUserId(userId);
        List<Long> menuIds = menus.stream().map(SysMenu::getId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(menuIds)) {
            return Collections.emptyList();
        }
        LambdaQueryWrapper<SysMenuResourceRel> wrapper = new LambdaQueryWrapper<SysMenuResourceRel>()
                .in(SysMenuResourceRel::getMenuId, menuIds);
        List<SysMenuResourceRel> sysMenuResourceRels = sysMenuResourceRelMapper.selectList(wrapper);

        List<Long> resourceIds = sysMenuResourceRels.stream().map(SysMenuResourceRel::getResourceId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(resourceIds)) {
            return Collections.emptyList();
        }
        List<SysResource> sysResources = sysResourceMapper.selectList(new LambdaQueryWrapper<SysResource>().in(SysResource::getId, resourceIds));

        return sysResources.stream().map(SysResource::getResourceUrl).collect(Collectors.toList());
    }

    @Override
    public SysMenuVo getByIdNew(Long id) {
        SysMenu menu = getById(id);
        SysMenuVo sysMenuVo = ModelUtils.copyTo(menu, SysMenuVo.class);
        LambdaQueryWrapper<SysMenuResourceRel> relQuery = new LambdaQueryWrapper<>();
        relQuery.eq(SysMenuResourceRel::getMenuId, id);
        List<SysMenuResourceRel> sysMenuResourceRels = sysMenuResourceRelMapper.selectList(relQuery);
        List<Long> resourceIds = sysMenuResourceRels.stream().map(SysMenuResourceRel::getResourceId).collect(Collectors.toList());
        sysMenuVo.setResourceIds(resourceIds);
        return sysMenuVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateNew(SysMenuDto sysMenuDto) {
        SysMenu sysMenu = ModelUtils.copyTo(sysMenuDto, SysMenu.class);
        saveOrUpdate(sysMenu);

        sysMenuResourceRelMapper.delete(new LambdaQueryWrapper<SysMenuResourceRel>().eq(SysMenuResourceRel::getMenuId, sysMenu.getId()));

        List<Long> resourceIds = sysMenuDto.getResourceIds();
        if (CollectionUtils.isEmpty(resourceIds)) {
            return;
        }
        List<SysMenuResourceRel> sysMenuResourceRels = resourceIds.stream().map(resourceId -> {
            SysMenuResourceRel sysMenuResourceRel = new SysMenuResourceRel();
            sysMenuResourceRel.setMenuId(sysMenu.getId());
            sysMenuResourceRel.setResourceId(resourceId);
            return sysMenuResourceRel;
        }).collect(Collectors.toList());
        sysMenuResourceRelService.saveBatch(sysMenuResourceRels);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByIdNew(Long id) {
        removeById(id);
        sysMenuResourceRelMapper.delete(new LambdaQueryWrapper<SysMenuResourceRel>().eq(SysMenuResourceRel::getMenuId, id));
    }

    @Override
    public List<SysMenu> getByUsername(String username) {
        LambdaQueryWrapper<SysUser> query = new LambdaQueryWrapper<>();
        query.eq(SysUser::getUserName, username);
        SysUser sysUser = sysUserMapper.selectOne(query);
        return getByUserId(sysUser.getId());
    }

    /**
     * 获取菜单
     *
     * @param onlyMenu 是否只获取菜单，不获取按钮
     * @return
     */
    private List<SysMenu> getByAdmin(boolean onlyMenu) {
        LambdaQueryWrapper<SysMenu> query = new LambdaQueryWrapper<>();
        if (onlyMenu) {
            query.in(SysMenu::getType, 1, 3);
        }
        query.orderByAsc(SysMenu::getSort);
        query.eq(SysMenu::getStatus, true);
        return list(query);
    }
}
