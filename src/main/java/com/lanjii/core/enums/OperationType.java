package com.lanjii.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 操作类型
 *
 * @author lizheng
 * @date 2025-04-10
 */
@Getter
@AllArgsConstructor
public enum OperationType {

    LOGIN(0, "登录"),
    LOGOUT(1, "登出");

    private final int type;
    private final String desc;
}
