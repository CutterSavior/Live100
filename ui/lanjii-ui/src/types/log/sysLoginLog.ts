/** 系统登录日志 */
export interface SysLoginLog {
  /** 日志ID */
  id?: number
  /** 用户名 */
  username?: string
  /** 登录IP地址 */
  ipAddress?: string
  /** 登录地点 */
  loginLocation?: string
  /** 浏览器类型 */
  browser?: string
  /** 操作系统 */
  os?: string
  /** 登录类型（0-登录，1-登出） */
  loginType?: number
  /** 登录状态（0-失败，1-成功） */
  status?: number
  /** 提示消息 */
  msg?: string
  /** 登录时间 */
  loginTime?: string
  /** 创建时间 */
  createTime?: string
}
