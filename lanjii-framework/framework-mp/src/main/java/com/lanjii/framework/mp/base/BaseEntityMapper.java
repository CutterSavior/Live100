package com.lanjii.framework.mp.base;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * MapStruct 基础接口
 *
 * @author lanjii
 */
public interface BaseEntityMapper<E, V, D> {

    V toVo(E entity);

    default List<V> toVo(List<E> model) {
        if (CollectionUtils.isEmpty(model)) {
            return Collections.emptyList();
        }
        return model.stream().map(this::toVo).collect(Collectors.toList());
    }

    E toEntity(D dto);

}
