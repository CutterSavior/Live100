
package com.lanjii.model.dto;

import com.lanjii.core.annotation.Where;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色表 DTO 对象
 *
 * @author lizheng
 * @date 2024-09-28
 */
@Data
public class SysRoleDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 角色名
     */
    @Where(column = "name")
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
     * 排序
     */
    private Integer sort;

}
