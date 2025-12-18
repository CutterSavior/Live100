package com.lanjii.websocket.config;

import com.lanjii.websocket.interceptor.JwtChannelInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocket 配置
 * 
 * 配置WebSocket STOMP消息代理，支持：
 * - 点对点消息（用户队列）
 * - 广播消息（主题订阅）
 * - JWT身份认证
 *
 * @author lanjii
 */
@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtChannelInterceptor jwtChannelInterceptor;

    /**
     * 配置消息代理
     * - /topic 用于广播消息
     * - /queue 用于点对点消息
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 启用简单的内存消息代理，用于处理以 /topic 和 /queue 开头的消息
        registry.enableSimpleBroker("/topic", "/queue");
        
        // 配置客户端发送消息的目标前缀
        registry.setApplicationDestinationPrefixes("/app");
        
        // 配置点对点消息的用户目标前缀
        registry.setUserDestinationPrefix("/user");
    }

    /**
     * 注册STOMP端点
     * 客户端通过此端点建立WebSocket连接
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*") // 允许所有来源，生产环境应配置具体域名
                .withSockJS(); // 启用SockJS降级支持，与前端SockJS客户端匹配
    }

    /**
     * 配置客户端入站通道拦截器
     * 注册JWT认证拦截器
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(jwtChannelInterceptor);
    }
}
