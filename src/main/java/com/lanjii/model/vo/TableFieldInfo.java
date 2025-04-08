package com.lanjii.model.vo;

import lombok.Data;

/**
 * 表单字段信息
 *
 * @author LiZheng
 * @date 2024-11-04
 */
@Data
public class TableFieldInfo {

    private String tableName;
    private String columnName;
    private String dataType;
    private String columnComment;
    private String columnKey;

}
