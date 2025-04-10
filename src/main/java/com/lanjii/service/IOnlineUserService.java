package com.lanjii.service;

import com.lanjii.core.base.IBaseService;
import com.lanjii.model.entity.OnlineUser;

/**
 * 在线用户 服务类
 *
 * @author lizheng
 * @date 2025-04-09
 */
public interface IOnlineUserService extends IBaseService<OnlineUser> {

    void saveNew(OnlineUser onlineUser);

}
