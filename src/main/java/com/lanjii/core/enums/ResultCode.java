package com.lanjii.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lizheng
 * @date 2024-09-12
 */
@Getter
@AllArgsConstructor
public enum ResultCode {
    FAILURE(-1, "请求失败"),
    SUCCESS(200, "请求成功"),
    TOKEN_IS_INVALID(401, "登录失效或被踢出，请重新登录！"),
    NO_PERMISSION(403, "无权限访问"),
    DEMO_USER(444, "演示账户禁止操作"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    VALIDATE_ERROR(1000, "参数校验失败"),
    WRONG_USERNAME_OR_PASSWORD(1001, "用户名或密码错误"),
    WRONG_PASSWORD(1002, "密码错误"),
    USER_IS_DISABLED(1002, "用户已被禁用"),
    RESPONSE_PACK_ERROR(2000, "response返回包装失败"),
    DATA_NOT_EXIST(3000, "数据不存在"),
    DICT_CODE_ALREADY_EXISTS(4000, "字典编码已存在"),
    DICT_ALREADY_EXISTS(4001, "字典已存在"),

    NOTICE_READ_ERROR(5000, "非用户通知");

    private final int code;
    private final String msg;

}
