import {get, getBlob, post} from '@/api/http'
import type {ToolFile} from '@/types/tool/toolFile.ts'
import type {PageResponse} from '@/types/common'

// 上传文件
export function uploadFile(formData: FormData) {
    return post<ToolFile>('/admin/tool/files/upload', formData, {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
}

// 查询文件详情
export function getFileById(id: number) {
    return get<ToolFile>(`/admin/tool/files/${id}`)
}

// 查询文件分页列表
export function getFileList(params?: {
    pageNum?: number
    pageSize?: number
    fileName?: string
    fileType?: string
    fileExtension?: string
}) {
    return get<PageResponse<ToolFile>>('/admin/tool/files', params)
}

// 下载文件
export function downloadFile(id: number): Promise<Blob> {
    return getBlob(`/admin/tool/files/${id}/download`)
}
