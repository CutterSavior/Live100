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
public enum IsAdminEnum implements CodeEnum<Integer> {

    NO(0, "否"),
    YES(1, "是");

    private final Integer code;
    private final String desc;

    /**
     * 判断是否为管理员
     *
     * @param isAdmin 是否管理员标识
     * @return true-管理员，false-普通用户
     */
    public static boolean isAdmin(Integer isAdmin) {
        return YES.getCode().equals(isAdmin);
    }
}
