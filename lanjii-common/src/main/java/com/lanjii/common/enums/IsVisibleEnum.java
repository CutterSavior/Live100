package com.lanjii.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 是否可见枚举
 *
 * @author lanjii
 */
@Getter
@RequiredArgsConstructor
public enum IsVisibleEnum {

    ENABLED(1, "显示"),
    DISABLED(0, "隐藏");

    private final Integer code;
    private final String desc;

}
