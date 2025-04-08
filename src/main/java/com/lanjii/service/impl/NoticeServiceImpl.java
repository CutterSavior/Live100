package com.lanjii.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.lanjii.core.base.BaseServiceImpl;
import com.lanjii.core.base.support.OrderParam;
import com.lanjii.core.base.support.PageParam;
import com.lanjii.core.enums.NoticeToType;
import com.lanjii.util.AuthUtils;
import com.lanjii.util.ModelUtils;
import com.lanjii.dao.NoticeMapper;
import com.lanjii.model.dto.NoticeDto;
import com.lanjii.model.entity.Notice;
import com.lanjii.model.entity.NoticeUserRel;
import com.lanjii.model.entity.SysUser;
import com.lanjii.service.INoticeService;
import com.lanjii.service.INoticeUserRelService;
import com.lanjii.service.ISysUserService;
import com.lanjii.model.vo.NoticeVo;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 通知 服务实现类
 *
 * @author lizheng
 * @date 2024-11-12
 */
@Service
@RequiredArgsConstructor
public class NoticeServiceImpl extends BaseServiceImpl<NoticeMapper, Notice> implements INoticeService {

    private final SimpMessagingTemplate messagingTemplate;
    private final ISysUserService sysUserService;
    private final INoticeUserRelService noticeUserRelService;
    private final NoticeMapper noticeMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveNew(NoticeDto noticeDto) {

        Notice notice = ModelUtils.copyTo(noticeDto, Notice.class);
        save(notice);

        Integer toType = noticeDto.getToType();
        List<NoticeUserRel> rels = new ArrayList<>();
        if (toType.equals(NoticeToType.TO_ALL.getType())) {
            List<SysUser> sysUserList = sysUserService.list();
            for (SysUser user : sysUserList) {
                NoticeUserRel noticeUserRel = new NoticeUserRel();
                noticeUserRel.setNoticeId(notice.getId());
                noticeUserRel.setUserId(user.getId());
                noticeUserRel.setIsRead(0);
                rels.add(noticeUserRel);
                messagingTemplate.convertAndSendToUser(String.valueOf(user.getId()), "/queue/unread", unreadCountByUserId(user.getId()) + 1);
            }
            noticeUserRelService.saveBatch(rels);
        }
        if (toType.equals(NoticeToType.TO_USER.getType())) {
            List<Long> userIdList = noticeDto.getToIds();
            for (Long userId : userIdList) {
                NoticeUserRel noticeUserRel = new NoticeUserRel();
                noticeUserRel.setNoticeId(notice.getId());
                noticeUserRel.setUserId(userId);
                noticeUserRel.setIsRead(0);
                rels.add(noticeUserRel);
                messagingTemplate.convertAndSendToUser(String.valueOf(userId), "/queue/unread", unreadCountByUserId(userId) + 1);
            }
            noticeUserRelService.saveBatch(rels);
        }
    }

    @Override
    public Integer myUnreadCount() {
        Long currentUserId = AuthUtils.getCurrentUserId();
        return unreadCountByUserId(currentUserId);
    }

    @Override
    public Integer unreadCountByUserId(Long userId) {
        LambdaQueryWrapper<NoticeUserRel> noticeUserRelLambdaQueryWrapper = new LambdaQueryWrapper<>();
        noticeUserRelLambdaQueryWrapper.eq(NoticeUserRel::getUserId, userId);
        noticeUserRelLambdaQueryWrapper.eq(NoticeUserRel::getIsRead, 0);
        return noticeUserRelService.count(noticeUserRelLambdaQueryWrapper);
    }

    @Override
    public Notice read(Long noticeId) {
        Long currentUserId = AuthUtils.getCurrentUserId();
        LambdaQueryWrapper<NoticeUserRel> noticeUserRelLambdaQueryWrapper = new LambdaQueryWrapper<>();
        noticeUserRelLambdaQueryWrapper.eq(NoticeUserRel::getUserId, currentUserId);
        noticeUserRelLambdaQueryWrapper.eq(NoticeUserRel::getNoticeId, noticeId);
        NoticeUserRel noticeUserRel = noticeUserRelService.getOne(noticeUserRelLambdaQueryWrapper);
        noticeUserRel.setIsRead(1);
        noticeUserRelService.saveOrUpdate(noticeUserRel);
        messagingTemplate.convertAndSendToUser(String.valueOf(currentUserId), "/queue/unread", unreadCountByUserId(currentUserId));
        return getById(noticeId);
    }

    @Override
    public List<NoticeVo> myList(NoticeDto noticeFilter, PageParam pageParam, OrderParam orderParam) {
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        return noticeMapper.myList(AuthUtils.getCurrentUserId());
    }
}
