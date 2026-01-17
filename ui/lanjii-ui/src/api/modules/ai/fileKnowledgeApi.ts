import {del, get, post, put} from '@/api/http'
import type {AiFileKnowledge} from '@/types/ai/aiFileKnowledge.ts'
import type {PageResponse} from '@/types/common'

// 获取文件知识库分页列表
export function getFileKnowledgeList(params: any) {
    return get<PageResponse<AiFileKnowledge>>('/admin/ai/file-knowledge/page', params)
}

// 获取文件知识库详情
export function getFileKnowledgeById(id: number) {
    return get<AiFileKnowledge>(`/admin/ai/file-knowledge/${id}`)
}

// 新增文件知识库
export function createFileKnowledge(formData: FormData) {
    return post('/admin/ai/file-knowledge', formData, {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
}

// 更新文件知识库
export function updateFileKnowledge(id: number, formData: FormData) {
    return put(`/admin/ai/file-knowledge/${id}`, formData, {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
}

// 删除文件知识库
export function deleteFileKnowledge(id: number) {
    return del(`/admin/ai/file-knowledge/${id}`)
}

// 重建所有文件知识库向量
export function rebuildFileKnowledgeVectors() {
    return post('/admin/ai/file-knowledge/rebuild-vectors', {})
}

// 重建单条文件知识库向量
export function rebuildFileKnowledgeVectorById(id: number) {
    return post(`/admin/ai/file-knowledge/${id}/rebuild-vector`, {})
}

