package com.lanjii.biz.admin.log.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户操作日志VO
 *
 * @author lanjii
 */
@Data
public class SysOperLogVO {

    /**
     * 日志ID
     */
    private Long id;

    /**
     * 操作模块
     */
    private String title;

/**
     * 业务类型（0-新增，1-修改，2-删除）
     */
    private Integer businessType;

    /**
     * 方法名称
     */
    private String method;

/**
     * 请求方式（1-GET，2-POST，3-PUT，4-DELETE）
     */
    private Integer requestMethod;

    /**
     * 操作人员
     */
    private String operName;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 请求URL
     */
    private String operUrl;


    /**
     * 请求参数
     */
    private String operParam;

    /**
     * 返回参数
     */
    private String jsonResult;

/**
     * 操作状态（0-失败，1-成功）
     */
    private Integer status;

    /**
     * 错误消息
     */
    private String errorMsg;

    /**
     * 操作时间
     */
    private LocalDateTime operTime;

    /**
     * 消耗时间（毫秒）
     */
    private Long costTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
