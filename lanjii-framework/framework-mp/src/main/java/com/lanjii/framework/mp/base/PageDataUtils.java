package com.lanjii.framework.mp.base;

import com.github.pagehelper.Page;
import com.lanjii.common.support.PageData;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * 分页数据工具类
 *
 * @author lanjii
 */
public class PageDataUtils {

    public static <E, V> PageData<V> make(List<E> list, BaseEntityMapper<E, V, ?> modelMapper) {
        long total = 0L;
        if (CollectionUtils.isEmpty(list)) {
            return PageData.emptyPage();
        }
        if (list instanceof Page<E>) {
            total = ((Page<E>) list).getTotal();
        }
        return new PageData<>(total, modelMapper.toVo(list));
    }

    public static <V> PageData<V> make(List<V> list) {
        long total = 0L;
        if (CollectionUtils.isEmpty(list)) {
            return PageData.emptyPage();
        }
        if (list instanceof Page<V>) {
            total = ((Page<V>) list).getTotal();
        }
        return new PageData<>(total, list);
    }

}
