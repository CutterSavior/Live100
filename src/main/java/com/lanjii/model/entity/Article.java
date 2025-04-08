package com.lanjii.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lanjii.core.base.BaseModel;
import com.lanjii.core.base.BaseModelMapper;
import com.lanjii.model.vo.ArticleVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 题库表
 *
 * @author lizheng
 * @date 2024-10-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Article extends BaseModel {

    private static final long serialVersionUID = 1L;


    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 分类
     */
    private Integer category;

    @Mapper
    public interface QuestionMapper extends BaseModelMapper<ArticleVo, Article> {
        @Override
        @Mapping(target = "categoryName", source = "category", qualifiedByName = "getCategoryName")
        ArticleVo modelToVo(Article model);

    }

    public static final QuestionMapper INSTANCE = Mappers.getMapper(QuestionMapper.class);

}
