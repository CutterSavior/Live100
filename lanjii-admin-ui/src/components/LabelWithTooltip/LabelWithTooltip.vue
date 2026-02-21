<template>
  <span>
    <span>{{ label }}</span>
    <el-tooltip
      :content="tooltip"
      :placement="placement"
    >
      <el-icon class="help-icon">
        <QuestionFilled />
      </el-icon>
    </el-tooltip>
  </span>
</template>

<script setup lang="ts">
import { QuestionFilled } from '@element-plus/icons-vue'

defineProps({
  /** 标签文本 */
  label: {
    type: String,
    required: true
  },
  /** 提示内容 */
  tooltip: {
    type: String,
    required: true
  },
  /** 提示位置 */
  placement: {
    type: String,
    default: 'top'
  }
})
</script>

<style scoped lang="scss">
@use "index.scss";
</style>
