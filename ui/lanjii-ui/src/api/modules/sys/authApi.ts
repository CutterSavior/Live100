import {post, get} from '@/api/http'
import type {SysUser} from "@/types/sys/sysUser.ts";

// 登录请求参数类型
export interface LoginRequest {
    username: string
    password: string
}

// 登录响应数据类型
export interface LoginResponse {
    token: string
    menusTree: MenuItem[]
    sysUser: SysUser
    permissions: string[]
    displayUuid: string
}

// 菜单项类型
export interface MenuItem {
    id: number
    name: string
    path: string
    icon?: string
    type: number
    component?: string
    children?: MenuItem[]
}

export function login(data: LoginRequest) {
    return post<LoginResponse>('/admin/login', data)
}

export function logout() {
    return post('/admin/logout')
}

// 验证码响应数据类型
export interface CaptchaResponse {
    captchaKey: string
    imageBase64: string
}

export function getCaptcha() {
    return get<CaptchaResponse>('/admin/captcha')
}
