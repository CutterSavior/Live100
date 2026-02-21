package com.lanjii.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lanjii.sys.entity.SysDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统部门表(SysDept)表数据库访问层
 *
 * @author lanjii
 */
public interface SysDeptDao extends BaseMapper<SysDept> {

    /**
     * 根据父级ID查询子部门
     *
     * @param parentId 父级部门ID
     * @return 子部门列表
     */
    List<SysDept> selectChildrenByParentId(@Param("parentId") Long parentId);

    /**
     * 根据部门ID查询所有子孙部门（使用ancestors字段）
     *
     * @param deptId 部门ID
     * @return 所有子孙部门列表
     */
    List<SysDept> selectDescendantsByDeptId(@Param("deptId") Long deptId);

}

