package com.lanjii.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 通知状态枚举
 *
 * @author lanjii
 */
@Getter
@RequiredArgsConstructor
public enum NoticeStatusEnum implements CodeEnum<Integer> {

    /**
     * 草稿
     */
    DRAFT(0, "草稿"),

    /**
     * 已发布
     */
    PUBLISHED(1, "已发布");

    private final Integer code;
    private final String desc;

    /**
     * 根据状态码获取枚举
     *
     * @param code 状态码
     * @return 枚举值
     */
    public static NoticeStatusEnum fromCode(Integer code) {
        return CodeEnum.getByCode(NoticeStatusEnum.class, code);
    }

    /**
     * 根据状态码获取描述
     *
     * @param code 状态码
     * @return 描述
     */
    public static String getDescByCode(Integer code) {
        NoticeStatusEnum status = fromCode(code);
        return status != null ? status.getDesc() : DRAFT.getDesc();
    }
}
