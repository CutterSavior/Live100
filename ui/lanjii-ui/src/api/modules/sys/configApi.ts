import {del, get, post, put} from '@/api/http'
import type {ResponseData} from '@/api/http'
import type {SysConfig} from '@/types/sys/sysConfig.ts'
import type {PageResponse} from '@/types/common'

// 获取系统配置分页列表（权限: sys:config:page）
export function getConfigList(params: any) {
    return get<PageResponse<SysConfig>>('/admin/sys/configs', params)
}

// 获取系统配置详情（权限: sys:config:view）
export function getConfigById(id: number) {
    return get<SysConfig>(`/admin/sys/configs/${id}`)
}

// 新增系统配置（权限: sys:config:save）
export function createConfig(data: SysConfig) {
    return post('/admin/sys/configs', data)
}

// 更新系统配置（权限: sys:config:update）
export function updateConfig(id: number, data: SysConfig) {
    return put(`/admin/sys/configs/${id}`, data)
}

// 删除系统配置（权限: sys:config:delete）
export function deleteConfig(id: number) {
    return del(`/admin/sys/configs/${id}`)
}

// 查询配置项
export function getConfigValue(configKey: string) {
    return get<string>(`/admin/sys/configs/key/${configKey}`)
}

// 清除系统配置缓存（权限: sys:config:cache）
export function clearConfigCache() {
    return post('/admin/sys/configs/cache/clear', {})
}