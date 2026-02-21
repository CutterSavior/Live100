package com.lanjii.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lanjii.sys.base.AdminBaseMapper;
import com.lanjii.system.api.dto.SysConfigDTO;
import com.lanjii.system.api.vo.SysConfigVO;
import com.lanjii.framework.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 系统配置表(SysConfig)表实体类
 *
 * @author lanjii
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_config")
public class SysConfig extends BaseEntity {

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
    public interface SysConfigMapper extends AdminBaseMapper<SysConfig, SysConfigVO, SysConfigDTO> {

    }

    public static final SysConfig.SysConfigMapper INSTANCE = Mappers.getMapper(SysConfig.SysConfigMapper.class);

}
