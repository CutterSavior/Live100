package com.lanjii.biz.admin.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lanjii.biz.admin.system.model.dto.SysDictDataDTO;
import com.lanjii.biz.admin.system.model.vo.SysDictDataVO;
import com.lanjii.core.base.BaseEntity;
import com.lanjii.core.base.BaseEntityMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 字典数据表(SysDictData)表实体类
 *
 * @author lanjii
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dict_data")
public class SysDictData extends BaseEntity<SysDictData> {

    /**
     * 字典数据主键
     */
    private Long id;

    /**
     * 字典排序
     */
    private Integer sortOrder;

    /**
     * 字典标签
     */
    private String dictLabel;

    /**
     * 字典键值
     */
    private Integer dictValue;

    /**
     * 字典类型编码
     */
    private String dictType;


    /**
     * 是否启用（1启用 0禁用）
     */
    private Integer isEnabled;

    /**
     * 备注
     */
    private String remark;

    @Mapper
    public interface SysDictDataMapper extends BaseEntityMapper<SysDictData, SysDictDataVO, SysDictDataDTO> {

        @Mapping(target = "isEnabledLabel", expression = "java(dictValueToLabel(entity.getIsEnabled(),\"STATUS\"))")
        SysDictDataVO toVo(SysDictData entity);

    }

    public static final SysDictData.SysDictDataMapper INSTANCE = Mappers.getMapper(SysDictData.SysDictDataMapper.class);

}
