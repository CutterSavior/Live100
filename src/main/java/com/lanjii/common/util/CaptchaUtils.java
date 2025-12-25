package com.lanjii.common.util;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 验证码工具类
 *
 * @author lanjii
 */
public class CaptchaUtils {

    /**
     * 验证码宽度
     */
    private static final int WIDTH = 110;

    /**
     * 验证码高度
     */
    private static final int HEIGHT = 40;

    /**
     * 验证码字符数量
     */
    private static final int CODE_COUNT = 4;

    /**
     * 干扰线数量
     */
    private static final int LINE_COUNT = 5;

    /**
     * 生成验证码
     *
     * @return Map包含验证码key、code和图片base64
     */
    public static Map<String, String> generateCaptcha() {
        // 生成随机验证码key
        String captchaKey = UUID.randomUUID().toString().replace("-", "");
        
        // 创建线性验证码
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(WIDTH, HEIGHT, CODE_COUNT, LINE_COUNT);
        
        // 获取验证码文本
        String code = lineCaptcha.getCode();
        
        // 获取验证码图片的Base64编码
        String imageBase64 = lineCaptcha.getImageBase64Data();
        
        Map<String, String> result = new HashMap<>();
        result.put("captchaKey", captchaKey);
        result.put("code", code);
        result.put("imageBase64", imageBase64);
        
        return result;
    }

    /**
     * 验证验证码
     *
     * @param inputCode 用户输入的验证码
     * @param actualCode 实际的验证码
     * @return 是否验证通过
     */
    public static boolean verifyCaptcha(String inputCode, String actualCode) {
        if (inputCode == null || actualCode == null) {
            return false;
        }
        return inputCode.equalsIgnoreCase(actualCode);
    }
}
