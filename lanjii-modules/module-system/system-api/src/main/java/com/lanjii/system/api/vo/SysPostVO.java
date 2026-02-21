package com.lanjii.system.api.vo;

import com.lanjii.common.base.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统岗位表(SysPost) VO
 *
 * @author lanjii
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysPostVO extends BaseVO {

    /**
     * 岗位ID
     */
    private Long id;

    /**
     * 岗位编码
     */
    private String postCode;

    /**
     * 岗位名称
     */
    private String postName;

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
