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
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_file_type`(`file_type` ASC) USING BTREE,
  INDEX `idx_create_time`(`create_time` ASC) USING BTREE,
  INDEX `idx_deleted`(`deleted` ASC) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'AI文件知识库表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ai_file_knowledge
-- ----------------------------

-- ----------------------------
-- Table structure for ai_knowledge
-- ----------------------------
DROP TABLE IF EXISTS `ai_knowledge`;
CREATE TABLE `ai_knowledge`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户ID（0-平台，>0-租户）',
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
  INDEX `idx_deleted`(`deleted` ASC) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'AI知识库表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ai_knowledge
-- ----------------------------
INSERT INTO `ai_knowledge` VALUES (16, 0, '物流运费如何计算', '问题：物流运费如何计算？\n回答：物流运费计算规则如下：\n1. 计费方式：\n   - 按重量计费：首重1kg起，续重每0.5kg计算一次；\n   - 按体积计费：长×宽×高（cm）÷6000=体积重量（kg）；\n   - 取实际重量和体积重量中较大值作为计费重量。\n2. 价格标准：\n   - 市内配送：首重8元/kg，续重2元/kg；\n   - 省内配送：首重10元/kg，续重5元/kg；\n   - 跨省配送：首重12-20元/kg，续重6-12元/kg（根据距离远近）；\n   - 偏远地区：在标准价格基础上加收30-50%费用。\n3. 优惠政策：\n   - 订单满99元包邮（偏远地区除外）；\n   - 会员用户享受运费9折优惠；\n   - 月度运费累计满300元，次月享受8折优惠；\n   - 大宗订单可联系客服协商优惠价格。\n4. 特殊商品：生鲜、易碎品、危险品等需要特殊包装和运输的商品，会收取额外包装费和保价费。\n下单前可在结算页查看预估运费。', '2026-01-05 12:24:37', '2026-01-05 13:31:45', 'admin', 'admin', 0, '{\"category\":\"费用说明\"}');
INSERT INTO `ai_knowledge` VALUES (17, 0, '物流签收注意事项', '问题：物流签收时需要注意什么？\n回答：为保障您的权益，签收物流包裹时请注意以下事项：\n1. 验收包裹外观：\n   - 检查包裹外包装是否完整，有无破损、水渍、挤压变形等情况；\n   - 如发现外包装破损严重，有权拒绝签收或当场拆封验货；\n   - 拍照或录像保留证据，便于后续维权。\n2. 核对收件信息：\n   - 确认收件人姓名、地址、电话是否正确；\n   - 核对包裹上的运单号与订单信息是否一致；\n   - 确认包裹数量是否齐全。\n3. 当场验货：\n   - 建议当着快递员的面拆开包裹验货；\n   - 核对商品型号、数量、颜色等是否与订单一致；\n   - 检查商品外观有无损坏、功能是否正常；\n   - 贵重物品或电子产品建议开机测试。\n4. 签收或拒收：\n   - 确认无误后签字确认收货；\n   - 如有问题，当场向快递员说明并拒绝签收；\n   - 拒收后及时联系客服说明原因。\n5. 保留凭证：\n   - 保留快递单、发票等凭证；\n   - 拍照记录包裹和商品状态。\n注意：签收后发现问题，48小时内联系客服处理；超过7天视为确认收货。', '2026-01-05 12:24:37', '2026-01-05 12:24:37', 'admin', NULL, 0, '{\"category\": \"签收指南\", \"tags\": [\"签收验货\", \"注意事项\"]}');
INSERT INTO `ai_knowledge` VALUES (18, 0, '物流包裹丢失如何处理', '问题：物流包裹丢失如何处理？\n回答：如果您的物流包裹丢失，请按以下流程处理：\n1. 确认丢失：\n   - 查看物流轨迹，确认最后一次扫描记录的时间和地点；\n   - 如超过承诺时效3天以上无更新，可初步判断为丢失；\n   - 核实派送员是否送错地址或已投放到代收点。\n2. 联系客服：\n   - 立即联系客服报告包裹疑似丢失；\n   - 提供订单号、运单号、收货地址等信息；\n   - 客服会立即联系物流公司核查包裹下落。\n3. 核查处理：\n   - 物流公司会进行内部核查，查找包裹去向；\n   - 核查期通常为3-7个工作日；\n   - 期间客服会持续跟进并反馈进展。\n4. 赔付方案：\n   - 确认丢失后，根据是否保价执行赔付：\n   - 已保价：按保价金额全额赔付；\n   - 未保价：按运费的3-5倍赔付（一般不超过商品实际价值）；\n   - 建议贵重物品一定要购买保价服务。\n5. 赔付流程：\n   - 填写理赔申请单，提供订单凭证；\n   - 提交银行账户信息；\n   - 审核通过后7-15个工作日到账。\n我们承诺：因物流原因造成的包裹丢失，由平台和物流公司共同承担责任，确保您的权益不受损失。', '2026-01-05 12:24:37', '2026-01-05 12:24:37', 'admin', NULL, 0, '{\"category\": \"物流异常\", \"tags\": [\"包裹丢失\", \"理赔流程\"]}');
INSERT INTO `ai_knowledge` VALUES (19, 0, '如何申请退货退款', '问题：如何申请退货退款？\n回答：退货退款申请流程如下：\n1. 申请条件：\n   - 签收后7天内可申请无理由退货（特殊商品除外）；\n   - 商品保持原包装完好，不影响二次销售；\n   - 附件、赠品、发票等齐全；\n   - 商品未使用、未损坏。\n2. 申请步骤：\n   - 登录系统，进入\"我的订单\"页面；\n   - 找到需要退货的订单，点击\"申请售后\"；\n   - 选择\"退货退款\"，填写退货原因和说明；\n   - 上传商品照片（如有质量问题需提供证明）；\n   - 提交申请，等待审核。\n3. 审核与退货：\n   - 系统会在24小时内审核您的申请；\n   - 审核通过后，会提供退货地址和退货单号；\n   - 请妥善包装商品，避免运输途中损坏；\n   - 使用推荐的物流公司寄回，保留运单号；\n   - 在系统中填写退货物流信息。\n4. 退款处理：\n   - 仓库收到退货商品后会进行质检；\n   - 质检通过后1-3个工作日内退款；\n   - 款项原路退回支付账户；\n   - 到账时间根据支付方式不同，一般为3-7个工作日。\n5. 运费承担：\n   - 质量问题或发错货：由平台承担往返运费；\n   - 个人原因退货：需自行承担退货运费。\n注意：定制品、生鲜、内衣等特殊商品不支持无理由退货。', '2026-01-05 12:24:37', '2026-01-05 12:24:37', 'admin', NULL, 0, '{\"category\": \"售后服务\", \"tags\": [\"退货退款\", \"售后申请\"]}');
INSERT INTO `ai_knowledge` VALUES (20, 0, '物流配送时效说明', '问题：物流配送需要多长时间？\n回答：我们的物流配送时效标准如下：\n1. 市内配送：\n   - 标准配送：24小时内送达；\n   - 同城急送：3-6小时送达；\n   - 特快专送：2小时内送达（需额外收费）。\n2. 省内配送：\n   - 省会城市及周边：48小时内送达；\n   - 地级市：2-3个工作日送达；\n   - 县级及乡镇：3-4个工作日送达。\n3. 跨省配送：\n   - 相邻省份：2-3个工作日送达；\n   - 华东、华南、华中区域：3-4个工作日送达；\n   - 东北、西北、西南区域：4-6个工作日送达；\n   - 偏远地区（新疆、西藏、内蒙古部分地区）：7-10个工作日送达。\n4. 特殊情况：\n   - 法定节假日、大促期间，配送时效可能延长1-2天；\n   - 恶劣天气（台风、暴雪等）可能导致配送延迟；\n   - 疫情等特殊时期，配送时效以实际为准。\n5. 时效保障：\n   - 支持指定日期配送服务；\n   - 紧急订单可选择加急配送；\n   - 超时配送可申请运费减免或补偿。\n注意：配送时效从仓库发货时间开始计算，不包括订单处理时间（通常为24小时）。您可以在订单详情页查看预计送达时间。', '2026-01-05 12:24:37', '2026-01-05 12:24:37', 'admin', NULL, 0, '{\"category\": \"配送时效\", \"tags\": [\"送达时间\", \"配送标准\"]}');
INSERT INTO `ai_knowledge` VALUES (21, 0, '如何查看详细物流轨迹', '问题：如何查看详细的物流轨迹信息？\n回答：查看详细物流轨迹的方法：\n1. 系统查询：\n   - 登录账号，进入\"我的订单\"；\n   - 点击订单中的\"查看物流\"按钮；\n   - 可以看到包裹的完整运输轨迹，包括时间、地点、状态描述；\n   - 支持地图模式，可视化展示包裹运输路线。\n2. 运单号查询：\n   - 复制订单中的运单号；\n   - 访问物流公司官网或使用第三方物流查询工具；\n   - 输入运单号即可查询详细轨迹。\n3. 扫码查询：\n   - 使用手机扫描快递单上的二维码；\n   - 自动跳转到物流轨迹页面。\n4. 轨迹信息说明：\n   - 已下单：商家已接单，准备出库；\n   - 已揽收：快递员已取件，正在运往转运中心；\n   - 运输中：包裹在各转运中心之间运输；\n   - 到达目的地：包裹已到达配送城市的转运中心；\n   - 派送中：快递员正在配送途中；\n   - 已签收：包裹已成功送达并签收；\n   - 异常：包裹运输过程中出现异常情况（破损、地址错误等）。\n5. 推送提醒：\n   - 关注公众号或开启APP通知；\n   - 物流状态更新时会自动推送消息；\n   - 支持短信提醒服务（需开通）。\n提示：如果物流轨迹长时间无更新（超过48小时），建议联系客服核查。', '2026-01-05 12:24:37', '2026-01-05 12:24:37', 'admin', NULL, 0, '{\"category\": \"物流查询\", \"tags\": [\"物流轨迹\", \"运单查询\"]}');
INSERT INTO `ai_knowledge` VALUES (22, 0, '特殊物品运输规定', '问题：哪些物品不能运输或有特殊要求？\n回答：为确保运输安全，以下物品有特殊运输规定：\n1. 禁止运输物品：\n   - 易燃易爆物品：汽油、酒精、烟花爆竹、打火机气体等；\n   - 腐蚀性物品：强酸、强碱等化学品；\n   - 毒害品：农药、杀虫剂等；\n   - 放射性物品；\n   - 枪支弹药、管制刀具等违禁品；\n   - 活体动物（宠物需使用专门的宠物托运服务）；\n   - 国家法律法规禁止运输的其他物品。\n2. 限制运输物品：\n   - 液体：单瓶不超过500ml，需密封包装，建议使用防漏袋；\n   - 粉末：需提供成分说明，白色粉末需额外检查；\n   - 电池：锂电池需符合航空运输标准，功率不超过100Wh；\n   - 化妆品：需提供产品说明和成分表；\n   - 食品：保质期内，包装完好，生鲜需冷链运输。\n3. 需要特殊包装的物品：\n   - 易碎品：玻璃、陶瓷、电子产品等，需使用气泡膜、泡沫等防护材料，并在外包装标注\"易碎品\"；\n   - 贵重物品：建议购买保价服务，单件价值超过3000元必须保价；\n   - 精密仪器：需使用专业包装箱，防震防潮；\n   - 大件商品：需使用木架或托盘加固。\n4. 保价建议：\n   - 价值超过500元的商品建议保价；\n   - 保价费率：保价金额×0.5%，最低5元；\n   - 保价后如有损坏或丢失，按保价金额赔付。\n5. 特殊时期规定：\n   - 疫情期间，部分地区不接收快递；\n   - 重大活动或会议期间，安检更严格，运输时效可能延长。\n寄件前建议先咨询客服，确认物品是否可以运输及是否需要特殊处理。', '2026-01-05 12:24:37', '2026-01-05 21:00:18', 'admin', 'admin', 0, '{\"category\": \"运输规定\", \"tags\": [\"禁运物品\", \"特殊商品\", \"保价服务\"]}');

-- ----------------------------
-- Table structure for ai_metadata_field
-- ----------------------------
DROP TABLE IF EXISTS `ai_metadata_field`;
CREATE TABLE `ai_metadata_field`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户ID（0-平台，>0-租户）',
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
  UNIQUE INDEX `uk_field_name`(`field_name` ASC) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'AI 元数据字段配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ai_metadata_field
-- ----------------------------
INSERT INTO `ai_metadata_field` VALUES (2, 0, 'category', '分类', 'string', '', '', 0, 1, 7, '2025-12-30 14:06:08', '2026-01-05 21:00:02', 'admin', 'admin', 0);
INSERT INTO `ai_metadata_field` VALUES (3, 0, 'tags', '标签', 'string', '', '', 0, 1, 6, '2026-01-04 22:54:02', '2026-01-05 12:54:39', 'admin', 'admin', 0);

-- ----------------------------
-- Table structure for ai_model_config
-- ----------------------------
DROP TABLE IF EXISTS `ai_model_config`;
CREATE TABLE `ai_model_config`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户ID（0-平台，>0-租户）',
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
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'AI 模型配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ai_model_config
-- ----------------------------
INSERT INTO `ai_model_config` VALUES (1, 0, 'DeepSeek', 'openai', 'deepseek-ai/DeepSeek-R1-Distill-Qwen-7B', 1, 1, 2, '', 'https://api.siliconflow.cn', '测试', 0.00, 0.90, 2048, 60, 0.00, 0.00, NULL, 3, 'admin', '2025-12-30 10:04:03', 'admin', '2026-01-05 16:08:47', 0);

-- ----------------------------
-- Table structure for ai_role_prompt
-- ----------------------------
DROP TABLE IF EXISTS `ai_role_prompt`;
CREATE TABLE `ai_role_prompt`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户ID（0-平台，>0-租户）',
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
  INDEX `idx_is_enabled`(`is_enabled` ASC) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'AI 角色与提示词配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ai_role_prompt
-- ----------------------------
INSERT INTO `ai_role_prompt` VALUES (2, 0, '物流订单智能客服', '专业的物流订单智能客服助手，能够解答用户关于订单查询、物流跟踪、配送问题、退换货流程、运费计算等各类物流相关问题。基于RAG技术，从知识库中检索最相关的信息，为用户提供准确、专业、友好的服务。', 1, '# 角色定位\r\n你是一名专业的物流订单智能客服助手，代表公司为用户提供物流相关的咨询服务。你的目标是快速、准确、友好地解决用户的问题，提升用户体验。\r\n\r\n# 重要提醒\r\n**你必须具备强大的上下文记忆能力**：\r\n1. **记住用户身份**：如果用户告诉你他们的姓名、身份、订单信息等，你必须在整个对话过程中记住这些信息\r\n2. **追踪对话历史**：仔细阅读之前的对话内容，确保你的回答与之前的对话保持一致\r\n3. **引用历史信息**：当用户询问之前提到的信息时，直接从对话历史中提取准确答案\r\n4. **保持连贯性**：不要重复询问用户已经提供过的信息，除非需要确认或更新\r\n\r\n# 核心能力\r\n1. **订单查询**：帮助用户查询订单状态、物流轨迹、配送进度等信息\r\n2. **问题解答**：解答物流配送、运费计算、签收验货、退换货等常见问题\r\n3. **异常处理**：协助处理配送延迟、包裹丢失、地址错误等异常情况\r\n4. **流程指导**：指导用户完成订单修改、取消订单、申请售后等操作\r\n5. **政策说明**：解释配送时效、运费标准、退换货政策等相关规定\r\n6. **上下文记忆**：准确记住并引用对话中的用户信息、订单详情、问题历史\r\n\r\n# 服务原则\r\n1. **准确性优先**：基于知识库和对话历史提供准确的信息，不确定时明确告知用户\r\n2. **快速响应**：简洁明了回答问题，避免冗长描述\r\n3. **友好专业**：保持礼貌、耐心、同理心，使用友好的语气\r\n4. **问题导向**：围绕用户问题核心，提供针对性解决方案\r\n5. **主动引导**：对于复杂问题，主动提供后续操作建议或转人工服务\r\n6. **记忆优先**：优先从对话历史中提取信息，避免遗忘用户已提供的内容\r\n\r\n# 回答规范\r\n1. **结构清晰**：使用分点、编号等方式，让答案易于阅读理解\r\n2. **关键信息突出**：重要信息（如时效、费用、注意事项）要明确强调\r\n3. **提供操作步骤**：涉及操作的问题，给出详细的步骤指引\r\n4. **补充相关信息**：适当补充用户可能关心的相关问题\r\n5. **引导下一步**：告知用户如需进一步帮助，可以如何联系客服\r\n6. **引用历史信息**：当回答涉及之前对话内容时，明确说明\"根据您之前提到的...\"\r\n\r\n# 特殊情况处理\r\n1. **超出能力范围**：礼貌告知用户该问题需要人工客服处理，并提供联系方式\r\n2. **信息不足**：当用户问题描述不清时，主动询问必要信息（如订单号、收货地址等），但要先检查对话历史中是否已有该信息\r\n3. **知识库无相关内容**：根据常识和经验给出建议，并说明建议联系人工客服确认\r\n4. **用户情绪激动**：保持冷静和同理心，先安抚情绪，再解决问题\r\n5. **投诉或纠纷**：表示歉意和重视，引导用户提供详细信息，承诺跟进处理\r\n6. **用户询问已提供的信息**：直接从对话历史中准确回答，不要说\"我不知道\"或\"您没告诉我\"\r\n\r\n# 对话历史处理规则\r\n1. **仔细阅读对话历史**：在回答任何问题前，先完整阅读之前的对话内容\r\n2. **提取关键信息**：识别并记录用户姓名、联系方式、订单号、地址等关键信息\r\n3. **保持一致性**：确保你的回答与之前的对话保持一致，不要前后矛盾\r\n4. **主动关联**：当新问题与之前讨论的内容相关时，主动建立关联\r\n5. **避免重复**：不要询问用户已经告诉过你的信息\r\n\r\n# 回答模板参考\r\n\r\n**查询类问题**：\r\n- 先检查对话历史中是否有相关信息\r\n- 确认用户需要查询的信息类型\r\n- 提供查询方法和步骤\r\n- 补充相关说明和注意事项\r\n\r\n**操作类问题**：\r\n- 说明操作条件和限制\r\n- 提供详细的操作步骤\r\n- 提示可能的后续影响\r\n\r\n**政策类问题**：\r\n- 清晰说明相关政策规定\r\n- 解释政策的适用范围\r\n- 举例说明特殊情况\r\n\r\n**异常类问题**：\r\n- 表示理解和重视\r\n- 提供问题处理流程\r\n- 告知预计解决时间和补偿方案\r\n\r\n**用户信息询问**：\r\n- 如：\"我是谁？\"、\"我的订单号是什么？\"\r\n- 回答格式：\"根据您之前告诉我的信息，您是[用户姓名]，您的订单号是[订单号]。\"\r\n- 如果对话历史中没有该信息，才说：\"抱歉，您还没有告诉我这个信息，请问您可以提供一下吗？\"\r\n\r\n# 语言风格\r\n- 使用\"您\"称呼用户，体现尊重\r\n- 多用\"请\"、\"建议\"、\"可以\"等礼貌用语\r\n- 避免使用生硬的命令式语气\r\n- 适当使用\"为您\"、\"帮您\"等服务性表达\r\n- 回答要简洁，避免过度客套\r\n- 引用历史信息时使用\"您之前提到...\"、\"根据您刚才说的...\"等表达\r\n\r\n# 禁止行为\r\n1. 不编造或猜测不确定的信息\r\n2. 不承诺无法兑现的服务\r\n3. 不对其他物流公司或竞品进行负面评价\r\n4. 不泄露用户隐私信息\r\n5. 不参与与物流服务无关的话题讨论\r\n6. **不要忘记用户已经告诉你的信息**\r\n7. **不要在对话历史中有答案的情况下说\"不知道\"**\r\n\r\n# 核心原则总结\r\n记住：\r\n1. **对话历史是你的记忆**：仔细阅读并记住对话中的所有信息\r\n2. **知识库是你的工具**：基于知识库提供的权威信息回答物流相关问题\r\n3. **上下文是你的基础**：结合对话历史、知识库和用户当前问题，提供准确、连贯、个性化的服务\r\n4. **用户信息必须记住**：用户姓名、订单号、联系方式、地址等关键信息一旦提供，必须在整个对话中保持记忆', 1, '# 知识库检索结果\n以下是从知识库中检索到的相关信息，请基于这些信息回答用户的问题：\n{context}\n\n# 回答要求\n1. **优先查看对话历史**：如果用户问题涉及他们之前提到的信息，直接从对话历史中提取准确答案\n2. **使用知识库内容**：对于物流政策、流程、规定等问题，优先使用知识库内容回答\n3. **整合信息回答**：将对话历史中的用户信息与知识库内容结合，提供个性化的回答\n4. **保持连贯性**：确保回答与之前的对话保持一致，不要前后矛盾\n5. **明确信息来源**：\n   - 如果信息来自对话历史，说\"根据您之前提到的...\"\n   - 如果信息来自知识库，直接引用并适当精简\n   - 如果需要综合多条信息，整合后给出完整答案\n6. **处理无相关信息的情况**：\n   - 对话历史中没有：询问用户提供该信息\n   - 知识库中没有：根据常识给出建议，并建议联系人工客服确认\n\n# 用户当前问题\n{query}\n\n# 回答指南\n- 先检查对话历史中是否有答案（特别是用户询问自己提供过的信息时）\n- 再参考知识库内容提供物流服务相关的专业回答\n- 结合两者，提供准确、连贯、个性化的服务\n- 记住：**用户告诉过你的信息，你必须记住并能准确回答**', 5, 0.7, '', '2026-01-05 12:38:06', '2026-01-05 16:20:12', 'admin', 'admin', 0);

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
INSERT INTO `sys_config` VALUES (27, '默认的用户密码', 'DEFAULT_USER_PWD', '123456', '1', 1, '', 'admin', '2025-11-14 14:22:54', 'admin', '2025-11-14 15:26:30', 0);

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户ID（0-平台，>0-租户）',
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
  INDEX `idx_parent_id`(`parent_id` ASC) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统部门表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------

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
  `tag_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'default' COMMENT '标签类型: success/info/warning/danger/default',
  `tag_color` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '自定义标签颜色(hex或颜色名)',
  `tag_effect` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'light' COMMENT '标签主题: dark/light/plain',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_dict_type`(`dict_type` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (3, 1, '显示', 1, 'IS_VISIBLE', NULL, NULL, 0, 1, NULL, NULL, NULL, 'admin', '2026-01-24 08:46:37', 0, 'success', NULL, 'light');
INSERT INTO `sys_dict_data` VALUES (4, 2, '隐藏', 0, 'IS_VISIBLE', NULL, NULL, 0, 1, '', NULL, NULL, 'admin', '2026-01-24 16:18:40', 0, 'info', '', 'light');
INSERT INTO `sys_dict_data` VALUES (5, 3, '失败', 0, 'LOGIN_STATUS', NULL, NULL, 0, 1, '登录失败', NULL, NULL, 'admin', '2025-10-11 20:38:58', 0, 'default', NULL, 'light');
INSERT INTO `sys_dict_data` VALUES (6, 2, '成功', 1, 'LOGIN_STATUS', NULL, NULL, 0, 1, '登录成功', NULL, NULL, NULL, NULL, 0, 'default', NULL, 'light');
INSERT INTO `sys_dict_data` VALUES (7, 1, '登录', 0, 'LOGIN_TYPE', NULL, NULL, 0, 1, '用户登录', NULL, NULL, 'admin', '2026-01-21 14:36:23', 0, 'success', NULL, 'light');
INSERT INTO `sys_dict_data` VALUES (8, 2, '登出', 1, 'LOGIN_TYPE', NULL, NULL, 0, 1, '用户登出', NULL, NULL, 'admin', '2026-01-21 14:36:37', 0, 'info', NULL, 'light');
INSERT INTO `sys_dict_data` VALUES (9, 1, '失败', 0, 'OPER_STATUS', NULL, NULL, 0, 1, '操作失败', NULL, NULL, NULL, NULL, 0, 'default', NULL, 'light');
INSERT INTO `sys_dict_data` VALUES (10, 2, '成功', 1, 'OPER_STATUS', NULL, NULL, 0, 1, '操作成功', NULL, NULL, NULL, NULL, 0, 'default', NULL, 'light');
INSERT INTO `sys_dict_data` VALUES (11, 1, '新增', 0, 'BUSINESS_TYPE', NULL, NULL, 0, 1, '新增操作', NULL, NULL, NULL, NULL, 0, 'success', NULL, 'light');
INSERT INTO `sys_dict_data` VALUES (12, 2, '修改', 1, 'BUSINESS_TYPE', NULL, NULL, 0, 1, '修改操作', NULL, NULL, NULL, NULL, 0, 'warning', NULL, 'light');
INSERT INTO `sys_dict_data` VALUES (13, 3, '删除', 2, 'BUSINESS_TYPE', NULL, NULL, 0, 1, '删除操作', NULL, NULL, NULL, NULL, 0, 'danger', NULL, 'light');
INSERT INTO `sys_dict_data` VALUES (28, 2, '禁用', 0, 'IS_ENABLED', NULL, NULL, 0, 1, '', 'admin', '2026-01-20 22:18:50', 'admin', '2026-01-24 13:24:33', 0, 'danger', '', 'light');
INSERT INTO `sys_dict_data` VALUES (29, 0, '启用', 1, 'IS_ENABLED', NULL, NULL, 0, 1, '', 'admin', '2026-01-20 22:19:20', 'admin', '2026-01-20 22:19:20', 0, 'success', '', 'light');
INSERT INTO `sys_dict_data` VALUES (30, 1, '男', 1, 'GENDER', NULL, NULL, 0, 1, '', 'admin', '2026-01-21 14:14:44', 'admin', '2026-01-21 14:15:09', 0, 'success', '', 'light');
INSERT INTO `sys_dict_data` VALUES (31, 2, '女', 2, 'GENDER', NULL, NULL, 0, 1, '', 'admin', '2026-01-21 14:14:56', 'admin', '2026-01-21 14:15:18', 0, 'warning', '', 'light');
INSERT INTO `sys_dict_data` VALUES (32, 1, '成功', 1, 'IS_SUCCESS', NULL, NULL, 0, 1, '', 'admin', '2026-01-21 14:25:38', 'admin', '2026-01-21 14:25:38', 0, 'success', '', 'light');
INSERT INTO `sys_dict_data` VALUES (33, 2, '失败', 0, 'IS_SUCCESS', NULL, NULL, 0, 1, '', 'admin', '2026-01-21 14:25:56', 'admin', '2026-01-21 14:25:56', 0, 'danger', '', 'light');
INSERT INTO `sys_dict_data` VALUES (34, 1, 'GET', 1, 'REQUEST_METHOD', NULL, NULL, 0, 1, '', 'admin', '2026-01-24 09:58:35', 'admin', '2026-01-24 09:58:35', 0, 'success', '', 'light');
INSERT INTO `sys_dict_data` VALUES (35, 2, 'POST', 2, 'REQUEST_METHOD', NULL, NULL, 0, 1, '', 'admin', '2026-01-24 09:58:50', 'admin', '2026-01-24 10:01:09', 0, 'primary', '', 'light');
INSERT INTO `sys_dict_data` VALUES (36, 3, 'PUT', 3, 'REQUEST_METHOD', NULL, NULL, 0, 1, '', 'admin', '2026-01-24 09:59:08', 'admin', '2026-01-24 09:59:14', 0, 'warning', '', 'light');
INSERT INTO `sys_dict_data` VALUES (37, 4, 'DELETE', 4, 'REQUEST_METHOD', NULL, NULL, 0, 1, '', 'admin', '2026-01-24 09:59:27', 'admin', '2026-01-24 09:59:27', 0, 'danger', '', 'light');
INSERT INTO `sys_dict_data` VALUES (38, 1, '系统配置', 1, 'CONFIG_TYPE', NULL, NULL, 0, 1, '', 'admin', '2026-01-24 11:56:09', 'admin', '2026-01-24 11:56:09', 0, 'primary', '', 'light');
INSERT INTO `sys_dict_data` VALUES (39, 2, '业务配置', 2, 'CONFIG_TYPE', NULL, NULL, 0, 1, '', 'admin', '2026-01-24 11:56:28', 'admin', '2026-01-24 11:56:28', 0, 'success', '', 'light');
INSERT INTO `sys_dict_data` VALUES (40, 1, '目录', 1, 'MENU_TYPE', NULL, NULL, 0, 1, '', 'admin', '2026-01-24 13:29:38', 'admin', '2026-01-24 13:30:31', 0, 'info', '', 'light');
INSERT INTO `sys_dict_data` VALUES (41, 2, '菜单', 2, 'MENU_TYPE', NULL, NULL, 0, 1, '', 'admin', '2026-01-24 13:29:55', 'admin', '2026-01-24 13:30:24', 0, 'success', '', 'light');
INSERT INTO `sys_dict_data` VALUES (42, 3, '按钮', 3, 'MENU_TYPE', NULL, NULL, 0, 1, '', 'admin', '2026-01-24 13:30:11', 'admin', '2026-01-24 13:30:11', 0, 'warning', '', 'light');
INSERT INTO `sys_dict_data` VALUES (43, 0, '内嵌', 0, 'OPEN_MODE', NULL, NULL, 0, 1, '', 'admin', '2026-01-24 16:22:16', 'admin', '2026-01-24 16:22:16', 0, 'primary', '', 'light');
INSERT INTO `sys_dict_data` VALUES (44, 1, '新窗口', 1, 'OPEN_MODE', NULL, NULL, 0, 1, '', 'admin', '2026-01-24 16:22:27', 'admin', '2026-01-24 16:22:40', 0, 'warning', '', 'light');
INSERT INTO `sys_dict_data` VALUES (45, 1, '是', 1, 'YES_OR_NO', NULL, NULL, 0, 1, '', 'admin', '2026-01-24 16:24:14', 'admin', '2026-01-24 16:24:14', 0, 'success', '', 'light');
INSERT INTO `sys_dict_data` VALUES (46, 2, '否', 0, 'YES_OR_NO', NULL, NULL, 0, 1, '', 'admin', '2026-01-24 16:24:27', 'admin', '2026-01-24 16:24:27', 0, 'info', '', 'light');
INSERT INTO `sys_dict_data` VALUES (47, 3, '全部', 0, 'MENU_SCOPE', NULL, NULL, 0, 1, '', 'admin', '2026-01-29 15:38:01', 'admin', '2026-01-29 15:38:01', 0, 'primary', '', 'light');
INSERT INTO `sys_dict_data` VALUES (48, 1, '租户', 1, 'MENU_SCOPE', NULL, NULL, 0, 1, '', 'admin', '2026-01-29 15:38:20', 'admin', '2026-01-29 15:38:20', 0, 'success', '', 'light');
INSERT INTO `sys_dict_data` VALUES (49, 2, '平台', 2, 'MENU_SCOPE', NULL, NULL, 0, 1, '', 'admin', '2026-01-29 15:38:37', 'admin', '2026-01-29 15:38:37', 0, 'warning', '', 'light');

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
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典类型表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (2, '是否可见', 'IS_VISIBLE', 1, NULL, 'admin', '2026-01-21 14:45:13', 'admin', '2026-01-24 08:46:12', 0);
INSERT INTO `sys_dict_type` VALUES (4, '登录状态', 'LOGIN_STATUS', 1, NULL, 'admin', '2026-01-21 14:45:13', 'admin', '2026-01-21 14:45:13', 0);
INSERT INTO `sys_dict_type` VALUES (5, '登录类型', 'LOGIN_TYPE', 1, NULL, 'admin', '2026-01-21 14:45:13', 'admin', '2026-01-21 14:45:13', 0);
INSERT INTO `sys_dict_type` VALUES (6, '操作状态', 'OPER_STATUS', 1, NULL, 'admin', '2026-01-21 14:45:13', 'admin', '2026-01-21 14:45:13', 0);
INSERT INTO `sys_dict_type` VALUES (7, '业务类型', 'BUSINESS_TYPE', 1, NULL, 'admin', '2026-01-21 14:45:13', 'admin', '2026-01-21 14:45:13', 0);
INSERT INTO `sys_dict_type` VALUES (13, '是否启用', 'IS_ENABLED', 1, NULL, 'admin', '2026-01-21 14:45:13', 'admin', '2026-01-21 14:45:13', 0);
INSERT INTO `sys_dict_type` VALUES (14, '性别', 'GENDER', 1, NULL, 'admin', '2026-01-21 14:45:13', 'admin', '2026-01-21 14:45:13', 0);
INSERT INTO `sys_dict_type` VALUES (15, '是否成功', 'IS_SUCCESS', 1, NULL, 'admin', '2026-01-21 14:45:13', 'admin', '2026-01-21 14:45:13', 0);
INSERT INTO `sys_dict_type` VALUES (16, '请求方式', 'REQUEST_METHOD', 1, NULL, 'admin', '2026-01-21 14:45:13', 'admin', '2026-01-21 14:45:13', 0);
INSERT INTO `sys_dict_type` VALUES (17, '配置类型', 'CONFIG_TYPE', 1, '', 'admin', '2026-01-24 11:55:41', 'admin', '2026-01-24 11:55:41', 0);
INSERT INTO `sys_dict_type` VALUES (18, '菜单类型', 'MENU_TYPE', 1, '', 'admin', '2026-01-24 13:28:57', 'admin', '2026-01-24 13:28:57', 0);
INSERT INTO `sys_dict_type` VALUES (20, '打开方式', 'OPEN_MODE', 1, '', 'admin', '2026-01-24 16:21:55', 'admin', '2026-01-24 16:21:55', 0);
INSERT INTO `sys_dict_type` VALUES (21, '是或否', 'YES_OR_NO', 1, '', 'admin', '2026-01-24 16:23:49', 'admin', '2026-01-24 16:23:49', 0);
INSERT INTO `sys_dict_type` VALUES (22, '菜单范围', 'MENU_SCOPE', 1, '', 'admin', '2026-01-29 15:37:32', 'admin', '2026-01-29 15:37:32', 0);

-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE `sys_login_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户ID',
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
  INDEX `idx_status`(`status` ASC) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1167 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户登录日志表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_login_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户ID（0-平台全局菜单，>0-租户自定义菜单）',
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
  `scope` tinyint NOT NULL DEFAULT 0 COMMENT '显示范围（0-全部，1-租户，2-平台）',
  `is_system` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否系统菜单（0-否，1-是，系统菜单不可编辑删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_parent_id`(`parent_id` ASC) USING BTREE,
  INDEX `idx_ancestors`(`ancestors` ASC) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 268 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 0, 0, '0', '控制台', 1, '/admin/index', 'admin/index', '', 'DataBoard', 1, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:02', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (3, 0, 0, '0', '系统管理', 1, NULL, NULL, NULL, 'Setting', 2, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:02', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (4, 0, 0, '0', '系统监控', 1, NULL, NULL, NULL, 'Monitor', 6, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:02', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (5, 0, 0, '0', '日志管理', 1, NULL, NULL, '', 'Tickets', 9, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:02', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (6, 0, 0, '0', '系统工具', 1, NULL, NULL, NULL, 'SetUp', 4, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:02', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (7, 0, 0, '0', '外部链接', 1, NULL, NULL, NULL, 'Link', 10, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:02', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (49, 0, 3, '0,3', '用户管理', 2, '/admin/system/user', 'system/user/index', 'sys:user:page,sys:post:list,sys:dept:tree,sys:role:list', 'User', 1, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:03', 'admin', 'admin', 0, 0, 0, 0);
INSERT INTO `sys_menu` VALUES (50, 0, 3, '0,3', '部门管理', 2, '/admin/system/dept', 'system/dept/index', 'sys:dept:page', 'OfficeBuilding', 2, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (51, 0, 3, '0,3', '岗位管理', 2, '/admin/system/post', 'system/post/index', 'sys:post:page', 'document', 3, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (52, 0, 3, '0,3', '角色管理', 2, '/admin/system/role', 'system/role/index', 'sys:role:page', 'UserFilled', 4, 1, 1, 0, 0, '2025-06-21 09:29:22', '2026-01-05 20:03:12', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (53, 0, 3, '0,3', '菜单管理', 2, '/admin/system/menu', 'system/menu/index', 'sys:menu:tree', 'Menu', 5, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (54, 0, 3, '0,3', '字典管理', 2, '/admin/system/dict', 'system/dict/index', 'sys:dict-type:page,sys:dict-type:data', 'DocumentCopy', 6, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (55, 0, 3, '0,3', '系统配置', 2, '/admin/system/config', 'system/config/index', 'sys:config:page', 'Operation', 7, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (59, 0, 4, '0,4', '在线用户', 2, '/admin/monitor/online', 'monitor/online/index', 'sys:session:page', 'Connection', 3, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (60, 0, 4, '0,4', 'SQL监控', 2, 'http://106.54.167.194/api/druid/login.html', 'monitor/sql/index', '', 'DataLine', 4, 1, 1, 1, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (61, 0, 5, '0,5', '操作日志', 2, '/admin/log/operlog', 'log/operlog/index', 'sys:operlog:page', 'Document', 1, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (62, 0, 5, '0,5', '登录日志', 2, '/admin/log/loginlog', 'log/loginlog/index', 'sys:loginlog:page', 'TrendCharts', 2, 1, 1, 0, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (63, 0, 6, '0,6', '文件管理', 2, '/admin/tool/file', 'tool/file/index', '', 'files', 1, 1, 1, 0, 0, '2025-06-21 09:29:22', '2026-01-05 19:56:13', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (65, 0, 7, '0,7', 'Gitee', 2, 'https://gitee.com/leven2018/lanjii', NULL, '', 'QuestionFilled', 1, 1, 1, 1, 1, '2025-06-21 09:29:22', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (66, 0, 7, '0,7', 'Element Plus', 2, 'https://element-plus.org/', NULL, '', 'ElementPlus', 2, 1, 1, 1, 0, '2025-06-21 09:29:22', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (69, 0, 49, '3,49', '新增', 3, '', '', 'sys:user:save', '', 1, 1, 1, 0, 0, '2025-09-27 13:21:00', '2026-01-05 20:01:43', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (70, 0, 49, '3,49', '编辑', 3, '', '', 'sys:user:update', '', 2, 1, 1, 0, 0, '2025-09-27 13:21:38', '2026-01-05 20:01:43', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (71, 0, 49, '3,49', '删除', 3, '', '', 'sys:user:delete', '', 3, 1, 1, 0, 0, '2025-09-27 13:22:16', '2026-01-24 16:44:13', 'admin', 'admin', 1, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (72, 0, 49, '3,49', '删除', 3, '', '', 'sys:user:delete', '', 4, 1, 1, 0, 0, '2025-09-27 13:25:39', '2026-01-05 20:01:43', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (73, 0, 49, '3,49', '查看', 3, '', '', 'sys:user:view', '', 0, 1, 1, 0, 0, '2025-09-27 13:54:17', '2026-01-05 20:01:43', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (74, 0, 49, '3,49', '导出', 3, '', '', 'sys:user:export', '', 5, 1, 1, 0, 0, '2025-09-27 14:37:32', '2026-01-05 20:01:43', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (75, 0, 49, '3,49', '状态切换', 3, '', '', 'sys:user:status', '', 7, 1, 1, 0, 0, '2025-09-27 15:37:14', '2026-01-05 20:01:43', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (76, 0, 49, '3,49', '重置密码', 3, '', '', 'sys:user:reset-pwd', '', 0, 1, 1, 0, 0, '2025-09-27 15:38:43', '2026-01-05 20:01:43', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (77, 0, 50, '3,50', '新增', 3, '', '', 'sys:dept:save', '', 1, 1, 1, 0, 0, '2025-09-27 16:00:00', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (78, 0, 50, '3,50', '编辑', 3, '', '', 'sys:dept:update', '', 2, 1, 1, 0, 0, '2025-09-27 16:00:00', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (79, 0, 50, '3,50', '删除', 3, '', '', 'sys:dept:delete', '', 3, 1, 1, 0, 0, '2025-09-27 16:00:00', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (80, 0, 51, '3,51', '新增', 3, '', '', 'sys:post:save', '', 1, 1, 1, 0, 0, '2025-09-27 16:00:00', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (81, 0, 51, '3,51', '编辑', 3, '', '', 'sys:post:update', '', 2, 1, 1, 0, 0, '2025-09-27 16:00:00', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (82, 0, 51, '3,51', '删除', 3, '', '', 'sys:post:delete', '', 3, 1, 1, 0, 0, '2025-09-27 16:00:00', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (83, 0, 52, '3,52', '新增', 3, '', '', 'sys:role:save', '', 1, 1, 1, 0, 0, '2025-09-27 16:00:00', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (84, 0, 52, '3,52', '编辑', 3, '', '', 'sys:role:update', '', 2, 1, 1, 0, 0, '2025-09-27 16:00:00', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (85, 0, 52, '3,52', '删除', 3, '', '', 'sys:role:delete', '', 3, 1, 1, 0, 0, '2025-09-27 16:00:00', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (86, 0, 50, '3,50', '查看', 3, '', '', 'sys:dept:view', '', 0, 1, 1, 0, 0, '2025-10-09 20:12:55', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (87, 0, 51, '3,51', '查看', 3, '', '', 'sys:post:view', '', 0, 1, 1, 0, 0, '2025-10-09 20:14:04', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (88, 0, 54, '3,54', '新增', 3, '', '', 'sys:dict-type:save', '', 1, 1, 1, 0, 0, '2025-10-09 20:18:02', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (89, 0, 54, '3,54', '编辑', 3, '', '', 'sys:dict-type:update', '', 2, 1, 1, 0, 0, '2025-10-09 20:18:02', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (90, 0, 54, '3,54', '删除', 3, '', '', 'sys:dict-type:delete', '', 3, 1, 1, 0, 0, '2025-10-09 20:18:02', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (91, 0, 54, '3,54', '查看', 3, '', '', 'sys:dict-type:view', '', 0, 1, 1, 0, 0, '2025-10-09 20:18:02', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (92, 0, 55, '3,55', '新增', 3, '', '', 'sys:config:save', '', 1, 1, 1, 0, 0, '2025-10-09 20:35:35', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (93, 0, 55, '3,55', '编辑', 3, '', '', 'sys:config:update', '', 2, 1, 1, 0, 0, '2025-10-09 20:35:35', '2026-01-05 20:04:34', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (94, 0, 55, '3,55', '删除', 3, '', '', 'sys:config:delete', '', 3, 1, 1, 0, 0, '2025-10-09 20:35:35', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (95, 0, 55, '3,55', '查看', 3, '', '', 'sys:config:view', '', 0, 1, 1, 0, 0, '2025-10-09 20:35:35', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (96, 0, 55, '3,55', '清除缓存', 3, '', '', 'sys:config:cache', '', 9, 1, 1, 0, 0, '2025-10-09 20:35:35', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (97, 0, 112, '0,3,112', '新增', 3, '', '', 'sys:dict-data:save', '', 1, 1, 1, 0, 0, '2025-10-09 20:49:06', '2026-01-05 20:15:17', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (98, 0, 112, '0,3,112', '编辑', 3, '', '', 'sys:dict-data:update', NULL, 2, 1, 1, 0, 0, '2025-10-09 20:49:28', '2026-01-05 20:15:18', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (100, 0, 112, '0,3,112', '删除', 3, '', '', 'sys:dict-data:delete', '', 3, 1, 1, 0, 0, '2025-10-09 20:50:22', '2026-01-05 20:15:18', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (101, 0, 53, '3,53', '删除', 3, '', '', 'sys:menu:delete', '', NULL, 1, 1, 0, 0, '2025-11-10 19:48:19', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (102, 0, 52, '3,52', '分配权限', 3, '', '', 'sys:role:permission', '', NULL, 1, 1, 0, 0, '2025-11-11 22:15:46', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (103, 0, 53, '3,53', '新增', 3, '', '', 'sys:menu:save', '', 1, 1, 1, 0, 0, '2025-11-13 18:26:00', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (104, 0, 53, '3,53', '编辑', 3, '', '', 'sys:menu:update', '', 0, 1, 1, 0, 0, '2025-11-13 18:26:23', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (105, 0, 53, '3,53', '查看', 3, '', '', 'sys:menu:view', '', 4, 1, 1, 0, 0, '2025-11-13 18:27:00', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (106, 0, 62, '5,62', '清空', 3, '', '', 'sys:loginlog:clean', '', 0, 1, 1, 0, 0, '2025-11-13 22:03:47', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (108, 0, 61, '5,61', '清空', 3, '', '', 'sys:operlog:clean', '', 0, 1, 1, 0, 0, '2025-11-13 22:04:48', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (109, 0, 62, '5,62', '查看', 3, '', '', 'sys:loginlog:view', '', 0, 1, 1, 0, 0, '2025-11-13 22:30:21', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (110, 0, 61, '5,61', '查看', 3, '', '', 'sys:operlog:view', '', 0, 1, 1, 0, 0, '2025-11-13 22:30:47', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (111, 0, 52, '3,52', '查看', 3, '', '', 'sys:role:view', '', 0, 1, 1, 0, 0, '2025-11-13 23:42:58', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (112, 0, 3, '0,3', '字典数据', 2, '/admin/system/dict/data', 'system/dict/data/index', 'sys:dict-data:page', 'Management', 6, 0, 1, 0, 0, '2025-11-14 10:23:45', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (113, 0, 59, '4,59', '踢出', 3, '', '', 'sys:session:kick', '', 1, 1, 1, 0, 0, '2025-11-14 22:39:12', '2025-11-17 17:12:03', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (114, 0, 0, '0', '通知公告', 1, '', '', '', 'Bell', 3, 1, 1, 0, 0, '2025-11-27 20:15:11', '2025-11-27 20:15:11', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (115, 0, 114, '0,114', '通知公告', 2, '/admin/notice', 'notice/index', 'notice:page', 'Tickets', 0, 1, 1, 0, 0, '2025-11-27 20:18:33', '2025-11-27 20:18:33', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (116, 0, 114, '0,114', '发布', 2, '/admin/notice/publish', 'notice/publish', 'notice:publish', 'Promotion', 0, 1, 1, 0, 0, '2025-11-28 11:33:00', '2025-11-28 11:33:00', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (117, 0, 0, '0', 'AI', 1, '', '', '', 'ChatDotRound', 100, 1, 1, 0, 0, '2025-12-22 17:11:58', '2025-12-22 17:11:58', 'admin', 'admin', 0, 1, 1, 0);
INSERT INTO `sys_menu` VALUES (118, 0, 117, '0,117', 'AI 聊天', 2, '/admin/ai-chat', 'ai/chat/index', 'ai:chats:stream', 'ChatLineRound', 1, 1, 1, 0, 0, '2025-12-22 17:14:35', '2026-01-05 20:02:12', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (119, 0, 117, '0,117', '数据库知识库', 2, '/admin/ai/knowledge', 'ai/knowledge/index', 'ai:knowledge:page', 'Notebook', NULL, 1, 1, 0, 0, '2025-12-25 15:03:17', '2026-01-05 19:54:03', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (120, 0, 119, '0,117,119', '新增', 3, '', '', 'ai:knowledge:save', '', NULL, 1, 1, 0, 0, '2025-12-25 15:30:50', '2025-12-25 15:30:50', 'admin', 'admin', 0, 0, 0, 0);
INSERT INTO `sys_menu` VALUES (121, 0, 119, '0,117,119', '编辑', 3, '', '', 'ai:knowledge:update', '', NULL, 1, 1, 0, 0, '2025-12-25 15:31:19', '2025-12-25 15:31:19', 'admin', 'admin', 0, 0, 0, 0);
INSERT INTO `sys_menu` VALUES (122, 0, 119, '0,117,119', '删除', 3, '', '', 'ai:knowledge:delete', '', NULL, 1, 1, 0, 0, '2025-12-25 15:31:58', '2025-12-25 15:31:58', 'admin', 'admin', 0, 0, 0, 0);
INSERT INTO `sys_menu` VALUES (123, 0, 119, '0,117,119', '查看', 3, '', '', 'ai:knowledge:view', '', NULL, 1, 1, 0, 0, '2025-12-25 15:32:19', '2025-12-25 15:32:19', 'admin', 'admin', 0, 0, 0, 0);
INSERT INTO `sys_menu` VALUES (127, 0, 117, '0,117', '模型配置', 2, '/admin/ai/model-config', 'ai/model-config/index', 'ai:model-config:page', 'Cpu', 2, 1, 1, 0, 0, '2025-12-30 00:00:00', '2025-12-30 00:00:00', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (128, 0, 127, '0,117,127', '新增', 3, '', '', 'ai:model-config:save', '', NULL, 1, 1, 0, 0, '2025-12-30 00:00:00', '2025-12-30 00:00:00', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (129, 0, 127, '0,117,127', '编辑', 3, '', '', 'ai:model-config:update', '', NULL, 1, 1, 0, 0, '2025-12-30 00:00:00', '2025-12-30 00:00:00', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (130, 0, 127, '0,117,127', '删除', 3, '', '', 'ai:model-config:delete', '', NULL, 1, 1, 0, 0, '2025-12-30 00:00:00', '2025-12-30 00:00:00', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (131, 0, 127, '0,117,127', '查看', 3, '', '', 'ai:model-config:view', '', NULL, 1, 1, 0, 0, '2025-12-30 00:00:00', '2025-12-30 00:00:00', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (132, 0, 127, '0,117,127', '清空', 3, '', '', 'ai:model-config:clear', '', NULL, 1, 1, 0, 0, '2025-12-30 00:00:00', '2025-12-30 00:00:00', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (134, 0, 127, '0,117,127', '设为默认', 3, '', '', 'ai:model-config:default', '', 6, 1, 1, 0, 0, '2025-12-30 00:00:00', '2026-01-04 17:26:40', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (135, 0, 117, '0,117', '角色与提示词', 2, '/admin/ai/role-prompt', 'ai/role-prompt/index', 'ai:role-prompt:page', 'ChatLineRound', 3, 1, 1, 0, 0, '2025-12-30 00:00:00', '2025-12-30 00:00:00', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (136, 0, 135, '0,117,135', '查看', 3, '', '', 'ai:role-prompt:view', '', 0, 1, 1, 0, 0, '2025-12-30 00:00:00', '2025-12-30 00:00:00', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (137, 0, 135, '0,117,135', '新增', 3, '', '', 'ai:role-prompt:save', '', 1, 1, 1, 0, 0, '2025-12-30 00:00:00', '2025-12-30 00:00:00', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (138, 0, 135, '0,117,135', '编辑', 3, '', '', 'ai:role-prompt:update', '', 2, 1, 1, 0, 0, '2025-12-30 00:00:00', '2025-12-30 00:00:00', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (139, 0, 135, '0,117,135', '删除', 3, '', '', 'ai:role-prompt:delete', '', 3, 1, 1, 0, 0, '2025-12-30 00:00:00', '2025-12-30 00:00:00', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (140, 0, 135, '0,117,135', '状态切换', 3, '', '', 'ai:role-prompt:toggle', '', 4, 1, 1, 0, 0, '2025-12-30 00:00:00', '2025-12-30 00:00:00', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (142, 0, 117, '0,117', '元数据', 2, '/admin/ai/metadata-config', 'ai/metadata-config/index', 'ai:metadata-field:page', 'CollectionTag', 4, 1, 1, 0, 0, '2025-12-30 00:00:00', '2026-01-05 19:50:13', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (143, 0, 142, '0,117,142', '查看', 3, '', '', 'ai:metadata-field:view', '', 0, 1, 1, 0, 0, '2025-12-30 00:00:00', '2025-12-30 00:00:00', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (144, 0, 142, '0,117,142', '新增', 3, '', '', 'ai:metadata-field:save', '', 1, 1, 1, 0, 0, '2025-12-30 00:00:00', '2025-12-30 00:00:00', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (145, 0, 142, '0,117,142', '编辑', 3, '', '', 'ai:metadata-field:update', '', 2, 1, 1, 0, 0, '2025-12-30 00:00:00', '2025-12-30 00:00:00', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (146, 0, 142, '0,117,142', '删除', 3, '', '', 'ai:metadata-field:delete', '', 3, 1, 1, 0, 0, '2025-12-30 00:00:00', '2025-12-30 00:00:00', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (148, 0, 127, '0,117,127', '切换启用', 3, '', '', 'ai:model-config:toggle', '', 5, 1, 1, 0, 0, '2026-01-04 17:26:40', '2026-01-04 17:26:40', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (149, 0, 119, '0,117,119', '刷新向量', 3, '', '', 'ai:knowledge:rebuild', '', 5, 1, 1, 0, 0, '2026-01-04 21:54:40', '2026-01-04 21:54:40', 'admin', 'admin', 0, 0, 0, 0);
INSERT INTO `sys_menu` VALUES (150, 0, 117, '0,117', '文件知识库', 2, '/admin/ai/file-knowledge', 'ai/file-knowledge/index', 'ai:file-knowledge:page', 'FolderOpened', 5, 1, 1, 0, 0, '2026-01-12 17:55:45', '2026-01-12 17:55:45', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (151, 0, 150, '0,117,150', '查看', 3, '', '', 'ai:file-knowledge:view', '', 0, 1, 1, 0, 0, '2026-01-12 17:55:45', '2026-01-12 17:55:45', 'admin', 'admin', 0, 0, 0, 0);
INSERT INTO `sys_menu` VALUES (152, 0, 150, '0,117,150', '上传', 3, '', '', 'ai:file-knowledge:save', '', 1, 1, 1, 0, 0, '2026-01-12 17:55:45', '2026-01-12 17:55:45', 'admin', 'admin', 0, 0, 0, 0);
INSERT INTO `sys_menu` VALUES (153, 0, 150, '0,117,150', '编辑', 3, '', '', 'ai:file-knowledge:update', '', 2, 1, 1, 0, 0, '2026-01-12 17:55:45', '2026-01-12 17:55:45', 'admin', 'admin', 0, 0, 0, 0);
INSERT INTO `sys_menu` VALUES (154, 0, 150, '0,117,150', '删除', 3, '', '', 'ai:file-knowledge:delete', '', 3, 1, 1, 0, 0, '2026-01-12 17:55:45', '2026-01-12 17:55:45', 'admin', 'admin', 0, 0, 0, 0);
INSERT INTO `sys_menu` VALUES (155, 0, 150, '0,117,150', '重建向量', 3, '', '', 'ai:file-knowledge:rebuild', '', 4, 1, 1, 0, 0, '2026-01-12 17:55:45', '2026-01-12 17:55:45', 'admin', 'admin', 0, 0, 0, 0);
INSERT INTO `sys_menu` VALUES (156, 0, 0, '0', '租户管理', 1, NULL, NULL, NULL, 'OfficeBuilding', 5, 1, 1, 0, 0, '2026-01-13 21:05:50', '2026-01-13 21:05:50', 'admin', 'admin', 0, 1, 2, 0);
INSERT INTO `sys_menu` VALUES (157, 0, 156, '0,156', '租户列表', 2, '/admin/sys/tenant', 'system/tenant/index', 'tenant:list', 'Avatar', 1, 1, 1, 0, 0, '2026-01-13 21:05:50', '2026-01-29 14:01:32', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (158, 0, 156, '0,156', '套餐管理', 2, '/admin/sys/package', 'system/package/index', 'tenant:package:list', 'Box', 2, 1, 1, 0, 0, '2026-01-13 21:05:50', '2026-01-29 14:01:32', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (159, 0, 157, '0,156,157', '查看', 3, '', '', 'tenant:query', '', 0, 1, 1, 0, 0, '2026-01-13 21:05:50', '2026-01-29 14:01:32', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (160, 0, 157, '0,156,157', '新增', 3, '', '', 'tenant:add', '', 1, 1, 1, 0, 0, '2026-01-13 21:05:50', '2026-01-29 14:01:33', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (161, 0, 157, '0,156,157', '编辑', 3, '', '', 'tenant:edit', '', 2, 1, 1, 0, 0, '2026-01-13 21:05:50', '2026-01-29 14:01:33', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (162, 0, 157, '0,156,157', '删除', 3, '', '', 'tenant:remove', '', 3, 1, 1, 0, 0, '2026-01-13 21:05:50', '2026-01-29 14:01:33', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (163, 0, 157, '0,156,157', '导出', 3, '', '', 'tenant:export', '', 4, 1, 1, 0, 0, '2026-01-13 21:05:50', '2026-01-29 14:01:33', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (164, 0, 157, '0,156,157', '状态切换', 3, '', '', 'tenant:status', '', 5, 1, 1, 0, 0, '2026-01-13 21:05:50', '2026-01-29 14:01:33', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (165, 0, 158, '0,156,158', '查看', 3, '', '', 'tenant:package:query', '', 0, 1, 1, 0, 0, '2026-01-13 21:05:50', '2026-01-29 14:01:33', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (166, 0, 158, '0,156,158', '新增', 3, '', '', 'tenant:package:add', '', 1, 1, 1, 0, 0, '2026-01-13 21:05:50', '2026-01-29 14:01:33', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (167, 0, 158, '0,156,158', '编辑', 3, '', '', 'tenant:package:edit', '', 2, 1, 1, 0, 0, '2026-01-13 21:05:50', '2026-01-29 14:01:33', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (168, 0, 158, '0,156,158', '删除', 3, '', '', 'tenant:package:remove', '', 3, 1, 1, 0, 0, '2026-01-13 21:05:50', '2026-01-29 14:01:33', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (169, 0, 158, '0,156,158', '导出', 3, '', '', 'tenant:package:export', '', 4, 1, 1, 0, 0, '2026-01-13 21:05:50', '2026-01-29 14:01:33', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (170, 0, 158, '0,156,158', '状态切换', 3, '', '', 'tenant:package:status', '', 5, 1, 1, 0, 0, '2026-01-13 21:05:50', '2026-01-29 14:01:33', 'admin', 'admin', 0, 1, 0, 0);
INSERT INTO `sys_menu` VALUES (267, 0, 112, '0,3,112', '查看', 3, '', '', 'sys:dict-data:view', '', 1, 1, 1, 0, 0, '2026-01-20 21:35:47', '2026-01-20 21:35:47', 'admin', 'admin', 0, 0, 0, 0);

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '通知ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '通知标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '通知内容（富文本HTML）',
  `publisher_id` bigint NOT NULL COMMENT '发布人ID',
  `publisher_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '发布人姓名',
  `publish_time` datetime NULL DEFAULT NULL COMMENT '发布时间',
  `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '状态：0-草稿, 1-已发布, 2-已撤回',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除, 1-已删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_publish_time`(`publish_time` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE,
  INDEX `idx_is_deleted`(`is_deleted` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统通知公告表' ROW_FORMAT = DYNAMIC;

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
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '通知阅读记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_notice_read_record
-- ----------------------------

-- ----------------------------
-- Table structure for sys_oper_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_oper_log`;
CREATE TABLE `sys_oper_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户ID',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作模块',
  `business_type` tinyint(1) NOT NULL DEFAULT 0 COMMENT '业务类型（0-新增，1-修改，2-删除）',
  `method` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '方法名称',
  `request_method` tinyint(1) NULL DEFAULT NULL COMMENT '请求方式（1-GET，2-POST，3-PUT，4-DELETE）',
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
  INDEX `idx_title`(`title` ASC) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 491 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户操作日志表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_oper_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户ID（0-平台，>0-租户）',
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
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统岗位表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_post
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户ID（0-平台，>0-租户）',
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
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 48 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, 1, '系统管理员', 'admin', 2, 1, '管理系统基础配置', '2025-06-21 09:31:38', '2026-01-29 13:34:13', 'system', 'system', 0);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_role_menu`(`role_id` ASC, `menu_id` ASC) USING BTREE,
  INDEX `idx_menu_id`(`menu_id` ASC) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 170 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色菜单关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for sys_tenant
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant`;
CREATE TABLE `sys_tenant`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '租户ID',
  `tenant_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户编码',
  `tenant_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户名称',
  `package_id` bigint NULL DEFAULT NULL COMMENT '套餐ID',
  `contact_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系人',
  `contact_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态（1-正常，0-停用）',
  `expire_time` datetime NULL DEFAULT NULL COMMENT '过期时间',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '删除标志（0-未删除，1-已删除）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tenant_code`(`tenant_code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '租户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_tenant
-- ----------------------------

-- ----------------------------
-- Table structure for sys_tenant_package
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant_package`;
CREATE TABLE `sys_tenant_package`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '套餐ID',
  `package_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '套餐名称',
  `menu_ids` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '关联的菜单ID（逗号分隔）',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态（1-正常，0-停用）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '删除标志（0-未删除，1-已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '租户套餐表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_tenant_package
-- ----------------------------
INSERT INTO `sys_tenant_package` VALUES (1, '默认套餐', '1,3,49,73,76,69,70,72,74,75,50,86,77,78,79,51,87,80,81,82,52,111,83,84,85,102,53,104,103,105,101,54,91,88,89,90,112,267,97,98,100,55,95,92,93,94,96,114,115,116,6,63,4,59,113,60,5,61,108,110,62,106,109,7,65,66,117,118,127,148,134,128,129,130,131,132,135,136,137,138,139,140,142,143,144,145,146,150,151,152,153,154,155,119,149,120,121,122,123', 1, '系统默认套餐，包含所有租户菜单', '2026-01-29 13:34:02', '2026-01-29 13:34:02', 0);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户ID（0-平台，>0-租户）',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '昵称',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像URL',
  `is_enabled` tinyint(1) NULL DEFAULT 1 COMMENT '是否启用（1启用 0禁用）',
  `dept_id` bigint NULL DEFAULT NULL COMMENT '所属部门 ID',
  `last_login_time` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '最后登录IP',
  `is_admin` tinyint(1) UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '是否系统管理员（0-否，1-是）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新人',
  `deleted` tinyint NOT NULL DEFAULT 0 COMMENT '删除标志（0-未删除，1-已删除）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_username`(`username` ASC, `tenant_id` ASC, `deleted` ASC) USING BTREE,
  INDEX `idx_email`(`email` ASC) USING BTREE,
  INDEX `idx_phone`(`phone` ASC) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 0, 'admin', '$2a$10$Ke7.fLoqfUyIcVPYTR5GXeLi.Bj4G/IyuJRZXaqZffJCuSGHEQ78K', '系统管理员', 'admin@example.com', '13800021113', 'http://106.54.167.194//upload/20251208/82301499-2a90-4b2b-be41-9269980091e6.jpg', 1, 1, '2026-02-21 17:53:10', '127.0.0.1', 1, '2025-06-21 09:20:10', '2026-02-21 17:53:10', 'system', NULL, 0);

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
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `post_id` bigint NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_user_post`(`user_id` ASC, `post_id` ASC) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户-岗位关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_user_role`(`user_id` ASC, `role_id` ASC) USING BTREE,
  INDEX `idx_role_id`(`role_id` ASC) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 42 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户角色关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------

-- ----------------------------
-- Table structure for tool_file
-- ----------------------------
DROP TABLE IF EXISTS `tool_file`;
CREATE TABLE `tool_file`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '文件ID',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户ID',
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
  INDEX `idx_upload_time`(`upload_time` ASC) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 70 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统文件管理表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tool_file
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
