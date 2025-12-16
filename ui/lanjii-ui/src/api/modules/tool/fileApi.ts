import {get, getBlob, post} from '@/api/http'
import type {ToolFile} from '@/types/tool/toolFile.ts'
import type {PageResponse} from '@/types/common'

/**
 * 文件管理 API
 * 基础路径：/admin/tool/files
 */

// 1. 上传文件（权限: tool:file:upload）
export function uploadFile(formData: FormData) {
    return post<ToolFile>('/admin/tool/files/upload', formData, {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
}

// 2. 查询文件详情（权限: tool:file:view）
export function getFileById(id: number) {
    return get<ToolFile>(`/admin/tool/files/${id}`)
}

// 3. 分页查询文件列表（权限: tool:file:page）
export function getFileList(params?: {
    pageNum?: number
    pageSize?: number
    fileName?: string
    fileType?: string
    fileExtension?: string
}) {
    return get<PageResponse<ToolFile>>('/admin/tool/files', params)
}

// 4. 下载文件（权限: tool:file:download）
export function downloadFile(id: number): Promise<Blob> {
    return getBlob(`/admin/tool/files/${id}/download`)
}
