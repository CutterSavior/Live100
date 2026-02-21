package com.lanjii.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lanjii.system.api.dto.ToolFileDTO;
import com.lanjii.system.api.vo.ToolFileVO;
import com.lanjii.framework.mp.base.TenantBaseEntity;
import com.lanjii.sys.base.AdminBaseMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 系统文件管理表(ToolFile)实体类
 *
 * @author lanjii
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("tool_file")
public class ToolFile extends TenantBaseEntity {

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
    public interface ToolFileMapper extends AdminBaseMapper<ToolFile, ToolFileVO, ToolFileDTO> {

        @Mapping(target = "fileTypeLabel", expression = "java(com.lanjii.framework.web.util.FileUtils.detectFileCategoryByExtension(entity.getFileExtension()).getDescription())")
        @Mapping(target = "fileSizeLabel", expression = "java(com.lanjii.framework.web.util.FileUtils.formatFileSize(entity.getFileSize()))")
        @Mapping(target = "fullFilePath", expression = "java(getConfig(com.lanjii.common.constant.SysConfigKeys.FILE_SERVER_BASE_URL)+entity.getFilePath())")
        ToolFileVO toVo(ToolFile entity);

    }

    public static final ToolFileMapper INSTANCE = Mappers.getMapper(ToolFileMapper.class);

}
