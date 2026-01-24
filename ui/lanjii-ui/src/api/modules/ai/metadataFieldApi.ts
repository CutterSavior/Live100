import { del, get, post, put } from '@/api/http'
import type { AiMetadataField } from '@/types/ai/aiMetadataField'
import type { PageResponse } from '@/types/common'

// 获取元数据字段分页列表
export function getMetadataFieldPage(params: any) {
  return get<PageResponse<AiMetadataField>>('/admin/ai/metadata-field/page', params)
}

// 详情
export function getMetadataFieldById(id: number) {
  return get<AiMetadataField>(`/admin/ai/metadata-field/${id}`)
}

// 新增
export function createMetadataField(data: AiMetadataField) {
  return post('/admin/ai/metadata-field', data)
}

// 更新
export function updateMetadataField(id: number, data: AiMetadataField) {
  return put(`/admin/ai/metadata-field/${id}`, data)
}

// 删除
export function deleteMetadataField(id: number) {
  return del(`/admin/ai/metadata-field/${id}`)
}
