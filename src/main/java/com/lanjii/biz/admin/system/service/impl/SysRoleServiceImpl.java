package com.lanjii.biz.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lanjii.biz.admin.system.dao.SysMenuDao;
import com.lanjii.biz.admin.system.dao.SysRoleDao;
import com.lanjii.biz.admin.system.dao.SysRoleMenuDao;
import com.lanjii.biz.admin.system.model.dto.SysRoleDTO;
import com.lanjii.biz.admin.system.model.vo.SysRoleVO;
import com.lanjii.core.base.BaseServiceImpl;
import com.lanjii.core.resp.ResultCode;
import com.lanjii.biz.admin.system.model.entity.SysMenu;
import com.lanjii.biz.admin.system.model.entity.SysRole;
import com.lanjii.biz.admin.system.model.entity.SysRoleMenu;
import com.lanjii.common.exception.BizException;
import com.lanjii.biz.admin.system.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色表(SysRole)表服务实现类
 *
 * @author lanjii
 */
@Service("sysRoleService")
@RequiredArgsConstructor
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleDao, SysRole> implements SysRoleService {

    private final SysRoleMenuDao sysRoleMenuDao;
    private final SysMenuDao sysMenuDao;

    @Override
    public void updateByIdNew(Long id, SysRoleDTO roleDTO) {
        SysRole existingRole = getById(id);
        if (existingRole == null) {
            throw BizException.validationError(ResultCode.NOT_FOUND, "角色不存在");
        }

        if (StringUtils.hasText(roleDTO.getName()) &&
                !roleDTO.getName().equals(existingRole.getName())) {
            LambdaQueryWrapper<SysRole> nameQuery = new LambdaQueryWrapper<SysRole>()
                    .eq(SysRole::getName, roleDTO.getName())
                    .ne(SysRole::getId, id);
            if (baseMapper.exists(nameQuery)) {
                throw BizException.validationError(ResultCode.CONFLICT, "角色名称已存在");
            }
        }

        if (StringUtils.hasText(roleDTO.getCode()) &&
                !roleDTO.getCode().equals(existingRole.getCode())) {
            LambdaQueryWrapper<SysRole> codeQuery = new LambdaQueryWrapper<SysRole>()
                    .eq(SysRole::getCode, roleDTO.getCode())
                    .ne(SysRole::getId, id);
            if (baseMapper.exists(codeQuery)) {
                throw BizException.validationError(ResultCode.CONFLICT, "角色编码已存在");
            }
        }

        SysRole entity = SysRole.INSTANCE.toEntity(roleDTO);
        updateById(entity);
    }

    @Override
    public void saveNew(SysRoleDTO roleDTO) {
        LambdaQueryWrapper<SysRole> nameQuery = new LambdaQueryWrapper<SysRole>()
                .eq(SysRole::getName, roleDTO.getName());
        if (baseMapper.exists(nameQuery)) {
            throw BizException.validationError(ResultCode.CONFLICT, "角色名称已存在");
        }

        LambdaQueryWrapper<SysRole> codeQuery = new LambdaQueryWrapper<SysRole>()
                .eq(SysRole::getCode, roleDTO.getCode());
        if (baseMapper.exists(codeQuery)) {
            throw BizException.validationError(ResultCode.CONFLICT, "角色编码已存在");
        }
        SysRole entity = SysRole.INSTANCE.toEntity(roleDTO);
        save(entity);
    }

    @Override
    public SysRoleVO getByIdNew(Long id) {
        SysRole entity = getById(id);
        return SysRole.INSTANCE.toVo(entity);
    }

    @Override
    public List<Long> getRoleMenuIds(Long roleId) {
        // 验证角色是否存在
        validateRoleExists(roleId);

        // 查询角色已分配的菜单ID列表
        LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleMenu::getRoleId, roleId);
        List<SysRoleMenu> roleMenuList = sysRoleMenuDao.selectList(queryWrapper);

        // 提取菜单ID列表
        return roleMenuList.stream()
                .map(SysRoleMenu::getMenuId)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignMenusToRole(Long roleId, List<Long> menuIds) {
        // 验证角色是否存在
        validateRoleExists(roleId);

        // 验证菜单ID是否都存在
        if (menuIds != null && !menuIds.isEmpty()) {
            List<SysMenu> menus = sysMenuDao.selectByIds(menuIds);
            if (menus.size() != menuIds.size()) {
                throw BizException.validationError(ResultCode.BAD_REQUEST, "部分菜单不存在");
            }
        }

        // 删除该角色原有的所有菜单关联关系
        LambdaQueryWrapper<SysRoleMenu> deleteWrapper = new LambdaQueryWrapper<>();
        deleteWrapper.eq(SysRoleMenu::getRoleId, roleId);
        sysRoleMenuDao.delete(deleteWrapper);

        // 批量插入新的角色-菜单关联关系
        if (menuIds != null && !menuIds.isEmpty()) {
            List<SysRoleMenu> roleMenuList = menuIds.stream()
                    .map(menuId -> {
                        SysRoleMenu roleMenu = new SysRoleMenu();
                        roleMenu.setRoleId(roleId);
                        roleMenu.setMenuId(menuId);
                        return roleMenu;
                    })
                    .collect(Collectors.toList());
            // 使用批量插入
            roleMenuList.forEach(sysRoleMenuDao::insert);
        }
    }

    /**
     * 验证角色是否存在
     */
    private void validateRoleExists(Long roleId) {
        SysRole role = getById(roleId);
        if (role == null) {
            throw BizException.validationError(ResultCode.NOT_FOUND, "角色不存在");
        }
    }
}
