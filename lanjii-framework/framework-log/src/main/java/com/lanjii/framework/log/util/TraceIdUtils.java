package com.lanjii.framework.log.util;

import org.slf4j.MDC;
import java.util.UUID;

/**
 * TraceId 工具类
 *
 * @author lanjii
 */
public class TraceIdUtils {

    public static final String TRACE_ID_KEY = "traceId";

    /**
     * 生成并设置 TraceId
     */
    public static String setupTraceId() {
        String traceId = UUID.randomUUID().toString().replace("-", "");
        MDC.put(TRACE_ID_KEY, traceId);
        return traceId;
    }

    /**
     * 设置自定义 TraceId
     */
    public static void setTraceId(String traceId) {
        MDC.put(TRACE_ID_KEY, traceId);
    }

    /**
     * 获取当前 TraceId
     */
    public static String getTraceId() {
        return MDC.get(TRACE_ID_KEY);
    }

    /**
     * 清除 TraceId
     */
    public static void clearTraceId() {
        MDC.remove(TRACE_ID_KEY);
    }
}
