package com.lanjii.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lanjii.model.entity.Notice;
import com.lanjii.model.vo.NoticeVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 通知Mapper 接口
 * </p>
 *
 * @author lizheng
 * @since 2023-08-28
 */
public interface NoticeMapper extends BaseMapper<Notice> {

    /**
     * 查询我的消息通知列表
     */
    @Select("select t2.*,t1.is_read as isRead from notice_user_rel t1 left join notice t2 on t1.notice_id=t2.id where t1.user_id=#{userId} order by t2.updated_time desc")
    List<NoticeVo> myList(@Param("userId") Long userId);

}
