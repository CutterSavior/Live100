package com.lanjii.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 是否可见枚举
 *
 * @author lizheng
 */
@Getter
@RequiredArgsConstructor
public enum IsVisibleEnum implements CodeEnum<Integer> {

    ENABLED(1, "显示"),
    DISABLED(0, "隐藏");

    private final Integer code;
    private final String desc;

}
