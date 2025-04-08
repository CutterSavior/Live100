package com.lanjii.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lanjii.model.entity.ArticleCategory;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章类别Mapper
 */
@Mapper
public interface ArticleCategoryMapper extends BaseMapper<ArticleCategory> {
} 