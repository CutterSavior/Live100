package com.lanjii.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 部门Dto
 *
 * @author lizheng
 * @date 2024-10-29
 */
@Data
public class SysDeptDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Long id;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 父级部门 ID
     */
    private Long parentId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 排序
     */
    private Integer sort;
}
