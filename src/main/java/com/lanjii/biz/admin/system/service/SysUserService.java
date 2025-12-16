package com.lanjii.biz.admin.system.service;

import com.lanjii.biz.admin.system.model.dto.SysUserDTO;
import com.lanjii.biz.admin.system.model.dto.UpdateProfileDTO;
import com.lanjii.biz.admin.system.model.vo.SysUserVO;
import com.lanjii.core.base.BaseService;
import com.lanjii.biz.admin.system.model.entity.SysUser;

/**
 * 系统用户表(SysUser)表服务接口
 *
 * @author lanjii
 */
public interface SysUserService extends BaseService<SysUser> {

    /**
     * 保存用户信息
     *
     * @param dto 用户DTO
     */
    void saveNew(SysUserDTO dto);

    /**
     * 根据ID获取用户详情
     *
     * @param id 用户ID
     * @return 用户视图对象
     */
    SysUserVO getByIdNew(Long id);

    /**
     * 根据ID更新用户信息
     *
     * @param id  用户ID
     * @param dto 用户DTO
     */
    void updateByIdNew(Long id, SysUserDTO dto);

    /**
     * 根据ID删除用户及其关联数据
     *
     * @param id 用户ID
     */
    void removeByIdNew(Long id);

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     */
    SysUser getUserByUsername(String username);

    /**
     * 重置用户密码
     *
     * @param id 用户ID
     */
    void resetPassword(Long id);

    /**
     * 切换用户状态
     *
     * @param id 用户ID
     */
    void toggleStatus(Long id);

    /**
     * 修改当前用户自己的信息
     *
     * @param userId 用户ID
     * @param dto 用户信息DTO
     */
    void updateProfile(Long userId, UpdateProfileDTO dto);
}

