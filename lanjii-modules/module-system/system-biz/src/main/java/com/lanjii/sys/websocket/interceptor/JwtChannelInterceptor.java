package com.lanjii.sys.websocket.interceptor;

import com.lanjii.sys.service.UserSessionService;
import com.lanjii.framework.security.util.JwtUtils;
import com.lanjii.framework.security.model.AuthUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * WebSocket STOMP 消息通道 JWT 拦截器
 *
 * @author lanjii
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtChannelInterceptor implements ChannelInterceptor {

    private final JwtUtils jwtUtils;
    private final UserSessionService userSessionService;
    private final UserDetailsService userDetailsService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            // 处理CONNECT命令时进行JWT认证
            String token = extractToken(accessor);

            if (token == null) {
                log.warn("WebSocket STOMP连接失败：未提供Token");
                return null; // 拒绝连接
            }

            // 验证token是否有效（与JwtAuthenticationFilter保持一致）
            if (!userSessionService.isTokenValid(token)) {
                log.warn("WebSocket STOMP连接失败：Token无效或已过期");
                return null; // 拒绝连接
            }
            String username = jwtUtils.getUsernameFromToken(token);

            AuthUser authUser = (AuthUser) userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(authUser, null, authUser.getAuthorities());

            // 将认证信息设置到STOMP会话中
            accessor.setUser(authentication);

            // 存储用户ID和token到会话属性（用于推送通知和断开连接时处理）
            accessor.getSessionAttributes().put("userId", authUser.getUserId());
            accessor.getSessionAttributes().put("username", authUser.getUsername());
            accessor.getSessionAttributes().put("token", token);

            // WebSocket连接建立时，设置会话状态为激活
            userSessionService.setSessionActive(token, true);

            log.info("WebSocket STOMP连接成功，用户：{} (ID: {})", authUser.getUsername(), authUser.getUserId());
        } else if (accessor != null && StompCommand.DISCONNECT.equals(accessor.getCommand())) {
            // 处理DISCONNECT命令时设置会话状态为非激活
            String token = (String) accessor.getSessionAttributes().get("token");
            if (token != null) {
                userSessionService.setSessionActive(token, false);
                String username = (String) accessor.getSessionAttributes().get("username");
                log.info("WebSocket STOMP断开连接，用户：{}", username);
            }
        }

        return message;
    }

    /**
     * 从STOMP header中提取JWT token
     * 支持从Authorization header中读取Bearer token
     *
     * @param accessor STOMP header访问器
     * @return JWT token，如果不存在则返回null
     */
    private String extractToken(StompHeaderAccessor accessor) {
        // 尝试从Authorization header获取
        List<String> authHeaders = accessor.getNativeHeader("Authorization");

        if (authHeaders != null && !authHeaders.isEmpty()) {
            String bearerToken = authHeaders.get(0);
            if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
                return bearerToken.substring(7);
            }
        }

        return null;
    }

}
