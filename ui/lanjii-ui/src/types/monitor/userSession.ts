/** 在线用户会话 */
export interface UserSession {
  /** 会话 token 原始值 */
  token: string
  /** 已脱敏的 token 显示值 */
  maskedToken: string
  /** 用户名 */
  username: string
  /** 是否在线 */
  active: boolean
  /** 设备类型：PC/Mobile/Tablet/Unknown 等 */
  deviceType: string
  /** 会话创建时间 */
  createTime: string
  /** 最后访问时间 */
  lastAccessTime: string
  /** 客户端 IP */
  clientIp: string
  /** User-Agent 原始字符串 */
  userAgent: string
  /** 用于前端展示和踢出的唯一标识 */
  displayUuid: string
}

/** 用户会话分页请求参数 */
export interface UserSessionPageParams {
  pageNum: number
  pageSize: number
}

/** 用户会话分页响应 */
export interface UserSessionPageResponse {
  total: number
  list: UserSession[]
}
