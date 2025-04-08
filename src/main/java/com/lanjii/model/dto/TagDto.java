package com.lanjii.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 标签表Dto
 *
 * @author lizheng
 * @date 2025-03-29
 */
@Data
public class TagDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Long id;

    /**
     * 标签名
     */
    private String name;


}
