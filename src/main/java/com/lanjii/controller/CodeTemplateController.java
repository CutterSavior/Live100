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
import com.lanjii.model.dto.CodeGenerator;
import com.lanjii.model.dto.CodeTemplateDto;
import com.lanjii.model.entity.CodeTemplate;
import com.lanjii.service.ICodeTemplateService;
import com.lanjii.model.vo.CodeTemplateVo;
import com.lanjii.model.vo.TableInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 代码模板表
 *
 * @author lizheng
 * @date 2024-11-04
 */
@Slf4j
@RestController
@RequestMapping("/admin/code-template")
@RequiredArgsConstructor
public class CodeTemplateController {

    private final ICodeTemplateService codeTemplateService;

    @PostMapping("/table/list")
    public R<List<TableInfo>> tableList() {
        return R.success(codeTemplateService.getTables("leven_db"));
    }

    @PostMapping("/generateCodeAndDownload")
    public R<Map<String, String>> generateCodeAndDownload(@MultiRequestBody CodeGenerator codeGenerator) {
        return R.success(Map.of("path", codeTemplateService.generateCodeAndDownload(codeGenerator)));
    }

    @PostMapping("/generate")
    public R<List<CodeTemplateVo>> generate(@MultiRequestBody CodeGenerator codeGenerator) {
        return R.success(codeTemplateService.generateCode(codeGenerator));
    }

    /**
     * 列表
     */
    @PostMapping("/list")
    public R<PageData<CodeTemplateVo>> list(@MultiRequestBody(required = false) PageParam pageParam, @MultiRequestBody(required = false) OrderParam orderParam,
                                            @MultiRequestBody(required = false) CodeTemplateDto codeTemplateFilter) {
        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
        List<CodeTemplate> list = codeTemplateService.getListByFilter(codeTemplateFilter, orderParam);
        return R.success(PageDataUtils.make(list, CodeTemplate.INSTANCE));
    }


    /**
     * 详情
     */
    @GetMapping("/query")
    public R<CodeTemplate> query(Long id) {
        return R.success(codeTemplateService.getById(id));
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R<CodeTemplate> save(@MultiRequestBody CodeTemplateDto codeTemplateDto) {
        CodeTemplate codeTemplate = ModelUtils.copyTo(codeTemplateDto, CodeTemplate.class);
        codeTemplateService.save(codeTemplate);
        return R.success();
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    public R<CodeTemplate> update(@MultiRequestBody CodeTemplateDto codeTemplateDto) {
        CodeTemplate originalData = codeTemplateService.getById(codeTemplateDto.getId());
        if (originalData == null) {
            return R.fail(ResultCode.DATA_NOT_EXIST);
        }
        CodeTemplate codeTemplate = ModelUtils.copyTo(codeTemplateDto, CodeTemplate.class);
        codeTemplateService.updateById(codeTemplate);
        return R.success();
    }

    /**
     * 删除
     */
    @GetMapping("/del")
    public R<CodeTemplate> delete(Long id) {
        codeTemplateService.removeById(id);
        return R.success();
    }

}
