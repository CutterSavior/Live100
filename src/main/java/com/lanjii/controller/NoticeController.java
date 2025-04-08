package com.lanjii.controller;

import com.github.pagehelper.PageHelper;
import com.lanjii.core.annotation.MultiRequestBody;
import com.lanjii.core.base.support.OrderParam;
import com.lanjii.core.base.support.PageData;
import com.lanjii.core.base.support.PageParam;
import com.lanjii.core.enums.ResultCode;
import com.lanjii.core.obj.R;
import com.lanjii.util.ModelUtils;
import com.lanjii.util.PageDataUtils;
import com.lanjii.model.dto.NoticeDto;
import com.lanjii.model.entity.Notice;
import com.lanjii.service.INoticeService;
import com.lanjii.model.vo.NoticeVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.List;
import java.util.Map;

/**
 * 通知
 *
 * @author lizheng
 * @date 2024-11-12
 */
@Slf4j
@RestController
@RequestMapping("/admin/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final INoticeService noticeService;
    private final SimpMessagingTemplate messagingTemplate;

    /**
     * 用户连接时主动推送未读数
     */
    @EventListener
    public void handleWebSocketConnectListener(SessionSubscribeEvent event) {
        StompHeaderAccessor headers = StompHeaderAccessor.wrap(event.getMessage());
        String userId = headers.getUser().getName();
        int unreadCount = noticeService.unreadCountByUserId(Long.parseLong(userId));
        messagingTemplate.convertAndSendToUser(userId, "/queue/unread", unreadCount);
    }

    /**
     * 读自己的通知
     */
    @PostMapping("/read")
    public R<NoticeVo> read(@MultiRequestBody Long id) {
        if (id == null) {
            return R.fail(ResultCode.DATA_NOT_EXIST);
        }
        return R.success(Notice.INSTANCE.modelToVo(noticeService.read(id)));
    }

    /**
     * 我的未读消息数量
     */
    @GetMapping("/my/unreadCount")
    public R<Object> myUnreadCount() {
        Integer count = noticeService.myUnreadCount();
        return R.success(Map.of("unreadCount", count));
    }

    /**
     * 我的通知列表
     */
    @PostMapping("/myList")
    public R<PageData<NoticeVo>> myList(@MultiRequestBody(required = false) PageParam pageParam, @MultiRequestBody(required = false) OrderParam orderParam,
                                        @MultiRequestBody(required = false) NoticeDto noticeFilter) {

        return R.success(PageDataUtils.make(noticeService.myList(noticeFilter, pageParam, orderParam)));
    }

    /**
     * 详情
     */
    @GetMapping("/query")
    public R<Notice> query(Long id) {
        return R.success(noticeService.getById(id));
    }

    /**
     * 列表
     */
    @PostMapping("/list")
    public R<PageData<NoticeVo>> list(@MultiRequestBody(required = false) PageParam pageParam, @MultiRequestBody(required = false) OrderParam orderParam,
                                      @MultiRequestBody(required = false) NoticeDto noticeFilter) {
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<Notice> list = noticeService.getListByFilter(noticeFilter, orderParam);
        return R.success(PageDataUtils.make(list, Notice.INSTANCE));
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R<Notice> save(@MultiRequestBody NoticeDto noticeDto) {
        noticeService.saveNew(noticeDto);
        return R.success();
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    public R<Notice> update(@MultiRequestBody NoticeDto noticeDto) {
        Notice originalData = noticeService.getById(noticeDto.getId());
        if (originalData == null) {
            return R.fail(ResultCode.DATA_NOT_EXIST);
        }
        Notice notice = ModelUtils.copyTo(noticeDto, Notice.class);
        noticeService.updateById(notice);
        return R.success();
    }

    /**
     * 删除
     */
    @GetMapping("/del")
    public R<Notice> delete(Long id) {
        noticeService.removeById(id);
        return R.success();
    }

}
