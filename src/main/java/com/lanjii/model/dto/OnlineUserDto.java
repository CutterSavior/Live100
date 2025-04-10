package com.lanjii.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 在线用户Dto
 *
 * @author lizheng
 * @date 2025-04-09
 */
@Data
public class OnlineUserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * ip地址
     */
    private String ipAddress;

    /**
     * 最后访问时间
     */
    private Date lastActiveTime;

    /**
     * 位置
     */
    private String location;

    /**
     * 在线状态
     */
    private Integer onlineStatus;

    /**
     * 姓名
     */
    private String realName;

    /**
     * jwt生成的token
     */
    private String token;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户ID
     */
    private Long userid;


}
