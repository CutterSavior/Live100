package com.lanjii.advice;

import com.lanjii.core.exception.BusinessException;
import com.lanjii.core.obj.R;
import com.lanjii.core.enums.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理
 *
 * @author lizheng
 * @date 2024-09-12
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    /**
     * 参数校验错误
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public R<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {

        boolean fieldErrorUnobtainable = e == null || CollectionUtils.isEmpty(e.getBindingResult().getAllErrors()) || e.getBindingResult().getAllErrors().get(0) == null;
        if (fieldErrorUnobtainable) {
            return R.success();
        }

        FieldError fieldError = (FieldError) e.getBindingResult().getAllErrors().get(0);
        String objectName = fieldError.getObjectName();
        String field = fieldError.getField();
        String defaultMessage = fieldError.getDefaultMessage();
        String errMsg = "参数" + objectName + '.' + field + "：" + defaultMessage;
        return R.fail(errMsg);
    }

    /**
     * 业务异常错误
     */
    @ExceptionHandler({BusinessException.class})
    public R<String> businessExceptionHandler(BusinessException e) {
        return R.fail(e.getMessage());
    }

    /**
     * 用户校验错误
     */
    @ExceptionHandler({BadCredentialsException.class, UsernameNotFoundException.class})
    public R<String> wrongUsernameOrPasswordHandler(Exception e) {
        return R.fail(ResultCode.WRONG_USERNAME_OR_PASSWORD);
    }

    /**
     * 用户权限校验失败，访问被拒绝异常
     */
    @ExceptionHandler({AccessDeniedException.class})
    public void accessDeniedException(Exception e) throws Exception {
        throw e;
    }

    /**
     * 用户验证异常
     */
    @ExceptionHandler({AuthenticationException.class})
    public R<String> authenticationExceptionHandler(AuthenticationException e) throws Exception {
        return R.fail(e.getMessage());
    }

    @ExceptionHandler({Exception.class})
    public R<String> exceptionHandler(Exception e) {
        log.error("未知异常", e);
        return R.fail(ResultCode.INTERNAL_SERVER_ERROR);
    }

}
