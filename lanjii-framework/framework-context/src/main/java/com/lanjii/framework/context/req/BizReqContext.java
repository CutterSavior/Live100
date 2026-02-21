package com.lanjii.framework.context.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 业务请求上下文
 *
 * @author lanjii
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BizReqContext {

    private String clientIp;

    private String clientLocation;

    private String browser;

    private String os;

    private String userAgent;

    private String requestUrl;

    private String requestMethod;

    private String username;
}
