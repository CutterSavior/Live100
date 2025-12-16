package com.lanjii.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * 自定义User
 *
 * @author lanjii
 */
@Data
public class AuthUser implements UserDetails {

    private final Long userId;
    private final String username;
    private final String password;
    private final Integer isAdmin;
    private final List<GrantedAuthority> authorities;
    private final boolean enabled;


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}