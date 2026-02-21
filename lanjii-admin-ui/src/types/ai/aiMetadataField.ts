/**
 * AI 元数据字段类型定义
 */
export interface AiMetadataField {
  /** 主键ID */
  id?: number

  /** 字段名（英文唯一标识） */
  fieldName: string

  /** 显示名称（中文名称） */
  displayName: string

  /** 数据类型 */
  dataType: string

  /** 默认值 */
  defaultValue?: string

  /** 字段描述 */
  description?: string

  /** 是否必填（1 是 0 否） */
  isRequired: number

  /** 是否可搜索（1 是 0 否） */
  isSearchable: number

  /** 使用次数 */
  useCount?: number

  /** 创建时间 */
  createTime?: string

  /** 更新时间 */
  updateTime?: string

  /** 创建人 */
  createBy?: string

  /** 更新人 */
  updateBy?: string
}
