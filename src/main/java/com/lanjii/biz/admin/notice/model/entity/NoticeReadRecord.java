package com.lanjii.biz.admin.notice.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 通知阅读记录表(NoticeReadRecord)实体类
 *
 * @author lanjii
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_notice_read_record")
public class NoticeReadRecord extends Model<NoticeReadRecord> {

    /**
     * 记录ID
     */
    private Long id;

    /**
     * 通知ID
     */
    private Long noticeId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 阅读时间
     */
    private LocalDateTime readTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
