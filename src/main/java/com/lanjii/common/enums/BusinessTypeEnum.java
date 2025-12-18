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
public enum BusinessTypeEnum implements CodeEnum<Integer> {

    /**
     * 新增
     */
    INSERT(0, "新增"),

    /**
     * 修改
     */
    UPDATE(1, "修改"),

    /**
     * 删除
     */
    DELETE(2, "删除"),

    /**
     * 查询
     */
    SELECT(3, "查询"),

    /**
     * 导出
     */
    EXPORT(4, "导出"),

    /**
     * 导入
     */
    IMPORT(5, "导入"),

    /**
     * 其他
     */
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
        return CodeEnum.getByCode(BusinessTypeEnum.class, code);
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
