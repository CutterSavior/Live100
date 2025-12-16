import {defineStore} from 'pinia'
import type {Notice} from '@/types/notification/notice'
import {getRecentNotices, getUnreadCount} from '@/api'

/** WebSocket 连接状态 */
export type ConnectionStatus = 'disconnected' | 'connecting' | 'connected' | 'reconnecting' | 'error' | 'failed'

export const useNoticeStore = defineStore('notice', {
    state: () => ({
        connectionStatus: 'disconnected' as ConnectionStatus,
        unreadCount: 0,
        unreadNotices: [] as Notice[],
        allNotices: [] as Notice[]
    }),

    getters: {
        isConnected: (state) => state.connectionStatus === 'connected'
    },

    actions: {
        /**
         * 设置连接状态
         */
        setConnectionStatus(status: ConnectionStatus) {
            this.connectionStatus = status
        },

        /**
         * 设置未读数（WebSocket推送）
         */
        setUnreadCount(count: number) {
            this.unreadCount = count
        },

        /**
         * 拉取最近通知列表
         */
        async fetchRecentNotices() {
            const [unreadRes, allRes] = await Promise.all([
                getRecentNotices(5, 0),
                getRecentNotices(5)
            ])

            this.unreadNotices = unreadRes.data
            this.allNotices = allRes.data
        },


        /**
         * 获取未读数统计
         */
        async fetchUnreadCount() {
            const {data} = await getUnreadCount()
            this.unreadCount = data.total || data.unreadCount || 0
        },

        /**
         * 重置状态
         */
        resetState() {
            this.connectionStatus = 'disconnected'
            this.unreadCount = 0
            this.unreadNotices = []
            this.allNotices = []
        }
    },

    persist: {
        key: 'notice-store',
        storage: localStorage
    }
})
