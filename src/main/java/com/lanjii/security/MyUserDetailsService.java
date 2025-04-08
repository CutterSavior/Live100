package com.lanjii.security;

import com.lanjii.core.enums.ResultCode;
import com.lanjii.util.LocalCacheUtils;
import com.lanjii.model.entity.SysUser;
import com.lanjii.service.ISysMenuService;
import com.lanjii.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final ISysUserService sysUserService;
    private final ISysMenuService sysMenuService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Object authUserObj = LocalCacheUtils.get(LocalCacheUtils.CacheType.OTHER, "auth:" + username);
        if (authUserObj != null) {
            AuthUser authUser = (AuthUser) authUserObj;
            return new AuthUser(authUser.getSysUser(), authUser.getAuthorities());
        }
        SysUser user = sysUserService.getByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(ResultCode.WRONG_USERNAME_OR_PASSWORD.getMsg());
        }
        List<String> resources = sysMenuService.getResourceByUserId(user.getId());
        List<SimpleGrantedAuthority> authorities = resources.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        AuthUser newAuthUser = new AuthUser(user, authorities);
        LocalCacheUtils.put(LocalCacheUtils.CacheType.OTHER, "auth:" + username, newAuthUser);
        return newAuthUser;
    }
}
