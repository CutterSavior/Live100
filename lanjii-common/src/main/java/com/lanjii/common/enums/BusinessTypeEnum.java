package com.lanjii.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 业务操作类型枚举
 *
 * @author lanjii
 */
@Getter
@RequiredArgsConstructor
public enum BusinessTypeEnum {

    INSERT(0, "新增"),
    UPDATE(1, "修改"),
    DELETE(2, "删除"),
    SELECT(3, "查询"),
    EXPORT(4, "导出"),
    IMPORT(5, "导入"),
    OTHER(9, "其他");

    private final Integer code;
    private final String desc;

    /**
     * 根据code获取枚举
     *
     * @param code 业务类型代码
     * @return 枚举值
     */
    public static BusinessTypeEnum fromCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (BusinessTypeEnum e : values()) {
            if (code.equals(e.getCode())) {
                return e;
            }
        }
        return null;
    }

    /**
     * 根据code获取描述
     *
     * @param code 业务类型代码
     * @return 描述
     */
    public static String getDescByCode(Integer code) {
        BusinessTypeEnum type = fromCode(code);
        return type != null ? type.getDesc() : OTHER.getDesc();
    }
}
