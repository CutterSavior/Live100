package com.lanjii.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lanjii.system.api.dto.SysDictDataDTO;
import com.lanjii.system.api.vo.SysDictDataVO;
import com.lanjii.framework.mp.base.BaseEntity;
import com.lanjii.sys.base.AdminBaseMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 字典数据表(SysDictData)实体类
 *
 * @author lanjii
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dict_data")
public class SysDictData extends BaseEntity {

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

    /**
     * 标签类型: success/info/warning/danger/default
     */
    private String tagType;

    /**
     * 自定义标签颜色(hex或颜色名)
     */
    private String tagColor;

    /**
     * 标签主题: dark/light/plain
     */
    private String tagEffect;

    @Mapper
    public interface SysDictDataMapper extends AdminBaseMapper<SysDictData, SysDictDataVO, SysDictDataDTO> {

    }

    public static final SysDictData.SysDictDataMapper INSTANCE = Mappers.getMapper(SysDictData.SysDictDataMapper.class);

}
