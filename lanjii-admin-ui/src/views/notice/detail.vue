<template>
  <div class="main-content">
    <!-- 加载状态 -->
    <div v-if="loading" class="loading-container">
      <el-icon class="is-loading" size="32">
        <Loading />
      </el-icon>
      <p>加载中...</p>
    </div>

    <!-- 错误状态 -->
    <el-result
      v-else-if="error"
      icon="error"
      title="加载失败"
      :sub-title="error"
    >
      <template #extra>
        <el-button type="primary" @click="fetchNoticeDetail">重试</el-button>
      </template>
    </el-result>

    <!-- 通知详情内容 -->
    <div v-else class="card-box table-card">
      <!-- 通知标题 - 居中显示 -->
      <div class="notice-title-section">
        <h1 class="notice-title">{{ noticeData.title }}</h1>
      </div>
      
      <!-- 通知元信息 -->
      <div class="notice-meta-section">
        <div class="meta-item">
          <el-icon><User /></el-icon>
          <span>{{ noticeData.publisherName }}</span>
        </div>
        <div class="meta-item">
          <el-icon><Clock /></el-icon>
          <span>{{ noticeData.publishTime }}</span>
        </div>
      </div>

      <!-- 分割线 -->
      <el-divider />

      <!-- 富文本内容区域 -->
      <div class="rich-text-content" v-html="noticeData.content"></div>
    </div>
  </div>
</template>

<script setup lang="ts">
import {ref, watch} from 'vue'
import {useRoute} from 'vue-router'
import {Clock, Loading, User} from '@element-plus/icons-vue'
import {useNoticeStore} from '@/stores/notice.store'
import {getNoticeDetail} from '@/api/modules/notification/noticeApi'
import type {Notice} from '@/types/notification/notice'

const route = useRoute()
const noticeStore = useNoticeStore()

// 响应式数据
const loading = ref(true)
const error = ref('')
const noticeData = ref<Notice>({} as Notice)

/**
 * 获取通知详情
 */
const fetchNoticeDetail = async () => {
  const noticeId = route.params.id as string
  if (!noticeId) {
    error.value = '通知ID不存在'
    loading.value = false
    return
  }

  try {
    loading.value = true
    error.value = ''
    
    const { data } = await getNoticeDetail(noticeId)
    noticeData.value = data
    
    // 注意：后端getNoticeDetail接口会自动标记为已读并推送未读数，前端无需额外处理
  } catch (err: any) {
    console.error('获取通知详情失败:', err)
    error.value = err.message || '获取通知详情失败'
  } finally {
    loading.value = false
  }
}

/**
 * 监听路由参数变化，当通知ID改变时重新获取详情
 * immediate: true 确保组件初始化时也会调用
 */
watch(() => route.params.id, (newId) => {
  if (newId) {
    fetchNoticeDetail()
  }
}, { immediate: true })
</script>

<style scoped lang="scss">
// 主容器 - 设置合适的最小高度，既占满主体又保留底部灰色空隙
.main-content {
  min-height: calc(100vh - 180px); // 减去头部、面包屑等固定元素高度，保留底部空隙
  
  .card-box {
    min-height: calc(100vh - 220px); // 卡片区域最小高度，确保内容充实
  }
}

// 标题区域
.notice-title-section {
  text-align: center;
  margin-bottom: 24px;

  .notice-title {
    font-size: 28px;
    font-weight: 600;
    color: var(--el-text-color-primary);
    margin: 0;
    line-height: 1.4;
  }
}

// 元信息区域
.notice-meta-section {
  display: flex;
  justify-content: center;
  gap: 32px;
  margin-bottom: 16px;
  color: var(--el-text-color-regular);
  font-size: 14px;

  .meta-item {
    display: flex;
    align-items: center;
    gap: 6px;

    .el-icon {
      font-size: 16px;
    }
  }
}

// 富文本内容区域
.rich-text-content {
  line-height: 1.8;
  color: var(--el-text-color-primary);
  font-size: 15px;

  // 富文本内容样式优化
  :deep(p) {
    margin: 0 0 16px 0;
    
    &:last-child {
      margin-bottom: 0;
    }
  }

  :deep(h1), :deep(h2), :deep(h3), :deep(h4), :deep(h5), :deep(h6) {
    margin: 24px 0 16px 0;
    font-weight: 600;
    color: var(--el-text-color-primary);
    
    &:first-child {
      margin-top: 0;
    }
  }

  :deep(h1) { font-size: 24px; }
  :deep(h2) { font-size: 20px; }
  :deep(h3) { font-size: 18px; }
  :deep(h4) { font-size: 16px; }
  :deep(h5) { font-size: 14px; }
  :deep(h6) { font-size: 13px; }

  :deep(ul), :deep(ol) {
    margin: 16px 0;
    padding-left: 24px;

    li {
      margin: 8px 0;
      line-height: 1.6;
    }
  }

  :deep(blockquote) {
    margin: 16px 0;
    padding: 12px 16px;
    background: var(--el-fill-color-lighter);
    border-left: 4px solid var(--el-color-primary);
    border-radius: 0 4px 4px 0;
  }

  :deep(code) {
    background: var(--el-fill-color-light);
    padding: 2px 6px;
    border-radius: 3px;
    font-family: 'Courier New', monospace;
    font-size: 13px;
  }

  :deep(pre) {
    background: var(--el-fill-color-light);
    padding: 16px;
    border-radius: 6px;
    overflow-x: auto;
    margin: 16px 0;

    code {
      background: none;
      padding: 0;
    }
  }

  :deep(img) {
    max-width: 100%;
    height: auto;
    border-radius: 6px;
    margin: 16px 0;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  }

  :deep(table) {
    width: 100%;
    border-collapse: collapse;
    margin: 16px 0;
    border: 1px solid var(--el-border-color);

    th, td {
      padding: 12px;
      text-align: left;
      border: 1px solid var(--el-border-color);
    }

    th {
      background: var(--el-fill-color-lighter);
      font-weight: 600;
    }
  }

  :deep(a) {
    color: var(--el-color-primary);
    text-decoration: none;

    &:hover {
      text-decoration: underline;
    }
  }

  :deep(strong) {
    font-weight: 600;
    color: var(--el-text-color-primary);
  }

  :deep(em) {
    font-style: italic;
  }

  :deep(u) {
    text-decoration: underline;
  }
}

// 暗黑模式：强制覆盖富文本 inline style 中的硬编码颜色
html.dark {
  .rich-text-content {
    :deep([style*="color"]),
    :deep(span),
    :deep(p),
    :deep(div),
    :deep(li),
    :deep(td),
    :deep(th) {
      color: var(--el-text-color-primary) !important;
    }

    :deep([style*="background"]) {
      background-color: transparent !important;
    }

    :deep(table) {
      th {
        background: var(--el-fill-color-lighter) !important;
      }
    }
  }
}

// 加载状态
.loading-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 80px 20px;
  color: var(--el-text-color-regular);

  p {
    margin-top: 16px;
    font-size: 14px;
  }
}

// 响应式设计
@media (max-width: 768px) {
  .main-content {
    min-height: calc(100vh - 160px); // 移动端头部较小，相应调整
    
    .card-box {
      min-height: calc(100vh - 200px); // 移动端卡片最小高度
    }
  }

  .notice-title-section {
    .notice-title {
      font-size: 24px;
    }
  }

  .notice-meta-section {
    flex-direction: column;
    gap: 12px;
    align-items: center;
  }

  .rich-text-content {
    font-size: 14px;
  }
}
</style>
