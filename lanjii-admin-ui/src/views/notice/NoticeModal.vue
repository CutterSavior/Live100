<template>
  <el-dialog
    :model-value="visible"
    :title="noticeData.title"
    width="800px"
    @close="handleClose"
  >
    <el-descriptions :column="2" border>
      <el-descriptions-item label="发布人">
        {{ noticeData.publisherName }}
      </el-descriptions-item>
      <el-descriptions-item label="发布时间">
        {{ noticeData.publishTime }}
      </el-descriptions-item>
      <el-descriptions-item label="状态">
        <el-tag :type="noticeData.readStatus === 0 ? 'danger' : 'info'">
          {{ noticeData.readStatus === 0 ? '未读' : '已读' }}
        </el-tag>
      </el-descriptions-item>
      <el-descriptions-item v-if="noticeData.readTime" label="阅读时间">
        {{ noticeData.readTime }}
      </el-descriptions-item>
    </el-descriptions>
    
    <el-divider content-position="left">通知内容</el-divider>
    <div class="notice-content" v-html="noticeData.content"></div>
  </el-dialog>
</template>

<script setup lang="ts">
import type { Notice } from '@/types/notification/notice'

interface Props {
  visible: boolean
  type: 'view'
  noticeData: Notice
}

const props = defineProps<Props>()
const emit = defineEmits<{
  close: []
}>()

const handleClose = () => {
  emit('close')
}
</script>

<style scoped lang="scss">
.notice-content {
  padding: 16px 0;
  line-height: 1.8;
  color: var(--el-text-color-primary);
  
  :deep(img) {
    max-width: 100%;
    height: auto;
  }
  
  :deep(p) {
    margin: 8px 0;
  }
}

</style>
