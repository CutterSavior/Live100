package com.lanjii.biz.admin.ai.model.vo;

import com.lanjii.core.base.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * AI 元数据字段 VO
 *
 * @author lanjii
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AiMetadataFieldVO extends BaseVO {

    private Long id;

    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 显示名称
     */
    private String displayName;

    /**
     * 数据类型
     */
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
    private Integer isRequired;

    /**
     * 是否可搜索（1-是 0-否）
     */
    private Integer isSearchable;

    /**
     * 使用次数
     */
    private Long useCount;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String createBy;
    private String updateBy;
}
