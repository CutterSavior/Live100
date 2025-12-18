package com.lanjii.biz.admin.system.controller;

import com.github.benmanes.caffeine.cache.Cache;
import com.lanjii.biz.admin.system.model.dto.LoginBody;
import com.lanjii.biz.admin.system.model.vo.CaptchaVO;
import com.lanjii.biz.admin.system.model.vo.LoginInfo;
import com.lanjii.biz.admin.system.service.LoginService;
import com.lanjii.common.util.CaptchaUtils;
import com.lanjii.core.annotation.LoginLog;
import com.lanjii.core.resp.R;
import com.lanjii.security.AuthUser;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 用户认证
 *
 * @author lanjii
 */
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final AuthenticationManager authenticationManager;
    private final Cache<String, String> captchaCache;

    /**
     * 登录
     *
     * @param loginBody 用户信息
     */
    @LoginLog
    @PostMapping("/login")
    public R<LoginInfo> login(@Validated @RequestBody LoginBody loginBody) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginBody.getUsername(),
                        loginBody.getPassword()
                )
        );

        AuthUser userDetails = (AuthUser) authentication.getPrincipal();
        LoginInfo loginInfo = loginService.generateLoginInfo(userDetails);

        return R.success(loginInfo);
    }

    /**
     * 登出
     */
    @LoginLog
    @PostMapping("/logout")
    public R<Void> logout() {
        loginService.logout();
        return R.success();
    }

    /**
     * 获取验证码
     *
     * @param response HTTP响应对象，用于设置禁用缓存的响应头
     * @return 验证码信息（captchaKey和图片Base64）
     */
    @GetMapping("/captcha")
    public R<CaptchaVO> getCaptcha(HttpServletResponse response) {
        // 设置响应头禁用缓存
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");

        // 生成验证码
        Map<String, String> captchaMap = CaptchaUtils.generateCaptcha();
        String captchaKey = captchaMap.get("captchaKey");
        String code = captchaMap.get("code");
        String imageBase64 = captchaMap.get("imageBase64");

        // 将验证码存入缓存，有效期5分钟
        captchaCache.put(captchaKey, code);

        CaptchaVO captchaVO = new CaptchaVO(captchaKey, imageBase64);
        return R.success(captchaVO);
    }
}