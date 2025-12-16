package com.lanjii.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 条件
 *
 * @author lizheng
 */
@AllArgsConstructor
@Getter
public enum Condition {

    EQ("eq"),
    NE("ne"),
    LT("lt"),
    LE("le"),
    GT("gt"),
    GE("ge"),
    LIKE("like"),
    LIKE_LEFT("likeLeft"),
    IN("in"),
    LIKE_RIGHT("likeRight");

    private final String expr;
}
