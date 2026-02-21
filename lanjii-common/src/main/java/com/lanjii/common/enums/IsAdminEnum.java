package com.lanjii.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 是否管理员枚举
 *
 * @author lanjii
 */
@Getter
@RequiredArgsConstructor
public enum IsAdminEnum {

    NO(0, "否"),
    YES(1, "是");

    private final Integer code;
    private final String desc;

    public static boolean isAdmin(Integer isAdmin) {
        return YES.getCode().equals(isAdmin);
    }
}
