package com.lanjii.common.base;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * VO基础类
 *
 * @author lanjii
 */
@Data
public class BaseVO {

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新人
     */
    private String updateBy;

}
