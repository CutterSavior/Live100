package com.lanjii.common.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Web上下文工具类
 *
 * @author lanjii
 */
public class WebContextUtils {

    /**
     * 获取当前请求的HttpServletRequest
     *
     * @return HttpServletRequest对象，如果当前不在Web上下文中则返回null
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = getServletRequestAttributes();
        return attributes != null ? attributes.getRequest() : null;
    }

    /**
     * 获取当前请求的HttpServletResponse
     *
     * @return HttpServletResponse对象，如果当前不在Web上下文中则返回null
     */
    public static HttpServletResponse getResponse() {
        ServletRequestAttributes attributes = getServletRequestAttributes();
        return attributes != null ? attributes.getResponse() : null;
    }

    /**
     * 获取ServletRequestAttributes
     *
     * @return ServletRequestAttributes对象，如果当前不在Web上下文中则返回null
     */
    private static ServletRequestAttributes getServletRequestAttributes() {
        try {
            return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 从当前请求中获取指定名称的请求头值
     *
     * @param headerName 请求头名称
     * @return 请求头值，如果不存在则返回null
     */
    public static String getHeader(String headerName) {
        HttpServletRequest request = getRequest();
        return request != null ? request.getHeader(headerName) : null;
    }

    /**
     * 从当前请求中获取Authorization头中的Bearer token
     *
     * @return JWT token，如果不存在则返回null
     */
    public static String getBearerToken() {
        String bearerToken = getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * 从当前请求中获取客户端IP地址
     *
     * @return 客户端IP地址
     */
    public static String getClientIpAddress() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return "unknown";
        }

        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty() && !"unknown".equalsIgnoreCase(xForwardedFor)) {
            return xForwardedFor.split(",")[0].trim();
        }

        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty() && !"unknown".equalsIgnoreCase(xRealIp)) {
            return xRealIp;
        }

        return request.getRemoteAddr();
    }

    /**
     * 从当前请求中获取User-Agent
     *
     * @return User-Agent字符串
     */
    public static String getUserAgent() {
        return getHeader("User-Agent");
    }
}