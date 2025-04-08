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
import com.lanjii.model.dto.SysDeptDto;
import com.lanjii.model.entity.SysDept;
import com.lanjii.service.ISysDeptService;
import com.lanjii.model.vo.SysDeptVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 部门
 *
 * @author lizheng
 * @date 2024-10-29
 */
@Slf4j
@RestController
@RequestMapping("/admin/sys-dept")
@RequiredArgsConstructor
public class SysDeptController {

    private final ISysDeptService sysDeptService;

    /**
     * 列表
     */
    @PostMapping("/list")
    public R<PageData<SysDeptVo>> list(@MultiRequestBody(required = false) PageParam pageParam, @MultiRequestBody(required = false) OrderParam orderParam,
                                       @MultiRequestBody(required = false) SysDeptDto sysDeptFilter) {
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<SysDept> list = sysDeptService.getListByFilter(sysDeptFilter, orderParam);
        return R.success(PageDataUtils.make(list, SysDept.INSTANCE));
    }


    /**
     * 详情
     */
    @GetMapping("/query")
    public R<SysDept> query(Long id) {
        return R.success(sysDeptService.getById(id));
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R<SysDept> save(@MultiRequestBody SysDeptDto sysDeptDto) {
        SysDept sysDept = ModelUtils.copyTo(sysDeptDto, SysDept.class);
        sysDeptService.save(sysDept);
        return R.success();
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    public R<SysDept> update(@MultiRequestBody SysDeptDto sysDeptDto) {
        SysDept originalData = sysDeptService.getById(sysDeptDto.getId());
        if (originalData == null) {
            return R.fail(ResultCode.DATA_NOT_EXIST);
        }
        SysDept sysDept = ModelUtils.copyTo(sysDeptDto, SysDept.class);
        sysDeptService.updateById(sysDept);
        return R.success();
    }

    /**
     * 删除
     */
    @GetMapping("/del")
    public R<SysDept> delete(Long id) {
        sysDeptService.removeById(id);
        return R.success();
    }

}
