package com.lanjii.system.api.vo;

import com.lanjii.common.base.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色表(SysRole) VO
 *
 * @author lanjii
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysRoleVO extends BaseVO {

    /**
     * 角色ID
     */
    private Long id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色编码
     */
    private String code;

    /**
     * 显示顺序
     */
    private Integer sortOrder;

    /**
     * 是否启用（1启用 0禁用）
     */
    private Integer isEnabled;

    /**
     * 备注
     */
    private String remark;

}
