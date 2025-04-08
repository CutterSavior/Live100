package com.lanjii.model.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户和角色关联表
 * </p>
 *
 * @author lizheng
 * @since 2023-08-28
 */
@Data
@Accessors(chain = true)
public class SysUserRoleRel extends Model {

    private static final long serialVersionUID = 1L;

    /**
     * 用户主键
     */
    private Long userId;

    /**
     * 角色主键
     */
    private Long roleId;


}
