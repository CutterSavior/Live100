package com.lanjii.service;

import com.lanjii.core.base.IBaseService;
import com.lanjii.model.dto.ArticleDto;
import com.lanjii.model.entity.Article;
import com.lanjii.model.vo.ArticleVo;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author lizheng
 * @since 2023-08-28
 */
public interface IQuestionService extends IBaseService<Article> {

    List<Long> allIds(String category, String tags);

    List<String> allTags();

    void saveOrUpdateNew(ArticleDto articleDto);

    ArticleVo getByIdNew(Long id);
}
