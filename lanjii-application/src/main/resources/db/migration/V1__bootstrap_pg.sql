CREATE TABLE IF NOT EXISTS sys_user (
  id BIGSERIAL PRIMARY KEY,
  tenant_id BIGINT NOT NULL DEFAULT 0,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(255) NOT NULL,
  nickname VARCHAR(50),
  email VARCHAR(100),
  phone VARCHAR(20),
  avatar VARCHAR(255),
  is_enabled SMALLINT DEFAULT 1,
  dept_id BIGINT,
  last_login_time TIMESTAMP,
  last_login_ip VARCHAR(50),
  is_admin SMALLINT,
  create_time TIMESTAMP DEFAULT NOW(),
  update_time TIMESTAMP DEFAULT NOW(),
  create_by VARCHAR(50),
  update_by VARCHAR(50),
  deleted SMALLINT DEFAULT 0
);

CREATE UNIQUE INDEX IF NOT EXISTS idx_sys_user_username ON sys_user (username, tenant_id, deleted);

CREATE TABLE IF NOT EXISTS sys_role (
  id BIGSERIAL PRIMARY KEY,
  tenant_id BIGINT NOT NULL DEFAULT 0,
  name VARCHAR(50) NOT NULL,
  code VARCHAR(50) NOT NULL,
  sort_order INT,
  is_enabled SMALLINT DEFAULT 1,
  remark VARCHAR(500),
  create_time TIMESTAMP DEFAULT NOW(),
  update_time TIMESTAMP DEFAULT NOW(),
  create_by VARCHAR(50),
  update_by VARCHAR(50),
  deleted SMALLINT DEFAULT 0
);

CREATE UNIQUE INDEX IF NOT EXISTS idx_sys_role_code ON sys_role (code);

CREATE TABLE IF NOT EXISTS sys_user_role (
  id BIGSERIAL PRIMARY KEY,
  user_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS idx_user_role ON sys_user_role (user_id, role_id);

CREATE TABLE IF NOT EXISTS sys_menu (
  id BIGSERIAL PRIMARY KEY,
  parent_id BIGINT DEFAULT 0,
  ancestors VARCHAR(512) DEFAULT '0',
  name VARCHAR(100) NOT NULL,
  type INT NOT NULL,
  path VARCHAR(255),
  component VARCHAR(255),
  permission VARCHAR(255),
  icon VARCHAR(50),
  sort_order INT DEFAULT 1,
  is_visible SMALLINT DEFAULT 1,
  is_enabled SMALLINT DEFAULT 1,
  is_ext SMALLINT DEFAULT 0,
  open_mode SMALLINT DEFAULT 0,
  is_keep_alive SMALLINT DEFAULT 0,
  scope SMALLINT DEFAULT 0,
  create_time TIMESTAMP DEFAULT NOW(),
  update_time TIMESTAMP DEFAULT NOW(),
  create_by VARCHAR(50),
  update_by VARCHAR(50),
  deleted SMALLINT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS sys_role_menu (
  id BIGSERIAL PRIMARY KEY,
  role_id BIGINT NOT NULL,
  menu_id BIGINT NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS idx_role_menu ON sys_role_menu (role_id, menu_id);

INSERT INTO sys_role (id, tenant_id, name, code, sort_order, is_enabled, remark, create_by, update_by, deleted)
SELECT 1, 0, '系统管理员', 'admin', 1, 1, '系统默认角色', 'system', 'system', 0
WHERE NOT EXISTS (SELECT 1 FROM sys_role WHERE id = 1);

INSERT INTO sys_user (id, tenant_id, username, password, nickname, email, phone, avatar, is_enabled, dept_id, last_login_time, last_login_ip, is_admin, create_by, update_by, deleted)
SELECT 1, 0, 'admin', '$2a$10$Ke7.fLoqfUyIcVPYTR5GXeLi.Bj4G/IyuJRZXaqZffJCuSGHEQ78K', '系统管理员', 'admin@example.com', '13800000000', NULL, 1, NULL, NOW(), '127.0.0.1', 1, 'system', 'system', 0
WHERE NOT EXISTS (SELECT 1 FROM sys_user WHERE id = 1);

INSERT INTO sys_user_role (user_id, role_id)
SELECT 1, 1
WHERE NOT EXISTS (SELECT 1 FROM sys_user_role WHERE user_id = 1 AND role_id = 1);

INSERT INTO sys_menu (id, parent_id, ancestors, name, type, path, component, permission, icon, sort_order, is_visible, is_enabled, is_ext, open_mode, is_keep_alive, scope, create_by, update_by, deleted)
SELECT 100, 0, '0', '首页', 2, '/admin/index', 'home/index', NULL, 'HomeFilled', 1, 1, 1, 0, 0, 1, 0, 'system', 'system', 0
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE id = 100);

INSERT INTO sys_role_menu (role_id, menu_id)
SELECT 1, 100
WHERE NOT EXISTS (SELECT 1 FROM sys_role_menu WHERE role_id = 1 AND menu_id = 100);
