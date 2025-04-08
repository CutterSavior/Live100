package com.lanjii.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lanjii.core.base.BaseModel;
import com.lanjii.core.base.BaseModelMapper;
import com.lanjii.model.vo.CodeTemplateVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 代码模板表
 *
 * @author lizheng
 * @date 2024-11-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CodeTemplate extends BaseModel {

    public static final CodeTemplateModelMapper INSTANCE = Mappers.getMapper(CodeTemplateModelMapper.class);
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 模板内容
     */
    private String templateContent;

    /**
     * 模板描述
     */
    private String description;

    /**
     * 文件输出路径
     */
    private String outputPath;

    /**
     * 模块名称
     */
    private String moduleName;

    @Mapper
    public interface CodeTemplateModelMapper extends BaseModelMapper<CodeTemplateVo, CodeTemplate> {

        @Override
        CodeTemplateVo modelToVo(CodeTemplate model);

    }

}
