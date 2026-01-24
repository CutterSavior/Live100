package com.lanjii.biz.admin.log.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lanjii.biz.admin.log.model.dto.SysLoginLogDTO;
import com.lanjii.biz.admin.log.model.vo.SysLoginLogVO;
import com.lanjii.core.base.BaseEntity;
import com.lanjii.core.base.BaseEntityMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

/**
 * 用户登录日志表(SysLoginLog)表实体类
 *
 * @author lanjii
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_login_log")
public class SysLoginLog extends BaseEntity<SysLoginLog> {

    /**
     * 日志ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 登录IP地址
     */
    private String ipAddress;

    /**
     * 登录地点
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 登录类型（0-登录，1-登出）
     */
    private Integer loginType;

    /**
     * 登录状态（0-失败，1-成功）
     */
    private Integer status;

    /**
     * 提示消息
     */
    private String msg;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;

    @Mapper
    public interface SysLoginLogMapper extends BaseEntityMapper<SysLoginLog, SysLoginLogVO, SysLoginLogDTO> {

    }

    public static final SysLoginLogMapper INSTANCE = Mappers.getMapper(SysLoginLogMapper.class);

}
