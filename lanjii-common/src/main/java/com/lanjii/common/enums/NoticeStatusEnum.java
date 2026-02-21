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
public enum NoticeStatusEnum {

    DRAFT(0, "草稿"),
    PUBLISHED(1, "已发布"),
    REVOKED(2, "已撤回");

    private final Integer code;
    private final String desc;

    public static NoticeStatusEnum fromCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (NoticeStatusEnum e : values()) {
            if (code.equals(e.getCode())) {
                return e;
            }
        }
        return null;
    }

    public static String getDescByCode(Integer code) {
        NoticeStatusEnum status = fromCode(code);
        return status != null ? status.getDesc() : DRAFT.getDesc();
    }
}
