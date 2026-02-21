package com.lanjii.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 响应体结构
 *
 * @author lanjii
 */
@Data
@AllArgsConstructor
public class R<T> {

    /**
     * 编码
     */
    private int code;
    /**
     * 信息
     */
    private String msg;
    /**
     * 数据
     */
    private T data;

    public static <T> R<T> success(T data) {
        return new R<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), data);
    }

    public static <T> R<T> success() {
        return success(null);
    }

    public static <T> R<T> fail(String errorMsg) {
        return new R<T>(ResultCode.INTERNAL_SERVER_ERROR.getCode(), errorMsg, null);
    }

    public static <T> R<T> fail(int code, String errorMsg) {
        return new R<T>(code, errorMsg, null);
    }

    public static <T> R<T> fail(ResultCode resultCode) {
        return new R<T>(resultCode.getCode(), resultCode.getMsg(), null);
    }

    public static <T> R<T> fail(ResultCode resultCode, String errorMsg) {
        return new R<T>(resultCode.getCode(), errorMsg, null);
    }
}
