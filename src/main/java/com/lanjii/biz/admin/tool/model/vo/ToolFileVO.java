package com.lanjii.biz.admin.tool.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统工具-文件管理VO
 *
 * @author lanjii
 */
@Data
public class ToolFileVO {

    /**
     * 文件ID
     */
    private Long id;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 原始文件名
     */
    private String originalName;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 文件全路径
     */
    private String fullFilePath;

    /**
     * 文件大小（字节）
     */
    private Long fileSize;

    /**
     * 文件大小标签（格式化后）
     */
    private String fileSizeLabel;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件类型名称
     */
    private String fileTypeLabel;

    /**
     * 文件扩展名
     */
    private String fileExtension;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
