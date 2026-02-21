<template>
  <div class="wang-editor-container">
    <Toolbar
      :editor="editorRef"
      :defaultConfig="toolbarConfig"
      mode="default"
    />
    <Editor
      v-model="valueHtml"
      :defaultConfig="editorConfig"
      mode="default"
      :style="{ height: height }"
      @onCreated="handleCreated"
      @onChange="handleChange"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, onBeforeUnmount, watch } from 'vue'
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'

interface Props {
  modelValue?: string
  placeholder?: string
  height?: string
}

const props = withDefaults(defineProps<Props>(), {
  modelValue: '',
  placeholder: '请输入内容...',
  height: '400px'
})

const emit = defineEmits<{
  'update:modelValue': [value: string]
}>()

const editorRef = ref()
const valueHtml = ref(props.modelValue)

// 工具栏配置 - 精简版，避免过度设计
const toolbarConfig = {
  toolbarKeys: [
    'headerSelect',
    'bold',
    'italic',
    'underline',
    '|',
    'color',
    'bgColor',
    'fontSize',
    '|',
    'bulletedList',
    'numberedList',
    '|',
    'justifyLeft',
    'justifyCenter',
    'justifyRight',
    '|',
    'insertLink',
    'insertTable',
    '|',
    'undo',
    'redo'
  ]
}

// 编辑器配置
const editorConfig = {
  placeholder: props.placeholder,
  MENU_CONF: {
    // 字体大小配置
    fontSize: {
      fontSizeList: ['12px', '14px', '16px', '18px', '20px', '24px']
    },
    // 颜色配置
    color: {
      colors: ['#000000', '#333333', '#666666', '#999999', '#cccccc', '#ffffff', '#ff0000', '#00ff00', '#0000ff']
    }
  }
}

const handleCreated = (editor: any) => {
  editorRef.value = editor
}

const handleChange = (editor: any) => {
  const html = editor.getHtml()
  emit('update:modelValue', html)
}

// 监听外部值变化
watch(() => props.modelValue, (newValue) => {
  if (valueHtml.value !== newValue) {
    valueHtml.value = newValue
  }
})

onBeforeUnmount(() => {
  const editor = editorRef.value
  if (editor == null) return
  editor.destroy()
})
</script>

<style scoped lang="scss">
.wang-editor-container {
  border: 1px solid var(--el-border-color);
  border-radius: 4px;
  background-color: var(--el-bg-color);
  
  // 工具栏样式适配
  :deep(.w-e-toolbar) {
    background-color: var(--el-fill-color-lighter);
    border-bottom: 1px solid var(--el-border-color);
  }
  
  // 编辑器内容区域样式适配
  :deep(.w-e-text-container) {
    background-color: var(--el-bg-color);
  }
  
  :deep(.w-e-text-placeholder) {
    color: var(--el-text-color-placeholder);
  }
  
  :deep(.w-e-text) {
    color: var(--el-text-color-primary);
  }
}
</style>
