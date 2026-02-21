package com.lanjii.sys.controller;

import com.github.pagehelper.PageHelper;
import com.lanjii.common.support.PageParam;
import com.lanjii.common.support.PageData;
import com.lanjii.common.response.R;
import com.lanjii.system.api.dto.SysLoginLogDTO;
import com.lanjii.sys.entity.SysLoginLog;
import com.lanjii.sys.service.SysLoginLogService;
import com.lanjii.framework.mp.base.PageDataUtils;
import com.lanjii.system.api.vo.SysLoginLogVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户登录日志管理
 *
 * @author lanjii
 */
@RestController
@RequestMapping("/admin/sys/login-logs")
@RequiredArgsConstructor
public class SysLoginLogController {

    private final SysLoginLogService sysLoginLogService;

    /**
     * 查询详情
     *
     * @param id 数据 ID
     */
    @PreAuthorize("hasAuthority('sys:loginlog:view')")
    @GetMapping("/{id}")
    public R<SysLoginLogVO> getById(@PathVariable Long id) {
        SysLoginLogVO vo = sysLoginLogService.getByIdNew(id);
        return R.success(vo);
    }

    /**
     * 分页查询
     *
     * @param pageParam 分页查询参数
     * @param filter    查询条件
     */
    @PreAuthorize("hasAuthority('sys:loginlog:page')")
    @GetMapping
    public R<PageData<SysLoginLogVO>> page(PageParam pageParam, SysLoginLogDTO filter) {
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<SysLoginLog> sysLoginLogList = sysLoginLogService.listByFilter(filter);
        return R.success(PageDataUtils.make(sysLoginLogList, SysLoginLog.INSTANCE));
    }


    /**
     * 清空登录日志
     */
    @PreAuthorize("hasAuthority('sys:loginlog:clean')")
    @DeleteMapping("/clean")
    public R<Void> cleanLoginLogs() {
        sysLoginLogService.cleanLoginLog();
        return R.success();
    }

}
