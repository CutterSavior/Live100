package com.lanjii.tenant.api.dto;

import lombok.Data;

import java.util.List;

/**
 * 租户套餐数据传输对象
 *
 * @author lanjii
 */
@Data
public class SysTenantPackageDTO {

    private Long id;
    private String packageName;
    private List<Long> menuIds;
    private Integer status;
    private String remark;

}
