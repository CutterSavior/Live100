package com.lanjii.service;

import com.lanjii.core.base.IBaseService;
import com.lanjii.core.base.support.OrderParam;
import com.lanjii.core.base.support.PageData;
import com.lanjii.core.base.support.PageParam;
import com.lanjii.model.dto.OnlineUserDto;
import com.lanjii.model.entity.OnlineUser;
import com.lanjii.model.vo.OnlineUserVo;

import java.util.List;

/**
 * 在线用户 服务类
 *
 * @author lizheng
 * @date 2025-04-09
 */
public interface IOnlineUserService extends IBaseService<OnlineUser> {

    /**
     * 将用户信息存入缓存
     * @param user 用户信息
     * @param token 用户token
     */
    void putOnlineUser(OnlineUser user, String token);
    
    /**
     * 获取在线用户列表
     * @param pageParam 分页参数
     * @param orderParam 排序参数
     * @param filter 过滤条件
     * @return 在线用户列表
     */
    PageData<OnlineUserVo> getOnlineUserList(PageParam pageParam, OrderParam orderParam, OnlineUserDto filter);
    
    /**
     * 获取指定用户的在线信息
     * @param userId 用户ID
     * @return 在线用户信息，不存在时返回null
     */
    OnlineUserVo getOnlineUserById(Long userId);
    
    /**
     * 踢人下线
     * @param userId 用户ID
     * @param operatorId 操作者ID（不能踢自己）
     * @return 是否成功
     */
    boolean kickOut(Long userId, Long operatorId);
    
    /**
     * 清除用户的在线信息（登出时调用）
     * @param token 用户token
     * @param username 用户名
     */
    void removeOnlineUser(String token, String username);
}
