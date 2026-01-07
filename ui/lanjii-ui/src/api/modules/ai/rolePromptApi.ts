import { del, get, post, put } from '@/api/http'
import type { AiRolePrompt } from '@/types/ai/aiRolePrompt'
import type { PageResponse } from '@/types/common'

// 分页列表（权限: ai:role-prompt:page）
export function getRolePromptPage(params: any) {
  return get<PageResponse<AiRolePrompt>>('/admin/ai/role-prompt/page', params)
}

// 启用的角色与提示词列表（下拉选项，权限: ai:role-prompt:page）
export function getEnabledRolePrompts() {
  return get<AiRolePrompt[]>('/admin/ai/role-prompt/all')
}

// 详情（权限: ai:role-prompt:view）
export function getRolePromptById(id: number) {
  return get<AiRolePrompt>(`/admin/ai/role-prompt/${id}`)
}

// 新增（权限: ai:role-prompt:save）
export function createRolePrompt(data: AiRolePrompt) {
  return post('/admin/ai/role-prompt', data)
}

// 更新（权限: ai:role-prompt:update）
export function updateRolePrompt(id: number, data: AiRolePrompt) {
  return put(`/admin/ai/role-prompt/${id}`, data)
}

// 删除（权限: ai:role-prompt:delete）
export function deleteRolePrompt(id: number) {
  return del(`/admin/ai/role-prompt/${id}`)
}

// 切换启用状态（权限: ai:role-prompt:toggle）
export function toggleRolePrompt(id: number) {
  return post(`/admin/ai/role-prompt/${id}/toggle`)
}
