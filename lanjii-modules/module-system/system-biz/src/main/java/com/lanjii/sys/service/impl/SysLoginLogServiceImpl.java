package com.lanjii.sys.service.impl;

import com.lanjii.sys.dao.SysLoginLogDao;
import com.lanjii.sys.entity.SysLoginLog;
import com.lanjii.system.api.dto.SysLoginLogDTO;
import com.lanjii.system.api.vo.SysLoginLogVO;
import com.lanjii.framework.mp.base.BaseServiceImpl;
import com.lanjii.sys.service.SysLoginLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 用户登录日志表(SysLoginLog)表服务实现类
 *
 * @author lanjii
 */
@RequiredArgsConstructor
@Service("sysLoginLogService")
public class SysLoginLogServiceImpl extends BaseServiceImpl<SysLoginLogDao, SysLoginLog> implements SysLoginLogService {

    @Override
    public void saveLoginLog(SysLoginLogDTO dto) {
        SysLoginLog entity = SysLoginLog.INSTANCE.toEntity(dto);
        save(entity);
    }

    @Override
    public SysLoginLogVO getByIdNew(Long id) {
        SysLoginLog entity = getById(id);
        return SysLoginLog.INSTANCE.toVo(entity);
    }

    @Override
    public void cleanLoginLog() {
        LocalDateTime fifteenDaysAgo = LocalDateTime.now().minusDays(15);
        baseMapper.deleteLoginLogsBefore(fifteenDaysAgo);
    }

}
