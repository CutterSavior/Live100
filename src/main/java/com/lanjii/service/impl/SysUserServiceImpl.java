package com.lanjii.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.lanjii.core.base.BaseServiceImpl;
import com.lanjii.util.LocalCacheUtils;
import com.lanjii.util.ModelUtils;
import com.lanjii.dao.SysUserMapper;
import com.lanjii.dao.SysUserRoleRelMapper;
import com.lanjii.model.dto.SysUserDto;
import com.lanjii.model.entity.SysUser;
import com.lanjii.model.entity.SysUserRoleRel;
import com.lanjii.service.ISysConfigService;
import com.lanjii.service.ISysUserService;
import com.lanjii.model.vo.SysUserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lizheng
 * @since 2023-08-28
 */
@Slf4j
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUser> implements ISysUserService<SysUser> {

    private final SysUserMapper sysUserMapper;
    private final SysUserRoleRelMapper sysUserRoleRelMapper;
    private final SysUserRoleRelServiceImpl sysUserRoleRelService;
    private final ISysConfigService sysConfigService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SysUserServiceImpl(SysUserMapper sysUserMapper, 
                             SysUserRoleRelMapper sysUserRoleRelMapper,
                             @Lazy SysUserRoleRelServiceImpl sysUserRoleRelService,
                             ISysConfigService sysConfigService,
                             @Lazy PasswordEncoder passwordEncoder) {
        this.sysUserMapper = sysUserMapper;
        this.sysUserRoleRelMapper = sysUserRoleRelMapper;
        this.sysUserRoleRelService = sysUserRoleRelService;
        this.sysConfigService = sysConfigService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 根据账户获取用户
     *
     * @param username 账户名
     * @return 用户
     */
    @Override
    public SysUser getByUsername(String username) {
        LambdaQueryWrapper<SysUser> query = new LambdaQueryWrapper<>();
        query.eq(SysUser::getUserName, username);
        List<SysUser> sysUsers = sysUserMapper.selectList(query);
        if (CollectionUtils.isEmpty(sysUsers)) {
            return null;
        }
        return sysUsers.get(0);
    }

    @Override
    public SysUserVo getByIdNew(Long id) {
        // 先从缓存中获取
        SysUserVo cachedUser = LocalCacheUtils.get(LocalCacheUtils.CacheType.USER, String.valueOf(id));
        if (cachedUser != null) {
            return cachedUser;
        }

        SysUser sysUser = getById(id);
        SysUserVo sysUserVo = ModelUtils.copyTo(sysUser, SysUserVo.class);
        LambdaQueryWrapper<SysUserRoleRel> relQuery = new LambdaQueryWrapper<>();
        relQuery.eq(SysUserRoleRel::getUserId, id);
        List<SysUserRoleRel> rels = sysUserRoleRelMapper.selectList(relQuery);
        if (CollectionUtils.isNotEmpty(rels)) {
            List<Long> roleIds = rels.stream().map(SysUserRoleRel::getRoleId).collect(Collectors.toList());
            sysUserVo.setRoleIds(roleIds);
        }
        // 放入缓存
        LocalCacheUtils.put(LocalCacheUtils.CacheType.USER, String.valueOf(id), sysUserVo);

        return sysUserVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateNew(SysUserDto sysUserDto) {

        SysUser sysUser = ModelUtils.copyTo(sysUserDto, SysUser.class);
        if (sysUser.getId() == null) {
            sysUser.setPassword(passwordEncoder.encode(sysConfigService.getValue("DEFAULT_PASSWORD")));
        }

        // 设置管理员标志
        boolean isAdmin = CollectionUtils.isNotEmpty(sysUserDto.getRoleIds())
                && sysUserDto.getRoleIds().contains(1L);
        sysUser.setIsAdmin(isAdmin ? 1 : 0);

        this.saveOrUpdate(sysUser);

        // 删除缓存
        LocalCacheUtils.invalidate(LocalCacheUtils.CacheType.USER, String.valueOf(sysUser.getId()));


        // 处理用户角色关系
        Optional.ofNullable(sysUserDto.getRoleIds())
                .filter(CollectionUtils::isNotEmpty)
                .ifPresent(roleIds -> {
                    // 删除现有角色关系
                    LambdaQueryWrapper<SysUserRoleRel> relQuery = new LambdaQueryWrapper<>();
                    relQuery.eq(SysUserRoleRel::getUserId, sysUser.getId());
                    sysUserRoleRelMapper.delete(relQuery);

                    // 批量插入新角色关系
                    List<SysUserRoleRel> rels = roleIds.stream()
                            .map(roleId -> new SysUserRoleRel()
                                    .setUserId(sysUser.getId())
                                    .setRoleId(roleId))
                            .collect(Collectors.toList());
                    sysUserRoleRelService.saveBatch(rels);
                });

    }

    @Override
    public boolean removeById(Serializable id) {
        boolean b = SqlHelper.retBool(this.baseMapper.deleteById(id));
        if (b) {
            LocalCacheUtils.invalidate(LocalCacheUtils.CacheType.USER, String.valueOf(id));
        }
        return b;
    }

}
