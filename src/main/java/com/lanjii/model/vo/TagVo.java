package com.lanjii.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 标签表Vo
 *
 * @author lizheng
 * @date 2025-03-29
 */
@Data
public class TagVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Long id;

    /**
     * 标签名
     */
    private String name;

    private Date createdTime;
    private String createdBy;
}
