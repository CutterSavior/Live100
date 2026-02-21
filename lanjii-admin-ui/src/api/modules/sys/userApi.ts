import {del, get, getBlob, post, put} from '@/api/http'
import type {SysUser} from '@/types/sys/sysUser.ts'
import type {PageResponse} from '@/types/common'

// 获取用户分页列表
export function getUserList(params: any) {
    return get<PageResponse<SysUser>>('/admin/sys/users', params)
}

// 获取用户详情
export function getUserById(id: number) {
    return get<SysUser>(`/admin/sys/users/${id}`)
}

// 新增用户
export function createUser(sysUser: SysUser) {
    return post('/admin/sys/users', sysUser)
}

// 更新用户
export function updateUser(id: number, sysUser: SysUser) {
    return put(`/admin/sys/users/${id}`, sysUser)
}

// 删除用户
export function deleteUser(id: number) {
    return del(`/admin/sys/users/${id}`)
}

// 切换用户状态
export function toggleUserStatus(id: number) {
    return put(`/admin/sys/users/${id}/status`)
}

// 重置用户密码
export function resetUserPassword(id: number) {
    return put(`/admin/sys/users/${id}/reset-pwd`)
}

// 导出用户数据
export function exportUsers(params: any): Promise<Blob> {
    return getBlob('/admin/sys/users/export', params)
}

// 修改当前用户个人信息
export function updateProfile(data: any) {
    return put('/admin/sys/users/me', data)
} 