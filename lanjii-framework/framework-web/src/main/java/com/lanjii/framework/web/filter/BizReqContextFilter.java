package com.lanjii.framework.web.filter;

import com.lanjii.framework.context.req.BizReqContext;
import com.lanjii.framework.context.req.BizReqContextHolder;
import com.lanjii.framework.web.util.IpUtils;
import com.lanjii.framework.web.util.ServletUtils;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 业务请求上下文过滤器
 *
 * @author lanjii
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class BizReqContextFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            if (request instanceof HttpServletRequest httpRequest) {
                String ipAddr = IpUtils.getIpAddr(httpRequest);
                BizReqContext context = BizReqContext.builder()
                        .clientIp(ipAddr)
                        .clientLocation(IpUtils.getFormattedAddress(ipAddr))
                        .browser(ServletUtils.getBrowser(httpRequest))
                        .os(ServletUtils.getOs(httpRequest))
                        .userAgent(ServletUtils.getUserAgent(httpRequest))
                        .requestUrl(httpRequest.getRequestURI())
                        .requestMethod(httpRequest.getMethod())
                        .build();

                BizReqContextHolder.setContext(context);
            }

            chain.doFilter(request, response);
        } finally {
            BizReqContextHolder.clear();
        }
    }
}
