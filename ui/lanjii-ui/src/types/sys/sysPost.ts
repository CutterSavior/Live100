/** 系统岗位 */
export interface SysPost {
  /** 岗位ID */
  id?: number
  /** 岗位名称 */
  postName?: string
  /** 岗位编码 */
  postCode?: string
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
