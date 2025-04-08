package com.lanjii.core.exception;

import com.lanjii.core.enums.ResultCode;
import lombok.Getter;

/**
 * 业务异常
 *
 * @author lizheng
 * @date 2024-09-12
 */
@Getter
public class BusinessException extends RuntimeException {

    private final int code;

    private final String msg;

    public BusinessException(ResultCode resultCode, String message) {
        super(message);
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    public BusinessException(ResultCode resultCode) {
        super(resultCode.getMsg());
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }
}
