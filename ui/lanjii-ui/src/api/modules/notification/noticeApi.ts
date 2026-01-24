import {del, get, post, put} from '@/api/http'
import type {
    Notice,
    NoticeListResponse,
    NoticeQuery,
    NoticeStatus,
    PublishNoticeRequest,
    PublishNoticeResponse,
    UnreadCount
} from '@/types/notification/notice'

// 获取通知列表
export const getNoticeList = (params: NoticeQuery) => {
    return get<NoticeListResponse>('/admin/notice', params)
}

// 获取通知详情
export const getNoticeDetail = (id: string | number) => {
    return get<Notice>(`/admin/notice/${id}`)
}

// 获取未读数统计
export const getUnreadCount = () => {
    return get<UnreadCount>('/admin/notice/unread-count')
}

// 标记单条通知为已读
export const markAsRead = (id: number) => {
    return put(`/admin/notice/${id}/read`)
}

// 标记全部通知为已读
export const markAllAsRead = () => {
    return put('/admin/notice/read-all')
}

// 获取最近通知（用于铃铛下拉）
export const getRecentNotices = (limit: number = 5, readStatus?: NoticeStatus) => {
    const params: any = {limit}
    if (readStatus !== undefined && readStatus !== null) {
        params.readStatus = readStatus
    }
    return get<Notice[]>('/admin/notice/recent', params)
}

// 发布通知
export const publishNotice = (data: PublishNoticeRequest) => {
    return post<PublishNoticeResponse>('/admin/notice', data)
}

// 删除通知
export const deleteNotice = (id: number) => {
    return del(`/admin/notice/${id}`)
}
