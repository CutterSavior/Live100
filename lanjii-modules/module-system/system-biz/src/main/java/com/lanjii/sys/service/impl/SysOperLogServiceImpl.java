package com.lanjii.sys.service.impl;

import com.lanjii.sys.entity.SysOperLog;
import com.lanjii.sys.dao.SysOperLogDao;
import com.lanjii.sys.service.SysOperLogService;
import com.lanjii.system.api.dto.SysOperLogDTO;
import com.lanjii.system.api.vo.SysOperLogVO;
import com.lanjii.framework.mp.base.BaseServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 用户操作日志表(SysOperLog)表服务实现类
 *
 * @author lanjii
 */
@RequiredArgsConstructor
@Service("sysOperLogService")
public class SysOperLogServiceImpl extends BaseServiceImpl<SysOperLogDao, SysOperLog> implements SysOperLogService {

    @Override
    public void saveOperLog(SysOperLogDTO dto) {
        SysOperLog entity = SysOperLog.INSTANCE.toEntity(dto);
        save(entity);
    }

    @Override
    public SysOperLogVO getByIdNew(Long id) {
        SysOperLog entity = getById(id);
        return SysOperLog.INSTANCE.toVo(entity);
    }

    @Override
    public void cleanOperLog() {
        LocalDateTime fifteenDaysAgo = LocalDateTime.now().minusDays(15);
        baseMapper.deleteOperLogsBefore(fifteenDaysAgo);
    }

}
