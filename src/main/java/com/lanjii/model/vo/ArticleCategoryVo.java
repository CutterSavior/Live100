package com.lanjii.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 文章类别VO
 */
@Data
public class ArticleCategoryVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 类别名称
     */
    private String category;

    private Date createdTime;
    private String createdBy;
    private Date updatedTime;
} 