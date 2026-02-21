package com.lanjii.sys.service;

import com.lanjii.system.api.vo.LoginInfo;
import com.lanjii.framework.security.model.AuthUser;

/**
 * 用户认证服务实现类
 *
 * @author lanjii
 */
public interface LoginService {

    /**
     * 用户登出
     */
    void logout();

    /**
     * 生成登录信息
     *
     * @param userDetails 认证用户信息
     * @return 登录信息
     */
    LoginInfo generateLoginInfo(AuthUser userDetails);
}