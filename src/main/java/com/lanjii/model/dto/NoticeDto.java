package com.lanjii.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 通知Dto
 *
 * @author lizheng
 * @date 2024-11-12
 */
@Data
public class NoticeDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 接收人
     */
    private Long id;

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

    /**
     * 接收id
     */
    private List<Long> toIds;


}
