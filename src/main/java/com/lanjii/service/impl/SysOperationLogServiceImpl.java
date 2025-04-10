package com.lanjii.service.impl;

import com.lanjii.core.base.BaseServiceImpl;
import com.lanjii.dao.SysOperationLogMapper;
import com.lanjii.model.entity.SysOperationLog;
import com.lanjii.service.ISysOperationLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 操作日志表 服务实现类
 *
 * @author lizheng
 * @date 2025-04-10
 */
@Service
@RequiredArgsConstructor
public class SysOperationLogServiceImpl extends BaseServiceImpl<SysOperationLogMapper, SysOperationLog> implements ISysOperationLogService {

}
