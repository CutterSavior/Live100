package com.lanjii.task;

import com.lanjii.service.IOnlineUserService;
import com.lanjii.util.LocalCacheUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 在线用户任务
 *
 * @author lizheng
 * @date 2025-04-10
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OnlineUserTask {

    private final IOnlineUserService onlineUserService;

    @Scheduled(fixedRate = 10000)
    public void onlineUserTask() {
        onlineUserService.list().forEach(onlineUser -> {
            Object onlineUserCache = LocalCacheUtils.get(LocalCacheUtils.CacheType.ONLINE_USER, onlineUser.getToken());
            Integer onlineStatus = onlineUser.getOnlineStatus();
            if (onlineStatus == 1 && onlineUserCache == null) {
                log.info("{}离线了", onlineUser.getRealName());
                onlineUser.setOnlineStatus(0);
            }
            if (onlineStatus == 0 && onlineUserCache != null) {
                log.info("{}上线了", onlineUser.getRealName());
                onlineUser.setOnlineStatus(1);
            }
            onlineUserService.saveOrUpdate(onlineUser);
        });
    }
}
