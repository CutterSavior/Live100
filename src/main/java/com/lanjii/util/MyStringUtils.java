package com.lanjii.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author lizheng
 * @date 2024-09-27
 */
public class MyStringUtils extends StringUtils {

    public static void main(String[] args) {
        System.out.println(toPascalCase("aaa_bb"));
    }

    /**
     * 下划线转小驼峰
     *
     * @param snakeCase 下划线分隔符的小写字母单词
     * @return 小驼峰
     */
    public static String toCamelCase(String snakeCase) {
        StringBuilder result = new StringBuilder();

        // 切割字符串并以下划线为分隔符
        String[] words = snakeCase.split("_");

        // 遍历分割后的单词
        for (int i = 0; i < words.length; i++) {
            String word = words[i];

            // 将首字母转换为小写
            if (i == 0) {
                result.append(word.toLowerCase());
            } else {
                // 将其余单词的首字母转换为大写
                result.append(Character.toUpperCase(word.charAt(0)));
                result.append(word.substring(1).toLowerCase());
            }
        }

        return result.toString();
    }

    /**
     * 下划线转大驼峰
     *
     * @param snakeCase 下划线分隔符的小写字母单词
     * @return 大驼峰
     */
    public static String toPascalCase(String snakeCase) {
        String[] words = snakeCase.split("_");
        return Arrays.stream(words)
                .map(word -> {
                    if (word.isEmpty()) {
                        return word;
                    } else {
                        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
                    }
                })
                .collect(Collectors.joining());
    }
}
