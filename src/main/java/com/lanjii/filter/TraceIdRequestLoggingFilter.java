package com.lanjii.filter;

import com.lanjii.core.constant.CommonConstants;
import com.lanjii.util.IdUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * 日志链路追踪配置
 *
 * @author lizheng
 * @date 2024-10-18
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TraceIdRequestLoggingFilter extends AbstractRequestLoggingFilter {


    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        String traceId = request.getHeader(CommonConstants.TRACE_ID);
        if (StringUtils.isEmpty(traceId)) {
            MDC.put(CommonConstants.TRACE_ID, IdUtils.simpleId());
        } else {
            MDC.put(CommonConstants.TRACE_ID, traceId);
        }

    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        MDC.clear();
    }
}
