package com.lanjii.biz.admin.system.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 角色菜单分配DTO
 *
 * @author lanjii
 */
@Data
public class RoleMenuAssignDTO {

    /**
     * 菜单ID列表
     */
    @NotNull(message = "菜单ID列表不能为空")
    private List<Long> menuIds;
}
