package com.lanjii.biz.admin.notice.model.dto;

import com.lanjii.core.annotation.QueryCondition;
import com.lanjii.core.base.BaseDTO;
import com.lanjii.core.enums.QueryType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统通知公告表(Notice) DTO
 *
 * @author lanjii
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class NoticeDTO extends BaseDTO {

    /**
     * 通知ID
     */
    private Long id;

    /**
     * 通知标题
     */
    @NotBlank(message = "通知标题不能为空")
    @QueryCondition(type = QueryType.LIKE)
    private String title;

    /**
     * 通知内容（富文本HTML）
     */
    @NotBlank(message = "通知内容不能为空")
    private String content;

    /**
     * 状态：0-草稿, 1-已发布, 2-已撤回
     */
    @QueryCondition
    private Integer status;

    /**
     * 关键词搜索（标题或内容）
     */
    private String keyword;

    /**
     * 阅读状态筛选：0-未读, 1-已读
     */
    private Integer readStatus;

}
