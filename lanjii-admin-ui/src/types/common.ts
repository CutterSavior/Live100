/**
 * 分页参数
 */
export interface PageParam {
  /** 页码 */
  pageNum: number
  /** 每页大小 */
  pageSize: number
}

/**
 * 分页响应
 */
export interface PageResponse<T> {
  /** 总记录数 */
  total: number
  /** 数据列表 */
  list: T[]
}
