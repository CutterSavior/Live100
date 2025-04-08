package com.lanjii.core.base.support;

import lombok.Setter;

/**
 * 分页入参
 *
 * @author lizheng
 * @date 2024-09-18
 */
@Setter
public class PageParam {
    public static final int DEFAULT_PAGE_NUM = 1;
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int DEFAULT_MAX_SIZE = 1000;

    /**
     * 分页号码，从1开始计数。
     */
    private Integer pageNum;

    /**
     * 每页大小。
     */
    private Integer pageSize;

    /**
     * 获取当前分页页号。
     *
     */
    public Integer getPageNum() {
        if (this.pageNum == null || this.pageNum <= 0) {
            this.pageNum = DEFAULT_PAGE_NUM;
        }
        return this.pageNum;
    }

    /**
     * 获取分页的大小。
     *
     */
    public Integer getPageSize() {
        if (this.pageSize == null || this.pageSize <= 0 || this.pageSize > DEFAULT_MAX_SIZE) {
            this.pageSize = DEFAULT_PAGE_SIZE;
        }
        return this.pageSize;
    }

}
