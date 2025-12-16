package com.lanjii.core.annotation;

import com.lanjii.core.enums.QueryType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 查询条件
 *
 * @author lizheng
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryCondition {

    /**
     * 查询方式
     */
    QueryType type() default QueryType.EQUAL;

    /**
     *字段名（默认为空，使用属性名）
     */
    String field() default "";
}