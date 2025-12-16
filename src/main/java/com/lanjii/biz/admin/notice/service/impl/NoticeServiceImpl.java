package com.lanjii.biz.admin.notice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lanjii.biz.admin.notice.dao.NoticeDao;
import com.lanjii.biz.admin.notice.dao.NoticeReadRecordDao;
import com.lanjii.biz.admin.notice.model.dto.NoticeDTO;
import com.lanjii.biz.admin.notice.model.entity.Notice;
import com.lanjii.biz.admin.notice.model.entity.NoticeReadRecord;
import com.lanjii.biz.admin.notice.model.vo.NoticeVO;
import com.lanjii.biz.admin.notice.model.vo.UnreadCountVO;
import com.lanjii.biz.admin.notice.service.NoticeService;
import com.lanjii.biz.admin.notice.service.NoticeWebSocketService;
import com.lanjii.biz.admin.system.dao.SysUserDao;
import com.lanjii.biz.admin.system.model.entity.SysUser;
import com.lanjii.common.exception.BizException;
import com.lanjii.core.base.BaseServiceImpl;
import com.lanjii.core.resp.ResultCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统通知公告表(Notice)服务实现类
 *
 * @author lanjii
 */
@RequiredArgsConstructor
@Service
public class NoticeServiceImpl extends BaseServiceImpl<NoticeDao, Notice> implements NoticeService {

    private final NoticeReadRecordDao noticeReadRecordDao;
    private final SysUserDao sysUserDao;
    private final NoticeWebSocketService noticeWebSocketService;

