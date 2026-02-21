<template>
  <el-popover
    placement="bottom-end"
    :width="120"
    trigger="hover"
    :show-arrow="false"
  >
    <template #reference>
      <el-button type="primary" link :icon="More">{{ buttonText }}</el-button>
    </template>
    <div class="popover-actions">
      <slot :data="data"></slot>
    </div>
  </el-popover>
</template>

<script setup lang="ts">
import { More } from '@element-plus/icons-vue'

interface Props {
  buttonText?: string
  data?: any
}

withDefaults(defineProps<Props>(), {
  buttonText: '更多',
  data: undefined
})
</script>

<style scoped>
.popover-actions {
  display: flex;
  flex-direction: column;
  gap: 4px;
  align-items: flex-start;
}

.popover-actions :deep(.el-button) {
  margin-left: 0;
}
</style>
