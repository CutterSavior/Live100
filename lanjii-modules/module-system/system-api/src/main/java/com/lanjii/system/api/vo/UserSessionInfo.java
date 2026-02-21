package com.lanjii.system.api.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 用户会话信息
 *
 * @author lanjii
 */
@Data
public class UserSessionInfo {

    /**
     * JWT Token
     */
    @JsonIgnore
    private String token;

    /**
     * 脱敏后的Token
     */
    private String maskedToken;

    /**
     * 会话标识
     */
    private String sessionId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 会话状态：true-有效，false-已踢出
     */
    private Boolean active;

    /**
     * 设备类型（PC、Mobile、Tablet等）
     */
    private String deviceType;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 最后访问时间
     */
    private LocalDateTime lastAccessTime;

    /**
     * 客户端IP
     */
    private String clientIp;

    /**
     * 用户代理
     */
    private String userAgent;

    /**
     * 显示用的UUID，用于前端识别自己的会话
     */
    private String displayUuid;

    public UserSessionInfo() {
        this.active = true;
        this.createTime = LocalDateTime.now();
        this.lastAccessTime = LocalDateTime.now();
    }

    public UserSessionInfo(String token, String username) {
        this();
        this.token = token;
        this.username = username;
        this.displayUuid = UUID.randomUUID().toString();
    }

    /**
     * 标记为已踢出
     */
    public void markAsKicked() {
        this.active = false;
    }

    /**
     * 对token进行脱敏处理
     * 显示格式：前6位 + **** + 后4位
     */
    public void maskToken() {
        if (this.token == null || this.token.length() <= 10) {
            this.maskedToken = "****";
            this.sessionId = String.valueOf(System.identityHashCode(this));
            return;
        }
        
        String prefix = this.token.substring(0, 6);
        String suffix = this.token.substring(this.token.length() - 4);
        this.maskedToken = prefix + "****" + suffix;
        this.sessionId = String.valueOf(this.token.hashCode());
    }

}
