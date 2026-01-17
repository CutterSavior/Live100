/*
 Navicat Premium Dump SQL

 Source Server         : xxx
 Source Server Type    : MySQL
 Source Server Version : 80044 (8.0.44)
 Source Host           : xxx
 Source Schema         : lanjii-v3

 Target Server Type    : MySQL
 Target Server Version : 80044 (8.0.44)
 File Encoding         : 65001

 Date: 16/01/2026 19:42:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ai_file_knowledge
-- ----------------------------
DROP TABLE IF EXISTS `ai_file_knowledge`;
CREATE TABLE `ai_file_knowledge`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户ID（0-平台，>0-租户）',
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件名称',
  `file_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件类型（pdf/markdown/txt/html/doc/docx等）',
  `file_path` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件存储路径',
  `full_path` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '物理全路径',
  `file_size` bigint NOT NULL COMMENT '文件大小（字节）',
  `metadata_json` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '元数据JSON',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '更新人',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除标志（0-未删除 1-已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'AI文件知识库表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ai_file_knowledge
-- ----------------------------

-- ----------------------------
-- Table structure for ai_knowledge
-- ----------------------------
DROP TABLE IF EXISTS `ai_knowledge`;
CREATE TABLE `ai_knowledge`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '知识库标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '知识库内容',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新人',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除(0-未删除，1-已删除)',
  `metadata_json` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '元数据 JSON（根据元数据字段配置动态生成）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_title`(`title` ASC) USING BTREE,
  INDEX `idx_create_time`(`create_time` ASC) USING BTREE,
  INDEX `idx_deleted`(`deleted` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'AI知识库表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ai_knowledge
-- ----------------------------

-- ----------------------------
-- Table structure for ai_metadata_field
-- ----------------------------
DROP TABLE IF EXISTS `ai_metadata_field`;
CREATE TABLE `ai_metadata_field`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `field_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字段名（英文唯一标识）',
  `display_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '显示名称（中文）',
  `data_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '数据类型(string,number,boolean,date,datetime,array,object,enum)',
  `default_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '默认值',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '字段描述',
  `is_required` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否必填（1 是 0 否）',
  `is_searchable` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否可搜索（1 是 0 否）',
  `use_count` bigint NULL DEFAULT 0 COMMENT '使用次数',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '逻辑删除标志',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_field_name`(`field_name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'AI 元数据字段配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ai_metadata_field
-- ----------------------------

-- ----------------------------
-- Table structure for ai_model_config
-- ----------------------------
DROP TABLE IF EXISTS `ai_model_config`;
CREATE TABLE `ai_model_config`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `config_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '配置名称',
  `api_provider` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'API 提供商（openai/azure/anthropic/alibaba/baidu/custom 等）',
  `model_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '模型标识（例如 gpt-4-turbo-preview）',
  `is_enabled` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否启用（1-启用 0-禁止）',
  `is_default` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否默认（1-是 0-否）',
  `role_id` bigint NULL DEFAULT NULL COMMENT '绑定角色ID（来自角色配置模块，允许为空）',
  `api_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '模型 API Key（建议后期做加密/脱敏）',
  `api_endpoint` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'API 端点（为空则使用默认）',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  `temperature` decimal(3, 2) NULL DEFAULT 0.70 COMMENT '温度，0.0-2.0',
  `top_p` decimal(3, 2) NULL DEFAULT 0.90 COMMENT 'Top P，0.0-1.0',
  `max_tokens` int NULL DEFAULT 2048 COMMENT '最大 Token 数',
  `timeout_seconds` int NULL DEFAULT 60 COMMENT '超时时间（秒）',
  `frequency_penalty` decimal(3, 2) NULL DEFAULT 0.00 COMMENT '频率惩罚，-2.0~2.0',
  `presence_penalty` decimal(3, 2) NULL DEFAULT 0.00 COMMENT '存在惩罚，-2.0~2.0',
  `stop_sequences` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '停止序列，按行分隔',
  `retry_count` int NULL DEFAULT 3 COMMENT '重试次数',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '删除标志（0-未删除，1-已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'AI 模型配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ai_model_config
-- ----------------------------

-- ----------------------------
-- Table structure for ai_role_prompt
-- ----------------------------
DROP TABLE IF EXISTS `ai_role_prompt`;
CREATE TABLE `ai_role_prompt`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '角色描述',
  `is_enabled` tinyint(1) NULL DEFAULT 1 COMMENT '是否启用（1-启用 0-禁用）',
  `system_prompt` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '系统提示词（System Prompt）',
  `is_rag_enabled` tinyint(1) NULL DEFAULT 1 COMMENT '是否启用 RAG（1-启用 0-禁用）',
  `rag_template` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT 'RAG 提示词模板',
  `top_k` int NULL DEFAULT NULL COMMENT '检索文档数量',
  `similarity_threshold` double NULL DEFAULT NULL COMMENT '相似度阈值（0-1）',
  `custom_filter` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '自定义过滤条件 JSON 字符串',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新人',
  `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '删除标志（0-未删除，1-已删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_role_name`(`role_name` ASC) USING BTREE,
  INDEX `idx_is_enabled`(`is_enabled` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'AI 角色与提示词配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ai_role_prompt
-- ----------------------------

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '配置ID',
  `config_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '配置名称',
  `config_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '配置键名',
  `config_value` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '配置键值',
  `config_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '配置类型（1-系统配置 2-业务配置）',
  `is_enabled` tinyint(1) NULL DEFAULT 1 COMMENT '是否启用（1启用 0禁用）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '删除标志（0-未删除，1-已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (25, '文件服务器地址前缀', 'FILE_SERVER_BASE_URL', 'http://106.54.167.194', '1', 1, '文件访问的服务器地址前缀，包含协议、IP和端口。示例：http://192.168.1.100:8080 或 https://files.example.com', 'admin', '2025-11-14 13:17:22', 'admin', '2025-12-08 15:00:49', 0);
INSERT INTO `sys_config` VALUES (26, '静态资源映射路径', 'UPLOAD_ROOT_PATH', '/opt/app/lanjii/upload/', '1', 1, '', 'admin', '2025-11-14 14:22:54', 'admin', '2025-12-25 10:55:29', 1);
INSERT INTO `sys_config` VALUES (27, '默认的用户密码', 'DEFAULT_USER_PWD', '123456', '1', 1, '', 'admin', '2025-11-14 14:22:54', 'admin', '2025-11-14 15:26:30', 0);

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `parent_id` bigint NULL DEFAULT NULL COMMENT '父部门ID',
  `ancestors` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '祖级列表(逗号分隔)',
  `dept_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '部门名称',
  `sort_order` int NULL DEFAULT NULL COMMENT '显示顺序',
  `leader` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '负责人',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `is_enabled` tinyint(1) NULL DEFAULT 1 COMMENT '是否启用（1启用 0禁用）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建人',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新人',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '删除标志(0未删除 1已删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_parent_id`(`parent_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统部门表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1, 0, '', 'XX科技有限公司', 1, '张总', '13800001111', 'ceo@company.com', 1, '2023-01-01 10:00:00', '2023-01-01 10:00:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (2, 1, '1', '北京分公司', 1, '李经理', '13800002222', 'bj@company.com', 1, '2023-01-02 09:00:00', '2023-01-02 09:00:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (3, 1, '1', '上海分公司', 2, '王经理', '13800003333', 'sh@company.com', 1, '2023-01-02 09:30:00', '2023-01-02 09:30:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (4, 1, '1', '技术研发中心', 3, '赵总监', '13800004444', 'tech@company.com', 1, '2023-01-03 10:00:00', '2023-01-03 10:00:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (5, 1, '1', '市场营销部', 4, '钱总监', '13800005555', 'market@company.com', 1, '2023-01-03 11:00:00', '2023-01-03 11:00:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (6, 1, '1', '人力资源部', 5, '孙经理', '13800006666', 'hr@company.com', 1, '2023-01-04 09:00:00', '2023-01-04 09:00:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (7, 1, '1', '财务部', 6, '周总监', '13800007777', 'finance@company.com', 1, '2023-01-04 10:00:00', '2023-01-04 10:00:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (8, 4, '1,4', '前端开发部', 1, '吴主管', '13800008888', 'frontend@company.com', 1, '2023-01-05 09:00:00', '2023-01-05 09:00:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (9, 4, '1,4', '后端开发部', 2, '郑主管', '13800009999', 'backend@company.com', 1, '2023-01-05 10:00:00', '2023-01-05 10:00:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (10, 4, '1,4', '测试部', 3, '冯主管', '13800010000', 'qa@company.com', 1, '2023-01-05 11:00:00', '2023-01-05 11:00:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (11, 4, '1,4', '产品设计部', 4, '陈经理', '13800011111', 'product@company.com', 1, '2023-01-06 09:00:00', '2023-01-06 09:00:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (12, 4, '1,4', '运维部', 5, '楚主管', '13800012222', 'devops@company.com', 1, '2023-01-06 10:00:00', '2023-01-06 10:00:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (13, 8, '1,4,8', 'Web开发组', 1, '魏组长', '13800013333', 'web@company.com', 1, '2023-01-10 09:00:00', '2023-01-10 09:00:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (14, 8, '1,4,8', '移动端组', 2, '蒋组长', '13800014444', 'mobile@company.com', 1, '2023-01-10 10:00:00', '2023-01-10 10:00:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (15, 8, '1,4,8', 'UI设计组', 3, '沈组长', '13800015555', 'ui@company.com', 1, '2023-01-10 11:00:00', '2023-01-10 11:00:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (16, 1, '1', '临时项目组', 7, '韩经理', '13800016666', 'temp@company.com', 1, '2023-02-01 09:00:00', '2023-02-01 09:00:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (17, 4, '1,4', '已解散部门', 6, NULL, NULL, NULL, 0, '2023-01-15 09:00:00', '2023-01-15 09:00:00', 'admin', 'admin', 1);
INSERT INTO `sys_dept` VALUES (18, 0, '', '合作伙伴', 8, NULL, NULL, NULL, 1, '2023-02-10 10:00:00', '2023-02-10 10:00:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (19, 5, '1,5', '市场策划部', 1, '杨经理', '13800017777', 'plan@company.com', 1, '2023-01-07 09:00:00', '2023-01-07 09:00:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (20, 5, '1,5', '品牌推广部', 2, '朱经理', '13800018888', 'brand@company.com', 1, '2023-01-07 10:00:00', '2023-01-07 10:00:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (21, 6, '1,6', '招聘部', 1, '秦主管', '13800019999', 'recruit@company.com', 1, '2023-01-08 09:00:00', '2023-01-08 09:00:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (22, 6, '1,6', '培训发展部', 2, '许主管', '13800020000', 'training@company.com', 1, '2023-01-08 10:00:00', '2023-01-08 10:00:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (23, 7, '1,7', '会计核算部', 1, '何主管', '13800021111', 'accounting@company.com', 1, '2023-01-09 09:00:00', '2023-01-09 09:00:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (24, 7, '1,7', '资金管理部', 2, '吕主管', '13800022222', 'treasury@company.com', 1, '2023-01-09 10:00:00', '2023-01-09 10:00:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (35, 5, '1,5', '宣传部', 0, '林某某', '15612378965', '1134809127@qq.com', 1, '2025-12-23 15:28:07', '2025-12-23 15:28:07', 'admin', 'admin', 0);

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '字典数据主键',
  `sort_order` int NULL DEFAULT NULL COMMENT '字典排序',
  `dict_label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典标签',
  `dict_value` int NULL DEFAULT NULL COMMENT '字典键值',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典类型编码',
  `css_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '表格回显样式',
  `is_default` tinyint NULL DEFAULT 0 COMMENT '是否默认（1是 0否）',
  `is_enabled` tinyint(1) NULL DEFAULT 1 COMMENT '是否启用（1启用 0禁用）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '删除标志（0-未删除，1-已删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_dict_type`(`dict_type` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (1, 2, '禁用', 0, 'STATUS', NULL, NULL, 0, 1, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_dict_data` VALUES (2, 1, '正常', 1, 'STATUS', NULL, NULL, 0, 1, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_dict_data` VALUES (3, 1, '显示', 1, 'VISIBLE', NULL, NULL, 0, 1, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_dict_data` VALUES (4, 0, '隐藏', 0, 'VISIBLE', NULL, NULL, 0, 1, '22', NULL, NULL, 'admin', '2025-10-11 20:28:57', 0);
INSERT INTO `sys_dict_data` VALUES (5, 3, '失败', 0, 'LOGIN_STATUS', NULL, NULL, 0, 1, '登录失败', NULL, NULL, 'admin', '2025-10-11 20:38:58', 0);
INSERT INTO `sys_dict_data` VALUES (6, 2, '成功', 1, 'LOGIN_STATUS', NULL, NULL, 0, 1, '登录成功', NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_dict_data` VALUES (7, 1, '登录', 0, 'LOGIN_TYPE', NULL, NULL, 0, 1, '用户登录', NULL, NULL, 'admin', '2025-10-11 20:27:27', 0);
INSERT INTO `sys_dict_data` VALUES (8, 2, '登出', 1, 'LOGIN_TYPE', NULL, NULL, 0, 1, '用户登出', NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_dict_data` VALUES (9, 1, '失败', 0, 'OPER_STATUS', NULL, NULL, 0, 1, '操作失败', NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_dict_data` VALUES (10, 2, '成功', 1, 'OPER_STATUS', NULL, NULL, 0, 1, '操作成功', NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_dict_data` VALUES (11, 1, '新增', 0, 'BUSINESS_TYPE', NULL, NULL, 0, 1, '新增操作', NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_dict_data` VALUES (12, 2, '修改', 1, 'BUSINESS_TYPE', NULL, NULL, 0, 1, '修改操作', NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_dict_data` VALUES (13, 3, '删除', 2, 'BUSINESS_TYPE', NULL, NULL, 0, 1, '删除操作', NULL, NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `type_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典名称',
  `type_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典类型编码',
  `is_enabled` tinyint(1) NULL DEFAULT 1 COMMENT '是否启用（1启用 0禁用）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '删除标志（0-未删除，1-已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典类型表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1, '状态', 'STATUS', 1, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_dict_type` VALUES (2, '是否可见', 'VISIBLE', 1, NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_dict_type` VALUES (4, '登录状态', 'LOGIN_STATUS', 1, '用户登录状态字典', NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_dict_type` VALUES (5, '登录类型', 'LOGIN_TYPE', 1, '用户登录类型字典', NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_dict_type` VALUES (6, '操作状态', 'OPER_STATUS', 1, '用户操作状态字典', NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_dict_type` VALUES (7, '业务类型', 'BUSINESS_TYPE', 1, '操作业务类型字典', NULL, NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE `sys_login_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `ip_address` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '登录IP地址',
  `login_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '登录地点',
  `browser` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '浏览器类型',
  `os` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作系统',
  `login_type` tinyint(1) NOT NULL DEFAULT 0 COMMENT '登录类型（0-登录，1-登出）',
  `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '登录状态（0-失败，1-成功）',
  `msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '提示消息',
  `login_time` datetime NOT NULL COMMENT '登录时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除标志（0-未删除，1-已删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_username`(`username` ASC) USING BTREE,
  INDEX `idx_ip_address`(`ip_address` ASC) USING BTREE,
  INDEX `idx_login_time`(`login_time` ASC) USING BTREE,
  INDEX `idx_login_type`(`login_type` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户登录日志表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_login_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `parent_id` bigint NULL DEFAULT NULL COMMENT '父菜单ID',
  `ancestors` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '祖级列表(逗号分隔)',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单名称',
  `type` tinyint NOT NULL COMMENT '菜单类型（1-目录，2-菜单，3-按钮）',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由路径(前端路由或外链URL)',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '组件路径',
  `permission` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `sort_order` int NULL DEFAULT NULL COMMENT '显示顺序',
  `is_visible` int NULL DEFAULT 1 COMMENT '是否可见（0-隐藏，1-显示）',
  `is_enabled` tinyint(1) NULL DEFAULT 1 COMMENT '是否启用（1启用 0禁用）',
  `is_ext` int NULL DEFAULT 0 COMMENT '是否外链（0-否，1-是）',
  `open_mode` int NULL DEFAULT 0 COMMENT '打开方式（0-内嵌，1-新窗口）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新人',
  `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '删除标志（0-未删除，1-已删除）',
  `is_keep_alive` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否页面缓存（0-否，1-是）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_parent_id`(`parent_id` ASC) USING BTREE,
  INDEX `idx_ancestors`(`ancestors` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 150 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 0, '0', '控制台', 1, '/admin/index', 'admin/index', '', 'DataBoard', 1, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:02', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (3, 0, '0', '系统管理', 1, NULL, NULL, NULL, 'Setting', 2, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:02', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (4, 0, '0', '系统监控', 1, NULL, NULL, NULL, 'Monitor', 6, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:02', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (5, 0, '0', '日志管理', 1, NULL, NULL, '', 'Tickets', 9, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:02', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (6, 0, '0', '系统工具', 1, NULL, NULL, NULL, 'SetUp', 4, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:02', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (7, 0, '0', '外部链接', 1, NULL, NULL, NULL, 'Link', 10, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:02', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (49, 3, '0,3', '用户管理', 2, '/admin/system/user', 'system/user/index', 'sys:user:page,sys:post:list,sys:dept:tree,sys:role:list', 'User', 1, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:03', 'admin', 'admin', 0, 0);
INSERT INTO `sys_menu` VALUES (50, 3, '0,3', '部门管理', 2, '/admin/system/dept', 'system/dept/index', 'sys:dept:page', 'OfficeBuilding', 2, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (51, 3, '0,3', '岗位管理', 2, '/admin/system/post', 'system/post/index', 'sys:post:page', 'document', 3, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (52, 3, '0,3', '角色管理', 2, '/admin/system/role', 'system/role/index', 'sys:role:page', 'UserFilled', 4, 1, 1, 0, 0, '2025-06-21 09:29:22', '2026-01-05 20:03:12', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (53, 3, '0,3', '菜单管理', 2, '/admin/system/menu', 'system/menu/index', 'sys:menu:tree', 'Menu', 5, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (54, 3, '0,3', '字典管理', 2, '/admin/system/dict', 'system/dict/index', 'sys:dict-type:page,sys:dict-type:data', 'DocumentCopy', 6, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (55, 3, '0,3', '系统配置', 2, '/admin/system/config', 'system/config/index', 'sys:config:page', 'Operation', 7, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (59, 4, '0,4', '在线用户', 2, '/admin/monitor/online', 'monitor/online/index', 'sys:session:page', 'Connection', 3, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (60, 4, '0,4', 'SQL监控', 2, 'http://106.54.167.194/api/druid/login.html', 'monitor/sql/index', '', 'DataLine', 4, 1, 1, 1, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (61, 5, '0,5', '操作日志', 2, '/admin/log/operlog', 'log/operlog/index', 'sys:operlog:page', 'Document', 1, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (62, 5, '0,5', '登录日志', 2, '/admin/log/loginlog', 'log/loginlog/index', 'sys:loginlog:page', 'TrendCharts', 2, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (63, 6, '0,6', '文件管理', 2, '/admin/tool/file', 'tool/file/index', '', 'files', 1, 1, 1, 0, 0, '2025-06-21 09:29:22', '2026-01-05 19:56:13', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (65, 7, '0,7', 'Gitee', 2, 'https://gitee.com/leven2018/lanjii', NULL, '', 'QuestionFilled', 1, 1, 1, 1, 1, '2025-06-21 09:29:22', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (66, 7, '0,7', 'Element Plus', 2, 'https://element-plus.org/', NULL, '', 'ElementPlus', 2, 1, 1, 1, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (69, 49, '3,49', '新增', 3, '', '', 'sys:user:save', '', 1, 1, 1, 0, 0, '2025-09-27 13:21:00', '2026-01-05 20:01:43', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (70, 49, '3,49', '编辑', 3, '', '', 'sys:user:update', '', 2, 1, 1, 0, 0, '2025-09-27 13:21:38', '2026-01-05 20:01:43', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (71, 49, '3,49', '删除', 3, '', '', 'sys:user:delete', '', 3, 1, 1, 0, 0, '2025-09-27 13:22:16', '2026-01-05 20:01:43', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (72, 49, '3,49', '删除', 3, '', '', 'sys:user:delete', '', 4, 1, 1, 0, 0, '2025-09-27 13:25:39', '2026-01-05 20:01:43', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (73, 49, '3,49', '查看', 3, '', '', 'sys:user:view', '', 0, 1, 1, 0, 0, '2025-09-27 13:54:17', '2026-01-05 20:01:43', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (74, 49, '3,49', '导出', 3, '', '', 'sys:user:export', '', 5, 1, 1, 0, 0, '2025-09-27 14:37:32', '2026-01-05 20:01:43', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (75, 49, '3,49', '状态切换', 3, '', '', 'sys:user:status', '', 7, 1, 1, 0, 0, '2025-09-27 15:37:14', '2026-01-05 20:01:43', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (76, 49, '3,49', '重置密码', 3, '', '', 'sys:user:reset-pwd', '', 0, 1, 1, 0, 0, '2025-09-27 15:38:43', '2026-01-05 20:01:43', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (77, 50, '3,50', '新增', 3, '', '', 'sys:dept:save', '', 1, 1, 1, 0, 0, '2025-09-27 16:00:00', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (78, 50, '3,50', '编辑', 3, '', '', 'sys:dept:update', '', 2, 1, 1, 0, 0, '2025-09-27 16:00:00', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (79, 50, '3,50', '删除', 3, '', '', 'sys:dept:delete', '', 3, 1, 1, 0, 0, '2025-09-27 16:00:00', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (80, 51, '3,51', '新增', 3, '', '', 'sys:post:save', '', 1, 1, 1, 0, 0, '2025-09-27 16:00:00', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (81, 51, '3,51', '编辑', 3, '', '', 'sys:post:update', '', 2, 1, 1, 0, 0, '2025-09-27 16:00:00', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (82, 51, '3,51', '删除', 3, '', '', 'sys:post:delete', '', 3, 1, 1, 0, 0, '2025-09-27 16:00:00', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (83, 52, '3,52', '新增', 3, '', '', 'sys:role:save', '', 1, 1, 1, 0, 0, '2025-09-27 16:00:00', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (84, 52, '3,52', '编辑', 3, '', '', 'sys:role:update', '', 2, 1, 1, 0, 0, '2025-09-27 16:00:00', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (85, 52, '3,52', '删除', 3, '', '', 'sys:role:delete', '', 3, 1, 1, 0, 0, '2025-09-27 16:00:00', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (86, 50, '3,50', '查看', 3, '', '', 'sys:dept:view', '', 0, 1, 1, 0, 0, '2025-10-09 20:12:55', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (87, 51, '3,51', '查看', 3, '', '', 'sys:post:view', '', 0, 1, 1, 0, 0, '2025-10-09 20:14:04', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (88, 54, '3,54', '新增', 3, '', '', 'sys:dict-type:save', '', 1, 1, 1, 0, 0, '2025-10-09 20:18:02', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (89, 54, '3,54', '编辑', 3, '', '', 'sys:dict-type:update', '', 2, 1, 1, 0, 0, '2025-10-09 20:18:02', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (90, 54, '3,54', '删除', 3, '', '', 'sys:dict-type:delete', '', 3, 1, 1, 0, 0, '2025-10-09 20:18:02', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (91, 54, '3,54', '查看', 3, '', '', 'sys:dict-type:view', '', 0, 1, 1, 0, 0, '2025-10-09 20:18:02', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (92, 55, '3,55', '新增', 3, '', '', 'sys:config:save', '', 1, 1, 1, 0, 0, '2025-10-09 20:35:35', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (93, 55, '3,55', '编辑', 3, '', '', 'sys:config:update', '', 2, 1, 1, 0, 0, '2025-10-09 20:35:35', '2026-01-05 20:04:34', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (94, 55, '3,55', '删除', 3, '', '', 'sys:config:delete', '', 3, 1, 1, 0, 0, '2025-10-09 20:35:35', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (95, 55, '3,55', '查看', 3, '', '', 'sys:config:view', '', 0, 1, 1, 0, 0, '2025-10-09 20:35:35', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (96, 55, '3,55', '清除缓存', 3, '', '', 'sys:config:cache', '', 9, 1, 1, 0, 0, '2025-10-09 20:35:35', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (97, 112, '0,3,112', '新增', 3, '', '', 'sys:dict-data:save', '', 1, 1, 1, 0, 0, '2025-10-09 20:49:06', '2026-01-05 20:15:17', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (98, 112, '0,3,112', '编辑', 3, '', '', 'sys:dict-data:update', NULL, 2, 1, 1, 0, 0, '2025-10-09 20:49:28', '2026-01-05 20:15:18', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (100, 112, '0,3,112', '删除', 3, '', '', 'sys:dict-data:delete', '', 3, 1, 1, 0, 0, '2025-10-09 20:50:22', '2026-01-05 20:15:18', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (101, 53, '3,53', '删除', 3, '', '', 'sys:menu:delete', '', NULL, 1, 1, 0, 0, '2025-11-10 19:48:19', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (102, 52, '3,52', '分配权限', 3, '', '', 'sys:role:permission', '', NULL, 1, 1, 0, 0, '2025-11-11 22:15:46', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (103, 53, '3,53', '新增', 3, '', '', 'sys:menu:save', '', 1, 1, 1, 0, 0, '2025-11-13 18:26:00', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (104, 53, '3,53', '编辑', 3, '', '', 'sys:menu:update', '', 0, 1, 1, 0, 0, '2025-11-13 18:26:23', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (105, 53, '3,53', '查看', 3, '', '', 'sys:menu:view', '', 4, 1, 1, 0, 0, '2025-11-13 18:27:00', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (106, 62, '5,62', '清空', 3, '', '', 'sys:loginlog:clean', '', 0, 1, 1, 0, 0, '2025-11-13 22:03:47', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (108, 61, '5,61', '清空', 3, '', '', 'sys:operlog:clean', '', 0, 1, 1, 0, 0, '2025-11-13 22:04:48', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (109, 62, '5,62', '查看', 3, '', '', 'sys:loginlog:view', '', 0, 1, 1, 0, 0, '2025-11-13 22:30:21', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (110, 61, '5,61', '查看', 3, '', '', 'sys:operlog:view', '', 0, 1, 1, 0, 0, '2025-11-13 22:30:47', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (111, 52, '3,52', '查看', 3, '', '', 'sys:role:view', '', 0, 1, 1, 0, 0, '2025-11-13 23:42:58', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (112, 3, '0,3', '字典数据', 2, '/admin/system/dict/data', 'system/dict/data/index', 'sys:dict-data:page', 'Management', 6, 0, 1, 0, 0, '2025-11-14 10:23:45', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (113, 59, '4,59', '踢出', 3, '', '', 'sys:session:kick', '', 1, 1, 1, 0, 0, '2025-11-14 22:39:12', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (114, 0, '0', '通知公告', 1, '', '', '', 'Bell', 3, 1, 1, 0, 0, '2025-11-27 20:15:11', '2025-11-27 20:15:11', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (115, 114, '0,114', '通知公告', 2, '/admin/notice', 'notice/index', 'notice:page', 'Tickets', 0, 1, 1, 0, 0, '2025-11-27 20:18:33', '2025-11-27 20:18:33', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (116, 114, '0,114', '发布', 2, '/admin/notice/publish', 'notice/publish', 'notice:publish', 'Promotion', 0, 1, 1, 0, 0, '2025-11-28 11:33:00', '2025-11-28 11:33:00', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (117, 0, '0', 'AI', 1, '', '', '', 'ChatDotRound', 100, 1, 1, 0, 0, '2025-12-22 17:11:58', '2025-12-22 17:11:58', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (118, 117, '0,117', 'AI 聊天', 2, '/admin/ai-chat', 'ai/chat/index', 'ai:chats:stream', 'ChatLineRound', 1, 1, 1, 0, 0, '2025-12-22 17:14:35', '2026-01-05 20:02:12', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (119, 117, '0,117', '数据库知识库', 2, '/admin/ai/knowledge', 'ai/knowledge/index', 'ai:knowledge:page', 'Notebook', NULL, 1, 1, 0, 0, '2025-12-25 15:03:17', '2026-01-05 19:54:03', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (120, 119, '0,117,119', '新增', 3, '', '', 'ai:knowledge:save', '', NULL, 1, 1, 0, 0, '2025-12-25 15:30:50', '2025-12-25 15:30:50', 'admin', 'admin', 0, 0);
INSERT INTO `sys_menu` VALUES (121, 119, '0,117,119', '编辑', 3, '', '', 'ai:knowledge:update', '', NULL, 1, 1, 0, 0, '2025-12-25 15:31:19', '2025-12-25 15:31:19', 'admin', 'admin', 0, 0);
INSERT INTO `sys_menu` VALUES (122, 119, '0,117,119', '删除', 3, '', '', 'ai:knowledge:delete', '', NULL, 1, 1, 0, 0, '2025-12-25 15:31:58', '2025-12-25 15:31:58', 'admin', 'admin', 0, 0);
INSERT INTO `sys_menu` VALUES (123, 119, '0,117,119', '查看', 3, '', '', 'ai:knowledge:view', '', NULL, 1, 1, 0, 0, '2025-12-25 15:32:19', '2025-12-25 15:32:19', 'admin', 'admin', 0, 0);
INSERT INTO `sys_menu` VALUES (127, 117, '0,117', '模型配置', 2, '/admin/ai/model-config', 'ai/model-config/index', 'ai:model-config:page', 'Cpu', 2, 1, 1, 0, 0, '2025-12-30 00:00:00', '2025-12-30 00:00:00', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (128, 127, '0,117,127', '新增', 3, '', '', 'ai:model-config:save', '', NULL, 1, 1, 0, 0, '2025-12-30 00:00:00', '2025-12-30 00:00:00', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (129, 127, '0,117,127', '编辑', 3, '', '', 'ai:model-config:update', '', NULL, 1, 1, 0, 0, '2025-12-30 00:00:00', '2025-12-30 00:00:00', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (130, 127, '0,117,127', '删除', 3, '', '', 'ai:model-config:delete', '', NULL, 1, 1, 0, 0, '2025-12-30 00:00:00', '2025-12-30 00:00:00', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (131, 127, '0,117,127', '查看', 3, '', '', 'ai:model-config:view', '', NULL, 1, 1, 0, 0, '2025-12-30 00:00:00', '2025-12-30 00:00:00', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (132, 127, '0,117,127', '清空', 3, '', '', 'ai:model-config:clear', '', NULL, 1, 1, 0, 0, '2025-12-30 00:00:00', '2025-12-30 00:00:00', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (134, 127, '0,117,127', '设为默认', 3, '', '', 'ai:model-config:default', '', 6, 1, 1, 0, 0, '2025-12-30 00:00:00', '2026-01-04 17:26:40', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (135, 117, '0,117', '角色与提示词', 2, '/admin/ai/role-prompt', 'ai/role-prompt/index', 'ai:role-prompt:page', 'ChatLineRound', 3, 1, 1, 0, 0, '2025-12-30 00:00:00', '2025-12-30 00:00:00', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (136, 135, '0,117,135', '查看', 3, '', '', 'ai:role-prompt:view', '', 0, 1, 1, 0, 0, '2025-12-30 00:00:00', '2025-12-30 00:00:00', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (137, 135, '0,117,135', '新增', 3, '', '', 'ai:role-prompt:save', '', 1, 1, 1, 0, 0, '2025-12-30 00:00:00', '2025-12-30 00:00:00', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (138, 135, '0,117,135', '编辑', 3, '', '', 'ai:role-prompt:update', '', 2, 1, 1, 0, 0, '2025-12-30 00:00:00', '2025-12-30 00:00:00', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (139, 135, '0,117,135', '删除', 3, '', '', 'ai:role-prompt:delete', '', 3, 1, 1, 0, 0, '2025-12-30 00:00:00', '2025-12-30 00:00:00', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (140, 135, '0,117,135', '状态切换', 3, '', '', 'ai:role-prompt:toggle', '', 4, 1, 1, 0, 0, '2025-12-30 00:00:00', '2025-12-30 00:00:00', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (142, 117, '0,117', '元数据', 2, '/admin/ai/metadata-config', 'ai/metadata-config/index', 'ai:metadata-field:page', 'CollectionTag', 4, 1, 1, 0, 0, '2025-12-30 00:00:00', '2026-01-05 19:50:13', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (143, 142, '0,117,142', '查看', 3, '', '', 'ai:metadata-field:view', '', 0, 1, 1, 0, 0, '2025-12-30 00:00:00', '2025-12-30 00:00:00', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (144, 142, '0,117,142', '新增', 3, '', '', 'ai:metadata-field:save', '', 1, 1, 1, 0, 0, '2025-12-30 00:00:00', '2025-12-30 00:00:00', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (145, 142, '0,117,142', '编辑', 3, '', '', 'ai:metadata-field:update', '', 2, 1, 1, 0, 0, '2025-12-30 00:00:00', '2025-12-30 00:00:00', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (146, 142, '0,117,142', '删除', 3, '', '', 'ai:metadata-field:delete', '', 3, 1, 1, 0, 0, '2025-12-30 00:00:00', '2025-12-30 00:00:00', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (148, 127, '0,117,127', '切换启用', 3, '', '', 'ai:model-config:toggle', '', 5, 1, 1, 0, 0, '2026-01-04 17:26:40', '2026-01-04 17:26:40', 'admin', 'admin', 0, 1);
INSERT INTO `sys_menu` VALUES (149, 119, '0,117,119', '刷新向量', 3, '', '', 'ai:knowledge:rebuild', '', 5, 1, 1, 0, 0, '2026-01-04 21:54:40', '2026-01-04 21:54:40', 'admin', 'admin', 0, 0);

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '通知ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '通知标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '通知内容（富文本HTML）',
  `level` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'normal' COMMENT '级别：normal-普通, important-重要, urgent-紧急',
  `publisher_id` bigint NOT NULL COMMENT '发布人ID',
  `publisher_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '发布人姓名（冗余字段）',
  `publish_time` datetime NULL DEFAULT NULL COMMENT '发布时间',
  `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '状态：0-草稿, 1-已发布, 2-已撤回',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除, 1-已删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_publish_time`(`publish_time` ASC) USING BTREE,
  INDEX `idx_level`(`level` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE,
  INDEX `idx_is_deleted`(`is_deleted` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统通知公告表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_notice
-- ----------------------------

-- ----------------------------
-- Table structure for sys_notice_read_record
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice_read_record`;
CREATE TABLE `sys_notice_read_record`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `notice_id` bigint NOT NULL COMMENT '通知ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `read_time` datetime NOT NULL COMMENT '阅读时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_notice_user`(`notice_id` ASC, `user_id` ASC) USING BTREE COMMENT '通知+用户唯一索引',
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE,
  INDEX `idx_notice_id`(`notice_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '通知阅读记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_notice_read_record
-- ----------------------------

-- ----------------------------
-- Table structure for sys_oper_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_oper_log`;
CREATE TABLE `sys_oper_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作模块',
  `business_type` tinyint(1) NOT NULL DEFAULT 0 COMMENT '业务类型（0-新增，1-修改，2-删除）',
  `method` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '方法名称',
  `request_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求方式',
  `oper_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作人员',
  `dept_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '部门名称',
  `oper_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求URL',
  `oper_param` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '请求参数',
  `json_result` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '返回参数',
  `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '操作状态（0-失败，1-成功）',
  `error_msg` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '错误消息',
  `oper_time` datetime NOT NULL COMMENT '操作时间',
  `cost_time` bigint NULL DEFAULT NULL COMMENT '消耗时间（毫秒）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除标志（0-未删除，1-已删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_oper_name`(`oper_name` ASC) USING BTREE,
  INDEX `idx_business_type`(`business_type` ASC) USING BTREE,
  INDEX `idx_oper_time`(`oper_time` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE,
  INDEX `idx_title`(`title` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户操作日志表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_oper_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '岗位名称',
  `sort_order` int NULL DEFAULT NULL COMMENT '显示顺序',
  `is_enabled` tinyint(1) NULL DEFAULT 1 COMMENT '是否启用（1启用 0禁用）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建人',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新人',
  `deleted` tinyint NULL DEFAULT 0 COMMENT '删除标志(0未删除 1已删除)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_post_code`(`post_code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统岗位表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_post
-- ----------------------------
INSERT INTO `sys_post` VALUES (1, 'ceo', '董事长', 1, 0, '公司最高管理者', '2023-01-01 10:00:00', '2025-09-18 23:07:28', 'admin', 'admin', 0);
INSERT INTO `sys_post` VALUES (2, 'manager', '总经理', 2, 1, '公司日常管理者', '2023-01-01 10:05:00', '2023-01-15 14:30:00', 'admin', 'admin', 0);
INSERT INTO `sys_post` VALUES (3, 'hr', '人力资源', 3, 1, '负责招聘、培训等人事工作', '2023-01-02 09:00:00', '2023-02-10 16:45:00', 'admin', 'hr_user', 0);
INSERT INTO `sys_post` VALUES (4, 'finance', '财务', 4, 1, '负责公司财务管理', '2023-01-02 09:30:00', '2023-03-05 11:20:00', 'admin', 'finance_user', 0);
INSERT INTO `sys_post` VALUES (5, 'dev', '开发', 5, 1, '负责软件开发', '2023-01-03 08:45:00', '2023-04-18 09:15:00', 'admin', 'tech_lead', 0);
INSERT INTO `sys_post` VALUES (6, 'test', '测试', 6, 1, '负责软件测试', '2023-01-03 09:15:00', '2023-05-22 13:40:00', 'admin', 'qa_lead', 0);
INSERT INTO `sys_post` VALUES (7, 'sales', '销售', 7, 1, '负责产品销售', '2023-01-04 10:00:00', '2023-06-30 15:10:00', 'admin', 'sales_manager', 0);
INSERT INTO `sys_post` VALUES (8, 'support', '客服', 8, 1, '负责客户支持', '2023-01-05 11:00:00', '2023-07-12 10:25:00', 'admin', 'support_lead', 0);
INSERT INTO `sys_post` VALUES (9, 'design', '设计', 9, 1, '负责UI/UX设计', '2023-01-06 14:00:00', '2023-08-08 16:50:00', 'admin', 'design_lead', 0);
INSERT INTO `sys_post` VALUES (10, 'intern', '实习生', 10, 1, '实习岗位', '2023-01-07 15:00:00', '2023-09-01 14:15:00', 'admin', 'hr_user', 0);
INSERT INTO `sys_post` VALUES (11, 'old_post', '旧岗位', 11, 0, '已停用的旧岗位', '2022-12-01 09:00:00', '2023-01-10 17:00:00', 'old_admin', 'admin', 0);
INSERT INTO `sys_post` VALUES (12, 'deleted_post', '已删除岗位', 12, 1, '已删除的测试岗位', '2023-01-08 10:00:00', '2023-01-09 11:30:00', 'admin', 'admin', 1);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色编码',
  `sort_order` int NULL DEFAULT NULL COMMENT '显示顺序',
  `is_enabled` tinyint(1) NULL DEFAULT 1 COMMENT '是否启用（1启用 0禁用）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新人',
  `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '删除标志（0-未删除，1-已删除）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_code`(`code` ASC) USING BTREE,
  INDEX `idx_name`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '系统管理员', 'admin', 2, 1, '管理系统基础配置', '2025-06-21 09:31:38', '2025-12-24 15:39:05', 'system', 'system', 0);
INSERT INTO `sys_role` VALUES (3, '安全管理员', 'security_admin', 3, 1, '负责系统安全管理', '2025-06-21 09:31:38', '2025-06-21 09:31:38', 'system', 'system', 0);
INSERT INTO `sys_role` VALUES (4, '部门经理', 'dept_manager', 10, 1, '管理部门内事务', '2025-06-21 09:31:38', '2025-06-21 09:31:38', 'admin', 'admin', 0);
INSERT INTO `sys_role` VALUES (5, '项目负责人', 'project_leader', 11, 1, '负责项目管理', '2025-06-21 09:31:38', '2025-06-21 09:31:38', 'admin', 'admin', 0);
INSERT INTO `sys_role` VALUES (6, '开发工程师', 'dev_engineer', 12, 1, '系统开发岗位', '2025-06-21 09:31:38', '2025-06-21 09:31:38', 'admin', 'admin', 0);
INSERT INTO `sys_role` VALUES (7, '测试工程师', 'test_engineer', 13, 1, '系统测试岗位', '2025-06-21 09:31:38', '2025-06-21 09:31:38', 'admin', 'admin', 0);
INSERT INTO `sys_role` VALUES (8, '产品经理', 'product_manager', 14, 1, '负责产品设计', '2025-06-21 09:31:38', '2025-06-21 09:31:38', 'admin', 'admin', 0);
INSERT INTO `sys_role` VALUES (9, 'UI设计师', 'ui_designer', 15, 1, '负责界面设计', '2025-06-21 09:31:38', '2025-06-21 09:31:38', 'admin', 'admin', 0);
INSERT INTO `sys_role` VALUES (10, '已禁用角色', 'disabled_role', 90, 0, '测试禁用状态', '2025-06-21 09:31:38', '2025-06-21 09:31:38', 'admin', 'admin', 0);
INSERT INTO `sys_role` VALUES (11, '待审核角色', 'pending_role', 91, 1, '等待审核的角色', '2025-06-21 09:31:38', '2025-06-21 09:31:38', 'admin', 'admin', 0);
INSERT INTO `sys_role` VALUES (12, '临时角色', 'temp_role', 92, 1, '临时使用的角色', '2025-06-21 09:31:38', '2025-06-21 09:31:38', 'admin', 'admin', 0);
INSERT INTO `sys_role` VALUES (13, '归档角色', 'archived_role', 99, 1, '已归档的旧角色', '2025-06-21 09:31:38', '2025-06-21 09:31:38', 'admin', 'admin', 1);
INSERT INTO `sys_role` VALUES (14, '废弃角色', 'deprecated_role', 100, 0, '不再使用的角色', '2025-06-21 09:31:38', '2025-06-21 09:31:38', 'admin', 'admin', 1);
INSERT INTO `sys_role` VALUES (15, '财务审批员', 'finance_approver', 20, 1, NULL, '2025-06-21 09:31:38', '2025-06-21 09:31:38', 'admin', 'admin', 0);
INSERT INTO `sys_role` VALUES (16, '人事专员', 'hr_specialist', 21, 1, NULL, '2025-06-21 09:31:38', '2025-06-21 09:31:38', 'admin', 'admin', 0);
INSERT INTO `sys_role` VALUES (17, '客服主管', 'cs_manager', 22, 1, NULL, '2025-06-21 09:31:38', '2025-06-21 09:31:38', 'admin', 'admin', 0);
INSERT INTO `sys_role` VALUES (18, '运维工程师', 'ops_engineer', 23, 1, NULL, '2025-06-21 09:31:38', '2025-06-21 09:31:38', 'admin', 'admin', 0);
INSERT INTO `sys_role` VALUES (19, '数据分析师', 'data_analyst', 24, 1, NULL, '2025-06-21 09:31:38', '2025-06-21 09:31:38', 'admin', 'admin', 0);
INSERT INTO `sys_role` VALUES (20, '市场专员', 'marketing_specialist', 25, 1, NULL, '2025-06-21 09:31:38', '2025-06-21 09:31:38', 'admin', 'admin', 0);
INSERT INTO `sys_role` VALUES (21, '销售代表', 'sales_rep', 26, 1, NULL, '2025-06-21 09:31:38', '2025-06-21 09:31:38', 'admin', 'admin', 0);
INSERT INTO `sys_role` VALUES (22, '采购专员', 'purchasing_specialist', 27, 1, NULL, '2025-06-21 09:31:38', '2025-06-21 09:31:38', 'admin', 'admin', 0);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_role_menu`(`role_id` ASC, `menu_id` ASC) USING BTREE,
  INDEX `idx_menu_id`(`menu_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 167 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色菜单关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (165, 3, 3);
INSERT INTO `sys_role_menu` VALUES (153, 3, 4);
INSERT INTO `sys_role_menu` VALUES (158, 3, 5);
INSERT INTO `sys_role_menu` VALUES (166, 3, 49);
INSERT INTO `sys_role_menu` VALUES (117, 3, 50);
INSERT INTO `sys_role_menu` VALUES (122, 3, 51);
INSERT INTO `sys_role_menu` VALUES (127, 3, 52);
INSERT INTO `sys_role_menu` VALUES (133, 3, 53);
INSERT INTO `sys_role_menu` VALUES (138, 3, 54);
INSERT INTO `sys_role_menu` VALUES (146, 3, 55);
INSERT INTO `sys_role_menu` VALUES (154, 3, 57);
INSERT INTO `sys_role_menu` VALUES (155, 3, 59);
INSERT INTO `sys_role_menu` VALUES (157, 3, 60);
INSERT INTO `sys_role_menu` VALUES (159, 3, 61);
INSERT INTO `sys_role_menu` VALUES (162, 3, 62);
INSERT INTO `sys_role_menu` VALUES (116, 3, 74);
INSERT INTO `sys_role_menu` VALUES (118, 3, 77);
INSERT INTO `sys_role_menu` VALUES (119, 3, 78);
INSERT INTO `sys_role_menu` VALUES (120, 3, 79);
INSERT INTO `sys_role_menu` VALUES (123, 3, 80);
INSERT INTO `sys_role_menu` VALUES (124, 3, 81);
INSERT INTO `sys_role_menu` VALUES (125, 3, 82);
INSERT INTO `sys_role_menu` VALUES (128, 3, 83);
INSERT INTO `sys_role_menu` VALUES (129, 3, 84);
INSERT INTO `sys_role_menu` VALUES (130, 3, 85);
INSERT INTO `sys_role_menu` VALUES (121, 3, 86);
INSERT INTO `sys_role_menu` VALUES (126, 3, 87);
INSERT INTO `sys_role_menu` VALUES (139, 3, 88);
INSERT INTO `sys_role_menu` VALUES (140, 3, 89);
INSERT INTO `sys_role_menu` VALUES (141, 3, 90);
INSERT INTO `sys_role_menu` VALUES (142, 3, 91);
INSERT INTO `sys_role_menu` VALUES (147, 3, 92);
INSERT INTO `sys_role_menu` VALUES (148, 3, 93);
INSERT INTO `sys_role_menu` VALUES (149, 3, 94);
INSERT INTO `sys_role_menu` VALUES (150, 3, 95);
INSERT INTO `sys_role_menu` VALUES (151, 3, 96);
INSERT INTO `sys_role_menu` VALUES (143, 3, 97);
INSERT INTO `sys_role_menu` VALUES (144, 3, 98);
INSERT INTO `sys_role_menu` VALUES (145, 3, 100);
INSERT INTO `sys_role_menu` VALUES (134, 3, 101);
INSERT INTO `sys_role_menu` VALUES (131, 3, 102);
INSERT INTO `sys_role_menu` VALUES (135, 3, 103);
INSERT INTO `sys_role_menu` VALUES (136, 3, 104);
INSERT INTO `sys_role_menu` VALUES (137, 3, 105);
INSERT INTO `sys_role_menu` VALUES (163, 3, 106);
INSERT INTO `sys_role_menu` VALUES (160, 3, 108);
INSERT INTO `sys_role_menu` VALUES (164, 3, 109);
INSERT INTO `sys_role_menu` VALUES (161, 3, 110);
INSERT INTO `sys_role_menu` VALUES (132, 3, 111);
INSERT INTO `sys_role_menu` VALUES (152, 3, 112);
INSERT INTO `sys_role_menu` VALUES (156, 3, 113);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '昵称',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像URL',
  `is_enabled` tinyint(1) NULL DEFAULT 1 COMMENT '是否启用（1启用 0禁用）',
  `dept_id` bigint NOT NULL COMMENT '所属部门 ID',
  `last_login_time` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '最后登录IP',
  `is_admin` tinyint(1) UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '是否系统管理员（0-否，1-是）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新人',
  `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '删除标志（0-未删除，1-已删除）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_username`(`username` ASC) USING BTREE,
  INDEX `idx_email`(`email` ASC) USING BTREE,
  INDEX `idx_phone`(`phone` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '$2a$10$Ke7.fLoqfUyIcVPYTR5GXeLi.Bj4G/IyuJRZXaqZffJCuSGHEQ78K', '系统管理员', 'admin@example.com', '13800021113', 'http://106.54.167.194//upload/20251208/82301499-2a90-4b2b-be41-9269980091e6.jpg', 1, 1, '2026-01-05 20:15:29', '127.0.0.1', 1, '2025-06-21 09:20:10', '2026-01-05 20:15:29', 'system', 'admin', 0);
INSERT INTO `sys_user` VALUES (2, 'zhangsan', '$2a$10$U6g80GGnt.g4vxWSh3/0f.Qy2lCNqo3R1R/97MdSJgvPa0.Ci7vbe', '张三', 'zhangsan@company.com', '13900001111', 'https://example.com/avatars/zhangsan.jpg', 1, 1, '2023-06-16 09:15:00', '192.168.1.101', NULL, '2025-06-21 09:20:10', '2025-07-02 13:11:39', 'admin', 'admin', 0);
INSERT INTO `sys_user` VALUES (3, 'lisi', '$2a$10$DSBCdR7PS0BSrEXrJfkEz.DKEwPbVbzxf84Y6izM.ptRbRugLreWi', '李四', 'lisi@company.com', '13900002222', 'https://example.com/avatars/lisi.jpg', 1, 2, '2023-06-16 10:20:00', '192.168.1.102', NULL, '2025-06-21 09:20:10', '2025-09-18 22:02:11', 'admin', 'admin', 0);
INSERT INTO `sys_user` VALUES (4, 'wangwu', '$2a$10$U6g80GGnt.g4vxWSh3/0f.Qy2lCNqo3R1R/97MdSJgvPa0.Ci7vbe', '王五', 'wangwu@company.com', '13900003333', 'https://example.com/avatars/wangwu.jpg', 1, 3, '2023-06-16 11:30:00', '192.168.1.103', NULL, '2025-06-21 09:20:10', '2025-07-02 13:11:42', 'admin', 'admin', 0);
INSERT INTO `sys_user` VALUES (5, 'zhaoliu', '$2a$10$U6g80GGnt.g4vxWSh3/0f.Qy2lCNqo3R1R/97MdSJgvPa0.Ci7vbe', '赵六', 'zhaoliu@company.com', '13900004444', 'https://example.com/avatars/zhaoliu.jpg', 1, 4, '2023-06-16 14:45:00', '192.168.1.104', NULL, '2025-06-21 09:20:10', '2025-07-02 13:11:44', 'admin', 'admin', 0);
INSERT INTO `sys_user` VALUES (6, 'dev_manager', '$2a$10$U6g80GGnt.g4vxWSh3/0f.Qy2lCNqo3R1R/97MdSJgvPa0.Ci7vbe', '张开发', 'dev_manager@company.com', '13900005555', 'https://example.com/avatars/dev_manager.jpg', 1, 0, '2023-06-15 13:20:00', '192.168.1.105', NULL, '2025-06-21 09:20:10', '2025-06-21 15:36:54', 'admin', 'admin', 0);
INSERT INTO `sys_user` VALUES (7, 'test_manager', '$2a$10$U6g80GGnt.g4vxWSh3/0f.Qy2lCNqo3R1R/97MdSJgvPa0.Ci7vbe', '李测试', 'test_manager@company.com', '13900006666', 'https://example.com/avatars/test_manager.jpg', 1, 0, '2023-06-15 14:30:00', '192.168.1.106', NULL, '2025-06-21 09:20:10', '2025-06-21 15:36:54', 'admin', 'admin', 0);
INSERT INTO `sys_user` VALUES (8, 'product_manager', '$2a$10$U6g80GGnt.g4vxWSh3/0f.Qy2lCNqo3R1R/97MdSJgvPa0.Ci7vbe', '王产品', 'product_manager@company.com', '13900007777', 'https://example.com/avatars/product_manager.jpg', 1, 0, '2023-06-15 15:40:00', '192.168.1.107', NULL, '2025-06-21 09:20:10', '2025-06-21 15:36:54', 'admin', 'admin', 0);
INSERT INTO `sys_user` VALUES (9, 'disabled_user', '$2a$10$U6g80GGnt.g4vxWSh3/0f.Qy2lCNqo3R1R/97MdSJgvPa0.Ci7vbe', '禁用用户', 'disabled@example.com', '13900008888', 'https://example.com/avatars/disabled.jpg', 0, 0, '2023-05-20 16:50:00', '192.168.1.108', NULL, '2025-06-21 09:20:10', '2025-06-21 15:36:54', 'admin', 'admin', 0);
INSERT INTO `sys_user` VALUES (10, 'deleted_user', '$2a$10$U6g80GGnt.g4vxWSh3/0f.Qy2lCNqo3R1R/97MdSJgvPa0.Ci7vbe', '已删除用户', 'deleted@example.com', '13900009999', 'https://example.com/avatars/deleted.jpg', 1, 0, '2023-05-10 17:00:00', '192.168.1.109', NULL, '2025-06-21 09:20:10', '2025-06-21 15:36:54', 'admin', 'admin', 1);

-- ----------------------------
-- Table structure for sys_user_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_dept`;
CREATE TABLE `sys_user_dept`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `dept_id` bigint NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统用户-部门关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_dept
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `post_id` bigint NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_user_post`(`user_id` ASC, `post_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户-岗位关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------
INSERT INTO `sys_user_post` VALUES (34, 1, 2);
INSERT INTO `sys_user_post` VALUES (33, 27, 2);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_user_role`(`user_id` ASC, `role_id` ASC) USING BTREE,
  INDEX `idx_role_id`(`role_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户角色关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (40, 1, 1);
INSERT INTO `sys_user_role` VALUES (35, 27, 3);
INSERT INTO `sys_user_role` VALUES (36, 28, 1);
INSERT INTO `sys_user_role` VALUES (37, 28, 5);
INSERT INTO `sys_user_role` VALUES (39, 29, 2);

-- ----------------------------
-- Table structure for tool_file
-- ----------------------------
DROP TABLE IF EXISTS `tool_file`;
CREATE TABLE `tool_file`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '文件ID',
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件名称',
  `original_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '原始文件名',
  `file_path` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件路径',
  `file_size` bigint NULL DEFAULT NULL COMMENT '文件大小（字节）',
  `file_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件类型',
  `file_extension` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件扩展名',
  `upload_user_id` bigint NULL DEFAULT NULL COMMENT '上传用户ID',
  `upload_username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '上传用户名',
  `upload_time` datetime NULL DEFAULT NULL COMMENT '上传时间',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件描述',
  `access_count` int NULL DEFAULT 0 COMMENT '访问次数',
  `last_access_time` datetime NULL DEFAULT NULL COMMENT '最后访问时间',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者',
  `deleted` tinyint(1) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_file_type`(`file_type` ASC) USING BTREE,
  INDEX `idx_upload_user`(`upload_user_id` ASC) USING BTREE,
  INDEX `idx_upload_time`(`upload_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统文件管理表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tool_file
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
