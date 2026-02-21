package com.lanjii.framework.mp.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 租户实体基础类
 *
 * @author lanjii
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TenantBaseEntity extends BaseEntity {

    /**
     * 租户ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Long tenantId;

}
