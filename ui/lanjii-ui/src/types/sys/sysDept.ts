/**
 * 系统部门实体（对应后端SysDept）
 */
export interface SysDept {
  /** 部门ID */
  id?: number
  /** 父部门ID */
  parentId?: number
  /** 祖级列表 */
  ancestors?: string
  /** 部门名称 */
  deptName?: string
  /** 部门编码 */
  deptCode?: string
  /** 显示顺序 */
  sortOrder?: number
  /** 负责人 */
  leader?: string
  /** 联系电话 */
  phone?: string
  /** 邮箱 */
  email?: string
  /** 是否启用（1启用 0禁用） */
  isEnabled?: number
  /** 备注 */
  remark?: string
  /** 子部门列表 */
  children?: SysDept[]
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
