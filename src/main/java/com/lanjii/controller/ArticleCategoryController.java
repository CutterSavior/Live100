package com.lanjii.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.lanjii.core.annotation.MultiRequestBody;
import com.lanjii.core.base.support.OrderParam;
import com.lanjii.core.base.support.PageData;
import com.lanjii.core.base.support.PageParam;
import com.lanjii.core.enums.ResultCode;
import com.lanjii.core.obj.R;
import com.lanjii.util.ModelUtils;
import com.lanjii.util.PageDataUtils;
import com.lanjii.model.dto.ArticleCategoryDto;
import com.lanjii.model.entity.ArticleCategory;
import com.lanjii.service.IArticleCategoryService;
import com.lanjii.model.vo.ArticleCategoryVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 文章类别Controller
 */
@Slf4j
@RestController
@RequestMapping("/admin/article-category")
@RequiredArgsConstructor
public class ArticleCategoryController {

    private final IArticleCategoryService articleCategoryService;

    /**
     * 列表
     */
    @PostMapping("/list")
    public R<PageData<ArticleCategoryVo>> list(@MultiRequestBody(required = false) PageParam pageParam, @MultiRequestBody(required = false) OrderParam orderParam,
                                               @MultiRequestBody(required = false) ArticleCategoryDto articleCategoryFilter) {
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<ArticleCategory> list = articleCategoryService.getListByFilter(articleCategoryFilter, orderParam);
        return R.success(PageDataUtils.make(list, ArticleCategory.INSTANCE));
    }

    /**
     * 详情
     */
    @GetMapping("/query")
    public R<ArticleCategory> query(Long id) {
        return R.success(articleCategoryService.getById(id));
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R<ArticleCategory> save(@MultiRequestBody ArticleCategoryDto articleCategoryDto) {

        String articleCategoryName = articleCategoryDto.getCategory();
        LambdaQueryWrapper<ArticleCategory> query = new LambdaQueryWrapper<>();
        query.eq(ArticleCategory::getCategory, articleCategoryName);
        if (articleCategoryService.count(query) > 0) {
            return R.fail("类别已存在");
        }
        ArticleCategory articleCategory = ModelUtils.copyTo(articleCategoryDto, ArticleCategory.class);
        articleCategoryService.saveOrUpdate(articleCategory);
        return R.success();
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    public R<ArticleCategory> update(@MultiRequestBody ArticleCategoryDto articleCategoryDto) {
        ArticleCategory originalData = articleCategoryService.getById(articleCategoryDto.getId());
        if (originalData == null) {
            return R.fail(ResultCode.DATA_NOT_EXIST);
        }
        
        // 检查分类名称是否与其他分类重复
        if (!originalData.getCategory().equals(articleCategoryDto.getCategory())) {
            LambdaQueryWrapper<ArticleCategory> query = new LambdaQueryWrapper<>();
            query.eq(ArticleCategory::getCategory, articleCategoryDto.getCategory());
            if (articleCategoryService.count(query) > 0) {
                return R.fail("分类名称已存在");
            }
        }
        
        ArticleCategory articleCategory = ModelUtils.copyTo(articleCategoryDto, ArticleCategory.class);
        articleCategoryService.saveOrUpdate(articleCategory);
        return R.success();
    }

    /**
     * 删除
     */
    @GetMapping("/del")
    public R<ArticleCategory> delete(Long id) {
        articleCategoryService.removeById(id);
        return R.success();
    }

} 