package com.lanjii.biz.admin.system.service;

import com.lanjii.biz.admin.system.model.vo.LoginInfo;
import com.lanjii.security.AuthUser;

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