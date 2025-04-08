package com.lanjii.model.dto;

import com.lanjii.core.annotation.Where;
import com.lanjii.core.enums.Condition;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 题库表
 *
 * @author lizheng
 * @date 2024-10-08
 */
@Data
public class ArticleDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 标题
     */
    @Where(column = "title", value = Condition.LIKE)
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 分类
     */
    private Integer category;

    /**
     * 标签
     */
    private List<Long> tagIds;

    /**
     * 标签
     */
    @Where(column = "tag", value = Condition.LIKE)
    private String tag;

}
