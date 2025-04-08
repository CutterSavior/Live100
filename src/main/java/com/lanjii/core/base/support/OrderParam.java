package com.lanjii.core.base.support;

import lombok.Data;

/**
 * 排序入参
 *
 * @author lizheng
 * @date 2024-09-26
 */
@Data
public class OrderParam {

    /**
     * 排序的字段名
     */
    private String fieldName;
    /**
     * 是否正序排序
     */
    private Boolean asc;
    /**
     * 聚合日期排序
     */
    private String dateAggregateBy;

}
