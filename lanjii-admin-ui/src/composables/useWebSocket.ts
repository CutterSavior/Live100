import {h, onUnmounted, ref} from 'vue'
import {ElNotification} from 'element-plus'
import {useNoticeStore} from '@/stores/notice.store'
import {useUserStore} from '@/stores/user.store'
import type {Notice} from '@/types/notification/notice'
import SockJS from 'sockjs-client';
import {Client} from '@stomp/stompjs'
import {useRouter} from 'vue-router'

export interface WebSocketConfig {
    url?: string                    // WebSocket URL（可选）
    heartbeatInterval?: number      // 心跳间隔（毫秒）
    reconnectDelay?: number         // 重连延迟（毫秒）
}

export function useWebSocket(config: WebSocketConfig = {}) {
    const noticeStore = useNoticeStore()
    const userStore = useUserStore()
    const router = useRouter()

    const client = ref<Client | null>(null)
    const isConnected = ref(false)

    const {
        url = import.meta.env.VITE_WS_URL,
        heartbeatInterval = 4000,   // 默认4秒心跳
        reconnectDelay = 5000       // 默认5秒重连
    } = config

    /**
     * 处理未读数变化
     */
    const handleUnreadCountChange = async (data: { unreadCount: number }) => {
        console.log('📢 收到服务端推送的未读数:', data.unreadCount)
        noticeStore.setUnreadCount(data.unreadCount)

        await noticeStore.fetchRecentNotices()
        console.log('✅ 通知列表已更新')
    }

    /**
     * 处理新通知推送
     */
    const handleNewNotice = (notice: Notice) => {
        console.log('📬 收到新通知:', notice)

        // 页面可见时显示详细通知
        if (document.visibilityState === 'visible') {
            const notification = ElNotification({
                title: '新通知',
                message: h(
                    'div',
                    {
                        style: {
                            display: 'flex',
                            alignItems: 'center',
                            justifyContent: 'space-between',
                            cursor: 'pointer',
                            gap: '12px'
                        },
                        onClick: () => {
                            // 关闭通知弹窗
                            notification.close()
                            // 跳转到通知详情页
                            router.push({name: 'noticeDetail', params: {id: notice.id}})
                        }
                    },
                    [
                        h('span', {style: {flex: 1, fontWeight: 500}}, notice.title),
                        h('span', {
                            style: {
                                fontSize: '16px',
                                color: '#409eff',
                                display: 'flex',
                                alignItems: 'center'
                            }
                        }, '→')
                    ]
                ),
                type: 'info',
                duration: 3000,
                position: 'bottom-right'
            })
        }
    }

    /**
     * 拉取初始数据
     */
    const fetchInitialData = async () => {
        try {
            // 并行拉取未读数统计和通知列表
            await Promise.all([
                noticeStore.fetchUnreadCount(),
                noticeStore.fetchRecentNotices()
            ])
        } catch (error) {
            console.error('拉取初始数据失败:', error)
        }
    }

    /**
     * 连接 WebSocket
     */
    const connect = () => {
        const token = userStore.token
        if (!token) {
            console.warn('未登录，无法建立WebSocket连接')
            return
        }

        noticeStore.setConnectionStatus('connecting')

        // 创建SockJS实例
        const socket = new SockJS(url)

        // 创建STOMP客户端
        client.value = new Client({
            webSocketFactory: () => socket,

            // 连接headers（传递JWT token）
            connectHeaders: {
                'Authorization': `Bearer ${token}`
            },

            // 调试信息（生产环境可关闭）
            debug: (str: string) => {
                if (import.meta.env.DEV) {
                    console.log('[STOMP Debug]', str)
                }
            },

            // 重连配置
            reconnectDelay: reconnectDelay,
            heartbeatIncoming: heartbeatInterval,
            heartbeatOutgoing: heartbeatInterval,

            // 连接成功回调
            onConnect: (frame) => {
                console.log('✅ WebSocket 连接成功', frame)
                isConnected.value = true
                noticeStore.setConnectionStatus('connected')

                // 订阅个人未读数变化（服务端推送）
                client.value?.subscribe('/user/queue/unread-count', (message: any) => {
                    const data = JSON.parse(message.body)
                    handleUnreadCountChange(data)
                })

                // 订阅新通知广播
                client.value?.subscribe('/topic/notices', (message: any) => {
                    const notice: Notice = JSON.parse(message.body)
                    handleNewNotice(notice)
                })

                // 拉取初始数据
                fetchInitialData()
            },

            // 连接失败回调
            onStompError: (frame) => {
                console.error('❌ STOMP错误:', frame.headers['message'])
                console.error('详细信息:', frame.body)
                isConnected.value = false
                noticeStore.setConnectionStatus('error')
            },

            // 断开连接回调
            onDisconnect: () => {
                console.warn('⚠️ WebSocket已断开')
                isConnected.value = false
                noticeStore.setConnectionStatus('disconnected')
            },

            // WebSocket连接错误回调
            onWebSocketError: (event: any) => {
                console.error('❌ WebSocket错误:', event)
                noticeStore.setConnectionStatus('error')
            },

            // 连接前回调（用于调试）
            beforeConnect: () => {
                console.log('🔌 准备连接WebSocket...')
                console.log('连接地址:', url)
                console.log('Token:', token ? `${token.substring(0, 20)}...` : '无Token')
            }
        })

        client.value.activate()
    }

    /**
     * 断开连接
     */
    const disconnect = () => {
        if (client.value) {
            client.value.deactivate()
            console.log('WebSocket已主动断开')
        }

        isConnected.value = false
        noticeStore.setConnectionStatus('disconnected')
    }

    /**
     * 发送消息（可选 - 用于主动发送消息）
     */
    const send = (destination: string, body: any) => {
        if (client.value && isConnected.value) {
            client.value.publish({
                destination,
                body: typeof body === 'string' ? body : JSON.stringify(body)
            })
        } else {
            console.warn('WebSocket 未连接，无法发送消息')
        }
    }

    // 页面可见性监听 - 切换回标签页时重连
    const handleVisibilityChange = () => {
        if (!document.hidden && !isConnected.value && userStore.isLoggedIn) {
            console.log('🔄 页面切换回来，尝试重连...')
            connect()
        }
    }

    // 网络状态监听 - 网络恢复时重连
    const handleOnline = () => {
        console.log('🌐 网络已恢复，尝试重连...')
        if (!isConnected.value && userStore.isLoggedIn) {
            connect()
        }
    }

    // 注册事件监听
    document.addEventListener('visibilitychange', handleVisibilityChange)
    window.addEventListener('online', handleOnline)

    // 组件卸载时清理
    onUnmounted(() => {
        document.removeEventListener('visibilitychange', handleVisibilityChange)
        window.removeEventListener('online', handleOnline)
        disconnect()
    })

    return {
        isConnected,
        connect,
        disconnect,
        send
    }
}
