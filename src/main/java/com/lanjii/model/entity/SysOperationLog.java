package com.lanjii.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lanjii.core.base.BaseModelMapper;
import com.lanjii.model.vo.SysOperationLogVo;
import lombok.Data;
import lombok.experimental.Accessors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 操作日志表
 *
 * @author lizheng
 * @date 2025-04-10
 */
@Data
@Accessors(chain = true)
public class SysOperationLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 操作描述
     */
    private String operation;

    /**
     * 日志类型
     */
    private Integer type;

    /**
     * 操作IP
     */
    private String ip;

    /**
     * 执行时长(毫秒)
     */
    private Long duration;

    /**
     * 是否成功
     */
    private Integer isSuccess;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;

    /**
     * 操作人
     */
    private String operationBy;


    @Mapper
    public interface SysOperationLogModelMapper extends BaseModelMapper<SysOperationLogVo, SysOperationLog> {
        @Override
        @Mapping(target = "typeLabel", expression = "java(getDictLabel(model.getType(),\"OPERATION_TYPE\"))")
        SysOperationLogVo modelToVo(SysOperationLog model);

    }

    public static final SysOperationLogModelMapper INSTANCE = Mappers.getMapper(SysOperationLogModelMapper.class);

}
