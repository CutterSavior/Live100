package com.lanjii.config;

import com.lanjii.core.constant.WsConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * websocket 配置
 * <p>
 * 使用 Spring Security 可以直接配置 AbstractSecurityWebSocketMessageBrokerConfigurer
 *
 * @author LiZheng
 * @date 2024-11-12
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private AuthChannelInterceptor authChannelInterceptor;

    /**
     * 注册stomp端点
     *
     * @param registry stomp端点注册对象
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(WsConstants.WEBSOCKET_PATH)
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(authChannelInterceptor);
    }

    /**
     * 配置消息代理
     *
     * @param registry 消息代理注册对象
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 配置服务端推送消息给客户端的代理路径
        registry.enableSimpleBroker(WsConstants.BROKER_QUEUE, WsConstants.BROKER_TOPIC);
        registry.setUserDestinationPrefix(WsConstants.BROKER_USER);
        registry.setApplicationDestinationPrefixes(WsConstants.WS_PERFIX);
    }

}
