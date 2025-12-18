package com.lanjii.biz.admin.system.model.vo;

import com.lanjii.core.base.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典类型表(SysDictType) VO
 *
 * @author lanjii
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysDictTypeVO extends BaseVO {

    /**
     * 字典主键
     */
    private Long id;

    /**
     * 字典名称
     */
    private String typeName;

    /**
     * 字典类型编码
     */
    private String typeCode;

    /**
     * 是否启用（1启用 0禁用）
     */
    private Integer isEnabled;

    /**
     * 是否启用
     */
    private String isEnabledLabel;

    /**
     * 备注
     */
    private String remark;

}
