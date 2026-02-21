package com.lanjii.sys.service;

import com.lanjii.sys.entity.SysNotice;
import com.lanjii.system.api.dto.NoticeDTO;
import com.lanjii.system.api.vo.NoticeVO;
import com.lanjii.system.api.vo.UnreadCountVO;
import com.lanjii.framework.mp.base.BaseService;

import java.util.List;

/**
 * 系统通知公告表(SysNotice)服务接口
 *
 * @author lanjii
 */
public interface SysNoticeService extends BaseService<SysNotice> {

    /**
     * 分页查询通知列表（带已读状态）
     *
     * @param filter 查询条件
     * @param userId 当前用户ID
     * @return 通知列表
     */
    List<NoticeVO> listByFilter(NoticeDTO filter, Long userId);

    /**
     * 获取通知详情
     *
     * @param id     通知ID
     * @param userId 当前用户ID
     * @return 通知详情
     */
    NoticeVO getByIdWithReadStatus(Long id, Long userId);

    /**
     * 获取未读数统计
     *
     * @param userId 当前用户ID
     * @return 未读数统计
     */
    UnreadCountVO getUnreadCount(Long userId);

    /**
     * 获取最近通知
     *
     * @param userId 当前用户ID
     * @param limit  获取数量
     * @param readStatus 阅读状态：0-未读, 1-已读，null-全部
     * @return 最近通知列表
     */
    List<NoticeVO> getRecentNotices(Long userId, Integer limit, Integer readStatus);

    /**
     * 标记单条通知为已读
     *
     * @param noticeId 通知ID
     * @param userId   当前用户ID
     */
    void markAsRead(Long noticeId, Long userId);

    /**
     * 标记全部通知为已读
     *
     * @param userId 当前用户ID
     * @param level  级别（可选）
     * @return 标记数量
     */
    int markAllAsRead(Long userId, String level);

    /**
     * 发布通知
     *
     * @param dto         通知DTO
     * @param publisherId 发布人ID
     * @return 通知ID
     */
    Long publishNotice(NoticeDTO dto, Long publisherId);

    /**
     * 保存通知（含草稿）
     *
     * @param dto 通知DTO
     */
    void saveNotice(NoticeDTO dto);

    /**
     * 更新通知
     *
     * @param id  通知ID
     * @param dto 通知DTO
     */
    void updateNotice(Long id, NoticeDTO dto);

    /**
     * 删除通知（逻辑删除）
     *
     * @param id 通知ID
     */
    void deleteNotice(Long id);

}
