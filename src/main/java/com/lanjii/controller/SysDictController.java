
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
import com.lanjii.model.dto.SysDictDto;
import com.lanjii.model.entity.SysDict;
import com.lanjii.service.ISysDictService;
import com.lanjii.model.vo.SysDictVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * 字典表
 *
 * @author lizheng
 * @date 2024-10-11
 */
@Slf4j
@RestController
@RequestMapping("/admin/sys-dict")
@RequiredArgsConstructor
public class SysDictController {

    private final ISysDictService sysDictService;

    /**
     * 列表
     */
    @PostMapping("/list")
    public R<PageData<SysDictVo>> list(@MultiRequestBody(required = false) PageParam pageParam, @MultiRequestBody(required = false) OrderParam orderParam,
                                       @MultiRequestBody(required = false) SysDictDto sysDictFilter) {
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<SysDict> list = sysDictService.getListByFilter(sysDictFilter, orderParam);
        return R.success(PageDataUtils.make(list, SysDict.INSTANCE));
    }

    /**
     * 详情
     */
    @GetMapping("/query")
    public R<SysDict> query(Long id) {
        return R.success(sysDictService.getById(id));
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R<SysDict> save(@MultiRequestBody SysDictDto sysDictDto) {
        SysDictDto filter = new SysDictDto();
        filter.setDictCode(sysDictDto.getDictCode());
        if (sysDictService.getOneByFilter(filter) != null) {
            return R.fail(ResultCode.DICT_CODE_ALREADY_EXISTS);
        }
        SysDict sysDict = ModelUtils.copyTo(sysDictDto, SysDict.class);
        sysDictService.save(sysDict);
        return R.success();
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    public R<SysDict> update(@MultiRequestBody SysDictDto sysDictDto) {
        SysDict originalData = sysDictService.getById(sysDictDto.getId());
        if (originalData == null) {
            return R.fail(ResultCode.DATA_NOT_EXIST);
        }
        SysDictDto filter = new SysDictDto();
        filter.setDictCode(sysDictDto.getDictCode());
        SysDict oneByFilter = sysDictService.getOneByFilter(filter);
        if (oneByFilter != null && !Objects.equals(oneByFilter.getId(), sysDictDto.getId())) {
            return R.fail(ResultCode.DICT_CODE_ALREADY_EXISTS);
        }
        SysDict sysDict = ModelUtils.copyTo(sysDictDto, SysDict.class);
        sysDictService.updateNew(sysDict, originalData);
        return R.success();
    }

    /**
     * 删除
     */
    @GetMapping("/del")
    public R<SysDict> delete(Long id) {
        sysDictService.removeByIdNew(id);
        return R.success();
    }

}
