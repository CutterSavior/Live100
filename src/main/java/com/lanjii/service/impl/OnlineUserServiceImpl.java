package com.lanjii.service.impl;

import com.lanjii.core.base.BaseServiceImpl;
import com.lanjii.core.base.support.OrderParam;
import com.lanjii.core.base.support.PageData;
import com.lanjii.core.base.support.PageParam;
import com.lanjii.dao.OnlineUserMapper;
import com.lanjii.model.dto.OnlineUserDto;
import com.lanjii.model.entity.OnlineUser;
import com.lanjii.model.vo.OnlineUserVo;
import com.lanjii.service.IOnlineUserService;
import com.lanjii.util.IpUtils;
import com.lanjii.util.LocalCacheUtils;
import com.lanjii.util.ServletUtils;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 在线用户 服务实现类
 * 使用缓存而非数据库存储在线用户信息
 *
 * @author lizheng
 * @date 2025-04-09
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OnlineUserServiceImpl extends BaseServiceImpl<OnlineUserMapper, OnlineUser> implements IOnlineUserService {

    // 用户ID到Token的映射，用于踢下线功能
    private static final Map<Long, String> USER_ID_TO_TOKEN_MAP = new ConcurrentHashMap<>();

    @Override
    public void putOnlineUser(OnlineUser user, String token) {
        if (user == null || StringUtils.isBlank(token)) {
            return;
        }

        // 填充客户端信息
        HttpServletRequest httpRequest = ServletUtils.getHttpRequest();
        if (httpRequest != null) {
            String clientIp = IpUtils.getClientIp(httpRequest);
            UserAgent userAgent = UserAgent.parseUserAgentString(httpRequest.getHeader("User-Agent"));
            user.setBrowser(userAgent.getBrowser().getName());
            user.setIpAddress(clientIp);
            user.setLocation(IpUtils.getLocation(clientIp));
        }

        // 设置在线状态和最后活动时间
        user.setOnlineStatus(1);
        user.setLastActiveTime(new Date());

        // 存入缓存
        LocalCacheUtils.put(LocalCacheUtils.CacheType.ONLINE_USER, token, user);

        // 更新用户ID到Token的映射
        if (user.getUserid() != null) {
            USER_ID_TO_TOKEN_MAP.put(user.getUserid(), token);
        }
    }

    @Override
    public PageData<OnlineUserVo> getOnlineUserList(PageParam pageParam, OrderParam orderParam, OnlineUserDto filter) {
        // 从缓存中获取所有在线用户
        Map<String, OnlineUser> allOnlineUsers = getAllOnlineUsersFromCache();
        List<OnlineUser> onlineUserList = new ArrayList<>(allOnlineUsers.values());

        // 应用过滤条件
        List<OnlineUser> filteredList = filterOnlineUsers(onlineUserList, filter);

        // 应用排序
        sortOnlineUsers(filteredList, orderParam);

        // 应用分页
        int pageSize = pageParam.getPageSize();
        int pageNum = pageParam.getPageNum();
        int total = filteredList.size();

        int fromIndex = (pageNum - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, total);

        // 防止越界
        if (fromIndex >= total) {
            fromIndex = 0;
            toIndex = 0;
        }

        List<OnlineUser> pagedList = fromIndex < toIndex
                ? filteredList.subList(fromIndex, toIndex)
                : new ArrayList<>();

        // 转换为VO
        List<OnlineUserVo> voList = pagedList.stream()
                .map(OnlineUser.INSTANCE::modelToVo)
                .collect(Collectors.toList());

        // 创建并返回分页数据
        PageData<OnlineUserVo> pageData = new PageData<>();
        pageData.setTotal((long) total);
        pageData.setList(voList);

        return pageData;
    }

    @Override
    public OnlineUserVo getOnlineUserById(Long userId) {
        if (userId == null) {
            return null;
        }

        // 从用户ID到Token的映射中获取Token
        String token = USER_ID_TO_TOKEN_MAP.get(userId);
        if (token == null) {
            return null;
        }

        // 从缓存中获取用户信息
        OnlineUser onlineUser = LocalCacheUtils.get(LocalCacheUtils.CacheType.ONLINE_USER, token);
        if (onlineUser == null) {
            // 如果缓存中没有，清除映射
            USER_ID_TO_TOKEN_MAP.remove(userId);
            return null;
        }

        return OnlineUser.INSTANCE.modelToVo(onlineUser);
    }

    @Override
    public boolean kickOut(Long userId, Long operatorId) {
        // 不能踢自己下线
        if (Objects.equals(userId, operatorId)) {
            log.warn("用户[{}]尝试踢自己下线，操作被拒绝", operatorId);
            return false;
        }

        // 获取用户Token
        String token = USER_ID_TO_TOKEN_MAP.get(userId);
        if (token == null) {
            log.info("用户[{}]没有在线会话，无需踢下线", userId);
            return false;
        }

        // 获取用户信息（用于获取用户名）
        OnlineUser onlineUser = LocalCacheUtils.get(LocalCacheUtils.CacheType.ONLINE_USER, token);

        // 清除token缓存
        LocalCacheUtils.invalidate(LocalCacheUtils.CacheType.ONLINE_USER, token);

        // 清除用户ID到Token的映射
        USER_ID_TO_TOKEN_MAP.remove(userId);

        // 清除用户认证缓存
        if (onlineUser != null && onlineUser.getUserName() != null) {
            // 清除认证信息缓存
            LocalCacheUtils.invalidate(LocalCacheUtils.CacheType.OTHER, "auth:" + onlineUser.getUserName());
            
            log.info("已清除用户[{}]的所有认证缓存", onlineUser.getUserName());
        }

        log.info("用户[{}]被管理员[{}]成功踢下线", userId, operatorId);
        return true;
    }

    @Override
    public void removeOnlineUser(String token, String username) {
        if (StringUtils.isBlank(token)) {
            return;
        }

        // 获取用户信息
        OnlineUser onlineUser = LocalCacheUtils.get(LocalCacheUtils.CacheType.ONLINE_USER, token);

        // 清除token缓存
        LocalCacheUtils.invalidate(LocalCacheUtils.CacheType.ONLINE_USER, token);

        // 清除用户ID到Token的映射
        if (onlineUser != null && onlineUser.getUserid() != null) {
            USER_ID_TO_TOKEN_MAP.remove(onlineUser.getUserid());
        }

        // 清除用户认证缓存
        if (StringUtils.isNotBlank(username)) {
            LocalCacheUtils.invalidate(LocalCacheUtils.CacheType.OTHER, "auth:" + username);
        }

        log.info("用户[{}]已登出，清除在线状态和认证信息", username);
    }

    /**
     * 从缓存中获取所有在线用户
     * @return 在线用户映射
     */
    private Map<String, OnlineUser> getAllOnlineUsersFromCache() {
        Map<String, Object> allItems = LocalCacheUtils.getAllItems(LocalCacheUtils.CacheType.ONLINE_USER);

        Map<String, OnlineUser> result = new HashMap<>();
        for (Map.Entry<String, Object> entry : allItems.entrySet()) {
            if (entry.getValue() instanceof OnlineUser) {
                result.put(entry.getKey(), (OnlineUser) entry.getValue());
            }
        }

        return result;
    }

    /**
     * 过滤在线用户列表
     * @param userList 用户列表
     * @param filter 过滤条件
     * @return 过滤后的列表
     */
    private List<OnlineUser> filterOnlineUsers(List<OnlineUser> userList, OnlineUserDto filter) {
        if (filter == null) {
            return userList;
        }

        return userList.stream()
                .filter(user -> {
                    // 按ID过滤
                    if (filter.getId() != null && !Objects.equals(user.getId(), filter.getId())) {
                        return false;
                    }

                    // 按用户ID过滤
                    if (filter.getUserid() != null && !Objects.equals(user.getUserid(), filter.getUserid())) {
                        return false;
                    }

                    // 按用户名过滤
                    if (StringUtils.isNotBlank(filter.getUserName()) &&
                            (user.getUserName() == null || !user.getUserName().contains(filter.getUserName()))) {
                        return false;
                    }

                    // 按姓名过滤
                    if (StringUtils.isNotBlank(filter.getRealName()) &&
                            (user.getRealName() == null || !user.getRealName().contains(filter.getRealName()))) {
                        return false;
                    }

                    // 按IP地址过滤
                    if (StringUtils.isNotBlank(filter.getIpAddress()) &&
                            (user.getIpAddress() == null || !user.getIpAddress().contains(filter.getIpAddress()))) {
                        return false;
                    }

                    // 按位置过滤
                    if (StringUtils.isNotBlank(filter.getLocation()) &&
                            (user.getLocation() == null || !user.getLocation().contains(filter.getLocation()))) {
                        return false;
                    }

                    // 按浏览器过滤
                    if (StringUtils.isNotBlank(filter.getBrowser()) &&
                            (user.getBrowser() == null || !user.getBrowser().contains(filter.getBrowser()))) {
                        return false;
                    }

                    // 按在线状态过滤
                    if (filter.getOnlineStatus() != null && !Objects.equals(user.getOnlineStatus(), filter.getOnlineStatus())) {
                        return false;
                    }

                    return true;
                })
                .collect(Collectors.toList());
    }

    /**
     * 对在线用户列表进行排序
     * @param userList 用户列表
     * @param orderParam 排序参数
     */
    private void sortOnlineUsers(List<OnlineUser> userList, OrderParam orderParam) {
        if (orderParam == null || StringUtils.isBlank(orderParam.getFieldName())) {
            // 默认按最后活动时间倒序
            userList.sort(Comparator.comparing(OnlineUser::getLastActiveTime, Comparator.nullsLast(Comparator.reverseOrder())));
            return;
        }

        // 获取排序方向
        boolean isAsc = Boolean.TRUE.equals(orderParam.getAsc());

        // 根据字段名排序
        switch (orderParam.getFieldName()) {
            case "id":
                userList.sort(isAsc ? Comparator.comparing(OnlineUser::getId, Comparator.nullsLast(Comparator.naturalOrder())) :
                        Comparator.comparing(OnlineUser::getId, Comparator.nullsLast(Comparator.reverseOrder())));
                break;
            case "userid":
                userList.sort(isAsc ? Comparator.comparing(OnlineUser::getUserid, Comparator.nullsLast(Comparator.naturalOrder())) :
                        Comparator.comparing(OnlineUser::getUserid, Comparator.nullsLast(Comparator.reverseOrder())));
                break;
            case "userName":
                userList.sort(isAsc ? Comparator.comparing(OnlineUser::getUserName, Comparator.nullsLast(Comparator.naturalOrder())) :
                        Comparator.comparing(OnlineUser::getUserName, Comparator.nullsLast(Comparator.reverseOrder())));
                break;
            case "realName":
                userList.sort(isAsc ? Comparator.comparing(OnlineUser::getRealName, Comparator.nullsLast(Comparator.naturalOrder())) :
                        Comparator.comparing(OnlineUser::getRealName, Comparator.nullsLast(Comparator.reverseOrder())));
                break;
            case "ipAddress":
                userList.sort(isAsc ? Comparator.comparing(OnlineUser::getIpAddress, Comparator.nullsLast(Comparator.naturalOrder())) :
                        Comparator.comparing(OnlineUser::getIpAddress, Comparator.nullsLast(Comparator.reverseOrder())));
                break;
            case "location":
                userList.sort(isAsc ? Comparator.comparing(OnlineUser::getLocation, Comparator.nullsLast(Comparator.naturalOrder())) :
                        Comparator.comparing(OnlineUser::getLocation, Comparator.nullsLast(Comparator.reverseOrder())));
                break;
            case "browser":
                userList.sort(isAsc ? Comparator.comparing(OnlineUser::getBrowser, Comparator.nullsLast(Comparator.naturalOrder())) :
                        Comparator.comparing(OnlineUser::getBrowser, Comparator.nullsLast(Comparator.reverseOrder())));
                break;
            case "lastActiveTime":
            default:
                userList.sort(isAsc ? Comparator.comparing(OnlineUser::getLastActiveTime, Comparator.nullsLast(Comparator.naturalOrder())) :
                        Comparator.comparing(OnlineUser::getLastActiveTime, Comparator.nullsLast(Comparator.reverseOrder())));
                break;
        }
    }
}
