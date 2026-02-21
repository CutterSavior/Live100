import {del, get, post, put} from '@/api/http'
import type {SysRole} from '@/types/sys/sysRole.ts'
import type {PageResponse} from '@/types/common'

// 获取角色分页列表
export function getRoleList(params: any) {
    return get<PageResponse<SysRole>>('/admin/sys/roles', params)
}

// 获取角色详情
export function getRoleById(id: number) {
    return get<SysRole>(`/admin/sys/roles/${id}`)
}

// 新增角色
export function createRole(role: SysRole) {
    return post('/admin/sys/roles', role)
}

// 更新角色
export function updateRole(id: number, role: SysRole) {
    return put(`/admin/sys/roles/${id}`, role)
}

// 删除角色
export function deleteRole(id: number) {
    return del(`/admin/sys/roles/${id}`)
}

// 获取所有角色
export function getAllRoles() {
    return get<SysRole[]>('/admin/sys/roles/all')
}

// 获取角色已分配的菜单ID列表
export function getRoleMenuIds(roleId: number) {
    return get<number[]>(`/admin/sys/roles/${roleId}/menus`)
}

// 为角色分配菜单权限
export function assignRoleMenus(roleId: number, menuIds: number[]) {
    return post(`/admin/sys/roles/${roleId}/menus`, { menuIds })
}
