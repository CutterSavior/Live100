package com.lanjii.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 文件类型大类枚举
 *
 * @author lizheng
 */
@Getter
@RequiredArgsConstructor
public enum FileCategory {

    /**
     * 图片
     */
    IMAGE("图片"),
    /**
     * 视频
     */
    VIDEO("视频"),
    /**
     * 音频
     */
    AUDIO("音频"),
    /**
     * 文档
     */
    DOCUMENT("文档"),
    /**
     * 压缩包
     */
    ARCHIVE("压缩包"),
    /**
     * 文本
     */
    TEXT("文本"),
    /**
     * 其他
     */
    OTHER("其他");

    private final String description;

}
