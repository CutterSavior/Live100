package com.lanjii.sys.service;

import com.lanjii.sys.service.UserSessionService;
import com.lanjii.framework.security.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * TokenService 实现
 *
 * @author lanjii
 */
@Service
@RequiredArgsConstructor
public class WebTokenService implements TokenService {

    private final UserSessionService userSessionService;

    @Override
    public boolean validate(String token) {
        return userSessionService.isTokenValid(token);
    }
}
