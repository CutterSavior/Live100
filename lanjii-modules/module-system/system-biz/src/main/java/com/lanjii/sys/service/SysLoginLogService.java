package com.lanjii.sys.service;

import com.lanjii.sys.entity.SysLoginLog;
import com.lanjii.system.api.dto.SysLoginLogDTO;
import com.lanjii.system.api.vo.SysLoginLogVO;
import com.lanjii.framework.mp.base.BaseService;

/**
 * 用户登录日志表(SysLoginLog)表服务接口
 *
 * @author lanjii
 */
public interface SysLoginLogService extends BaseService<SysLoginLog> {

    /**
     * 保存登录日志
     *
     * @param dto 登录日志DTO
     */
    void saveLoginLog(SysLoginLogDTO dto);

    /**
     * 根据ID获取登录日志详情
     *
     * @param id 日志ID
     * @return 登录日志视图对象
     */
    SysLoginLogVO getByIdNew(Long id);

    /**
     * 清空登录日志
     */
    void cleanLoginLog();

}
