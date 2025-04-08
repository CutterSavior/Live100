
package com.lanjii.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lanjii.model.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 题库Mapper 接口
 * </p>
 *
 * @author lizheng
 * @since 2023-08-28
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    @Select("<script>select id from article where deleted = 0 " +
            "<when test='category !=null and category !=\"\"'> " +
            " and category = (#{category})" +
            "</when>" +
            "<when test='tags !=null'> " +
            " and tag in (" +
            "<foreach collection='tags' separator=',' item='tag'>" +
            " #{tag}" +
            "</foreach> " +
            ")" +
            "</when>" +
            "</script>")
    List<Long> allIds(@Param(value = "category") String category, @Param(value = "tags") List<String> tags);

    @Select("select distinct tag from article where deleted = 0")
    List<String> allTags();
}
