package com.lanjii.biz.admin.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lanjii.biz.admin.system.model.dto.SysConfigDTO;
import com.lanjii.biz.admin.system.model.vo.SysConfigVO;
import com.lanjii.core.base.BaseEntity;
import com.lanjii.core.base.BaseEntityMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 系统配置表(SysConfig)表实体类
 *
 * @author lanjii
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_config")
public class SysConfig extends BaseEntity<SysConfig> {

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

    @Mapper
    public interface SysConfigMapper extends BaseEntityMapper<SysConfig, SysConfigVO, SysConfigDTO> {

        @Mapping(target = "isEnabledLabel", expression = "java(dictValueToLabel(entity.getIsEnabled(),\"STATUS\"))")
        SysConfigVO toVo(SysConfig entity);

    }

    public static final SysConfig.SysConfigMapper INSTANCE = Mappers.getMapper(SysConfig.SysConfigMapper.class);

}
