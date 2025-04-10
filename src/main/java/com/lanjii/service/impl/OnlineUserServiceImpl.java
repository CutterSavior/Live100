package com.lanjii.service.impl;

import com.lanjii.core.base.BaseServiceImpl;
import com.lanjii.dao.OnlineUserMapper;
import com.lanjii.model.entity.OnlineUser;
import com.lanjii.service.IOnlineUserService;
import com.lanjii.util.IpUtils;
import com.lanjii.util.ServletUtils;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * 在线用户 服务实现类
 *
 * @author lizheng
 * @date 2025-04-09
 */
@Service
@RequiredArgsConstructor
public class OnlineUserServiceImpl extends BaseServiceImpl<OnlineUserMapper, OnlineUser> implements IOnlineUserService {

    @Override
    public void saveNew(OnlineUser onlineUser) {
        HttpServletRequest httpRequest = ServletUtils.getHttpRequest();
        String clientIp = IpUtils.getClientIp(httpRequest);
        UserAgent userAgent = UserAgent.parseUserAgentString(httpRequest.getHeader("User-Agent"));
        onlineUser.setBrowser(userAgent.getBrowser().getName());
        onlineUser.setIpAddress(clientIp);
        onlineUser.setLocation(IpUtils.getLocation(clientIp));
        save(onlineUser);
    }
}
