package com.lanjii.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 代码生成
 *
 * @author LiZheng
 * @date 2024-11-04
 */
@Data
public class CodeGenerator {

    @NotNull(message = "请选择需要生成的表")
    private List<String> tableNameList;

    private String tableSchema;

    private List<Integer> templateIdList;

    private String author;

}
