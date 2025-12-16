package com.lanjii.biz.admin.monitor.controller;

import com.lanjii.biz.admin.monitor.service.UserSessionService;
import com.lanjii.core.base.PageParam;
import com.lanjii.core.base.support.PageData;
import com.lanjii.core.resp.R;
import com.lanjii.biz.admin.system.model.vo.UserSessionInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * 会话管理
 *
 * @author lanjii
 */
@Slf4j
@RestController
@RequestMapping("/admin/session")
@RequiredArgsConstructor
public class UserSessionController {

    private final UserSessionService userSessionService;

    /**
     * 分页查询
     */
    @PreAuthorize("hasAuthority('sys:session:page')")
    @GetMapping("/sessions")
    public R<PageData<UserSessionInfo>> getAllSessionsPage(PageParam pageParam) {
        PageData<UserSessionInfo> result = userSessionService.getAllSessionsPage(pageParam);
        return R.success(result);
    }

    /**
     * 踢出会话
     */
    @PreAuthorize("hasAuthority('sys:session:kick')")
    @PostMapping("/kick/{sessionId}")
    public R<Void> kickSession(@PathVariable String sessionId) {
        userSessionService.kickSession(sessionId);
        return R.success();
    }

}
