import {get, del} from '@/api/http'
import type {SysLoginLog} from '@/types/log/sysLoginLog.ts'
import type {PageResponse} from '@/types/common'

// 获取登录日志分页列表
export function getLoginLogList(params: any) {
    return get<PageResponse<SysLoginLog>>('/admin/sys/login-logs', params)
}

// 获取登录日志详情
export function getLoginLogById(id: number) {
    return get<SysLoginLog>(`/admin/sys/login-logs/${id}`)
}

// 清空登录日志
export function cleanLoginLogs() {
    return del('/admin/sys/login-logs/clean')
}

