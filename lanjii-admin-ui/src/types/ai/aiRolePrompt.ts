/**
 * AI 角色与提示词 类型定义
 */
export interface AiRolePrompt {
  id?: number

  /** 角色名称 */
  roleName: string


  /** 角色描述 */
  description?: string

  /** 是否启用（1 启用 0 禁用） */
  isEnabled: number

  /** 系统提示词 */
  systemPrompt: string

  /** 是否启用 RAG（1 启用 0 禁用） */
  isRagEnabled?: number

  /** RAG 提示词模板 */
  ragTemplate?: string

  /** 检索文档数量 topK */
  topK?: number

  /** 相似度阈值 */
  similarityThreshold?: number

  /** 自定义过滤表达式（类 SQL 语法，例如：priority >= 5 && author == 'admin'） */
  customFilter?: string


  /** 创建时间 */
  createTime?: string

  /** 更新时间 */
  updateTime?: string

  /** 创建人 */
  createBy?: string

  /** 更新人 */
  updateBy?: string
}
