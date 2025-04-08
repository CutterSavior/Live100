package com.lanjii.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lanjii.core.base.BaseModel;
import com.lanjii.core.base.BaseModelMapper;
import com.lanjii.model.vo.SysDeptVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 部门
 *
 * @author lizheng
 * @date 2024-10-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysDept extends BaseModel {

    public static final SysDeptModelMapper INSTANCE = Mappers.getMapper(SysDeptModelMapper.class);
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 父级部门 ID
     */
    private Long parentId;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 排序
     */
    private Integer sort;

    @Mapper
    public interface SysDeptModelMapper extends BaseModelMapper<SysDeptVo, SysDept> {

        @Override
        @Mapping(target = "statusName", expression = "java(getDictLabel(model.getStatus(),\"status\"))")
        SysDeptVo modelToVo(SysDept model);

    }

}
