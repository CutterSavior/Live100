package com.lanjii.model.dto;

import com.lanjii.core.annotation.Where;
import com.lanjii.core.enums.Condition;
import lombok.Data;

import java.io.Serializable;

/**
 * Dto
 *
 * @author lizheng
 * @date 2024-10-30
 */
@Data
public class SysResourceDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Long id;

    /**
     * 资源名称
     */
    @Where(column = "resource_name", value = Condition.LIKE)
    private String resourceName;

    /**
     * 资源地址
     */
    @Where(column = "resource_url", value = Condition.LIKE)
    private String resourceUrl;

}
