/*
 Navicat Premium Dump SQL

 Source Server         : 【腾讯云】106.54.167.194
 Source Server Type    : MySQL
 Source Server Version : 80044 (8.0.44)
 Source Host           : 106.54.167.194:3107
 Source Schema         : lanjii-v3

 Target Server Type    : MySQL
 Target Server Version : 80044 (8.0.44)
 File Encoding         : 65001

 Date: 16/12/2025 10:46:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
INSERT INTO `sys_config` VALUES (26, '静态资源映射路径', 'UPLOAD_ROOT_PATH', '/opt/app/lanjii/upload/', '1', 1, '', 'admin', '2025-11-14 14:22:54', 'admin', '2025-12-05 16:14:53', 0);
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
  `dept_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '部门编码',
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
  UNIQUE INDEX `idx_dept_code`(`dept_code` ASC) USING BTREE,
  INDEX `idx_parent_id`(`parent_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统部门表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1, 0, '', 'XX科技有限公司1', 'D1001', 1, '张总', '13800001111', 'ceo@company.com', 1, '2023-01-01 10:00:00', '2023-01-01 10:00:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (2, 1, '1', '北京分公司', 'D101', 1, '李经理', '13800002222', 'bj@company.com', 1, '2023-01-02 09:00:00', '2023-01-02 09:00:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (3, 1, '1', '上海分公司', 'D102', 2, '王经理', '13800003333', 'sh@company.com', 1, '2023-01-02 09:30:00', '2023-01-02 09:30:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (4, 1, '1', '技术研发中心', 'D200', 3, '赵总监', '13800004444', 'tech@company.com', 1, '2023-01-03 10:00:00', '2023-01-03 10:00:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (5, 1, '1', '市场营销部', 'D300', 4, '钱总监', '13800005555', 'market@company.com', 1, '2023-01-03 11:00:00', '2023-01-03 11:00:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (6, 1, '1', '人力资源部', 'D400', 5, '孙经理', '13800006666', 'hr@company.com', 1, '2023-01-04 09:00:00', '2023-01-04 09:00:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (7, 1, '1', '财务部', 'D500', 6, '周总监', '13800007777', 'finance@company.com', 1, '2023-01-04 10:00:00', '2023-01-04 10:00:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (8, 4, '1,4', '前端开发部', 'D201', 1, '吴主管', '13800008888', 'frontend@company.com', 1, '2023-01-05 09:00:00', '2023-01-05 09:00:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (9, 4, '1,4', '后端开发部', 'D202', 2, '郑主管', '13800009999', 'backend@company.com', 1, '2023-01-05 10:00:00', '2023-01-05 10:00:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (10, 4, '1,4', '测试部', 'D203', 3, '冯主管', '13800010000', 'qa@company.com', 1, '2023-01-05 11:00:00', '2023-01-05 11:00:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (11, 4, '1,4', '产品设计部', 'D204', 4, '陈经理', '13800011111', 'product@company.com', 1, '2023-01-06 09:00:00', '2023-01-06 09:00:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (12, 4, '1,4', '运维部', 'D205', 5, '楚主管', '13800012222', 'devops@company.com', 1, '2023-01-06 10:00:00', '2023-01-06 10:00:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (13, 8, '1,4,8', 'Web开发组', 'D20101', 1, '魏组长', '13800013333', 'web@company.com', 1, '2023-01-10 09:00:00', '2023-01-10 09:00:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (14, 8, '1,4,8', '移动端组', 'D20102', 2, '蒋组长', '13800014444', 'mobile@company.com', 1, '2023-01-10 10:00:00', '2023-01-10 10:00:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (15, 8, '1,4,8', 'UI设计组', 'D20103', 3, '沈组长', '13800015555', 'ui@company.com', 1, '2023-01-10 11:00:00', '2023-01-10 11:00:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (16, 1, '1', '临时项目组', 'D600', 7, '韩经理', '13800016666', 'temp@company.com', 1, '2023-02-01 09:00:00', '2023-02-01 09:00:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (17, 4, '1,4', '已解散部门', 'D206', 6, NULL, NULL, NULL, 0, '2023-01-15 09:00:00', '2023-01-15 09:00:00', 'admin', 'admin', 1);
INSERT INTO `sys_dept` VALUES (18, 0, '', '合作伙伴', 'D700', 8, NULL, NULL, NULL, 1, '2023-02-10 10:00:00', '2023-02-10 10:00:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (19, 5, '1,5', '市场策划部', 'D301', 1, '杨经理', '13800017777', 'plan@company.com', 1, '2023-01-07 09:00:00', '2023-01-07 09:00:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (20, 5, '1,5', '品牌推广部', 'D302', 2, '朱经理', '13800018888', 'brand@company.com', 1, '2023-01-07 10:00:00', '2023-01-07 10:00:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (21, 6, '1,6', '招聘部', 'D401', 1, '秦主管', '13800019999', 'recruit@company.com', 1, '2023-01-08 09:00:00', '2023-01-08 09:00:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (22, 6, '1,6', '培训发展部', 'D402', 2, '许主管', '13800020000', 'training@company.com', 1, '2023-01-08 10:00:00', '2023-01-08 10:00:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (23, 7, '1,7', '会计核算部', 'D501', 1, '何主管', '13800021111', 'accounting@company.com', 1, '2023-01-09 09:00:00', '2023-01-09 09:00:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (24, 7, '1,7', '资金管理部', 'D502', 2, '吕主管', '13800022222', 'treasury@company.com', 1, '2023-01-09 10:00:00', '2023-01-09 10:00:00', 'admin', 'admin', 0);
INSERT INTO `sys_dept` VALUES (25, 1, '', 'XXX', 'XXX', 0, '', '', '', 1, '2025-07-02 15:31:19', '2025-07-02 15:31:44', '', '', 1);
INSERT INTO `sys_dept` VALUES (26, 1, '', 'qw', 'qw', 5, 'qw', 'qw', 'qw', 1, '2025-09-18 21:51:17', '2025-09-18 21:51:17', '', '', 0);
INSERT INTO `sys_dept` VALUES (27, 18, '', '11', 'qwer', NULL, '', '', '', 1, '2025-10-01 18:06:37', '2025-12-05 19:25:24', '', 'admin', 1);
INSERT INTO `sys_dept` VALUES (28, 2, '', 'A11AA22', 'D101001', 2, '2', '2', '2', 0, '2025-11-14 20:30:30', '2025-11-14 20:30:30', 'admin', 'admin', 0);

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
INSERT INTO `sys_dict_data` VALUES (14, NULL, '1', 11, '', NULL, NULL, 0, 1, '', NULL, '2025-10-09 20:53:48', NULL, '2025-10-09 20:53:48', 0);
INSERT INTO `sys_dict_data` VALUES (15, NULL, '1', 1, '', NULL, NULL, 0, 1, '', NULL, '2025-10-09 20:54:04', NULL, '2025-10-09 20:54:04', 0);
INSERT INTO `sys_dict_data` VALUES (16, NULL, '2', 2, '', NULL, NULL, 0, 1, '', NULL, '2025-10-09 20:54:20', NULL, '2025-10-09 20:54:20', 0);
INSERT INTO `sys_dict_data` VALUES (17, NULL, '11', 22, '', NULL, NULL, 0, 1, '', NULL, '2025-10-09 20:59:49', NULL, '2025-10-09 20:59:49', 0);
INSERT INTO `sys_dict_data` VALUES (18, NULL, '33', 33, '', NULL, NULL, 0, 1, '', NULL, '2025-10-09 21:00:33', NULL, '2025-10-09 21:00:33', 0);
INSERT INTO `sys_dict_data` VALUES (19, NULL, '11', 11, '', NULL, NULL, 0, 1, '', 'admin', '2025-10-10 22:55:33', 'admin', '2025-10-10 22:55:33', 0);
INSERT INTO `sys_dict_data` VALUES (20, NULL, '11', 22, '', NULL, NULL, 0, 1, '', 'admin', '2025-10-11 20:05:40', 'admin', '2025-10-11 20:05:40', 0);
INSERT INTO `sys_dict_data` VALUES (21, NULL, '112', 2211, '12', NULL, NULL, 0, 0, '', 'admin', '2025-10-11 20:13:59', 'admin', '2025-10-11 20:15:23', 1);
INSERT INTO `sys_dict_data` VALUES (22, 5, '33', 33, '12', NULL, NULL, 0, 1, '', 'admin', '2025-10-11 20:14:20', 'admin', '2025-10-11 20:20:18', 0);
INSERT INTO `sys_dict_data` VALUES (23, 4, '11', 13, '12', NULL, NULL, 0, 1, '', 'admin', '2025-10-11 20:14:29', 'admin', '2025-10-11 20:20:13', 0);
INSERT INTO `sys_dict_data` VALUES (24, 0, '11', 3, '', NULL, NULL, 0, 1, '', 'admin', '2025-10-12 22:25:47', 'admin', '2025-10-12 22:25:47', 0);
INSERT INTO `sys_dict_data` VALUES (25, 0, '11', 1, '12', NULL, NULL, 0, 1, '', 'admin', '2025-10-12 22:34:09', 'admin', '2025-10-12 22:34:09', 0);
INSERT INTO `sys_dict_data` VALUES (26, 0, '121', 2, '12', NULL, NULL, 0, 1, '', 'admin', '2025-10-12 22:37:48', 'admin', '2025-10-12 22:37:48', 0);
INSERT INTO `sys_dict_data` VALUES (27, 2, '22', 3, 'BUSINESS_TYPE', NULL, NULL, 0, 1, '', 'admin', '2025-11-14 12:51:19', 'admin', '2025-11-14 12:51:19', 0);

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
INSERT INTO `sys_dict_type` VALUES (3, '11', '11', 1, '2', NULL, '2025-09-18 21:56:37', NULL, '2025-09-18 21:56:37', 0);
INSERT INTO `sys_dict_type` VALUES (4, '登录状态', 'LOGIN_STATUS', 1, '用户登录状态字典', NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_dict_type` VALUES (5, '登录类型', 'LOGIN_TYPE', 1, '用户登录类型字典', NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_dict_type` VALUES (6, '操作状态', 'OPER_STATUS', 1, '用户操作状态字典', NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_dict_type` VALUES (7, '业务类型', 'BUSINESS_TYPE', 1, '操作业务类型字典', NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_dict_type` VALUES (10, '11', '11', 1, '', 'admin', '2025-10-10 21:59:30', 'admin', '2025-10-10 22:52:08', 1);
INSERT INTO `sys_dict_type` VALUES (11, '11', '22', 0, '11', 'admin', '2025-10-10 22:06:30', 'admin', '2025-10-10 22:52:06', 1);
INSERT INTO `sys_dict_type` VALUES (12, '12', '12', 1, '', 'admin', '2025-10-11 20:05:31', 'admin', '2025-10-11 20:05:31', 0);

-- ----------------------------
-- Table structure for sys_file_access_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_file_access_log`;
CREATE TABLE `sys_file_access_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '访问日志ID',
  `file_id` bigint NOT NULL COMMENT '文件ID',
  `access_user_id` bigint NULL DEFAULT NULL COMMENT '访问用户ID',
  `access_username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '访问用户名',
  `access_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '访问IP',
  `access_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '访问类型（download-下载，preview-预览）',
  `access_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '访问时间',
  `user_agent` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户代理',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_file_id`(`file_id` ASC) USING BTREE,
  INDEX `idx_access_user`(`access_user_id` ASC) USING BTREE,
  INDEX `idx_access_time`(`access_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文件访问日志表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_file_access_log
-- ----------------------------

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
) ENGINE = InnoDB AUTO_INCREMENT = 611 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户登录日志表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_login_log
-- ----------------------------
INSERT INTO `sys_login_log` VALUES (433, 'anonymousUser', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-11-25 19:05:05', '2025-11-25 19:05:05', '2025-11-25 19:05:05', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (434, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-11-25 19:05:10', '2025-11-25 19:05:10', '2025-11-25 19:05:10', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (435, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-11-25 19:40:12', '2025-11-25 19:40:12', '2025-11-25 19:40:12', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (436, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-11-25 19:40:14', '2025-11-25 19:40:14', '2025-11-25 19:40:14', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (437, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-11-25 20:19:01', '2025-11-25 20:19:01', '2025-11-25 20:19:01', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (438, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-11-25 20:19:03', '2025-11-25 20:19:03', '2025-11-25 20:19:03', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (439, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-11-25 21:16:48', '2025-11-25 21:16:48', '2025-11-25 21:16:48', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (440, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-11-25 21:16:52', '2025-11-25 21:16:52', '2025-11-25 21:16:52', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (441, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-11-25 21:45:21', '2025-11-25 21:45:21', '2025-11-25 21:45:21', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (442, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-11-25 21:45:23', '2025-11-25 21:45:23', '2025-11-25 21:45:23', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (443, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-11-25 22:02:26', '2025-11-25 22:02:26', '2025-11-25 22:02:26', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (444, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-11-25 22:02:29', '2025-11-25 22:02:29', '2025-11-25 22:02:29', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (445, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-11-26 12:46:04', '2025-11-26 12:46:04', '2025-11-26 12:46:04', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (446, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-11-26 12:48:16', '2025-11-26 12:48:16', '2025-11-26 12:48:16', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (447, 'anonymousUser', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-11-26 12:54:04', '2025-11-26 12:54:04', '2025-11-26 12:54:04', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (448, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-11-26 12:54:05', '2025-11-26 12:54:05', '2025-11-26 12:54:05', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (449, 'anonymousUser', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-11-26 20:22:33', '2025-11-26 20:22:33', '2025-11-26 20:22:33', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (450, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-11-26 20:22:36', '2025-11-26 20:22:36', '2025-11-26 20:22:36', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (451, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-11-26 21:37:09', '2025-11-26 21:37:09', '2025-11-26 21:37:09', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (452, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-11-26 21:37:12', '2025-11-26 21:37:12', '2025-11-26 21:37:12', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (453, 'anonymousUser', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-11-27 19:59:06', '2025-11-27 19:59:06', '2025-11-27 19:59:06', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (454, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-11-27 19:59:09', '2025-11-27 19:59:09', '2025-11-27 19:59:09', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (455, 'anonymousUser', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-11-27 20:04:36', '2025-11-27 20:04:36', '2025-11-27 20:04:36', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (456, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-11-27 20:04:40', '2025-11-27 20:04:40', '2025-11-27 20:04:40', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (457, 'anonymousUser', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-11-27 20:09:34', '2025-11-27 20:09:34', '2025-11-27 20:09:34', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (458, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-11-27 20:09:39', '2025-11-27 20:09:39', '2025-11-27 20:09:39', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (459, 'anonymousUser', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-11-27 20:17:40', '2025-11-27 20:17:40', '2025-11-27 20:17:40', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (460, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-11-27 20:17:42', '2025-11-27 20:17:42', '2025-11-27 20:17:42', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (461, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-11-27 20:18:45', '2025-11-27 20:18:45', '2025-11-27 20:18:45', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (462, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-11-27 20:18:47', '2025-11-27 20:18:47', '2025-11-27 20:18:47', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (463, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-11-27 20:19:21', '2025-11-27 20:19:21', '2025-11-27 20:19:21', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (464, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-11-27 20:19:25', '2025-11-27 20:19:25', '2025-11-27 20:19:25', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (465, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-11-27 20:19:38', '2025-11-27 20:19:38', '2025-11-27 20:19:38', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (466, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-11-27 20:24:38', '2025-11-27 20:24:38', '2025-11-27 20:24:38', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (467, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-11-27 20:24:48', '2025-11-27 20:24:48', '2025-11-27 20:24:48', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (468, 'admin', '127.0.0.1', NULL, 'unknown', 'unknown', 0, 1, '登录成功', '2025-11-27 22:12:11', '2025-11-27 22:12:11', '2025-11-27 22:12:11', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (469, 'admin', '127.0.0.1', NULL, 'unknown', 'unknown', 0, 1, '登录成功', '2025-11-27 22:13:07', '2025-11-27 22:13:07', '2025-11-27 22:13:07', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (470, 'admin', '127.0.0.1', NULL, 'unknown', 'unknown', 0, 1, '登录成功', '2025-11-27 22:14:32', '2025-11-27 22:14:32', '2025-11-27 22:14:32', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (471, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-11-27 22:15:09', '2025-11-27 22:15:09', '2025-11-27 22:15:09', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (472, 'anonymousUser', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-11-28 08:45:59', '2025-11-28 08:45:59', '2025-11-28 08:45:59', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (473, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-11-28 08:46:02', '2025-11-28 08:46:02', '2025-11-28 08:46:02', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (474, 'anonymousUser', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-11-28 10:52:42', '2025-11-28 10:52:42', '2025-11-28 10:52:42', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (475, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-11-28 10:52:44', '2025-11-28 10:52:44', '2025-11-28 10:52:44', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (476, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-11-28 11:33:04', '2025-11-28 11:33:04', '2025-11-28 11:33:04', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (477, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-11-28 11:33:07', '2025-11-28 11:33:07', '2025-11-28 11:33:07', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (478, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-11-28 11:33:24', '2025-11-28 11:33:24', '2025-11-28 11:33:24', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (479, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-01 20:32:04', '2025-12-01 20:32:04', '2025-12-01 20:32:04', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (480, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-01 20:41:00', '2025-12-01 20:41:00', '2025-12-01 20:41:00', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (481, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-01 20:41:02', '2025-12-01 20:41:02', '2025-12-01 20:41:02', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (482, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-01 20:43:06', '2025-12-01 20:43:06', '2025-12-01 20:43:06', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (483, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-01 20:43:09', '2025-12-01 20:43:09', '2025-12-01 20:43:09', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (484, 'anonymousUser', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-01 20:44:52', '2025-12-01 20:44:52', '2025-12-01 20:44:52', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (485, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-01 20:44:54', '2025-12-01 20:44:54', '2025-12-01 20:44:54', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (486, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-01 20:49:54', '2025-12-01 20:49:54', '2025-12-01 20:49:54', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (487, 'test', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-01 20:50:00', '2025-12-01 20:50:00', '2025-12-01 20:50:00', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (488, 'test', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-01 20:57:58', '2025-12-01 20:57:58', '2025-12-01 20:57:58', 'test', 'test', 0);
INSERT INTO `sys_login_log` VALUES (489, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-01 20:58:00', '2025-12-01 20:58:00', '2025-12-01 20:58:00', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (490, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-01 20:58:17', '2025-12-01 20:58:17', '2025-12-01 20:58:17', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (491, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-01 20:59:21', '2025-12-01 20:59:21', '2025-12-01 20:59:21', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (492, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-01 20:59:27', '2025-12-01 20:59:27', '2025-12-01 20:59:27', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (493, 'anonymousUser', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-01 21:08:32', '2025-12-01 21:08:32', '2025-12-01 21:08:32', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (494, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-01 21:08:34', '2025-12-01 21:08:34', '2025-12-01 21:08:34', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (495, 'anonymousUser', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-01 21:32:42', '2025-12-01 21:32:42', '2025-12-01 21:32:42', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (496, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-01 21:32:44', '2025-12-01 21:32:44', '2025-12-01 21:32:44', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (497, 'anonymousUser', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-01 21:37:59', '2025-12-01 21:37:59', '2025-12-01 21:37:59', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (498, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-01 21:38:01', '2025-12-01 21:38:01', '2025-12-01 21:38:01', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (499, 'anonymousUser', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-01 21:52:33', '2025-12-01 21:52:33', '2025-12-01 21:52:33', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (500, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-01 21:52:35', '2025-12-01 21:52:35', '2025-12-01 21:52:35', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (501, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-01 21:55:17', '2025-12-01 21:55:17', '2025-12-01 21:55:17', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (502, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-01 21:55:18', '2025-12-01 21:55:18', '2025-12-01 21:55:18', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (503, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-01 22:01:21', '2025-12-01 22:01:21', '2025-12-01 22:01:21', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (504, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-01 22:01:24', '2025-12-01 22:01:24', '2025-12-01 22:01:24', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (505, 'anonymousUser', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-02 20:20:43', '2025-12-02 20:20:43', '2025-12-02 20:20:43', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (506, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-02 20:20:46', '2025-12-02 20:20:46', '2025-12-02 20:20:46', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (507, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-02 20:46:40', '2025-12-02 20:46:40', '2025-12-02 20:46:40', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (508, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-02 20:46:42', '2025-12-02 20:46:42', '2025-12-02 20:46:42', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (509, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-02 20:49:55', '2025-12-02 20:49:55', '2025-12-02 20:49:55', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (510, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-02 20:49:57', '2025-12-02 20:49:57', '2025-12-02 20:49:57', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (511, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-02 20:51:07', '2025-12-02 20:51:07', '2025-12-02 20:51:07', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (512, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-02 20:51:09', '2025-12-02 20:51:09', '2025-12-02 20:51:09', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (513, 'anonymousUser', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-02 21:02:53', '2025-12-02 21:02:53', '2025-12-02 21:02:53', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (514, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-02 21:02:57', '2025-12-02 21:02:57', '2025-12-02 21:02:57', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (515, 'anonymousUser', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-02 21:06:08', '2025-12-02 21:06:08', '2025-12-02 21:06:08', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (516, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-02 21:06:10', '2025-12-02 21:06:10', '2025-12-02 21:06:10', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (517, 'anonymousUser', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-02 21:09:17', '2025-12-02 21:09:17', '2025-12-02 21:09:17', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (518, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-02 21:09:20', '2025-12-02 21:09:20', '2025-12-02 21:09:20', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (519, 'anonymousUser', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-02 21:13:41', '2025-12-02 21:13:41', '2025-12-02 21:13:41', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (520, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-02 21:13:44', '2025-12-02 21:13:44', '2025-12-02 21:13:44', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (521, 'anonymousUser', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-02 21:16:56', '2025-12-02 21:16:56', '2025-12-02 21:16:56', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (522, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-02 21:16:58', '2025-12-02 21:16:58', '2025-12-02 21:16:58', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (523, 'anonymousUser', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-02 21:20:55', '2025-12-02 21:20:55', '2025-12-02 21:20:55', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (524, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-02 21:20:57', '2025-12-02 21:20:57', '2025-12-02 21:20:57', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (525, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-02 21:21:08', '2025-12-02 21:21:08', '2025-12-02 21:21:08', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (526, 'anonymousUser', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-02 21:21:42', '2025-12-02 21:21:42', '2025-12-02 21:21:42', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (527, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-02 21:21:45', '2025-12-02 21:21:45', '2025-12-02 21:21:45', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (528, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-02 21:24:21', '2025-12-02 21:24:21', '2025-12-02 21:24:21', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (529, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-02 21:25:47', '2025-12-02 21:25:47', '2025-12-02 21:25:47', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (530, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-02 21:27:44', '2025-12-02 21:27:44', '2025-12-02 21:27:44', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (531, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-02 21:27:46', '2025-12-02 21:27:46', '2025-12-02 21:27:46', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (532, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-02 21:30:28', '2025-12-02 21:30:28', '2025-12-02 21:30:28', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (533, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-02 21:33:53', '2025-12-02 21:33:53', '2025-12-02 21:33:53', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (534, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-02 21:33:55', '2025-12-02 21:33:55', '2025-12-02 21:33:55', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (535, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-02 21:59:22', '2025-12-02 21:59:22', '2025-12-02 21:59:22', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (536, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-02 21:59:25', '2025-12-02 21:59:25', '2025-12-02 21:59:25', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (537, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-02 22:00:34', '2025-12-02 22:00:34', '2025-12-02 22:00:34', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (538, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-02 22:00:36', '2025-12-02 22:00:36', '2025-12-02 22:00:36', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (539, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-02 22:03:45', '2025-12-02 22:03:45', '2025-12-02 22:03:45', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (540, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-02 22:03:47', '2025-12-02 22:03:47', '2025-12-02 22:03:47', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (541, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-02 22:04:09', '2025-12-02 22:04:09', '2025-12-02 22:04:09', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (542, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-02 22:04:11', '2025-12-02 22:04:11', '2025-12-02 22:04:11', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (543, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-02 22:04:15', '2025-12-02 22:04:15', '2025-12-02 22:04:15', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (544, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-02 22:04:16', '2025-12-02 22:04:16', '2025-12-02 22:04:16', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (545, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-02 22:05:57', '2025-12-02 22:05:57', '2025-12-02 22:05:57', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (546, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-02 22:05:59', '2025-12-02 22:05:59', '2025-12-02 22:05:59', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (547, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-02 22:08:27', '2025-12-02 22:08:27', '2025-12-02 22:08:27', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (548, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-02 22:08:28', '2025-12-02 22:08:28', '2025-12-02 22:08:28', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (549, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-02 22:08:32', '2025-12-02 22:08:32', '2025-12-02 22:08:32', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (550, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-02 22:08:34', '2025-12-02 22:08:34', '2025-12-02 22:08:34', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (551, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-02 22:23:04', '2025-12-02 22:23:04', '2025-12-02 22:23:04', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (552, 'test', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-02 22:23:08', '2025-12-02 22:23:08', '2025-12-02 22:23:08', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (553, 'test', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-02 22:28:15', '2025-12-02 22:28:15', '2025-12-02 22:28:15', 'test', 'test', 0);
INSERT INTO `sys_login_log` VALUES (554, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-02 22:28:17', '2025-12-02 22:28:17', '2025-12-02 22:28:17', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (555, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-02 22:29:02', '2025-12-02 22:29:02', '2025-12-02 22:29:02', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (556, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-02 22:29:04', '2025-12-02 22:29:04', '2025-12-02 22:29:04', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (557, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-02 22:31:18', '2025-12-02 22:31:18', '2025-12-02 22:31:18', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (558, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-02 22:35:22', '2025-12-02 22:35:22', '2025-12-02 22:35:22', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (559, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-02 22:35:24', '2025-12-02 22:35:24', '2025-12-02 22:35:24', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (560, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-02 22:38:10', '2025-12-02 22:38:10', '2025-12-02 22:38:10', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (561, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-03 21:21:06', '2025-12-03 21:21:06', '2025-12-03 21:21:06', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (562, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-03 21:57:54', '2025-12-03 21:57:54', '2025-12-03 21:57:54', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (563, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-03 21:57:56', '2025-12-03 21:57:56', '2025-12-03 21:57:56', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (564, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-03 22:15:41', '2025-12-03 22:15:41', '2025-12-03 22:15:41', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (565, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-03 22:22:08', '2025-12-03 22:22:08', '2025-12-03 22:22:08', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (566, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-03 22:22:11', '2025-12-03 22:22:11', '2025-12-03 22:22:11', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (567, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-03 22:26:26', '2025-12-03 22:26:26', '2025-12-03 22:26:26', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (568, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-03 22:46:43', '2025-12-03 22:46:43', '2025-12-03 22:46:43', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (569, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-04 14:29:00', '2025-12-04 14:29:00', '2025-12-04 14:29:00', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (570, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-04 14:48:02', '2025-12-04 14:48:02', '2025-12-04 14:48:02', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (571, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-04 14:49:02', '2025-12-04 14:49:02', '2025-12-04 14:49:02', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (572, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-04 14:49:04', '2025-12-04 14:49:04', '2025-12-04 14:49:04', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (573, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-04 14:52:26', '2025-12-04 14:52:26', '2025-12-04 14:52:26', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (574, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-04 14:52:27', '2025-12-04 14:52:27', '2025-12-04 14:52:27', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (575, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-04 17:39:08', '2025-12-04 17:39:08', '2025-12-04 17:39:08', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (576, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-04 17:55:20', '2025-12-04 17:55:20', '2025-12-04 17:55:20', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (577, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-04 18:01:25', '2025-12-04 18:01:25', '2025-12-04 18:01:25', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (578, 'admin', '127.0.0.1', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-04 18:01:26', '2025-12-04 18:01:26', '2025-12-04 18:01:26', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (579, 'admin', '127.0.0.1', NULL, 'unknown', 'unknown', 0, 1, '登录成功', '2025-12-05 16:06:29', '2025-12-05 16:06:29', '2025-12-05 16:06:29', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (580, 'admin', '121.60.126.44', NULL, 'unknown', 'unknown', 0, 1, '登录成功', '2025-12-05 16:11:57', '2025-12-05 16:11:57', '2025-12-05 16:11:57', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (581, 'admin', '121.60.126.44', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-05 16:12:31', '2025-12-05 16:12:31', '2025-12-05 16:12:31', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (582, 'admin', '121.60.126.44', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-05 16:13:12', '2025-12-05 16:13:12', '2025-12-05 16:13:12', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (583, 'admin', '121.60.126.44', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-05 16:15:11', '2025-12-05 16:15:11', '2025-12-05 16:15:11', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (584, 'admin', '121.60.126.44', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-05 16:15:13', '2025-12-05 16:15:13', '2025-12-05 16:15:13', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (585, 'admin', '121.60.126.44', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-05 16:16:13', '2025-12-05 16:16:13', '2025-12-05 16:16:13', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (586, 'admin', '121.60.126.44', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-05 16:16:15', '2025-12-05 16:16:15', '2025-12-05 16:16:15', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (587, 'admin', '121.60.126.44', NULL, 'Chrome', 'Linux', 0, 1, '登录成功', '2025-12-05 16:18:37', '2025-12-05 16:18:37', '2025-12-05 16:18:37', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (588, 'admin', '183.14.132.14', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-05 16:18:43', '2025-12-05 16:18:43', '2025-12-05 16:18:43', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (589, 'admin', '180.167.27.2', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-05 16:24:10', '2025-12-05 16:24:10', '2025-12-05 16:24:10', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (590, 'admin', '121.60.126.44', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-05 17:00:57', '2025-12-05 17:00:57', '2025-12-05 17:00:57', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (591, 'admin', '121.60.126.44', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-05 17:20:52', '2025-12-05 17:20:52', '2025-12-05 17:20:52', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (592, 'admin', '121.60.126.44', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-05 17:33:11', '2025-12-05 17:33:11', '2025-12-05 17:33:11', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (593, 'admin', '43.227.138.54', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-05 19:25:10', '2025-12-05 19:25:10', '2025-12-05 19:25:10', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (594, 'admin', '43.227.136.95', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-05 19:47:03', '2025-12-05 19:47:03', '2025-12-05 19:47:03', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (595, 'admin', '43.227.136.95', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-05 19:54:36', '2025-12-05 19:54:36', '2025-12-05 19:54:36', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (596, 'admin', '43.227.138.54', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-05 19:55:36', '2025-12-05 19:55:36', '2025-12-05 19:55:36', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (597, 'admin', '43.227.136.95', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-05 19:55:42', '2025-12-05 19:55:42', '2025-12-05 19:55:42', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (598, 'admin', '43.227.136.95', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-05 19:57:19', '2025-12-05 19:57:19', '2025-12-05 19:57:19', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (599, 'admin', '43.227.136.95', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-05 20:02:11', '2025-12-05 20:02:11', '2025-12-05 20:02:11', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (600, 'admin', '43.227.136.95', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-05 20:02:51', '2025-12-05 20:02:51', '2025-12-05 20:02:51', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (601, 'admin', '121.60.126.44', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-08 09:36:00', '2025-12-08 09:36:00', '2025-12-08 09:36:00', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (602, 'admin', '127.0.0.1', NULL, 'unknown', 'unknown', 0, 1, '登录成功', '2025-12-08 14:45:11', '2025-12-08 14:45:11', '2025-12-08 14:45:11', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (603, 'admin', '121.60.126.44', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-08 14:52:00', '2025-12-08 14:52:00', '2025-12-08 14:52:00', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (604, 'admin', '121.60.126.44', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-08 14:59:15', '2025-12-08 14:59:15', '2025-12-08 14:59:15', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (605, 'admin', '121.60.126.44', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-08 14:59:16', '2025-12-08 14:59:16', '2025-12-08 14:59:16', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (606, 'admin', '121.60.126.44', NULL, 'Chrome', 'Windows', 1, 1, '登出成功', '2025-12-08 15:00:51', '2025-12-08 15:00:51', '2025-12-08 15:00:51', 'admin', 'admin', 0);
INSERT INTO `sys_login_log` VALUES (607, 'admin', '121.60.126.44', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-08 15:00:52', '2025-12-08 15:00:52', '2025-12-08 15:00:52', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (608, 'admin', '127.0.0.1', NULL, 'unknown', 'unknown', 0, 1, '登录成功', '2025-12-08 15:05:30', '2025-12-08 15:05:30', '2025-12-08 15:05:30', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (609, 'admin', '121.60.125.198', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-16 08:29:36', '2025-12-16 08:29:36', '2025-12-16 08:29:36', NULL, NULL, 0);
INSERT INTO `sys_login_log` VALUES (610, 'admin', '121.60.125.198', NULL, 'Chrome', 'Windows', 0, 1, '登录成功', '2025-12-16 09:52:53', '2025-12-16 09:52:53', '2025-12-16 09:52:53', NULL, NULL, 0);

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
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_parent_id`(`parent_id` ASC) USING BTREE,
  INDEX `idx_ancestors`(`ancestors` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 117 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 0, '0', '控制台', 1, '/admin/index', 'admin/index', '', 'DataBoard', 1, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:02', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (2, 0, '0', '组件示例', 1, NULL, NULL, NULL, 'Grid', 2, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:02', 'admin', 'admin', 1);
INSERT INTO `sys_menu` VALUES (3, 0, '0', '系统管理', 1, NULL, NULL, NULL, 'Setting', 2, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:02', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (4, 0, '0', '系统监控', 1, NULL, NULL, NULL, 'Monitor', 6, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:02', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (5, 0, '0', '日志管理', 1, NULL, NULL, '', 'Tickets', 9, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:02', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (6, 0, '0', '系统工具', 1, NULL, NULL, NULL, 'SetUp', 4, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:02', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (7, 0, '0', '外部链接', 1, NULL, NULL, NULL, 'Link', 10, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:02', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (47, 2, '0,2', '表格示例', 2, '/admin/template/table', 'admin/template/table', 'sys:table:view', 'list', 1, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:03', 'admin', 'admin', 1);
INSERT INTO `sys_menu` VALUES (48, 2, '0,2', '图标示例', 2, '/admin/template/icon', 'admin/template/icon', 'sys:icon:view', 'document', 2, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:03', 'admin', 'admin', 1);
INSERT INTO `sys_menu` VALUES (49, 3, '0,3', '用户管理', 2, '/admin/system/user', 'system/user/index', 'sys:user:page,sys:post:list,sys:dept:tree,sys:role:list', 'User', 1, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (50, 3, '0,3', '部门管理', 2, '/admin/system/dept', 'system/dept/index', 'sys:dept:page', 'OfficeBuilding', 2, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (51, 3, '0,3', '岗位管理', 2, '/admin/system/post', 'system/post/index', 'sys:post:page', 'document', 3, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (52, 3, '0,3', '角色管理', 2, '/admin/system/role', 'system/role/index', 'sys:role:page,sys:role:list', 'UserFilled', 4, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (53, 3, '0,3', '菜单管理', 2, '/admin/system/menu', 'system/menu/index', 'sys:menu:tree', 'Menu', 5, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (54, 3, '0,3', '字典管理', 2, '/admin/system/dict', 'system/dict/index', 'sys:dict-type:page,sys:dict-type:data', 'DocumentCopy', 6, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (55, 3, '0,3', '系统配置', 2, '/admin/system/config', 'system/config/index', 'sys:config:page', 'Operation', 7, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (56, 3, '0,3', '个人中心', 2, '/admin/system/profile', 'system/profile/index', 'sys:profile:view', 'user', 8, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:03', 'admin', 'admin', 1);
INSERT INTO `sys_menu` VALUES (57, 4, '0,4', '定时任务', 2, '/admin/monitor/job', 'monitor/job/index', 'sys:job:view', 'timer', 1, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-12-03 22:20:27', 'admin', 'admin', 1);
INSERT INTO `sys_menu` VALUES (59, 4, '0,4', '在线用户', 2, '/admin/monitor/online', 'monitor/online/index', 'sys:session:page', 'Connection', 3, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (60, 4, '0,4', 'SQL监控', 2, 'http://localhost:8080/druid/login.html', 'monitor/sql/index', '', 'DataLine', 4, 1, 1, 1, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (61, 5, '0,5', '操作日志', 2, '/admin/log/operlog', 'log/operlog/index', 'sys:operlog:page', 'Document', 1, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (62, 5, '0,5', '登录日志', 2, '/admin/log/loginlog', 'log/loginlog/index', 'sys:loginlog:page', 'TrendCharts', 2, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (63, 6, '0,6', '文件管理', 2, '/admin/tool/file', 'tool/file/index', 'tool:file:upload', 'files', 1, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (65, 7, '0,7', 'Gitee', 2, 'https://gitee.com/leven2018/lanjii', NULL, '', 'QuestionFilled', 1, 1, 1, 1, 1, '2025-06-21 09:29:22', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (66, 7, '0,7', 'Element Plus', 2, 'https://element-plus.org/', NULL, '', 'ElementPlus', 2, 1, 1, 1, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (69, 49, '3,49', '新增', 3, '', '', 'sys:user:save', '', 1, 1, 1, 0, 0, '2025-09-27 13:21:00', '2025-11-17 17:12:03', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (70, 49, '3,49', '编辑', 3, '', '', 'sys:user:update', '', 2, 1, 1, 0, 0, '2025-09-27 13:21:38', '2025-11-17 17:12:03', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (71, 49, '3,49', '删除', 3, '', '', 'sys:user:delete', '', 3, 1, 1, 0, 0, '2025-09-27 13:22:16', '2025-11-17 17:12:03', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (72, 49, '3,49', '删除', 3, '', '', 'sys:user:delete', '', 4, 1, 1, 0, 0, '2025-09-27 13:25:39', '2025-11-17 17:12:03', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (73, 49, '3,49', '查看', 3, '', '', 'sys:user:view', '', 0, 1, 1, 0, 0, '2025-09-27 13:54:17', '2025-11-17 17:12:03', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (74, 49, '3,49', '导出', 3, '', '', 'sys:user:export', '', 5, 1, 1, 0, 0, '2025-09-27 14:37:32', '2025-11-17 17:12:03', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (75, 49, '3,49', '状态切换', 3, '', '', 'sys:user:status', '', 7, 1, 1, 0, 0, '2025-09-27 15:37:14', '2025-11-17 17:12:03', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (76, 49, '3,49', '重置密码', 3, '', '', 'sys:user:reset-pwd', '', 0, 1, 1, 0, 0, '2025-09-27 15:38:43', '2025-11-17 17:12:03', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (77, 50, '3,50', '新增', 3, '', '', 'sys:dept:save', '', 1, 1, 1, 0, 0, '2025-09-27 16:00:00', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (78, 50, '3,50', '编辑', 3, '', '', 'sys:dept:update', '', 2, 1, 1, 0, 0, '2025-09-27 16:00:00', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (79, 50, '3,50', '删除', 3, '', '', 'sys:dept:delete', '', 3, 1, 1, 0, 0, '2025-09-27 16:00:00', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (80, 51, '3,51', '新增', 3, '', '', 'sys:post:save', '', 1, 1, 1, 0, 0, '2025-09-27 16:00:00', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (81, 51, '3,51', '编辑', 3, '', '', 'sys:post:update', '', 2, 1, 1, 0, 0, '2025-09-27 16:00:00', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (82, 51, '3,51', '删除', 3, '', '', 'sys:post:delete', '', 3, 1, 1, 0, 0, '2025-09-27 16:00:00', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (83, 52, '3,52', '新增', 3, '', '', 'sys:role:save', '', 1, 1, 1, 0, 0, '2025-09-27 16:00:00', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (84, 52, '3,52', '编辑', 3, '', '', 'sys:role:update', '', 2, 1, 1, 0, 0, '2025-09-27 16:00:00', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (85, 52, '3,52', '删除', 3, '', '', 'sys:role:delete', '', 3, 1, 1, 0, 0, '2025-09-27 16:00:00', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (86, 50, '3,50', '查看', 3, '', '', 'sys:dept:view', '', 0, 1, 1, 0, 0, '2025-10-09 20:12:55', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (87, 51, '3,51', '查看', 3, '', '', 'sys:post:view', '', 0, 1, 1, 0, 0, '2025-10-09 20:14:04', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (88, 54, '3,54', '新增', 3, '', '', 'sys:dict-type:save', '', 1, 1, 1, 0, 0, '2025-10-09 20:18:02', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (89, 54, '3,54', '编辑', 3, '', '', 'sys:dict-type:update', '', 2, 1, 1, 0, 0, '2025-10-09 20:18:02', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (90, 54, '3,54', '删除', 3, '', '', 'sys:dict-type:delete', '', 3, 1, 1, 0, 0, '2025-10-09 20:18:02', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (91, 54, '3,54', '查看', 3, '', '', 'sys:dict-type:view', '', 0, 1, 1, 0, 0, '2025-10-09 20:18:02', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (92, 55, '3,55', '新增', 3, '', '', 'sys:config:save', '', 1, 1, 1, 0, 0, '2025-10-09 20:35:35', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (93, 55, '3,55', '编辑', 3, '', '', 'sys:config:update,sys:config:view', '', 2, 1, 1, 0, 0, '2025-10-09 20:35:35', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (94, 55, '3,55', '删除', 3, '', '', 'sys:config:delete', '', 3, 1, 1, 0, 0, '2025-10-09 20:35:35', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (95, 55, '3,55', '查看', 3, '', '', 'sys:config:view', '', 0, 1, 1, 0, 0, '2025-10-09 20:35:35', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (96, 55, '3,55', '清除缓存', 3, '', '', 'sys:config:cache', '', 9, 1, 1, 0, 0, '2025-10-09 20:35:35', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (97, 54, '3,54', '新增数据', 3, '', '', 'sys:dict-data:save', '', NULL, 1, 1, 0, 0, '2025-10-09 20:49:06', '2025-11-17 17:12:03', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (98, 54, '3,54', '编辑数据', 3, '', '', 'sys:dict-data:update', NULL, NULL, 1, 1, 0, 0, '2025-10-09 20:49:28', '2025-11-17 17:12:03', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (100, 54, '3,54', '删除数据', 3, '', '', 'sys:dict-data:delete', '', NULL, 1, 1, 0, 0, '2025-10-09 20:50:22', '2025-11-17 17:12:03', NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (101, 53, '3,53', '删除', 3, '', '', 'sys:menu:delete', '', NULL, 1, 1, 0, 0, '2025-11-10 19:48:19', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (102, 52, '3,52', '分配权限', 3, '', '', 'sys:role:permission', '', NULL, 1, 1, 0, 0, '2025-11-11 22:15:46', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (103, 53, '3,53', '新增', 3, '', '', 'sys:menu:save', '', 1, 1, 1, 0, 0, '2025-11-13 18:26:00', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (104, 53, '3,53', '编辑', 3, '', '', 'sys:menu:update', '', 0, 1, 1, 0, 0, '2025-11-13 18:26:23', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (105, 53, '3,53', '查看', 3, '', '', 'sys:menu:view', '', 4, 1, 1, 0, 0, '2025-11-13 18:27:00', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (106, 62, '5,62', '清空', 3, '', '', 'sys:loginlog:clean', '', 0, 1, 1, 0, 0, '2025-11-13 22:03:47', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (107, 62, '5,62', '导出', 3, '', '', 'sys:loginlog:export', '', 2, 1, 1, 0, 0, '2025-11-13 22:04:25', '2025-11-17 17:12:03', 'admin', 'admin', 1);
INSERT INTO `sys_menu` VALUES (108, 61, '5,61', '清空', 3, '', '', 'sys:operlog:clean', '', 0, 1, 1, 0, 0, '2025-11-13 22:04:48', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (109, 62, '5,62', '查看', 3, '', '', 'sys:loginlog:view', '', 0, 1, 1, 0, 0, '2025-11-13 22:30:21', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (110, 61, '5,61', '查看', 3, '', '', 'sys:operlog:view', '', 0, 1, 1, 0, 0, '2025-11-13 22:30:47', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (111, 52, '3,52', '查看', 3, '', '', 'sys:role:view', '', 0, 1, 1, 0, 0, '2025-11-13 23:42:58', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (112, 3, '0,3', '字典数据', 2, '/admin/system/dict/data', 'system/dict/data/index', 'sys:dict-data:page', 'Management', 6, 0, 1, 0, 0, '2025-11-14 10:23:45', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (113, 59, '4,59', '踢出', 3, '', '', 'sys:session:kick', '', 1, 1, 1, 0, 0, '2025-11-14 22:39:12', '2025-11-17 17:12:03', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (114, 0, '0', '通知公告', 1, '', '', '', 'Bell', 3, 1, 1, 0, 0, '2025-11-27 20:15:11', '2025-11-27 20:15:11', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (115, 114, '0,114', '通知公告', 2, '/admin/notice', 'notice/index', 'notice:page', 'Tickets', 0, 1, 1, 0, 0, '2025-11-27 20:18:33', '2025-11-27 20:18:33', 'admin', 'admin', 0);
INSERT INTO `sys_menu` VALUES (116, 114, '0,114', '发布', 2, '/admin/notice/publish', 'notice/publish', 'notice:publish', 'Promotion', 0, 1, 1, 0, 0, '2025-11-28 11:33:00', '2025-11-28 11:33:00', 'admin', 'admin', 0);

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
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统通知公告表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
INSERT INTO `sys_notice` VALUES (1, '系统维护通知', '<p>系统将于本周六22:00-24:00进行维护升级，期间可能无法访问，请提前做好准备。</p>', 'important', 1, '系统管理员', '2025-11-27 13:42:53', 1, 0, '2025-11-27 13:42:53', '2025-11-27 13:42:53', NULL, NULL);
INSERT INTO `sys_notice` VALUES (2, '新功能上线公告', '<p>通知公告功能已正式上线，支持实时推送、已读标记等功能，欢迎使用！</p>', 'normal', 1, '系统管理员', '2025-11-27 13:42:53', 1, 0, '2025-11-27 13:42:53', '2025-11-27 13:42:53', NULL, NULL);
INSERT INTO `sys_notice` VALUES (3, '秋海棠伟大电器一道因此', 'laboris incididunt ut', 'normal', 1, '系统管理员', '2025-11-27 22:14:36', 1, 0, '2025-11-27 22:14:36', '2025-11-27 22:14:36', 'admin', 'admin');
INSERT INTO `sys_notice` VALUES (4, '秋海棠伟大电器一道因此', 'laboris incididunt ut', 'normal', 1, '系统管理员', '2025-11-27 22:15:22', 1, 0, '2025-11-27 22:15:22', '2025-11-27 22:15:22', 'admin', 'admin');
INSERT INTO `sys_notice` VALUES (5, '秋海棠伟大电器一道因此', 'laboris incididunt ut', 'normal', 1, '系统管理员', '2025-11-27 22:15:38', 1, 0, '2025-11-27 22:15:38', '2025-11-27 22:15:38', 'admin', 'admin');
INSERT INTO `sys_notice` VALUES (6, '秋海棠伟大电器一道因此', 'laboris incididunt ut', 'normal', 1, '系统管理员', '2025-11-27 22:16:33', 1, 0, '2025-11-27 22:16:33', '2025-11-27 22:16:33', 'admin', 'admin');
INSERT INTO `sys_notice` VALUES (7, '秋海棠伟大电器一道因此', 'laboris incididunt ut', 'normal', 1, '系统管理员', '2025-11-27 22:16:37', 1, 0, '2025-11-27 22:16:37', '2025-11-27 22:16:37', 'admin', 'admin');
INSERT INTO `sys_notice` VALUES (8, '11', '<p>222</p>', 'normal', 1, '系统管理员', '2025-11-28 11:38:06', 1, 0, '2025-11-28 11:38:06', '2025-11-28 11:38:06', 'admin', 'admin');
INSERT INTO `sys_notice` VALUES (9, '33', '<table style=\"width: auto;\"><tbody><tr><th colSpan=\"1\" rowSpan=\"1\" width=\"auto\">12</th><th colSpan=\"1\" rowSpan=\"1\" width=\"auto\"></th><th colSpan=\"1\" rowSpan=\"1\" width=\"auto\"></th><th colSpan=\"1\" rowSpan=\"1\" width=\"auto\"></th><th colSpan=\"1\" rowSpan=\"1\" width=\"auto\"></th><th colSpan=\"1\" rowSpan=\"1\" width=\"auto\"></th></tr><tr><td colSpan=\"1\" rowSpan=\"1\" width=\"auto\"></td><td colSpan=\"1\" rowSpan=\"1\" width=\"auto\">333</td><td colSpan=\"1\" rowSpan=\"1\" width=\"auto\"></td><td colSpan=\"1\" rowSpan=\"1\" width=\"auto\"></td><td colSpan=\"1\" rowSpan=\"1\" width=\"auto\"></td><td colSpan=\"1\" rowSpan=\"1\" width=\"auto\"></td></tr><tr><td colSpan=\"1\" rowSpan=\"1\" width=\"auto\"></td><td colSpan=\"1\" rowSpan=\"1\" width=\"auto\"></td><td colSpan=\"1\" rowSpan=\"1\" width=\"auto\">11</td><td colSpan=\"1\" rowSpan=\"1\" width=\"auto\"></td><td colSpan=\"1\" rowSpan=\"1\" width=\"auto\"></td><td colSpan=\"1\" rowSpan=\"1\" width=\"auto\"></td></tr><tr><td colSpan=\"1\" rowSpan=\"1\" width=\"auto\"></td><td colSpan=\"1\" rowSpan=\"1\" width=\"auto\"></td><td colSpan=\"1\" rowSpan=\"1\" width=\"auto\"></td><td colSpan=\"1\" rowSpan=\"1\" width=\"auto\"></td><td colSpan=\"1\" rowSpan=\"1\" width=\"auto\"></td><td colSpan=\"1\" rowSpan=\"1\" width=\"auto\"></td></tr><tr><td colSpan=\"1\" rowSpan=\"1\" width=\"auto\"></td><td colSpan=\"1\" rowSpan=\"1\" width=\"auto\"></td><td colSpan=\"1\" rowSpan=\"1\" width=\"auto\"></td><td colSpan=\"1\" rowSpan=\"1\" width=\"auto\"></td><td colSpan=\"1\" rowSpan=\"1\" width=\"auto\"></td><td colSpan=\"1\" rowSpan=\"1\" width=\"auto\"></td></tr><tr><td colSpan=\"1\" rowSpan=\"1\" width=\"auto\"></td><td colSpan=\"1\" rowSpan=\"1\" width=\"auto\"></td><td colSpan=\"1\" rowSpan=\"1\" width=\"auto\"></td><td colSpan=\"1\" rowSpan=\"1\" width=\"auto\"></td><td colSpan=\"1\" rowSpan=\"1\" width=\"auto\"></td><td colSpan=\"1\" rowSpan=\"1\" width=\"auto\"></td></tr><tr><td colSpan=\"1\" rowSpan=\"1\" width=\"auto\"></td><td colSpan=\"1\" rowSpan=\"1\" width=\"auto\"></td><td colSpan=\"1\" rowSpan=\"1\" width=\"auto\"></td><td colSpan=\"1\" rowSpan=\"1\" width=\"auto\"></td><td colSpan=\"1\" rowSpan=\"1\" width=\"auto\"></td><td colSpan=\"1\" rowSpan=\"1\" width=\"auto\"></td></tr></tbody></table><p><br></p>', 'normal', 1, '系统管理员', '2025-11-28 13:52:56', 1, 0, '2025-11-28 13:52:56', '2025-11-28 13:52:56', 'admin', 'admin');
INSERT INTO `sys_notice` VALUES (10, '111', '<p>333</p>', 'normal', 1, '系统管理员', '2025-12-01 20:45:09', 1, 0, '2025-12-01 20:45:09', '2025-12-01 20:45:09', 'admin', 'admin');
INSERT INTO `sys_notice` VALUES (11, '33', '<p>11</p>', 'normal', 1, '系统管理员', '2025-12-01 20:58:25', 1, 0, '2025-12-01 20:58:25', '2025-12-01 20:58:25', 'admin', 'admin');
INSERT INTO `sys_notice` VALUES (12, '111', '<p>2</p>', 'normal', 1, '系统管理员', '2025-12-01 21:08:41', 1, 0, '2025-12-01 21:08:41', '2025-12-01 21:08:41', 'admin', 'admin');
INSERT INTO `sys_notice` VALUES (13, '421', '<p>heihie</p>', 'normal', 1, '系统管理员', '2025-12-01 21:09:38', 1, 0, '2025-12-01 21:09:38', '2025-12-01 21:09:38', 'admin', 'admin');
INSERT INTO `sys_notice` VALUES (14, '33', '<p>11</p>', 'normal', 1, '系统管理员', '2025-12-01 21:10:14', 1, 0, '2025-12-01 21:10:14', '2025-12-01 21:10:14', 'admin', 'admin');
INSERT INTO `sys_notice` VALUES (15, '311', '<p>11</p>', 'normal', 1, '系统管理员', '2025-12-01 21:10:29', 1, 0, '2025-12-01 21:10:29', '2025-12-01 21:10:29', 'admin', 'admin');
INSERT INTO `sys_notice` VALUES (16, '33', '<p>222</p>', 'normal', 1, '系统管理员', '2025-12-01 21:32:57', 1, 0, '2025-12-01 21:32:57', '2025-12-01 21:32:57', 'admin', 'admin');
INSERT INTO `sys_notice` VALUES (17, '33', '<p>222</p>', 'normal', 1, '系统管理员', '2025-12-01 21:33:28', 1, 0, '2025-12-01 21:33:28', '2025-12-01 21:33:28', 'admin', 'admin');
INSERT INTO `sys_notice` VALUES (18, '33', '<p>22</p>', 'normal', 1, '系统管理员', '2025-12-01 21:38:10', 1, 0, '2025-12-01 21:38:10', '2025-12-01 21:38:10', 'admin', 'admin');
INSERT INTO `sys_notice` VALUES (19, '33', '<p>22</p>', 'normal', 1, '系统管理员', '2025-12-01 21:41:19', 1, 0, '2025-12-01 21:41:19', '2025-12-01 21:41:19', 'admin', 'admin');
INSERT INTO `sys_notice` VALUES (20, '紧急通知，这是一个公告', '<p>哈哈哈哈哈哈哈</p>', 'normal', 1, '系统管理员', '2025-12-01 21:42:07', 1, 0, '2025-12-01 21:42:07', '2025-12-01 21:42:07', 'admin', 'admin');
INSERT INTO `sys_notice` VALUES (21, '33', '<p>11</p>', 'normal', 1, '系统管理员', '2025-12-01 22:06:10', 1, 0, '2025-12-01 22:06:10', '2025-12-01 22:06:10', 'admin', 'admin');
INSERT INTO `sys_notice` VALUES (22, '33', '<p>11</p>', 'normal', 1, '系统管理员', '2025-12-01 22:08:37', 1, 0, '2025-12-01 22:08:37', '2025-12-01 22:08:37', 'admin', 'admin');
INSERT INTO `sys_notice` VALUES (23, '33', '<p>11</p>', 'normal', 1, '系统管理员', '2025-12-01 22:08:48', 1, 0, '2025-12-01 22:08:48', '2025-12-01 22:08:48', 'admin', 'admin');
INSERT INTO `sys_notice` VALUES (24, '和我', '<p>为我访问</p>', 'normal', 1, '系统管理员', '2025-12-03 21:21:34', 1, 0, '2025-12-03 21:21:34', '2025-12-03 21:21:34', 'admin', 'admin');
INSERT INTO `sys_notice` VALUES (25, '33', '<p>11</p>', 'normal', 1, '系统管理员', '2025-12-03 21:22:16', 1, 0, '2025-12-03 21:22:16', '2025-12-03 21:22:16', 'admin', 'admin');
INSERT INTO `sys_notice` VALUES (26, '11', '<p>233</p>', 'normal', 1, '系统管理员', '2025-12-03 21:42:06', 1, 0, '2025-12-03 21:42:06', '2025-12-03 21:42:06', 'admin', 'admin');
INSERT INTO `sys_notice` VALUES (27, '333', '<p>2</p>', 'normal', 1, '系统管理员', '2025-12-03 21:42:26', 1, 0, '2025-12-03 21:42:26', '2025-12-03 21:42:26', 'admin', 'admin');
INSERT INTO `sys_notice` VALUES (28, '333', '<p>2</p>', 'normal', 1, '系统管理员', '2025-12-03 21:44:22', 1, 0, '2025-12-03 21:44:22', '2025-12-03 21:44:22', 'admin', 'admin');
INSERT INTO `sys_notice` VALUES (29, '33', '<p>11</p>', 'normal', 1, '系统管理员', '2025-12-03 21:45:21', 1, 0, '2025-12-03 21:45:21', '2025-12-03 21:45:21', 'admin', 'admin');
INSERT INTO `sys_notice` VALUES (30, '33', '<p>11</p>', 'normal', 1, '系统管理员', '2025-12-03 21:53:11', 1, 0, '2025-12-03 21:53:11', '2025-12-03 21:53:11', 'admin', 'admin');
INSERT INTO `sys_notice` VALUES (31, '341', '<p>111</p>', 'normal', 1, '系统管理员', '2025-12-03 21:53:22', 1, 0, '2025-12-03 21:53:22', '2025-12-03 21:53:22', 'admin', 'admin');
INSERT INTO `sys_notice` VALUES (32, '31231', '<p>111</p>', 'normal', 1, '系统管理员', '2025-12-03 21:58:12', 1, 0, '2025-12-03 21:58:12', '2025-12-03 21:58:12', 'admin', 'admin');
INSERT INTO `sys_notice` VALUES (33, '111', '<p>22</p>', 'normal', 1, '系统管理员', '2025-12-05 17:37:42', 1, 0, '2025-12-05 17:37:42', '2025-12-05 17:37:42', 'admin', 'admin');
INSERT INTO `sys_notice` VALUES (34, '33', '<p>11123</p>', 'normal', 1, '系统管理员', '2025-12-08 09:41:25', 1, 0, '2025-12-08 09:41:25', '2025-12-08 09:41:25', 'admin', 'admin');
INSERT INTO `sys_notice` VALUES (35, '333', '<p>11</p>', 'normal', 1, '系统管理员', '2025-12-16 08:35:01', 1, 0, '2025-12-16 08:35:01', '2025-12-16 08:35:01', 'admin', 'admin');

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
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '通知阅读记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_notice_read_record
-- ----------------------------
INSERT INTO `sys_notice_read_record` VALUES (4, 7, 1, '2025-11-27 23:32:03', '2025-11-27 23:32:03');
INSERT INTO `sys_notice_read_record` VALUES (5, 5, 1, '2025-11-27 23:32:10', '2025-11-27 23:32:09');
INSERT INTO `sys_notice_read_record` VALUES (6, 6, 1, '2025-11-27 23:32:14', '2025-11-27 23:32:14');
INSERT INTO `sys_notice_read_record` VALUES (7, 4, 1, '2025-11-28 11:08:25', '2025-11-28 11:08:24');
INSERT INTO `sys_notice_read_record` VALUES (8, 1, 1, '2025-11-28 13:27:27', '2025-11-28 13:27:27');
INSERT INTO `sys_notice_read_record` VALUES (9, 2, 1, '2025-11-28 13:27:27', '2025-11-28 13:27:27');
INSERT INTO `sys_notice_read_record` VALUES (10, 3, 1, '2025-11-28 13:27:27', '2025-11-28 13:27:27');
INSERT INTO `sys_notice_read_record` VALUES (11, 8, 1, '2025-11-28 13:27:27', '2025-11-28 13:27:27');
INSERT INTO `sys_notice_read_record` VALUES (12, 6, 27, '2025-12-01 20:50:17', '2025-12-01 20:50:17');
INSERT INTO `sys_notice_read_record` VALUES (13, 19, 1, '2025-12-01 21:41:23', '2025-12-01 21:41:24');
INSERT INTO `sys_notice_read_record` VALUES (14, 20, 1, '2025-12-01 21:47:22', '2025-12-01 21:47:23');
INSERT INTO `sys_notice_read_record` VALUES (15, 17, 1, '2025-12-01 21:49:21', '2025-12-01 21:49:22');
INSERT INTO `sys_notice_read_record` VALUES (16, 18, 1, '2025-12-01 21:49:32', '2025-12-01 21:49:32');
INSERT INTO `sys_notice_read_record` VALUES (17, 12, 1, '2025-12-01 21:52:42', '2025-12-01 21:52:43');
INSERT INTO `sys_notice_read_record` VALUES (18, 11, 1, '2025-12-01 21:53:00', '2025-12-01 21:53:00');
INSERT INTO `sys_notice_read_record` VALUES (19, 15, 1, '2025-12-01 21:55:25', '2025-12-01 21:55:26');
INSERT INTO `sys_notice_read_record` VALUES (20, 10, 1, '2025-12-01 21:55:40', '2025-12-01 21:55:40');
INSERT INTO `sys_notice_read_record` VALUES (21, 13, 1, '2025-12-01 21:55:53', '2025-12-01 21:55:54');
INSERT INTO `sys_notice_read_record` VALUES (22, 14, 1, '2025-12-01 22:00:05', '2025-12-01 22:00:06');
INSERT INTO `sys_notice_read_record` VALUES (23, 16, 1, '2025-12-01 22:01:30', '2025-12-01 22:01:30');
INSERT INTO `sys_notice_read_record` VALUES (24, 21, 1, '2025-12-01 22:06:19', '2025-12-01 22:06:20');
INSERT INTO `sys_notice_read_record` VALUES (25, 23, 1, '2025-12-01 22:08:52', '2025-12-01 22:08:53');
INSERT INTO `sys_notice_read_record` VALUES (26, 9, 1, '2025-12-01 22:12:03', '2025-12-01 22:12:04');
INSERT INTO `sys_notice_read_record` VALUES (27, 22, 1, '2025-12-01 22:12:10', '2025-12-01 22:12:10');
INSERT INTO `sys_notice_read_record` VALUES (28, 23, 27, '2025-12-02 22:23:50', '2025-12-02 22:23:50');
INSERT INTO `sys_notice_read_record` VALUES (29, 21, 27, '2025-12-02 22:23:56', '2025-12-02 22:23:57');
INSERT INTO `sys_notice_read_record` VALUES (30, 22, 27, '2025-12-02 22:24:01', '2025-12-02 22:24:02');
INSERT INTO `sys_notice_read_record` VALUES (31, 17, 27, '2025-12-02 22:24:11', '2025-12-02 22:24:12');
INSERT INTO `sys_notice_read_record` VALUES (32, 24, 1, '2025-12-03 21:30:23', '2025-12-03 21:30:23');
INSERT INTO `sys_notice_read_record` VALUES (33, 25, 1, '2025-12-03 21:40:18', '2025-12-03 21:40:17');
INSERT INTO `sys_notice_read_record` VALUES (34, 31, 1, '2025-12-03 21:53:32', '2025-12-03 21:53:32');
INSERT INTO `sys_notice_read_record` VALUES (35, 28, 1, '2025-12-03 21:54:20', '2025-12-03 21:54:20');
INSERT INTO `sys_notice_read_record` VALUES (36, 29, 1, '2025-12-03 21:55:44', '2025-12-03 21:55:44');
INSERT INTO `sys_notice_read_record` VALUES (37, 26, 1, '2025-12-03 21:56:44', '2025-12-03 21:56:44');
INSERT INTO `sys_notice_read_record` VALUES (38, 27, 1, '2025-12-03 21:57:08', '2025-12-03 21:57:07');
INSERT INTO `sys_notice_read_record` VALUES (39, 30, 1, '2025-12-03 21:58:00', '2025-12-03 21:58:00');
INSERT INTO `sys_notice_read_record` VALUES (40, 32, 1, '2025-12-03 22:19:56', '2025-12-03 22:19:56');
INSERT INTO `sys_notice_read_record` VALUES (41, 34, 1, '2025-12-08 09:41:31', '2025-12-08 09:41:31');
INSERT INTO `sys_notice_read_record` VALUES (42, 33, 1, '2025-12-08 09:41:34', '2025-12-08 09:41:34');
INSERT INTO `sys_notice_read_record` VALUES (43, 35, 1, '2025-12-16 08:35:19', '2025-12-16 08:35:18');

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
  `oper_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '主机地址',
  `oper_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作地点',
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
) ENGINE = InnoDB AUTO_INCREMENT = 117 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户操作日志表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_oper_log
-- ----------------------------
INSERT INTO `sys_oper_log` VALUES (45, '全部标记已读', 1, 'markAllAsRead', 'PUT', 'admin', NULL, '/admin/notice/read-all', '127.0.0.1', NULL, '[null]', '{\"code\":200,\"msg\":\"成功\",\"data\":2}', 1, NULL, '2025-11-27 20:53:34', 36, '2025-11-27 20:53:34', '2025-11-27 20:53:34', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (46, '发布通知', 0, 'publishNotice', 'POST', 'admin', NULL, '/admin/notice', '127.0.0.1', NULL, '[{\"createTime\":null,\"updateTime\":null,\"id\":null,\"title\":\"秋海棠伟大电器一道因此\",\"content\":\"laboris incididunt ut\",\"level\":null,\"status\":null,\"keyword\":null,\"readStatus\":null}]', '{\"code\":200,\"msg\":\"成功\",\"data\":3}', 1, NULL, '2025-11-27 22:14:36', 52, '2025-11-27 22:14:36', '2025-11-27 22:14:36', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (47, '发布通知', 0, 'publishNotice', 'POST', 'admin', NULL, '/admin/notice', '127.0.0.1', NULL, '[{\"createTime\":null,\"updateTime\":null,\"id\":null,\"title\":\"秋海棠伟大电器一道因此\",\"content\":\"laboris incididunt ut\",\"level\":null,\"status\":null,\"keyword\":null,\"readStatus\":null}]', '{\"code\":200,\"msg\":\"成功\",\"data\":4}', 1, NULL, '2025-11-27 22:15:22', 30, '2025-11-27 22:15:22', '2025-11-27 22:15:22', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (48, '发布通知', 0, 'publishNotice', 'POST', 'admin', NULL, '/admin/notice', '127.0.0.1', NULL, '[{\"createTime\":null,\"updateTime\":null,\"id\":null,\"title\":\"秋海棠伟大电器一道因此\",\"content\":\"laboris incididunt ut\",\"level\":null,\"status\":null,\"keyword\":null,\"readStatus\":null}]', '{\"code\":200,\"msg\":\"成功\",\"data\":5}', 1, NULL, '2025-11-27 22:15:38', 26, '2025-11-27 22:15:38', '2025-11-27 22:15:38', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (49, '发布通知', 0, 'publishNotice', 'POST', 'admin', NULL, '/admin/notice', '127.0.0.1', NULL, '[{\"createTime\":null,\"updateTime\":null,\"id\":null,\"title\":\"秋海棠伟大电器一道因此\",\"content\":\"laboris incididunt ut\",\"level\":null,\"status\":null,\"keyword\":null,\"readStatus\":null}]', '{\"code\":200,\"msg\":\"成功\",\"data\":6}', 1, NULL, '2025-11-27 22:16:33', 29, '2025-11-27 22:16:33', '2025-11-27 22:16:33', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (50, '发布通知', 0, 'publishNotice', 'POST', 'admin', NULL, '/admin/notice', '127.0.0.1', NULL, '[{\"createTime\":null,\"updateTime\":null,\"id\":null,\"title\":\"秋海棠伟大电器一道因此\",\"content\":\"laboris incididunt ut\",\"level\":null,\"status\":null,\"keyword\":null,\"readStatus\":null}]', '{\"code\":200,\"msg\":\"成功\",\"data\":7}', 1, NULL, '2025-11-27 22:16:37', 34, '2025-11-27 22:16:37', '2025-11-27 22:16:37', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (51, '标记通知已读', 1, 'markAsRead', 'PUT', 'admin', NULL, '/admin/notice/7/read', '127.0.0.1', NULL, '[7]', '{\"code\":200,\"msg\":\"成功\",\"data\":null}', 1, NULL, '2025-11-27 23:32:03', 58, '2025-11-27 23:32:03', '2025-11-27 23:32:03', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (52, '标记通知已读', 1, 'markAsRead', 'PUT', 'admin', NULL, '/admin/notice/5/read', '127.0.0.1', NULL, '[5]', '{\"code\":200,\"msg\":\"成功\",\"data\":null}', 1, NULL, '2025-11-27 23:32:10', 23, '2025-11-27 23:32:10', '2025-11-27 23:32:10', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (53, '标记通知已读', 1, 'markAsRead', 'PUT', 'admin', NULL, '/admin/notice/6/read', '127.0.0.1', NULL, '[6]', '{\"code\":200,\"msg\":\"成功\",\"data\":null}', 1, NULL, '2025-11-27 23:32:14', 22, '2025-11-27 23:32:14', '2025-11-27 23:32:14', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (54, '标记通知已读', 1, 'markAsRead', 'PUT', 'admin', NULL, '/admin/notice/4/read', '127.0.0.1', NULL, '[4]', '{\"code\":200,\"msg\":\"成功\",\"data\":null}', 1, NULL, '2025-11-28 11:08:25', 52, '2025-11-28 11:08:25', '2025-11-28 11:08:25', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (55, '发布通知', 0, 'publishNotice', 'POST', 'admin', NULL, '/admin/notice', '127.0.0.1', NULL, '[{\"createTime\":null,\"updateTime\":null,\"id\":null,\"title\":\"11\",\"content\":\"<p>222</p>\",\"level\":null,\"status\":null,\"keyword\":null,\"readStatus\":null}]', '{\"code\":200,\"msg\":\"成功\",\"data\":8}', 1, NULL, '2025-11-28 11:38:06', 71, '2025-11-28 11:38:06', '2025-11-28 11:38:06', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (56, '全部标记已读', 1, 'markAllAsRead', 'PUT', 'admin', NULL, '/admin/notice/read-all', '127.0.0.1', NULL, '[null]', '{\"code\":200,\"msg\":\"成功\",\"data\":4}', 1, NULL, '2025-11-28 13:27:28', 164, '2025-11-28 13:27:28', '2025-11-28 13:27:28', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (57, '发布通知', 0, 'publishNotice', 'POST', 'admin', NULL, '/admin/notice', '127.0.0.1', NULL, '[{\"createTime\":null,\"updateTime\":null,\"id\":null,\"title\":\"33\",\"content\":\"<table style=\\\"width: auto;\\\"><tbody><tr><th colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\">12</th><th colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\"></th><th colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\"></th><th colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\"></th><th colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\"></th><th colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\"></th></tr><tr><td colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\"></td><td colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\">333</td><td colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\"></td><td colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\"></td><td colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\"></td><td colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\"></td></tr><tr><td colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\"></td><td colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\"></td><td colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\">11</td><td colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\"></td><td colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\"></td><td colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\"></td></tr><tr><td colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\"></td><td colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\"></td><td colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\"></td><td colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\"></td><td colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\"></td><td colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\"></td></tr><tr><td colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\"></td><td colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\"></td><td colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\"></td><td colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\"></td><td colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\"></td><td colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\"></td></tr><tr><td colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\"></td><td colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\"></td><td colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\"></td><td colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\"></td><td colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\"></td><td colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\"></td></tr><tr><td colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\"></td><td colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\"></td><td colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\"></td><td colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\"></td><td colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\"></td><td colSpan=\\\"1\\\" rowSpan=\\\"1\\\" width=\\\"auto\\\"></td></tr></tbody></table><p><br></p>\",\"level\":null,\"status\":null,\"keyword\":null,\"readStatus\":null}]', '{\"code\":200,\"msg\":\"成功\",\"data\":9}', 1, NULL, '2025-11-28 13:52:56', 66, '2025-11-28 13:52:56', '2025-11-28 13:52:56', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (58, '菜单管理', 1, 'update', 'PUT', 'admin', NULL, '/admin/sys/menus/114', '127.0.0.1', NULL, '[114,{\"createTime\":\"2025-11-27 20:15:11\",\"updateTime\":\"2025-11-27 20:15:11\",\"id\":114,\"parentId\":0,\"name\":\"通知公告\",\"type\":1,\"path\":\"\",\"component\":\"\",\"permission\":\"\",\"icon\":\"Bell\",\"sortOrder\":0,\"isVisible\":1,\"isEnabled\":1,\"isExt\":0,\"openMode\":0}]', '{\"code\":200,\"msg\":\"成功\",\"data\":null}', 1, NULL, '2025-12-01 20:40:05', 121, '2025-12-01 20:40:05', '2025-12-01 20:40:05', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (59, '菜单管理', 1, 'update', 'PUT', 'admin', NULL, '/admin/sys/menus/115', '127.0.0.1', NULL, '[115,{\"createTime\":\"2025-11-27 20:18:33\",\"updateTime\":\"2025-11-27 20:18:33\",\"id\":115,\"parentId\":114,\"name\":\"通知公告\",\"type\":2,\"path\":\"/admin/notice\",\"component\":\"notice/index\",\"permission\":\"notice:page\",\"icon\":\"Tickets\",\"sortOrder\":0,\"isVisible\":1,\"isEnabled\":1,\"isExt\":0,\"openMode\":0}]', '{\"code\":200,\"msg\":\"成功\",\"data\":null}', 1, NULL, '2025-12-01 20:40:23', 187, '2025-12-01 20:40:23', '2025-12-01 20:40:23', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (60, '菜单管理', 1, 'update', 'PUT', 'admin', NULL, '/admin/sys/menus/116', '127.0.0.1', NULL, '[116,{\"createTime\":\"2025-11-28 11:33:00\",\"updateTime\":\"2025-11-28 11:33:00\",\"id\":116,\"parentId\":114,\"name\":\"发布\",\"type\":2,\"path\":\"/admin/notice/publish\",\"component\":\"notice/publish\",\"permission\":\"sys:notice:publish\",\"icon\":\"CirclePlus\",\"sortOrder\":0,\"isVisible\":1,\"isEnabled\":1,\"isExt\":0,\"openMode\":0}]', '{\"code\":200,\"msg\":\"成功\",\"data\":null}', 1, NULL, '2025-12-01 20:40:49', 181, '2025-12-01 20:40:49', '2025-12-01 20:40:49', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (61, '菜单管理', 1, 'update', 'PUT', 'admin', NULL, '/admin/sys/menus/116', '127.0.0.1', NULL, '[116,{\"createTime\":\"2025-11-28 11:33:00\",\"updateTime\":\"2025-11-28 11:33:00\",\"id\":116,\"parentId\":114,\"name\":\"发布\",\"type\":2,\"path\":\"/admin/notice/publish\",\"component\":\"notice/publish\",\"permission\":\"sys:notice:publish\",\"icon\":\"Promotion\",\"sortOrder\":0,\"isVisible\":1,\"isEnabled\":1,\"isExt\":0,\"openMode\":0}]', '{\"code\":200,\"msg\":\"成功\",\"data\":null}', 1, NULL, '2025-12-01 20:41:59', 143, '2025-12-01 20:41:59', '2025-12-01 20:41:59', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (62, '菜单管理', 1, 'update', 'PUT', 'admin', NULL, '/admin/sys/menus/116', '127.0.0.1', NULL, '[116,{\"createTime\":\"2025-11-28 11:33:00\",\"updateTime\":\"2025-11-28 11:33:00\",\"id\":116,\"parentId\":114,\"name\":\"发布\",\"type\":2,\"path\":\"/admin/notice/publish\",\"component\":\"notice/publish\",\"permission\":\"notice:publish\",\"icon\":\"Promotion\",\"sortOrder\":0,\"isVisible\":1,\"isEnabled\":1,\"isExt\":0,\"openMode\":0}]', '{\"code\":200,\"msg\":\"成功\",\"data\":null}', 1, NULL, '2025-12-01 20:42:40', 281, '2025-12-01 20:42:40', '2025-12-01 20:42:40', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (63, '发布通知', 0, 'publishNotice', 'POST', 'admin', NULL, '/admin/notice', '127.0.0.1', NULL, '[{\"createTime\":null,\"updateTime\":null,\"id\":null,\"title\":\"111\",\"content\":\"<p>333</p>\",\"status\":null,\"keyword\":null,\"readStatus\":null}]', '{\"code\":200,\"msg\":\"成功\",\"data\":10}', 1, NULL, '2025-12-01 20:45:09', 181, '2025-12-01 20:45:09', '2025-12-01 20:45:09', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (64, '重置用户密码', 1, 'resetPassword', 'PUT', 'admin', NULL, '/admin/sys/users/27/reset-pwd', '127.0.0.1', NULL, '[27]', '{\"code\":200,\"msg\":\"成功\",\"data\":null}', 1, NULL, '2025-12-01 20:49:50', 241, '2025-12-01 20:49:50', '2025-12-01 20:49:50', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (65, '发布通知', 0, 'publishNotice', 'POST', 'admin', NULL, '/admin/notice', '127.0.0.1', NULL, '[{\"createTime\":null,\"updateTime\":null,\"id\":null,\"title\":\"33\",\"content\":\"<p>11</p>\",\"status\":null,\"keyword\":null,\"readStatus\":null}]', '{\"code\":200,\"msg\":\"成功\",\"data\":11}', 1, NULL, '2025-12-01 20:58:25', 228, '2025-12-01 20:58:25', '2025-12-01 20:58:25', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (66, '发布通知', 0, 'publishNotice', 'POST', 'admin', NULL, '/admin/notice', '127.0.0.1', NULL, '[{\"createTime\":null,\"updateTime\":null,\"id\":null,\"title\":\"111\",\"content\":\"<p>2</p>\",\"status\":null,\"keyword\":null,\"readStatus\":null}]', '{\"code\":200,\"msg\":\"成功\",\"data\":12}', 1, NULL, '2025-12-01 21:08:41', 184, '2025-12-01 21:08:41', '2025-12-01 21:08:41', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (67, '发布通知', 0, 'publishNotice', 'POST', 'admin', NULL, '/admin/notice', '127.0.0.1', NULL, '[{\"createTime\":null,\"updateTime\":null,\"id\":null,\"title\":\"421\",\"content\":\"<p>heihie</p>\",\"status\":null,\"keyword\":null,\"readStatus\":null}]', '{\"code\":200,\"msg\":\"成功\",\"data\":13}', 1, NULL, '2025-12-01 21:09:38', 196, '2025-12-01 21:09:38', '2025-12-01 21:09:38', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (68, '发布通知', 0, 'publishNotice', 'POST', 'admin', NULL, '/admin/notice', '127.0.0.1', NULL, '[{\"createTime\":null,\"updateTime\":null,\"id\":null,\"title\":\"33\",\"content\":\"<p>11</p>\",\"status\":null,\"keyword\":null,\"readStatus\":null}]', '{\"code\":200,\"msg\":\"成功\",\"data\":14}', 1, NULL, '2025-12-01 21:10:14', 169, '2025-12-01 21:10:14', '2025-12-01 21:10:14', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (69, '发布通知', 0, 'publishNotice', 'POST', 'admin', NULL, '/admin/notice', '127.0.0.1', NULL, '[{\"createTime\":null,\"updateTime\":null,\"id\":null,\"title\":\"311\",\"content\":\"<p>11</p>\",\"status\":null,\"keyword\":null,\"readStatus\":null}]', '{\"code\":200,\"msg\":\"成功\",\"data\":15}', 1, NULL, '2025-12-01 21:10:29', 208, '2025-12-01 21:10:29', '2025-12-01 21:10:29', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (70, '发布通知', 0, 'publishNotice', 'POST', 'admin', NULL, '/admin/notice', '127.0.0.1', NULL, '[{\"createTime\":null,\"updateTime\":null,\"id\":null,\"title\":\"33\",\"content\":\"<p>222</p>\",\"status\":null,\"keyword\":null,\"readStatus\":null}]', '{\"code\":200,\"msg\":\"成功\",\"data\":16}', 1, NULL, '2025-12-01 21:32:57', 156, '2025-12-01 21:32:57', '2025-12-01 21:32:57', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (71, '发布通知', 0, 'publishNotice', 'POST', 'admin', NULL, '/admin/notice', '127.0.0.1', NULL, '[{\"createTime\":null,\"updateTime\":null,\"id\":null,\"title\":\"33\",\"content\":\"<p>222</p>\",\"status\":null,\"keyword\":null,\"readStatus\":null}]', '{\"code\":200,\"msg\":\"成功\",\"data\":17}', 1, NULL, '2025-12-01 21:33:28', 242, '2025-12-01 21:33:28', '2025-12-01 21:33:28', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (72, '发布通知', 0, 'publishNotice', 'POST', 'admin', NULL, '/admin/notice', '127.0.0.1', NULL, '[{\"createTime\":null,\"updateTime\":null,\"id\":null,\"title\":\"33\",\"content\":\"<p>22</p>\",\"status\":null,\"keyword\":null,\"readStatus\":null}]', '{\"code\":200,\"msg\":\"成功\",\"data\":18}', 1, NULL, '2025-12-01 21:38:10', 169, '2025-12-01 21:38:10', '2025-12-01 21:38:10', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (73, '发布通知', 0, 'publishNotice', 'POST', 'admin', NULL, '/admin/notice', '127.0.0.1', NULL, '[{\"createTime\":null,\"updateTime\":null,\"id\":null,\"title\":\"33\",\"content\":\"<p>22</p>\",\"status\":null,\"keyword\":null,\"readStatus\":null}]', '{\"code\":200,\"msg\":\"成功\",\"data\":19}', 1, NULL, '2025-12-01 21:41:19', 379, '2025-12-01 21:41:19', '2025-12-01 21:41:19', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (74, '发布通知', 0, 'publishNotice', 'POST', 'admin', NULL, '/admin/notice', '127.0.0.1', NULL, '[{\"createTime\":null,\"updateTime\":null,\"id\":null,\"title\":\"紧急通知，这是一个公告\",\"content\":\"<p>哈哈哈哈哈哈哈</p>\",\"status\":null,\"keyword\":null,\"readStatus\":null}]', '{\"code\":200,\"msg\":\"成功\",\"data\":20}', 1, NULL, '2025-12-01 21:42:08', 965, '2025-12-01 21:42:08', '2025-12-01 21:42:08', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (75, '发布通知', 0, 'publishNotice', 'POST', 'admin', NULL, '/admin/notice', '127.0.0.1', NULL, '[{\"createTime\":null,\"updateTime\":null,\"id\":null,\"title\":\"33\",\"content\":\"<p>11</p>\",\"status\":null,\"keyword\":null,\"readStatus\":null}]', '{\"code\":200,\"msg\":\"成功\",\"data\":21}', 1, NULL, '2025-12-01 22:06:10', 148, '2025-12-01 22:06:10', '2025-12-01 22:06:10', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (76, '发布通知', 0, 'publishNotice', 'POST', 'admin', NULL, '/admin/notice', '127.0.0.1', NULL, '[{\"createTime\":null,\"updateTime\":null,\"id\":null,\"title\":\"33\",\"content\":\"<p>11</p>\",\"status\":null,\"keyword\":null,\"readStatus\":null}]', '{\"code\":200,\"msg\":\"成功\",\"data\":22}', 1, NULL, '2025-12-01 22:08:38', 187, '2025-12-01 22:08:38', '2025-12-01 22:08:38', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (77, '发布通知', 0, 'publishNotice', 'POST', 'admin', NULL, '/admin/notice', '127.0.0.1', NULL, '[{\"createTime\":null,\"updateTime\":null,\"id\":null,\"title\":\"33\",\"content\":\"<p>11</p>\",\"status\":null,\"keyword\":null,\"readStatus\":null}]', '{\"code\":200,\"msg\":\"成功\",\"data\":23}', 1, NULL, '2025-12-01 22:08:48', 144, '2025-12-01 22:08:48', '2025-12-01 22:08:48', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (78, '菜单管理', 1, 'update', 'PUT', 'admin', NULL, '/admin/sys/menus/60', '127.0.0.1', NULL, '[60,{\"createTime\":\"2025-06-21 09:29:22\",\"updateTime\":\"2025-11-17 17:12:03\",\"id\":60,\"parentId\":4,\"name\":\"SQL监控\",\"type\":2,\"path\":\"/druid/login.html\",\"component\":\"monitor/sql/index\",\"permission\":\"\",\"icon\":\"DataLine\",\"sortOrder\":4,\"isVisible\":1,\"isEnabled\":1,\"isExt\":1,\"openMode\":0}]', '{\"code\":200,\"msg\":\"成功\",\"data\":null}', 1, NULL, '2025-12-02 21:59:19', 245, '2025-12-02 21:59:19', '2025-12-02 21:59:19', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (79, '菜单管理', 1, 'update', 'PUT', 'admin', NULL, '/admin/sys/menus/65', '127.0.0.1', NULL, '[65,{\"createTime\":\"2025-06-21 09:29:22\",\"updateTime\":\"2025-11-17 17:12:03\",\"id\":65,\"parentId\":7,\"name\":\"Gitee\",\"type\":2,\"path\":\"https://gitee.com/leven2018/lanjii\",\"component\":null,\"permission\":\"\",\"icon\":\"QuestionFilled\",\"sortOrder\":1,\"isVisible\":1,\"isEnabled\":1,\"isExt\":1,\"openMode\":1}]', '{\"code\":200,\"msg\":\"成功\",\"data\":null}', 1, NULL, '2025-12-02 22:00:19', 216, '2025-12-02 22:00:19', '2025-12-02 22:00:19', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (80, '菜单管理', 1, 'update', 'PUT', 'admin', NULL, '/admin/sys/menus/66', '127.0.0.1', NULL, '[66,{\"createTime\":\"2025-06-21 09:29:22\",\"updateTime\":\"2025-11-17 17:12:03\",\"id\":66,\"parentId\":7,\"name\":\"Element Plus\",\"type\":2,\"path\":\"https://element-plus.org/\",\"component\":null,\"permission\":\"\",\"icon\":\"ElementPlus\",\"sortOrder\":2,\"isVisible\":1,\"isEnabled\":1,\"isExt\":1,\"openMode\":0}]', '{\"code\":200,\"msg\":\"成功\",\"data\":null}', 1, NULL, '2025-12-02 22:00:32', 237, '2025-12-02 22:00:32', '2025-12-02 22:00:32', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (81, '分配角色权限', 1, 'assignMenusToRole', 'POST', 'admin', NULL, '/admin/sys/roles/3/menus', '127.0.0.1', NULL, '[3,{\"menuIds\":[74,50,77,78,79,86,51,80,81,82,87,52,83,84,85,102,111,53,101,103,104,105,54,88,89,90,91,97,98,100,55,92,93,94,95,96,112,4,57,59,113,60,5,61,108,110,62,106,109,3,49]}]', '{\"code\":200,\"msg\":\"成功\",\"data\":null}', 1, NULL, '2025-12-02 22:23:00', 1707, '2025-12-02 22:23:00', '2025-12-02 22:23:00', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (82, '发布通知', 0, 'publishNotice', 'POST', 'admin', NULL, '/admin/notice', '127.0.0.1', NULL, '[{\"createTime\":null,\"updateTime\":null,\"id\":null,\"title\":\"和我\",\"content\":\"<p>为我访问</p>\",\"status\":null,\"keyword\":null,\"readStatus\":null}]', '{\"code\":200,\"msg\":\"成功\",\"data\":24}', 1, NULL, '2025-12-03 21:21:34', 231, '2025-12-03 21:21:34', '2025-12-03 21:21:34', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (83, '发布通知', 0, 'publishNotice', 'POST', 'admin', NULL, '/admin/notice', '127.0.0.1', NULL, '[{\"createTime\":null,\"updateTime\":null,\"id\":null,\"title\":\"33\",\"content\":\"<p>11</p>\",\"status\":null,\"keyword\":null,\"readStatus\":null}]', '{\"code\":200,\"msg\":\"成功\",\"data\":25}', 1, NULL, '2025-12-03 21:22:16', 221, '2025-12-03 21:22:16', '2025-12-03 21:22:16', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (84, '发布通知', 0, 'publishNotice', 'POST', 'admin', NULL, '/admin/notice', '127.0.0.1', NULL, '[{\"createTime\":null,\"updateTime\":null,\"id\":null,\"title\":\"11\",\"content\":\"<p>233</p>\",\"status\":null,\"keyword\":null,\"readStatus\":null}]', '{\"code\":200,\"msg\":\"成功\",\"data\":26}', 1, NULL, '2025-12-03 21:42:06', 189, '2025-12-03 21:42:06', '2025-12-03 21:42:06', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (85, '发布通知', 0, 'publishNotice', 'POST', 'admin', NULL, '/admin/notice', '127.0.0.1', NULL, '[{\"createTime\":null,\"updateTime\":null,\"id\":null,\"title\":\"333\",\"content\":\"<p>2</p>\",\"status\":null,\"keyword\":null,\"readStatus\":null}]', '{\"code\":200,\"msg\":\"成功\",\"data\":27}', 1, NULL, '2025-12-03 21:42:26', 141, '2025-12-03 21:42:26', '2025-12-03 21:42:26', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (86, '发布通知', 0, 'publishNotice', 'POST', 'admin', NULL, '/admin/notice', '127.0.0.1', NULL, '[{\"createTime\":null,\"updateTime\":null,\"id\":null,\"title\":\"333\",\"content\":\"<p>2</p>\",\"status\":null,\"keyword\":null,\"readStatus\":null}]', '{\"code\":200,\"msg\":\"成功\",\"data\":28}', 1, NULL, '2025-12-03 21:44:22', 130, '2025-12-03 21:44:22', '2025-12-03 21:44:22', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (87, '发布通知', 0, 'publishNotice', 'POST', 'admin', NULL, '/admin/notice', '127.0.0.1', NULL, '[{\"createTime\":null,\"updateTime\":null,\"id\":null,\"title\":\"33\",\"content\":\"<p>11</p>\",\"status\":null,\"keyword\":null,\"readStatus\":null}]', '{\"code\":200,\"msg\":\"成功\",\"data\":29}', 1, NULL, '2025-12-03 21:45:21', 188, '2025-12-03 21:45:21', '2025-12-03 21:45:21', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (88, '发布通知', 0, 'publishNotice', 'POST', 'admin', NULL, '/admin/notice', '127.0.0.1', NULL, '[{\"createTime\":null,\"updateTime\":null,\"id\":null,\"title\":\"33\",\"content\":\"<p>11</p>\",\"status\":null,\"keyword\":null,\"readStatus\":null}]', '{\"code\":200,\"msg\":\"成功\",\"data\":30}', 1, NULL, '2025-12-03 21:53:11', 113, '2025-12-03 21:53:11', '2025-12-03 21:53:11', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (89, '发布通知', 0, 'publishNotice', 'POST', 'admin', NULL, '/admin/notice', '127.0.0.1', NULL, '[{\"createTime\":null,\"updateTime\":null,\"id\":null,\"title\":\"341\",\"content\":\"<p>111</p>\",\"status\":null,\"keyword\":null,\"readStatus\":null}]', '{\"code\":200,\"msg\":\"成功\",\"data\":31}', 1, NULL, '2025-12-03 21:53:23', 161, '2025-12-03 21:53:23', '2025-12-03 21:53:23', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (90, '发布通知', 0, 'publishNotice', 'POST', 'admin', NULL, '/admin/notice', '127.0.0.1', NULL, '[{\"createTime\":null,\"updateTime\":null,\"id\":null,\"title\":\"31231\",\"content\":\"<p>111</p>\",\"status\":null,\"keyword\":null,\"readStatus\":null}]', '{\"code\":200,\"msg\":\"成功\",\"data\":32}', 1, NULL, '2025-12-03 21:58:12', 353, '2025-12-03 21:58:12', '2025-12-03 21:58:12', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (91, '菜单管理', 2, 'delete', 'DELETE', 'admin', NULL, '/admin/sys/menus/57', '127.0.0.1', NULL, '[57]', '{\"code\":200,\"msg\":\"成功\",\"data\":null}', 1, NULL, '2025-12-03 22:20:28', 146, '2025-12-03 22:20:28', '2025-12-03 22:20:28', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (92, '菜单管理', 1, 'update', 'PUT', 'admin', NULL, '/admin/sys/menus/114', '127.0.0.1', NULL, '[114,{\"createTime\":\"2025-11-27 20:15:11\",\"updateTime\":\"2025-11-27 20:15:11\",\"id\":114,\"parentId\":0,\"name\":\"通知公告\",\"type\":1,\"path\":\"\",\"component\":\"\",\"permission\":\"\",\"icon\":\"Bell\",\"sortOrder\":3,\"isVisible\":1,\"isEnabled\":1,\"isExt\":0,\"openMode\":0}]', '{\"code\":200,\"msg\":\"成功\",\"data\":null}', 1, NULL, '2025-12-03 22:21:08', 110, '2025-12-03 22:21:08', '2025-12-03 22:21:08', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (93, '菜单管理', 1, 'update', 'PUT', 'admin', NULL, '/admin/sys/menus/3', '127.0.0.1', NULL, '[3,{\"createTime\":\"2025-06-21 09:29:22\",\"updateTime\":\"2025-11-17 17:12:02\",\"id\":3,\"parentId\":0,\"name\":\"系统管理\",\"type\":1,\"path\":null,\"component\":null,\"permission\":null,\"icon\":\"Setting\",\"sortOrder\":2,\"isVisible\":1,\"isEnabled\":1,\"isExt\":0,\"openMode\":0}]', '{\"code\":200,\"msg\":\"成功\",\"data\":null}', 1, NULL, '2025-12-03 22:21:34', 122, '2025-12-03 22:21:34', '2025-12-03 22:21:34', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (94, '菜单管理', 1, 'update', 'PUT', 'admin', NULL, '/admin/sys/menus/6', '127.0.0.1', NULL, '[6,{\"createTime\":\"2025-06-21 09:29:22\",\"updateTime\":\"2025-11-17 17:12:02\",\"id\":6,\"parentId\":0,\"name\":\"系统工具\",\"type\":1,\"path\":null,\"component\":null,\"permission\":null,\"icon\":\"SetUp\",\"sortOrder\":4,\"isVisible\":1,\"isEnabled\":1,\"isExt\":0,\"openMode\":0}]', '{\"code\":200,\"msg\":\"成功\",\"data\":null}', 1, NULL, '2025-12-03 22:21:51', 154, '2025-12-03 22:21:51', '2025-12-03 22:21:51', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (95, '菜单管理', 1, 'update', 'PUT', 'admin', NULL, '/admin/sys/menus/7', '127.0.0.1', NULL, '[7,{\"createTime\":\"2025-06-21 09:29:22\",\"updateTime\":\"2025-11-17 17:12:02\",\"id\":7,\"parentId\":0,\"name\":\"外部链接\",\"type\":1,\"path\":null,\"component\":null,\"permission\":null,\"icon\":\"Link\",\"sortOrder\":10,\"isVisible\":1,\"isEnabled\":1,\"isExt\":0,\"openMode\":0}]', '{\"code\":200,\"msg\":\"成功\",\"data\":null}', 1, NULL, '2025-12-03 22:21:58', 144, '2025-12-03 22:21:58', '2025-12-03 22:21:58', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (96, '菜单管理', 1, 'update', 'PUT', 'admin', NULL, '/admin/sys/menus/5', '127.0.0.1', NULL, '[5,{\"createTime\":\"2025-06-21 09:29:22\",\"updateTime\":\"2025-11-17 17:12:02\",\"id\":5,\"parentId\":0,\"name\":\"日志管理\",\"type\":1,\"path\":null,\"component\":null,\"permission\":\"\",\"icon\":\"Tickets\",\"sortOrder\":9,\"isVisible\":1,\"isEnabled\":1,\"isExt\":0,\"openMode\":0}]', '{\"code\":200,\"msg\":\"成功\",\"data\":null}', 1, NULL, '2025-12-03 22:22:05', 94, '2025-12-03 22:22:05', '2025-12-03 22:22:05', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (97, '菜单管理', 1, 'update', 'PUT', 'admin', NULL, '/admin/sys/menus/1', '127.0.0.1', NULL, '[1,{\"createTime\":\"2025-06-21 09:29:22\",\"updateTime\":\"2025-11-17 17:12:02\",\"id\":1,\"parentId\":0,\"name\":\"控制台\",\"type\":1,\"path\":\"/admin/index\",\"component\":\"admin/index\",\"permission\":\"\",\"icon\":\"DataBoard\",\"sortOrder\":1,\"isVisible\":1,\"isEnabled\":1,\"isExt\":0,\"openMode\":0}]', '{\"code\":200,\"msg\":\"成功\",\"data\":null}', 1, NULL, '2025-12-03 22:22:52', 207, '2025-12-03 22:22:52', '2025-12-03 22:22:52', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (98, '菜单管理', 1, 'update', 'PUT', 'admin', NULL, '/admin/sys/menus/4', '127.0.0.1', NULL, '[4,{\"createTime\":\"2025-06-21 09:29:22\",\"updateTime\":\"2025-11-17 17:12:02\",\"id\":4,\"parentId\":0,\"name\":\"系统监控\",\"type\":1,\"path\":null,\"component\":null,\"permission\":null,\"icon\":\"Monitor\",\"sortOrder\":6,\"isVisible\":1,\"isEnabled\":1,\"isExt\":0,\"openMode\":0}]', '{\"code\":200,\"msg\":\"成功\",\"data\":null}', 1, NULL, '2025-12-03 22:26:54', 105, '2025-12-03 22:26:54', '2025-12-03 22:26:54', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (99, '修改个人信息', 1, 'updateProfile', 'PUT', 'admin', NULL, '/admin/sys/users/me', '127.0.0.1', NULL, '[{\"nickname\":\"系统管理员1\",\"email\":\"1admin@example.com\",\"phone\":\"13800021113\",\"avatar\":\"http://127.0.0.1:8080/upload\\\\2025\\\\11\\\\14\\\\dff26c38-604a-42a7-8949-b573ca1334a9.jpg\"}]', '{\"code\":200,\"msg\":\"成功\",\"data\":null}', 1, NULL, '2025-12-04 14:40:16', 28, '2025-12-04 14:40:16', '2025-12-04 14:40:16', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (100, '修改个人信息', 1, 'updateProfile', 'PUT', 'admin', NULL, '/admin/sys/users/me', '127.0.0.1', NULL, '[{\"nickname\":\"系统管理员\",\"email\":\"admin@example.com\",\"phone\":\"13800021113\",\"avatar\":\"http://127.0.0.1:8080/upload\\\\2025\\\\11\\\\14\\\\dff26c38-604a-42a7-8949-b573ca1334a9.jpg\"}]', '{\"code\":200,\"msg\":\"成功\",\"data\":null}', 1, NULL, '2025-12-04 14:40:21', 28, '2025-12-04 14:40:21', '2025-12-04 14:40:21', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (101, '菜单管理', 1, 'update', 'PUT', 'admin', NULL, '/admin/sys/menus/60', '127.0.0.1', NULL, '[60,{\"createTime\":\"2025-06-21 09:29:22\",\"updateTime\":\"2025-11-17 17:12:03\",\"id\":60,\"parentId\":4,\"name\":\"SQL监控\",\"type\":2,\"path\":\"http://localhost:8080/druid/login.html\",\"component\":\"monitor/sql/index\",\"permission\":\"\",\"icon\":\"DataLine\",\"sortOrder\":4,\"isVisible\":1,\"isEnabled\":1,\"isExt\":1,\"openMode\":0}]', '{\"code\":200,\"msg\":\"成功\",\"data\":null}', 1, NULL, '2025-12-04 14:48:59', 37, '2025-12-04 14:48:59', '2025-12-04 14:48:59', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (102, '系统配置管理', 1, 'update', 'PUT', 'admin', NULL, '/admin/sys/configs/26', '121.60.126.44', NULL, '[26,{\"id\":26,\"configName\":\"静态资源映射路径\",\"configKey\":\"UPLOAD_ROOT_PATH\",\"configValue\":\"/opt/app/lanjii/upload/\",\"configType\":1,\"isEnabled\":1,\"remark\":\"\"}]', '{\"code\":200,\"msg\":\"成功\",\"data\":null}', 1, NULL, '2025-12-05 16:14:53', 42, '2025-12-05 16:14:53', '2025-12-05 16:14:53', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (103, '系统配置管理', 1, 'update', 'PUT', 'admin', NULL, '/admin/sys/configs/25', '121.60.126.44', NULL, '[25,{\"id\":25,\"configName\":\"文件服务器地址前缀\",\"configKey\":\"FILE_SERVER_BASE_URL\",\"configValue\":\"http://106.54.167.194/\",\"configType\":1,\"isEnabled\":1,\"remark\":\"文件访问的服务器地址前缀，包含协议、IP和端口。示例：http://192.168.1.100:8080 或 https://files.example.com\"}]', '{\"code\":200,\"msg\":\"成功\",\"data\":null}', 1, NULL, '2025-12-05 16:15:08', 32, '2025-12-05 16:15:08', '2025-12-05 16:15:08', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (104, '清除配置缓存', 1, 'clearCache', 'POST', 'admin', NULL, '/admin/sys/configs/cache/clear', '121.60.126.44', NULL, '', '{\"code\":200,\"msg\":\"成功\",\"data\":null}', 1, NULL, '2025-12-05 16:15:45', 2, '2025-12-05 16:15:45', '2025-12-05 16:15:45', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (105, '用户管理', 0, 'save', 'POST', 'admin', NULL, '/admin/sys/users', '183.14.132.14', NULL, '[{\"createTime\":null,\"updateTime\":null,\"id\":null,\"username\":\"wang\",\"password\":null,\"nickname\":\"min\",\"email\":\"michael.wang@neware.site\",\"phone\":\"13434872470\",\"avatar\":\"\",\"isEnabled\":1,\"deptId\":8,\"postIds\":[],\"roleIds\":[1,5]}]', '{\"code\":200,\"msg\":\"成功\",\"data\":null}', 1, NULL, '2025-12-05 16:20:07', 113, '2025-12-05 16:20:07', '2025-12-05 16:20:07', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (106, '发布通知', 0, 'publishNotice', 'POST', 'admin', NULL, '/admin/notice', '121.60.126.44', NULL, '[{\"createTime\":null,\"updateTime\":null,\"id\":null,\"title\":\"111\",\"content\":\"<p>22</p>\",\"status\":null,\"keyword\":null,\"readStatus\":null}]', '{\"code\":200,\"msg\":\"成功\",\"data\":33}', 1, NULL, '2025-12-05 17:37:42', 60, '2025-12-05 17:37:42', '2025-12-05 17:37:42', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (107, '部门管理', 2, 'delete', 'DELETE', 'admin', NULL, '/admin/sys/depts/27', '43.227.138.54', NULL, '[27]', '{\"code\":200,\"msg\":\"成功\",\"data\":null}', 1, NULL, '2025-12-05 19:25:24', 34, '2025-12-05 19:25:24', '2025-12-05 19:25:24', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (108, '切换用户状态', 1, 'toggleStatus', 'PUT', 'admin', NULL, '/admin/sys/users/28/status', '43.227.136.95', NULL, '[28]', '{\"code\":200,\"msg\":\"成功\",\"data\":null}', 1, NULL, '2025-12-05 19:46:58', 28, '2025-12-05 19:46:58', '2025-12-05 19:46:58', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (109, '切换用户状态', 1, 'toggleStatus', 'PUT', 'admin', NULL, '/admin/sys/users/28/status', '43.227.136.95', NULL, '[28]', '{\"code\":200,\"msg\":\"成功\",\"data\":null}', 1, NULL, '2025-12-05 19:46:59', 26, '2025-12-05 19:46:59', '2025-12-05 19:46:59', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (110, '发布通知', 0, 'publishNotice', 'POST', 'admin', NULL, '/admin/notice', '121.60.126.44', NULL, '[{\"createTime\":null,\"updateTime\":null,\"id\":null,\"title\":\"33\",\"content\":\"<p>11123</p>\",\"status\":null,\"keyword\":null,\"readStatus\":null}]', '{\"code\":200,\"msg\":\"成功\",\"data\":34}', 1, NULL, '2025-12-08 09:41:25', 43, '2025-12-08 09:41:25', '2025-12-08 09:41:25', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (111, '系统配置管理', 1, 'update', 'PUT', 'admin', NULL, '/admin/sys/configs/25', '121.60.126.44', NULL, '[25,{\"id\":25,\"configName\":\"文件服务器地址前缀\",\"configKey\":\"FILE_SERVER_BASE_URL\",\"configValue\":\"http://106.54.167.194/api\",\"configType\":1,\"isEnabled\":1,\"remark\":\"文件访问的服务器地址前缀，包含协议、IP和端口。示例：http://192.168.1.100:8080 或 https://files.example.com\"}]', '{\"code\":200,\"msg\":\"成功\",\"data\":null}', 1, NULL, '2025-12-08 14:54:32', 40, '2025-12-08 14:54:32', '2025-12-08 14:54:32', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (112, '清除配置缓存', 1, 'clearCache', 'POST', 'admin', NULL, '/admin/sys/configs/cache/clear', '121.60.126.44', NULL, '', '{\"code\":200,\"msg\":\"成功\",\"data\":null}', 1, NULL, '2025-12-08 14:54:36', 0, '2025-12-08 14:54:36', '2025-12-08 14:54:36', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (113, '系统配置管理', 1, 'update', 'PUT', 'admin', NULL, '/admin/sys/configs/25', '121.60.126.44', NULL, '[25,{\"id\":25,\"configName\":\"文件服务器地址前缀\",\"configKey\":\"FILE_SERVER_BASE_URL\",\"configValue\":\"http://106.54.167.194/files\",\"configType\":1,\"isEnabled\":1,\"remark\":\"文件访问的服务器地址前缀，包含协议、IP和端口。示例：http://192.168.1.100:8080 或 https://files.example.com\"}]', '{\"code\":200,\"msg\":\"成功\",\"data\":null}', 1, NULL, '2025-12-08 14:59:12', 32, '2025-12-08 14:59:12', '2025-12-08 14:59:12', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (114, '系统配置管理', 1, 'update', 'PUT', 'admin', NULL, '/admin/sys/configs/25', '121.60.126.44', NULL, '[25,{\"id\":25,\"configName\":\"文件服务器地址前缀\",\"configKey\":\"FILE_SERVER_BASE_URL\",\"configValue\":\"http://106.54.167.194\",\"configType\":1,\"isEnabled\":1,\"remark\":\"文件访问的服务器地址前缀，包含协议、IP和端口。示例：http://192.168.1.100:8080 或 https://files.example.com\"}]', '{\"code\":200,\"msg\":\"成功\",\"data\":null}', 1, NULL, '2025-12-08 15:00:49', 31, '2025-12-08 15:00:49', '2025-12-08 15:00:49', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (115, '修改个人信息', 1, 'updateProfile', 'PUT', 'admin', NULL, '/admin/sys/users/me', '121.60.126.44', NULL, '[{\"nickname\":\"系统管理员\",\"email\":\"admin@example.com\",\"phone\":\"13800021113\",\"avatar\":\"http://106.54.167.194//upload/20251208/82301499-2a90-4b2b-be41-9269980091e6.jpg\"}]', '{\"code\":200,\"msg\":\"成功\",\"data\":null}', 1, NULL, '2025-12-08 15:01:04', 31, '2025-12-08 15:01:04', '2025-12-08 15:01:04', 'admin', 'admin', 0);
INSERT INTO `sys_oper_log` VALUES (116, '发布通知', 0, 'publishNotice', 'POST', 'admin', NULL, '/admin/notice', '121.60.125.198', NULL, '[{\"createTime\":null,\"updateTime\":null,\"id\":null,\"title\":\"333\",\"content\":\"<p>11</p>\",\"status\":null,\"keyword\":null,\"readStatus\":null}]', '{\"code\":200,\"msg\":\"成功\",\"data\":35}', 1, NULL, '2025-12-16 08:35:01', 51, '2025-12-16 08:35:01', '2025-12-16 08:35:01', 'admin', 'admin', 0);

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
INSERT INTO `sys_post` VALUES (13, '1', '2', 1, 1, '3', '2025-07-01 15:40:43', '2025-07-01 15:40:48', '', '', 1);
INSERT INTO `sys_post` VALUES (14, '222', '111', 0, 1, '', '2025-07-02 15:32:09', '2025-07-02 15:32:14', '', '', 1);
INSERT INTO `sys_post` VALUES (15, '11', '22', 0, 1, '', '2025-09-18 21:45:09', '2025-09-18 21:45:09', '', '', 0);
INSERT INTO `sys_post` VALUES (16, '3', '33', 0, 1, '', '2025-09-18 21:45:14', '2025-09-18 23:49:34', '', '', 1);
INSERT INTO `sys_post` VALUES (19, '112', '22', 1, 1, '', '2025-09-18 21:49:16', '2025-09-18 21:49:16', '', '', 0);
INSERT INTO `sys_post` VALUES (24, 'ce0', '111', 0, 1, '', '2025-09-18 23:39:51', '2025-09-18 23:48:55', '', '', 1);
INSERT INTO `sys_post` VALUES (25, 'ceo1', '11', 1, 1, '', '2025-09-18 23:40:19', '2025-09-18 23:46:52', '', '', 0);
INSERT INTO `sys_post` VALUES (26, '董事长', 'eo', 0, 1, '', '2025-09-24 21:15:03', '2025-09-24 21:15:03', '', 'admin', 0);
INSERT INTO `sys_post` VALUES (27, '111', 'ceo', 0, 0, '', '2025-09-25 23:06:18', '2025-09-25 23:06:18', '', 'admin', 0);
INSERT INTO `sys_post` VALUES (28, '2', '11', 0, 1, '', '2025-11-25 19:40:38', '2025-11-25 19:40:43', 'admin', 'admin', 1);

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
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'super_admin', 1, 1, '拥有系统所有权限', '2025-06-21 09:31:38', '2025-06-21 09:31:38', 'system', 'system', 0);
INSERT INTO `sys_role` VALUES (2, '系统管理员', 'admin', 2, 1, '管理系统基础配置', '2025-06-21 09:31:38', '2025-06-21 09:31:38', 'system', 'system', 0);
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
INSERT INTO `sys_role` VALUES (23, '111', '222', 0, 1, '333', '2025-07-01 14:37:46', '2025-07-01 14:49:42', NULL, NULL, 1);
INSERT INTO `sys_role` VALUES (24, '11', '22', 0, 1, '33', '2025-07-01 14:59:39', '2025-07-01 14:59:50', NULL, NULL, 1);
INSERT INTO `sys_role` VALUES (27, '1133', '221', 0, 1, '3', '2025-07-01 15:08:33', '2025-07-01 15:08:48', NULL, NULL, 1);
INSERT INTO `sys_role` VALUES (28, '1', '2', 0, 1, '', '2025-07-01 15:10:38', '2025-07-01 15:10:51', NULL, NULL, 1);
INSERT INTO `sys_role` VALUES (29, '3', '1', 0, 1, '', '2025-07-01 15:12:42', '2025-07-02 15:32:51', NULL, NULL, 1);
INSERT INTO `sys_role` VALUES (30, '33', '3', 0, 1, '', '2025-07-01 15:22:17', '2025-07-01 15:22:35', NULL, NULL, 1);
INSERT INTO `sys_role` VALUES (34, '22', '223', 0, 1, '', '2025-07-02 15:32:41', '2025-07-02 15:32:49', NULL, NULL, 1);
INSERT INTO `sys_role` VALUES (38, '2', '11', 0, 1, '', '2025-09-18 21:53:31', '2025-11-11 21:35:45', NULL, 'admin', 1);
INSERT INTO `sys_role` VALUES (43, '超级管理员1', 'admin1', 0, 1, '22', '2025-09-24 21:19:42', '2025-11-11 22:24:38', NULL, 'admin', 1);

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
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '$2a$10$Ke7.fLoqfUyIcVPYTR5GXeLi.Bj4G/IyuJRZXaqZffJCuSGHEQ78K', '系统管理员', 'admin@example.com', '13800021113', 'http://106.54.167.194//upload/20251208/82301499-2a90-4b2b-be41-9269980091e6.jpg', 1, 1, '2025-12-16 09:52:53', '121.60.125.198', 1, '2025-06-21 09:20:10', '2025-12-16 09:52:53', 'system', NULL, 0);
INSERT INTO `sys_user` VALUES (2, 'zhangsan', '$2a$10$U6g80GGnt.g4vxWSh3/0f.Qy2lCNqo3R1R/97MdSJgvPa0.Ci7vbe', '张三', 'zhangsan@company.com', '13900001111', 'https://example.com/avatars/zhangsan.jpg', 1, 1, '2023-06-16 09:15:00', '192.168.1.101', NULL, '2025-06-21 09:20:10', '2025-07-02 13:11:39', 'admin', 'admin', 0);
INSERT INTO `sys_user` VALUES (3, 'lisi', '$2a$10$DSBCdR7PS0BSrEXrJfkEz.DKEwPbVbzxf84Y6izM.ptRbRugLreWi', '李四', 'lisi@company.com', '13900002222', 'https://example.com/avatars/lisi.jpg', 1, 2, '2023-06-16 10:20:00', '192.168.1.102', NULL, '2025-06-21 09:20:10', '2025-09-18 22:02:11', 'admin', 'admin', 0);
INSERT INTO `sys_user` VALUES (4, 'wangwu', '$2a$10$U6g80GGnt.g4vxWSh3/0f.Qy2lCNqo3R1R/97MdSJgvPa0.Ci7vbe', '王五', 'wangwu@company.com', '13900003333', 'https://example.com/avatars/wangwu.jpg', 1, 3, '2023-06-16 11:30:00', '192.168.1.103', NULL, '2025-06-21 09:20:10', '2025-07-02 13:11:42', 'admin', 'admin', 0);
INSERT INTO `sys_user` VALUES (5, 'zhaoliu', '$2a$10$U6g80GGnt.g4vxWSh3/0f.Qy2lCNqo3R1R/97MdSJgvPa0.Ci7vbe', '赵六', 'zhaoliu@company.com', '13900004444', 'https://example.com/avatars/zhaoliu.jpg', 1, 4, '2023-06-16 14:45:00', '192.168.1.104', NULL, '2025-06-21 09:20:10', '2025-07-02 13:11:44', 'admin', 'admin', 0);
INSERT INTO `sys_user` VALUES (6, 'dev_manager', '$2a$10$U6g80GGnt.g4vxWSh3/0f.Qy2lCNqo3R1R/97MdSJgvPa0.Ci7vbe', '张开发', 'dev_manager@company.com', '13900005555', 'https://example.com/avatars/dev_manager.jpg', 1, 0, '2023-06-15 13:20:00', '192.168.1.105', NULL, '2025-06-21 09:20:10', '2025-06-21 15:36:54', 'admin', 'admin', 0);
INSERT INTO `sys_user` VALUES (7, 'test_manager', '$2a$10$U6g80GGnt.g4vxWSh3/0f.Qy2lCNqo3R1R/97MdSJgvPa0.Ci7vbe', '李测试', 'test_manager@company.com', '13900006666', 'https://example.com/avatars/test_manager.jpg', 1, 0, '2023-06-15 14:30:00', '192.168.1.106', NULL, '2025-06-21 09:20:10', '2025-06-21 15:36:54', 'admin', 'admin', 0);
INSERT INTO `sys_user` VALUES (8, 'product_manager', '$2a$10$U6g80GGnt.g4vxWSh3/0f.Qy2lCNqo3R1R/97MdSJgvPa0.Ci7vbe', '王产品', 'product_manager@company.com', '13900007777', 'https://example.com/avatars/product_manager.jpg', 1, 0, '2023-06-15 15:40:00', '192.168.1.107', NULL, '2025-06-21 09:20:10', '2025-06-21 15:36:54', 'admin', 'admin', 0);
INSERT INTO `sys_user` VALUES (9, 'disabled_user', '$2a$10$U6g80GGnt.g4vxWSh3/0f.Qy2lCNqo3R1R/97MdSJgvPa0.Ci7vbe', '禁用用户', 'disabled@example.com', '13900008888', 'https://example.com/avatars/disabled.jpg', 0, 0, '2023-05-20 16:50:00', '192.168.1.108', NULL, '2025-06-21 09:20:10', '2025-06-21 15:36:54', 'admin', 'admin', 0);
INSERT INTO `sys_user` VALUES (10, 'deleted_user', '$2a$10$U6g80GGnt.g4vxWSh3/0f.Qy2lCNqo3R1R/97MdSJgvPa0.Ci7vbe', '已删除用户', 'deleted@example.com', '13900009999', 'https://example.com/avatars/deleted.jpg', 1, 0, '2023-05-10 17:00:00', '192.168.1.109', NULL, '2025-06-21 09:20:10', '2025-06-21 15:36:54', 'admin', 'admin', 1);
INSERT INTO `sys_user` VALUES (11, 'user001', '$2a$10$U6g80GGnt.g4vxWSh3/0f.Qy2lCNqo3R1R/97MdSJgvPa0.Ci7vbe', '测试用户1', 'user001@test.com', '13811112222', NULL, 1, 0, NULL, NULL, NULL, '2025-06-21 09:20:10', '2025-06-21 15:36:54', 'admin', 'admin', 0);
INSERT INTO `sys_user` VALUES (12, 'user002', '$2a$10$U6g80GGnt.g4vxWSh3/0f.Qy2lCNqo3R1R/97MdSJgvPa0.Ci7vbe', '测试用户2', 'user002@test.com', '13811113333', NULL, 1, 0, NULL, NULL, NULL, '2025-06-21 09:20:10', '2025-06-21 15:36:54', 'admin', 'admin', 0);
INSERT INTO `sys_user` VALUES (13, 'user003', '$2a$10$U6g80GGnt.g4vxWSh3/0f.Qy2lCNqo3R1R/97MdSJgvPa0.Ci7vbe', '测试用户3', 'user003@test.com', '13811114444', NULL, 1, 0, NULL, NULL, NULL, '2025-06-21 09:20:10', '2025-06-21 15:36:54', 'admin', 'admin', 0);
INSERT INTO `sys_user` VALUES (14, 'user004', '$2a$10$U6g80GGnt.g4vxWSh3/0f.Qy2lCNqo3R1R/97MdSJgvPa0.Ci7vbe', '测试用户4', 'user004@test.com', '13811115555', NULL, 1, 0, NULL, NULL, NULL, '2025-06-21 09:20:10', '2025-06-21 15:36:54', 'admin', 'admin', 0);
INSERT INTO `sys_user` VALUES (15, 'user005', '$2a$10$U6g80GGnt.g4vxWSh3/0f.Qy2lCNqo3R1R/97MdSJgvPa0.Ci7vbe', '测试用户5', 'user005@test.com', '13811116666', NULL, 1, 0, NULL, NULL, NULL, '2025-06-21 09:20:10', '2025-06-21 15:36:54', 'admin', 'admin', 0);
INSERT INTO `sys_user` VALUES (16, 'user006', '$2a$10$U6g80GGnt.g4vxWSh3/0f.Qy2lCNqo3R1R/97MdSJgvPa0.Ci7vbe', '测试用户6', 'user006@test.com', '13811117777', NULL, 1, 0, NULL, NULL, NULL, '2025-06-21 09:20:10', '2025-06-21 15:36:54', 'admin', 'admin', 0);
INSERT INTO `sys_user` VALUES (17, 'user007', '$2a$10$U6g80GGnt.g4vxWSh3/0f.Qy2lCNqo3R1R/97MdSJgvPa0.Ci7vbe', '测试用户7', 'user007@test.com', '13811118888', NULL, 1, 0, NULL, NULL, NULL, '2025-06-21 09:20:10', '2025-06-21 15:36:54', 'admin', 'admin', 0);
INSERT INTO `sys_user` VALUES (18, 'user008', '$2a$10$U6g80GGnt.g4vxWSh3/0f.Qy2lCNqo3R1R/97MdSJgvPa0.Ci7vbe', '测试用户8', 'user008@test.com', '13811119999', NULL, 1, 0, NULL, NULL, NULL, '2025-06-21 09:20:10', '2025-06-21 15:36:54', 'admin', 'admin', 0);
INSERT INTO `sys_user` VALUES (19, 'user009', '$2a$10$U6g80GGnt.g4vxWSh3/0f.Qy2lCNqo3R1R/97MdSJgvPa0.Ci7vbe', '测试用户9', 'user009@test.com', '13811110000', NULL, 1, 0, NULL, NULL, NULL, '2025-06-21 09:20:10', '2025-06-21 15:36:54', 'admin', 'admin', 0);
INSERT INTO `sys_user` VALUES (20, '1222', '123456', '23334', '11222221@qq.com', '', NULL, 1, 0, NULL, NULL, NULL, '2025-07-02 12:45:22', '2025-07-02 12:46:16', NULL, NULL, 1);
INSERT INTO `sys_user` VALUES (21, 'xxx2', '$2a$10$/aHwNTS0qnNOyIDpXNgeeO.NTiNgGiLWnmzUmfA7qElX9JdL0XxRK', 'xxxx', '11222221@qq.com', '', '', 1, 1, NULL, NULL, 1, '2025-07-02 16:43:31', '2025-10-01 18:05:54', NULL, NULL, 1);
INSERT INTO `sys_user` VALUES (22, '1212', 'qwe1234', '啊飒飒', '', '', NULL, 0, 1, NULL, NULL, 1, '2025-09-27 13:24:28', '2025-09-27 13:26:08', NULL, NULL, 1);
INSERT INTO `sys_user` VALUES (23, '四点', '$2a$10$LlHWNnWxL6UOEqeesg7IeeaMdfuMh7aPY3F5G4OSJ2E5UKkvxos/G', '为我1', '', '', '', 0, 1, NULL, NULL, 1, '2025-09-29 20:16:29', '2025-11-10 19:53:07', NULL, 'admin', 1);
INSERT INTO `sys_user` VALUES (24, '11', '$2a$10$ZGUvF.DIYZ6FehDhb1qu0eV54b1tE8KT0VJ3hjqP1Z..L8ljmL/fq', '李斯', 'leven111993@163.com', '13469995371', '', 0, 1, NULL, NULL, 0, '2025-10-01 09:34:29', '2025-10-01 10:17:54', NULL, NULL, 1);
INSERT INTO `sys_user` VALUES (25, '12121', '$2a$10$K0lAjvrRjaZhwY5SpWGDuegYILPwbfvPJGrWwMhQtyjeH.kEGo/.m', 'QWE11', '11222221@qq.com', '13469995372', NULL, 0, 4, NULL, NULL, 1, '2025-10-01 09:34:49', '2025-10-01 10:17:57', NULL, NULL, 1);
INSERT INTO `sys_user` VALUES (26, '12', '$2a$10$vMHCJ0LWEFYlT7DMBrbzDupVQB5y9AAU7Qpe6IbDtAOzEmxr8QgcK', '1', '', '', '', 0, 1, NULL, NULL, 1, '2025-10-14 22:34:39', '2025-11-10 19:53:03', 'admin', 'admin', 1);
INSERT INTO `sys_user` VALUES (27, 'test', '$2a$10$tzzh2im.AfPX.Vq7xYq9zOaf4Cq0dY9QB1vc66Re.RIgZz8nH08tS', '测试', '', '', NULL, 1, 18, '2025-12-02 22:23:08', '127.0.0.1', 0, '2025-11-11 22:25:22', '2025-12-02 22:23:08', 'admin', NULL, 0);
INSERT INTO `sys_user` VALUES (28, 'wang', '$2a$10$y8/uIcnyhV4.ymFFNRnMM.xoxc.Q2sPkZlGIGCxjeT0QPQLZg8lCK', 'min', 'michael.wang@neware.site', '13434872470', '', 1, 8, NULL, NULL, 1, '2025-12-05 16:20:07', '2025-12-05 16:20:07', 'admin', 'admin', 0);

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
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户-岗位关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------
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
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户角色关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (35, 27, 3);
INSERT INTO `sys_user_role` VALUES (36, 28, 1);
INSERT INTO `sys_user_role` VALUES (37, 28, 5);

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
) ENGINE = InnoDB AUTO_INCREMENT = 66 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统文件管理表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tool_file
-- ----------------------------
INSERT INTO `tool_file` VALUES (1, '57b61f21-3698-4372-b1c9-fff5bf589f83.SRT', 'DJI_20250622132848_0012_D.SRT', 'uploads\\files\\57b61f21-3698-4372-b1c9-fff5bf589f83.SRT', 127151, 'application/octet-stream', '.SRT', NULL, NULL, '2025-09-29 20:57:25', NULL, 0, NULL, '2025-09-29 20:57:25', '2025-09-29 20:58:23', NULL, NULL, 0);
INSERT INTO `tool_file` VALUES (2, '7dcdaab2-c75c-45a9-b9d7-5038dc3e5061.JPG', 'DJI_20250622131750_0008_D.JPG', 'uploads\\files\\7dcdaab2-c75c-45a9-b9d7-5038dc3e5061.JPG', 7434240, 'image/jpeg', '.JPG', NULL, NULL, '2025-09-29 20:58:00', NULL, 11, '2025-09-29 21:02:36', '2025-09-29 20:58:00', '2025-09-29 20:58:25', NULL, NULL, 0);
INSERT INTO `tool_file` VALUES (3, 'b7cba25c-1ab4-49f0-97fd-370e319bd1bc.DNG', 'DJI_20250622171408_0033_D.DNG', 'uploads\\files\\b7cba25c-1ab4-49f0-97fd-370e319bd1bc.DNG', 18992128, 'application/octet-stream', '.DNG', NULL, NULL, '2025-09-29 20:58:04', NULL, 4, '2025-09-29 21:01:12', '2025-09-29 20:58:04', '2025-09-29 20:58:27', NULL, NULL, 0);
INSERT INTO `tool_file` VALUES (4, 'bec09bc2-4d05-426e-9256-aa062d2d4ef0.xls', '用户列表.xls', 'uploads\\files\\bec09bc2-4d05-426e-9256-aa062d2d4ef0.xls', 4979, 'application/vnd.ms-excel', '.xls', NULL, NULL, '2025-10-03 10:18:09', NULL, 0, NULL, '2025-10-03 10:18:09', '2025-10-03 10:18:09', NULL, NULL, NULL);
INSERT INTO `tool_file` VALUES (5, '6f203c2f900a45ce9aabdae34625430b.xls', '用户列表.xls', 'uploads\\files\\6f203c2f900a45ce9aabdae34625430b.xls', 4979, 'application/vnd.ms-excel', '.xls', NULL, NULL, '2025-10-15 15:53:13', NULL, 0, NULL, '2025-10-15 15:53:13', '2025-10-15 15:53:13', NULL, NULL, NULL);
INSERT INTO `tool_file` VALUES (6, '512b0de21a774624bf69e7c8f6436dff.xls', '用户列表.xls', 'uploads\\files\\512b0de21a774624bf69e7c8f6436dff.xls', 4979, '文档', '.xls', NULL, NULL, '2025-10-15 16:15:33', NULL, 0, NULL, '2025-10-15 16:15:33', '2025-10-15 16:15:33', NULL, NULL, NULL);
INSERT INTO `tool_file` VALUES (7, '51c14d8e-a895-49f7-bde4-85572d9d6579.docx', '测试.docx', 'uploads\\files\\51c14d8e-a895-49f7-bde4-85572d9d6579.docx', 10063, 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', '.docx', NULL, NULL, '2025-11-11 19:31:40', NULL, 0, NULL, '2025-11-11 19:31:40', '2025-11-11 19:31:40', 'admin', 'admin', NULL);
INSERT INTO `tool_file` VALUES (8, '2859a224-ab37-4a6f-8148-0d33d59a2767.docx', '测试.docx', 'uploads\\files\\2859a224-ab37-4a6f-8148-0d33d59a2767.docx', 10063, 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', '.docx', NULL, NULL, '2025-11-11 19:31:58', NULL, 0, NULL, '2025-11-11 19:31:58', '2025-11-11 19:31:58', 'admin', 'admin', NULL);
INSERT INTO `tool_file` VALUES (9, '22802815-3445-4cb1-8ec8-88cedb123a9f.docx', '测试.docx', 'uploads\\files\\22802815-3445-4cb1-8ec8-88cedb123a9f.docx', 10063, 'application/vnd.openxmlformats-officedocument.wordprocessingml.document', '.docx', NULL, NULL, '2025-11-11 19:33:51', NULL, 0, NULL, '2025-11-11 19:33:51', '2025-11-11 19:33:51', 'admin', 'admin', 0);
INSERT INTO `tool_file` VALUES (10, '028ee033-d600-4d34-acc4-93313784e9d9.docx', '测试.docx', 'uploads\\files\\2025\\11\\11\\028ee033-d600-4d34-acc4-93313784e9d9.docx', 10063, 'DOCUMENT', '.docx', NULL, NULL, '2025-11-11 19:35:15', NULL, 0, NULL, '2025-11-11 19:35:15', '2025-11-11 19:35:15', NULL, NULL, 0);
INSERT INTO `tool_file` VALUES (11, '0020768c-2bf2-4198-9688-2ce8295fa6d2.docx', '测试.docx', 'uploads\\files\\2025\\11\\11\\0020768c-2bf2-4198-9688-2ce8295fa6d2.docx', 10063, 'DOCUMENT', '.docx', NULL, NULL, '2025-11-11 19:37:30', NULL, 0, NULL, '2025-11-11 19:37:30', '2025-11-11 19:37:30', NULL, NULL, 0);
INSERT INTO `tool_file` VALUES (12, 'c9578082-e05c-4fcb-b7fb-da31977a5028.json', '新建文本文档 (2).json', 'uploads\\files\\2025\\11\\11\\c9578082-e05c-4fcb-b7fb-da31977a5028.json', 9663, 'TEXT', '.json', NULL, NULL, '2025-11-11 19:37:42', NULL, 0, NULL, '2025-11-11 19:37:42', '2025-11-11 19:37:42', NULL, NULL, 0);
INSERT INTO `tool_file` VALUES (13, '1b7b0af3-3a98-43bf-a3db-db8d9b8b706b.docx', '测试.docx', 'uploads\\files\\2025\\11\\11\\1b7b0af3-3a98-43bf-a3db-db8d9b8b706b.docx', 10063, 'DOCUMENT', '.docx', NULL, NULL, '2025-11-11 20:42:47', NULL, 0, NULL, '2025-11-11 20:42:47', '2025-11-11 20:42:47', 'admin', 'admin', 0);
INSERT INTO `tool_file` VALUES (14, 'de62f77c-7b03-4249-8133-bf4ec6564a88.jpg', 'th.jpg', 'uploads\\files\\2025\\11\\13\\de62f77c-7b03-4249-8133-bf4ec6564a88.jpg', 326223, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-11-13 20:29:32', '2025-11-13 20:29:32', NULL, NULL, 0);
INSERT INTO `tool_file` VALUES (15, 'c7a6ea52-1b4f-4645-a8b0-fc69662874a5.jpg', 'th.jpg', 'uploads\\files\\2025\\11\\13\\c7a6ea52-1b4f-4645-a8b0-fc69662874a5.jpg', 326223, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-11-13 20:29:42', '2025-11-13 20:29:42', NULL, NULL, 0);
INSERT INTO `tool_file` VALUES (16, 'fbb1d4b0-9386-4ddd-9d7f-14946feface6.jpg', 'th.jpg', 'uploads\\files\\2025\\11\\13\\fbb1d4b0-9386-4ddd-9d7f-14946feface6.jpg', 326223, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-11-13 20:29:47', '2025-11-13 20:29:47', NULL, NULL, 0);
INSERT INTO `tool_file` VALUES (17, '9df39d25-20bf-4ca8-ba1e-6f27edfd19fe.jpg', 'th.jpg', 'uploads\\files\\2025\\11\\13\\9df39d25-20bf-4ca8-ba1e-6f27edfd19fe.jpg', 326223, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-11-13 20:31:12', '2025-11-13 20:31:12', NULL, NULL, 0);
INSERT INTO `tool_file` VALUES (18, '2883e3b0-23f6-41b2-9f89-fa33ed310434.jpg', 'th.jpg', 'uploads\\files\\2025\\11\\13\\2883e3b0-23f6-41b2-9f89-fa33ed310434.jpg', 326223, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-11-13 20:32:03', '2025-11-13 20:32:03', NULL, NULL, 0);
INSERT INTO `tool_file` VALUES (19, '306e1418-ffae-4a3f-b593-1c99599b86af.jpg', 'th.jpg', 'uploads\\files\\2025\\11\\13\\306e1418-ffae-4a3f-b593-1c99599b86af.jpg', 326223, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-11-13 21:24:21', '2025-11-13 21:24:21', NULL, NULL, 0);
INSERT INTO `tool_file` VALUES (20, 'aaf6a362-cc42-420a-b5bf-eae5ed8658ca.jpg', 'bingimg_20250514_1920x1080.jpg', 'uploads\\files\\2025\\11\\14\\aaf6a362-cc42-420a-b5bf-eae5ed8658ca.jpg', 258398, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-11-14 13:37:05', '2025-11-14 13:37:05', NULL, NULL, 0);
INSERT INTO `tool_file` VALUES (21, '899cb46e-6d13-4fb4-9e1c-86fdb221de5c.jpg', 'bingimg_20250514_1920x1080.jpg', 'uploads\\files\\2025\\11\\14\\899cb46e-6d13-4fb4-9e1c-86fdb221de5c.jpg', 258398, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-11-14 13:37:43', '2025-11-14 13:37:43', NULL, NULL, 0);
INSERT INTO `tool_file` VALUES (22, 'e176f17c-eb92-4e2b-b5de-94642c784c13.png', '11.png', 'uploads\\files\\2025\\11\\14\\e176f17c-eb92-4e2b-b5de-94642c784c13.png', 126241, 'OTHER', '.png', NULL, NULL, NULL, NULL, 0, NULL, '2025-11-14 13:38:05', '2025-11-14 13:38:05', NULL, NULL, 0);
INSERT INTO `tool_file` VALUES (23, '2db1dac3-66b3-4221-9e82-d34f2733e257.jpg', 'bingimg_20250514_1920x1080.jpg', 'uploads\\files\\2025\\11\\14\\2db1dac3-66b3-4221-9e82-d34f2733e257.jpg', 258398, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-11-14 13:38:14', '2025-11-14 13:38:14', NULL, NULL, 0);
INSERT INTO `tool_file` VALUES (24, '002cb2d9-7119-4fe9-9216-85689e5b98c0.png', '11.png', 'uploads\\files\\2025\\11\\14\\002cb2d9-7119-4fe9-9216-85689e5b98c0.png', 126241, 'OTHER', '.png', NULL, NULL, NULL, NULL, 0, NULL, '2025-11-14 13:38:45', '2025-11-14 13:38:45', NULL, NULL, 0);
INSERT INTO `tool_file` VALUES (25, '5b70a7d9-9076-472c-949d-5f4b5e06025a.jpg', 'bingimg_20250514_1920x1080.jpg', 'uploads\\files\\2025\\11\\14\\5b70a7d9-9076-472c-949d-5f4b5e06025a.jpg', 258398, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-11-14 13:39:44', '2025-11-14 13:39:44', NULL, NULL, 0);
INSERT INTO `tool_file` VALUES (26, 'bd9ba979-c36e-4447-a1e3-edc3bd31baf5.jpg', 'bingimg_20250514_1920x1080.jpg', 'uploads\\files\\2025\\11\\14\\bd9ba979-c36e-4447-a1e3-edc3bd31baf5.jpg', 258398, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-11-14 14:12:23', '2025-11-14 14:12:23', NULL, NULL, 0);
INSERT INTO `tool_file` VALUES (27, '939ac16a-c425-4d66-abc4-b1c1c8bc5571.jpg', 'bingimg_20250514_1920x1080.jpg', 'uploads\\files\\2025\\11\\14\\939ac16a-c425-4d66-abc4-b1c1c8bc5571.jpg', 258398, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-11-14 14:25:52', '2025-11-14 14:25:52', NULL, NULL, 0);
INSERT INTO `tool_file` VALUES (28, 'c8ab8910-0a90-4bc6-b902-bf65973cbe32.jpg', 'bingimg_20250514_1920x1080.jpg', 'uploads\\files\\2025\\11\\14\\c8ab8910-0a90-4bc6-b902-bf65973cbe32.jpg', 258398, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-11-14 14:27:21', '2025-11-14 14:27:21', NULL, NULL, 0);
INSERT INTO `tool_file` VALUES (29, '3e27b108-4699-4d0f-a45a-761a5f2949a7.jpg', 'bingimg_20250514_1920x1080.jpg', 'D:\\uploads\\files\\2025\\11\\14\\3e27b108-4699-4d0f-a45a-761a5f2949a7.jpg', 258398, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-11-14 14:30:39', '2025-11-14 14:30:39', NULL, NULL, 0);
INSERT INTO `tool_file` VALUES (30, '6bc9be72-11b7-4dfc-a03c-2ca482438976.jpg', 'bingimg_20250514_1920x1080.jpg', 'D:\\uploads\\files\\2025\\11\\14\\6bc9be72-11b7-4dfc-a03c-2ca482438976.jpg', 258398, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-11-14 14:31:35', '2025-11-14 14:31:35', NULL, NULL, 0);
INSERT INTO `tool_file` VALUES (31, '4280918d-1caf-49f8-818a-4faa6561bb3d.jpg', 'bingimg_20250514_1920x1080.jpg', 'D:\\uploads\\files\\2025\\11\\14\\4280918d-1caf-49f8-818a-4faa6561bb3d.jpg', 258398, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-11-14 14:34:31', '2025-11-14 14:34:31', NULL, NULL, 0);
INSERT INTO `tool_file` VALUES (32, '1b8417dc-e3d3-4c58-bff7-0bbe1e5fbf6e.jpg', 'bingimg_20250514_1920x1080.jpg', 'D:\\upload\\2025\\11\\14\\1b8417dc-e3d3-4c58-bff7-0bbe1e5fbf6e.jpg', 258398, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-11-14 15:47:48', '2025-11-14 15:47:48', NULL, NULL, 0);
INSERT INTO `tool_file` VALUES (33, 'a0fbcb31-90f1-4fcb-ab97-219111502a0a.jpg', 'bingimg_20250514_1920x1080.jpg', 'D:\\upload\\2025\\11\\14\\a0fbcb31-90f1-4fcb-ab97-219111502a0a.jpg', 258398, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-11-14 15:48:01', '2025-11-14 15:48:01', NULL, NULL, 0);
INSERT INTO `tool_file` VALUES (34, 'a03c5520-21dd-41d0-9832-8072c7bf3b04.jpg', 'bingimg_20250514_1920x1080.jpg', 'D:\\upload\\2025\\11\\14\\a03c5520-21dd-41d0-9832-8072c7bf3b04.jpg', 258398, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-11-14 16:00:38', '2025-11-14 16:00:38', NULL, NULL, 0);
INSERT INTO `tool_file` VALUES (35, 'ea225749-15ef-49b8-8eeb-6ae726d044ef.jpg', 'bingimg_20250514_1920x1080.jpg', 'D:\\upload\\2025\\11\\14\\ea225749-15ef-49b8-8eeb-6ae726d044ef.jpg', 258398, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-11-14 16:03:55', '2025-11-14 16:03:55', NULL, NULL, 0);
INSERT INTO `tool_file` VALUES (36, 'b70a9219-a638-4786-913e-e77f56897dbd.jpg', 'bingimg_20250514_1920x1080.jpg', 'D:\\upload\\2025\\11\\14\\b70a9219-a638-4786-913e-e77f56897dbd.jpg', 258398, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-11-14 16:05:26', '2025-11-14 16:05:26', NULL, NULL, 0);
INSERT INTO `tool_file` VALUES (37, '018ca12c-cfaa-446c-b54c-cd1583553cf0.jpg', 'bingimg_20250514_1920x1080.jpg', 'D:\\upload\\2025\\11\\14\\018ca12c-cfaa-446c-b54c-cd1583553cf0.jpg', 258398, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-11-14 16:22:34', '2025-11-14 16:22:34', NULL, NULL, 0);
INSERT INTO `tool_file` VALUES (38, 'c63e80b2-adca-43ed-aad0-42b24b407746.jpg', 'bingimg_20250514_1920x1080.jpg', 'D:\\upload\\2025\\11\\14\\c63e80b2-adca-43ed-aad0-42b24b407746.jpg', 258398, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-11-14 16:22:47', '2025-11-14 16:22:47', NULL, NULL, 0);
INSERT INTO `tool_file` VALUES (39, '7d0bde5f-a515-4283-855b-83815fd070c8.jpg', 'bingimg_20250514_1920x1080.jpg', 'D:\\upload\\2025\\11\\14\\7d0bde5f-a515-4283-855b-83815fd070c8.jpg', 258398, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-11-14 16:26:09', '2025-11-14 16:26:09', NULL, NULL, 0);
INSERT INTO `tool_file` VALUES (40, 'a11c76b9-819f-4c08-bfb0-a6716410b1ca.jpg', 'bingimg_20250514_1920x1080.jpg', 'D:\\upload\\2025\\11\\14\\a11c76b9-819f-4c08-bfb0-a6716410b1ca.jpg', 258398, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-11-14 16:26:40', '2025-11-14 16:26:40', NULL, NULL, 0);
INSERT INTO `tool_file` VALUES (41, 'bafc89a2-d8cb-43c1-8b84-7328cd8e0e19.jpg', 'bingimg_20250514_1920x1080.jpg', 'upload\\2025\\11\\14\\bafc89a2-d8cb-43c1-8b84-7328cd8e0e19.jpg', 258398, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-11-14 16:37:00', '2025-11-14 16:37:00', NULL, NULL, 0);
INSERT INTO `tool_file` VALUES (42, 'ab964d12-6fc4-4ffc-ba11-35de38a91f1e.jpg', 'bingimg_20250514_1920x1080.jpg', 'upload\\2025\\11\\14\\ab964d12-6fc4-4ffc-ba11-35de38a91f1e.jpg', 258398, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-11-14 16:40:40', '2025-11-14 16:40:40', NULL, NULL, 0);
INSERT INTO `tool_file` VALUES (43, '32cd66b3-3cd7-475c-872e-22e58c850136.jpg', 'bingimg_20250514_1920x1080.jpg', 'upload\\2025\\11\\14\\32cd66b3-3cd7-475c-872e-22e58c850136.jpg', 258398, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-11-14 16:47:20', '2025-11-14 16:47:20', NULL, NULL, 0);
INSERT INTO `tool_file` VALUES (44, '4dbcfac6-c054-4d48-ae1d-423f54437d7b.jpg', 'bingimg_20250514_1920x1080.jpg', 'upload\\2025\\11\\14\\4dbcfac6-c054-4d48-ae1d-423f54437d7b.jpg', 258398, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-11-14 16:47:48', '2025-11-14 16:47:48', NULL, NULL, 0);
INSERT INTO `tool_file` VALUES (45, 'f4c955e7-3ec5-4b3a-b638-263a54cc77ba.jpg', 'th (1).jpg', 'upload\\2025\\11\\14\\f4c955e7-3ec5-4b3a-b638-263a54cc77ba.jpg', 311061, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-11-14 16:48:25', '2025-11-14 16:48:25', NULL, NULL, 0);
INSERT INTO `tool_file` VALUES (46, 'dff26c38-604a-42a7-8949-b573ca1334a9.jpg', 'th.jpg', 'upload\\2025\\11\\14\\dff26c38-604a-42a7-8949-b573ca1334a9.jpg', 326223, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-11-14 19:32:18', '2025-11-14 19:32:18', NULL, NULL, 0);
INSERT INTO `tool_file` VALUES (47, '7a973c31-430c-4d04-893c-3df19e6d1207.exe', 'ksolaunch.exe', 'upload\\2025\\11\\15\\7a973c31-430c-4d04-893c-3df19e6d1207.exe', 1252344, 'OTHER', '.exe', NULL, NULL, NULL, NULL, 0, NULL, '2025-11-15 12:09:31', '2025-11-15 12:09:31', 'admin', 'admin', 0);
INSERT INTO `tool_file` VALUES (48, 'ed57096d-8255-479a-81f9-0a2e7d7fcddf.jpg', 'th.jpg', 'upload\\2025\\12\\02\\ed57096d-8255-479a-81f9-0a2e7d7fcddf.jpg', 326223, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-12-02 22:35:30', '2025-12-02 22:35:30', 'admin', 'admin', 0);
INSERT INTO `tool_file` VALUES (49, '639ce222-7602-4ff1-84d4-6c2e7c619d6e.jpg', 'th.jpg', 'upload\\2025\\12\\02\\639ce222-7602-4ff1-84d4-6c2e7c619d6e.jpg', 326223, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-12-02 22:35:34', '2025-12-02 22:35:34', 'admin', 'admin', 0);
INSERT INTO `tool_file` VALUES (50, '6dbb58d3-7c59-46fe-8ea0-ddc069b1765f.jpg', 'th.jpg', 'upload\\2025\\12\\02\\6dbb58d3-7c59-46fe-8ea0-ddc069b1765f.jpg', 326223, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-12-02 22:38:17', '2025-12-02 22:38:17', 'admin', 'admin', 0);
INSERT INTO `tool_file` VALUES (51, '7910471c-3dd6-423d-be55-8bb2fd159229.png', '【哲风壁纸】原野风光-户外场景.png', 'upload\\2025\\12\\04\\7910471c-3dd6-423d-be55-8bb2fd159229.png', 21784656, 'OTHER', '.png', NULL, NULL, NULL, NULL, 0, NULL, '2025-12-04 14:36:08', '2025-12-04 14:36:08', 'admin', 'admin', 0);
INSERT INTO `tool_file` VALUES (52, 'de675a30-3989-4bf4-9192-58514e892d6f.jpg', 'bingimg_20250514_1920x1080.jpg', 'D:/upload/2025/12/05/de675a30-3989-4bf4-9192-58514e892d6f.jpg', 258398, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-12-05 16:13:30', '2025-12-05 16:13:30', 'admin', 'admin', 0);
INSERT INTO `tool_file` VALUES (53, 'fe69468d-f842-4397-a43f-0a1b4d1c028b.jpg', 'bingimg_20250514_1920x1080.jpg', 'D:/upload/2025/12/05/fe69468d-f842-4397-a43f-0a1b4d1c028b.jpg', 258398, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-12-05 16:13:40', '2025-12-05 16:13:40', 'admin', 'admin', 0);
INSERT INTO `tool_file` VALUES (54, '4e8c0778-2230-4cd5-8a27-99c0b4d8d532.jpg', 'bingimg_20250514_1920x1080.jpg', 'D:/upload/2025/12/05/4e8c0778-2230-4cd5-8a27-99c0b4d8d532.jpg', 258398, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-12-05 16:15:20', '2025-12-05 16:15:20', 'admin', 'admin', 0);
INSERT INTO `tool_file` VALUES (55, '1f24a521-aff4-4726-be1f-cd0c89beed56.jpg', 'bingimg_20250514_1920x1080.jpg', 'D:/upload/2025/12/05/1f24a521-aff4-4726-be1f-cd0c89beed56.jpg', 258398, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-12-05 16:15:33', '2025-12-05 16:15:33', 'admin', 'admin', 0);
INSERT INTO `tool_file` VALUES (56, '197ed507-6433-4d00-8865-7ee48e6c215b.jpg', 'bingimg_20250514_1920x1080.jpg', 'D:/upload/2025/12/05/197ed507-6433-4d00-8865-7ee48e6c215b.jpg', 258398, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-12-05 16:15:52', '2025-12-05 16:15:52', 'admin', 'admin', 0);
INSERT INTO `tool_file` VALUES (57, 'fbe06196-2ab8-499c-ace0-b5bcce76b4ac.jpg', 'bingimg_20250514_1920x1080.jpg', 'D:/upload/2025/12/05/fbe06196-2ab8-499c-ace0-b5bcce76b4ac.jpg', 258398, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-12-05 16:16:36', '2025-12-05 16:16:36', 'admin', 'admin', 0);
INSERT INTO `tool_file` VALUES (58, 'cb08021c-b10f-444c-ade1-333521b4da79.jpg', 'th (2).jpg', '../jar/opt/app/lanjii/upload/2025/12/05/cb08021c-b10f-444c-ade1-333521b4da79.jpg', 337829, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-12-05 17:33:23', '2025-12-05 17:33:23', 'admin', 'admin', 0);
INSERT INTO `tool_file` VALUES (59, 'c0239992-ed2a-46bc-ac9c-9c07f5a45e01.jpg', 'th.jpg', '../jar/opt/app/lanjii/upload/2025/12/05/c0239992-ed2a-46bc-ac9c-9c07f5a45e01.jpg', 326223, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-12-05 19:46:41', '2025-12-05 19:46:41', 'admin', 'admin', 0);
INSERT INTO `tool_file` VALUES (60, NULL, '【哲风壁纸】原野风光-户外场景.png', '/upload/2025120812851909-0db6-44da-b0b4-1c36c138befd.png', 21784656, 'OTHER', '.png', NULL, NULL, NULL, NULL, 0, NULL, '2025-12-08 14:45:14', '2025-12-08 14:45:14', 'admin', 'admin', 0);
INSERT INTO `tool_file` VALUES (61, NULL, 'bingimg_20250514_1920x1080.jpg', '/upload/20251208/94d285fe-e93c-4b5b-ab91-97423ba3cc8e.jpg', 258398, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-12-08 14:52:18', '2025-12-08 14:52:18', 'admin', 'admin', 0);
INSERT INTO `tool_file` VALUES (62, NULL, 'bingimg_20250514_1920x1080.jpg', '/upload/20251208/76c03385-9643-4b1f-a937-e7946d4b462d.jpg', 258398, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-12-08 14:54:47', '2025-12-08 14:54:47', 'admin', 'admin', 0);
INSERT INTO `tool_file` VALUES (63, NULL, 'bingimg_20250514_1920x1080.jpg', '/upload/20251208/73e9d6a7-621f-4f36-b2c5-6f31c3393286.jpg', 258398, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-12-08 14:59:34', '2025-12-08 14:59:34', 'admin', 'admin', 0);
INSERT INTO `tool_file` VALUES (64, NULL, 'bingimg_20250514_1920x1080.jpg', '/upload/20251208/82301499-2a90-4b2b-be41-9269980091e6.jpg', 258398, 'OTHER', '.jpg', NULL, NULL, NULL, NULL, 0, NULL, '2025-12-08 15:00:57', '2025-12-08 15:00:57', 'admin', 'admin', 0);
INSERT INTO `tool_file` VALUES (65, NULL, '【哲风壁纸】原野风光-户外场景.png', '/upload/20251208/4c33235a-cca3-4417-992b-f50dd36f6510.png', 21784656, 'OTHER', '.png', NULL, NULL, NULL, NULL, 0, NULL, '2025-12-08 15:05:33', '2025-12-08 15:05:33', 'admin', 'admin', 0);

-- ----------------------------
-- Procedure structure for update_menu_ancestors
-- ----------------------------
DROP PROCEDURE IF EXISTS `update_menu_ancestors`;
delimiter ;;
CREATE PROCEDURE `update_menu_ancestors`()
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE v_id BIGINT;
    DECLARE v_parent_id BIGINT;
    DECLARE v_ancestors VARCHAR(500);
    DECLARE cur CURSOR FOR 
        SELECT id, parent_id FROM sys_menu ORDER BY parent_id;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    -- 先将所有顶级菜单的ancestors设置为'0'
    UPDATE sys_menu SET ancestors = '0' WHERE parent_id = 0 OR parent_id IS NULL;

    -- 迭代更新所有菜单的ancestors
    -- 最多循环10次，防止数据层级过深导致死循环
    SET @max_level = 10;
    WHILE @max_level > 0 DO
        UPDATE sys_menu m
        INNER JOIN sys_menu p ON m.parent_id = p.id
        SET m.ancestors = CONCAT(p.ancestors, ',', m.parent_id)
        WHERE m.parent_id > 0 
          AND (m.ancestors IS NULL OR m.ancestors = '0' OR m.ancestors NOT LIKE CONCAT('%,', m.parent_id));
        
        SET @max_level = @max_level - 1;
    END WHILE;

END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
