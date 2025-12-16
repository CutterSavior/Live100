<template>
  <div class="notice-dropdown">
    <el-tabs v-model="activeTab" class="notice-tabs">
      <el-tab-pane label="未读" name="unread">
        <el-divider style="margin: 0"/>
        <el-scrollbar max-height="300px">
          <div v-if="noticeStore.unreadNotices.length > 0" class="notice-list">
            <div
                v-for="notice in noticeStore.unreadNotices"
                :key="notice.id"
                class="notice-item is-unread"
                @click="handleNoticeClick(notice)"
            >
              <el-badge is-dot/>
              <span class="notice-title">{{ notice.title }}</span>
              <span class="notice-time">{{ formatRelativeTime(notice.publishTime) }}</span>
            </div>
          </div>

          <el-empty
              v-else
              description="暂无未读通知"
              :image-size="60"
          />
        </el-scrollbar>
      </el-tab-pane>

      <el-tab-pane label="全部" name="all">
        <el-divider style="margin: 0"/>
        <el-scrollbar max-height="300px">
          <div v-if="noticeStore.allNotices.length > 0" class="notice-list">
            <div
                v-for="notice in noticeStore.allNotices"
                :key="notice.id"
                class="notice-item"
                :class="{ 'is-unread': notice.readStatus === 0 }"
                @click="handleNoticeClick(notice)"
            >
              <el-badge
                  v-if="notice.readStatus === 0"
                  is-dot
              />
              <span class="notice-title">{{ notice.title }}</span>
              <span class="notice-time">{{ formatRelativeTime(notice.publishTime) }}</span>
            </div>
          </div>

          <el-empty
              v-else
              description="暂无通知"
              :image-size="60"
          />
        </el-scrollbar>
      </el-tab-pane>
    </el-tabs>

    <el-divider style="margin: 0"/>

    <div class="notice-footer">
      <el-button
          link
          type="primary"
          size="small"
          @click="handleViewAll"
      >
        查看全部
        <el-icon class="el-icon--right">
          <ArrowRight/>
        </el-icon>
      </el-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import {ref} from 'vue'
import {ArrowRight} from '@element-plus/icons-vue'
import {useRouter} from 'vue-router'
import {useNoticeStore} from '@/stores/notice.store'
import {formatRelativeTime} from '@/utils'
import type {Notice} from '@/types/notification/notice'

const router = useRouter()
const noticeStore = useNoticeStore()

// 本地状态
const activeTab = ref('unread')

const emit = defineEmits<{
  close: []
}>()

const props = defineProps<{
  visible?: boolean
}>()


/**
 * 点击通知项
 */
const handleNoticeClick = async (notice: Notice) => {
  // 关闭下拉菜单
  emit('close')

  // 跳转到详情页面，并标记来源
  router.push({
    path: `/admin/notice/detail/${notice.id}`,
    query: {from: 'dropdown'}
  })
}

/**
 * 查看全部通知
 */
const handleViewAll = () => {
  emit('close')
  router.push('/admin/notice')
}
</script>

<style scoped lang="scss">
.notice-dropdown {
  .notice-tabs {
    :deep(.el-tabs__header) {
      margin: 0;

      .el-tabs__nav-wrap::after {
        display: none;
      }
    }
  }

  .notice-list {
    .notice-item {
      display: flex;
      align-items: center;
      gap: 8px;
      padding: 8px 16px;
      cursor: pointer;

      .el-badge {
        display: flex;
      }

      &:hover {
        background-color: var(--el-fill-color-light);
      }

      .notice-title {
        flex: 1;
        font-size: 14px;
        color: var(--el-text-color-primary);
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
      }

      .notice-time {
        font-size: 12px;
        color: var(--el-text-color-regular);
        flex-shrink: 0;
      }
    }
  }

  .notice-footer {
    text-align: center;
    padding: 8px 16px;
  }

  .loading-container {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 8px;
    padding: 32px 16px;
    color: var(--el-text-color-regular);
    font-size: 14px;
  }
}
</style>
