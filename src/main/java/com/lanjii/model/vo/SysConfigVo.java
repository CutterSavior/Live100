package com.lanjii.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统配置表Vo
 *
 * @author lizheng
 * @date 2025-03-28
 */
@Data
public class SysConfigVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 配置项名称
     */
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
