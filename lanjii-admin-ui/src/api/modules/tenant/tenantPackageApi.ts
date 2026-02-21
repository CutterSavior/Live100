import { del, get, post, put } from '@/api/http'
import type { SysTenantPackage } from '@/types/tenant/sysTenantPackage'

// 获取套餐列表
export function getPackageList(params: any) {
    return get<SysTenantPackage[]>('/admin/tenant/package', params)
}

// 获取套餐详情
export function getPackageById(id: number) {
    return get<SysTenantPackage>(`/admin/tenant/package/${id}`)
}

// 新增套餐
export function createPackage(data: SysTenantPackage) {
    return post('/admin/tenant/package', data)
}

// 更新套餐
export function updatePackage(id: number, data: SysTenantPackage) {
    return put(`/admin/tenant/package/${id}`, data)
}

// 删除套餐
export function deletePackage(id: number) {
    return del(`/admin/tenant/package/${id}`)
}
