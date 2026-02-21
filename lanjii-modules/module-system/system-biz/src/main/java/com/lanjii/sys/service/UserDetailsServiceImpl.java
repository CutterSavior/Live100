package com.lanjii.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lanjii.sys.config.SystemCacheConstants;
import com.lanjii.sys.entity.SysUser;
import com.lanjii.framework.context.tenant.TenantContext;
import com.lanjii.common.enums.IsEnabledEnum;
import com.lanjii.framework.security.model.AuthUser;
import com.lanjii.tenant.api.TenantApi;
import com.lanjii.tenant.api.vo.SysTenantVO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
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

    private final SysUserService sysUserService;
    private final SysMenuService sysMenuService;
    private final TenantApi tenantApi;
    private final CacheManager cacheManager;

    private Cache getUserInfoCache() {
        return cacheManager.getCache(SystemCacheConstants.USER_INFO.getName());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Long tenantId = TenantContext.getTenantId();

        // 获取套餐ID
        Long packageId = null;
        if (tenantId != null && tenantId > 0) {
            SysTenantVO tenant = tenantApi.getById(tenantId);
            if (tenant != null) {
                packageId = tenant.getPackageId();
            }
        }

        AuthUser cachedUser = getUserInfoCache().get(username, AuthUser.class);
        if (cachedUser != null) {
            return cachedUser;
        }

        SysUser user = sysUserService.getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, username));
        if (user == null) {
            throw new BadCredentialsException("用户名或密码错误");
        }

        // 获取用户权限
        List<String> permissions = sysMenuService.getUserPermissions(user.getId(), user.getIsAdmin(), tenantId, packageId);

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
                tenantId,
                packageId,
                authorities,
                user.getIsEnabled().equals(IsEnabledEnum.ENABLED.getCode())
        );

        getUserInfoCache().put(username, authUser);

        return authUser;
    }
}