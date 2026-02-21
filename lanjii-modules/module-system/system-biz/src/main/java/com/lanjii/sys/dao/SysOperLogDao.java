package com.lanjii.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lanjii.sys.entity.SysOperLog;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * 用户操作日志表(SysOperLog)表数据库访问层
 *
 * @author lanjii
 */
@Mapper
public interface SysOperLogDao extends BaseMapper<SysOperLog> {

    /**
     * 硬删除15天前的操作日志
     *
     * @param beforeTime 15天前的时间
     * @return 删除的记录数
     */
    @Delete("DELETE FROM sys_oper_log WHERE oper_time < #{beforeTime}")
    int deleteOperLogsBefore(@Param("beforeTime") LocalDateTime beforeTime);

}
