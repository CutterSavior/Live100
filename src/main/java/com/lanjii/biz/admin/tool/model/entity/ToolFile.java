package com.lanjii.biz.admin.tool.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lanjii.biz.admin.tool.model.dto.ToolFileDTO;
import com.lanjii.biz.admin.tool.model.vo.ToolFileVO;
import com.lanjii.core.base.BaseEntity;
import com.lanjii.core.base.BaseEntityMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 系统工具-文件管理表(ToolFile)表实体类
 *
 * @author lanjii
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("tool_file")
public class ToolFile extends BaseEntity<ToolFile> {

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
     * 文件大小（字节）
     */
    private Long fileSize;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件扩展名
     */
    private String fileExtension;

    @Mapper
    public interface ToolFileMapper extends BaseEntityMapper<ToolFile, ToolFileVO, ToolFileDTO> {

        @Mapping(target = "fileTypeLabel", expression = "java(com.lanjii.common.util.FileUtils.detectFileCategoryByExtension(entity.getFileExtension()).getDescription())")
        @Mapping(target = "fileSizeLabel", expression = "java(com.lanjii.common.util.FileUtils.formatFileSize(entity.getFileSize()))")
        @Mapping(target = "fullFilePath", expression = "java(getConfig(com.lanjii.common.constant.SysConfigKeys.FILE_SERVER_BASE_URL)+entity.getFilePath())")
        ToolFileVO toVo(ToolFile entity);

    }

    public static final ToolFileMapper INSTANCE = Mappers.getMapper(ToolFileMapper.class);

}
