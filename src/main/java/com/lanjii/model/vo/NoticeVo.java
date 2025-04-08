package com.lanjii.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 通知Vo
 *
 * @author lizheng
 * @date 2024-11-12
 */
@Data
public class NoticeVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 通知内容
     */
    private String content;
    /**
     * 是否已读
     */
    private Integer isRead;
    /**
     * 标题
     */
    private String title;

    /**
     * 通知范围（0-所有人 1-指定人员）
     */
    private Integer toType;

    private String toTypeLabel;

    private Date createdTime;

    private String createdBy;

}
