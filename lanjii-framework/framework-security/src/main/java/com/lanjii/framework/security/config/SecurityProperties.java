package com.lanjii.framework.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Security 配置属性
 *
 * @author lanjii
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "lanjii.security")
public class SecurityProperties {
    /**
     * 白名单接口路径
     */
    private List<String> whitelist = new ArrayList<>();

    /**
     * JWT 配置
     */
    private Jwt jwt = new Jwt();

    @Data
    public static class Jwt {
        /**
         * 签名密钥
         */
        private String secret;
        /**
         * 过期时间（毫秒）
         */
        private Long expiration = 86400000L;
    }
}
