package com.lanjii.framework.web.filter;

import com.lanjii.framework.log.util.TraceIdUtils;
import jakarta.servlet.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * TraceId 过滤器
 *
 * @author lanjii
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TraceIdFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            // 生成 TraceId
            TraceIdUtils.setupTraceId();
            chain.doFilter(request, response);
        } finally {
            // 请求结束清理 TraceId
            TraceIdUtils.clearTraceId();
        }
    }
}
