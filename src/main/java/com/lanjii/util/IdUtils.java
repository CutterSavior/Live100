package com.lanjii.util;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * ID 生成器
 *
 * @author lizheng
 * @date 2024-10-18
 */
@Slf4j
public class IdUtils {

    public static String simpleId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
