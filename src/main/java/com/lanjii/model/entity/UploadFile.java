package com.lanjii.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lanjii.core.base.BaseModel;
import com.lanjii.core.base.BaseModelMapper;
import com.lanjii.model.vo.UploadFileVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * <p>
 *
 * </p>
 *
 * @author lizheng
 * @since 2024-10-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class UploadFile extends BaseModel {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文件名字
     */
    private String fileName;

    /**
     * 文件路径
     */
    private String filePath;

    @Mapper
    public interface UploadFileModelMapper extends BaseModelMapper<UploadFileVo, UploadFile> {
        @Override
        @Mapping(target = "fileUrl", source = "filePath", qualifiedByName = "getFileUrl")
        UploadFileVo modelToVo(UploadFile model);
    }

    public static final UploadFileModelMapper INSTANCE = Mappers.getMapper(UploadFileModelMapper.class);

}
