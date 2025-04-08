package com.lanjii.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lanjii.core.base.BaseModel;
import com.lanjii.core.base.BaseModelMapper;
import com.lanjii.model.vo.NoticeVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 通知
 *
 * @author lizheng
 * @date 2024-11-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Notice extends BaseModel {

    public static final NoticeModelMapper INSTANCE = Mappers.getMapper(NoticeModelMapper.class);
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
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

    @Mapper
    public interface NoticeModelMapper extends BaseModelMapper<NoticeVo, Notice> {

        @Override
        @Mapping(target = "toTypeLabel", expression = "java(getDictLabel(model.getToType(),\"NOTICE_TO_TYPE\"))")
        NoticeVo modelToVo(Notice model);

    }

}
