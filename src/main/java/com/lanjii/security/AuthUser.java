package com.lanjii.security;

import com.lanjii.model.entity.SysUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * 安全认证用户
 *
 * @author lizheng
 * @date 2024-10-10
 */
@Getter
public class AuthUser extends User {

    private final SysUser sysUser;

    @Setter
    private String token;

    public AuthUser(SysUser sysUser, Collection<? extends GrantedAuthority> authorities) {
        super(sysUser.getUserName(), sysUser.getPassword(), authorities);
        this.sysUser = sysUser;
    }

}
