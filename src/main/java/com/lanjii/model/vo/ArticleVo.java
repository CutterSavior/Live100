package com.lanjii.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lanjii.model.entity.Tag;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 题库表
 *
 * @author lizheng
 * @date 2024-10-08
 */
@Data
public class ArticleVo implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 标题
     */
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
     * 分类名称
     */
    private String categoryName;

    private List<Long> tagIds;

    /**
     * 标签列表
     */
    private List<Tag> tags;

    private Date createdTime;
    private String createdBy;
    private Date updatedTime;

}
