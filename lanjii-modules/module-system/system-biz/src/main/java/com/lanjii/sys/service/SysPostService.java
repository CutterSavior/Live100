package com.lanjii.sys.service;

import com.lanjii.sys.entity.SysPost;
import com.lanjii.system.api.dto.SysPostDTO;
import com.lanjii.system.api.vo.SysPostVO;
import com.lanjii.framework.mp.base.BaseService;

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

