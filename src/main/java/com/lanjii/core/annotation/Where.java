package com.lanjii.core.annotation;

import com.lanjii.core.enums.Condition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义条件查询
 *
 * @author lizheng
 * @date 2024-09-12
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Where {
    /**
     * 查询条件
     */
    Condition value() default Condition.EQ;

    /**
     * 数据库字段名称
     */
    String column();
}