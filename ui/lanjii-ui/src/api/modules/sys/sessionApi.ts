import {get, post} from '@/api/http'

// 用户会话信息VO类型定义
export interface UserSessionInfo {
  token: string
  maskedToken: string
  username: string
  active: boolean
  deviceType: string
  createTime: string
  lastAccessTime: string
  clientIp: string
  userAgent: string
  displayUuid: string
}

// 会话分页请求参数
export interface SessionPageParams {
  pageNum: number
  pageSize: number
}

// 会话分页响应
export interface SessionPageResponse {
  total: number
  list: UserSessionInfo[]
}

// 获取在线会话分页列表
export function getSessionList(params: SessionPageParams) {
  return get<SessionPageResponse>('/admin/session/sessions', params)
}

// 踢出指定会话
export function kickSession(displayUuid: string) {
  return post(`/admin/session/kick/${encodeURIComponent(displayUuid)}`)
}

// 清除用户会话缓存
export function clearSessionCache() {
  return post('/admin/session/cache/clear')
}

