package com.lanjii.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lanjii.core.annotation.Where;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统配置表Dto
 *
 * @author lizheng
 * @date 2025-03-28
 */
@Data
public class SysConfigDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 配置项名称
     */
    @Where(column = "config_name")
    private String configName;

    /**
     * 配置项值
     */
    private String configValue;

    /**
     * 配置项描述
     */
    private String description;

}
