/**
 * 系统菜单实体（对应后端SysMenu）
 */
export interface SysMenu {
  /** 菜单ID */
  id?: number
  /** 父菜单ID */
  parentId?: number
  /** 菜单名称 */
  name: string
  /** 菜单类型（1-目录，2-菜单，3-按钮） */
  type: number
  /** 路由路径(前端路由或外链URL) */
  path?: string
  /** 组件路径 */
  component?: string
  /** 权限标识 */
  permission?: string
  /** 菜单图标 */
  icon?: string
  /** 显示顺序 */
  sortOrder?: number
  /** 是否可见（0-隐藏，1-显示） */
  isVisible?: number
  /** 是否启用（1启用 0禁用） */
  isEnabled?: number
  /** 是否外链（0-否，1-是） */
  isExt?: number
  /** 打开方式（0-内嵌，1-新窗口） */
  openMode?: number
  /** 子菜单列表 */
  children?: SysMenu[]
  /** 启用状态标签（字典转换） */
  isEnabledLabel?: string
  /** 可见状态标签（字典转换） */
  isVisibleLabel?: string
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
