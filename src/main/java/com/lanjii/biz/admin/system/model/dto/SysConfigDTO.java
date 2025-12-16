package com.lanjii.biz.admin.system.model.dto;

import com.lanjii.core.annotation.QueryCondition;
import com.lanjii.core.enums.QueryType;
import lombok.Data;

/**
 * 系统配置表(SysConfig) DTO
 *
 * @author lanjii
 */
@Data
public class SysConfigDTO {

    /**
     * 配置ID
     */
    private Long id;

    /**
     * 配置名称
     */
    @QueryCondition(type = QueryType.LIKE)
    private String configName;

    /**
     * 配置键名
     */
    @QueryCondition(type = QueryType.LIKE)
    private String configKey;

    /**
     * 配置键值
     */
    @QueryCondition(type = QueryType.LIKE)
    private String configValue;

    /**
     * 配置类型（1-系统配置 2-业务配置）
     */
    @QueryCondition
    private Integer configType;

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
