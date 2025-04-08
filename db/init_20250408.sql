/*
 Navicat Premium Dump SQL

 Source Server         : Docker数据库
 Source Server Type    : MySQL
 Source Server Version : 80036 (8.0.36)
 Source Host           : 127.0.0.1:3306
 Source Schema         : leven_db

 Target Server Type    : MySQL
 Target Server Version : 80036 (8.0.36)
 File Encoding         : 65001

 Date: 08/04/2025 11:04:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '内容',
  `created_time` datetime NULL DEFAULT NULL,
  `created_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `updated_time` datetime NULL DEFAULT NULL,
  `updated_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `deleted` int NULL DEFAULT NULL,
  `category` int NULL DEFAULT NULL COMMENT '分类',
  `tag` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标签',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '题库表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES (1, '近观“两山” | 树木，也是树人', '<p style=\"text-align: justify;\">每逢春日，习近平总书记都会与首都各界共植新绿，植树现场几乎都有孩子们的身影。一年又一年的春风里，树木，也是树人。</p><p style=\"text-align: justify;\">种树时，不刻意追求奇花异草、名贵树木，种什么根据规划和地块实际，因地制宜；小学生就近组织几个班参加，一个孩子都不落下。习近平总书记说，种下的既是绿色树苗，也是祖国的美好未来。</p><p style=\"text-align: justify;\">挥锄挖土、扶树正苗、培土围堰、提桶浇水……从浙江安吉的竹林到北京大兴的油松，从“绿水青山”的生态觉醒到“美丽中国”的现代图景，习近平总书记身体力行，为美丽中国擘画出一幅恢弘壮阔的生态画卷。</p><p style=\"text-align: justify;\">党的十八大以来，我国森林覆盖率提升至25%以上；人工林保存面积超过13亿亩，稳居全球首位。本世纪以来，我国为全球贡献了约四分之一的新增绿化面积。一圈圈年轮里，镌刻着塞罕坝三代人的坚守，见证着三北工程数百万亩林海的崛起，书写着毛乌素沙漠变绿洲的奇迹，更孕育着一代代少年播种的绿色梦想。</p>', '2025-04-07 14:29:04', 'admin', '2025-04-07 14:29:04', 'admin', 0, 1, NULL);

-- ----------------------------
-- Table structure for article_category
-- ----------------------------
DROP TABLE IF EXISTS `article_category`;
CREATE TABLE `article_category`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `category` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类别',
  `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `created_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `updated_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `updated_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `deleted` int(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文章类别表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of article_category
-- ----------------------------
INSERT INTO `article_category` VALUES (1, '新闻', '2025-04-07 14:28:37', 'admin', '2025-04-07 14:28:37', 'admin', 0);

-- ----------------------------
-- Table structure for article_tag_rel
-- ----------------------------
DROP TABLE IF EXISTS `article_tag_rel`;
CREATE TABLE `article_tag_rel`  (
  `article_id` bigint NOT NULL COMMENT '文章ID',
  `tag_id` bigint NOT NULL COMMENT '标签ID'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文章标签关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of article_tag_rel
-- ----------------------------

-- ----------------------------
-- Table structure for code_template
-- ----------------------------
DROP TABLE IF EXISTS `code_template`;
CREATE TABLE `code_template`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `template_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '模板名称',
  `template_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '模板内容',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '模板描述',
  `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `created_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `updated_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `updated_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `deleted` int(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '逻辑删除',
  `output_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件输出路径',
  `module_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '模块名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '代码模板表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of code_template
-- ----------------------------
INSERT INTO `code_template` VALUES (1, 'Entity', 'package ${packageName}.entity;\n\nimport com.baomidou.mybatisplus.annotation.IdType;\nimport com.baomidou.mybatisplus.annotation.TableId;\nimport lombok.Data;\nimport lombok.EqualsAndHashCode;\nimport lombok.experimental.Accessors;\nimport org.mapstruct.Mapper;\n\n/**\n *  ${tableComment}\n *\n * @author ${author}\n * @date ${date}\n */\n@Data\n@EqualsAndHashCode(callSuper = true)\n@Accessors(chain = true)\npublic class $upperClassName extends BaseModel{\n\nprivate static final long serialVersionUID=1L;\n\n/**\n * $pkField.comment\n */\n@TableId(value = \"id\", type = IdType.AUTO)\nprivate $pkField.type ${pkField.name};\n\n#foreach ($field in $tableFields)\n/**\n * $field.comment\n */\nprivate $field.type $field.name;\n\n#end\n\n@Mapper\npublic interface ${upperClassName}ModelMapper extends BaseModelMapper< ${upperClassName}Vo, ${upperClassName}> {\n    @Override\n        ${upperClassName}Vo modelToVo(${upperClassName} model);\n\n}\n\n    public static final ${upperClassName}ModelMapper INSTANCE = Mappers.getMapper(${upperClassName}ModelMapper.class);\n\n}\n', '实体类模板', '2024-11-04 10:20:05', 'admin', '2024-11-04 14:03:25', 'admin', 0, 'src/main/java/com/leven/entity/${upperClassName}.java', 'entity');
INSERT INTO `code_template` VALUES (2, 'Dao', 'package ${packageName}.dao;\n\nimport com.baomidou.mybatisplus.core.mapper.BaseMapper;\n\n/**\n * <p>\n *  ${tableComment}Mapper 接口\n * </p>\n *\n * @author lizheng\n * @since 2023-08-28\n */\npublic interface ${upperClassName}Mapper extends BaseMapper< ${upperClassName}> {\n\n}\n', '数据库操作', '2024-11-04 11:10:58', 'admin', '2024-11-04 14:13:34', 'admin', 0, 'src/main/java/com/leven/dao/${upperClassName}Mapper.java', 'dao');
INSERT INTO `code_template` VALUES (3, 'IService', 'package ${packageName}.service;\n\n/**\n *  ${tableComment} 服务类\n *\n * @author ${author}\n * @date ${date}\n */\npublic interface I${upperClassName}Service extends IBaseService<${upperClassName}> {\n\n}\n', 'Service接口', '2024-11-04 11:11:30', 'admin', '2024-11-04 14:03:16', 'admin', 0, 'src/main/java/com/leven/service/I${upperClassName}Service.java', 'service');
INSERT INTO `code_template` VALUES (4, 'ServiceImpl', 'package ${packageName}.service.impl;\n\nimport org.mapstruct.Mapper;\n\n/**\n *  ${tableComment} 服务实现类\n *\n * @author ${author}\n * @date ${date}\n */\n@Service\n@RequiredArgsConstructor\npublic class ${upperClassName}ServiceImpl extends BaseServiceImpl<${upperClassName}Mapper, ${upperClassName}> implements I${upperClassName}Service {\n\n}\n', 'Service 实现类', '2024-11-04 11:11:53', 'admin', '2024-11-04 14:13:55', 'admin', 0, 'src/main/java/com/leven/service/imp/${upperClassName}ServiceImpl.java', 'service.impl');
INSERT INTO `code_template` VALUES (5, 'Controller', 'package ${packageName}.controller;\n\n/**\n *  ${tableComment}\n *\n * @author ${author}\n * @date ${date}\n */\n@Slf4j\n@RestController\n@RequestMapping(\"/${lowerClassName}\")\n@RequiredArgsConstructor\npublic class ${upperClassName}Controller {\n\n    private final I${upperClassName}Service ${lowerClassName}Service;\n\n    /**\n * 列表\n */\n    @PostMapping(\"/list\")\n    public R<PageData<${upperClassName}Vo>> list(@MultiRequestBody(required = false) PageParam pageParam, @MultiRequestBody(required = false) OrderParam orderParam,\n                                                 @MultiRequestBody(required = false) ${upperClassName}Dto ${lowerClassName}Filter) {\n        PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());\n        List<${upperClassName}> list = ${lowerClassName}Service.getListByFilter(${lowerClassName}Filter, orderParam);\n        return R.success(PageDataUtils.make(list, ${upperClassName}.INSTANCE));\n    }\n\n\n    /**\n    * 详情\n    */\n    @GetMapping(\"/query\")\n    public R<${upperClassName}> query(Long id) {\n        return R.success(${lowerClassName}Service.getById(id));\n    }\n\n    /**\n     * 保存\n     */\n    @PostMapping(\"/save\")\n    public R<${upperClassName}> save(@MultiRequestBody ${upperClassName}Dto ${lowerClassName}Dto) {\n        ${upperClassName} ${lowerClassName} =ModelUtils.copyTo(${lowerClassName}Dto, ${upperClassName}. class);\n            ${lowerClassName}Service.save(${lowerClassName});\n        return R.success();\n    }\n\n    /**\n     * 更新\n     */\n    @PostMapping(\"/update\")\n    public R<${upperClassName}> update(@MultiRequestBody ${upperClassName}Dto ${lowerClassName}Dto) {\n        ${upperClassName} originalData = ${lowerClassName}Service.getById(${lowerClassName}Dto.getId());\n        if (originalData == null) {\n            return R.fail(ResultCode.DATA_NOT_EXIST);\n        }\n        ${upperClassName} ${lowerClassName} =ModelUtils.copyTo(${lowerClassName}Dto, ${upperClassName}. class);\n            ${lowerClassName}Service.updateById(${lowerClassName});\n        return R.success();\n    }\n\n    /**\n     * 删除\n     */\n    @GetMapping(\"/del\")\n    public R<${upperClassName}> delete(Long id) {\n            ${lowerClassName}Service.removeById(id);\n        return R.success();\n    }\n\n}\n', '接口层', '2024-11-04 11:12:11', 'admin', '2025-04-06 12:36:34', 'admin', 0, 'src/main/java/com/leven/controller/${upperClassName}Controller.java', 'controller');
INSERT INTO `code_template` VALUES (6, 'VO', 'package ${packageName}.vo;\n\nimport lombok.Data;\n\nimport java.io.Serializable;\n\n/**\n *  ${tableComment}Vo\n *\n * @author ${author}\n * @date ${date}\n */\n@Data\npublic class ${upperClassName}Vo implements Serializable {\n\n    private static final long serialVersionUID = 1L;\n\n/**\n * $pkField.comment\n */\n    private $pkField.type ${pkField.name};\n\n    #foreach ($field in $tableFields)\n        /**\n         * $field.comment\n         */\n        private $field.type $field.name;\n\n    #end\n      \n    /**\n     * 创建时间\n     */\n       private Date createdTime;\n  \n      /**\n     * 创建人\n     */\n  	private String createBy;\n}\n', NULL, '2024-11-04 11:12:33', 'admin', '2025-04-06 12:35:35', 'admin', 0, 'src/main/java/com/leven/vo/${upperClassName}Vo.java', NULL);
INSERT INTO `code_template` VALUES (7, 'DTO', 'package ${packageName}.dto;\n\nimport lombok.Data;\n\nimport java.io.Serializable;\n\n/**\n *  ${tableComment}Dto\n *\n * @author ${author}\n * @date ${date}\n */\n@Data\npublic class ${upperClassName}Dto implements Serializable {\n\n    private static final long serialVersionUID = 1L;\n\n/**\n * $pkField.comment\n */\n    private $pkField.type ${pkField.name};\n\n    #foreach ($field in $tableFields)\n        /**\n         * $field.comment\n         */\n        private $field.type $field.name;\n\n    #end\n\n}\n', NULL, '2024-11-04 11:12:44', 'admin', '2024-11-04 14:14:16', 'admin', 0, 'src/main/java/com/leven/dto/${upperClassName}Dto.java', 'dto');
INSERT INTO `code_template` VALUES (8, 'Table', '#set ($dialogCode=\"this.$dialog\")\n<template>\n    <div class=\"table-box\">\n        <search-box :formItemList=\"formItemList\" @search=\"search\"/>\n        <function-box>\n            <template>\n                <el-button type=\"primary\" @click=\"open${upperClassName}Dialog(\'add\')\">添加</el-button>\n            </template>\n        </function-box>\n        <el-row>\n            <el-col :lg=\"24\">\n                <el-table :data=\"${lowerClassName}.impl.dataList\" class=\"table-box-content\" border>\n                    <el-table-column label=\"序号\" header-align=\"center\" align=\"center\" type=\"index\" width=\"80px\"\n                                     :index=\"${lowerClassName}.impl.getTableIndex\"/>\n                    #foreach ($field in $tableFields)\n                        <el-table-column prop=\"$field.name\" label=\"$field.comment\" align=\"center\"></el-table-column>\n                    #end\n                    <el-table-column label=\"操作\" fixed=\"right\" width=\"100px\" align=\"center\">\n                        <template slot-scope=\"scope\" class=\"\">\n                            <el-link class=\"icon-btn el-icon-view\" @click=\"open${upperClassName}Dialog(\'view\',scope.row)\"></el-link>\n                            <el-link class=\"icon-btn el-icon-edit-outline\" @click=\"open${upperClassName}Dialog(\'edit\',scope.row)\"></el-link>\n                            <el-link class=\"icon-btn el-icon-delete\" @click=\"confirmDel(scope.row.id)\"></el-link>\n                        </template>\n                    </el-table-column>\n                </el-table>\n            </el-col>\n        </el-row>\n        <el-row type=\"flex\" justify=\"end\" style=\"background: white;padding: 10px 0;\">\n            <el-pagination\n                    :total=\"${lowerClassName}.impl.totalCount\"\n                    :current-page=\"${lowerClassName}.impl.currentPage\"\n                    :page-size=\"${lowerClassName}.impl.pageSize\"\n                    :page-sizes=\"[10, 20, 50, 100]\"\n                    @current-change=\"${lowerClassName}.impl.onCurrentPageChange\"\n                    @size-change=\"${lowerClassName}.impl.onPageSizeChange\"\n                    background\n                    layout=\"total, prev, pager, next, sizes\">\n            </el-pagination>\n        </el-row>\n\n    </div>\n</template>\n<script>\n    import SearchBox from \'@/components/SearchBox.vue\'\n    import FunctionBox from \'@/components/FunctionBox.vue\'\n    import { TableWidget } from \'@/utils/widget\'\n\n    export default {\n        components: {\n            SearchBox,\n            FunctionBox,\n            ${upperClassName}FormDialog\n        },\n        data () {\n            return {\n                formItemList: [\n                    {\n                        label: \'姓名\',\n                        type: \'input\',\n                        param: \'userName\'\n                    }],\n                filter: {},\n                ${lowerClassName}: {\n                    impl: new TableWidget(this.load${upperClassName}Data, this.load${upperClassName}Verify, true, false)\n                },\n            }\n        },\n        methods: {\n            search (params) {\n                this.filter = params\n                this.${lowerClassName}.impl.refreshTable(true, 1)\n            },\n            open${upperClassName}Dialog (method, row) {\n                let params = {\n                    id: row ? row.id : undefined,\n                    method: method\n                }\n                ${dialogCode}.show(\'添加\', ${upperClassName}FormDialog, {\n                area: [\'40%\', \'410px\']\n                }, params).then(res => {\n                this.${lowerClassName}.impl.refreshTable()\n                }).catch(e => {\n                })\n            },\n            confirmDel (id) {\n                let params = { id: id }\n                this.$confirm(\'确定要删除该数据吗?\', \'提示\', {\n                    confirmButtonText: \'确定\',\n                    cancelButtonText: \'取消\',\n                    type: \'warning\'\n                }).then(res => {\n                    ${lowerClassName}Api.remove(params).then(res => {\n                        this.$message.success(\'删除成功\')\n                        this.${lowerClassName}.impl.refreshTable()\n                    }).catch(e => {\n                    })\n                }).catch(e => {\n                })\n            },\n            load${upperClassName}Data (params) {\n                return new Promise((resolve, reject) => {\n                    let searchParams = {\n                        ${lowerClassName}Filter: { ...this.filter },\n                        ...params\n                    }\n                    ${lowerClassName}Api.list(searchParams).then(data => {\n                        resolve({\n                            dataList: data.list,\n                            totalCount: data.total\n                        })\n                    }).catch(e => {\n                        reject(e)\n                    })\n                })\n            },\n            load${upperClassName}Verify () {\n                return true\n            }\n        },\n        mounted () {\n            this.${lowerClassName}.impl.refreshTable()\n        }\n    }\n</script>\n<style lang=\"less\" scoped>\n\n</style>\n', 'Vue 表格页面', '2024-11-04 11:13:15', 'admin', '2024-11-04 14:13:26', 'admin', 0, 'vue/${lowerClassName}/index.vue', NULL);
INSERT INTO `code_template` VALUES (9, 'FormDialog', '#set ($refs=\"this.$refs\")\n#set ($message=\"this.$message\")\n<template>\n    <el-form ref=\"${lowerClassName}Form\" :rules=\"${lowerClassName}Rules\" :model=\"${lowerClassName}\" label-width=\"80px\">\n        <el-row :gutter=\"20\">\n            #foreach ($field in $tableFields)\n                <el-col :span=\"24\">\n                    <el-form-item label=\"$field.comment\" prop=\"$field.name\">\n                        <el-input v-model=\"${lowerClassName}.$field.name\" :disabled=\"method===\'view\'\"></el-input>\n                    </el-form-item>\n                </el-col>\n            #end\n            <el-col :span=\"24\">\n                <el-row type=\"flex\" justify=\"end\">\n                    <el-button type=\"primary\" size=\"mini\" :plain=\"true\"\n                               @click=\"onCancel(false)\">\n                        取消\n                    </el-button>\n                    <template v-if=\"method===\'add\'\">\n                        <el-button type=\"primary\" size=\"mini\" @click=\"onSaveOrUpdate\">\n                            保存\n                        </el-button>\n                    </template>\n                    <template v-if=\"method===\'edit\'\">\n                        <el-button type=\"primary\" size=\"mini\" @click=\"onSaveOrUpdate\">\n                            编辑\n                        </el-button>\n                    </template>\n                </el-row>\n            </el-col>\n        </el-row>\n    </el-form>\n</template>\n<script>\n    import * as ${lowerClassName}Api from \'@/api/sys/${lowerClassName}Api\'\n\n    export default {\n        props: {\n            method: {\n                type: String\n            },\n            id: {\n                type: Number\n            }\n        },\n        data() {\n            return {\n                    ${lowerClassName}: {\n                    #foreach ($field in $tableFields)\n                            ${field.name}: undefined,\n                    #end\n                },\n                    ${lowerClassName}Rules: {\n                }\n            }\n        },\n        methods: {\n            onCancel(isSuccess) {\n                if (this.observer != null) {\n                    this.observer.cancel(isSuccess)\n                }\n            },\n            onSaveOrUpdate() {\n                let params = {${lowerClassName}Dto: this.${lowerClassName}}\n                    ${refs}[\'${lowerClassName}Form\'].validate((valid) => {\n                    if (valid) {\n                        let promise = this.method === \'add\' ? ${lowerClassName}Api.save(params) : ${lowerClassName}Api.update(params)\n                        promise.then(res => {\n                           ${message}.success(this.method === \'add\' ? \'保存成功\' : \'编辑成功\')\n                                this.onCancel(true)\n                        })\n                    }\n                })\n            },\n            initForm() {\n                if (this.id) {\n                    let params = {id: this.id}\n                        ${lowerClassName}Api.query(params).then(res => {\n                        this.${lowerClassName} = res.data\n                    })\n                }\n            }\n        },\n        mounted() {\n            this.initForm()\n        }\n    }\n</script>\n<style scoped lang=\"less\">\n\n</style>', '表单弹窗，新增/修改数据', '2024-11-04 11:21:41', 'admin', '2024-11-04 14:03:35', 'admin', 0, 'vue/${lowerClassName}/${upperClassName}FormDialog.vue', NULL);
INSERT INTO `code_template` VALUES (10, NULL, '111', NULL, '2024-11-07 08:26:39', 'admin', '2024-11-07 08:26:39', 'admin', 1, NULL, NULL);

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '通知内容',
  `to_type` tinyint NULL DEFAULT NULL COMMENT '通知范围（0-所有人 1-指定人员）',
  `is_read` int NULL DEFAULT NULL COMMENT '是否已读',
  `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `created_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `updated_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `updated_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `deleted` int(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '通知' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (1, '通知', '这是一个通知！', 0, 0, '2025-04-08 09:48:57', 'admin', '2025-04-08 09:48:57', 'admin', 0);

-- ----------------------------
-- Table structure for notice_user_rel
-- ----------------------------
DROP TABLE IF EXISTS `notice_user_rel`;
CREATE TABLE `notice_user_rel`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `notice_id` bigint NULL DEFAULT NULL COMMENT '通知ID',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `is_read` tinyint(1) NULL DEFAULT NULL COMMENT '是否已读（0-未读 1-已读）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文章和人员关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of notice_user_rel
-- ----------------------------
INSERT INTO `notice_user_rel` VALUES (1, 1, 1, 0);
INSERT INTO `notice_user_rel` VALUES (2, 1, 2, 0);

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `config_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '配置项名称',
  `config_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '配置项值',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `created_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `updated_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `updated_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `deleted` int(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `configName`(`config_name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (20, 'FILE_URL', 'http://127.0.0.1:8081/', '资源文件访问地址', NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_config` VALUES (21, '你好', '哈哈哈哈', '11', '2025-03-28 08:07:56', 'admin', '2025-03-28 08:07:56', 'admin', 1);
INSERT INTO `sys_config` VALUES (22, 'DEFAULT_PASSWORD', '123456', '系统用户默认密码', '2025-03-28 19:35:38', 'admin', '2025-03-28 19:49:29', 'admin', 0);

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `parent_id` bigint NOT NULL COMMENT '父级部门 ID',
  `dept_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '部门名称',
  `status` tinyint(1) NOT NULL COMMENT '状态',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `created_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `updated_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `updated_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `deleted` int(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '部门表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1, 0, '武汉总公司', 1, 1, '2024-10-29 10:25:10', 'admin', '2024-10-29 10:25:10', 'admin', 0);
INSERT INTO `sys_dept` VALUES (7, 1, '财务部', 1, 1, '2024-10-29 15:20:40', 'admin', '2024-10-29 15:20:40', 'admin', 0);
INSERT INTO `sys_dept` VALUES (8, 1, '研发部', 1, 1, '2024-10-29 15:20:48', 'admin', '2025-04-07 15:11:36', 'admin', 0);
INSERT INTO `sys_dept` VALUES (9, 1, '测试部', 1, 1, '2024-10-29 15:21:04', 'admin', '2024-10-29 15:21:04', 'admin', 0);
INSERT INTO `sys_dept` VALUES (10, 1, '销售部', 1, 1, '2024-10-29 15:21:13', 'admin', '2024-10-29 15:21:13', 'admin', 0);
INSERT INTO `sys_dept` VALUES (11, 8, 'UI设计部', 1, 1, '2024-10-29 15:37:40', 'admin', '2024-10-29 15:37:40', 'admin', 0);

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dict_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典编码',
  `dict_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典名称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典描述',
  `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `created_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `updated_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `updated_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `deleted` int(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (1, 'article_category', '题库分类', NULL, NULL, NULL, '2025-03-29 09:54:53', 'admin', 1);
INSERT INTO `sys_dict` VALUES (2, 'sex', '性别', NULL, '2024-10-11 11:56:57', 'admin', '2024-10-11 11:56:57', 'admin', 0);
INSERT INTO `sys_dict` VALUES (3, 'status', '状态', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_dict` VALUES (4, 'SOS3', '测试', NULL, '2024-10-30 11:21:23', 'admin', '2024-10-30 11:40:59', 'admin', 1);
INSERT INTO `sys_dict` VALUES (6, 'SOS3', '11', NULL, '2024-10-30 11:47:45', 'admin', '2024-10-30 11:47:45', 'admin', 1);
INSERT INTO `sys_dict` VALUES (7, 'NOTICE_TO_TYPE', '通知指定类型', NULL, '2025-04-04 06:14:42', 'admin', '2025-04-04 06:14:42', 'admin', 0);

-- ----------------------------
-- Table structure for sys_dict_detail
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_detail`;
CREATE TABLE `sys_dict_detail`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dict_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典编码',
  `dict_label` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典标签（页面显示名称）',
  `dict_value` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典值',
  `dict_sort` int NULL DEFAULT NULL COMMENT '排序',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `created_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `updated_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `updated_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `deleted` int(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典明细表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_detail
-- ----------------------------
INSERT INTO `sys_dict_detail` VALUES (4, 'status', '启用', '1', 1, NULL, '2024-10-30 11:35:36', 'admin', '2024-10-30 11:35:36', 'admin', 0);
INSERT INTO `sys_dict_detail` VALUES (5, 'status', '禁用', '0', 2, NULL, '2024-10-30 11:35:36', 'admin', '2024-10-30 11:35:36', 'admin', 0);
INSERT INTO `sys_dict_detail` VALUES (7, 'sex', '女', '0', 1, NULL, '2024-10-30 11:35:36', 'admin', '2024-10-30 11:35:36', 'admin', 0);
INSERT INTO `sys_dict_detail` VALUES (8, 'sex', '男', '1', 2, NULL, '2024-10-30 11:35:36', 'admin', '2024-10-30 11:35:36', 'admin', 0);
INSERT INTO `sys_dict_detail` VALUES (9, 'sex', '其他', '2', 3, '', '2024-10-30 11:35:36', 'admin', '2024-10-30 11:35:36', 'admin', 0);
INSERT INTO `sys_dict_detail` VALUES (14, 'NOTICE_TO_TYPE', '全部成员', '0', 1, NULL, '2025-04-04 06:50:57', 'admin', '2025-04-04 06:50:57', 'admin', 0);
INSERT INTO `sys_dict_detail` VALUES (15, 'NOTICE_TO_TYPE', '指定成员', '1', 2, NULL, '2025-04-04 06:51:06', 'admin', '2025-04-04 06:51:13', 'admin', 0);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单名',
  `route_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路由名称',
  `route_path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路由路径',
  `ext_link` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '外部链接',
  `parent_id` bigint NULL DEFAULT NULL COMMENT '父菜单',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '状态',
  `type` int NULL DEFAULT NULL COMMENT '菜单类型 （1 - 菜单 、2 - 按钮、3 - 外链）',
  `btn_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '按钮编码',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `created_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `updated_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `updated_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `deleted` int(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 186 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', '', NULL, NULL, 0, 'el-icon-s-tools', 1, 1, NULL, 2, '2023-08-28 23:07:58', 'admin', '2024-10-08 16:37:30', 'admin222', 0);
INSERT INTO `sys_menu` VALUES (2, '用户管理', 'admin/sys/user/index', 'admin/sys/user/index', NULL, 1, 'el-icon-s-custom', 1, 1, NULL, 1, '2023-08-28 23:07:58', 'admin', '2025-04-08 10:48:47', 'admin', 0);
INSERT INTO `sys_menu` VALUES (3, '角色管理', 'admin/sys/role/index', 'admin/sys/role/index', NULL, 1, 'fa fa-users', 1, 1, NULL, 3, '2023-08-28 23:07:58', 'admin', '2025-04-06 17:56:11', 'admin', 0);
INSERT INTO `sys_menu` VALUES (4, '菜单管理', 'admin/sys/menu/index', 'admin/sys/menu/index', NULL, 1, 'el-icon-menu', 1, 1, NULL, 4, '2023-08-28 23:07:58', 'admin', '2024-11-01 13:58:55', 'admin', 0);
INSERT INTO `sys_menu` VALUES (6, '首页', 'admin/index', 'admin/home/index', NULL, 0, 'el-icon-s-home', 1, 1, NULL, 1, '2023-09-27 10:02:13', 'admin', '2025-04-07 16:49:50', 'admin', 0);
INSERT INTO `sys_menu` VALUES (12, '添加', NULL, NULL, NULL, 2, NULL, 1, 2, NULL, 1, '2023-09-28 17:03:45', 'admin', '2025-04-06 18:10:07', 'admin', 0);
INSERT INTO `sys_menu` VALUES (13, '编辑', NULL, NULL, NULL, 2, NULL, 1, 2, NULL, 3, '2023-09-28 17:03:52', 'admin', '2025-04-06 18:10:15', 'admin', 0);
INSERT INTO `sys_menu` VALUES (14, '删除', NULL, NULL, NULL, 2, NULL, 1, 2, NULL, 2, '2023-09-28 17:04:03', 'admin', '2025-04-06 18:10:10', 'admin', 0);
INSERT INTO `sys_menu` VALUES (16, '添加', NULL, NULL, NULL, 4, NULL, 1, 2, NULL, 1, '2024-03-12 10:35:54', 'admin', '2025-04-06 18:26:34', 'test', 0);
INSERT INTO `sys_menu` VALUES (17, '删除', NULL, NULL, NULL, 4, NULL, 1, 2, NULL, 2, '2024-03-12 10:36:13', 'admin', '2025-04-06 18:26:40', 'test', 0);
INSERT INTO `sys_menu` VALUES (18, '编辑', NULL, NULL, NULL, 4, NULL, 1, 2, NULL, 3, '2024-03-12 10:36:43', 'admin', '2025-04-06 18:26:45', 'test', 0);
INSERT INTO `sys_menu` VALUES (112, '部门管理', 'admin/sys/dept/index', 'admin/sys/dept/index', NULL, 1, 'fa fa-sitemap', 1, 1, NULL, 2, '2024-10-29 10:12:40', 'admin', '2025-04-08 10:48:57', 'admin', 0);
INSERT INTO `sys_menu` VALUES (113, '添加', NULL, NULL, NULL, 112, NULL, 1, 2, NULL, 0, '2024-10-29 10:14:52', 'admin', '2025-04-06 17:31:26', 'admin', 0);
INSERT INTO `sys_menu` VALUES (114, '字典管理', 'admin/sys/dict/index', 'admin/sys/dict/index', NULL, 1, 'fa fa-clipboard', 1, 1, NULL, 6, '2024-10-29 16:25:13', 'admin', '2025-04-08 10:49:39', 'admin', 0);
INSERT INTO `sys_menu` VALUES (118, '编辑', NULL, NULL, NULL, 112, NULL, 1, 2, NULL, 2, '2024-11-01 12:30:46', 'admin', '2024-11-01 12:30:46', 'admin', 0);
INSERT INTO `sys_menu` VALUES (119, '删除', NULL, NULL, NULL, 112, NULL, 1, 2, NULL, 3, '2024-11-01 12:31:18', 'admin', '2024-11-01 12:31:28', 'admin', 0);
INSERT INTO `sys_menu` VALUES (120, '查看', NULL, NULL, NULL, 112, NULL, 1, 2, NULL, 0, '2024-11-01 12:32:09', 'admin', '2025-04-06 17:21:00', 'admin', 0);
INSERT INTO `sys_menu` VALUES (121, '资源管理', 'admin/sys/resource/index', 'admin/sys/resource/index', NULL, 1, 'fa fa-list-ul', 1, 1, NULL, 5, '2024-11-01 13:12:07', 'admin', '2025-04-08 10:49:25', 'admin', 0);
INSERT INTO `sys_menu` VALUES (122, '添加', NULL, NULL, NULL, 3, NULL, 1, 2, NULL, 1, '2024-11-01 13:47:31', 'admin', '2025-04-06 18:27:16', 'test', 0);
INSERT INTO `sys_menu` VALUES (123, '添加', NULL, NULL, NULL, 160, NULL, 1, 2, NULL, 1, '2024-11-01 13:53:25', 'admin', '2025-04-07 08:44:29', 'admin', 0);
INSERT INTO `sys_menu` VALUES (124, '编辑', NULL, NULL, NULL, 160, NULL, 1, 2, NULL, 3, '2024-11-01 13:54:00', 'admin', '2025-04-07 08:44:15', 'admin', 0);
INSERT INTO `sys_menu` VALUES (125, '删除', NULL, NULL, NULL, 160, NULL, 1, 2, NULL, 2, '2024-11-01 13:55:16', 'admin', '2025-04-07 08:45:17', 'admin', 0);
INSERT INTO `sys_menu` VALUES (126, '查看', NULL, NULL, NULL, 160, NULL, 1, 2, NULL, 4, '2024-11-01 13:55:58', 'admin', '2025-04-07 08:45:29', 'admin', 0);
INSERT INTO `sys_menu` VALUES (127, '查看', NULL, NULL, NULL, 4, NULL, 1, 2, NULL, 4, '2024-11-01 13:59:46', 'admin', '2025-04-06 18:26:49', 'test', 0);
INSERT INTO `sys_menu` VALUES (128, '删除', NULL, NULL, NULL, 3, NULL, 1, 2, NULL, 2, '2024-11-01 14:00:28', 'admin', '2025-04-06 18:27:14', 'test', 0);
INSERT INTO `sys_menu` VALUES (129, '编辑', NULL, NULL, NULL, 3, NULL, 1, 2, NULL, 3, '2024-11-01 14:00:47', 'admin', '2025-04-06 18:27:06', 'test', 0);
INSERT INTO `sys_menu` VALUES (130, '查看', NULL, NULL, NULL, 3, NULL, 1, 2, NULL, 4, '2024-11-01 14:01:37', 'admin', '2025-04-06 18:27:10', 'test', 0);
INSERT INTO `sys_menu` VALUES (131, '添加', NULL, NULL, NULL, 121, NULL, 1, 2, NULL, NULL, '2024-11-01 14:02:16', 'admin', '2024-11-01 14:02:16', 'admin', 0);
INSERT INTO `sys_menu` VALUES (132, '删除', NULL, NULL, NULL, 121, NULL, 1, 2, NULL, NULL, '2024-11-01 14:02:37', 'admin', '2024-11-01 14:02:37', 'admin', 0);
INSERT INTO `sys_menu` VALUES (133, '编辑', NULL, NULL, NULL, 121, NULL, 1, 2, NULL, NULL, '2024-11-01 14:02:48', 'admin', '2024-11-01 14:02:48', 'admin', 0);
INSERT INTO `sys_menu` VALUES (134, '查询', NULL, NULL, NULL, 121, NULL, 1, 2, NULL, NULL, '2024-11-01 14:02:59', 'admin', '2024-11-01 14:02:59', 'admin', 0);
INSERT INTO `sys_menu` VALUES (135, '添加', NULL, NULL, NULL, 114, NULL, 1, 2, NULL, 1, '2024-11-01 14:05:27', 'admin', '2025-04-07 08:13:23', 'admin', 0);
INSERT INTO `sys_menu` VALUES (136, '明细添加', NULL, NULL, NULL, 114, NULL, 1, 2, NULL, 11, '2024-11-01 14:05:47', 'admin', '2025-04-07 08:14:57', 'admin', 0);
INSERT INTO `sys_menu` VALUES (137, '删除', NULL, NULL, NULL, 114, NULL, 1, 2, NULL, 2, '2024-11-01 14:06:18', 'admin', '2025-04-07 08:13:28', 'admin', 0);
INSERT INTO `sys_menu` VALUES (138, '明细删除', NULL, NULL, NULL, 114, NULL, 1, 2, NULL, 12, '2024-11-01 14:06:32', 'admin', '2025-04-07 08:14:45', 'admin', 0);
INSERT INTO `sys_menu` VALUES (139, '编辑', NULL, NULL, NULL, 114, NULL, 1, 2, NULL, 3, '2024-11-01 14:06:47', 'admin', '2025-04-07 08:14:20', 'admin', 0);
INSERT INTO `sys_menu` VALUES (140, '明细编辑', NULL, NULL, NULL, 114, NULL, 1, 2, NULL, 13, '2024-11-01 14:07:28', 'admin', '2025-04-07 08:14:37', 'admin', 0);
INSERT INTO `sys_menu` VALUES (141, '查询明细详情', NULL, NULL, NULL, 114, NULL, 1, 2, NULL, 14, '2024-11-01 14:08:40', 'admin', '2025-04-07 08:15:12', 'admin', 0);
INSERT INTO `sys_menu` VALUES (142, '系统监控', NULL, NULL, NULL, 0, 'fa fa-envira', 1, 1, NULL, 3, '2024-11-02 08:47:35', 'admin', '2024-11-02 08:47:35', 'admin', 0);
INSERT INTO `sys_menu` VALUES (146, 'SQL 监控', 'admin/minitor/sql', 'admin/iframe/index', 'http://127.0.0.1:8080/api/druid/login.html', 142, 'fa fa-database', 1, 3, NULL, 1, '2024-11-02 08:47:35', 'admin', '2024-11-15 16:55:11', 'admin', 0);
INSERT INTO `sys_menu` VALUES (149, '系统应用', NULL, NULL, NULL, 0, 'fa fa-send-o', 1, 1, NULL, 5, '2024-11-03 07:51:43', 'admin', '2024-11-06 13:01:44', 'admin', 0);
INSERT INTO `sys_menu` VALUES (153, '代码生成', 'admin/app/generator/index', 'admin/app/generator/index', NULL, 149, 'fa fa-coffee', 1, 1, NULL, 1, '2024-11-03 07:57:11', 'admin', '2025-04-07 09:19:12', 'admin', 0);
INSERT INTO `sys_menu` VALUES (154, '通知管理', NULL, NULL, NULL, 0, 'fa fa-exclamation-circle', 1, 1, NULL, 4, '2024-11-12 14:21:33', 'admin', '2024-11-15 11:29:39', 'admin', 0);
INSERT INTO `sys_menu` VALUES (155, '发布通知', 'admin/notice/notice/index', 'admin/notice/notice/index', NULL, 154, 'fa fa-bullhorn', 1, 1, NULL, 1, '2024-11-12 14:22:00', 'admin', '2025-04-07 08:32:19', 'test', 0);
INSERT INTO `sys_menu` VALUES (156, '我的通知', 'admin/notice/my/index', 'admin/notice/my/index', NULL, 154, 'fa fa-bell', 1, 1, NULL, 2, '2024-11-12 14:22:24', 'admin', '2025-04-06 14:15:44', 'admin', 0);
INSERT INTO `sys_menu` VALUES (158, '系统配置', 'admin/sys/config/index', 'admin/sys/config/index', NULL, 1, 'fa fa-keyboard-o', 1, 1, NULL, 7, '2025-03-28 07:13:32', 'admin', '2025-04-08 10:49:34', 'admin', 0);
INSERT INTO `sys_menu` VALUES (159, '文章管理', NULL, NULL, NULL, 0, 'el-icon-notebook-2', 1, 1, NULL, 9, '2025-03-29 07:40:16', 'admin', '2025-03-29 07:45:34', 'admin', 0);
INSERT INTO `sys_menu` VALUES (160, '文章管理', 'admin/article/index', 'admin/article/index', NULL, 159, 'fa fa-pencil-square', 1, 1, NULL, 1, '2025-03-29 07:43:32', 'admin', '2025-04-07 08:44:03', 'admin', 0);
INSERT INTO `sys_menu` VALUES (161, '标签管理', 'admin/tag/index', 'admin/tag/index', NULL, 159, 'fa fa-tags', 1, 1, NULL, 3, '2025-03-29 07:44:09', 'admin', '2025-04-07 08:56:57', 'admin', 0);
INSERT INTO `sys_menu` VALUES (162, '类别管理', 'admin/ac/index', 'admin/ac/index', NULL, 159, 'fa fa-delicious', 1, 1, NULL, 2, '2025-03-29 07:44:44', 'admin', '2025-04-07 08:49:00', 'admin', 0);
INSERT INTO `sys_menu` VALUES (163, '重置密码', NULL, NULL, NULL, 2, NULL, 1, 2, NULL, 5, '2025-04-06 18:02:25', 'admin', '2025-04-06 18:10:27', 'admin', 0);
INSERT INTO `sys_menu` VALUES (164, '查询', NULL, NULL, NULL, 2, NULL, 1, 2, NULL, 4, '2025-04-06 18:08:56', 'admin', '2025-04-06 18:10:23', 'admin', 0);
INSERT INTO `sys_menu` VALUES (165, '添加', NULL, NULL, NULL, 158, NULL, 1, 2, NULL, 1, '2025-04-06 18:12:42', 'admin', '2025-04-06 18:25:16', 'admin', 0);
INSERT INTO `sys_menu` VALUES (166, '删除', NULL, NULL, NULL, 158, NULL, 1, 2, NULL, 2, '2025-04-06 18:12:54', 'admin', '2025-04-06 18:25:25', 'admin', 0);
INSERT INTO `sys_menu` VALUES (167, '编辑', NULL, NULL, NULL, 158, NULL, 1, 2, NULL, 3, '2025-04-06 18:13:15', 'admin', '2025-04-06 18:25:31', 'admin', 0);
INSERT INTO `sys_menu` VALUES (168, '查询', NULL, NULL, NULL, 158, NULL, 1, 2, NULL, 4, '2025-04-06 18:13:37', 'admin', '2025-04-06 18:25:38', 'admin', 0);
INSERT INTO `sys_menu` VALUES (169, '查询字典详情', NULL, NULL, NULL, 114, NULL, 1, 2, NULL, 4, '2025-04-07 08:14:07', 'admin', '2025-04-07 08:14:07', 'admin', 0);
INSERT INTO `sys_menu` VALUES (170, '查询', NULL, NULL, NULL, 155, NULL, 1, 2, NULL, 1, '2025-04-07 08:32:58', 'test', '2025-04-07 08:32:58', 'test', 0);
INSERT INTO `sys_menu` VALUES (171, '查询', NULL, NULL, NULL, 156, NULL, 1, 2, NULL, 1, '2025-04-07 08:33:15', 'test', '2025-04-07 08:33:15', 'test', 0);
INSERT INTO `sys_menu` VALUES (172, '添加', NULL, NULL, NULL, 162, NULL, 1, 2, NULL, 1, '2025-04-07 08:49:21', 'admin', '2025-04-07 08:49:21', 'admin', 0);
INSERT INTO `sys_menu` VALUES (173, '删除', NULL, NULL, NULL, 162, NULL, 1, 2, NULL, 2, '2025-04-07 08:49:36', 'admin', '2025-04-07 08:49:36', 'admin', 0);
INSERT INTO `sys_menu` VALUES (174, '编辑', NULL, NULL, NULL, 162, NULL, 1, 2, NULL, 3, '2025-04-07 08:49:55', 'admin', '2025-04-07 08:50:01', 'admin', 0);
INSERT INTO `sys_menu` VALUES (175, '查询', NULL, NULL, NULL, 162, NULL, 1, 2, NULL, 4, '2025-04-07 08:50:16', 'admin', '2025-04-07 08:50:16', 'admin', 0);
INSERT INTO `sys_menu` VALUES (176, '添加', NULL, NULL, NULL, 161, NULL, 1, 2, NULL, 1, '2025-04-07 08:57:16', 'admin', '2025-04-07 08:57:16', 'admin', 0);
INSERT INTO `sys_menu` VALUES (177, '删除', NULL, NULL, NULL, 161, NULL, 1, 2, NULL, 2, '2025-04-07 08:57:25', 'admin', '2025-04-07 08:57:25', 'admin', 0);
INSERT INTO `sys_menu` VALUES (178, '修改', NULL, NULL, NULL, 161, NULL, 1, 2, NULL, 3, '2025-04-07 08:57:36', 'admin', '2025-04-07 08:57:36', 'admin', 0);
INSERT INTO `sys_menu` VALUES (179, '查询', NULL, NULL, NULL, 161, NULL, 1, 2, NULL, 4, '2025-04-07 08:57:49', 'admin', '2025-04-07 08:57:49', 'admin', 0);
INSERT INTO `sys_menu` VALUES (180, '分配菜单', NULL, NULL, NULL, 3, NULL, 1, 2, NULL, 5, '2025-04-07 09:00:14', 'admin', '2025-04-07 09:00:14', 'admin', 0);
INSERT INTO `sys_menu` VALUES (181, '添加', NULL, NULL, NULL, 153, NULL, 1, 2, NULL, 1, '2025-04-07 09:19:30', 'admin', '2025-04-07 09:19:30', 'admin', 0);
INSERT INTO `sys_menu` VALUES (182, '删除', NULL, NULL, NULL, 153, NULL, 1, 2, NULL, 2, '2025-04-07 09:19:43', 'admin', '2025-04-07 09:19:43', 'admin', 0);
INSERT INTO `sys_menu` VALUES (183, '编辑', NULL, NULL, NULL, 153, NULL, 1, 2, NULL, 3, '2025-04-07 09:20:05', 'admin', '2025-04-07 09:20:05', 'admin', 0);
INSERT INTO `sys_menu` VALUES (184, '查询', NULL, NULL, NULL, 153, NULL, 1, 2, NULL, 4, '2025-04-07 09:20:31', 'admin', '2025-04-07 09:20:31', 'admin', 0);
INSERT INTO `sys_menu` VALUES (185, '代码生成', NULL, NULL, NULL, 153, NULL, 1, 2, NULL, 5, '2025-04-07 09:23:14', 'admin', '2025-04-07 09:23:14', 'admin', 0);

-- ----------------------------
-- Table structure for sys_menu_resource_rel
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_resource_rel`;
CREATE TABLE `sys_menu_resource_rel`  (
  `menu_id` bigint NOT NULL COMMENT '菜单 ID',
  `resource_id` bigint NOT NULL COMMENT '资源 ID'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单与资源关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu_resource_rel
-- ----------------------------
INSERT INTO `sys_menu_resource_rel` VALUES (118, 18);
INSERT INTO `sys_menu_resource_rel` VALUES (119, 6);
INSERT INTO `sys_menu_resource_rel` VALUES (4, 10);
INSERT INTO `sys_menu_resource_rel` VALUES (131, 27);
INSERT INTO `sys_menu_resource_rel` VALUES (132, 29);
INSERT INTO `sys_menu_resource_rel` VALUES (133, 32);
INSERT INTO `sys_menu_resource_rel` VALUES (134, 19);
INSERT INTO `sys_menu_resource_rel` VALUES (146, 1);
INSERT INTO `sys_menu_resource_rel` VALUES (156, 59);
INSERT INTO `sys_menu_resource_rel` VALUES (120, 60);
INSERT INTO `sys_menu_resource_rel` VALUES (113, 52);
INSERT INTO `sys_menu_resource_rel` VALUES (3, 21);
INSERT INTO `sys_menu_resource_rel` VALUES (12, 39);
INSERT INTO `sys_menu_resource_rel` VALUES (14, 9);
INSERT INTO `sys_menu_resource_rel` VALUES (13, 41);
INSERT INTO `sys_menu_resource_rel` VALUES (164, 34);
INSERT INTO `sys_menu_resource_rel` VALUES (163, 61);
INSERT INTO `sys_menu_resource_rel` VALUES (165, 63);
INSERT INTO `sys_menu_resource_rel` VALUES (166, 65);
INSERT INTO `sys_menu_resource_rel` VALUES (167, 64);
INSERT INTO `sys_menu_resource_rel` VALUES (168, 62);
INSERT INTO `sys_menu_resource_rel` VALUES (16, 54);
INSERT INTO `sys_menu_resource_rel` VALUES (17, 30);
INSERT INTO `sys_menu_resource_rel` VALUES (18, 37);
INSERT INTO `sys_menu_resource_rel` VALUES (127, 45);
INSERT INTO `sys_menu_resource_rel` VALUES (129, 33);
INSERT INTO `sys_menu_resource_rel` VALUES (130, 48);
INSERT INTO `sys_menu_resource_rel` VALUES (128, 20);
INSERT INTO `sys_menu_resource_rel` VALUES (122, 47);
INSERT INTO `sys_menu_resource_rel` VALUES (135, 51);
INSERT INTO `sys_menu_resource_rel` VALUES (137, 15);
INSERT INTO `sys_menu_resource_rel` VALUES (169, 14);
INSERT INTO `sys_menu_resource_rel` VALUES (139, 49);
INSERT INTO `sys_menu_resource_rel` VALUES (140, 35);
INSERT INTO `sys_menu_resource_rel` VALUES (138, 22);
INSERT INTO `sys_menu_resource_rel` VALUES (136, 25);
INSERT INTO `sys_menu_resource_rel` VALUES (141, 17);
INSERT INTO `sys_menu_resource_rel` VALUES (155, 66);
INSERT INTO `sys_menu_resource_rel` VALUES (170, 67);
INSERT INTO `sys_menu_resource_rel` VALUES (171, 67);
INSERT INTO `sys_menu_resource_rel` VALUES (160, 69);
INSERT INTO `sys_menu_resource_rel` VALUES (124, 71);
INSERT INTO `sys_menu_resource_rel` VALUES (123, 72);
INSERT INTO `sys_menu_resource_rel` VALUES (125, 73);
INSERT INTO `sys_menu_resource_rel` VALUES (126, 70);
INSERT INTO `sys_menu_resource_rel` VALUES (162, 74);
INSERT INTO `sys_menu_resource_rel` VALUES (172, 75);
INSERT INTO `sys_menu_resource_rel` VALUES (173, 76);
INSERT INTO `sys_menu_resource_rel` VALUES (174, 77);
INSERT INTO `sys_menu_resource_rel` VALUES (175, 78);
INSERT INTO `sys_menu_resource_rel` VALUES (161, 79);
INSERT INTO `sys_menu_resource_rel` VALUES (176, 80);
INSERT INTO `sys_menu_resource_rel` VALUES (177, 81);
INSERT INTO `sys_menu_resource_rel` VALUES (178, 82);
INSERT INTO `sys_menu_resource_rel` VALUES (179, 83);
INSERT INTO `sys_menu_resource_rel` VALUES (180, 50);
INSERT INTO `sys_menu_resource_rel` VALUES (153, 84);
INSERT INTO `sys_menu_resource_rel` VALUES (181, 85);
INSERT INTO `sys_menu_resource_rel` VALUES (182, 86);
INSERT INTO `sys_menu_resource_rel` VALUES (183, 87);
INSERT INTO `sys_menu_resource_rel` VALUES (184, 88);
INSERT INTO `sys_menu_resource_rel` VALUES (185, 89);
INSERT INTO `sys_menu_resource_rel` VALUES (6, 90);
INSERT INTO `sys_menu_resource_rel` VALUES (2, 43);
INSERT INTO `sys_menu_resource_rel` VALUES (112, 11);
INSERT INTO `sys_menu_resource_rel` VALUES (121, 1);
INSERT INTO `sys_menu_resource_rel` VALUES (158, 58);
INSERT INTO `sys_menu_resource_rel` VALUES (114, 8);
INSERT INTO `sys_menu_resource_rel` VALUES (114, 40);

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `resource_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '资源名称',
  `resource_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '资源地址',
  `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `created_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `updated_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `updated_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `deleted` int(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 91 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '资源表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
INSERT INTO `sys_resource` VALUES (1, '资源列表', '/admin/sys-resource/list', '2024-10-10 11:31:53', 'admin', '2024-10-17 11:38:56', 'admin', 0);
INSERT INTO `sys_resource` VALUES (4, '更新题库', '/admin/question/update', '2024-10-10 12:05:09', 'admin', '2024-10-17 11:39:08', 'admin', 1);
INSERT INTO `sys_resource` VALUES (6, '删除部门', '/admin/sys-dept/del', '2024-10-10 12:05:43', 'admin', '2024-10-17 11:30:44', 'admin', 0);
INSERT INTO `sys_resource` VALUES (7, '查询部门详情', '/admin/sys-dept/query', '2024-10-10 12:06:20', 'admin', '2024-10-17 11:38:36', 'admin', 0);
INSERT INTO `sys_resource` VALUES (8, '字典列表', '/admin/sys-dict/list', '2024-10-10 12:06:49', 'admin', '2024-11-01 14:04:30', 'admin', 0);
INSERT INTO `sys_resource` VALUES (9, '删除用户', '/admin/sys-user/del', '2024-10-10 12:10:57', 'admin', '2024-10-17 11:38:08', 'admin', 0);
INSERT INTO `sys_resource` VALUES (10, '菜单列表', '/admin/sys-menu/list', '2024-10-18 07:59:52', 'admin', '2024-10-18 07:59:52', 'admin', 0);
INSERT INTO `sys_resource` VALUES (11, '部门列表', '/admin/sys-dept/list', '2024-10-18 08:04:28', 'admin', '2024-10-18 08:06:38', 'admin', 0);
INSERT INTO `sys_resource` VALUES (12, '保存题库', '/admin/question/save', '2024-10-18 08:07:52', 'admin', '2024-10-18 08:07:52', 'admin', 1);
INSERT INTO `sys_resource` VALUES (13, '当前菜单', '/admin/sys-menu/current', '2024-10-18 08:10:57', 'admin', '2024-10-18 13:23:03', 'admin', 0);
INSERT INTO `sys_resource` VALUES (14, '查询字典详情', '/admin/sys-dict/query', '2024-10-18 08:19:03', 'admin', '2024-10-18 08:19:03', 'admin', 0);
INSERT INTO `sys_resource` VALUES (15, '删除字典', '/admin/sys-dict/del', '2024-10-18 08:20:14', 'admin', '2024-10-18 13:24:36', 'admin', 0);
INSERT INTO `sys_resource` VALUES (17, '查询字典明细详情', '/admin/sys-dict-detail/query', '2024-10-18 08:21:10', 'admin', '2024-10-18 08:21:10', 'admin', 0);
INSERT INTO `sys_resource` VALUES (18, '更新部门', '/admin/sys-dept/update', '2024-10-18 08:22:14', 'admin', '2024-10-18 13:19:43', 'admin', 0);
INSERT INTO `sys_resource` VALUES (19, '查询资源详情', '/admin/sys-resource/query', '2024-10-18 08:22:47', 'admin', '2024-10-18 13:17:25', 'admin', 0);
INSERT INTO `sys_resource` VALUES (20, '删除角色', '/admin/sys-role/del', '2024-10-18 08:23:45', 'admin', '2024-10-18 13:18:47', 'admin', 0);
INSERT INTO `sys_resource` VALUES (21, '角色列表', '/admin/sys-role/list', '2024-10-18 08:24:18', 'admin', '2024-10-18 08:24:18', 'admin', 0);
INSERT INTO `sys_resource` VALUES (22, '删除字典明细', '/admin/sys-dict-detail/del', '2024-10-18 08:24:40', 'admin', '2024-10-18 12:55:31', 'admin', 0);
INSERT INTO `sys_resource` VALUES (23, '角色赋权', '/admin/sys-role/assign-menu', '2024-10-18 08:25:24', 'admin', '2024-10-18 12:54:59', 'admin', 0);
INSERT INTO `sys_resource` VALUES (24, '题库详情', '/admin/question/query', '2024-10-18 08:25:46', 'admin', '2024-11-01 13:57:26', 'admin', 1);
INSERT INTO `sys_resource` VALUES (25, '保存字典明细', '/admin/sys-dict-detail/save', '2024-10-18 08:26:29', 'admin', '2024-10-18 13:22:12', 'admin', 0);
INSERT INTO `sys_resource` VALUES (26, '题库列表', '/admin/question/list', '2024-10-18 08:28:21', 'admin', '2024-11-01 13:57:17', 'admin', 1);
INSERT INTO `sys_resource` VALUES (27, '保存资源', '/admin/sys-resource/save', '2024-10-18 08:28:42', 'admin', '2024-10-18 08:36:21', 'admin', 0);
INSERT INTO `sys_resource` VALUES (29, '删除资源', '/admin/sys-resource/del', '2024-10-18 21:47:12', 'admin', '2024-10-22 09:11:41', 'admin', 0);
INSERT INTO `sys_resource` VALUES (30, '删除菜单', '/admin/sys-menu/del', '2024-10-18 21:48:18', 'admin', '2024-10-22 09:12:16', 'admin', 0);
INSERT INTO `sys_resource` VALUES (32, '更新资源', '/admin/sys-resource/update', '2024-10-18 21:48:18', 'admin', '2024-10-21 11:50:24', 'admin', 0);
INSERT INTO `sys_resource` VALUES (33, '更新角色', '/admin/sys-role/update', '2024-10-21 12:48:24', 'admin', '2024-10-22 09:13:47', 'admin', 0);
INSERT INTO `sys_resource` VALUES (34, '查询用户详情', '/admin/sys-user/query', '2024-10-21 12:48:46', 'admin', '2024-10-22 09:14:42', 'admin', 0);
INSERT INTO `sys_resource` VALUES (35, '更新字典明细', '/admin/sys-dict-detail/update', '2024-10-21 12:49:03', 'admin', '2024-10-22 09:15:03', 'admin', 0);
INSERT INTO `sys_resource` VALUES (37, '更新菜单', '/admin/sys-menu/update', '2024-10-21 12:49:27', 'admin', '2024-10-22 09:16:04', 'admin', 0);
INSERT INTO `sys_resource` VALUES (38, '删除题库', '/admin/question/del', '2024-10-21 12:50:02', 'admin', '2024-11-01 13:57:11', 'admin', 1);
INSERT INTO `sys_resource` VALUES (39, '保存用户', '/admin/sys-user/save', '2024-10-21 12:50:23', 'admin', '2024-10-21 12:50:23', 'admin', 0);
INSERT INTO `sys_resource` VALUES (40, '字典明细列表', '/admin/sys-dict-detail/list', '2024-10-21 12:50:41', 'admin', '2024-10-22 09:20:06', 'admin', 0);
INSERT INTO `sys_resource` VALUES (41, '更新用户', '/admin/sys-user/update', '2024-10-21 12:50:59', 'admin', '2024-10-21 12:50:59', 'admin', 0);
INSERT INTO `sys_resource` VALUES (42, '当前用户菜单', '/admin/sys-user/current', '2024-10-21 12:51:25', 'admin', '2024-10-21 13:02:28', 'admin', 0);
INSERT INTO `sys_resource` VALUES (43, '用户列表', '/admin/sys-user/list', '2024-10-21 12:52:00', 'admin', '2025-04-06 17:54:18', 'test', 0);
INSERT INTO `sys_resource` VALUES (44, '查询问题详情（F）', '/question/query', '2024-10-21 12:52:20', 'admin', '2024-10-21 12:52:20', 'admin', 1);
INSERT INTO `sys_resource` VALUES (45, '查询菜单详情', '/admin/sys-menu/query', '2024-10-21 12:52:40', 'admin', '2024-10-21 12:52:40', 'admin', 0);
INSERT INTO `sys_resource` VALUES (46, '接口列表', '/admin/request-mapping/list', '2024-10-21 12:52:58', 'admin', '2024-10-21 12:52:58', 'admin', 0);
INSERT INTO `sys_resource` VALUES (47, '保存角色', '/admin/sys-role/save', '2024-10-21 12:53:13', 'admin', '2024-10-21 12:53:13', 'admin', 0);
INSERT INTO `sys_resource` VALUES (48, '查询角色详情', '/admin/sys-role/query', '2024-10-21 12:53:53', 'admin', '2024-10-21 13:00:09', 'admin', 0);
INSERT INTO `sys_resource` VALUES (49, '更新字典', '/admin/sys-dict/update', '2024-10-21 12:54:13', 'admin', '2024-10-21 12:54:13', 'admin', 0);
INSERT INTO `sys_resource` VALUES (50, '角色菜单', '/admin/sys-role/list-menu-ids', '2024-10-21 13:04:45', 'admin', '2024-10-21 13:11:28', 'admin', 0);
INSERT INTO `sys_resource` VALUES (51, '保存字典', '/admin/sys-dict/save', '2024-10-21 13:05:03', 'admin', '2024-10-21 13:12:31', 'admin', 0);
INSERT INTO `sys_resource` VALUES (52, '保存部门', '/admin/sys-dept/save', '2024-10-21 13:05:18', 'admin', '2024-10-21 13:05:18', 'admin', 0);
INSERT INTO `sys_resource` VALUES (53, '文件上传', '/admin/file/upload', '2024-10-21 13:05:36', 'admin', '2024-10-21 13:05:36', 'admin', 0);
INSERT INTO `sys_resource` VALUES (54, '保存菜单', '/admin/sys-menu/save', '2024-10-21 13:05:52', 'admin', '2024-10-21 13:05:52', 'admin', 0);
INSERT INTO `sys_resource` VALUES (58, '系统配置列表', '/admin/sys-config/list', '2025-03-28 07:14:29', 'admin', '2025-03-28 07:14:42', 'admin', 0);
INSERT INTO `sys_resource` VALUES (59, '我的通知列表', '/admin/notice/myList', '2025-04-06 14:14:54', 'admin', '2025-04-07 08:28:03', 'test', 0);
INSERT INTO `sys_resource` VALUES (60, '部门详情', '/admin/sys-dept/query', '2025-04-06 17:16:38', 'admin', '2025-04-06 17:16:38', 'admin', 0);
INSERT INTO `sys_resource` VALUES (61, '重置用户密码', '/admin/sys-user/resetPwd', '2025-04-06 18:01:03', 'admin', '2025-04-06 18:01:03', 'admin', 0);
INSERT INTO `sys_resource` VALUES (62, '查询配置详情', '/admin/sys-config/query', '2025-04-06 18:24:15', 'admin', '2025-04-06 18:24:15', 'admin', 0);
INSERT INTO `sys_resource` VALUES (63, '保存配置', '/admin/sys-config/save', '2025-04-06 18:24:27', 'admin', '2025-04-06 18:24:27', 'admin', 0);
INSERT INTO `sys_resource` VALUES (64, '更新配置', '/admin/sys-config/update', '2025-04-06 18:24:37', 'admin', '2025-04-06 18:24:37', 'admin', 0);
INSERT INTO `sys_resource` VALUES (65, '删除配置', '/admin/sys-config/del', '2025-04-06 18:24:47', 'admin', '2025-04-06 18:24:47', 'admin', 0);
INSERT INTO `sys_resource` VALUES (66, '通知列表', '/admin/notice/list', '2025-04-07 08:27:14', 'test', '2025-04-07 08:27:14', 'test', 0);
INSERT INTO `sys_resource` VALUES (67, '查询通知详情', '/admin/notice/list', '2025-04-07 08:27:32', 'test', '2025-04-07 08:27:32', 'test', 0);
INSERT INTO `sys_resource` VALUES (68, '保存通知', '/admin/notice/save', '2025-04-07 08:31:17', 'test', '2025-04-07 08:31:17', 'test', 0);
INSERT INTO `sys_resource` VALUES (69, '文章列表', '/admin/article/list', '2025-04-07 08:41:34', 'admin', '2025-04-07 08:41:34', 'admin', 0);
INSERT INTO `sys_resource` VALUES (70, '查询文章详情', '/admin/article/query', '2025-04-07 08:41:46', 'admin', '2025-04-07 08:41:46', 'admin', 0);
INSERT INTO `sys_resource` VALUES (71, '更新文章', '/admin/article/update', '2025-04-07 08:41:59', 'admin', '2025-04-07 08:41:59', 'admin', 0);
INSERT INTO `sys_resource` VALUES (72, '保存文章', '/admin/article/save', '2025-04-07 08:42:30', 'admin', '2025-04-07 08:42:30', 'admin', 0);
INSERT INTO `sys_resource` VALUES (73, '删除文章', '/admin/article/del', '2025-04-07 08:44:55', 'admin', '2025-04-07 08:44:55', 'admin', 0);
INSERT INTO `sys_resource` VALUES (74, '文章类别列表', '/admin/article-category/list', '2025-04-07 08:47:19', 'admin', '2025-04-07 08:47:19', 'admin', 0);
INSERT INTO `sys_resource` VALUES (75, '保存文章类别', '/admin/article-category/save', '2025-04-07 08:47:33', 'admin', '2025-04-07 08:47:33', 'admin', 0);
INSERT INTO `sys_resource` VALUES (76, '删除文章类别', '/admin/article-category/del', '2025-04-07 08:47:52', 'admin', '2025-04-07 08:47:52', 'admin', 0);
INSERT INTO `sys_resource` VALUES (77, '更新文章类别', '/admin/article-category/update', '2025-04-07 08:48:07', 'admin', '2025-04-07 08:48:07', 'admin', 0);
INSERT INTO `sys_resource` VALUES (78, '查询文章类别详情', '/admin/article-category/query', '2025-04-07 08:48:22', 'admin', '2025-04-07 08:48:22', 'admin', 0);
INSERT INTO `sys_resource` VALUES (79, '标签列表', '/admin/tag/list', '2025-04-07 08:54:06', 'admin', '2025-04-07 08:54:06', 'admin', 0);
INSERT INTO `sys_resource` VALUES (80, '保存标签', '/admin/tag/save', '2025-04-07 08:54:27', 'admin', '2025-04-07 08:54:27', 'admin', 0);
INSERT INTO `sys_resource` VALUES (81, '删除标签', '/admin/tag/del', '2025-04-07 08:54:39', 'admin', '2025-04-07 08:54:39', 'admin', 0);
INSERT INTO `sys_resource` VALUES (82, '更新标签', '/admin/tag/update', '2025-04-07 08:54:52', 'admin', '2025-04-07 08:55:38', 'admin', 0);
INSERT INTO `sys_resource` VALUES (83, '查询标签详情', '/admin/tag/query', '2025-04-07 08:55:48', 'admin', '2025-04-07 08:55:48', 'admin', 0);
INSERT INTO `sys_resource` VALUES (84, '代码模板列表', '/admin/code-template/list', '2025-04-07 09:17:56', 'admin', '2025-04-07 09:17:56', 'admin', 0);
INSERT INTO `sys_resource` VALUES (85, '保存代码模板', '/admin/code-template/save', '2025-04-07 09:18:10', 'admin', '2025-04-07 09:18:10', 'admin', 0);
INSERT INTO `sys_resource` VALUES (86, '删除代码模板', '/admin/code-template/del', '2025-04-07 09:18:26', 'admin', '2025-04-07 09:18:26', 'admin', 0);
INSERT INTO `sys_resource` VALUES (87, '更新代码模板', '/admin/code-template/update', '2025-04-07 09:18:40', 'admin', '2025-04-07 09:18:40', 'admin', 0);
INSERT INTO `sys_resource` VALUES (88, '查询代码模板详情', '/admin/code-template/query', '2025-04-07 09:18:56', 'admin', '2025-04-07 09:18:56', 'admin', 0);
INSERT INTO `sys_resource` VALUES (89, '代码生成', '/admin/code-template/generate', '2025-04-07 09:22:51', 'admin', '2025-04-07 09:22:51', 'admin', 0);
INSERT INTO `sys_resource` VALUES (90, '首页数量统计', '/admin/home/count', '2025-04-07 16:49:36', 'admin', '2025-04-07 16:49:36', 'admin', 0);

-- ----------------------------
-- Table structure for sys_resource_category
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource_category`;
CREATE TABLE `sys_resource_category`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '资源类别名称',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `created_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `updated_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `updated_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `deleted` int NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '资源类别' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_resource_category
-- ----------------------------
INSERT INTO `sys_resource_category` VALUES (1, '系统管理', 1, '2023-09-28 17:20:14', 'admin', '2023-09-28 17:20:14', 'admin222', NULL);
INSERT INTO `sys_resource_category` VALUES (2, '系统管理', 0, '2023-09-28 17:20:44', 'admin', '2023-09-28 17:20:44', 'admin222', 0);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名',
  `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `status` int NULL DEFAULT NULL COMMENT '状态',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `created_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `updated_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `updated_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `deleted` int(1) UNSIGNED ZEROFILL NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', '拥有系统最高权限', 1, 1, '2023-08-28 23:05:33', 'admin', '2023-08-28 23:05:47', 'admin', 0);
INSERT INTO `sys_role` VALUES (2, '普通管理员', '一般权限', 1, 2, '2023-09-28 11:28:41', 'admin', '2025-04-06 15:57:20', 'admin', 0);

-- ----------------------------
-- Table structure for sys_role_menu_rel
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu_rel`;
CREATE TABLE `sys_role_menu_rel`  (
  `menu_id` bigint NOT NULL COMMENT '菜单主键',
  `role_id` bigint NOT NULL COMMENT '角色主键'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu_rel
-- ----------------------------
INSERT INTO `sys_role_menu_rel` VALUES (14, 1);
INSERT INTO `sys_role_menu_rel` VALUES (3, 1);
INSERT INTO `sys_role_menu_rel` VALUES (4, 1);
INSERT INTO `sys_role_menu_rel` VALUES (19, 7);
INSERT INTO `sys_role_menu_rel` VALUES (0, 7);
INSERT INTO `sys_role_menu_rel` VALUES (19, 7);
INSERT INTO `sys_role_menu_rel` VALUES (0, 7);
INSERT INTO `sys_role_menu_rel` VALUES (0, 2);
INSERT INTO `sys_role_menu_rel` VALUES (159, 2);
INSERT INTO `sys_role_menu_rel` VALUES (162, 2);
INSERT INTO `sys_role_menu_rel` VALUES (175, 2);
INSERT INTO `sys_role_menu_rel` VALUES (174, 2);
INSERT INTO `sys_role_menu_rel` VALUES (173, 2);
INSERT INTO `sys_role_menu_rel` VALUES (172, 2);
INSERT INTO `sys_role_menu_rel` VALUES (161, 2);
INSERT INTO `sys_role_menu_rel` VALUES (179, 2);
INSERT INTO `sys_role_menu_rel` VALUES (178, 2);
INSERT INTO `sys_role_menu_rel` VALUES (177, 2);
INSERT INTO `sys_role_menu_rel` VALUES (176, 2);
INSERT INTO `sys_role_menu_rel` VALUES (160, 2);
INSERT INTO `sys_role_menu_rel` VALUES (126, 2);
INSERT INTO `sys_role_menu_rel` VALUES (125, 2);
INSERT INTO `sys_role_menu_rel` VALUES (124, 2);
INSERT INTO `sys_role_menu_rel` VALUES (123, 2);
INSERT INTO `sys_role_menu_rel` VALUES (154, 2);
INSERT INTO `sys_role_menu_rel` VALUES (156, 2);
INSERT INTO `sys_role_menu_rel` VALUES (171, 2);
INSERT INTO `sys_role_menu_rel` VALUES (155, 2);
INSERT INTO `sys_role_menu_rel` VALUES (170, 2);
INSERT INTO `sys_role_menu_rel` VALUES (149, 2);
INSERT INTO `sys_role_menu_rel` VALUES (153, 2);
INSERT INTO `sys_role_menu_rel` VALUES (185, 2);
INSERT INTO `sys_role_menu_rel` VALUES (184, 2);
INSERT INTO `sys_role_menu_rel` VALUES (183, 2);
INSERT INTO `sys_role_menu_rel` VALUES (182, 2);
INSERT INTO `sys_role_menu_rel` VALUES (181, 2);
INSERT INTO `sys_role_menu_rel` VALUES (142, 2);
INSERT INTO `sys_role_menu_rel` VALUES (148, 2);
INSERT INTO `sys_role_menu_rel` VALUES (146, 2);
INSERT INTO `sys_role_menu_rel` VALUES (6, 2);
INSERT INTO `sys_role_menu_rel` VALUES (1, 2);
INSERT INTO `sys_role_menu_rel` VALUES (158, 2);
INSERT INTO `sys_role_menu_rel` VALUES (168, 2);
INSERT INTO `sys_role_menu_rel` VALUES (167, 2);
INSERT INTO `sys_role_menu_rel` VALUES (166, 2);
INSERT INTO `sys_role_menu_rel` VALUES (165, 2);
INSERT INTO `sys_role_menu_rel` VALUES (121, 2);
INSERT INTO `sys_role_menu_rel` VALUES (134, 2);
INSERT INTO `sys_role_menu_rel` VALUES (133, 2);
INSERT INTO `sys_role_menu_rel` VALUES (132, 2);
INSERT INTO `sys_role_menu_rel` VALUES (131, 2);
INSERT INTO `sys_role_menu_rel` VALUES (114, 2);
INSERT INTO `sys_role_menu_rel` VALUES (169, 2);
INSERT INTO `sys_role_menu_rel` VALUES (141, 2);
INSERT INTO `sys_role_menu_rel` VALUES (140, 2);
INSERT INTO `sys_role_menu_rel` VALUES (139, 2);
INSERT INTO `sys_role_menu_rel` VALUES (138, 2);
INSERT INTO `sys_role_menu_rel` VALUES (137, 2);
INSERT INTO `sys_role_menu_rel` VALUES (136, 2);
INSERT INTO `sys_role_menu_rel` VALUES (135, 2);
INSERT INTO `sys_role_menu_rel` VALUES (112, 2);
INSERT INTO `sys_role_menu_rel` VALUES (120, 2);
INSERT INTO `sys_role_menu_rel` VALUES (119, 2);
INSERT INTO `sys_role_menu_rel` VALUES (118, 2);
INSERT INTO `sys_role_menu_rel` VALUES (113, 2);
INSERT INTO `sys_role_menu_rel` VALUES (4, 2);
INSERT INTO `sys_role_menu_rel` VALUES (127, 2);
INSERT INTO `sys_role_menu_rel` VALUES (18, 2);
INSERT INTO `sys_role_menu_rel` VALUES (17, 2);
INSERT INTO `sys_role_menu_rel` VALUES (16, 2);
INSERT INTO `sys_role_menu_rel` VALUES (3, 2);
INSERT INTO `sys_role_menu_rel` VALUES (180, 2);
INSERT INTO `sys_role_menu_rel` VALUES (130, 2);
INSERT INTO `sys_role_menu_rel` VALUES (129, 2);
INSERT INTO `sys_role_menu_rel` VALUES (128, 2);
INSERT INTO `sys_role_menu_rel` VALUES (122, 2);
INSERT INTO `sys_role_menu_rel` VALUES (2, 2);
INSERT INTO `sys_role_menu_rel` VALUES (164, 2);
INSERT INTO `sys_role_menu_rel` VALUES (163, 2);
INSERT INTO `sys_role_menu_rel` VALUES (14, 2);
INSERT INTO `sys_role_menu_rel` VALUES (13, 2);
INSERT INTO `sys_role_menu_rel` VALUES (12, 2);

-- ----------------------------
-- Table structure for sys_role_resource_rel
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_resource_rel`;
CREATE TABLE `sys_role_resource_rel`  (
  `role_id` bigint NOT NULL COMMENT '角色主键',
  `resource_id` bigint NOT NULL COMMENT '资源主键'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色和资源的关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_resource_rel
-- ----------------------------
INSERT INTO `sys_role_resource_rel` VALUES (1, 4);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `status` int NULL DEFAULT NULL COMMENT '状态',
  `dept_id` bigint NULL DEFAULT NULL COMMENT '部门 ID',
  `is_admin` tinyint NULL DEFAULT NULL COMMENT '是否是超级管理员（0-否 1-是）',
  `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `created_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `updated_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `updated_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `deleted` int(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '逻辑删除',
  `gender` tinyint NULL DEFAULT NULL COMMENT '性别（1-男 2-女）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 64 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '$2a$10$cAvL8.8K1Gn74NIO40md5.Jw.mrKEKaGPbhGULD.uRggR0NVGP1o.', '超级管理员', '1234@qq.com', '13688888888', 'https://t7.baidu.com/it/u=1819248061,230866778&fm=193&f=GIF', 1, 1, 1, '2023-08-28 23:06:11', 'admin', '2025-04-08 10:23:28', 'admin', 0, 1);
INSERT INTO `sys_user` VALUES (2, 'test', '$2a$10$BxdIO/o/pdOPGWI3FzR.Qe9UQCC7gj7Yb0jS7Fs.19lKGjpJlGMEu', '演示账户', 'xxx@qq.com', '13466666666', NULL, 1, 10, 0, '2025-03-28 19:34:35', 'admin', '2025-04-07 16:37:23', 'admin', 0, 1);

-- ----------------------------
-- Table structure for sys_user_role_rel
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role_rel`;
CREATE TABLE `sys_user_role_rel`  (
  `user_id` bigint NOT NULL COMMENT '用户主键',
  `role_id` bigint NOT NULL COMMENT '角色主键'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户和角色关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role_rel
-- ----------------------------
INSERT INTO `sys_user_role_rel` VALUES (52, 6);
INSERT INTO `sys_user_role_rel` VALUES (53, 6);
INSERT INTO `sys_user_role_rel` VALUES (55, 2);
INSERT INTO `sys_user_role_rel` VALUES (51, 2);
INSERT INTO `sys_user_role_rel` VALUES (1, 1);
INSERT INTO `sys_user_role_rel` VALUES (63, 2);
INSERT INTO `sys_user_role_rel` VALUES (59, 2);
INSERT INTO `sys_user_role_rel` VALUES (59, 1);
INSERT INTO `sys_user_role_rel` VALUES (60, 2);
INSERT INTO `sys_user_role_rel` VALUES (2, 2);

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标签名',
  `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `created_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `updated_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `updated_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `deleted` int(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '标签表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tag
-- ----------------------------
INSERT INTO `tag` VALUES (1, 'Spring', '2025-03-29 07:51:25', 'admin', '2025-03-29 07:51:25', 'admin', 0);
INSERT INTO `tag` VALUES (2, 'MyBatis', '2025-03-29 09:05:13', 'admin', '2025-03-29 09:05:13', 'admin', 0);

-- ----------------------------
-- Table structure for upload_file
-- ----------------------------
DROP TABLE IF EXISTS `upload_file`;
CREATE TABLE `upload_file`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件名字',
  `file_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件路径',
  `created_time` datetime NULL DEFAULT NULL,
  `created_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `updated_time` datetime NULL DEFAULT NULL,
  `updated_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `deleted` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '资源文件表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of upload_file
-- ----------------------------
INSERT INTO `upload_file` VALUES (19, '176712-ai_tuo_sha_guo_jia_gong_yuan-mi_ku_mi_guo_jia_gong_yuan-an_bo_sai_li_guo_jia_gong_yuan-gong_yuan-safari-1920x1080.jpg', '2024-10-10\\944b9c5ba43c444b91835a28644ebb05..jpg', '2024-10-10 10:16:30', 'admin', '2024-10-10 10:16:30', 'admin', 0);

SET FOREIGN_KEY_CHECKS = 1;
