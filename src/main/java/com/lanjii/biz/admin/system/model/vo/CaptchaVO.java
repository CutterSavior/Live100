package com.lanjii.biz.admin.system.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 验证码响应对象
 *
 * @author lanjii
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaVO {

    /**
     * 验证码唯一标识
     */
    private String captchaKey;

    /**
     * 验证码图片Base64编码
     */
    private String imageBase64;
}
