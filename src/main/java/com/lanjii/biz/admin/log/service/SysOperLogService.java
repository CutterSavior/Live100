package com.lanjii.biz.admin.log.service;

import com.lanjii.biz.admin.log.model.dto.SysOperLogDTO;
import com.lanjii.biz.admin.log.model.vo.SysOperLogVO;
import com.lanjii.core.base.BaseService;
import com.lanjii.biz.admin.log.model.entity.SysOperLog;

/**
 * 用户操作日志表(SysOperLog)表服务接口
 *
 * @author lanjii
 */
public interface SysOperLogService extends BaseService<SysOperLog> {

    /**
     * 保存操作日志
     *
     * @param dto 操作日志DTO
     */
    void saveOperLog(SysOperLogDTO dto);

    /**
     * 根据ID获取操作日志详情
     *
     * @param id 日志ID
     * @return 操作日志视图对象
     */
    SysOperLogVO getByIdNew(Long id);

    /**
     * 清空操作日志
     */
    void cleanOperLog();

}
