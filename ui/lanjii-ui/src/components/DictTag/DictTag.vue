<template>
  <el-tag
      v-if="dictItem"
      :type="tagType"
      :color="tagColor"
      :effect="tagEffect"
      v-bind="$attrs"
  >
    {{ dictItem.dictLabel }}
  </el-tag>
  <span v-else class="dict-tag-empty">{{ emptyText }}</span>
</template>

<script setup lang="ts">
import {computed} from 'vue'
import {useDictStore} from '@/stores/dict.store'

/**
 * 组件属性定义
 */
interface Props {
  /** 字典类型（必填） */
  dictType: string
  /** 字典值 */
  value?: string | number | null
  /** 字典项不存在时显示的文本 */
  emptyText?: string
}

const props = withDefaults(defineProps<Props>(), {
  value: undefined,
  emptyText: '-'
})

const dictStore = useDictStore()

const dictList = computed(() => {
  return dictStore.getDictByType(props.dictType)
})

// 从字典数组中查找匹配项
const dictItem = computed(() => {
  if (!props.value && props.value !== 0) {
    return null
  }

  return dictList.value?.find(
      (item) => String(item.dictValue) === String(props.value)
  ) ?? null
})

// 标签类型
const tagType = computed(() => {
  return dictItem.value?.tagType ?? 'default'
})

// 标签颜色
const tagColor = computed(() => {
  return dictItem.value?.tagColor
})

// 标签主题
const tagEffect = computed(() => {
  return dictItem.value?.tagEffect ?? 'light'
})

// 禁用属性自动继承
defineOptions({
  inheritAttrs: false
})
</script>

<style scoped>
.dict-tag-empty {
  color: var(--el-text-color-secondary);
  font-size: 14px;
}
</style>
