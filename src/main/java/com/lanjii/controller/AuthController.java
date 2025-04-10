package com.lanjii.controller;

import com.google.code.kaptcha.Producer;
import com.lanjii.core.annotation.Log;
import com.lanjii.core.annotation.MultiRequestBody;
import com.lanjii.core.enums.OperationType;
import com.lanjii.core.enums.ResultCode;
import com.lanjii.core.exception.BusinessException;
import com.lanjii.core.obj.R;
import com.lanjii.model.dto.LoginBody;
import com.lanjii.service.IOnlineUserService;
import com.lanjii.service.impl.AuthService;
import com.lanjii.util.AuthUtils;
import com.lanjii.util.IdUtils;
import com.lanjii.util.LocalCacheUtils;
import com.lanjii.util.ServletUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Map;

/**
 * 用户认证控制层
 *
 * @author lizheng
 * @date 2024-10-10
 */
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final Producer kaptchaProducer;
    private final IOnlineUserService onlineUserService;

    @GetMapping("captcha")
    public R<Map<String, String>> getCaptcha() {
        HttpServletResponse response = ServletUtils.getHttpResponse();
        response.setContentType("image/jpeg");
        response.setHeader("Cache-Control", "no-store, no-cache");

        String captchaKey = IdUtils.simpleId();
        String capText = kaptchaProducer.createText();
        BufferedImage bi = kaptchaProducer.createImage(capText);
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(bi, "jpg", baos);
            byte[] imageBytes = baos.toByteArray();
            LocalCacheUtils.put(LocalCacheUtils.CacheType.CAPTCHA, captchaKey, capText);

            return R.success(Map.of("captchaKey", captchaKey, "captcha", Base64.getEncoder().encodeToString(imageBytes)));
        } catch (Exception e) {
            throw new BusinessException(ResultCode.INTERNAL_SERVER_ERROR, "生成验证码失败");
        }
    }

    /**
     * 登录
     */
    @Log(type = OperationType.LOGIN)
    @PostMapping("/login")
    public R<Map<String, Object>> login(@MultiRequestBody LoginBody loginBody) {
        String captchaKey = loginBody.getCaptchaKey();
        String captcha = LocalCacheUtils.get(LocalCacheUtils.CacheType.CAPTCHA, captchaKey);
        LocalCacheUtils.invalidate(LocalCacheUtils.CacheType.CAPTCHA, captchaKey);
        if (captcha == null || !captcha.equalsIgnoreCase(loginBody.getCaptcha())) {
            return R.fail("验证码错误");
        }
        return R.success(authService.login(loginBody));
    }

    /**
     * 登出
     */
    @Log(type = OperationType.LOGOUT)
    @PostMapping("/logout")
    public R<Map<String, Object>> logout() {
        LocalCacheUtils.invalidate(LocalCacheUtils.CacheType.OTHER, "auth:" + AuthUtils.getCurrentUsername());
        LocalCacheUtils.invalidate(LocalCacheUtils.CacheType.ONLINE_USER, AuthUtils.getCurrentUser().getToken());
        return R.success();
    }
}


