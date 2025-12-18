package com.lanjii.websocket.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * WebSocket 通用消息推送服务
 *
 * @author lanjii
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class WebSocketMessageService {

    private final SimpMessagingTemplate messagingTemplate;

    /**
     * 发送消息给指定用户
     *
     * @param userId      用户ID
     * @param destination 目标队列路径，如：/queue/notices, /queue/messages
     * @param message     消息内容
     */
    public void sendToUser(Long userId, String destination, Object message) {
        try {
            messagingTemplate.convertAndSendToUser(
                userId.toString(),
                destination,
                message
            );
        } catch (Exception e) {
            log.error("推送消息给用户失败，用户ID：{}，目标：{}", userId, destination, e);
        }
    }

    /**
     * 发送消息给指定用户（字符串用户ID）
     *
     * @param userId      用户ID字符串
     * @param destination 目标队列路径
     * @param message     消息内容
     */
    public void sendToUser(String userId, String destination, Object message) {
        messagingTemplate.convertAndSendToUser(userId, destination, message);
    }

    /**
     * 广播消息给所有用户
     *
     * @param destination 目标主题路径，如：/topic/notices, /topic/announcements
     * @param message     消息内容
     */
    public void broadcast(String destination, Object message) {
        messagingTemplate.convertAndSend(destination, message);
    }

    /**
     * 发送通知给指定用户（便捷方法）
     *
     * @param userId 用户ID
     * @param notice 通知对象
     */
    public void sendNoticeToUser(Long userId, Object notice) {
        sendToUser(userId, "/queue/notices", notice);
    }

    /**
     * 广播通知给所有用户（便捷方法）
     *
     * @param notice 通知对象
     */
    public void broadcastNotice(Object notice) {
        broadcast("/topic/notices", notice);
    }

    /**
     * 发送个人消息给指定用户（便捷方法）
     *
     * @param userId  用户ID
     * @param message 消息对象
     */
    public void sendMessageToUser(Long userId, Object message) {
        sendToUser(userId, "/queue/messages", message);
    }

    /**
     * 发送系统提醒给指定用户（便捷方法）
     *
     * @param userId 用户ID
     * @param alert  提醒对象
     */
    public void sendAlertToUser(Long userId, Object alert) {
        sendToUser(userId, "/queue/alerts", alert);
    }
}
