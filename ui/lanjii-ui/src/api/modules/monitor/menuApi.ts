import {del, get, post, put} from '@/api/http'
import type {SysMenu} from '@/types/monitor/sysMenu.ts'
import type {PageParam, PageResponse} from '@/types/common'

// 获取用户菜单（权限: sys:menu:user）
export function getUserMenus() {
    return get<SysMenu[]>('/admin/sys/menus/user')
}

// 获取树形菜单（权限: sys:menu:tree）
export function getMenuTree(filter: SysMenu) {
    return get<SysMenu[]>('/admin/sys/menus/tree', filter)
}

// 获取菜单详情（权限: sys:menu:view）
export function getMenuById(id: number) {
    return get<SysMenu>(`/admin/sys/menus/${id}`)
}

// 获取菜单分页列表（权限: sys:menu:page）
export function getMenuList(pageParam: PageParam, filter: SysMenu) {
    return get<PageResponse<SysMenu>>('/admin/sys/menus', {pageParam, filter})
}

// 新增菜单（权限: sys:menu:save）
export function createMenu(menu: SysMenu) {
    return post('/admin/sys/menus', menu)
}

// 更新菜单（权限: sys:menu:update）
export function updateMenu(id: number, menu: SysMenu) {
    return put(`/admin/sys/menus/${id}`, menu)
}

// 删除菜单（权限: sys:menu:delete）
export function deleteMenu(id: number) {
    return del(`/admin/sys/menus/${id}`)
} 