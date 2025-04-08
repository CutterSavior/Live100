package com.lanjii.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NoticeToType {

    TO_ALL(0),
    TO_USER(1);

    private final int type;
}
