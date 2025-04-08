package com.lanjii.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lanjii.core.base.BaseModel;
import com.lanjii.core.base.BaseModelMapper;
import com.lanjii.model.vo.SysDictDetailVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 字典表详情
 *
 * @author lizheng
 * @date 2024-10-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysDictDetail extends BaseModel {

    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 描述
     */
    private String description;
    /**
     * 字典编码
     */
    private String dictCode;
    /**
     * 字典标签（页面显示名称）
     */
    private String dictLabel;
    /**
     * 排序
     */
    private String dictSort;
    /**
     * 字典值
     */
    private Integer dictValue;

    @Mapper
    public interface SysDictDetailModelMapper extends BaseModelMapper<SysDictDetailVo, SysDictDetail> {
        @Override
        SysDictDetailVo modelToVo(SysDictDetail model);
    }

    public static final SysDictDetailModelMapper INSTANCE = Mappers.getMapper(SysDictDetailModelMapper.class);

}
