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
    baseURL: import.meta.env.DEV ? '/api' : import.meta.env.VITE_AXIOS_BASE_URL,
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
            console.log('【HTTP】请求设置Token:', {
                url: config.url,
                tokenPrefix: `${token.substring(0, 20)}...`
            })
        } else {
            console.log('【HTTP】请求无Token:', config.url)
        }

        return config
    },
    (error) => {
        console.error('【HTTP】❌ 请求拦截错误:', error)
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

        console.log('【HTTP】响应:', {
            url: response.config.url,
            code: res.code,
            msg: res.msg,
            dataType: typeof res.data,
            dataLength: Array.isArray(res.data) ? res.data.length : 'N/A'
        })

        if (res.code !== 200) {
            // 401状态码特殊处理
            if (res.code === 401) {
                console.error('【HTTP】❌ Token已过期或无效')
                if (!isShowingTokenExpiredMessage) {
                    isShowingTokenExpiredMessage = true
                    ElMessage.error('登录已失效，请重新登录')

                    // 延迟重置标志，避免短时间内重复提示
                    setTimeout(() => {
                        isShowingTokenExpiredMessage = false
                    }, 3000)
                }
                
                const userStore = useUserStore()
                console.log('【HTTP】清除用户数据', {
                    hasToken: !!userStore.token,
                    hasUserInfo: !!userStore.userInfo
                })
                userStore.clearUserData()
                router.replace('/admin/login')
                return Promise.reject(new Error('登录已失效'))
            }

            // 其他错误码正常处理
            const errorMessage = res.msg || '请求失败'
            console.error('【HTTP】❌ 请求失败:', {
                url: response.config.url,
                code: res.code,
                msg: errorMessage
            })
            ElMessage.error(errorMessage)
            return Promise.reject(new Error(errorMessage))
        } else {
            console.log('【HTTP】✅ 请求成功:', response.config.url)
            return response
        }
    },
    (error) => {
        console.error('【HTTP】❌ 响应拦截错误:', error)

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
            console.error('【HTTP】响应错误详情:', {
                url: error.config?.url,
                status: error.response.status,
                msg: errorMessage,
                data: error.response.data
            })
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