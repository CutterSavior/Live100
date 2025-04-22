package com.lanjii.security;

import com.lanjii.config.support.JwtProperties;
import com.lanjii.model.entity.OnlineUser;
import com.lanjii.service.IOnlineUserService;
import com.lanjii.util.JwtTokenUtil;
import com.lanjii.util.LocalCacheUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT登录授权过滤器
 */
@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtProperties jwtProperties;
    private final ObjectProvider<IOnlineUserService> onlineUserServiceProvider;

    @Autowired
    public JwtAuthenticationTokenFilter(UserDetailsService userDetailsService,
                                       JwtTokenUtil jwtTokenUtil,
                                       JwtProperties jwtProperties,
                                       ObjectProvider<IOnlineUserService> onlineUserServiceProvider) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtProperties = jwtProperties;
        this.onlineUserServiceProvider = onlineUserServiceProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String authHeader = request.getHeader(jwtProperties.getTokenHeader());
        if (authHeader != null && authHeader.startsWith(jwtProperties.getTokenHead())) {
            String authToken = authHeader.substring(jwtProperties.getTokenHead().length());// The part after "Bearer "
            String username = jwtTokenUtil.getUserNameFromToken(authToken);
            
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // 获取在线用户服务
                IOnlineUserService onlineUserService = onlineUserServiceProvider.getIfAvailable();
                
                // 首先检查token是否存在于在线用户缓存中
                if (onlineUserService != null) {
                    // 直接用token在onlineUserService中查询，而不是先获取userId
                    OnlineUser onlineUser = LocalCacheUtils.get(LocalCacheUtils.CacheType.ONLINE_USER, authToken);
                    
                    // 如果用户不在线（被踢出或自行登出），则不进行身份验证，强制用户重新登录
                    if (onlineUser == null) {
                        log.debug("Token无效或用户[{}]已被踢出，需要重新登录", username);
                        chain.doFilter(request, response);
                        return;
                    }
                }
                
                // 用户在线，继续正常的身份验证流程
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                ((AuthUser) userDetails).setToken(authToken);
                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    
                    // 更新用户最后活动时间
                    if (onlineUserService != null) {
                        updateUserOnlineStatus(authToken, (AuthUser) userDetails, onlineUserService);
                    }
                }
            }
        }
        chain.doFilter(request, response);
    }
    
    /**
     * 更新用户在线状态和最后活动时间
     * @param token 用户token
     * @param authUser 认证用户信息
     * @param onlineUserService 在线用户服务
     */
    private void updateUserOnlineStatus(String token, AuthUser authUser, IOnlineUserService onlineUserService) {
        try {
            // 创建在线用户对象
            OnlineUser onlineUser = new OnlineUser();
            onlineUser.setToken(token);
            onlineUser.setUserid(authUser.getSysUser().getId());
            onlineUser.setUserName(authUser.getUsername());
            onlineUser.setRealName(authUser.getSysUser().getRealName());
            
            // 使用新的方法存储到缓存
            onlineUserService.putOnlineUser(onlineUser, token);
        } catch (Exception e) {
            // 捕获异常但不影响主流程
            log.error("更新在线用户状态失败", e);
        }
    }
}