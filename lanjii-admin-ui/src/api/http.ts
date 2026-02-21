import axios, {
    type AxiosInstance,
    type AxiosRequestConfig,
    type AxiosResponse,
    type InternalAxiosRequestConfig
} from 'axios'

import { ElMessage } from 'element-plus'
import router from '@/router'
import {useUserStore} from "@/stores/user.store";

// 防重复提示标志
let isShowingTokenExpiredMessage = false

// 定义请求响应的数据结构类型
export interface ResponseData<T = any> {
    code: number
    data: T
    msg: string
}

// 创建axios实例
const service: AxiosInstance = axios.create({
    baseURL: '/api',
    timeout: 15000,
    headers: {
        'Content-Type': 'application/json;charset=utf-8'
    }
})
// 请求拦截器
service.interceptors.request.use(
    (config: InternalAxiosRequestConfig) => {
        const userStore = useUserStore()
        const token = userStore.token

        if (token) {
            config.headers = config.headers || {}
            config.headers['Authorization'] = `Bearer ${token}`
        }

        return config
    },
    (error) => {
        console.error('请求错误:', error)
        return Promise.reject(error)
    }
)

// 响应拦截器
service.interceptors.response.use(
    (response: AxiosResponse<ResponseData | Blob>) => {
        // 如果是blob响应，直接返回
        if (response.config.responseType === 'blob') {
            return response
        }

        // 普通JSON响应处理
        const res = response.data as ResponseData

        if (res.code !== 200) {
            // 401状态码特殊处理
            if (res.code === 401) {
                if (!isShowingTokenExpiredMessage) {
                    isShowingTokenExpiredMessage = true
                    ElMessage.error('登录已失效，请重新登录')

                    // 延迟重置标志，避免短时间内重复提示
                    setTimeout(() => {
                        isShowingTokenExpiredMessage = false
                    }, 3000)
                }
                
                const userStore = useUserStore()
                userStore.clearUserData()
                router.replace('/admin/login')
                return Promise.reject(new Error('登录已失效'))
            }

            // 其他错误码正常处理
            const errorMessage = res.msg || '请求失败'
            ElMessage.error(errorMessage)
            return Promise.reject(new Error(errorMessage))
        } else {
            return response
        }
    },
    (error) => {
        console.error('响应错误:', error)

        let errorMessage = '网络异常，请检查您的网络连接'

        if (error.message) {
            errorMessage = error.message
        }

        if (error.response) {
            if (error.response.data && error.response.data.msg) {
                errorMessage = error.response.data.msg
            } else {
                errorMessage = `请求失败，状态码：${error.response.status}`
            }
        }

        ElMessage.error(errorMessage)
        return Promise.reject(error)
    }
)


// 封装GET请求
export function get<T = any>(url: string, params?: any, config?: AxiosRequestConfig): Promise<ResponseData<T>> {
    return service.get(url, {params, ...config}).then(res => {
        return res.data as ResponseData<T>
    })
}

// 封装POST请求
export function post<T = any>(url: string, data?: any, config?: AxiosRequestConfig): Promise<ResponseData<T>> {
    return service.post(url, data, config).then(res => res.data)
}

// 封装PUT请求
export function put<T = any>(url: string, data?: any, config?: AxiosRequestConfig): Promise<ResponseData<T>> {
    return service.put(url, data, config).then(res => res.data)
}

// 封装DELETE请求
export function del<T = any>(url: string, config?: AxiosRequestConfig): Promise<ResponseData<T>> {
    return service.delete(url, config).then(res => res.data)
}

// 导出axios实例
export function getBlob(url: string, params?: any, config?: AxiosRequestConfig): Promise<Blob> {
    return service.get(url, {params, ...config, responseType: 'blob'}).then(res => res.data as Blob)
}

export default service 