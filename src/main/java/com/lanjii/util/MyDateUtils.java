package com.lanjii.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 时间工具类
 *
 * @author lizheng
 * @date 2024-09-27
 */
public class MyDateUtils {

    public static String nowDate() {
        return getCurrentDate("yyyy-MM-dd");
    }

    public static String nowTime() {
        return getCurrentDate("yyyy-MM-dd HH:mm:ss");
    }

    public static String getCurrentDate(String format) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return now.format(formatter);
    }
}
