package com.lanjii.biz.admin.system.service;

import com.lanjii.biz.admin.system.model.dto.SysDeptDTO;
import com.lanjii.biz.admin.system.model.vo.SysDeptVO;
import com.lanjii.core.base.BaseService;
import com.lanjii.biz.admin.system.model.entity.SysDept;

/**
 * 系统部门表(SysDept)表服务接口
 *
 * @author lanjii
 */
public interface SysDeptService extends BaseService<SysDept> {
    
    /**
     * 根据ID更新部门信息
     *
     * @param id  部门ID
     * @param dto 部门DTO
     */
    void updateByIdNew(Long id, SysDeptDTO dto);
    
    /**
     * 保存部门信息
     *
     * @param dto 部门DTO
     */
    void saveNew(SysDeptDTO dto);

    /**
     * 根据ID获取部门详情
     *
     * @param id 部门ID
     * @return 部门视图对象
     */
    SysDeptVO getByIdNew(Long id);

    /**
     * 删除部门及其所有子部门
     *
     * @param id 部门ID
     */
    void deleteDeptWithChildren(Long id);
}

