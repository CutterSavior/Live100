package com.lanjii.config.support;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * JWT配置属性类
 *
 * @author lizheng
 * @date 2024-10-10
 */
@Data
@ConfigurationProperties(prefix = "security.jwt")
public class JwtProperties {
    /**
     * JWT存储的请求头
     */
    private String tokenHeader;
    
    /**
     * JWT加解密使用的密钥
     */
    private String secret;
    
    /**
     * JWT的超期限时间(秒)
     */
    private Long expiration;
    
    /**
     * JWT负载中拿到开头
     */
    private String tokenHead;
} 