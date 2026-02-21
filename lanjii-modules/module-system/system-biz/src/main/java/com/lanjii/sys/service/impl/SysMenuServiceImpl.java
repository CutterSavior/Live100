package com.lanjii.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lanjii.sys.dao.SysMenuDao;
import com.lanjii.system.api.dto.SysMenuDTO;
import com.lanjii.sys.entity.SysMenu;
import com.lanjii.system.api.vo.SysMenuVO;
import com.lanjii.sys.service.SysMenuService;
import com.lanjii.common.enums.IsAdminEnum;
import com.lanjii.common.exception.BizException;
import com.lanjii.common.response.ResultCode;
import com.lanjii.common.util.TreeUtils;
import com.lanjii.framework.mp.base.BaseServiceImpl;
import com.lanjii.tenant.api.TenantApi;
import io.jsonwebtoken.lang.Collections;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 系统菜单表(SysMenu)表服务实现类
 *
 * @author lanjii
 */
@Service("sysMenuService")
@RequiredArgsConstructor
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuDao, SysMenu> implements SysMenuService {

    private final TenantApi tenantApi;

    @Override
    public void updateByIdNew(Long id, SysMenuDTO dto) {
        SysMenu originalMenu = getById(id);
        if (originalMenu == null) {
            throw BizException.validationError(ResultCode.NOT_FOUND, "菜单不存在");
        }

        SysMenu entity = SysMenu.INSTANCE.toEntity(dto);
        entity.setId(id);

        if (entity.getParentId() != null && !entity.getParentId().equals(originalMenu.getParentId())) {
            entity.setAncestors(buildAncestors(entity.getParentId()));
            updateDescendantsAncestors(id);
        }

        updateById(entity);
    }

    @Override
    public void saveNew(SysMenuDTO dto) {
        SysMenu entity = SysMenu.INSTANCE.toEntity(dto);
        entity.setAncestors(buildAncestors(entity.getParentId()));
        save(entity);
    }

    @Override
    public SysMenuVO getByIdNew(Long id) {
        SysMenu entity = getById(id);
        return SysMenu.INSTANCE.toVo(entity);
    }

    @Override
    public List<SysMenuVO> getUserMenuTree(Long userId, Integer isAdmin, Long tenantId, Long packageId) {
        boolean isPlatformTenant = tenantId == null || tenantId == 0;
        
        List<SysMenu> menuList;
        
        if (isPlatformTenant) {
            if (IsAdminEnum.isAdmin(isAdmin)) {
                menuList = baseMapper.selectList(new LambdaQueryWrapper<SysMenu>()
                        .ne(SysMenu::getType, 3)
                        .eq(SysMenu::getIsEnabled, 1)
                );
            } else {
                menuList = baseMapper.selectMenusByUserId(userId);
            }
        } else {
            List<Long> packageMenuIds = tenantApi.getMenuIdsByPackageId(packageId);
            if (packageMenuIds.isEmpty()) {
                return Collections.emptyList();
            }
            if (IsAdminEnum.isAdmin(isAdmin)) {
                menuList = baseMapper.selectList(new LambdaQueryWrapper<SysMenu>()
                        .in(SysMenu::getId, packageMenuIds)
                        .ne(SysMenu::getType, 3)
                        .eq(SysMenu::getIsEnabled, 1)
                );
            } else {
                menuList = baseMapper.selectMenusByUserId(userId);
                Set<Long> packageMenuIdSet = new HashSet<>(packageMenuIds);
                menuList = menuList.stream()
                        .filter(menu -> packageMenuIdSet.contains(menu.getId()))
                        .collect(Collectors.toList());
            }
        }

        List<SysMenuVO> voList = SysMenu.INSTANCE.toVo(menuList);
        return TreeUtils.buildTree(voList);
    }

    @Override
    public List<String> getUserPermissions(Long userId, Integer isAdmin, Long tenantId, Long packageId) {
        boolean isPlatformTenant = tenantId == null || tenantId == 0;
        
        List<String> permissions;
        
        if (isPlatformTenant) {
            if (IsAdminEnum.isAdmin(isAdmin)) {
                permissions = baseMapper.selectAllPermissions();
            } else {
                permissions = baseMapper.selectPermissionsByUserId(userId);
            }
        } else {
            List<Long> packageMenuIds = tenantApi.getMenuIdsByPackageId(packageId);
            if (packageMenuIds.isEmpty()) {
                return Collections.emptyList();
            }
            if (IsAdminEnum.isAdmin(isAdmin)) {
                permissions = baseMapper.selectPermissionsByMenuIds(packageMenuIds);
            } else {
                permissions = baseMapper.selectPermissionsByUserId(userId);
                List<String> packagePermissions = baseMapper.selectPermissionsByMenuIds(packageMenuIds);
                permissions.retainAll(packagePermissions);
            }
        }
        return permissions;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMenuWithChildren(Long id) {
        List<SysMenu> descendants = baseMapper.selectDescendantsByMenuId(id);

        // 批量删除所有子孙菜单
        if (!descendants.isEmpty()) {
            List<Long> descendantIds = descendants.stream()
                    .map(SysMenu::getId)
                    .toList();
            removeBatchByIds(descendantIds);
        }

        // 删除当前菜单
        removeById(id);
    }

    @Override
    public List<SysMenuVO> treeByFilterWithParent(SysMenuDTO filter) {
        long menuCount = count();
        List<SysMenu> menuList = listByFilter(filter);
        if (CollectionUtils.isEmpty(menuList)) {
            return Collections.emptyList();
        }
        Set<SysMenu> uniqueMenuSet = new HashSet<>(menuList);
        if (menuCount != menuList.size()) {
            Set<String> uniqueAncestorsSet = uniqueMenuSet.stream()
                    .map(SysMenu::getAncestors)
                    .flatMap(ancestorsStr -> Arrays.stream(ancestorsStr.split(",")))
                    .collect(Collectors.toSet());

            List<SysMenu> parentMenuList = listByIds(uniqueAncestorsSet);
            uniqueMenuSet.addAll(new HashSet<>(parentMenuList));
        }
        List<SysMenu> finalMenuList = new ArrayList<>(uniqueMenuSet);
        List<SysMenuVO> voList = SysMenu.INSTANCE.toVo(finalMenuList);
        return TreeUtils.buildTree(voList);
    }

    /**
     * 构建ancestors字符串
     *
     * @param parentId 父级菜单ID
     * @return ancestors字符串
     */
    private String buildAncestors(Long parentId) {
        if (parentId == null || parentId == 0) {
            return "0";
        }
        SysMenu parent = getById(parentId);
        if (parent == null || parent.getAncestors() == null) {
            return "0," + parentId;
        }
        return parent.getAncestors() + "," + parentId;
    }

    /**
     * 更新所有子孙节点的ancestors字段
     *
     * @param menuId 菜单ID
     */
    private void updateDescendantsAncestors(Long menuId) {
        List<SysMenu> children = baseMapper.selectChildrenByParentId(menuId);
        for (SysMenu child : children) {
            child.setAncestors(buildAncestors(child.getParentId()));
            updateById(child);
            updateDescendantsAncestors(child.getId());
        }
    }
}

