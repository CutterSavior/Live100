package com.lanjii.common.enums;

/**
 * 查询方式
 *
 * @author lanjii
 */
public enum QueryType {
    /**
     * 等于
     */
    EQUAL,
    /**
     * 模糊查询
     */
    LIKE,
    /**
     * 左模糊
     */
    LEFT_LIKE,
    /**
     * 右模糊
     */
    RIGHT_LIKE,
    /**
     * 大于
     */
    GT,
    /**
     * 大于等于
     */
    LT,
    /**
     * 小于
     */
    GE,
    /**
     * 小于等于
     */
    LE,
    /**
     * IN查询
     */
    IN,
    /**
     * 范围查询
     */
    BETWEEN,
    /**
     * 不等于
     */
    NE
}
