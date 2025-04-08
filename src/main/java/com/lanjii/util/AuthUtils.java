package com.lanjii.util;

import com.lanjii.security.AuthUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtils {

    public static String getCurrentUsername() {
        return getCurrentUser().getUsername();
    }

    public static Long getCurrentUserId() {
        return getCurrentUser().getSysUser().getId();
    }

    public static AuthUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof AuthUser) {
            return (AuthUser) authentication.getPrincipal();
        }
        return null;
    }

}
