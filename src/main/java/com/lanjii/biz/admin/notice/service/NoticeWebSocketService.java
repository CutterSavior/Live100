package com.lanjii.biz.admin.notice.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lanjii.biz.admin.monitor.service.UserSessionService;
import com.lanjii.biz.admin.notice.dao.NoticeDao;
import com.lanjii.biz.admin.notice.model.entity.Notice;
import com.lanjii.biz.admin.notice.model.vo.NoticeVO;
import com.lanjii.biz.admin.notice.model.vo.UnreadCountVO;
import com.lanjii.biz.admin.system.dao.SysUserDao;
import com.lanjii.biz.admin.system.model.entity.SysUser;
import com.lanjii.websocket.service.WebSocketMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 通知 WebSocket 推送服务
 *
 *
 * @author lanjii
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeWebSocketService {

    private final WebSocketMessageService webSocketMessageService;
    private final UserSessionService userSessionService;
    private final SysUserDao sysUserDao;
    private final NoticeDao noticeDao;

    /**
     * 推送通知给指定用户
     *
     * @param userId 用户ID
     * @param notice 通知实体
     */
    public void sendToUser(Long userId, Notice notice) {
        NoticeVO noticeVO = buildNoticeVO(notice);
        webSocketMessageService.sendNoticeToUser(userId, noticeVO);
    }

    /**
     * 推送通知给指定用户
     *
     * @param userId   用户ID
     * @param noticeVO 通知VO
     */
    public void sendToUser(Long userId, NoticeVO noticeVO) {
        webSocketMessageService.sendNoticeToUser(userId, noticeVO);
    }

    /**
     * 广播通知给所有用户
     *
     * @param notice 通知实体
     */
    public void broadcast(Notice notice) {
        NoticeVO noticeVO = buildNoticeVO(notice);
        webSocketMessageService.broadcastNotice(noticeVO);
    }

    /**
     * 广播通知给所有用户
     *
     * @param noticeVO 通知VO
     */
    public void broadcast(NoticeVO noticeVO) {
        webSocketMessageService.broadcastNotice(noticeVO);
    }

    /**
     * 推送未读数给指定用户
     *
     * @param username    用户名
     * @param unreadCount 未读数
     */
    public void sendUnreadCountToUser(String username, Long unreadCount) {
        UnreadCountVO vo = new UnreadCountVO(unreadCount);
        webSocketMessageService.sendToUser(username, "/queue/unread-count", vo);
    }

    /**
     * 推送未读数给所有在线用户
     * 用于发布新通知时，给每个在线用户推送各自的未读数
     */
    public void broadcastUnreadCountToAllOnlineUsers() {
        // 获取所有在线用户的用户名
        List<String> onlineUsernames = userSessionService.getAllOnlineUsernames();

        if (onlineUsernames.isEmpty()) {
            log.debug("当前没有在线用户，跳过未读数推送");
            return;
        }

        log.info("开始推送未读数给 {} 个在线用户", onlineUsernames.size());

        // 遍历每个在线用户
        for (String username : onlineUsernames) {
            // 根据用户名查询用户ID
            LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysUser::getUsername, username);
            SysUser user = sysUserDao.selectOne(wrapper);

            if (user != null) {
                // 查询该用户的未读数
                Long unreadCount = noticeDao.countUnread(user.getId());

                // 推送给该用户（使用用户名而不是用户ID）
                sendUnreadCountToUser(username, unreadCount != null ? unreadCount : 0L);
            }
        }

        log.info("未读数推送完成");

    }

    /**
     * 构建通知VO对象
     */
    private NoticeVO buildNoticeVO(Notice notice) {
        NoticeVO vo = new NoticeVO();
        vo.setId(notice.getId());
        vo.setTitle(notice.getTitle());
        vo.setContent(notice.getContent());
        vo.setPublisherName(notice.getPublisherName());
        vo.setPublishTime(notice.getPublishTime());
        return vo;
    }
}
