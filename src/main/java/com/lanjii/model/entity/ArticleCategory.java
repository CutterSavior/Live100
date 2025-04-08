package com.lanjii.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lanjii.core.base.BaseModel;
import com.lanjii.core.base.BaseModelMapper;
import com.lanjii.model.vo.ArticleCategoryVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 文章类别实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ArticleCategory extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 类别名称
     */
    private String category;

    @Mapper
    public interface ArticleCategoryModelMapper extends BaseModelMapper<ArticleCategoryVo, ArticleCategory> {
        @Override
        ArticleCategoryVo modelToVo(ArticleCategory model);
    }

    public static final ArticleCategoryModelMapper INSTANCE = Mappers.getMapper(ArticleCategoryModelMapper.class);
} 