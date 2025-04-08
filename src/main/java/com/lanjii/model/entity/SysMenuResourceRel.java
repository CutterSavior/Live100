package com.lanjii.model.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 菜单与资源关联表
 *
 * @author lizheng
 * @date 2024-11-01
 */
@Data
@Accessors(chain = true)
public class SysMenuResourceRel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单 ID
     */
    private Long menuId;

    /**
     * 资源 ID
     */
    private Long resourceId;


}
