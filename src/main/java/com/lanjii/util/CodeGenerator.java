package com.lanjii.util;

import lombok.Builder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

@Slf4j
public class CodeGenerator {

    private static final List<String> baseFields = Arrays.asList("created_time", "updated_time", "created_by", "updated_by", "deleted");
    private static final String jdbcUrl = "jdbc:mysql://139.159.212.139:3306/leven_db";
    private static final String username = "leven";
    private static final String password = "yyds2024";
    private static final String tableSchema = "leven_db";
    private static GlobalConfig globalConfig;


    public static void main(String[] args) {
        CodeGenerator.globalConfig = GlobalConfig.builder()
                .packageRoot("com.lanjii")
                .moduleName("admin")
                .author("lizheng")
                .outputPackagePath("com" + File.separator + "leven" + File.separator + "ge")
                .build();
        generateCode("code_template");
    }


    public static void generateCode(String... tableNames) {
        // 初始化Velocity引擎
        Velocity.init();

        String projectPath = System.getProperty("user.dir");
        String templateFolderPath = projectPath + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "templates";

        for (String tableName : tableNames) {
            File templateFolder = new File(templateFolderPath);
            if (templateFolder.exists() && templateFolder.isDirectory()) {
                File[] files = templateFolder.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isFile()) {
                            String templateName = file.getName();
                            try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
                                VelocityContext context = createContext(tableName, connection);

                                // Generate code for the table and template
                                generateCodeForTable(context, templateName);
                            } catch (Exception e) {
                                log.error("Error generating code for table: " + tableName, e);
                            }
                        }
                    }
                }
            } else {
                log.error("Template folder does not exist.");
            }
        }
    }

    private static void generateCodeForTable(VelocityContext context, String templateName) throws Exception {
        // Load templates
        Template template = Velocity.getTemplate("src" + File.separator + "main" + File.separator + "resources" + File.separator + "templates" + File.separator + templateName);

        // Merge templates and generate code
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        String generatedCode = writer.toString();

        // Get the output path from the context
        String outputPath = (String) context.get("outputPath");

        if (outputPath == null) {
            log.info("No specified output path: {}", templateName);
            return;
        }

        // Create the parent directory if it doesn't exist
        File parentDir = new File(outputPath).getParentFile();
        if (!parentDir.exists()) {
            if (parentDir.mkdirs()) {
                log.info("Created parent directory: {}", parentDir);
            } else {
                log.error("Failed to create parent directory: {}", parentDir);
                return;
            }
        }

        // Write code to the specified output path
        File outputFile = new File(outputPath);
        try (FileWriter fileWriter = new FileWriter(outputFile)) {
            fileWriter.write(generatedCode);
        }
    }

    private static VelocityContext createContext(String tableName, Connection connection) {
        VelocityContext context = new VelocityContext();
        context.put("packageName", globalConfig.packageRoot);
        context.put("moduleName", globalConfig.moduleName);
        context.put("author", globalConfig.author);
        context.put("outputPackagePath", globalConfig.outputPackagePath);
        context.put("date", MyDateUtils.nowDate());
        context.put("upperClassName", MyStringUtils.toPascalCase(tableName));
        context.put("lowerClassName", MyStringUtils.toCamelCase(tableName));

        tableComment(connection, context, tableName);
        tableFields(connection, context, tableName);

        return context;
    }

    @SneakyThrows
    private static void tableComment(Connection connection, VelocityContext context, String tableName) {
        String sql = "SELECT table_comment FROM INFORMATION_SCHEMA.TABLES WHERE table_name = ? AND table_schema = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, tableName);
            statement.setString(2, tableSchema);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                context.put("tableComment", resultSet.getString("table_comment"));
            }
        }
    }

    @SneakyThrows
    private static void tableFields(Connection connection, VelocityContext context, String tableName) {
        String sql = "SELECT table_name, column_name, data_type, column_comment, column_key FROM information_schema.columns WHERE table_schema = ? AND table_name = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, tableSchema);
            statement.setString(2, tableName);
            ResultSet resultSet = statement.executeQuery();

            List<Map<String, String>> tableFields = new ArrayList<>();
            Map<String, String> pkField = new HashMap<>();
            while (resultSet.next()) {
                String columnName = resultSet.getString("column_name");
                if (baseFields.contains(columnName)) {
                    continue;
                }
                if ("PRI".equals(resultSet.getString("column_key"))) {
                    pkField.put("type", TypeMapper.mapToJavaType(resultSet.getString("data_type")));
                    pkField.put("name", MyStringUtils.toCamelCase(columnName));
                    pkField.put("comment", resultSet.getString("column_comment"));
                } else {
                    Map<String, String> field = new HashMap<>();
                    field.put("type", TypeMapper.mapToJavaType(resultSet.getString("data_type")));
                    field.put("name", MyStringUtils.toCamelCase(columnName));
                    field.put("comment", resultSet.getString("column_comment"));
                    tableFields.add(field);
                }
            }
            context.put("pkField", pkField);
            context.put("tableFields", tableFields);
        }
    }

    public static class TypeMapper {
        private static final Map<String, String> dbToJavaTypeMap = new HashMap<>();

        static {
            // Add database types to Java types mapping
            dbToJavaTypeMap.put("varchar", "String");
            dbToJavaTypeMap.put("int", "Integer");
            dbToJavaTypeMap.put("bigint", "Long");
            dbToJavaTypeMap.put("datetime", "Date");
        }

        public static String mapToJavaType(String dbType) {
            // Return Java type if mapping found, otherwise return a default type
            return dbToJavaTypeMap.getOrDefault(dbType, "Object");
        }
    }

    @Builder
    public static class GlobalConfig {

        /**
         * 作者
         */
        private String author;

        /**
         * 模块名称
         */
        private String moduleName;

        /**
         * 输出的包路径
         */
        private String outputPackagePath;

        /**
         * 基础包
         */
        private String packageRoot;
    }
}
