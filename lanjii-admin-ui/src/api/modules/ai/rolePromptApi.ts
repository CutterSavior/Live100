import { del, get, post, put } from '@/api/http'
import type { AiRolePrompt } from '@/types/ai/aiRolePrompt'
import type { PageResponse } from '@/types/common'

// 获取角色与提示词分页列表
export function getRolePromptPage(params: any) {
  return get<PageResponse<AiRolePrompt>>('/admin/ai/role-prompt/page', params)
}

// 启用的角色与提示词列表
export function getEnabledRolePrompts() {
  return get<AiRolePrompt[]>('/admin/ai/role-prompt/all')
}

// 详情
export function getRolePromptById(id: number) {
  return get<AiRolePrompt>(`/admin/ai/role-prompt/${id}`)
}

// 新增
export function createRolePrompt(data: AiRolePrompt) {
  return post('/admin/ai/role-prompt', data)
}

// 更新
export function updateRolePrompt(id: number, data: AiRolePrompt) {
  return put(`/admin/ai/role-prompt/${id}`, data)
}

// 删除
export function deleteRolePrompt(id: number) {
  return del(`/admin/ai/role-prompt/${id}`)
}

// 切换启用状态
export function toggleRolePrompt(id: number) {
  return post(`/admin/ai/role-prompt/${id}/toggle`)
}
