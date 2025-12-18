package com.lanjii.biz.admin.notice.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lanjii.biz.admin.notice.model.dto.NoticeDTO;
import com.lanjii.biz.admin.notice.model.vo.NoticeVO;
import com.lanjii.core.base.BaseEntity;
import com.lanjii.core.base.BaseEntityMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

/**
 * 系统通知公告表(Notice)实体类
 *
 * @author lanjii
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_notice")
public class Notice extends BaseEntity<Notice> {

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
     * 发布人姓名（冗余字段）
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
     * 逻辑删除：0-未删除, 1-已删除
     */
    @TableField("is_deleted")
    private Integer deleted;

    @Mapper
    public interface NoticeMapper extends BaseEntityMapper<Notice, NoticeVO, NoticeDTO> {
        
        @Override
        @org.mapstruct.Mapping(target = "statusLabel", expression = "java(com.lanjii.common.enums.NoticeStatusEnum.getDescByCode(entity.getStatus()))")
        NoticeVO toVo(Notice entity);
    }

    public static final NoticeMapper INSTANCE = Mappers.getMapper(NoticeMapper.class);

}
