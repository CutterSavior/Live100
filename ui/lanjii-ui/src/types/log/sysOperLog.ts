/**
 * 系统操作日志实体（对应后端SysOperLog）
 */
export interface SysOperLog {
  /** 日志ID */
  id?: number
  /** 操作模块 */
  operModule?: string
  /** 操作类型 */
  operType?: string
  /** 操作描述 */
  operDesc?: string
  /** 请求方法 */
  requestMethod?: string
  /** 请求URL */
  requestUrl?: string
  /** 请求参数 */
  requestParam?: string
  /** 响应结果 */
  responseResult?: string
  /** 操作时间 */
  operTime?: string
  /** 执行耗时（毫秒） */
  costTime?: number
  /** 用户ID */
  userId?: number
  /** 用户名 */
  username?: string
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
