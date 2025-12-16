import {del, get, post, put} from '@/api/http'
import type {SysPost} from '@/types/sys/sysPost.ts'
import type {PageResponse} from '@/types/common'

// 获取所有岗位
export function getAllPosts() {
    return get<SysPost[]>('/admin/sys/posts/all')
}

// 获取岗位分页列表（权限: sys:post:page）
export function getPostList(params: any) {
    return get<PageResponse<SysPost>>('/admin/sys/posts', params)
}

// 获取岗位详情（权限: sys:post:view）
export function getPostById(id: number) {
    return get<SysPost>(`/admin/sys/posts/${id}`)
}

// 新增岗位（权限: sys:post:save）
export function createPost(sysPost: SysPost) {
    return post('/admin/sys/posts', sysPost)
}

// 更新岗位（权限: sys:post:update）
export function updatePost(id: number, post: SysPost) {
    return put(`/admin/sys/posts/${id}`, post)
}

// 删除岗位（权限: sys:post:delete）
export function deletePost(id: number) {
    return del(`/admin/sys/posts/${id}`)
}
