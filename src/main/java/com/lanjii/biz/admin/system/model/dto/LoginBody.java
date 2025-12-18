package com.lanjii.biz.admin.system.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 用户登录
 *
 * @author lanjii
 */
@Data
public class LoginBody {

    @NotEmpty(message = "用户名不能为空")
    private String username;

    @NotEmpty(message = "密码不能为空")
    private String password;
}
