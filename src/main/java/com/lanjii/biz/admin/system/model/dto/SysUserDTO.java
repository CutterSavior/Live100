package com.lanjii.biz.admin.system.model.dto;

import com.lanjii.core.annotation.QueryCondition;
import com.lanjii.core.base.BaseDTO;
import com.lanjii.core.enums.QueryType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 系统用户表(SysUser) DTO
 *
 * @author lanjii
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysUserDTO extends BaseDTO {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名
     */
    @QueryCondition(type = QueryType.LIKE)
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 是否启用（1启用 0禁用）
     */
    @QueryCondition
    private Integer isEnabled;

    /**
     * 所属部门 ID
     */
    @QueryCondition
    private Long deptId;

    /**
     * 岗位 ID
     */
    private List<Long> postIds;

    /**
     * 角色 ID
     */
    private List<Long> roleIds;

}
