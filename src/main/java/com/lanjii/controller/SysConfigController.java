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
import com.lanjii.model.dto.SysConfigDto;
import com.lanjii.model.entity.SysConfig;
import com.lanjii.service.ISysConfigService;
import com.lanjii.model.vo.SysConfigVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 系统配置表
 *
 * @author lizheng
 * @date 2025-03-28
 */
@Slf4j
@RestController
@RequestMapping("/admin/sys-config")
@RequiredArgsConstructor
public class SysConfigController {

    private final ISysConfigService sysConfigService;

    /**
     * 列表
     */
    @PostMapping("/list")
    public R<PageData<SysConfigVo>> list(@MultiRequestBody(required = false) PageParam pageParam, @MultiRequestBody(required = false) OrderParam orderParam,
                                         @MultiRequestBody(required = false) SysConfigDto sysConfigFilter) {
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<SysConfig> list = sysConfigService.getListByFilter(sysConfigFilter, orderParam);
        return R.success(PageDataUtils.make(list, SysConfig.INSTANCE));
    }

    /**
     * 详情
     */
    @GetMapping("/query")
    public R<SysConfig> query(Long id) {
        return R.success(sysConfigService.getById(id));
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R<SysConfig> save(@MultiRequestBody SysConfigDto sysConfigDto) {
        SysConfig sysConfig = ModelUtils.copyTo(sysConfigDto, SysConfig.class);
        sysConfigService.save(sysConfig);
        return R.success();
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    public R<SysConfig> update(@MultiRequestBody SysConfigDto sysConfigDto) {
        SysConfig originalData = sysConfigService.getById(sysConfigDto.getId());
        if (originalData == null) {
            return R.fail(ResultCode.DATA_NOT_EXIST);
        }
        SysConfig sysConfig = ModelUtils.copyTo(sysConfigDto, SysConfig.class);
        sysConfigService.updateById(sysConfig);
        return R.success();
    }

    /**
     * 删除
     */
    @GetMapping("/del")
    public R<SysConfig> delete(Long id) {
        sysConfigService.removeById(id);
        return R.success();
    }

}
