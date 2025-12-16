package com.lanjii.biz.admin.system.model.dto;

import com.lanjii.core.annotation.QueryCondition;
import com.lanjii.core.annotation.SortField;
import com.lanjii.core.base.BaseDTO;
import com.lanjii.core.enums.QueryType;
import com.lanjii.core.enums.SortOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 系统岗位表(SysPost) DTO
 *
 * @author lanjii
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysPostDTO extends BaseDTO {

    /**
     * 岗位ID
     */
    private Long id;

    /**
     * 岗位编码
     */
    @QueryCondition(type = QueryType.LIKE)
    private String postCode;

    /**
     * 岗位名称
     */
    @QueryCondition(type = QueryType.LIKE)
    private String postName;

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

    @SortField(order = SortOrder.DESC, priority = 1)
    private LocalDateTime updateTime;

}
