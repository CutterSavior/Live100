package com.lanjii.core.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lanjii.core.annotation.Log;
import com.lanjii.core.annotation.LoginLog;
import com.lanjii.biz.admin.log.model.dto.SysLoginLogDTO;
import com.lanjii.biz.admin.log.model.dto.SysOperLogDTO;
import com.lanjii.biz.admin.log.service.SysLoginLogService;
import com.lanjii.biz.admin.log.service.SysOperLogService;
import com.lanjii.common.util.WebContextUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

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

    private final SysOperLogService sysOperLogService;
    private final SysLoginLogService sysLoginLogService;
    private final ObjectMapper objectMapper;

    /**
     * 操作日志切点
     */
    @Pointcut("@annotation(com.lanjii.core.annotation.Log)")
    public void logPointcut() {
    }

    /**
     * 登录日志切点
     */
    @Pointcut("@annotation(com.lanjii.core.annotation.LoginLog)")
    public void loginLogPointcut() {
    }

    /**
     * 操作日志环绕通知
     */
    @Around("logPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = null;
        Exception exception = null;

        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            exception = e;
            throw e;
        } finally {
            long endTime = System.currentTimeMillis();
            long costTime = endTime - startTime;
            
            try {
                handleOperLog(joinPoint, result, exception, costTime);
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
            handleLoginLog(joinPoint, result, null);
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
            handleLoginLog(joinPoint, null, exception);
        } catch (Exception e) {
            log.error("记录登录日志异常", e);
        }
    }

    /**
     * 处理操作日志
     */
    private void handleOperLog(ProceedingJoinPoint joinPoint, Object result, Exception exception, long costTime) {
        Log logAnnotation = getLogAnnotation(joinPoint);
        if (logAnnotation == null) {
            return;
        }

        HttpServletRequest request = WebContextUtils.getRequest();
        if (request == null) {
            return;
        }

        SysOperLogDTO operLogDTO = new SysOperLogDTO();
        
        operLogDTO.setTitle(logAnnotation.title());
        operLogDTO.setBusinessType(logAnnotation.businessType().getCode());
        operLogDTO.setMethod(joinPoint.getSignature().getName());
        operLogDTO.setRequestMethod(request.getMethod());
        operLogDTO.setOperUrl(request.getRequestURI());
        operLogDTO.setOperIp(WebContextUtils.getClientIpAddress());
        operLogDTO.setOperTime(LocalDateTime.now());
        operLogDTO.setCostTime(costTime);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            operLogDTO.setOperName(username);
        }

        if (logAnnotation.isSaveRequestData()) {
            String operParam = getRequestParams(joinPoint);
            operLogDTO.setOperParam(maskSensitiveData(operParam));
        }

        if (logAnnotation.isSaveResponseData()) {
            String jsonResult = getResponseResult(result);
            operLogDTO.setJsonResult(jsonResult);
        }

        if (exception != null) {
            operLogDTO.setStatus(0);
            operLogDTO.setErrorMsg(exception.getMessage());
        } else {
            operLogDTO.setStatus(1);
        }

        sysOperLogService.saveOperLog(operLogDTO);
    }

    /**
     * 处理登录日志
     */
    private void handleLoginLog(JoinPoint joinPoint, Object result, Exception exception) {
        LoginLog loginLogAnnotation = getLoginLogAnnotation(joinPoint);
        if (loginLogAnnotation == null || !loginLogAnnotation.enabled()) {
            return;
        }

        HttpServletRequest request = WebContextUtils.getRequest();
        if (request == null) {
            return;
        }

        SysLoginLogDTO loginLogDTO = new SysLoginLogDTO();
        
        loginLogDTO.setIpAddress(WebContextUtils.getClientIpAddress());
        loginLogDTO.setBrowser(getBrowser(request));
        loginLogDTO.setOs(getOs(request));
        loginLogDTO.setLoginTime(LocalDateTime.now());

        // 判断是登录还是登出操作
        String methodName = joinPoint.getSignature().getName();
        boolean isLogout = "logout".equals(methodName);

        if (isLogout) {
            // 登出操作：从Security上下文中获取用户名
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = "unknown";
            if (authentication != null && authentication.isAuthenticated()) {
                username = authentication.getName();
            }
            loginLogDTO.setUsername(username);
            loginLogDTO.setLoginType(1); // 登出
            loginLogDTO.setStatus(1); // 成功
            loginLogDTO.setMsg("登出成功");
        } else {
            // 登录操作：从请求参数中获取用户名
            String username = getUsernameFromRequest(joinPoint);
            loginLogDTO.setUsername(username);
            loginLogDTO.setLoginType(0);

            if (exception != null) {
                loginLogDTO.setStatus(0);
                loginLogDTO.setMsg(exception.getMessage());
            } else {
                loginLogDTO.setStatus(1);
                loginLogDTO.setMsg("登录成功");
            }
        }

        // 异步保存登录日志
        sysLoginLogService.saveLoginLog(loginLogDTO);
    }

    /**
     * 获取Log注解
     */
    private Log getLogAnnotation(JoinPoint joinPoint) {
        return Arrays.stream(joinPoint.getSignature().getDeclaringType().getDeclaredMethods())
                .filter(method -> method.getName().equals(joinPoint.getSignature().getName()))
                .findFirst()
                .map(method -> method.getAnnotation(Log.class))
                .orElse(null);
    }

    /**
     * 获取LoginLog注解
     */
    private LoginLog getLoginLogAnnotation(JoinPoint joinPoint) {
        return Arrays.stream(joinPoint.getSignature().getDeclaringType().getDeclaredMethods())
                .filter(method -> method.getName().equals(joinPoint.getSignature().getName()))
                .findFirst()
                .map(method -> method.getAnnotation(LoginLog.class))
                .orElse(null);
    }

    /**
     * 获取请求参数
     */
    private String getRequestParams(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return "";
        }
        
        try {
            return objectMapper.writeValueAsString(args);
        } catch (JsonProcessingException e) {
            log.error("序列化请求参数失败", e);
            return Arrays.toString(args);
        }
    }

    /**
     * 获取响应结果
     */
    private String getResponseResult(Object result) {
        if (result == null) {
            return "";
        }
        
        try {
            return objectMapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            log.error("序列化响应结果失败", e);
            return result.toString();
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
                    // 尝试从对象中获取username字段
                    try {
                        return objectMapper.convertValue(arg, java.util.Map.class).get("username").toString();
                    } catch (Exception e) {
                        // 忽略异常，继续尝试下一个参数
                    }
                }
            }
        }
        return "unknown";
    }

    /**
     * 获取浏览器类型
     */
    private String getBrowser(HttpServletRequest request) {
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
     */
    private String getOs(HttpServletRequest request) {
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
     * 脱敏处理敏感数据
     */
    private String maskSensitiveData(String data) {
        if (data == null || data.isEmpty()) {
            return data;
        }
        
        // 替换密码字段
        data = data.replaceAll("\"password\"\\s*:\\s*\"[^\"]*\"", "\"password\":\"*******\"");
        data = data.replaceAll("\"pwd\"\\s*:\\s*\"[^\"]*\"", "\"pwd\":\"*******\"");
        data = data.replaceAll("\"oldPassword\"\\s*:\\s*\"[^\"]*\"", "\"oldPassword\":\"*******\"");
        data = data.replaceAll("\"newPassword\"\\s*:\\s*\"[^\"]*\"", "\"newPassword\":\"*******\"");
        
        return data;
    }

}
