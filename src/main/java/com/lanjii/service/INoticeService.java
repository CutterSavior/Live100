package com.lanjii.service;

import com.lanjii.core.base.IBaseService;
import com.lanjii.core.base.support.OrderParam;
import com.lanjii.core.base.support.PageParam;
import com.lanjii.model.dto.NoticeDto;
import com.lanjii.model.entity.Notice;
import com.lanjii.model.vo.NoticeVo;

import java.util.List;

/**
 * 通知 服务类
 *
 * @author lizheng
 * @date 2024-11-12
 */
public interface INoticeService extends IBaseService<Notice> {

    void saveNew(NoticeDto noticeDto);

    /**
     * 我的未读通知数
     */
    Integer myUnreadCount();

    Integer unreadCountByUserId(Long userId);

    Notice read(Long noticeId);

    /**
     * 我的消息通知列表
     */
    List<NoticeVo> myList(NoticeDto noticeFilter, PageParam pageParam, OrderParam orderParam);
}
