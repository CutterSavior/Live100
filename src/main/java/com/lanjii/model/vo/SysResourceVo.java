package com.lanjii.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Vo
 *
 * @author lizheng
 * @date 2024-10-30
 */
@Data
public class SysResourceVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Long id;

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 资源地址
     */
    private String resourceUrl;

    private Date createdTime;

}
