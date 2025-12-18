package com.lanjii.biz.admin.system.service;

import com.lanjii.biz.admin.system.model.dto.SysPostDTO;
import com.lanjii.biz.admin.system.model.vo.SysPostVO;
import com.lanjii.core.base.BaseService;
import com.lanjii.biz.admin.system.model.entity.SysPost;

/**
 * 系统岗位表(SysPost)表服务接口
 *
 * @author lanjii
 */
public interface SysPostService extends BaseService<SysPost> {
    
    /**
     * 根据ID更新岗位信息
     *
     * @param id  岗位ID
     * @param dto 岗位DTO
     */
    void updateByIdNew(Long id, SysPostDTO dto);
    
    /**
     * 保存岗位信息
     *
     * @param dto 岗位DTO
     */
    void saveNew(SysPostDTO dto);

    /**
     * 根据ID获取岗位详情
     *
     * @param id 岗位ID
     * @return 岗位视图对象
     */
    SysPostVO getByIdNew(Long id);
}

