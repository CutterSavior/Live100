package com.lanjii.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lanjii.core.base.BaseModel;
import com.lanjii.core.base.BaseModelMapper;
import com.lanjii.model.vo.TagVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 标签实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Tag extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 标签名称
     */
    private String name;

    @Mapper
    public interface TagModelMapper extends BaseModelMapper<TagVo, Tag> {
        @Override
        TagVo modelToVo(Tag model);

    }

    public static final TagModelMapper INSTANCE = Mappers.getMapper(TagModelMapper.class);

}
