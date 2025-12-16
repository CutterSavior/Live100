package com.lanjii.biz.admin.log.controller;

import com.github.pagehelper.PageHelper;
import com.lanjii.core.base.PageParam;
import com.lanjii.core.base.support.PageData;
import com.lanjii.core.resp.R;
import com.lanjii.biz.admin.log.model.dto.SysLoginLogDTO;
import com.lanjii.biz.admin.log.model.entity.SysLoginLog;
import com.lanjii.biz.admin.log.service.SysLoginLogService;
import com.lanjii.common.util.PageDataUtils;
import com.lanjii.biz.admin.log.model.vo.SysLoginLogVO;
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
