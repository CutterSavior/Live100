package com.lanjii.service;

import com.lanjii.core.base.IBaseService;
import com.lanjii.model.dto.SysUserDto;
import com.lanjii.model.vo.SysUserVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lizheng
 * @since 2023-08-28
 */
public interface ISysUserService<SysUser> extends IBaseService<SysUser> {

    /**
     * 根据账户获取用户
     *
     * @param username 账户名
     * @return 用户
     */
    com.lanjii.model.entity.SysUser getByUsername(String username);
    void saveOrUpdateNew(SysUserDto sysUserDto);

    SysUserVo getByIdNew(Long id);
}