    @Override
    public List<NoticeVO> listByFilter(NoticeDTO filter, Long userId) {
        return baseMapper.listWithReadStatus(filter, userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public NoticeVO getByIdWithReadStatus(Long id, Long userId) {
        // 直接使用自定义SQL查询
        NoticeVO vo = baseMapper.getByIdWithReadStatus(id, userId);
        if (vo == null) {
            throw BizException.validationError(ResultCode.NOT_FOUND, "通知不存在");
        }
        
        // 如果是未读状态，自动标记为已读
        if (vo.getReadStatus() != null && vo.getReadStatus() == 0) {
            // 检查是否已读
            LambdaQueryWrapper<NoticeReadRecord> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(NoticeReadRecord::getNoticeId, id);
            wrapper.eq(NoticeReadRecord::getUserId, userId);
            Long count = noticeReadRecordDao.selectCount(wrapper);
            
            if (count == 0) {
                // 插入阅读记录
                NoticeReadRecord record = new NoticeReadRecord();
                record.setNoticeId(id);
                record.setUserId(userId);
                record.setReadTime(LocalDateTime.now());
                noticeReadRecordDao.insert(record);
                
                // 更新VO的已读状态
                vo.setReadStatus(1);
                vo.setReadTime(LocalDateTime.now());
                
                // 推送最新未读数给用户
                SysUser user = sysUserDao.selectById(userId);
                if (user != null) {
                    Long unreadCount = baseMapper.countUnread(userId);
                    noticeWebSocketService.sendUnreadCountToUser(user.getUsername(), unreadCount != null ? unreadCount : 0L);
                }
            }
        }
        
        return vo;
    }

    @Override
    public UnreadCountVO getUnreadCount(Long userId) {
        // 直接使用自定义SQL统计
        Long unreadCount = baseMapper.countUnread(userId);
        return new UnreadCountVO(unreadCount != null ? unreadCount : 0L);
    }

    @Override
    public List<NoticeVO> getRecentNotices(Long userId, Integer limit, Integer readStatus) {
        if (limit == null || limit <= 0) {
            limit = 5;
        }
        return baseMapper.listRecentNotices(userId, limit, readStatus);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void markAsRead(Long noticeId, Long userId) {
        // 检查通知是否存在
        Notice notice = this.getById(noticeId);
        if (notice == null) {
            throw BizException.validationError(ResultCode.NOT_FOUND, "通知不存在");
        }
        
        // 检查是否已读
        LambdaQueryWrapper<NoticeReadRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(NoticeReadRecord::getNoticeId, noticeId);
        wrapper.eq(NoticeReadRecord::getUserId, userId);
        Long count = noticeReadRecordDao.selectCount(wrapper);
        
        if (count == 0) {
            // 插入阅读记录
            NoticeReadRecord record = new NoticeReadRecord();
            record.setNoticeId(noticeId);
            record.setUserId(userId);
            record.setReadTime(LocalDateTime.now());
            noticeReadRecordDao.insert(record);
            
            // 推送最新未读数给用户
            SysUser user = sysUserDao.selectById(userId);
            if (user != null) {
                Long unreadCount = baseMapper.countUnread(userId);
                noticeWebSocketService.sendUnreadCountToUser(user.getUsername(), unreadCount != null ? unreadCount : 0L);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int markAllAsRead(Long userId, String level) {
        // 查询所有未读通知ID
        LambdaQueryWrapper<Notice> noticeWrapper = new LambdaQueryWrapper<>();
        noticeWrapper.eq(Notice::getStatus, 1);
        List<Notice> noticeList = this.list(noticeWrapper);
        List<Long> noticeIds = noticeList.stream().map(Notice::getId).collect(Collectors.toList());
        
        if (noticeIds.isEmpty()) {
            return 0;
        }
        
        // 批量插入阅读记录
        int result = noticeReadRecordDao.batchInsertReadRecords(userId, noticeIds, null);
        
        // 推送最新未读数给用户（批量标记后通常为0）
        SysUser user = sysUserDao.selectById(userId);
        if (user != null) {
            Long unreadCount = baseMapper.countUnread(userId);
            noticeWebSocketService.sendUnreadCountToUser(user.getUsername(), unreadCount != null ? unreadCount : 0L);
        }
        
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long publishNotice(NoticeDTO dto, Long publisherId) {
        // 创建通知实体
        Notice notice = Notice.INSTANCE.toEntity(dto);
        notice.setPublisherId(publisherId);
        
        // 获取发布人姓名
        SysUser publisher = sysUserDao.selectById(publisherId);
        if (publisher != null) {
            notice.setPublisherName(publisher.getNickname());
        }
        
        notice.setPublishTime(LocalDateTime.now());
        notice.setStatus(1); // 已发布
        
        // 保存到数据库
        this.save(notice);
        
        // 1. 广播通知内容给所有用户
        noticeWebSocketService.broadcast(notice);
        
        // 2. 推送未读数给所有在线用户（每个用户的未读数不同）
        noticeWebSocketService.broadcastUnreadCountToAllOnlineUsers();
        
        return notice.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveNotice(NoticeDTO dto) {
        Notice notice = Notice.INSTANCE.toEntity(dto);
        // 草稿状态
        if (dto.getStatus() == null) {
            notice.setStatus(0);
        }
        this.save(notice);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateNotice(Long id, NoticeDTO dto) {
        Notice notice = this.getById(id);
        if (notice == null) {
            throw BizException.validationError(ResultCode.NOT_FOUND, "通知不存在");
        }
        
        // 只有草稿状态的通知才能更新
        if (notice.getStatus() != null && notice.getStatus() != 0) {
            throw BizException.validationError(ResultCode.BAD_REQUEST, "已发布的通知不允许修改");
        }
        
        Notice updateEntity = Notice.INSTANCE.toEntity(dto);
        updateEntity.setId(id);
        this.updateById(updateEntity);
        
        // 如果是从草稿发布，推送给所有在线用户
        if (notice.getStatus() == 0 && dto.getStatus() != null && dto.getStatus() == 1) {
            Notice publishedNotice = this.getById(id);
            noticeWebSocketService.broadcast(publishedNotice);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteNotice(Long id) {
        Notice notice = this.getById(id);
        if (notice == null) {
            throw BizException.validationError(ResultCode.NOT_FOUND, "通知不存在");
        }
        if (notice.getStatus() != null && notice.getStatus() != 0) {
            throw BizException.validationError(ResultCode.BAD_REQUEST, "已发布的通知不允许删除");
        }
        this.removeById(id);
    }


}
