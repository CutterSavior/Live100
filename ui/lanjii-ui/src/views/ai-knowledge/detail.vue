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
        <el-button type="primary" @click="fetchKnowledgeDetail">重试</el-button>
      </template>
    </el-result>

    <!-- 知识库详情内容 -->
    <div v-else class="card-box table-card">
      <!-- 知识库标题 - 居中显示 -->
      <div class="knowledge-title-section">
        <h1 class="knowledge-title">{{ knowledgeData.title }}</h1>
      </div>
      
      <!-- 知识库元信息 -->
      <div class="knowledge-meta-section">
        <div class="meta-item">
          <el-icon><User /></el-icon>
          <span>{{ knowledgeData.createBy || '系统' }}</span>
        </div>
        <div class="meta-item">
          <el-icon><Clock /></el-icon>
          <span>{{ knowledgeData.createTime }}</span>
        </div>
        <div class="meta-item" v-if="knowledgeData.updateTime && knowledgeData.updateTime !== knowledgeData.createTime">
          <el-icon><Edit /></el-icon>
          <span>{{ knowledgeData.updateTime }}</span>
        </div>
      </div>

      <!-- 分割线 -->
      <el-divider />

      <!-- 内容区域 -->
      <div class="content-area">
        <pre class="knowledge-content">{{ knowledgeData.content }}</pre>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import { useRoute } from 'vue-router'
import { Clock, Loading, User, Edit } from '@element-plus/icons-vue'
import { getKnowledgeById } from '@/api/modules/ai/knowledgeApi'
import type { AiKnowledge } from '@/types/ai/aiKnowledge'

const route = useRoute()

// 响应式数据
const loading = ref(true)
const error = ref('')
const knowledgeData = ref<AiKnowledge>({} as AiKnowledge)

/**
 * 获取知识库详情
 */
const fetchKnowledgeDetail = async () => {
  const knowledgeId = route.params.id as string
  if (!knowledgeId) {
    error.value = '知识库ID不存在'
    loading.value = false
    return
  }

  try {
    loading.value = true
    error.value = ''
    
    const { data } = await getKnowledgeById(Number(knowledgeId))
    knowledgeData.value = data
    
  } catch (err: any) {
    console.error('获取知识库详情失败:', err)
    error.value = err.message || '获取知识库详情失败'
  } finally {
    loading.value = false
  }
}

/**
 * 监听路由参数变化，当知识库ID改变时重新获取详情
 * immediate: true 确保组件初始化时也会调用
 */
watch(() => route.params.id, (newId) => {
  if (newId) {
    fetchKnowledgeDetail()
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
.knowledge-title-section {
  text-align: center;
  margin-bottom: 24px;

  .knowledge-title {
    font-size: 28px;
    font-weight: 600;
    color: var(--el-text-color-primary);
    margin: 0;
    line-height: 1.4;
  }
}

// 元信息区域
.knowledge-meta-section {
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

// 内容区域
.content-area {
  margin-top: 24px;
}

.knowledge-content {
  line-height: 1.8;
  color: var(--el-text-color-primary);
  font-size: 15px;
  white-space: pre-wrap;
  word-break: break-word;
  font-family: inherit;
  margin: 0;
  background: var(--el-fill-color-lighter);
  padding: 20px;
  border-radius: 8px;
  border: 1px solid var(--el-border-color-light);
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

  .knowledge-title-section {
    .knowledge-title {
      font-size: 24px;
    }
  }

  .knowledge-meta-section {
    flex-direction: column;
    gap: 12px;
    align-items: center;
  }

  .knowledge-content {
    font-size: 14px;
    padding: 16px;
  }
}
</style>
