import { request } from '@/utils/request'

export interface ChatMessage {
  message: string
}

export interface ChatResponse {
  success: boolean
  message: string
}

/**
 * 发送聊天消息
 * @param message 消息内容
 */
export const sendChatMessage = (message: string): Promise<ChatResponse> => {
  return request({
    url: '/chat/stream',
    method: 'POST',
    data: { message: message }
  })
}
