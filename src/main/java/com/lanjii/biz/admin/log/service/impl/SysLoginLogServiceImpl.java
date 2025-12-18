package com.lanjii.biz.admin.log.service.impl;

import com.lanjii.biz.admin.log.dao.SysLoginLogDao;
import com.lanjii.biz.admin.log.model.dto.SysLoginLogDTO;
import com.lanjii.biz.admin.log.model.vo.SysLoginLogVO;
import com.lanjii.core.base.BaseServiceImpl;
import com.lanjii.biz.admin.log.model.entity.SysLoginLog;
import com.lanjii.biz.admin.log.service.SysLoginLogService;
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
