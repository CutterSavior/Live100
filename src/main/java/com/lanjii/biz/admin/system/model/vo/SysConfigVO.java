package com.lanjii.biz.admin.system.model.vo;

import com.lanjii.core.base.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统配置表(SysConfig) VO
 *
 * @author lanjii
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysConfigVO extends BaseVO {

    /**
     * 配置ID
     */
    private Long id;

    /**
     * 配置名称
     */
    private String configName;

    /**
     * 配置键名
     */
    private String configKey;

    /**
     * 配置键值
     */
    private String configValue;

    /**
     * 配置类型（1-系统配置 2-业务配置）
     */
    private Integer configType;

    /**
     * 是否启用（1启用 0禁用）
     */
    private Integer isEnabled;

    /**
     * 备注
     */
    private String remark;

}
