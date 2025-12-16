package com.lanjii.biz.admin.system.model.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.lanjii.core.base.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统用户表(SysUser) VO
 *
 * @author lanjii
 */
@ExcelIgnoreUnannotated
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUserVO extends BaseVO {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名
     */
    @ExcelProperty(value = "用户名")
    private String username;

    /**
     * 昵称
     */
    @ExcelProperty(value = "昵称")
    private String nickname;

    /**
     * 邮箱
     */
    @ExcelProperty(value = "邮箱")
    private String email;

    /**
     * 手机号
     */
    @ExcelProperty(value = "手机号")
    private String phone;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 是否启用（1启用 0禁用）
     */
    private Integer isEnabled;

    /**
     * 状态
     */
    @ExcelProperty(value = "状态")
    private String statusLabel;

    /**
     * 所属部门 ID
     */
    private Long deptId;

    /**
     * 部门名称
     */
    @ExcelProperty(value = "部门名称")
    private String deptName;

    /**
     * 最后登录时间
     */
    @ExcelProperty(value = "最后登录时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLoginTime;

    /**
     * 最后登录IP
     */
    @ExcelProperty(value = "最后登录IP")
    private String lastLoginIp;

    /**
     * 岗位 ID
     */
    private List<Long> postIds;

    /**
     * 角色 ID
     */
    private List<Long> roleIds;

}
