package com.lanjii.sys.controller;

import com.lanjii.sys.service.UserSessionService;
import com.lanjii.common.support.PageParam;
import com.lanjii.common.support.PageData;
import com.lanjii.common.response.R;
import com.lanjii.system.api.vo.UserSessionInfo;
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
    @PostMapping("/kick/{displayUuid}")
    public R<Void> kickSession(@PathVariable String displayUuid) {
        userSessionService.kickSession(displayUuid);
        return R.success();
    }

}
