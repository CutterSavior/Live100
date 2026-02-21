package com.lanjii.sys.service.impl;

import com.lanjii.framework.security.model.AuthUser;
import com.lanjii.framework.security.util.JwtUtils;
import com.lanjii.framework.web.util.ServletUtils;
import com.lanjii.sys.config.SystemCacheConstants;
import com.lanjii.sys.entity.SysUser;
import com.lanjii.sys.service.UserSessionService;
import com.lanjii.sys.service.LoginService;
import com.lanjii.sys.service.SysMenuService;
import com.lanjii.sys.service.SysUserService;
import com.lanjii.system.api.vo.LoginInfo;
import com.lanjii.system.api.vo.SysMenuVO;
import com.lanjii.system.api.vo.SysUserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.baomidou.mybatisplus.extension.toolkit.Db.updateById;

/**
 * 用户认证服务实现类
 *
 * @author lanjii
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final SysUserService sysUserService;
    private final SysMenuService sysMenuService;
    private final JwtUtils jwtUtils;
    private final UserSessionService userSessionService;
    private final CacheManager cacheManager;

    private Cache getUserInfoCache() {
        return cacheManager.getCache(SystemCacheConstants.USER_INFO.getName());
    }

    @Transactional(rollbackFor = Exception.class)
    public LoginInfo generateLoginInfo(AuthUser userDetails) {
        String token = jwtUtils.generateToken(userDetails.getUsername(), userDetails.getTenantId(), userDetails.getPackageId());
        Long userId = userDetails.getUserId();

        // 更新用户最后登录时间和IP
        updateUserLoginInfo(userId);

        getUserInfoCache().put(userDetails.getUsername(), userDetails);
        userSessionService.recordUserSession(token);

        // 获取菜单树（过滤掉按钮类型）
        Long tenantId = userDetails.getTenantId();
        Long packageId = userDetails.getPackageId();
        List<SysMenuVO> menuTree = sysMenuService.getUserMenuTree(userId, userDetails.getIsAdmin(), tenantId, packageId);
        // 获取权限字符列表
        List<String> permissions = sysMenuService.getUserPermissions(userId, userDetails.getIsAdmin(), tenantId, packageId)
                .stream()
                .flatMap(perm -> Arrays.stream(perm.split(",")))
                .collect(Collectors.toList());

        String displayUuid = userSessionService.getSessionDisplayUuid(token);

        SysUser sysUser = sysUserService.getById(userId);
        SysUserVO sysUserVO = SysUser.INSTANCE.toVo(sysUser);
        return new LoginInfo(token, menuTree, sysUserVO, permissions, displayUuid);
    }

    /**
     * 更新用户最后登录时间和IP
     *
     * @param userId 用户ID
     */
    private void updateUserLoginInfo(Long userId) {
        String clientIp = ServletUtils.getClientIpAddress();
        Date currentTime = new Date();

        SysUser updateUser = new SysUser();
        updateUser.setId(userId);
        updateUser.setLastLoginTime(currentTime);
        updateUser.setLastLoginIp(clientIp);

        updateById(updateUser);
    }

    @Override
    public void logout() {
        String token = ServletUtils.getBearerToken();
        if (StringUtils.isNotEmpty(token)) {
            if (jwtUtils.validateToken(token)) {
                String username = jwtUtils.getUsernameFromToken(token);
                userSessionService.removeUserSession(token);
                getUserInfoCache().evict(username);
                String clientIp = ServletUtils.getClientIpAddress();
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