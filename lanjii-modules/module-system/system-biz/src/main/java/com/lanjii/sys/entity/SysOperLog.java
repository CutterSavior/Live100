package com.lanjii.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lanjii.system.api.dto.SysOperLogDTO;
import com.lanjii.system.api.vo.SysOperLogVO;
import com.lanjii.framework.mp.base.TenantBaseEntity;
import com.lanjii.sys.base.AdminBaseMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

/**
 * 用户操作日志表(SysOperLog)实体类
 *
 * @author lanjii
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_oper_log")
public class SysOperLog extends TenantBaseEntity {

    /**
     * 日志ID
     */
    private Long id;

    /**
     * 操作模块
     */
    private String title;

    /**
     * 业务类型（0-新增，1-修改，2-删除）
     */
    private Integer businessType;

    /**
     * 方法名称
     */
    private String method;

    /**
     * 请求方式（1-GET，2-POST，3-PUT，4-DELETE）
     */
    private Integer requestMethod;

    /**
     * 操作人员
     */
    private String operName;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 请求URL
     */
    private String operUrl;


    /**
     * 请求参数
     */
    private String operParam;

    /**
     * 返回参数
     */
    private String jsonResult;

    /**
     * 操作状态（0-失败，1-成功）
     */
    private Integer status;

    /**
     * 错误消息
     */
    private String errorMsg;

    /**
     * 操作时间
     */
    private LocalDateTime operTime;

    /**
     * 消耗时间（毫秒）
     */
    private Long costTime;

    @Mapper
    public interface SysOperLogMapper extends AdminBaseMapper<SysOperLog, SysOperLogVO, SysOperLogDTO> {

        @Mapping(target = "deptName", expression = "java(usernameToDeptName(entity.getOperName()))")
        SysOperLogVO toVo(SysOperLog entity);

    }

    public static final SysOperLogMapper INSTANCE = Mappers.getMapper(SysOperLogMapper.class);

}
