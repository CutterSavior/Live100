package com.lanjii.system.api.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 在线用户信息VO
 *
 * @author lanjii
 */
@Data
public class OnlineUserVO {

    /**
     * 用户名
     */
    private String username;

    /**
     * 在线会话数量
     */
    private Integer sessionCount;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 客户端IP地址
     */
    private String clientIp;

    /**
     * 用户代理
     */
    private String userAgent;

    /**
     * 是否在线
     */
    private Boolean online;

    public OnlineUserVO() {
    }

    public OnlineUserVO(String username, Integer sessionCount) {
        this.username = username;
        this.sessionCount = sessionCount;
        this.online = sessionCount > 0;
    }
}
