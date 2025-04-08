package com.lanjii.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author lizheng
 * @since 2024-10-09
 */
@Data
public class UploadFileDto implements Serializable {

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

}
