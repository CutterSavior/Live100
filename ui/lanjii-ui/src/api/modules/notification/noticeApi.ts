// 通知公告 API

import { get, post, put, del } from '@/api/http'
import type { 
  Notice, 
  NoticeQuery, 
  NoticeListResponse, 
  UnreadCount,
  PublishNoticeRequest,
  PublishNoticeResponse,
  NoticeStatus
} from '@/types/notification/notice'

/**
 * 获取通知列表（分页）
 */
export const getNoticeList = (params: NoticeQuery) => {
  return get<NoticeListResponse>('/admin/notice', params)
}

/**
 * 获取通知详情
 */
export const getNoticeDetail = (id: string | number) => {
  return get<Notice>(`/admin/notice/${id}`)
}

/**
 * 获取未读数统计
 */
export const getUnreadCount = () => {
  return get<UnreadCount>('/admin/notice/unread-count')
}

/**
 * 标记单条通知为已读
 */
export const markAsRead = (id: number) => {
  return put(`/admin/notice/${id}/read`)
}

/**
 * 标记全部通知为已读
 */
export const markAllAsRead = () => {
  return put('/admin/notice/read-all')
}

/**
 * 获取最近通知（用于铃铛下拉）
 * @param limit 限制数量
 * @param readStatus 阅读状态：0-未读, 1-已读，不传则查询全部
 */
export const getRecentNotices = (limit: number = 5, readStatus?: NoticeStatus) => {
  const params: any = { limit }
  if (readStatus !== undefined && readStatus !== null) {
    params.readStatus = readStatus
  }
  return get<Notice[]>('/admin/notice/recent', params)
}

/**
 * 发布通知（管理员功能）
 */
export const publishNotice = (data: PublishNoticeRequest) => {
  return post<PublishNoticeResponse>('/admin/notice', data)
}

/**
 * 删除通知（管理员功能）
 */
export const deleteNotice = (id: number) => {
  return del(`/admin/notice/${id}`)
}
