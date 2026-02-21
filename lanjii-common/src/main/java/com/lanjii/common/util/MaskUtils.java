package com.lanjii.common.util;

import java.util.regex.Pattern;

/**
 * 脱敏 工具类
 *
 * @author lanjii
 */
public class MaskUtils {

    /**
     * 敏感字段正则
     */
    private static final Pattern SENSITIVE_PATTERN = Pattern.compile(
            "\"(password|pwd|oldPassword|newPassword)\"\\s*:\\s*\"[^\"]*\"");

    private static final String MASK_REPLACEMENT = "\"$1\":\"*******\"";

    /**
     * 脱敏处理 API Key
     * 显示格式: sk-ab****xyz
     */
    public static String maskApiKey(String key) {
        if (key == null || key.length() < 8) {
            return "****";
        }
        int maskLength = Math.min(key.length() - 8, 20);
        return key.substring(0, 4) + "*".repeat(maskLength) + key.substring(key.length() - 4);
    }

    /**
     * 脱敏处理 JSON 中的敏感字段（password, pwd, oldPassword, newPassword）
     */
    public static String maskSensitiveData(String data) {
        if (data == null || data.isEmpty()) {
            return data;
        }
        return SENSITIVE_PATTERN.matcher(data).replaceAll(MASK_REPLACEMENT);
    }

}
