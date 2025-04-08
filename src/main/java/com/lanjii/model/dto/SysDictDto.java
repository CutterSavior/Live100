package com.lanjii.model.dto;

import com.lanjii.core.annotation.Where;
import lombok.Data;

import java.io.Serializable;

/**
 * 字典表Dto
 *
 * @author lizheng
 * @date 2024-10-10
 */
@Data
public class SysDictDto implements Serializable {

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
    @Where(column = "dict_code")
    private String dictCode;

    /**
     * 字典名称
     */
    private String dictName;


}
