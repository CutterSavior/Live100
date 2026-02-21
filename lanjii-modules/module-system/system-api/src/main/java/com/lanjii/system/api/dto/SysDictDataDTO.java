package com.lanjii.system.api.dto;

import com.lanjii.common.annotation.QueryCondition;
import com.lanjii.common.annotation.SortField;
import com.lanjii.common.enums.QueryType;
import com.lanjii.common.enums.SortOrder;
import lombok.Data;

/**
 * 字典数据表(SysDictData) DTO
 *
 * @author lanjii
 */
@Data
public class SysDictDataDTO {

    /**
     * 字典数据主键
     */
    private Long id;

    /**
     * 字典排序
     */
    @SortField(order = SortOrder.ASC, priority = 1)
    private Integer sortOrder;

    /**
     * 字典标签
     */
    @QueryCondition(type = QueryType.LIKE)
    private String dictLabel;

    /**
     * 字典键值
     */
    private Integer dictValue;

    /**
     * 字典类型编码
     */
    @QueryCondition
    private String dictType;


    /**
     * 是否启用（1启用 0禁用）
     */
    @QueryCondition
    private Integer isEnabled;

    /**
     * 备注
     */
    private String remark;

    /**
     * 标签类型
     */
    private String tagType;

    /**
     * 标签颜色
     */
    private String tagColor;

    /**
     * 标签主题
     */
    private String tagEffect;

    /**
     * 创建人
     */
    private String createBy;
}
