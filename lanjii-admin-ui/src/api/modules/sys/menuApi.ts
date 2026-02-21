import {del, get, post, put} from '@/api/http'
import type {SysMenu} from '@/types/sys/sysMenu.ts'
import type {PageParam, PageResponse} from '@/types/common'

// 获取用户菜单
export function getUserMenus() {
    return get<SysMenu[]>('/admin/sys/menus/user')
}

// 获取树形菜单
export function getMenuTree(filter: Record<string, any>) {
    return get<SysMenu[]>('/admin/sys/menus/tree', filter)
}

// 获取菜单详情
export function getMenuById(id: number) {
    return get<SysMenu>(`/admin/sys/menus/${id}`)
}

// 获取菜单分页列表
export function getMenuList(pageParam: PageParam, filter: SysMenu) {
    return get<PageResponse<SysMenu>>('/admin/sys/menus', {pageParam, filter})
}

// 新增菜单
export function createMenu(menu: SysMenu) {
    return post('/admin/sys/menus', menu)
}

// 更新菜单
export function updateMenu(id: number, menu: SysMenu) {
    return put(`/admin/sys/menus/${id}`, menu)
}

// 删除菜单
export function deleteMenu(id: number) {
    return del(`/admin/sys/menus/${id}`)
} 