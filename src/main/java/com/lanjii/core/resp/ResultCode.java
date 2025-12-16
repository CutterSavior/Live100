package com.lanjii.core.resp;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 结果编码
 *
 * @author lanjii
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    // ========== 成功状态码 ==========
    SUCCESS(200, "成功"),

    // ========== 客户端错误 4xx ==========
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "资源不存在"),
    METHOD_NOT_ALLOWED(405, "不支持的请求方法"),
    CONFLICT(409, "资源冲突"),
    UNSUPPORTED_MEDIA_TYPE(415, "不支持的媒体类型"),
    TOO_MANY_REQUESTS(429, "请求过于频繁"),

    // ========== 服务端错误 5xx ==========
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    SERVICE_UNAVAILABLE(503, "服务不可用");

    private final int code;
    private final String msg;

}
