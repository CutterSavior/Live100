package com.lanjii.tenant.api.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 租户视图对象
 *
 * @author lanjii
 */
@Data
public class SysTenantVO {

    private Long id;
    private String tenantCode;
    private String tenantName;
    private Long packageId;
    private String packageName;
    private String contactName;
    private String contactPhone;
    private Integer status;
    private LocalDateTime expireTime;
    private LocalDateTime createTime;

}
