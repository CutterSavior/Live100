package com.lanjii.common.support;

import lombok.Data;

/**
 * 查询基础类
 *
 * @author lizheng
 */
@Data
public class PageParam {

    /**
     * 页数
     */
    private Integer pageNum = 1;

    /**
     * 页大小
     */
    private Integer pageSize = 20;

}
