package com.lanjii.biz.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lanjii.biz.admin.system.dao.SysUserDao;
import com.lanjii.biz.admin.system.dao.SysUserPostDao;
import com.lanjii.biz.admin.system.dao.SysUserRoleDao;
import com.lanjii.biz.admin.system.model.dto.SysUserDTO;
import com.lanjii.biz.admin.system.model.vo.SysUserVO;
import com.lanjii.common.constant.SysConfigKeys;
import com.lanjii.core.base.BaseServiceImpl;
import com.lanjii.core.resp.ResultCode;
import com.lanjii.biz.admin.system.model.dto.UpdateProfileDTO;
import com.lanjii.biz.admin.system.model.entity.SysUser;
import com.lanjii.biz.admin.system.model.entity.SysUserPost;
import com.lanjii.biz.admin.system.model.entity.SysUserRole;
import com.lanjii.common.enums.IsEnabledEnum;
import com.lanjii.common.exception.BizException;
import com.lanjii.biz.admin.system.service.SysConfigService;
import com.lanjii.biz.admin.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统用户表(SysUser)表服务实现类
 *
 * @author lanjii
 */
@RequiredArgsConstructor
@Service("sysUserService")
public class SysUserServiceImpl extends BaseServiceImpl<SysUserDao, SysUser> implements SysUserService {

    private final SysUserPostDao sysUserPostDao;
    private final SysUserRoleDao sysUserRoleDao;
    private final SysConfigService sysConfigService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveNew(SysUserDTO dto) {
        SysUser entity = SysUser.INSTANCE.toEntity(dto);

        // 根据角色设置isAdmin字段
        setIsAdminByRoles(entity, dto.getRoleIds());
        entity.setPassword(passwordEncoder.encode("123456"));

        this.save(entity);

        // 批量处理岗位关联
        batchInsertUserPosts(entity.getId(), dto.getPostIds());

        // 批量处理角色关联
        batchInsertUserRoles(entity.getId(), dto.getRoleIds());
    }

    @Override
    public SysUserVO getByIdNew(Long id) {
        SysUser sysUser = getById(id);
        SysUserVO vo = SysUser.INSTANCE.toVo(sysUser);

        LambdaQueryWrapper<SysUserPost> sysUserPostLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysUserPostLambdaQueryWrapper.eq(SysUserPost::getUserId, id);
        List<Long> postIdList = sysUserPostDao.selectList(sysUserPostLambdaQueryWrapper)
                .stream().map(SysUserPost::getPostId).toList();
        vo.setPostIds(postIdList);

        LambdaQueryWrapper<SysUserRole> sysUserRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysUserRoleLambdaQueryWrapper.eq(SysUserRole::getUserId, id);
        List<Long> roleIdList = sysUserRoleDao.selectList(sysUserRoleLambdaQueryWrapper)
                .stream().map(SysUserRole::getRoleId).toList();
        vo.setRoleIds(roleIdList);
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateByIdNew(Long id, SysUserDTO dto) {
        SysUser originalUser = this.getById(id);
        if (originalUser == null) {
            throw BizException.validationError(ResultCode.NOT_FOUND, "用户不存在");
        }

        SysUser entity = SysUser.INSTANCE.toEntity(dto);
        entity.setId(id);
        entity.setPassword(originalUser.getPassword());

        // 根据角色设置isAdmin字段
        setIsAdminByRoles(entity, dto.getRoleIds());

        this.updateById(entity);

        deleteUserPosts(id);
        batchInsertUserPosts(id, dto.getPostIds());

        deleteUserRoles(id);
        batchInsertUserRoles(id, dto.getRoleIds());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByIdNew(Long id) {
        deleteUserPosts(id);
        deleteUserRoles(id);
        this.removeById(id);
    }

    /**
     * 根据用户名获取用户信息
     */
    public SysUser getUserByUsername(String username) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, username);
        return getOne(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(Long id) {
        SysUser user = this.getById(id);
        if (user == null) {
            throw BizException.validationError(ResultCode.NOT_FOUND, "用户不存在");
        }

        String defaultUserPwd = sysConfigService.getConfigValue(SysConfigKeys.DEFAULT_USER_PWD);
        String newPassword = passwordEncoder.encode(defaultUserPwd);
        user.setPassword(newPassword);
        this.updateById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void toggleStatus(Long id) {
        SysUser user = this.getById(id);
        if (user == null) {
            throw BizException.validationError(ResultCode.NOT_FOUND, "用户不存在");
        }

        // 切换状态：正常(1) <-> 禁用(0)
        Integer currentStatus = user.getIsEnabled();
        Integer newStatus = IsEnabledEnum.ENABLED.getCode().equals(currentStatus)
                ? IsEnabledEnum.DISABLED.getCode()
                : IsEnabledEnum.ENABLED.getCode();

        user.setIsEnabled(newStatus);
        this.updateById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateProfile(Long userId, UpdateProfileDTO dto) {
        SysUser existUser = this.getById(userId);
        if (existUser == null) {
            throw BizException.validationError(ResultCode.NOT_FOUND, "用户不存在");
        }

        SysUser updateUser = new SysUser();
        updateUser.setId(userId);
        updateUser.setNickname(dto.getNickname());
        updateUser.setEmail(dto.getEmail());
        updateUser.setPhone(dto.getPhone());
        updateUser.setAvatar(dto.getAvatar());

        this.updateById(updateUser);
    }

    /**
     * 根据角色列表设置用户的isAdmin字段
     * 如果角色包含超级管理员(id=1)，设置isAdmin为1，否则为0
     */
    private void setIsAdminByRoles(SysUser user, List<Long> roleIds) {
        if (CollectionUtils.isNotEmpty(roleIds) && roleIds.contains(1L)) {
            user.setIsAdmin(1);
        } else {
            user.setIsAdmin(0);
        }
    }

    /**
     * 批量插入用户岗位关联
     */
    private void batchInsertUserPosts(Long userId, List<Long> postIds) {
        if (CollectionUtils.isNotEmpty(postIds)) {
            List<SysUserPost> userPosts = postIds.stream()
                    .map(postId -> {
                        SysUserPost sysUserPost = new SysUserPost();
                        sysUserPost.setUserId(userId);
                        sysUserPost.setPostId(postId);
                        return sysUserPost;
                    })
                    .toList();
            userPosts.forEach(sysUserPostDao::insert);
        }
    }

    /**
     * 批量插入用户角色关联
     */
    private void batchInsertUserRoles(Long userId, List<Long> roleIds) {
        if (CollectionUtils.isNotEmpty(roleIds)) {
            List<SysUserRole> userRoles = roleIds.stream()
                    .map(roleId -> {
                        SysUserRole sysUserRole = new SysUserRole();
                        sysUserRole.setUserId(userId);
                        sysUserRole.setRoleId(roleId);
                        return sysUserRole;
                    })
                    .toList();
            userRoles.forEach(sysUserRoleDao::insert);
        }
    }

    /**
     * 删除用户的所有岗位关联
     */
    private void deleteUserPosts(Long userId) {
        LambdaQueryWrapper<SysUserPost> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserPost::getUserId, userId);
        sysUserPostDao.delete(queryWrapper);
    }

    /**
     * 删除用户的所有角色关联
     */
    private void deleteUserRoles(Long userId) {
        LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserRole::getUserId, userId);
        sysUserRoleDao.delete(queryWrapper);
    }
}

