package com.lanjii.biz.admin.monitor.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.lanjii.biz.admin.monitor.service.UserSessionService;
import com.lanjii.biz.admin.system.model.vo.UserSessionInfo;
import com.lanjii.common.util.JwtUtils;
import com.lanjii.common.util.WebContextUtils;
import com.lanjii.core.base.PageParam;
import com.lanjii.core.base.support.PageData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户会话管理服务实现类
 *
 * @author lanjii
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserSessionServiceImpl implements UserSessionService {

    private final JwtUtils jwtUtils;
    private final Cache<String, UserSessionInfo> userSessionCache;

    @Override
    public void recordUserSession(String token) {
        if (token != null && !token.trim().isEmpty()) {
            try {
                String username = jwtUtils.getUsernameFromToken(token);
                if (username != null) {
                    // 创建会话信息
                    UserSessionInfo sessionInfo = new UserSessionInfo(token, username);
                    sessionInfo.setClientIp(WebContextUtils.getClientIpAddress());
                    sessionInfo.setUserAgent(WebContextUtils.getUserAgent());

                    String deviceType = detectDeviceType(sessionInfo.getUserAgent());
                    sessionInfo.setDeviceType(deviceType);

                    // 存储会话信息到缓存
                    userSessionCache.put(token, sessionInfo);

                    log.debug("已记录用户 {} 的会话到缓存，token前缀: {}", username, token.substring(0, Math.min(20, token.length())));
                }
            } catch (Exception e) {
                log.warn("无法从token中提取用户名: {}", e.getMessage());
            }
        }
    }

    @Override
    public boolean isTokenValid(String token) {
        if (token == null || token.trim().isEmpty()) {
            return false;
        }

        // 检查token是否过期
        try {
            if (!jwtUtils.validateToken(token) || jwtUtils.isTokenExpired(token)) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        // 从缓存中检查会话是否存在且有效
        UserSessionInfo sessionInfo = userSessionCache.getIfPresent(token);
        return sessionInfo != null;
    }

    @Override
    public void removeUserSession(String token) {
        if (StringUtils.isNotEmpty(token)) {
            // 从缓存中移除
            UserSessionInfo sessionInfo = userSessionCache.getIfPresent(token);
            userSessionCache.invalidate(token);

            if (sessionInfo != null) {
                log.info("已移除用户 {} 的会话，token前缀: {}", sessionInfo.getUsername(), token.substring(0, Math.min(20, token.length())));
            }
        }
    }

    @Override
    public void kickSession(String displayUuid) {
        if (StringUtils.isEmpty(displayUuid)) {
            return;
        }

        String currentDisplayUuid = getSessionDisplayUuid(WebContextUtils.getBearerToken());

        // 防止踢出自己的会话
        if (displayUuid.equals(currentDisplayUuid)) {
            log.warn("不能踢出自己的会话，displayUuid: {}", displayUuid);
            return;
        }

        // 遍历缓存查找匹配displayUuid的会话
        for (var entry : userSessionCache.asMap().entrySet()) {
            String token = entry.getKey();
            removeUserSession(token);
        }

        log.warn("会话不存在或已失效，displayUuid: {}", displayUuid);
    }

    @Override
    public PageData<UserSessionInfo> getAllSessionsPage(PageParam pageParam) {
        // 从缓存中获取所有有效会话
        List<UserSessionInfo> allSessionsList = userSessionCache.asMap().values().stream()
                .filter(sessionInfo -> isTokenValid(sessionInfo.getToken()))
                .sorted(Comparator.comparing(UserSessionInfo::getCreateTime).reversed())
                .collect(Collectors.toList());

        // 手动分页
        int total = allSessionsList.size();
        int pageNum = pageParam.getPageNum();
        int pageSize = pageParam.getPageSize();

        int start = (pageNum - 1) * pageSize;
        int end = Math.min(start + pageSize, total);

        List<UserSessionInfo> pageData = start < total ? allSessionsList.subList(start, end) : new ArrayList<>();

        // 对token进行脱敏处理
        pageData.forEach(UserSessionInfo::maskToken);

        // 返回PageData
        return new PageData<>((long) total, pageData);
    }

    @Override
    public List<String> getAllOnlineUsernames() {
        // 从缓存中获取所有有效在线用户的用户名
        return userSessionCache.asMap().values().stream()
                .filter(sessionInfo -> sessionInfo.getActive() && isTokenValid(sessionInfo.getToken()))
                .map(UserSessionInfo::getUsername)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public String getSessionDisplayUuid(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }

        UserSessionInfo sessionInfo = userSessionCache.getIfPresent(token);
        return sessionInfo != null ? sessionInfo.getDisplayUuid() : null;
    }

    @Override
    public void setSessionActive(String token, boolean active) {
        if (StringUtils.isEmpty(token)) {
            return;
        }

        UserSessionInfo sessionInfo = userSessionCache.getIfPresent(token);
        if (sessionInfo != null) {
            sessionInfo.setActive(active);
            userSessionCache.put(token, sessionInfo);
            log.debug("已更新用户 {} 的会话状态为: {}", sessionInfo.getUsername(), active ? "激活" : "非激活");
        }
    }

    /**
     * 检测设备类型
     */
    private String detectDeviceType(String userAgent) {
        if (userAgent == null) return "Unknown";

        String ua = userAgent.toLowerCase();
        if (ua.contains("mobile") || ua.contains("android") || ua.contains("iphone")) {
            return "Mobile";
        } else if (ua.contains("tablet") || ua.contains("ipad")) {
            return "Tablet";
        } else {
            return "PC";
        }
    }

}
