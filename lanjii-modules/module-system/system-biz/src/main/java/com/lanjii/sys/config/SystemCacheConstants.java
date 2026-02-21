package com.lanjii.sys.config;

import com.lanjii.framework.cache.core.CacheDef;

import java.time.Duration;

/**
 * system 模块缓存定义常量
 *
 * @author lanjii
 */
public interface SystemCacheConstants {

    /**
     * 字典数据缓存
     */
    CacheDef DICT_DATA = CacheDef.of("dictData", Duration.ofHours(24), false);

    /**
     * 系统配置缓存
     */
    CacheDef SYS_CONFIG = CacheDef.of("sysConfig", Duration.ofDays(7), false);

    /**
     * 用户会话缓存
     */
    CacheDef USER_SESSION = CacheDef.of("userSession", Duration.ofHours(24), false);

    /**
     * 用户信息缓存
     */
    CacheDef USER_INFO = CacheDef.of("userInfo", Duration.ofHours(24));

    /**
     * 验证码缓存
     */
    CacheDef CAPTCHA = CacheDef.of("captcha", Duration.ofMinutes(5));
}
