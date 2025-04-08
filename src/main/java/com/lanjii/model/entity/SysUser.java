package com.lanjii.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lanjii.core.base.BaseModel;
import com.lanjii.core.base.BaseModelMapper;
import com.lanjii.model.vo.SysUserVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * <p>
 * 
 * </p>
 *
 * @author lizheng
 * @since 2023-08-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysUser extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
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
    private Long deptId;

    @Mapper
    public interface SysDataPermModelMapper extends BaseModelMapper<SysUserVo, SysUser> {
        @Override
        SysUserVo modelToVo(SysUser model);
    }

    public static final SysDataPermModelMapper INSTANCE = Mappers.getMapper(SysDataPermModelMapper.class);
}
