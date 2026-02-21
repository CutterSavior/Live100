package com.lanjii.framework.context.req;

import cn.hutool.core.thread.threadlocal.NamedThreadLocal;

/**
 * 业务请求上下文处理器
 *
 * @author lanjii
 */
public class BizReqContextHolder {

    private static final ThreadLocal<BizReqContext> CONTEXT_HOLDER = new NamedThreadLocal<>("Lanjii context");

    public static void setContext(BizReqContext context) {
        CONTEXT_HOLDER.set(context);
    }

    public static BizReqContext getContext() {
        return CONTEXT_HOLDER.get();
    }

    public static void clear() {
        CONTEXT_HOLDER.remove();
    }
}
