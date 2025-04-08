package com.lanjii.config;

import com.lanjii.util.JwtTokenUtil;
import com.lanjii.model.entity.SysUser;
import com.lanjii.service.ISysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Component;

import java.security.Principal;

/**
 * Websocket 用户认证
 */
@Component
public class AuthChannelInterceptor implements ChannelInterceptor {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private ISysUserService sysUserService;
    @Value("${security.jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${security.jwt.tokenHead}")
    private String tokenHead;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String authHeader = accessor.getFirstNativeHeader(this.tokenHeader);
            if (StringUtils.isBlank(authHeader)) {
                throw new AuthenticationCredentialsNotFoundException("Unauthorized");
            }
            String authToken = authHeader.substring(this.tokenHead.length());//
            if (jwtTokenUtil.isTokenExpired(authToken)) {
                throw new AuthenticationCredentialsNotFoundException("Unauthorized");
            }
            String userName = jwtTokenUtil.getUserNameFromToken(authToken);
            SysUser user = sysUserService.getByUsername(userName);
            accessor.setUser(new StompPrincipal(String.valueOf(user.getId())));
        }
        return message;
    }

    // 自定义Principal实现
    private static class StompPrincipal implements Principal {
        private final String name;

        public StompPrincipal(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}