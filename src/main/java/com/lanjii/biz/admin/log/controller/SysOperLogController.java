package com.lanjii.biz.admin.log.controller;

import com.github.pagehelper.PageHelper;
import com.lanjii.core.base.PageParam;
import com.lanjii.core.base.support.PageData;
import com.lanjii.core.resp.R;
import com.lanjii.biz.admin.log.model.dto.SysOperLogDTO;
import com.lanjii.biz.admin.log.model.entity.SysOperLog;
import com.lanjii.biz.admin.log.service.SysOperLogService;
import com.lanjii.common.util.PageDataUtils;
import com.lanjii.biz.admin.log.model.vo.SysOperLogVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户操作日志管理
 *
 * @author lanjii
 */
@RestController
@RequestMapping("/admin/sys/oper-logs")
@RequiredArgsConstructor
public class SysOperLogController {

    private final SysOperLogService sysOperLogService;

    /**
     * 查询详情
     *
     * @param id 数据 ID
     */
    @PreAuthorize("hasAuthority('sys:operlog:view')")
    @GetMapping("/{id}")
    public R<SysOperLogVO> getById(@PathVariable Long id) {
        SysOperLogVO vo = sysOperLogService.getByIdNew(id);
        return R.success(vo);
    }

    /**
     * 分页查询
     *
     * @param pageParam 分页查询参数
     * @param filter    查询条件
     */
    @PreAuthorize("hasAuthority('sys:operlog:page')")
    @GetMapping
    public R<PageData<SysOperLogVO>> page(PageParam pageParam, SysOperLogDTO filter) {
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<SysOperLog> sysOperLogList = sysOperLogService.listByFilter(filter);
        return R.success(PageDataUtils.make(sysOperLogList, SysOperLog.INSTANCE));
    }


    /**
     * 清空操作日志
     */
    @PreAuthorize("hasAuthority('sys:operlog:clean')")
    @DeleteMapping("/clean")
    public R<Void> cleanOperLogs() {
        sysOperLogService.cleanOperLog();
        return R.success();
    }

}
