package com.lanjii.service.impl;

import com.lanjii.core.base.BaseServiceImpl;
import com.lanjii.model.entity.ArticleCategory;
import com.lanjii.dao.ArticleCategoryMapper;
import com.lanjii.service.IArticleCategoryService;
import org.springframework.stereotype.Service;

/**
 * 文章类别Service实现类
 */
@Service
public class ArticleCategoryServiceImpl extends BaseServiceImpl<ArticleCategoryMapper, ArticleCategory> implements IArticleCategoryService {
} 