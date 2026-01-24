import { del, get, post, put } from '@/api/http'
import type { AiModelConfig } from '@/types/ai/aiModelConfig'
import type { PageResponse } from '@/types/common'

// 获取模型配置分页列表
export function getModelConfigList(params: any) {
  return get<PageResponse<AiModelConfig>>('/admin/ai/model-config/page', params)
}

// 详情
export function getModelConfigById(id: number) {
  return get<AiModelConfig>(`/admin/ai/model-config/${id}`)
}

// 新增
export function createModelConfig(data: AiModelConfig) {
  return post('/admin/ai/model-config', data)
}

// 更新
export function updateModelConfig(id: number, data: AiModelConfig) {
  return put(`/admin/ai/model-config/${id}`, data)
}

// 删除
export function deleteModelConfig(id: number) {
  return del(`/admin/ai/model-config/${id}`)
}

// 设置默认
export function setModelDefault(id: number) {
  return post(`/admin/ai/model-config/${id}/default`)
}

// 切换启用状态
export function toggleModelEnabled(id: number, enabled: number) {
  return put(`/admin/ai/model-config/${id}/enabled?enabled=${enabled}`)
}
