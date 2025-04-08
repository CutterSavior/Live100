package com.lanjii.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 部门Vo
 *
 * @author lizheng
 * @date 2024-10-29
 */
@Data
public class SysDeptVo implements Serializable {

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
     * 状态名称
     */
    private String statusName;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 创建时间
     */
    private Date createdTime;

}
