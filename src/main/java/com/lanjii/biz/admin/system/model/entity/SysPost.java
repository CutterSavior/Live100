package com.lanjii.biz.admin.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lanjii.biz.admin.system.model.dto.SysPostDTO;
import com.lanjii.biz.admin.system.model.vo.SysPostVO;
import com.lanjii.core.base.BaseEntity;
import com.lanjii.core.base.BaseEntityMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 系统岗位表(SysPost)表实体类
 *
 * @author lanjii
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_post")
public class SysPost extends BaseEntity<SysPost> {

    /**
     * 岗位ID
     */
    private Long id;

    /**
     * 岗位编码
     */
    private String postCode;

    /**
     * 岗位名称
     */
    private String postName;

    /**
     * 显示顺序
     */
    private Integer sortOrder;

    /**
     * 是否启用（1启用 0禁用）
     */
    private Integer isEnabled;

    /**
     * 备注
     */
    private String remark;

    @Mapper
    public interface SysPostMapper extends BaseEntityMapper<SysPost, SysPostVO, SysPostDTO> {

        @Mapping(target = "isEnabledLabel", expression = "java(dictValueToLabel(entity.getIsEnabled(),\"STATUS\"))")
        SysPostVO toVo(SysPost entity);

    }

    public static final SysPostMapper INSTANCE = Mappers.getMapper(SysPostMapper.class);


}
