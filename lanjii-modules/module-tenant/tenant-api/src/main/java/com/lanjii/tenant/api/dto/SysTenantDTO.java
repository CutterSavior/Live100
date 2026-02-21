package com.lanjii.tenant.api.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 租户数据传输对象
 *
 * @author lanjii
 */
@Data
public class SysTenantDTO {

    private Long id;
    private String tenantCode;
    private String tenantName;
    private Long packageId;
    private String contactName;
    private String contactPhone;
    private Integer status;
    private LocalDateTime expireTime;

}
