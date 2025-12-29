package com.lanjii.core.handler;

import com.lanjii.core.resp.R;
import com.lanjii.core.resp.ResultCode;
import com.lanjii.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

/**
 * 全局异常处理
 *
 * @author lanjii
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理用户禁用异常
     */
    @ExceptionHandler(DisabledException.class)
    public R<Void> handleDisabledException(DisabledException ex) {
        return R.fail(ResultCode.BAD_REQUEST.getCode(), "用户账户已被禁用");
    }

    /**
     * 处理认证失败异常
     */
    @ExceptionHandler(BadCredentialsException.class)
    public R<Void> handleBadCredentialsException(BadCredentialsException ex) {
        return R.fail(ResultCode.BAD_REQUEST.getCode(), "账户或密码错误，请重新输入！");
    }


    /**
     * 处理 @Validated 参数校验异常，只返回第一个错误
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<Void> handleValidationException(MethodArgumentNotValidException ex) {
        FieldError firstError = ex.getBindingResult().getFieldErrors().get(0);
        return R.fail(ResultCode.BAD_REQUEST.getCode(), firstError.getDefaultMessage());
    }

    /**
     * 处理业务异常
     */
    @ExceptionHandler(BizException.class)
    public R<Void> handleBusinessException(BizException ex) {
        log.warn(ex.toString());
        return R.fail(ex.getResultCode(), ex.getMessage());
    }

    /**
     * 处理未捕获异常
     */
    @ExceptionHandler(Exception.class)
    public R<Void> handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return R.fail(ResultCode.INTERNAL_SERVER_ERROR);
    }

    /**
     * 无权限
     */
    @ExceptionHandler(AuthorizationDeniedException.class)
    public R<Void> handleAuthorizationDeniedException(Exception ex) {
        return R.fail(ResultCode.FORBIDDEN);
    }

    /**
     * 处理文件上传大小超限异常
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public R<Void> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        log.warn("文件上传大小超限: {}", ex.getMessage());
        return R.fail(ResultCode.BAD_REQUEST.getCode(), "文件大小不能超过100MB");
    }

    /**
     * 处理文件上传异常
     */
    @ExceptionHandler(MultipartException.class)
    public R<Void> handleMultipartException(MultipartException ex) {
        log.warn("文件上传异常: {}", ex.getMessage());
        return R.fail(ResultCode.BAD_REQUEST.getCode(), "文件上传失败: " + ex.getMessage());
    }

}