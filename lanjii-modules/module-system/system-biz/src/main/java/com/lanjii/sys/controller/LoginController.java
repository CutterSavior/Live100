package com.lanjii.sys.controller;

import com.lanjii.sys.config.SystemCacheConstants;
import com.lanjii.sys.service.LoginService;
import com.lanjii.system.api.dto.LoginBody;
import com.lanjii.system.api.vo.CaptchaVO;
import com.lanjii.system.api.vo.LoginInfo;
import com.lanjii.framework.log.annotation.LoginLog;
import com.lanjii.framework.context.tenant.TenantContext;
import com.lanjii.common.exception.BizException;
import com.lanjii.common.response.R;
import com.lanjii.common.response.ResultCode;
import com.lanjii.common.util.CaptchaUtils;
import com.lanjii.framework.mp.tenant.TenantHandler;
import com.lanjii.framework.security.model.AuthUser;
import com.lanjii.tenant.api.TenantApi;
import com.lanjii.tenant.api.vo.SysTenantVO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
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
    private final CacheManager cacheManager;
    private final TenantApi tenantApi;

    private Cache getCaptchaCache() {
        return cacheManager.getCache(SystemCacheConstants.CAPTCHA.getName());
    }

    /**
     * 登录
     *
     * @param loginBody 用户信息
     */
    @LoginLog
    @PostMapping("/login")
    public R<LoginInfo> login(@Validated @RequestBody LoginBody loginBody) {

        String cachedCode = getCaptchaCache().get(loginBody.getCaptchaKey(), String.class);
        if (!CaptchaUtils.verifyCaptcha(loginBody.getCaptchaCode(), cachedCode)) {
            throw new BizException(ResultCode.BAD_REQUEST, "验证码错误");
        }

        getCaptchaCache().evict(loginBody.getCaptchaKey());

        // 校验租户并设置上下文
        Long tenantId = TenantHandler.PLATFORM_TENANT_ID;
        String tenantCode = loginBody.getTenantCode();

        if (StringUtils.hasText(tenantCode)) {
            SysTenantVO tenant = tenantApi.getTenantByCode(tenantCode);
            if (tenant == null) {
                throw new BizException(ResultCode.BAD_REQUEST, "租户不存在");
            }
            if (tenant.getStatus() != 1) {
                throw new BizException(ResultCode.BAD_REQUEST, "租户已被禁用");
            }
            tenantId = tenant.getId();
        }

        TenantContext.setTenantId(tenantId);
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginBody.getUsername(),
                            loginBody.getPassword()
                    )
            );

            AuthUser userDetails = (AuthUser) authentication.getPrincipal();
            LoginInfo loginInfo = loginService.generateLoginInfo(userDetails);

            return R.success(loginInfo);
        } finally {
            TenantContext.clear();
        }
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

        // 将验证码存入缓存
        getCaptchaCache().put(captchaKey, code);

        CaptchaVO captchaVO = new CaptchaVO(captchaKey, imageBase64);
        return R.success(captchaVO);
    }
}