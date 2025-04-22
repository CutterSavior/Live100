package com.lanjii.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.lanjii.core.annotation.MultiRequestBody;
import com.lanjii.core.base.support.OrderParam;
import com.lanjii.core.base.support.PageData;
import com.lanjii.core.base.support.PageParam;
import com.lanjii.core.enums.ResultCode;
import com.lanjii.core.obj.R;
import com.lanjii.util.ModelUtils;
import com.lanjii.util.PageDataUtils;
import com.lanjii.model.dto.TagDto;
import com.lanjii.model.entity.Tag;
import com.lanjii.service.ITagService;
import com.lanjii.model.vo.TagVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 标签表
 *
 * @author lizheng
 * @date 2025-03-29
 */
@Slf4j
@RestController
@RequestMapping("/admin/tag")
@RequiredArgsConstructor
public class TagController {

    private final ITagService tagService;

    /**
     * 列表
     */
    @PostMapping("/list")
    public R<PageData<TagVo>> list(@MultiRequestBody(required = false) PageParam pageParam, @MultiRequestBody(required = false) OrderParam orderParam,
                                   @MultiRequestBody(required = false) TagDto tagFilter) {
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<Tag> list = tagService.getListByFilter(tagFilter, orderParam);
        return R.success(PageDataUtils.make(list, Tag.INSTANCE));
    }


    /**
     * 详情
     */
    @GetMapping("/query")
    public R<Tag> query(Long id) {
        return R.success(tagService.getById(id));
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R<Tag> save(@MultiRequestBody TagDto tagDto) {

        String tagName = tagDto.getName();
        LambdaQueryWrapper<Tag> query = new LambdaQueryWrapper<>();
        query.eq(Tag::getName, tagName);
        if (tagService.count(query) > 0) {
            return R.fail("标签已存在");
        }
        Tag tag = ModelUtils.copyTo(tagDto, Tag.class);
        tagService.saveOrUpdate(tag);
        return R.success();
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    public R<Tag> update(@MultiRequestBody TagDto tagDto) {
        Tag originalData = tagService.getById(tagDto.getId());
        if (originalData == null) {
            return R.fail(ResultCode.DATA_NOT_EXIST);
        }
        
        // 检查标签名称是否与其他标签重复
        if (!originalData.getName().equals(tagDto.getName())) {
            LambdaQueryWrapper<Tag> query = new LambdaQueryWrapper<>();
            query.eq(Tag::getName, tagDto.getName());
            if (tagService.count(query) > 0) {
                return R.fail("标签名称已存在");
            }
        }
        
        Tag tag = ModelUtils.copyTo(tagDto, Tag.class);
        tagService.saveOrUpdate(tag);
        return R.success();
    }

    /**
     * 删除
     */
    @GetMapping("/del")
    public R<Tag> delete(Long id) {
        tagService.removeById(id);
        return R.success();
    }

}
