import {get, post} from '@/api/http'
import type {UserSessionPageParams, UserSessionPageResponse} from '@/types/monitor/userSession'

// 获取在线会话分页列表
export function getSessionList(params: UserSessionPageParams) {
  return get<UserSessionPageResponse>('/admin/session/sessions', params)
}

// 踢出指定会话
export function kickSession(displayUuid: string) {
  return post(`/admin/session/kick/${encodeURIComponent(displayUuid)}`)
}