package com.lanjii.tenant.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lanjii.framework.mp.base.BaseEntity;
import com.lanjii.framework.mp.base.BaseEntityMapper;
import com.lanjii.tenant.api.dto.SysTenantDTO;
import com.lanjii.tenant.api.vo.SysTenantVO;
import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

/**
 * 租户实体
 *
 * @author lanjii
 */
@Data
@TableName("sys_tenant")
public class SysTenant extends BaseEntity {

    /**
     * 租户ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 租户名称
     */
    private String tenantName;

    /**
     * 套餐ID
     */
    private Long packageId;

    /**
     * 联系人
     */
    private String contactName;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

    @Mapper
    public interface SysTenantMapper extends BaseEntityMapper<SysTenant, SysTenantVO, SysTenantDTO> {

        SysTenantVO toVo(SysTenant entity);

        SysTenant toEntity(SysTenantDTO dto);

    }

    public static final SysTenantMapper INSTANCE = Mappers.getMapper(SysTenantMapper.class);

}
