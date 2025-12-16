/**
 * 系统配置实体（对应后端SysConfig）
 */
export interface SysConfig {
  /** 配置ID */
  id?: number
  /** 配置名称 */
  configName: string
  /** 配置键名 */
  configKey: string
  /** 配置键值 */
  configValue: string
  /** 配置类型（1系统/2业务） */
  configType?: number
  /** 是否启用（1启用/0禁用） */
  isEnabled?: number
  /** 备注 */
  remark?: string
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
