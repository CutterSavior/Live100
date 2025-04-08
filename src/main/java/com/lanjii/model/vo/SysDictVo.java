package com.lanjii.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 字典表Vo
 *
 * @author lizheng
 * @date 2024-10-10
 */
@Data
public class SysDictVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 字典描述
     */
    private String description;

    /**
     * 字典编码
     */
    private String dictCode;

    /**
     * 字典名称
     */
    private String dictName;


}
