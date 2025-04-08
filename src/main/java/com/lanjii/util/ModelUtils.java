package com.lanjii.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 负责Model数据操作、类型转换和关系关联等行为的工具类。
 *
 * @author lizheng
 * @date 2024-09-25
 */
@Slf4j
public class ModelUtils {

    public static <S, T> T copyTo(S source, Class<T> targetClazz) {
        if (source == null) {
            return null;
        }
        try {
            T target = targetClazz.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (Exception e) {
            log.error("Failed to call MyModelUtil.copyTo", e);
            return null;
        }
    }

    public static <S, T> List<T> copyToList(List<S> source, Class<T> targetClazz) {
        if (source == null) {
            return null;
        }
        return source.stream().map(s -> {
            try {
                T target = targetClazz.getDeclaredConstructor().newInstance();
                BeanUtils.copyProperties(s, target);
                return target;
            } catch (Exception e) {
                log.error("Failed to call MyModelUtil.copyTo", e);
                return null;
            }
        }).collect(Collectors.toList());

    }
}
