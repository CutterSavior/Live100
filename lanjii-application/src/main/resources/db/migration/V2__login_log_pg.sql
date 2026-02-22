CREATE TABLE IF NOT EXISTS sys_login_log (
  id BIGSERIAL PRIMARY KEY,
  tenant_id BIGINT NOT NULL DEFAULT 0,
  username VARCHAR(50) NOT NULL,
  ip_address VARCHAR(128),
  login_location VARCHAR(255),
  browser VARCHAR(50),
  os VARCHAR(50),
  login_type SMALLINT NOT NULL DEFAULT 0,
  status SMALLINT NOT NULL DEFAULT 0,
  msg VARCHAR(255),
  login_time TIMESTAMP NOT NULL DEFAULT NOW(),
  create_time TIMESTAMP NOT NULL DEFAULT NOW(),
  update_time TIMESTAMP NOT NULL DEFAULT NOW(),
  create_by VARCHAR(64),
  update_by VARCHAR(64)
);

CREATE INDEX IF NOT EXISTS idx_login_log_username ON sys_login_log (username);
CREATE INDEX IF NOT EXISTS idx_login_log_tenant ON sys_login_log (tenant_id);
