package com.lanjii.system.api.dto;

import com.lanjii.common.annotation.SortField;
import com.lanjii.common.base.BaseDTO;
import com.lanjii.common.enums.SortOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统部门表(SysDept) DTO
 *
 * @author lanjii
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysDeptDTO extends BaseDTO {

    /**
     * 部门ID
     */
    private Long id;

    /**
     * 父部门ID
     */
    private Long parentId;

    /**
     * 祖级列表(逗号分隔)
     */
    private String ancestors;

    /**
     * 部门名称
     */
    private String deptName;


    /**
     * 显示顺序
     */
    @SortField(order = SortOrder.ASC, priority = 1)
    private Integer sortOrder;

    /**
     * 负责人
     */
    private String leader;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 是否启用（1启用 0禁用）
     */
    private Integer isEnabled;

}
