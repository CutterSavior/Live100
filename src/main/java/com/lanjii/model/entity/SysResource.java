package com.lanjii.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lanjii.core.base.BaseModel;
import com.lanjii.core.base.BaseModelMapper;
import com.lanjii.model.vo.SysResourceVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.io.Serializable;

/**
 * @author lizheng
 * @date 2024-10-30
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SysResource extends BaseModel implements Serializable {

    public static final SysResourceModelMapper INSTANCE = Mappers.getMapper(SysResourceModelMapper.class);
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 资源名称
     */
    private String resourceName;
    /**
     * 资源地址
     */
    private String resourceUrl;

    @Mapper
    public interface SysResourceModelMapper extends BaseModelMapper<SysResourceVo, SysResource> {

        @Override
        SysResourceVo modelToVo(SysResource model);

    }

}
