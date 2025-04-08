
package com.lanjii.controller;

import com.github.pagehelper.PageHelper;
import com.lanjii.core.annotation.MultiRequestBody;
import com.lanjii.core.base.support.OrderParam;
import com.lanjii.core.base.support.PageData;
import com.lanjii.core.base.support.PageParam;
import com.lanjii.core.enums.ResultCode;
import com.lanjii.core.obj.R;
import com.lanjii.util.PageDataUtils;
import com.lanjii.model.dto.ArticleDto;
import com.lanjii.model.entity.Article;
import com.lanjii.service.IQuestionService;
import com.lanjii.model.vo.ArticleVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 角色表管理
 *
 * @author lizheng
 * @date 2024-09-28
 */
@Slf4j
@RestController
@RequestMapping("/admin/article")
@RequiredArgsConstructor
public class ArticleController {

    private final IQuestionService questionService;

    /**
     * 分页查询
     *
     * @param pageParam  分页对象
     * @param orderParam 排序对象
     * @param filter     过滤对象
     * @return 分页结果
     */
    @PostMapping("/list")
    public R<PageData<ArticleVo>> list(@MultiRequestBody PageParam pageParam, @MultiRequestBody OrderParam orderParam,
                                       @MultiRequestBody ArticleDto filter) {
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<Article> list = questionService.getListByFilter(filter, orderParam);
        return R.success(PageDataUtils.make(list, Article.INSTANCE));
    }


    /**
     * 详情
     *
     * @param id 主键
     * @return 查询对象
     */
    @GetMapping("/query")
    public R<ArticleVo> query(Long id) {
        return R.success(questionService.getByIdNew(id));
    }

    /**
     * 保存
     *
     * @param articleDto 保存的对象
     * @return 结果
     */
    @PostMapping("/save")
    public R<Article> save(@MultiRequestBody ArticleDto articleDto) {
        questionService.saveOrUpdateNew(articleDto);
        return R.success();
    }

    /**
     * 更新
     *
     * @param articleDto 更新的对象
     * @return 结果
     */
    @PostMapping("/update")
    public R<Article> update(@MultiRequestBody ArticleDto articleDto) {
        Article originalSysRole = questionService.getById(articleDto.getId());
        if (originalSysRole == null) {
            return R.fail(ResultCode.DATA_NOT_EXIST);
        }
        questionService.saveOrUpdateNew(articleDto);
        return R.success();
    }

    /**
     * 删除
     *
     * @param id 需要删除对象的主键
     * @return 结果
     */
    @GetMapping("/del")
    public R<Article> delete(Long id) {
        questionService.removeById(id);
        return R.success();
    }
}

