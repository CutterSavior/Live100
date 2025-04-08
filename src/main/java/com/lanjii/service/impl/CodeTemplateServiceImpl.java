package com.lanjii.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lanjii.core.base.BaseServiceImpl;
import com.lanjii.util.FileUtils;
import com.lanjii.util.MyDateUtils;
import com.lanjii.util.MyStringUtils;
import com.lanjii.dao.CodeTemplateMapper;
import com.lanjii.model.dto.CodeGenerator;
import com.lanjii.model.entity.CodeTemplate;
import com.lanjii.service.ICodeTemplateService;
import com.lanjii.model.vo.CodeTemplateVo;
import com.lanjii.model.vo.TableFieldInfo;
import com.lanjii.model.vo.TableInfo;
import lombok.RequiredArgsConstructor;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.StringWriter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 代码模板表 服务实现类
 *
 * @author lizheng
 * @date 2024-11-04
 */
@Service
@RequiredArgsConstructor
public class CodeTemplateServiceImpl extends BaseServiceImpl<CodeTemplateMapper, CodeTemplate> implements ICodeTemplateService {

    private static final List<String> baseFields = Arrays.asList("created_time", "updated_time", "created_by", "updated_by", "deleted");
    private final CodeTemplateMapper codeTemplateMapper;

    @Override
    public String generateCodeAndDownload(CodeGenerator codeGenerator) {

        Velocity.init();
        String timeMillis = String.valueOf(System.currentTimeMillis());

        List<String> filePath = new ArrayList<>();

        List<String> tableNameList = codeGenerator.getTableNameList();
        for (String tableName : tableNameList) {
            List<CodeTemplate> codeTemplateList = list();
            filePath.addAll(codeTemplateList.stream().map(codeTemplate -> {
                String templateContent = codeTemplate.getTemplateContent();
                VelocityContext context = createContext(tableName, codeGenerator, codeTemplate);
                StringWriter contentWriter = new StringWriter();
                Velocity.evaluate(context, contentWriter, "inlineTemplate", templateContent);

                StringWriter pathWriter = new StringWriter();
                String outputPath = codeTemplate.getOutputPath();
                Velocity.evaluate(context, pathWriter, "inlineTemplate", outputPath);

                return FileUtils.upload(generatorPathPre(timeMillis) + pathWriter, contentWriter.toString());
            }).collect(Collectors.toList()));

        }
        // todo 这里要是相对路径
        return FileUtils.zipFile(generatorPathPre(timeMillis) + timeMillis + ".zip", filePath);
    }

    @Override
    public List<CodeTemplateVo> generateCode(CodeGenerator codeGenerator) {

        Velocity.init();

        LambdaQueryWrapper<CodeTemplate> codeTemplateQuery = new LambdaQueryWrapper<>();
        codeTemplateQuery.in(CodeTemplate::getId, codeGenerator.getTemplateIdList());
        List<CodeTemplate> codeTemplateList = list(codeTemplateQuery);

        List<CodeTemplateVo> codeTemplateVoList = new ArrayList<>();
        List<String> tableNameList = codeGenerator.getTableNameList();
        for (String tableName : tableNameList) {

            codeTemplateVoList.addAll(codeTemplateList.stream().map(codeTemplate -> {
                String templateContent = codeTemplate.getTemplateContent();
                VelocityContext context = createContext(tableName, codeGenerator, codeTemplate);
                StringWriter contentWriter = new StringWriter();
                Velocity.evaluate(context, contentWriter, "inlineTemplate", templateContent);

                StringWriter pathWriter = new StringWriter();
                String outputPath = codeTemplate.getOutputPath();
                Velocity.evaluate(context, pathWriter, "inlineTemplate", outputPath);
                CodeTemplateVo codeTemplateVo = CodeTemplate.INSTANCE.modelToVo(codeTemplate);
                codeTemplateVo.setCodeContent(contentWriter.toString());
                codeTemplateVo.setOutputPath(generatorPathPre("111") + pathWriter);
                return codeTemplateVo;
            }).collect(Collectors.toList()));
        }
        return codeTemplateVoList;
    }

    @Override
    public List<TableInfo> getTables(String tableSchema) {
        return codeTemplateMapper.getTables(tableSchema);
    }

    private VelocityContext createContext(String tableName, CodeGenerator codeGenerator, CodeTemplate codeTemplate) {
        VelocityContext context = new VelocityContext();
        context.put("packageName", "com.lanjii");
        context.put("moduleName", codeTemplate.getModuleName());
        context.put("author", codeGenerator.getAuthor());
        context.put("date", MyDateUtils.nowDate());
        context.put("upperClassName", MyStringUtils.toPascalCase(tableName));
        context.put("lowerClassName", MyStringUtils.toCamelCase(tableName));

        context.put("tableComment", codeTemplateMapper.getTableComment(tableName, codeGenerator.getTableSchema()));
        List<TableFieldInfo> tableFieldInfoList = codeTemplateMapper.getTableFields(tableName, codeGenerator.getTableSchema());
        List<Map<String, String>> tableFields = new ArrayList<>();
        Map<String, String> pkField = new HashMap<>();

        for (TableFieldInfo tableFieldInfo : tableFieldInfoList) {
            String columnName = tableFieldInfo.getColumnName();
            if (baseFields.contains(columnName)) {
                continue;
            }
            if ("PRI".equals(tableFieldInfo.getColumnKey())) {
                pkField.put("type", TypeMapper.mapToJavaType(tableFieldInfo.getDataType()));
                pkField.put("name", MyStringUtils.toCamelCase(columnName));
                String pkComment = tableFieldInfo.getColumnComment();
                pkField.put("comment", StringUtils.isEmpty(pkComment) ? "主键" : pkComment);
            } else {
                Map<String, String> field = new HashMap<>();
                field.put("type", TypeMapper.mapToJavaType(tableFieldInfo.getDataType()));
                field.put("name", MyStringUtils.toCamelCase(columnName));
                field.put("comment", tableFieldInfo.getColumnComment());
                tableFields.add(field);
            }
        }
        context.put("pkField", pkField);
        context.put("tableFields", tableFields);

        return context;
    }

    private String generatorPathPre(String dir) {
        return "generator" + File.separator + dir + File.separator;
    }

    public static class TypeMapper {
        private static final Map<String, String> dbToJavaTypeMap = new HashMap<>();

        static {
            dbToJavaTypeMap.put("varchar", "String");
            dbToJavaTypeMap.put("int", "Integer");
            dbToJavaTypeMap.put("tinyint", "Integer");
            dbToJavaTypeMap.put("bigint", "Long");
            dbToJavaTypeMap.put("datetime", "Date");
            dbToJavaTypeMap.put("text", "String");
        }

        public static String mapToJavaType(String dbType) {
            return dbToJavaTypeMap.getOrDefault(dbType, "Object");
        }
    }
}
