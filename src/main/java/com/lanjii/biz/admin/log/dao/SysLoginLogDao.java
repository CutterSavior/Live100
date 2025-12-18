package com.lanjii.biz.admin.log.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lanjii.biz.admin.log.model.entity.SysLoginLog;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * 用户登录日志表(SysLoginLog)表数据库访问层
 *
 * @author lanjii
 */
@Mapper
public interface SysLoginLogDao extends BaseMapper<SysLoginLog> {

    /**
     * 硬删除15天前的登录日志
     *
     * @param beforeTime 15天前的时间
     * @return 删除的记录数
     */
    @Delete("DELETE FROM sys_login_log WHERE login_time < #{beforeTime}")
    int deleteLoginLogsBefore(@Param("beforeTime") LocalDateTime beforeTime);

}
