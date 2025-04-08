package com.lanjii.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 字典表详情Vo
 *
 * @author lizheng
 * @date 2024-10-10
 */
@Data
public class SysDictDetailVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 描述
     */
    private String description;

    /**
     * 字典编码
     */
    private String dictCode;

    /**
     * 字典标签（页面显示名称）
     */
    private String dictLabel;

    /**
     * 排序
     */
    private String dictSort;

    /**
     * 字典值
     */
    private Integer dictValue;

    /**
     * 创建时间
     */
    private Date createdTime;

}
