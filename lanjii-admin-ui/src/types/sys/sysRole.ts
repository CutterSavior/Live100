/** 系统角色 */
export interface SysRole {
  /** 角色ID */
  id?: number
  /** 角色名称 */
  name: string
  /** 角色编码 */
  code: string
  /** 显示顺序 */
  sortOrder?: number
  /** 是否启用（1启用 0禁用） */
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
