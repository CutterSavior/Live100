package com.lanjii.core.annotation;

import com.lanjii.common.enums.BusinessTypeEnum;

import java.lang.annotation.*;

/**
 * 操作日志注解
 *
 * @author lanjii
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 操作模块
     */
    String title() default "";

    /**
     * 业务类型
     */
    BusinessTypeEnum businessType() default BusinessTypeEnum.OTHER;

    /**
     * 是否保存请求参数
     */
    boolean isSaveRequestData() default true;

    /**
     * 是否保存响应参数
     */
    boolean isSaveResponseData() default true;

}
