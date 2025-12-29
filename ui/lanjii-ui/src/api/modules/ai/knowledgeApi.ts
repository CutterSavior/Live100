import {del, get, post, put} from '@/api/http'
import type {AiKnowledge} from '@/types/ai/aiKnowledge.ts'
import type {PageResponse} from '@/types/common'

// 获取知识库分页列表（权限: ai:knowledge:list）
export function getKnowledgeList(params: any) {
    return get<PageResponse<AiKnowledge>>('/admin/ai/knowledge/page', params)
}

// 获取知识库详情（权限: ai:knowledge:query）
export function getKnowledgeById(id: number) {
    return get<AiKnowledge>(`/admin/ai/knowledge/${id}`)
}

// 新增知识库（权限: ai:knowledge:add）
export function createKnowledge(data: AiKnowledge) {
    return post('/admin/ai/knowledge', data)
}

// 更新知识库（权限: ai:knowledge:edit）
export function updateKnowledge(id: number, data: AiKnowledge) {
    return put(`/admin/ai/knowledge/${id}`, data)
}

// 删除知识库（权限: ai:knowledge:remove）
export function deleteKnowledge(id: number) {
    return del(`/admin/ai/knowledge/${id}`)
}

// 批量删除知识库（权限: ai:knowledge:remove）
export function deleteBatchKnowledge(ids: number[]) {
    return del('/admin/ai/knowledge/batch', ids)
}
