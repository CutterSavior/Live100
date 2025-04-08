package com.lanjii.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 代码模板表Vo
 *
 * @author lizheng
 * @date 2024-11-04
 */
@Data
public class CodeTemplateVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
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
     * 代码内容
     */
    private String codeContent;

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

    /**
     * 创建时间
     */
    private Date createdTime;


}
