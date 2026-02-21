package com.lanjii.system.api.dto;

import com.lanjii.common.annotation.QueryCondition;
import com.lanjii.common.enums.QueryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 系统工具-文件管理DTO
 *
 * @author lanjii
 */
@Data
public class ToolFileDTO {

    /**
     * 文件ID
     */
    private Long id;

    /**
     * 文件名称
     */
    @NotBlank(message = "文件名称不能为空")
    private String fileName;

    /**
     * 原始文件名
     */
    @QueryCondition(type = QueryType.LIKE)
    private String originalName;

    /**
     * 文件路径
     */
    @NotBlank(message = "文件路径不能为空")
    private String filePath;

    /**
     * 文件大小（字节）
     */
    @NotNull(message = "文件大小不能为空")
    private Long fileSize;

    /**
     * 文件类型
     */
    @QueryCondition
    private String fileType;

    /**
     * 文件扩展名
     */
    private String fileExtension;

}
