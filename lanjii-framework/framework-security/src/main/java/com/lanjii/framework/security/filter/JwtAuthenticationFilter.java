package com.lanjii.framework.security.filter;

import com.lanjii.framework.context.tenant.TenantContext;
import com.lanjii.framework.context.req.BizReqContext;
import com.lanjii.framework.context.req.BizReqContextHolder;
import com.lanjii.framework.security.service.TokenService;
import com.lanjii.framework.security.util.JwtUtils;
import com.lanjii.framework.web.util.ServletUtils;
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
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final TokenService tokenService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = ServletUtils.getBearerToken();

        if (token != null && tokenService.validate(token)) {
            String username = jwtUtils.getUsernameFromToken(token);
            try {
                Long tenantId = jwtUtils.getTenantIdFromToken(token);
                TenantContext.setTenantId(tenantId);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

                BizReqContext context = BizReqContextHolder.getContext();
                context.setUsername(username);
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
                TenantContext.clear();
            }
        }
        try {
            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }
}
