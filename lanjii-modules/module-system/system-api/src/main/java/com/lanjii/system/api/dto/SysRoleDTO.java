package com.lanjii.system.api.dto;

import com.lanjii.common.annotation.QueryCondition;
import com.lanjii.common.annotation.SortField;
import com.lanjii.common.base.BaseDTO;
import com.lanjii.common.enums.QueryType;
import com.lanjii.common.enums.SortOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色表(SysRole) DTO
 *
 * @author lanjii
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysRoleDTO extends BaseDTO {

    /**
     * 角色ID
     */
    private Long id;

    /**
     * 角色名称
     */
    @QueryCondition(type = QueryType.LIKE)
    private String name;

    /**
     * 角色编码
     */
    @QueryCondition(type = QueryType.LIKE)
    private String code;

    /**
     * 显示顺序
     */
    @SortField(order = SortOrder.ASC, priority = 1)
    private Integer sortOrder;

    /**
     * 是否启用（1启用 0禁用）
     */
    @QueryCondition
    private Integer isEnabled;

    /**
     * 备注
     */
    private String remark;

}
