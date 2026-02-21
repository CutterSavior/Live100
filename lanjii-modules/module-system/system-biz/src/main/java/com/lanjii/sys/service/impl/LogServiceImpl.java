package com.lanjii.sys.service.impl;

import com.lanjii.sys.service.SysOperLogService;
import com.lanjii.system.api.dto.SysLoginLogDTO;
import com.lanjii.system.api.dto.SysOperLogDTO;
import com.lanjii.sys.service.SysLoginLogService;
import com.lanjii.framework.log.LogService;
import com.lanjii.framework.log.model.LoginLogRecord;
import com.lanjii.framework.log.model.OperationLogRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 日志服务实现类
 * 桥接 core 层接口与 admin 层服务
 *
 * @author lanjii
 */
@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {

    private final SysOperLogService sysOperLogService;
    private final SysLoginLogService sysLoginLogService;

    @Override
    public void saveOperLog(OperationLogRecord record) {
        SysOperLogDTO sysOperLogDTO = new SysOperLogDTO();
        sysOperLogDTO.setTitle(record.getTitle());
        sysOperLogDTO.setBusinessType(record.getBusinessType());
        sysOperLogDTO.setMethod(record.getMethod());
        sysOperLogDTO.setRequestMethod(record.getRequestMethod());
        sysOperLogDTO.setOperName(record.getOperName());
        sysOperLogDTO.setOperUrl(record.getOperUrl());
        sysOperLogDTO.setOperParam(record.getOperParam());
        sysOperLogDTO.setJsonResult(record.getJsonResult());
        sysOperLogDTO.setStatus(record.getStatus());
        sysOperLogDTO.setErrorMsg(record.getErrorMsg());
        sysOperLogDTO.setOperTime(record.getOperTime());
        sysOperLogDTO.setCostTime(record.getCostTime());
        sysOperLogService.saveOperLog(sysOperLogDTO);
    }

    @Override
    public void saveLoginLog(LoginLogRecord record) {
        SysLoginLogDTO sysLoginLogDTO = new SysLoginLogDTO();
        sysLoginLogDTO.setUsername(record.getUsername());
        sysLoginLogDTO.setIpAddress(record.getIpAddress());
        sysLoginLogDTO.setLoginLocation(record.getLoginLocation());
        sysLoginLogDTO.setBrowser(record.getBrowser());
        sysLoginLogDTO.setOs(record.getOs());
        sysLoginLogDTO.setLoginType(record.getLoginType());
        sysLoginLogDTO.setStatus(record.getStatus());
        sysLoginLogDTO.setMsg(record.getMsg());
        sysLoginLogDTO.setLoginTime(record.getLoginTime());
        sysLoginLogService.saveLoginLog(sysLoginLogDTO);
    }
}
