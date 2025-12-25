<template>
  <div class="ai-chat-container">
    <!-- 左侧历史聊天记录 -->
    <div class="chat-history">
      <div class="history-header">
        <h3>聊天记录</h3>
        <el-button @click="clearHistory" size="small" type="danger" plain>
          清空记录
        </el-button>
      </div>
      <div class="history-list">
        <div
            v-for="(session, index) in chatHistory"
            :key="index"
            :class="['history-item', { active: currentSessionIndex === index }]"
            @click="switchSession(index)"
        >
          <div class="session-title">{{ session.title || `对话 ${index + 1}` }}</div>
          <div class="session-time">{{ formatTime(session.createTime) }}</div>
        </div>
      </div>
      <el-button @click="createNewSession" type="primary" class="new-chat-btn">
        新建对话
      </el-button>
    </div>

    <!-- 右侧聊天对话框 -->
    <div class="chat-main">
      <!-- 演示提示横幅 -->
      <div class="demo-notice">
        <el-alert
          title="演示版本提醒"
          type="warning"
          :closable="false"
          show-icon
        >
          <template #default>
            <span>此AI助手仅作演示使用，请勿输入敏感信息。演示功能可能存在限制，实际效果仅供参考。</span>
          </template>
        </el-alert>
      </div>
      <div class="chat-messages" ref="messagesContainer">
        <div
            v-for="(message, index) in currentMessages"
            :key="index"
            :class="['message-item', message.type]"
        >
          <div class="message-avatar">
            <el-avatar v-if="message.type === 'user'" :size="32">
              <el-icon>
                <User/>
              </el-icon>
            </el-avatar>
            <el-avatar v-else :size="32" style="background-color: #409eff;">
              <el-icon>
                <User/>
              </el-icon>
            </el-avatar>
          </div>
          <div class="message-content">
            <div class="message-text">{{ message.content }}</div>
            <div class="message-time">{{ formatTime(message.timestamp) }}</div>
            <div v-if="message.type === 'assistant' && message.isCompleted" class="completion-indicator">
              <el-tag size="small" type="success">回答完成</el-tag>
            </div>
          </div>
        </div>
        <div v-if="isLoading" class="loading-indicator">
          <el-icon class="is-loading">
            <Loading/>
          </el-icon>
          <span>AI正在思考中...</span>
        </div>
      </div>

      <div class="chat-input">
        <el-input
            v-model="inputMessage"
            type="textarea"
            :rows="3"
            placeholder="请输入您的问题..."
            @keydown.ctrl.enter="sendMessage"
            :disabled="isLoading"
        />
        <div class="input-actions">
          <span class="input-tip">Ctrl + Enter 发送</span>
          <el-button
              type="primary"
              @click="sendMessage"
              :loading="isLoading"
              :disabled="!inputMessage.trim()"
          >
            发送
          </el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import {computed, nextTick, onMounted, ref} from 'vue'
import {ElMessage} from 'element-plus'
import {Loading, User} from '@element-plus/icons-vue'

// 生成随机conversationId
const generateConversationId = (): string => {
  return 'conv_' + Date.now() + '_' + Math.random().toString(36).substr(2, 9)
}

interface ChatMessage {
  type: 'user' | 'assistant'
  content: string
  timestamp: number
  isCompleted?: boolean
}

interface ChatSession {
  id: string
  title: string
  messages: ChatMessage[]
  createTime: number
}

// 响应式数据
const inputMessage = ref('')
const isLoading = ref(false)
const messagesContainer = ref<HTMLElement>()
const chatHistory = ref<ChatSession[]>([])
const currentSessionIndex = ref(0)

// 计算属性
const currentMessages = computed(() => {
  return chatHistory.value[currentSessionIndex.value]?.messages || []
})

// 初始化
onMounted(() => {
  loadChatHistory()
  if (chatHistory.value.length === 0) {
    createNewSession()
  }
})

// 加载聊天历史
const loadChatHistory = () => {
  const saved = localStorage.getItem('ai-chat-history')
  if (saved) {
    chatHistory.value = JSON.parse(saved)
  }
}

// 保存聊天历史
const saveChatHistory = () => {
  localStorage.setItem('ai-chat-history', JSON.stringify(chatHistory.value))
}

// 创建新对话
const createNewSession = () => {
  const newSession: ChatSession = {
    id: generateConversationId(),
    title: `对话 ${chatHistory.value.length + 1}`,
    messages: [],
    createTime: Date.now()
  }
  chatHistory.value.unshift(newSession)
  currentSessionIndex.value = 0
  saveChatHistory()
}

// 切换对话
const switchSession = (index: number) => {
  currentSessionIndex.value = index
}

// 清空历史记录
const clearHistory = () => {
  chatHistory.value = []
  createNewSession()
  saveChatHistory()
  ElMessage.success('聊天记录已清空')
}

