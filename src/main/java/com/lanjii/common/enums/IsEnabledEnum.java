package com.lanjii.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 是否启用枚举
 *
 * @author lizheng
 */
@Getter
@RequiredArgsConstructor
public enum IsEnabledEnum implements CodeEnum<Integer> {

    ENABLED(1, "启用"),
    DISABLED(0, "禁用");

    private final Integer code;
    private final String desc;

}


