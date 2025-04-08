package com.lanjii.core.obj;

import com.lanjii.core.constant.CommonConstants;
import com.lanjii.core.enums.ResultCode;
import com.lanjii.filter.TraceIdRequestLoggingFilter;
import com.lanjii.util.JsonUtils;
import com.lanjii.util.ServletUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.MDC;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 统一响应体
 *
 * @author lizheng
 * @date 2024-08-29
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class R<T> {

    private int code;
    private String msg;
    @Builder.Default()
    private String traceId = MDC.get(CommonConstants.TRACE_ID);
    private T data;

    public R(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> R<T> success(T data) {
        return R.<T>builder().code(ResultCode.SUCCESS.getCode()).msg(ResultCode.SUCCESS.getMsg()).data(data).build();
    }

    public static <T> R<T> success() {
        return success(null);
    }

    public static <T> R<T> fail(String errorMsg) {
        return R.<T>builder().code(ResultCode.FAILURE.getCode()).msg(errorMsg).build();
    }

    public static <T> R<T> fail(ResultCode resultCode) {
        return R.<T>builder().code(resultCode.getCode()).msg(resultCode.getMsg()).build();
    }

    public static void writeFail(ResultCode resultCode) throws IOException {
        HttpServletResponse response = ServletUtils.getHttpResponse();
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(JsonUtils.objToJson(R.fail(resultCode)));
        response.getWriter().flush();
    }
}
