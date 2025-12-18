package com.lanjii.biz.admin.notice.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lanjii.biz.admin.notice.model.dto.NoticeDTO;
import com.lanjii.biz.admin.notice.model.entity.Notice;
import com.lanjii.biz.admin.notice.model.vo.NoticeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统通知公告表(Notice)数据库访问层
 *
 * @author lanjii
 */
public interface NoticeDao extends BaseMapper<Notice> {

    /**
     * 查询通知列表（带已读状态）
     *
     * @param filter 查询条件
     * @param userId 当前用户ID
     * @return 通知列表
     */
    List<NoticeVO> listWithReadStatus(@Param("filter") NoticeDTO filter, 
                                       @Param("userId") Long userId);

    /**
     * 根据ID查询通知（带已读状态）
     *
     * @param noticeId 通知ID
     * @param userId   当前用户ID
     * @return 通知详情
     */
    NoticeVO getByIdWithReadStatus(@Param("noticeId") Long noticeId, 
                                    @Param("userId") Long userId);

    /**
     * 统计未读数量
     *
     * @param userId 当前用户ID
     * @return 未读数量
     */
    Long countUnread(@Param("userId") Long userId);

    /**
     * 查询最近通知（支持按阅读状态筛选）
     *
     * @param userId 当前用户ID
     * @param limit  限制数量
     * @param readStatus 阅读状态：0-未读, 1-已读，null-全部
     * @return 最近通知列表，按发布时间倒序
     */
    List<NoticeVO> listRecentNotices(@Param("userId") Long userId, 
                                      @Param("limit") Integer limit,
                                      @Param("readStatus") Integer readStatus);

}
