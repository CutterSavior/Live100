package com.lanjii.controller;

import com.github.pagehelper.PageHelper;
import com.lanjii.core.annotation.MultiRequestBody;
import com.lanjii.core.base.support.OrderParam;
import com.lanjii.core.base.support.PageData;
import com.lanjii.core.base.support.PageParam;
import com.lanjii.core.enums.ResultCode;
import com.lanjii.core.obj.R;
import com.lanjii.model.dto.SysOperationLogDto;
import com.lanjii.model.entity.SysOperationLog;
import com.lanjii.model.vo.SysOperationLogVo;
import com.lanjii.service.ISysOperationLogService;
import com.lanjii.util.ModelUtils;
import com.lanjii.util.PageDataUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 操作日志表
 *
 * @author lizheng
 * @date 2025-04-10
 */
@Slf4j
@RestController
@RequestMapping("admin/sys-operation-log")
@RequiredArgsConstructor
public class SysOperationLogController {

    private final ISysOperationLogService sysOperationLogService;

    /**
     * 列表
     */
    @PostMapping("/list")
    public R<PageData<SysOperationLogVo>> list(@MultiRequestBody(required = false) PageParam pageParam, @MultiRequestBody(required = false) OrderParam orderParam,
                                               @MultiRequestBody(required = false) SysOperationLogDto sysOperationLogFilter) {
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<SysOperationLog> list = sysOperationLogService.getListByFilter(sysOperationLogFilter, orderParam);
        return R.success(PageDataUtils.make(list, SysOperationLog.INSTANCE));
    }


    /**
     * 详情
     */
    @GetMapping("/query")
    public R<SysOperationLog> query(Long id) {
        return R.success(sysOperationLogService.getById(id));
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R<SysOperationLog> save(@MultiRequestBody SysOperationLogDto sysOperationLogDto) {
        SysOperationLog sysOperationLog = ModelUtils.copyTo(sysOperationLogDto, SysOperationLog.class);
        sysOperationLogService.save(sysOperationLog);
        return R.success();
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    public R<SysOperationLog> update(@MultiRequestBody SysOperationLogDto sysOperationLogDto) {
        SysOperationLog originalData = sysOperationLogService.getById(sysOperationLogDto.getId());
        if (originalData == null) {
            return R.fail(ResultCode.DATA_NOT_EXIST);
        }
        SysOperationLog sysOperationLog = ModelUtils.copyTo(sysOperationLogDto, SysOperationLog.class);
        sysOperationLogService.updateById(sysOperationLog);
        return R.success();
    }

    /**
     * 删除
     */
    @GetMapping("/del")
    public R<SysOperationLog> delete(Long id) {
        sysOperationLogService.removeById(id);
        return R.success();
    }

}
