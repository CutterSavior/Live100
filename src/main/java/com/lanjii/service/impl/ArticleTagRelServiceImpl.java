
package com.lanjii.service.impl;

import com.lanjii.core.base.BaseServiceImpl;
import com.lanjii.dao.ArticleTagRelMapper;
import com.lanjii.model.entity.ArticleTagRel;
import com.lanjii.service.IArticleTagRelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 文章标签关联表 服务实现类
 *
 * @author lizheng
 * @date 2024-11-01
 */
@Service
@RequiredArgsConstructor
public class ArticleTagRelServiceImpl extends BaseServiceImpl<ArticleTagRelMapper, ArticleTagRel> implements IArticleTagRelService {

}
