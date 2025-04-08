package com.lanjii.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lanjii.model.entity.SysUserRoleRel;

import java.util.List;

/**
 * <p>
 * 用户和角色关联表 Mapper 接口
 * </p>
 *
 * @author lizheng
 * @since 2023-08-28
 */
public interface SysUserRoleRelMapper extends BaseMapper<SysUserRoleRel> {

    void batchInsert(List<SysUserRoleRel> relations);
}
