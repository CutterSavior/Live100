package com.lanjii.aspect;

import com.lanjii.core.annotation.Log;
import com.lanjii.core.enums.OperationType;
import com.lanjii.core.enums.ResultCode;
import com.lanjii.core.obj.R;
import com.lanjii.model.dto.LoginBody;
import com.lanjii.model.entity.SysOperationLog;
import com.lanjii.service.ISysOperationLogService;
import com.lanjii.util.AuthUtils;
import com.lanjii.util.IpUtils;
import com.lanjii.util.ServletUtils;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 日志切面
 *
 * @author lizheng
 * @date 2025-04-10
 */
@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {

    private final ISysOperationLogService sysOperationLogService;

    @Around("@annotation(com.lanjii.core.annotation.Log)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Log logAnnotation = method.getAnnotation(Log.class);

        SysOperationLog log = new SysOperationLog();
        log.setType(logAnnotation.type().getType());
        log.setOperation(logAnnotation.type().getDesc());
        HttpServletRequest httpRequest = ServletUtils.getHttpRequest();
        String clientIp = IpUtils.getClientIp(httpRequest);
        log.setIp(clientIp);

        if (logAnnotation.type().equals(OperationType.LOGIN)) {
            Object[] args = joinPoint.getArgs();
            String username = ((LoginBody) args[0]).getUsername();
            log.setOperationBy(username);
        } else {
            log.setOperationBy(AuthUtils.getCurrentUsername());
        }

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = null;
        try {
            result = joinPoint.proceed();
            int code = ((R<?>) result).getCode();
            log.setIsSuccess(code == ResultCode.SUCCESS.getCode() ? 1 : 0);
        } catch (Exception e) {
            log.setIsSuccess(0);
            throw e;
        } finally {
            stopWatch.stop();
            log.setDuration(stopWatch.getTotalTimeMillis());
            sysOperationLogService.save(log);
        }

        return result;
    }
}
