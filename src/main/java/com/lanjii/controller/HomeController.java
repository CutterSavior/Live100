package com.lanjii.controller;

import com.lanjii.core.obj.R;
import com.lanjii.service.INoticeService;
import com.lanjii.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 后台首页数据展示
 *
 * @author lizheng
 * @date 2025-03-28
 */
@Slf4j
@RestController
@RequestMapping("/admin/home")
@RequiredArgsConstructor
public class HomeController {

    private final ISysUserService sysUserService;
    private final INoticeService noticeService;

    @GetMapping("/count")
    public R<Map<String, Integer>> getUserCount() {
        return R.success(Map.of("userCount", sysUserService.count(), "noticeCount", noticeService.count()));
    }


}
