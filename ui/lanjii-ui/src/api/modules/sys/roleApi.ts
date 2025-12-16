import {del, get, post, put} from '@/api/http'
import type {SysRole} from '@/types/sys/sysRole.ts'
import type {PageResponse} from '@/types/common'

// 获取角色分页列表（权限: sys:role:page）
export function getRoleList(params: any) {
    return get<PageResponse<SysRole>>('/admin/sys/roles', params)
}

// 获取角色详情（权限: sys:role:view）
export function getRoleById(id: number) {
    return get<SysRole>(`/admin/sys/roles/${id}`)
}

// 新增角色（权限: sys:role:save）
export function createRole(role: SysRole) {
    return post('/admin/sys/roles', role)
}

// 更新角色（权限: sys:role:update）
export function updateRole(id: number, role: SysRole) {
    return put(`/admin/sys/roles/${id}`, role)
}

// 删除角色（权限: sys:role:delete）
export function deleteRole(id: number) {
    return del(`/admin/sys/roles/${id}`)
}

// 获取所有角色（不分页）
export function getAllRoles() {
    return get<SysRole[]>('/admin/sys/roles/all')
}

// 获取角色已分配的菜单ID列表（权限: sys:role:permission）
export function getRoleMenuIds(roleId: number) {
    return get<number[]>(`/admin/sys/roles/${roleId}/menus`)
}

// 为角色分配菜单权限（权限: sys:role:permission）
export function assignRoleMenus(roleId: number, menuIds: number[]) {
    return post(`/admin/sys/roles/${roleId}/menus`, { menuIds })
}
