package com.lanjii.ai.api.dto;

import com.lanjii.common.annotation.QueryCondition;
import com.lanjii.common.enums.QueryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * AI 元数据字段 DTO
 *
 * @author lanjii
 */
@Data
public class AiMetadataFieldDTO {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 字段名
     */
    @NotBlank(message = "字段名不能为空")
    @QueryCondition(type = QueryType.LIKE)
    private String fieldName;

    /**
     * 显示名称
     */
    @NotBlank(message = "显示名称不能为空")
    @QueryCondition(type = QueryType.LIKE)
    private String displayName;

    /**
     * 数据类型
     */
    @NotBlank(message = "数据类型不能为空")
    @QueryCondition
    private String dataType;

    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 字段描述
     */
    private String description;

    /**
     * 是否必填（1-是 0-否）
     */
    @NotNull(message = "是否必填不能为空")
    @QueryCondition
    private Integer isRequired;

    /**
     * 是否可搜索（1-是 0-否）
     */
    @NotNull(message = "是否可搜索不能为空")
    @QueryCondition
    private Integer isSearchable;
}
