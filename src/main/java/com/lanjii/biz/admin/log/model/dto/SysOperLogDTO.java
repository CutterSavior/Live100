package com.lanjii.biz.admin.log.model.dto;

import com.lanjii.core.annotation.QueryCondition;
import com.lanjii.core.enums.QueryType;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户操作日志DTO
 *
 * @author lanjii
 */
@Data
public class SysOperLogDTO {

    /**
     * 日志ID
     */
    private Long id;

    /**
     * 操作模块
     */
    @QueryCondition(type = QueryType.LIKE)
    private String title;

    /**
     * 业务类型（0-新增，1-修改，2-删除）
     */
    @QueryCondition
    private Integer businessType;

    /**
     * 方法名称
     */
    @QueryCondition(type = QueryType.LIKE)
    private String method;

    /**
     * 请求方式
     */
    @QueryCondition
    private String requestMethod;

    /**
     * 操作人员
     */
    @QueryCondition(type = QueryType.LIKE)
    private String operName;

    /**
     * 部门名称
     */
    @QueryCondition(type = QueryType.LIKE)
    private String deptName;

    /**
     * 请求URL
     */
    @QueryCondition(type = QueryType.LIKE)
    private String operUrl;

    /**
     * 主机地址
     */
    @QueryCondition(type = QueryType.LIKE)
    private String operIp;

    /**
     * 操作地点
     */
    @QueryCondition(type = QueryType.LIKE)
    private String operLocation;

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
    @QueryCondition
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

}
