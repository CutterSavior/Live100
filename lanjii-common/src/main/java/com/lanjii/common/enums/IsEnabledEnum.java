package com.lanjii.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 是否启用枚举
 *
 * @author lanjii
 */
@Getter
@RequiredArgsConstructor
public enum IsEnabledEnum {

    ENABLED(1, "启用"),
    DISABLED(0, "禁用");

    private final Integer code;
    private final String desc;

}


