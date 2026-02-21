package com.lanjii.system.api.vo;

import com.lanjii.common.base.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 系统通知公告表(Notice) VO
 *
 * @author lanjii
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class NoticeVO extends BaseVO {

    /**
     * 通知ID
     */
    private Long id;

    /**
     * 通知标题
     */
    private String title;

    /**
     * 通知内容（富文本HTML）
     */
    private String content;

    /**
     * 发布人ID
     */
    private Long publisherId;

    /**
     * 发布人姓名
     */
    private String publisherName;

    /**
     * 发布时间
     */
    private LocalDateTime publishTime;

    /**
     * 状态：0-草稿, 1-已发布
     */
    private Integer status;

    /**
     * 状态标签（中文）
     */
    private String statusLabel;

    /**
     * 阅读状态：0-未读, 1-已读
     */
    private Integer readStatus;

    /**
     * 阅读时间
     */
    private LocalDateTime readTime;

}