// 发送消息
const sendMessage = async () => {
  if (!inputMessage.value.trim() || isLoading.value) return

  const userMessage: ChatMessage = {
    type: 'user',
    content: inputMessage.value.trim(),
    timestamp: Date.now()
  }

  // 添加用户消息
  chatHistory.value[currentSessionIndex.value].messages.push(userMessage)

  // 创建AI回复消息
  const assistantMessage: ChatMessage = {
    type: 'assistant',
    content: '',
    timestamp: Date.now(),
    isCompleted: false
  }
  chatHistory.value[currentSessionIndex.value].messages.push(assistantMessage)

  const messageToSend = inputMessage.value.trim()
  inputMessage.value = ''
  isLoading.value = true

  // 滚动到底部
  await nextTick()
  scrollToBottom()

  try {
    // 使用EventSource接收流式响应
    const currentConversationId = chatHistory.value[currentSessionIndex.value].id
    const eventSource = new EventSource(`/api/chat/stream?message=${encodeURIComponent(messageToSend)}&conversationId=${currentConversationId}`)

    eventSource.onmessage = (event) => {
      if (event.data) {
        // 将接收到的数据追加到AI消息末尾
        const messageIndex = chatHistory.value[currentSessionIndex.value].messages.length - 1
        chatHistory.value[currentSessionIndex.value].messages[messageIndex].content += event.data

        // 滚动到底部
        nextTick(() => scrollToBottom())
      }
    }

    eventSource.onerror = (error) => {
      console.error('EventSource error:', error)
      eventSource.close()
      isLoading.value = false

      // 标记消息完成
      const messageIndex = chatHistory.value[currentSessionIndex.value].messages.length - 1
      chatHistory.value[currentSessionIndex.value].messages[messageIndex].isCompleted = true

      if (!chatHistory.value[currentSessionIndex.value].messages[messageIndex].content) {
        chatHistory.value[currentSessionIndex.value].messages[messageIndex].content = '抱歉，服务暂时不可用，请稍后重试。'
      }

      saveChatHistory()
    }

    eventSource.addEventListener('close', () => {
      eventSource.close()
      isLoading.value = false

      // 标记消息完成
      const messageIndex = chatHistory.value[currentSessionIndex.value].messages.length - 1
      chatHistory.value[currentSessionIndex.value].messages[messageIndex].isCompleted = true

      saveChatHistory()
    })

  } catch (error) {
    console.error('发送消息失败:', error)
    isLoading.value = false
    ElMessage.error('发送失败，请重试')

    // 移除失败的AI消息
    chatHistory.value[currentSessionIndex.value].messages.pop()
  }
}

// 滚动到底部
const scrollToBottom = () => {
  if (messagesContainer.value) {
    messagesContainer.value.scrollTop = messagesContainer.value.scrollHeight
  }
}

// 格式化时间
const formatTime = (timestamp: number) => {
  const date = new Date(timestamp)
  const now = new Date()
  const diff = now.getTime() - date.getTime()

  if (diff < 60000) return '刚刚'
  if (diff < 3600000) return `${Math.floor(diff / 60000)}分钟前`
  if (diff < 86400000) return `${Math.floor(diff / 3600000)}小时前`

  return date.toLocaleDateString() + ' ' + date.toLocaleTimeString().slice(0, 5)
}
</script>

<style scoped>
.ai-chat-container {
  display: flex;
  height: calc(100vh - 141px);
  background: #f5f5f5;
  border-radius: 8px;
  overflow: hidden;
}

.chat-history {
  width: 280px;
  background: white;
  border-right: 1px solid #e4e7ed;
  display: flex;
  flex-direction: column;
}

.history-header {
  padding: 16px;
  border-bottom: 1px solid #e4e7ed;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.history-header h3 {
  margin: 0;
  font-size: 16px;
  color: #303133;
}

.history-list {
  flex: 1;
  overflow-y: auto;
  padding: 8px;
}

.history-item {
  padding: 12px;
  margin-bottom: 8px;
  border-radius: 6px;
  cursor: pointer;
  transition: all 0.2s;
  border: 1px solid transparent;
}

.history-item:hover {
  background: #f0f9ff;
  border-color: #409eff;
}

.history-item.active {
  background: #409eff;
  color: white;
}

.session-title {
  font-size: 14px;
  font-weight: 500;
  margin-bottom: 4px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.session-time {
  font-size: 12px;
  opacity: 0.7;
}

.new-chat-btn {
  margin: 16px;
  width: calc(100% - 32px);
}

.chat-main {
  flex: 1;
  display: flex;
  flex-direction: column;
  background: white;
}

.chat-messages {
  flex: 1;
  overflow-y: auto;
  padding: 16px;
  scroll-behavior: smooth;
}

.message-item {
  display: flex;
  margin-bottom: 24px;
  align-items: flex-start;
}

.message-item.user {
  flex-direction: row-reverse;
}

.message-avatar {
  margin: 0 12px;
}

.message-content {
  max-width: 70%;
  position: relative;
}

.message-item.user .message-content {
  text-align: right;
}

.message-text {
  background: #f0f0f0;
  padding: 12px 16px;
  border-radius: 12px;
  line-height: 1.5;
  word-wrap: break-word;
  white-space: pre-wrap;
}

.message-item.user .message-text {
  background: #409eff;
  color: white;
}

.message-time {
  font-size: 12px;
  color: #909399;
  margin-top: 4px;
}

.completion-indicator {
  margin-top: 8px;
}

.loading-indicator {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
  color: #909399;
}

.loading-indicator .el-icon {
  margin-right: 8px;
}

.chat-input {
  border-top: 1px solid #e4e7ed;
  padding: 16px;
}

.input-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 12px;
}

.input-tip {
  font-size: 12px;
  color: #909399;
}

/* 滚动条样式 */
.history-list::-webkit-scrollbar,
.chat-messages::-webkit-scrollbar {
  width: 6px;
}

.history-list::-webkit-scrollbar-track,
.chat-messages::-webkit-scrollbar-track {
  background: #f1f1f1;
  border-radius: 3px;
}

.history-list::-webkit-scrollbar-thumb,
.chat-messages::-webkit-scrollbar-thumb {
  background: #c1c1c1;
  border-radius: 3px;
}

/* 演示提示横幅样式 */
.demo-notice {
  margin: 16px;
  margin-bottom: 0;
}

.demo-notice .el-alert {
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.demo-notice .el-alert__content {
  font-weight: 500;
}

.history-list::-webkit-scrollbar-thumb:hover,
.chat-messages::-webkit-scrollbar-thumb:hover {
  background: #a8a8a8;
}
</style>
