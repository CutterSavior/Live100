package com.lanjii.util;

import com.github.pagehelper.Page;
import com.lanjii.core.base.BaseModelMapper;
import com.lanjii.core.base.support.PageData;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * 分页数据工具类
 *
 * @author lizheng
 * @date 2024-09-25
 */
public class PageDataUtils {

    public static <T, V> PageData<V> make(List<T> list, BaseModelMapper<V, T> modelMapper) {
        long totalCount = 0L;
        if (CollectionUtils.isEmpty(list)) {
            return PageData.emptyPage();
        }
        if (list instanceof Page) {
            totalCount = ((Page<T>) list).getTotal();
        }
        return new PageData<>(totalCount, modelMapper.modelToVo(list));
    }

    public static <T> PageData<T> make(List<T> list) {
        long totalCount = 0L;
        if (CollectionUtils.isEmpty(list)) {
            return PageData.emptyPage();
        }
        if (list instanceof Page) {
            totalCount = ((Page<T>) list).getTotal();
        }
        return new PageData<>(totalCount, list);
    }
}
