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
import com.lanjii.model.dto.SysResourceDto;
import com.lanjii.model.entity.SysResource;
import com.lanjii.service.ISysResourceService;
import com.lanjii.model.vo.SysResourceVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lizheng
 * @date 2024-10-30
 */
@Slf4j
@RestController
@RequestMapping("/admin/sys-resource")
@RequiredArgsConstructor
public class SysResourceController {

    private final ISysResourceService sysResourceService;

    /**
     * 列表
     */
    @PostMapping("/list")
    public R<PageData<SysResourceVo>> list(@MultiRequestBody(required = false) PageParam pageParam,
                                           @MultiRequestBody(required = false) OrderParam orderParam,
                                           @MultiRequestBody(required = false) SysResourceDto sysResourceFilter) {
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<SysResource> list = sysResourceService.getListByFilter(sysResourceFilter, orderParam);
        return R.success(PageDataUtils.make(list, SysResource.INSTANCE));
    }

    /**
     * 详情
     */
    @GetMapping("/query")
    public R<SysResource> query(Long id) {
        return R.success(sysResourceService.getById(id));
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R<SysResource> save(@MultiRequestBody SysResourceDto sysResourceDto) {
        SysResource sysResource = ModelUtils.copyTo(sysResourceDto, SysResource.class);
        sysResourceService.save(sysResource);
        return R.success();
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    public R<SysResource> update(@MultiRequestBody SysResourceDto sysResourceDto) {
        SysResource originalData = sysResourceService.getById(sysResourceDto.getId());
        if (originalData == null) {
            return R.fail(ResultCode.DATA_NOT_EXIST);
        }
        SysResource sysResource = ModelUtils.copyTo(sysResourceDto, SysResource.class);
        sysResourceService.updateById(sysResource);
        return R.success();
    }

    /**
     * 删除
     */
    @GetMapping("/del")
    public R<SysResource> delete(Long id) {
        sysResourceService.removeById(id);
        return R.success();
    }

}
