import {del, get, post, put} from '@/api/http'
import type {SysDept} from '@/types/sys/sysDept.ts'
import type {PageResponse} from '@/types/common'

// 获取部门树形结构
export function getDeptsTree(params: any) {
    return get<SysDept[]>('/admin/sys/depts/tree', params)
}

// 获取部门分页列表
export function getDeptsList(params: any) {
    return get<PageResponse<SysDept>>('/admin/sys/depts', params)
}

// 获取部门详情
export function getDeptById(id: number) {
    return get<SysDept>(`/admin/sys/depts/${id}`)
}

// 新增部门
export function createDept(dept: SysDept) {
    return post('/admin/sys/depts', dept)
}

// 更新部门
export function updateDept(id: number, dept: SysDept) {
    return put(`/admin/sys/depts/${id}`, dept)
}

// 删除部门
export function deleteDept(id: number) {
    return del(`/admin/sys/depts/${id}`)
}
