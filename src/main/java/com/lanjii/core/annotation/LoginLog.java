package com.lanjii.core.annotation;

import java.lang.annotation.*;

/**
 * 登录日志注解
 *
 * @author lanjii
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginLog {

    /**
     * 是否记录登录日志
     */
    boolean enabled() default true;

}
