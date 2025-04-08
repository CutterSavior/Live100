package com.lanjii.model.dto;

import lombok.Data;

/**
 * @author lizheng
 * @date 2024-10-10
 */
@Data
public class LoginBody {

    private String username;
    private String password;
    private String captcha;
    private String captchaKey;

}
