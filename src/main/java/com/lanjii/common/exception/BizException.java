package com.lanjii.common.exception;

import com.lanjii.core.resp.ResultCode;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 业务异常类
 *
 * @author lanjii
 */
@Getter
public class BizException extends RuntimeException {

    private final ResultCode resultCode;
    private final boolean recordStackTrace;
    private final Map<String, Object> contextData = new HashMap<>(4); // 初始小容量

    public BizException(ResultCode resultCode, String msg, Throwable cause, boolean recordStackTrace) {
        super(msg, cause);
        this.resultCode = resultCode;
        this.recordStackTrace = recordStackTrace;
        if (!recordStackTrace) {
            super.setStackTrace(EMPTY_STACK_TRACE); // 使用常量空数组
        }
    }

    private static final StackTraceElement[] EMPTY_STACK_TRACE = new StackTraceElement[0];

    public BizException(String msg) {
        this(ResultCode.INTERNAL_SERVER_ERROR, msg, null, true);
    }

    public BizException(String msg, Throwable cause) {
        this(ResultCode.INTERNAL_SERVER_ERROR, msg, cause, true);
    }

    public BizException(ResultCode resultCode, String msg) {
        this(resultCode, msg, null, true);
    }

    public BizException(ResultCode resultCode, Throwable cause) {
        this(resultCode, resultCode.getMsg(), cause, true);
    }

    public static BizException validationError(ResultCode resultCode, String msg) {
        return new BizException(resultCode, msg, null, false);
    }

    public static BizException validationError(String msg) {
        return new BizException(ResultCode.BAD_REQUEST, msg, null, false);
    }

    public BizException withContext(String key, Object value) {
        contextData.put(key, value);
        return this;
    }

    @Override
    public Throwable fillInStackTrace() {
        if (!recordStackTrace) {
            return this;
        }
        return synchronizedFillInStackTrace();
    }


    private synchronized Throwable synchronizedFillInStackTrace() {
        return super.fillInStackTrace();
    }

    @Override
    public String toString() {
        String base = String.format("%s: code=%d, msg='%s'",
                getClass().getSimpleName(),
                resultCode.getCode(),
                getMessage());

        if (!contextData.isEmpty()) {
            return base + ", context=" + sanitize(contextData);
        }
        return base;
    }

    private String sanitize(Map<String, Object> data) {
        // 实现敏感信息过滤（示例）
        return data.entrySet().stream()
                .map(e -> e.getKey() + "=" + maskSensitiveValue(e.getValue()))
                .collect(Collectors.joining(", ", "{", "}"));
    }

    private Object maskSensitiveValue(Object value) {
        if (value instanceof CharSequence) {
            String str = value.toString();
            if (str.matches(".*(password|token|secret).*")) {
                return "***MASKED***";
            }
        }
        return value;
    }
}