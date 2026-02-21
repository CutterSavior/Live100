import type {SysMenu} from "@/types/sys/sysMenu.ts";
import type {SysUser} from "@/types/sys/sysUser.ts";

export interface LoginRequest {
    username: string
    password: string
}


// 登录响应数据类型
export interface LoginResponse {
    token: string
    menusTree: SysMenu[]
    sysUser: SysUser
    permissions: string[]
    displayUuid: string
}

// 验证码响应数据类型
export interface CaptchaResponse {
    captchaKey: string
    imageBase64: string
}
