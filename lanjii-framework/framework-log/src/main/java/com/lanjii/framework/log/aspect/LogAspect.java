package com.lanjii.framework.log.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lanjii.framework.log.annotation.Log;
import com.lanjii.framework.log.annotation.LoginLog;
import com.lanjii.framework.log.LogService;
import com.lanjii.framework.log.model.LoginLogRecord;
import com.lanjii.framework.log.model.OperationLogRecord;
import com.lanjii.common.util.JacksonUtils;
import com.lanjii.common.util.MaskUtils;
import com.lanjii.framework.context.req.BizReqContext;
import com.lanjii.framework.context.req.BizReqContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 日志切面处理器
 *
 * @author lanjii
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {

    private final LogService logService;
    private final ObjectMapper objectMapper;

    /**
     * 操作日志切点
     */
    @Pointcut("@annotation(com.lanjii.framework.log.annotation.Log)")
    public void logPointcut() {
    }

    /**
     * 登录日志切点
     */
    @Pointcut("@annotation(com.lanjii.framework.log.annotation.LoginLog)")
    public void loginLogPointcut() {
    }

    /**
     * 操作日志环绕通知
     */
    @Around("logPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = StopWatch.createStarted();
        Object result = null;
        Exception exception = null;

        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            exception = e;
            throw e;
        } finally {
            stopWatch.stop();
            try {
                handleOperLog(joinPoint, result, exception, stopWatch.getTime());
            } catch (Exception e) {
                log.error("记录操作日志异常", e);
            }
        }

        return result;
    }

    /**
     * 登录日志后置通知
     */
    @AfterReturning(pointcut = "loginLogPointcut()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        try {
            handleLoginLog(joinPoint, null);
        } catch (Exception e) {
            log.error("记录登录日志异常", e);
        }
    }

    /**
     * 登录日志异常通知
     */
    @AfterThrowing(pointcut = "loginLogPointcut()", throwing = "exception")
    public void afterThrowing(JoinPoint joinPoint, Exception exception) {
        try {
            handleLoginLog(joinPoint, exception);
        } catch (Exception e) {
            log.error("记录登录日志异常", e);
        }
    }

    /**
     * 处理操作日志
     */
    private void handleOperLog(ProceedingJoinPoint joinPoint, Object result, Exception exception, long costTime) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Log logAnnotation = signature.getMethod().getAnnotation(Log.class);
        if (logAnnotation == null) {
            return;
        }

        BizReqContext context = BizReqContextHolder.getContext();

        OperationLogRecord record = new OperationLogRecord();

        record.setTitle(logAnnotation.title());
        record.setBusinessType(logAnnotation.businessType().getCode());
        record.setMethod(joinPoint.getSignature().getName());
        record.setRequestMethod(mapRequestMethodToCode(context.getRequestMethod()));
        record.setOperUrl(context.getRequestUrl());
        record.setOperTime(LocalDateTime.now());
        record.setCostTime(costTime);
        record.setOperName(context.getUsername());

        if (logAnnotation.isSaveRequestData()) {
            Object[] args = joinPoint.getArgs();
            if (args != null && args.length > 0) {
                String operParam = JacksonUtils.toJson(args);
                record.setOperParam(MaskUtils.maskSensitiveData(operParam));
            }
        }

        if (logAnnotation.isSaveResponseData()) {
            String jsonResult = JacksonUtils.toJson(result);
            record.setJsonResult(jsonResult);
        }

        if (exception != null) {
            record.setStatus(0);
            record.setErrorMsg(exception.getMessage());
        } else {
            record.setStatus(1);
        }

        logService.saveOperLog(record);
    }

    /**
     * 处理登录日志
     */
    private void handleLoginLog(JoinPoint joinPoint, Exception exception) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        LoginLog loginLogAnnotation = signature.getMethod().getAnnotation(LoginLog.class);
        if (loginLogAnnotation == null || !loginLogAnnotation.enabled()) {
            return;
        }
        BizReqContext context = BizReqContextHolder.getContext();

        LoginLogRecord record = new LoginLogRecord();

        record.setIpAddress(context.getClientIp());
        record.setBrowser(context.getBrowser());
        record.setOs(context.getOs());
        record.setLoginLocation(context.getClientLocation());
        record.setLoginTime(LocalDateTime.now());

        // 判断是登录还是登出操作
        String methodName = joinPoint.getSignature().getName();
        boolean isLogout = "logout".equals(methodName);

        if (isLogout) {
            // 登出操作
            record.setUsername(context.getUsername());
            record.setLoginType(1); // 登出
            record.setStatus(1); // 成功
            record.setMsg("登出成功");
        } else {
            String username = getUsernameFromRequest(joinPoint);
            record.setUsername(username);
            record.setLoginType(0);

            if (exception != null) {
                record.setStatus(0);
                record.setMsg(exception.getMessage());
            } else {
                record.setStatus(1);
                record.setMsg("登录成功");
            }
        }

        // 保存登录日志
        logService.saveLoginLog(record);
    }


    /**
     * 将HTTP方法名映射为数值编码
     * 1-GET，2-POST，3-PUT，4-DELETE
     */
    private Integer mapRequestMethodToCode(String method) {
        if (method == null) {
            return null;
        }
        switch (method.toUpperCase()) {
            case "GET":
                return 1;
            case "POST":
                return 2;
            case "PUT":
                return 3;
            case "DELETE":
                return 4;
            default:
                return null;
        }
    }

    /**
     * 从请求参数中获取用户名
     */
    private String getUsernameFromRequest(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            for (Object arg : args) {
                if (arg != null) {
                    return objectMapper.convertValue(arg, java.util.Map.class).get("username").toString();
                }
            }
        }
        return "unknown";
    }

}
