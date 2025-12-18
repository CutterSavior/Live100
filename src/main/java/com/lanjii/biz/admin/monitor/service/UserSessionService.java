package com.lanjii.biz.admin.monitor.service;

import com.lanjii.biz.admin.system.model.vo.UserSessionInfo;
import com.lanjii.core.base.PageParam;
import com.lanjii.core.base.support.PageData;

import java.util.List;

/**
 * 用户会话管理服务接口
 *
 * @author lanjii
 */
public interface UserSessionService {

    /**
     * 记录用户会话
     *
     * @param token JWT token
     */
    void recordUserSession(String token);

    /**
     * 检查token是否有效（未过期且未被踢出）
     *
     * @param token JWT token
     * @return true-有效，false-无效
     */
    boolean isTokenValid(String token);

    /**
     * 移除用户会话（登出时调用）
     *
     * @param token JWT token
     */
    void removeUserSession(String token);

    /**
     * 踢出指定会话（强制下线）
     *
     * @param sessionId 会话标识
     */
    void kickSession(String sessionId);

    /**
     * 分页获取所有会话列表
     *
     * @param pageParam 分页参数
     * @return 分页会话信息
     */
    PageData<UserSessionInfo> getAllSessionsPage(PageParam pageParam);

    /**
     * 获取所有在线用户的用户名列表
     *
     * @return 在线用户名列表
     */
    List<String> getAllOnlineUsernames();

}
