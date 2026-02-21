import { del, get, post, put } from '@/api/http'
import type { SysTenant } from '@/types/tenant/sysTenant'

// 获取租户列表
export function getTenantList(params: any) {
    return get<SysTenant[]>('/admin/tenant', params)
}

// 获取租户详情
export function getTenantById(id: number) {
    return get<SysTenant>(`/admin/tenant/${id}`)
}

// 新增租户
export function createTenant(data: SysTenant) {
    return post('/admin/tenant', data)
}

// 更新租户
export function updateTenant(id: number, data: SysTenant) {
    return put(`/admin/tenant/${id}`, data)
}

// 删除租户
export function deleteTenant(id: number) {
    return del(`/admin/tenant/${id}`)
}
