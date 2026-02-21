package com.lanjii.framework.security.handler;

import com.lanjii.common.response.R;
import com.lanjii.common.response.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Security 异常处理
 *
 * @author lanjii
 */
@Slf4j
@RestControllerAdvice
public class SecurityExceptionHandler {

    /**
     * 处理用户禁用异常
     */
    @ExceptionHandler(DisabledException.class)
    public R<Void> handleDisabledException(DisabledException ex) {
        return R.fail(ResultCode.BAD_REQUEST.getCode(), "用户账户已被禁用");
    }

    /**
     * 处理内部认证服务异常
     */
    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public R<Void> handleInternalAuthenticationServiceException(InternalAuthenticationServiceException ex) {
        Throwable cause = ex.getCause();
        if (cause instanceof BadCredentialsException) {
            return R.fail(ResultCode.BAD_REQUEST.getCode(), cause.getMessage());
        }
        return R.fail(ResultCode.BAD_REQUEST.getCode(), ex.getMessage());
    }

    /**
     * 无权限
     */
    @ExceptionHandler(AuthorizationDeniedException.class)
    public R<Void> handleAuthorizationDeniedException(Exception ex) {
        return R.fail(ResultCode.FORBIDDEN);
    }
}
