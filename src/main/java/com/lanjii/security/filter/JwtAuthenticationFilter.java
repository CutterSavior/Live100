package com.lanjii.security.filter;

import com.lanjii.biz.admin.monitor.service.UserSessionService;
import com.lanjii.common.util.JwtUtils;
import com.lanjii.common.util.WebContextUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT认证过滤器
 *
 * @author lanjii
 */
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final UserSessionService userSessionService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = WebContextUtils.getBearerToken();

        if (token != null && userSessionService.isTokenValid(token)) {
            String username = jwtUtils.getUsernameFromToken(token);
            try {
                // 加载用户的完整权限信息
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                // 如果加载用户信息失败，清除认证信息
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(request, response);
    }

}