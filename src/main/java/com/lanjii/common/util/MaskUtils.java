package com.lanjii.common.util;

/**
 * 脱敏 工具类
 *
 * @author lanjii
 */
public class MaskUtils {

    /**
     * 脱敏处理
     * 显示格式: sk-ab****xyz
     */
    public static String maskApiKey(String key) {
        if (key == null || key.length() < 8) {
            return "****";
        }
        int maskLength = Math.min(key.length() - 8, 20);
        return key.substring(0, 4) + "*".repeat(maskLength) + key.substring(key.length() - 4);
    }

}
