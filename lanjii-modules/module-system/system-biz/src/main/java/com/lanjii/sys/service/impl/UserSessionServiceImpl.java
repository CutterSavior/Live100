package com.lanjii.sys.service.impl;

import com.lanjii.sys.service.UserSessionService;
import com.lanjii.system.api.vo.UserSessionInfo;
import com.lanjii.sys.config.SystemCacheConstants;
import com.lanjii.framework.cache.helper.CacheHelper;
import com.lanjii.framework.security.util.JwtUtils;
import com.lanjii.framework.web.util.ServletUtils;
import com.lanjii.common.support.PageParam;
import com.lanjii.common.support.PageData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
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
    private final CacheManager cacheManager;
    private final CacheHelper cacheHelper;

    private Cache getUserSessionCache() {
        return cacheManager.getCache(SystemCacheConstants.USER_SESSION.getName());
    }

    @Override
    public void recordUserSession(String token) {
        if (token != null && !token.trim().isEmpty()) {
            try {
                String username = jwtUtils.getUsernameFromToken(token);
                if (username != null) {
                    // 创建会话信息
                    UserSessionInfo sessionInfo = new UserSessionInfo(token, username);
                    sessionInfo.setClientIp(ServletUtils.getClientIpAddress());
                    sessionInfo.setUserAgent(ServletUtils.getUserAgent());

                    String deviceType = detectDeviceType(sessionInfo.getUserAgent());
                    sessionInfo.setDeviceType(deviceType);

                    // 存储会话信息到缓存
                    getUserSessionCache().put(token, sessionInfo);

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
        UserSessionInfo sessionInfo = getUserSessionCache().get(token, UserSessionInfo.class);
        return sessionInfo != null;
    }

    @Override
    public void removeUserSession(String token) {
        if (StringUtils.isNotEmpty(token)) {
            // 从缓存中移除
            UserSessionInfo sessionInfo = getUserSessionCache().get(token, UserSessionInfo.class);
            getUserSessionCache().evict(token);

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

        String currentDisplayUuid = getSessionDisplayUuid(ServletUtils.getBearerToken());

        // 防止踢出自己的会话
        if (displayUuid.equals(currentDisplayUuid)) {
            log.warn("不能踢出自己的会话，displayUuid: {}", displayUuid);
            return;
        }

        // 遍历缓存查找匹配displayUuid的会话
        Collection<UserSessionInfo> allSessions = cacheHelper.getAllValues(SystemCacheConstants.USER_SESSION.getName(), UserSessionInfo.class);
        for (UserSessionInfo session : allSessions) {
            if (displayUuid.equals(session.getDisplayUuid())) {
                removeUserSession(session.getToken());
                return;
            }
        }

        log.warn("会话不存在或已失效，displayUuid: {}", displayUuid);
    }

    @Override
    public PageData<UserSessionInfo> getAllSessionsPage(PageParam pageParam) {
        // 从缓存中获取所有有效会话
        Collection<UserSessionInfo> allSessions = cacheHelper.getAllValues(SystemCacheConstants.USER_SESSION.getName(), UserSessionInfo.class);
        List<UserSessionInfo> allSessionsList = allSessions.stream()
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
        Collection<UserSessionInfo> allSessions = cacheHelper.getAllValues(SystemCacheConstants.USER_SESSION.getName(), UserSessionInfo.class);
        return allSessions.stream()
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

        UserSessionInfo sessionInfo = getUserSessionCache().get(token, UserSessionInfo.class);
        return sessionInfo != null ? sessionInfo.getDisplayUuid() : null;
    }

    @Override
    public void setSessionActive(String token, boolean active) {
        if (StringUtils.isEmpty(token)) {
            return;
        }

        Cache cache = getUserSessionCache();
        UserSessionInfo sessionInfo = cache.get(token, UserSessionInfo.class);
        if (sessionInfo != null) {
            sessionInfo.setActive(active);
            cache.put(token, sessionInfo);
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
