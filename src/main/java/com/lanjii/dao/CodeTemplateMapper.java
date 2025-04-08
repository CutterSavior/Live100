package com.lanjii.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lanjii.model.entity.CodeTemplate;
import com.lanjii.model.vo.TableFieldInfo;
import com.lanjii.model.vo.TableInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 代码模板表Mapper 接口
 * </p>
 *
 * @author lizheng
 * @since 2023-08-28
 */
public interface CodeTemplateMapper extends BaseMapper<CodeTemplate> {

    /**
     * 获取表单注释
     *
     * @param tableName
     * @param tableSchema
     * @return
     */
    @Select("SELECT table_comment FROM INFORMATION_SCHEMA.TABLES WHERE table_name = #{tableName} AND table_schema = #{tableSchema}")
    String getTableComment(@Param("tableName") String tableName, @Param("tableSchema") String tableSchema);

    /**
     * 获取表单字段
     *
     * @param tableName
     * @param tableSchema
     * @return
     */
    @Select("SELECT table_name, column_name, data_type, column_comment, column_key FROM information_schema.columns WHERE table_schema = #{tableSchema} AND table_name = #{tableName}")
    List<TableFieldInfo> getTableFields(@Param("tableName") String tableName, @Param("tableSchema") String tableSchema);

    /**
     * 获取表单信息
     *
     * @return
     */
    @Select("SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE  table_schema = #{tableSchema}")
    List<TableInfo> getTables(@Param("tableSchema") String tableSchema);

}
