package com.lanjii.common.annotation;

import com.lanjii.common.enums.SortOrder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 排序字段
 *
 * @author lizheng
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SortField {

    /**
     * 数据库字段名（默认为空，自动转换驼峰为下划线）
     */
    String field() default "";

    /**
     * 排序顺序（默认升序）
     */
    SortOrder order() default SortOrder.ASC;

    /**
     * 条件优先级，数值越小优先级越高
     * 默认值为Integer.MAX_VALUE（最低优先级）
     */
    int priority() default Integer.MAX_VALUE;
}

