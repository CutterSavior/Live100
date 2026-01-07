package com.lanjii.common.util;

import com.lanjii.security.AuthUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Spring Security 相关便捷方法
 */
public final class SecurityUtils {

    private SecurityUtils() {
    }

    /**
     * 获取当前 Authentication，未认证时返回 null
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

/**
     * 获取当前登录用户（自定义 AuthUser），不可用或未登录返回 null
     */
    public static AuthUser getCurrentUser() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof AuthUser) {
            return (AuthUser) principal;
        }
        return null;
    }

    /**
     * 获取当前登录用户名，未登录返回 null
     */
    public static String getCurrentUsername() {
        AuthUser currentUser = getCurrentUser();
        if (currentUser == null) {
            return null;
        }
        return currentUser.getUsername();
    }

    /**
     * 获取当前登录用户ID，未登录返回 null
     */
    public static Long getCurrentUserId() {
        AuthUser currentUser = getCurrentUser();
        if (currentUser == null) {
            return null;
        }
        return currentUser.getUserId();
    }

}


