package com.lanjii.sys.controller;

import com.github.pagehelper.PageHelper;
import com.lanjii.sys.service.SysNoticeService;
import com.lanjii.system.api.dto.NoticeDTO;
import com.lanjii.system.api.vo.NoticeVO;
import com.lanjii.system.api.vo.UnreadCountVO;
import com.lanjii.common.enums.BusinessTypeEnum;
import com.lanjii.framework.mp.base.PageDataUtils;
import com.lanjii.framework.security.util.SecurityUtils;
import com.lanjii.framework.log.annotation.Log;
import com.lanjii.common.support.PageParam;
import com.lanjii.common.support.PageData;
import com.lanjii.common.response.R;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统通知公告管理
 *
 * @author lanjii
 */
@RestController
@RequestMapping("/admin/notice")
@RequiredArgsConstructor
public class SysNoticeController {

    private final SysNoticeService sysNoticeService;

    /**
     * 分页查询通知列表
     */
    @PreAuthorize("hasAuthority('notice:page')")
    @GetMapping
    public R<PageData<NoticeVO>> page(PageParam pageParam, NoticeDTO filter) {
        Long userId = SecurityUtils.getCurrentUserId();
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<NoticeVO> list = sysNoticeService.listByFilter(filter, userId);
        return R.success(PageDataUtils.make(list));
    }

    /**
     * 获取通知详情（自动标记为已读）
     */
    @GetMapping("/{id}")
    public R<NoticeVO> getById(@PathVariable Long id) {
        Long userId = SecurityUtils.getCurrentUserId();
        NoticeVO vo = sysNoticeService.getByIdWithReadStatus(id, userId);
        return R.success(vo);
    }

    /**
     * 获取未读数统计
     */
    @GetMapping("/unread-count")
    public R<UnreadCountVO> getUnreadCount() {
        Long userId = SecurityUtils.getCurrentUserId();
        UnreadCountVO vo = sysNoticeService.getUnreadCount(userId);
        return R.success(vo);
    }

    /**
     * 获取最近通知（用于铃铛下拉）
     * @param limit 限制数量
     * @param readStatus 阅读状态筛选：0-未读, 1-已读，不传则查询全部
     */
    @GetMapping("/recent")
    public R<List<NoticeVO>> getRecentNotices(@RequestParam(defaultValue = "5") Integer limit,
                                                @RequestParam(required = false) Integer readStatus) {
        Long userId = SecurityUtils.getCurrentUserId();
        List<NoticeVO> list = sysNoticeService.getRecentNotices(userId, limit, readStatus);
        return R.success(list);
    }

    /**
     * 标记全部通知为已读
     */
    @Log(title = "全部标记已读", businessType = BusinessTypeEnum.UPDATE)
    @PutMapping("/read-all")
    public R<Integer> markAllAsRead() {
        Long userId = SecurityUtils.getCurrentUserId();
        int count = sysNoticeService.markAllAsRead(userId, null);
        return R.success(count);
    }

    /**
     * 发布通知
     */
    @Log(title = "发布通知", businessType = BusinessTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('notice:publish')")
    @PostMapping
    public R<Long> publishNotice(@Valid @RequestBody NoticeDTO dto) {
        Long userId = SecurityUtils.getCurrentUserId();
        Long noticeId = sysNoticeService.publishNotice(dto, userId);
        return R.success(noticeId);
    }

    /**
     * 保存通知草稿
     */
    @Log(title = "保存通知草稿", businessType = BusinessTypeEnum.INSERT)
    @PreAuthorize("hasAuthority('notice:draft')")
    @PostMapping("/draft")
    public R<Void> saveDraft(@Valid @RequestBody NoticeDTO dto) {
        sysNoticeService.saveNotice(dto);
        return R.success();
    }

    /**
     * 更新通知
     */
    @Log(title = "更新通知", businessType = BusinessTypeEnum.UPDATE)
    @PreAuthorize("hasAuthority('notice:update')")
    @PutMapping("/{id}")
    public R<Void> updateNotice(@PathVariable Long id, @Valid @RequestBody NoticeDTO dto) {
        sysNoticeService.updateNotice(id, dto);
        return R.success();
    }

    /**
     * 删除通知
     */
    @Log(title = "删除通知", businessType = BusinessTypeEnum.DELETE)
    @PreAuthorize("hasAuthority('notice:delete')")
    @DeleteMapping("/{id}")
    public R<Void> deleteNotice(@PathVariable Long id) {
        sysNoticeService.deleteNotice(id);
        return R.success();
    }

}
