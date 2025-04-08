package com.lanjii.core.base.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

/**
 * 分页数据
 *
 * @author lizheng
 * @date 2024-09-25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageData<T> {
    /**
     * 总页数
     */
    private Long total;
    /**
     * 分页数据
     */
    private List<T> list;

    public static <T> PageData<T> emptyPage() {
        return new PageData<>(0L, new LinkedList<>());
    }

}
