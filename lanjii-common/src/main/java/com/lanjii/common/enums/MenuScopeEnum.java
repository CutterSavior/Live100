package com.lanjii.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 菜单显示范围枚举
 *
 * @author lanjii
 */
@Getter
@AllArgsConstructor
public enum MenuScopeEnum {

    /**
     * 全部（租户和平台都可见）
     */
    ALL(0, "全部"),

    /**
     * 仅租户可见
     */
    TENANT(1, "租户"),

    /**
     * 仅平台可见
     */
    PLATFORM(2, "平台");

    private final Integer code;
    private final String desc;

}
