package com.lanjii.biz.admin.log.service.impl;

import com.lanjii.biz.admin.log.dao.SysOperLogDao;
import com.lanjii.biz.admin.log.model.dto.SysOperLogDTO;
import com.lanjii.biz.admin.log.model.vo.SysOperLogVO;
import com.lanjii.core.base.BaseServiceImpl;
import com.lanjii.biz.admin.log.model.entity.SysOperLog;
import com.lanjii.biz.admin.log.service.SysOperLogService;
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
