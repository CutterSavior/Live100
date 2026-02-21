import {get, del} from '@/api/http'
import type {SysOperLog} from '@/types/log/sysOperLog.ts'
import type {PageResponse} from '@/types/common'

// 获取操作日志分页列表
export function getOperLogList(params: any) {
    return get<PageResponse<SysOperLog>>('/admin/sys/oper-logs', params)
}

// 获取操作日志详情
export function getOperLogById(id: number) {
    return get<SysOperLog>(`/admin/sys/oper-logs/${id}`)
}

// 清空操作日志
export function cleanOperLogs() {
    return del('/admin/sys/oper-logs/clean')
}

