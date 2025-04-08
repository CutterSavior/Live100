package com.lanjii.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 文章和人员关联表
 *
 * @author lizheng
 * @date 2025-04-01
 */
@Data
@Accessors(chain = true)
public class NoticeUserRel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文章ID
     */
    private Long noticeId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 是否已读（0-未读 1-已读）
     */
    private Integer isRead;

}
