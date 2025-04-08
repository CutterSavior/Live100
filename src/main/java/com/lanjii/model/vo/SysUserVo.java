package com.lanjii.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author lizheng
 * @since 2023-08-28
 */
@Data
public class SysUserVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 姓名
     */
    private String realName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 是否超级管理员（0-否 1-是）
     */
    private Integer isAdmin;

    /**
     * 性别（1-男 2-女）
     */
    private Integer gender;


    /**
     * 头像
     */
    private String avatar;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 部门 ID
     */
    private Long deptId;

    /**
     * 所属角色 ID
     */
    private List<Long> roleIds;

    private Date createdTime;

}
