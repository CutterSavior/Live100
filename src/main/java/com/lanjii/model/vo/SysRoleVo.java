package com.lanjii.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 角色表 VO 对象
 *
 * @author lizheng
 * @date 2024-09-28
 */
@Data
public class SysRoleVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 角色名
     */
    private String name;

    /**
     * 角色描述
     */
    private String description;

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

}
