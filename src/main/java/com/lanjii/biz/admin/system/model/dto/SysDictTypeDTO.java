package com.lanjii.biz.admin.system.model.dto;

import com.lanjii.core.annotation.QueryCondition;
import com.lanjii.core.enums.QueryType;
import lombok.Data;

/**
 * 字典类型表(SysDictType) DTO
 *
 * @author lanjii
 */
@Data
public class SysDictTypeDTO {

    /**
     * 字典主键
     */
    private Long id;

    /**
     * 字典名称
     */
    @QueryCondition(type = QueryType.LIKE)
    private String typeName;

    /**
     * 字典类型编码
     */
    @QueryCondition(type = QueryType.LIKE)
    private String typeCode;

    /**
     * 是否启用（1启用 0禁用）
     */
    private Integer isEnabled;

    /**
     * 备注
     */
    private String remark;

}
