package com.lanjii.biz.admin.ai.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lanjii.biz.admin.ai.model.dto.AiMetadataFieldDTO;
import com.lanjii.biz.admin.ai.model.vo.AiMetadataFieldVO;
import com.lanjii.core.base.BaseEntity;
import com.lanjii.core.base.BaseEntityMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * AI 元数据字段配置表
 *
 * @author lanjii
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ai_metadata_field")
public class AiMetadataField extends BaseEntity<AiMetadataField> {

    public static final AiMetadataFieldMapper INSTANCE = Mappers.getMapper(AiMetadataFieldMapper.class);
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 字段名
     */
    private String fieldName;
    /**
     * 显示名称
     */
    private String displayName;
    /**
     * 数据类型（string, number, date, datetime）
     */
    private String dataType;
    /**
     * 默认值
     */
    private String defaultValue;
    /**
     * 字段描述
     */
    private String description;
    /**
     * 是否必填（1-是 0-否）
     */
    private Integer isRequired;
    /**
     * 是否可搜索（1-是 0-否）
     */
    private Integer isSearchable;
    /**
     * 使用次数
     */
    private Long useCount;

    @Mapper
    public interface AiMetadataFieldMapper extends BaseEntityMapper<AiMetadataField, AiMetadataFieldVO, AiMetadataFieldDTO> {
        AiMetadataFieldMapper INSTANCE = Mappers.getMapper(AiMetadataFieldMapper.class);
    }
}
