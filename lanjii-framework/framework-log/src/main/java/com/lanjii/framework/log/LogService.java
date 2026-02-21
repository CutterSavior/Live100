package com.lanjii.framework.log;

import com.lanjii.framework.log.model.LoginLogRecord;
import com.lanjii.framework.log.model.OperationLogRecord;

/**
 * 日志服务接口
 *
 * @author lanjii
 */
public interface LogService {

    /**
     * 保存操作日志
     *
     * @param record 操作日志记录
     */
    void saveOperLog(OperationLogRecord record);

    /**
     * 保存登录日志
     *
     * @param record 登录日志记录
     */
    void saveLoginLog(LoginLogRecord record);

}
