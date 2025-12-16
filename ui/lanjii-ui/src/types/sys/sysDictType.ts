/**
 * 系统字典类型实体（对应后端SysDictType）
 */
export interface SysDictType {
  /** 字典类型ID */
  id?: number
  /** 字典类型名称 */
  typeName?: string
  /** 字典类型编码 */
  typeCode?: string
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
