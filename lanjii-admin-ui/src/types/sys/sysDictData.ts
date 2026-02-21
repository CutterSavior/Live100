/**
 * 系统字典数据
 */
export interface SysDictData {
  /** 字典数据ID */
  id?: number
  /** 字典类型编码 */
  dictType?: string
  /** 字典标签 */
  dictLabel?: string
  /** 字典键值 */
  dictValue?: number
  /** 显示顺序 */
  sortOrder?: number
  /** 是否默认（1是 0否） */
  isDefault?: number
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
  /** 标签类型: success/info/warning/danger/primary */
  tagType?: 'success' | 'warning' | 'danger' | 'info' | 'primary'
  /** 自定义标签颜色（hex或颜色名） */
  tagColor?: string
  /** 标签主题: dark/light/plain */
  tagEffect?: 'dark' | 'light' | 'plain'
}
