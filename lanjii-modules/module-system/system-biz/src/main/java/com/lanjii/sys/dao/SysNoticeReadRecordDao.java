package com.lanjii.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lanjii.sys.entity.SysNoticeReadRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 通知阅读记录表(SysNoticeReadRecord)数据库访问层
 *
 * @author lanjii
 */
public interface SysNoticeReadRecordDao extends BaseMapper<SysNoticeReadRecord> {

    /**
     * 批量插入阅读记录
     *
     * @param userId    用户ID
     * @param noticeIds 通知ID列表
     * @param level     级别（可选）
     * @return 插入数量
     */
    int batchInsertReadRecords(@Param("userId") Long userId,
                               @Param("noticeIds") List<Long> noticeIds,
                               @Param("level") String level);

}
