package com.lanjii.model.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 文章标签关联表
 *
 * @author lizheng
 * @date 2025-03-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ArticleTagRel extends Model {

    private static final long serialVersionUID = 1L;

    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * 标签ID
     */
    private Long tagId;

}
