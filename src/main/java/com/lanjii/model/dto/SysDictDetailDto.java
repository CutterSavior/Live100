package com.lanjii.model.dto;

import com.lanjii.core.annotation.Where;
import com.lanjii.core.enums.Condition;
import lombok.Data;

import java.io.Serializable;

/**
 * 字典表详情Dto
 *
 * @author lizheng
 * @date 2024-10-10
 */
@Data
public class SysDictDetailDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 描述
     */
    private String description;

    /**
     * 字典编码
     */
    @Where(column = "dict_code", value = Condition.EQ)
    private String dictCode;

    /**
     * 字典标签（页面显示名称）
     */
    private String dictLabel;

    /**
     * 排序
     */
    private String dictSort;

    /**
     * 字典值
     */
    @Where(column = "dict_value", value = Condition.EQ)
    private Integer dictValue;


}
