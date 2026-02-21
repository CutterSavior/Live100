// 通知公告类型定义

/** 通知阅读状态：0-未读, 1-已读 */
export type NoticeStatus = 0 | 1

/** 通知公告实体 */
export interface Notice {
  id: number
  title: string
  content: string
  status?: number              // 发布状态：0-草稿, 1-已发布
  statusLabel?: string         // 状态标签（中文）
  readStatus: NoticeStatus     // 阅读状态：0-未读, 1-已读
  publisherId?: number
  publisherName: string
  publishTime: string
  readTime?: string
}

/** 通知列表查询参数 */
export interface NoticeQuery {
  page?: number
  limit?: number
  keyword?: string
  readStatus?: NoticeStatus | null
}

/** 通知列表响应 */
export interface NoticeListResponse {
  list: Notice[]
  total: number
  page: number
  limit: number
}

/** 未读数统计 */
export interface UnreadCount {
  total: number
  unreadCount: number
}

/** 发布通知请求参数 */
export interface PublishNoticeRequest {
  title: string
  content: string
}

/** 发布通知响应 */
export interface PublishNoticeResponse {
  id: number
  message?: string
}

/** 通知状态选项（用于下拉选择） */
export const NOTICE_STATUS_OPTIONS = [
  { label: '未读', value: 0 },
  { label: '已读', value: 1 }
]
