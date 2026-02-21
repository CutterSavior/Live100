package com.lanjii.tenant.api.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 租户套餐视图对象
 *
 * @author lanjii
 */
@Data
public class SysTenantPackageVO {

    private Long id;
    private String packageName;
    private String menuIds;
    private List<Long> menuIdList;
    private Integer status;
    private String remark;
    private LocalDateTime createTime;

}
