package com.lanjii.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lanjii.core.base.BaseModel;
import com.lanjii.core.base.BaseModelMapper;
import com.lanjii.model.vo.SysDictVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 字典表
 *
 * @author lizheng
 * @date 2024-10-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysDict extends BaseModel {

    public static final SysDictModelMapper INSTANCE = Mappers.getMapper(SysDictModelMapper.class);
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 字典描述
     */
    private String description;
    /**
     * 字典编码
     */
    private String dictCode;
    /**
     * 字典名称
     */
    private String dictName;

    @Mapper
    public interface SysDictModelMapper extends BaseModelMapper<SysDictVo, SysDict> {
        @Override
        SysDictVo modelToVo(SysDict model);

    }

}
