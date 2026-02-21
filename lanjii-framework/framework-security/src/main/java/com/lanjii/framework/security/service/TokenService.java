package com.lanjii.framework.security.service;

/**
 * Token 服务接口
 *
 * @author lanjii
 */
public interface TokenService {

    /**
     * 验证 Token 是否有效
     *
     * @param token JWT Token
     */
    boolean validate(String token);
}
