package com.lanjii.framework.web.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Servlet 工具类 TODO
 *
 * @author lanjii
 */
public class ServletUtils {

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
     * 从当前请求中获取指定名称的请求头称
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
        return IpUtils.getIpAddr(getRequest());

    }

    /**
     * 获取浏览器类型
     *
     * @return 浏览器类型
     */
    public static String getBrowser() {
        return getBrowser(getRequest());
    }

    /**
     * 获取浏览器类型
     *
     * @param request 请求对象
     * @return 浏览器类型
     */
    public static String getBrowser(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }
        String userAgent = request.getHeader("User-Agent");
        if (userAgent == null) {
            return "unknown";
        }

        if (userAgent.contains("Chrome")) {
            return "Chrome";
        } else if (userAgent.contains("Firefox")) {
            return "Firefox";
        } else if (userAgent.contains("Safari")) {
            return "Safari";
        } else if (userAgent.contains("Edge")) {
            return "Edge";
        } else if (userAgent.contains("Opera")) {
            return "Opera";
        } else {
            return "unknown";
        }
    }

    /**
     * 获取操作系统
     *
     * @return 操作系统
     */
    public static String getOs() {
        return getOs(getRequest());
    }

    /**
     * 获取操作系统
     *
     * @param request 请求对象
     * @return 操作系统
     */
    public static String getOs(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }
        String userAgent = request.getHeader("User-Agent");
        if (userAgent == null) {
            return "unknown";
        }

        if (userAgent.contains("Windows")) {
            return "Windows";
        } else if (userAgent.contains("Mac")) {
            return "Mac";
        } else if (userAgent.contains("Linux")) {
            return "Linux";
        } else if (userAgent.contains("Android")) {
            return "Android";
        } else if (userAgent.contains("iOS")) {
            return "iOS";
        } else {
            return "unknown";
        }
    }

    /**
     * 从当前请求中获取User-Agent
     *
     * @return User-Agent字符串
     */
    public static String getUserAgent() {
        return getUserAgent(getRequest());
    }

    /**
     * 从当前请求中获取User-Agent
     *
     * @return User-Agent字符串
     */
    public static String getUserAgent(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }
}
