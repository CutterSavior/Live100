package com.lanjii.biz.admin.system.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.lanjii.biz.admin.monitor.service.UserSessionService;
import com.lanjii.biz.admin.system.dao.SysUserDao;
import com.lanjii.biz.admin.system.model.entity.SysUser;
import com.lanjii.biz.admin.system.model.vo.LoginInfo;
import com.lanjii.biz.admin.system.model.vo.SysMenuVO;
import com.lanjii.biz.admin.system.service.LoginService;
import com.lanjii.biz.admin.system.service.SysMenuService;
import com.lanjii.common.util.JwtUtils;
import com.lanjii.common.util.WebContextUtils;
import com.lanjii.security.AuthUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户认证服务实现类
 *
 * @author lanjii
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final SysUserDao sysUserDao;
    private final SysMenuService sysMenuService;
    private final JwtUtils jwtUtils;
    private final UserSessionService userSessionService;
    private final Cache<String, AuthUser> userInfoCache;

    @Transactional(rollbackFor = Exception.class)
    public LoginInfo generateLoginInfo(AuthUser userDetails) {
        String token = jwtUtils.generateToken(userDetails.getUsername());
        Long userId = userDetails.getUserId();

        // 更新用户最后登录时间和IP
        updateUserLoginInfo(userId);

        userInfoCache.put(userDetails.getUsername(), userDetails);
        userSessionService.recordUserSession(token);

        // 获取菜单树（过滤掉按钮类型）
        List<SysMenuVO> menuTree = sysMenuService.getUserMenuTree(userId, userDetails.getIsAdmin());
        // 获取权限字符列表
        List<String> permissions = sysMenuService.getUserPermissions(userId, userDetails.getIsAdmin())
                .stream()
                .flatMap(perm -> Arrays.stream(perm.split(",")))
                .collect(Collectors.toList());

        SysUser sysUser = sysUserDao.selectById(userId);
        return new LoginInfo(token, menuTree, sysUser, permissions);
    }

    /**
     * 更新用户最后登录时间和IP
     *
     * @param userId 用户ID
     */
    private void updateUserLoginInfo(Long userId) {
        String clientIp = WebContextUtils.getClientIpAddress();
        Date currentTime = new Date();

        SysUser updateUser = new SysUser();
        updateUser.setId(userId);
        updateUser.setLastLoginTime(currentTime);
        updateUser.setLastLoginIp(clientIp);

        sysUserDao.updateById(updateUser);
    }

    @Override
    public void logout() {
        String token = WebContextUtils.getBearerToken();
        if (StringUtils.isNotEmpty(token)) {
            if (jwtUtils.validateToken(token)) {
                String username = jwtUtils.getUsernameFromToken(token);
                userSessionService.removeUserSession(token);
                userInfoCache.invalidate(username);
                String clientIp = WebContextUtils.getClientIpAddress();
                log.info("用户 {} 已成功登出，客户端IP: {}", username, clientIp);
            } else {
                log.warn("登出时发现无效token，token前缀: {}",
                        token.length() > 20 ? token.substring(0, 20) : token);
            }
        } else {
            log.warn("登出请求中未找到有效的token");
        }
    }
}