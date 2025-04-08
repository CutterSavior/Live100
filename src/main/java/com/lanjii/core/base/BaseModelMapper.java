package com.lanjii.core.base;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lanjii.util.SpringUtils;
import com.lanjii.model.dto.SysDictDetailDto;
import com.lanjii.model.entity.ArticleCategory;
import com.lanjii.model.entity.SysDictDetail;
import com.lanjii.service.IArticleCategoryService;
import com.lanjii.service.ISysConfigService;
import com.lanjii.service.ISysDictDetailService;
import org.mapstruct.Context;
import org.mapstruct.Named;

import java.util.List;

/**
 * Model对象到 VO 类型对象的相互转换。实现类通常声明在Model实体类中。
 *
 * @param <V> VO 实体对象类型。
 * @param <M> Model实体对象类型。
 * @author lizheng
 * @date 2024-09-12
 */
public interface BaseModelMapper<V, M> {

    V modelToVo(M model);

    List<V> modelToVo(List<M> model);

    @Named("getFileUrl")
    default String getFileUrl(String filePath) {
        ISysConfigService sysConfigService = SpringUtils.getBean(ISysConfigService.class);
        return sysConfigService.getValue("FILE_URL") + "upload/" + filePath;
    }

    default String getDictLabel(Integer dictValue, @Context String dictCode) {
        ISysDictDetailService sysDictDetailService = SpringUtils.getBean(ISysDictDetailService.class);
        SysDictDetailDto filter = new SysDictDetailDto();
        filter.setDictCode(dictCode);
        filter.setDictValue(dictValue);
        SysDictDetail dictDetail = sysDictDetailService.getOneByFilter(filter);
        return dictDetail == null ? "" : dictDetail.getDictLabel();

    }

    @Named("getCategoryName")
    default String getCategoryName(Integer categoryId) {
        IArticleCategoryService articleCategoryService = SpringUtils.getBean(IArticleCategoryService.class);
        LambdaQueryWrapper<ArticleCategory> articleCategoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleCategoryLambdaQueryWrapper.eq(ArticleCategory::getId, categoryId);
        ArticleCategory articleCategory = articleCategoryService.getOne(articleCategoryLambdaQueryWrapper);
        return articleCategory.getCategory();

    }

}
