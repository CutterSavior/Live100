package com.lanjii.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.lanjii.biz.admin.system.dao.SysMenuDao;
import com.lanjii.biz.admin.system.dao.SysUserDao;
import com.lanjii.biz.admin.system.model.entity.SysUser;
import com.lanjii.common.enums.IsAdminEnum;
import com.lanjii.common.enums.IsEnabledEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义用户认证
 *
 * @author lanjii
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SysUserDao userMapper;
    private final SysMenuDao sysMenuDao;
    private final Cache<String, AuthUser> userInfoCache;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AuthUser cachedUser = userInfoCache.getIfPresent(username);
        if (cachedUser != null) {
            return cachedUser;
        }

        SysUser user = userMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, username));
        if (user == null) {
            throw new BadCredentialsException("用户名或密码错误");
        }

        List<String> permissions;
        if (IsAdminEnum.isAdmin(user.getIsAdmin())) {
            permissions = sysMenuDao.selectAllPermissions();
        } else {
            permissions = sysMenuDao.selectPermissionsByUserId(user.getId());
        }

        List<GrantedAuthority> authorities = permissions.stream()
                .filter(StringUtils::hasText)
                .flatMap(perm -> Arrays.stream(perm.split(",")))
                .map(String::trim)
                .filter(perm -> !perm.isEmpty())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        AuthUser authUser = new AuthUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getIsAdmin(),
                authorities,
                user.getIsEnabled().equals(IsEnabledEnum.ENABLED.getCode())
        );

        userInfoCache.put(username, authUser);

        return authUser;
    }
}