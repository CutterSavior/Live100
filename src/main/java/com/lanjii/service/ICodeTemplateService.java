package com.lanjii.service;

import com.lanjii.core.base.IBaseService;
import com.lanjii.model.dto.CodeGenerator;
import com.lanjii.model.entity.CodeTemplate;
import com.lanjii.model.vo.CodeTemplateVo;
import com.lanjii.model.vo.TableInfo;

import java.util.List;

/**
 * 代码模板表 服务类
 *
 * @author lizheng
 * @date 2024-11-04
 */
public interface ICodeTemplateService extends IBaseService<CodeTemplate> {

    String generateCodeAndDownload(CodeGenerator codeGenerator);

    List<CodeTemplateVo> generateCode(CodeGenerator codeGenerator);

    List<TableInfo> getTables(String tableSchema);

}
