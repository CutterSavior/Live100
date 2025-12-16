package com.lanjii.biz.admin.log.model.dto;

import com.lanjii.core.annotation.QueryCondition;
import com.lanjii.core.enums.QueryType;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 用户登录日志DTO
 *
 * @author lanjii
 */
@Data
public class SysLoginLogDTO {

    /**
     * 日志ID
     */
    private Long id;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @QueryCondition(type = QueryType.LIKE)
    private String username;

    /**
     * 登录IP地址
     */
    @QueryCondition(type = QueryType.LIKE)
    private String ipAddress;

    /**
     * 登录地点
     */
    @QueryCondition(type = QueryType.LIKE)
    private String loginLocation;

    /**
     * 浏览器类型
     */
    @QueryCondition(type = QueryType.LIKE)
    private String browser;

    /**
     * 操作系统
     */
    @QueryCondition(type = QueryType.LIKE)
    private String os;

    /**
     * 登录类型（0-登录，1-登出）
     */
    @QueryCondition
    private Integer loginType;

    /**
     * 登录状态（0-失败，1-成功）
     */
    @NotNull(message = "登录状态不能为空")
    @QueryCondition
    private Integer status;

    /**
     * 提示消息
     */
    private String msg;

    /**
     * 登录时间
     */
    private java.time.LocalDateTime loginTime;

}
