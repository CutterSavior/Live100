package com.lanjii.system.api.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户登录日志VO
 *
 * @author lanjii
 */
@Data
public class SysLoginLogVO {

    /**
     * 日志ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 登录IP地址
     */
    private String ipAddress;

    /**
     * 登录地点
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 登录类型（0-登录，1-登出）
     */
    private Integer loginType;

    /**
     * 登录状态（0-失败，1-成功）
     */
    private Integer status;

    /**
     * 提示消息
     */
    private String msg;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
