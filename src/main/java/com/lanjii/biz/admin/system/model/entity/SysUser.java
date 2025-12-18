package com.lanjii.biz.admin.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lanjii.biz.admin.system.model.dto.SysUserDTO;
import com.lanjii.biz.admin.system.model.vo.SysUserVO;
import com.lanjii.core.base.BaseEntity;
import com.lanjii.core.base.BaseEntityMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Date;

/**
 * 系统用户表(SysUser)表实体类
 *
 * @author lanjii
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_user")
public class SysUser extends BaseEntity<SysUser> {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名
     */
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
    private Integer isEnabled;

    /**
     * 所属部门 ID
     */
    private Long deptId;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;

    /**
     * 是否系统管理员（0-否，1-是）
     */
    private Integer isAdmin;

    @Mapper
    public interface SysUserMapper extends BaseEntityMapper<SysUser, SysUserVO, SysUserDTO> {

        @Mapping(target = "deptName",expression = "java(deptIdToName(entity.getDeptId()))")
        @Mapping(target = "statusLabel", expression = "java(dictValueToLabel(entity.getIsEnabled(),\"STATUS\"))")
        SysUserVO toVo(SysUser entity);

    }

    public static final SysUserMapper INSTANCE = Mappers.getMapper(SysUserMapper.class);

}
