package com.lanjii.biz.admin.system.model.dto;

import com.lanjii.core.annotation.QueryCondition;
import com.lanjii.core.annotation.SortField;
import com.lanjii.core.base.BaseDTO;
import com.lanjii.core.enums.QueryType;
import com.lanjii.core.enums.SortOrder;
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
