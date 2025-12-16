/**
 * 系统登录日志实体（对应后端SysLoginLog）
 */
export interface SysLoginLog {
  /** 日志ID */
  id?: number
  /** 用户ID */
  userId?: number
  /** 用户名 */
  username?: string
  /** 登录IP */
  loginIp?: string
  /** 登录地点 */
  loginLocation?: string
  /** 浏览器类型 */
  browser?: string
  /** 操作系统 */
  os?: string
  /** 登录状态（0-失败，1-成功） */
  loginStatus?: number
  /** 登录消息 */
  loginMsg?: string
  /** 登录时间 */
  loginTime?: string
  /** 创建时间 */
  createTime?: string
  /** 更新时间 */
  updateTime?: string
  /** 创建人 */
  createBy?: string
  /** 更新人 */
  updateBy?: string
  /** 删除标志（0-未删除，1-已删除） */
  deleted?: number
}
