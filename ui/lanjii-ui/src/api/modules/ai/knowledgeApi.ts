import {del, get, post, put} from '@/api/http'
import type {AiKnowledge} from '@/types/ai/aiKnowledge.ts'
import type {PageResponse} from '@/types/common'

// 获取知识库分页列表
export function getKnowledgeList(params: any) {
    return get<PageResponse<AiKnowledge>>('/admin/ai/knowledge/page', params)
}

// 获取知识库详情
export function getKnowledgeById(id: number) {
    return get<AiKnowledge>(`/admin/ai/knowledge/${id}`)
}

// 新增知识库
export function createKnowledge(data: AiKnowledge) {
    return post('/admin/ai/knowledge', data)
}

// 更新知识库
export function updateKnowledge(id: number, data: AiKnowledge) {
    return put(`/admin/ai/knowledge/${id}`, data)
}

// 删除知识库
export function deleteKnowledge(id: number) {
    return del(`/admin/ai/knowledge/${id}`)
}

// 重建所有知识库向量
export function rebuildKnowledgeVectors() {
    return post('/admin/ai/knowledge/rebuild-vectors', {})
}

// 重建单条知识库向量
export function rebuildKnowledgeVectorById(id: number) {
    return post(`/admin/ai/knowledge/${id}/rebuild-vector`, {})
}

