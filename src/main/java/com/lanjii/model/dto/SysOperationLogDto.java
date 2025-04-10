package com.lanjii.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 操作日志表Dto
 *
 * @author lizheng
 * @date 2025-04-10
 */
@Data
public class SysOperationLogDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 操作描述
     */
    private String operation;

    /**
     * 日志类型
     */
    private Integer type;

    /**
     * 操作IP
     */
    private String ip;

    /**
     * 执行时长(毫秒)
     */
    private Long duration;

    /**
     * 是否成功
     */
    private Integer isSuccess;

    /**
     * 操作人
     */
    private String operationBy;

}
