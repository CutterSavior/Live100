package com.lanjii.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lanjii.core.base.BaseModel;
import com.lanjii.core.base.BaseModelMapper;
import com.lanjii.model.vo.SysConfigVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 系统配置表
 *
 * @author lizheng
 * @date 2025-03-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysConfig extends BaseModel {

    private static final long serialVersionUID = 1L;

    public static final SysConfigModelMapper INSTANCE = Mappers.getMapper(SysConfigModelMapper.class);

    /**
     * 配置项名称
     */
    private String configName;

    /**
     * 配置项值
     */
    private String configValue;
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 配置项描述
     */
    private String description;

    @Mapper
    public interface SysConfigModelMapper extends BaseModelMapper<SysConfigVo, SysConfig> {
        @Override
        SysConfigVo modelToVo(SysConfig model);

    }

}
