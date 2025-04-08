package com.lanjii.model.dto;

import com.lanjii.core.annotation.Where;
import com.lanjii.core.enums.Condition;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author lizheng
 * @date 2024-09-19
 */
@Data
public class SysUserDto {
    /**
     * 主键
     */
    private Long id;

    /**
     * 用户名
     */
    @NotBlank(message = "请输入账户")
    @Where(value = Condition.LIKE, column = "user_name")
    private String userName;

    /**
     * 姓名
     */
    private String realName;

    /**
     * 密码
     */
    private String password;

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
    @Where(value = Condition.EQ, column = "dept_id")
    private Long deptId;

    /**
     * 所属角色 ID
     */
    private List<Long> roleIds;
}
