package com.lanjii.system.api.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 用户登录
 *
 * @author lanjii
 */
@Data
public class LoginBody {

    /**
     * 租户编码
     */
    private String tenantCode;

    @NotEmpty(message = "用户名不能为空")
    private String username;

    @NotEmpty(message = "密码不能为空")
    private String password;

    @NotEmpty(message = "验证码标识不能为空")
    private String captchaKey;

    @NotEmpty(message = "验证码不能为空")
    private String captchaCode;
}
