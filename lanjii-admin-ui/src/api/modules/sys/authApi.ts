import {get, post} from '@/api/http'
import type {CaptchaResponse, LoginRequest, LoginResponse} from '@/types/sys/auth'

export function login(data: LoginRequest) {
    return post<LoginResponse>('/admin/login', data)
}

export function logout() {
    return post('/admin/logout')
}

export function getCaptcha() {
    return get<CaptchaResponse>('/admin/captcha')
}
