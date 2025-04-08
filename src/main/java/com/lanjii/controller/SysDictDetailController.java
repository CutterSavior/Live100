
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
import com.lanjii.model.dto.SysDictDetailDto;
import com.lanjii.model.entity.SysDictDetail;
import com.lanjii.service.ISysDictDetailService;
import com.lanjii.model.vo.SysDictDetailVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * 字典表详情
 *
 * @author lizheng
 * @date 2024-10-11
 */
@Slf4j
@RestController
@RequestMapping("/admin/sys-dict-detail")
@RequiredArgsConstructor
public class SysDictDetailController {

    private final ISysDictDetailService sysDictDetailService;

    /**
     * 列表
     */
    @PostMapping("/list")
    public R<PageData<SysDictDetailVo>> list(@MultiRequestBody(required = false) PageParam pageParam, @MultiRequestBody(required = false) OrderParam orderParam,
                                             @MultiRequestBody(required = false) SysDictDetailDto sysDictDetailFilter) {
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<SysDictDetail> list = sysDictDetailService.getListByFilter(sysDictDetailFilter, orderParam);
        return R.success(PageDataUtils.make(list, SysDictDetail.INSTANCE));
    }

    /**
     * 详情
     */
    @GetMapping("/query")
    public R<SysDictDetail> query(Long id) {
        return R.success(sysDictDetailService.getById(id));
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R<SysDictDetail> save(@MultiRequestBody SysDictDetailDto sysDictDetailDto) {
        SysDictDetailDto filter = new SysDictDetailDto();
        filter.setDictCode(sysDictDetailDto.getDictCode());
        filter.setDictValue(sysDictDetailDto.getDictValue());
        if (sysDictDetailService.getOneByFilter(filter) != null) {
            return R.fail(ResultCode.DICT_ALREADY_EXISTS);
        }
        SysDictDetail sysDictDetail = ModelUtils.copyTo(sysDictDetailDto, SysDictDetail.class);
        sysDictDetailService.save(sysDictDetail);
        return R.success();
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    public R<SysDictDetail> update(@MultiRequestBody SysDictDetailDto sysDictDetailDto) {
        SysDictDetail originalData = sysDictDetailService.getById(sysDictDetailDto.getId());
        if (originalData == null) {
            return R.fail(ResultCode.DATA_NOT_EXIST);
        }
        SysDictDetailDto filter = new SysDictDetailDto();
        filter.setDictCode(sysDictDetailDto.getDictCode());
        filter.setDictValue(sysDictDetailDto.getDictValue());
        SysDictDetail one = sysDictDetailService.getOneByFilter(filter);
        if (one != null && !Objects.equals(one.getId(), sysDictDetailDto.getId())) {
            return R.fail(ResultCode.DICT_ALREADY_EXISTS);
        }
        SysDictDetail sysDictDetail = ModelUtils.copyTo(sysDictDetailDto, SysDictDetail.class);
        sysDictDetailService.updateById(sysDictDetail);
        return R.success();
    }

    /**
     * 删除
     */
    @GetMapping("/del")
    public R<SysDictDetail> delete(Long id) {
        sysDictDetailService.removeById(id);
        return R.success();
    }

}
