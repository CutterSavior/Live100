package com.lanjii.core.resp;

import com.lanjii.common.util.JacksonUtils;
import com.lanjii.common.util.WebContextUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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

    public static void write(ResultCode resultCode) throws IOException {
        write(resultCode, resultCode.getMsg());
    }

    public static void write(ResultCode resultCode, String errMsg) throws IOException {
        try {
            HttpServletResponse response = WebContextUtils.getResponse();
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Cache-Control", "no-cache");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().println(JacksonUtils.toJson(R.fail(resultCode.getCode(), errMsg)));
            response.getWriter().flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 写入文件响应
     *
     * @param resource     文件资源
     * @param fileName     下载文件名
     * @param contentType  内容类型，如果为null则使用 "application/octet-stream"
     * @throws IOException IO异常
     */
    public static void writeFile(Resource resource, String fileName, String contentType) throws IOException {
        if (resource == null || !resource.exists()) {
            write(ResultCode.NOT_FOUND, "文件不存在");
            return;
        }

        HttpServletResponse response = WebContextUtils.getResponse();
        if (response == null) {
            throw new IOException("无法获取HttpServletResponse");
        }

        // 设置响应头
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        response.setContentType(contentType);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
        
        // 设置文件长度
        long contentLength = resource.contentLength();
        if (contentLength > 0) {
            response.setContentLengthLong(contentLength);
        }

        // 写入文件内容
        try (InputStream inputStream = resource.getInputStream();
             OutputStream outputStream = response.getOutputStream()) {
            
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
        }
    }
}