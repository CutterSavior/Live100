package com.lanjii.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lanjii.model.entity.SysRoleMenuRel;

import java.util.List;

/**
 * <p>
 * 角色和菜单的关联表 Mapper 接口
 * </p>
 *
 * @author lizheng
 * @since 2023-08-28
 */
public interface SysRoleMenuRelMapper extends BaseMapper<SysRoleMenuRel> {

    void batchInsert(List<SysRoleMenuRel> relations);
}
