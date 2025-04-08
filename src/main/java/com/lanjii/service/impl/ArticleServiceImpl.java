package com.lanjii.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lanjii.core.base.BaseServiceImpl;
import com.lanjii.util.ModelUtils;
import com.lanjii.dao.ArticleTagRelMapper;
import com.lanjii.dao.ArticleMapper;
import com.lanjii.model.dto.ArticleDto;
import com.lanjii.model.entity.Article;
import com.lanjii.model.entity.ArticleTagRel;
import com.lanjii.service.IArticleTagRelService;
import com.lanjii.service.IQuestionService;
import com.lanjii.model.vo.ArticleVo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lizheng
 * @since 2023-08-28
 */
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl extends BaseServiceImpl<ArticleMapper, Article> implements IQuestionService {

    private final ArticleMapper articleMapper;
    private final ArticleTagRelMapper articleTagRelMapper;
    private final IArticleTagRelService articleTagRelService;

    @Override
    public List<Long> allIds(String category, String tags) {
        List<String> tagList;
        if (StringUtils.isNotEmpty(tags)) {
            tagList = Arrays.stream(tags.split(",")).collect(Collectors.toList());

        } else {
            tagList = null;
        }
        return articleMapper.allIds(category, tagList);

    }

    @Override
    public List<String> allTags() {
        return articleMapper.allTags();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateNew(ArticleDto articleDto) {

        Article article = ModelUtils.copyTo(articleDto, Article.class);
        this.saveOrUpdate(article);

        LambdaQueryWrapper<ArticleTagRel> relQuery = new LambdaQueryWrapper<>();
        relQuery.eq(ArticleTagRel::getArticleId, article.getId());
        articleTagRelMapper.delete(relQuery);

        List<Long> tagIds = articleDto.getTagIds();
        if (CollectionUtils.isNotEmpty(tagIds)) {
            List<ArticleTagRel> rels = tagIds.stream().map(tagId -> {
                ArticleTagRel rel = new ArticleTagRel();
                rel.setArticleId(article.getId());
                rel.setTagId(tagId);
                return rel;
            }).collect(Collectors.toList());
            articleTagRelService.saveBatch(rels);
        }
    }

    @Override
    public ArticleVo getByIdNew(Long id) {
        Article article = getById(id);
        ArticleVo vo = ModelUtils.copyTo(article, ArticleVo.class);
        LambdaQueryWrapper<ArticleTagRel> relQuery = new LambdaQueryWrapper<>();
        relQuery.eq(ArticleTagRel::getArticleId, id);
        List<ArticleTagRel> rels = articleTagRelMapper.selectList(relQuery);
        if (CollectionUtils.isNotEmpty(rels)) {
            List<Long> tagIds = rels.stream().map(ArticleTagRel::getTagId).collect(Collectors.toList());
            vo.setTagIds(tagIds);
        }
        return vo;
    }
}
