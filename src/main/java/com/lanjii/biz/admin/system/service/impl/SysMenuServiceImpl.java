package com.lanjii.biz.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lanjii.biz.admin.system.dao.SysMenuDao;
import com.lanjii.biz.admin.system.model.dto.SysMenuDTO;
import com.lanjii.biz.admin.system.model.vo.SysMenuVO;
import com.lanjii.core.base.BaseServiceImpl;
import com.lanjii.core.resp.ResultCode;
import com.lanjii.biz.admin.system.model.entity.SysMenu;
import com.lanjii.common.enums.IsAdminEnum;
import com.lanjii.common.exception.BizException;
import com.lanjii.biz.admin.system.service.SysMenuService;
import com.lanjii.common.util.TreeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统菜单表(SysMenu)表服务实现类
 *
 * @author lanjii
 */
@Service("sysMenuService")
@RequiredArgsConstructor
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuDao, SysMenu> implements SysMenuService {

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
    public List<SysMenuVO> getUserMenuTree(Long userId, Integer isAdmin) {
        List<SysMenu> menuList;
        if (IsAdminEnum.isAdmin(isAdmin)) {
            // 如果是管理员用户，获取所有菜单
            menuList = baseMapper.selectList(new LambdaQueryWrapper<SysMenu>()
                    .ne(SysMenu::getType, 3)
                    .eq(SysMenu::getIsEnabled, 1)
            );
        } else {
            // 普通用户按原有逻辑查询
            menuList = baseMapper.selectMenusByUserId(userId);
        }
        List<SysMenuVO> voList = SysMenu.INSTANCE.toVo(menuList);
        return TreeUtils.buildTree(voList);
    }

    @Override
    public List<String> getUserPermissions(Long userId, Integer isAdmin) {
        if (IsAdminEnum.isAdmin(isAdmin)) {
            // 如果是管理员用户，获取所有权限
            return baseMapper.selectAllPermissions();
        } else {
            // 普通用户获取其角色对应的权限
            return baseMapper.selectPermissionsByUserId(userId);
        }
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

