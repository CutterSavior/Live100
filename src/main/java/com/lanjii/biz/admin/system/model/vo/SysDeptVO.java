package com.lanjii.biz.admin.system.model.vo;

import com.lanjii.core.base.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 系统部门表(SysDept) VO
 *
 * @author lanjii
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDeptVO extends BaseVO {

    /**
     * 部门ID
     */
    private Long id;

    /**
     * 父部门ID
     */
    private Long parentId;

    /**
     * 祖级列表(逗号分隔)
     */
    private String ancestors;

    /**
     * 部门名称
     */
    private String deptName;


    /**
     * 显示顺序
     */
    private Integer sortOrder;

    /**
     * 负责人
     */
    private String leader;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 是否启用（1启用 0禁用）
     */
    private Integer isEnabled;

    /**
     * 部门状态
     */
    private String isEnabledLabel;

    /**
     * 子部门
     */
    private List<SysDeptVO> children;
}
