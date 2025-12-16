<template>
  <div class="simple-rich-editor">
    <div class="editor-toolbar">
      <el-button-group>
        <el-button size="small" @click="execCommand('bold')" :class="{ active: isActive('bold') }">
          <strong>B</strong>
        </el-button>
        <el-button size="small" @click="execCommand('italic')" :class="{ active: isActive('italic') }">
          <em>I</em>
        </el-button>
        <el-button size="small" @click="execCommand('underline')" :class="{ active: isActive('underline') }">
          <u>U</u>
        </el-button>
      </el-button-group>
      
      <el-button-group>
        <el-button size="small" @click="execCommand('insertUnorderedList')">
          列表
        </el-button>
        <el-button size="small" @click="execCommand('insertOrderedList')">
          编号
        </el-button>
      </el-button-group>
      
      <el-button-group>
        <el-button size="small" @click="execCommand('justifyLeft')">
          左对齐
        </el-button>
        <el-button size="small" @click="execCommand('justifyCenter')">
          居中
        </el-button>
        <el-button size="small" @click="execCommand('justifyRight')">
          右对齐
        </el-button>
      </el-button-group>
      
      <el-select
        v-model="fontSize"
        size="small"
        placeholder="字号"
        style="width: 80px; margin-left: 8px;"
        @change="changeFontSize"
      >
        <el-option label="12px" value="1" />
        <el-option label="14px" value="2" />
        <el-option label="16px" value="3" />
        <el-option label="18px" value="4" />
        <el-option label="24px" value="5" />
        <el-option label="32px" value="6" />
      </el-select>
    </div>
    
    <div
      ref="editorRef"
      class="editor-content"
      contenteditable="true"
      @input="handleInput"
      @focus="handleFocus"
      @blur="handleBlur"
      :placeholder="placeholder"
    ></div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'

interface Props {
  modelValue?: string
  placeholder?: string
  height?: string
}

const props = withDefaults(defineProps<Props>(), {
  modelValue: '',
  placeholder: '请输入内容...',
  height: '300px'
})

const emit = defineEmits<{
  'update:modelValue': [value: string]
}>()

const editorRef = ref<HTMLDivElement>()
const fontSize = ref('3')
const isFocused = ref(false)

/**
 * 执行编辑器命令
 */
const execCommand = (command: string, value?: string) => {
  document.execCommand(command, false, value)
  editorRef.value?.focus()
  handleInput()
}

/**
 * 检查命令是否激活
 */
const isActive = (command: string): boolean => {
  return document.queryCommandState(command)
}

/**
 * 改变字体大小
 */
const changeFontSize = (size: string) => {
  execCommand('fontSize', size)
}

/**
 * 处理输入事件
 */
const handleInput = () => {
  const html = editorRef.value?.innerHTML || ''
  emit('update:modelValue', html)
}

/**
 * 处理焦点事件
 */
const handleFocus = () => {
  isFocused.value = true
}

/**
 * 处理失焦事件
 */
const handleBlur = () => {
  isFocused.value = false
}

/**
 * 设置编辑器内容
 */
const setContent = (content: string) => {
  if (editorRef.value) {
    editorRef.value.innerHTML = content
  }
}

/**
 * 获取编辑器内容
 */
const getContent = (): string => {
  return editorRef.value?.innerHTML || ''
}

/**
 * 清空编辑器
 */
const clear = () => {
  if (editorRef.value) {
    editorRef.value.innerHTML = ''
    emit('update:modelValue', '')
  }
}

// 监听外部值变化
watch(() => props.modelValue, (newValue) => {
  if (editorRef.value && editorRef.value.innerHTML !== newValue) {
    editorRef.value.innerHTML = newValue
  }
})

onMounted(() => {
  if (props.modelValue && editorRef.value) {
    editorRef.value.innerHTML = props.modelValue
  }
})

// 暴露方法给父组件
defineExpose({
  setContent,
  getContent,
  clear
})
</script>

<style scoped lang="scss">
.simple-rich-editor {
  border: 1px solid var(--el-border-color);
  border-radius: 4px;
  background-color: var(--el-bg-color);
  
  .editor-toolbar {
    padding: 8px;
    border-bottom: 1px solid var(--el-border-color);
    background-color: var(--el-fill-color-lighter);
    display: flex;
    align-items: center;
    gap: 8px;
    flex-wrap: wrap;
    
    .el-button-group {
      .el-button {
        &.active {
          background-color: var(--el-color-primary);
          color: white;
          border-color: var(--el-color-primary);
        }
      }
    }
  }
  
  .editor-content {
    min-height: v-bind(height);
    padding: 12px;
    outline: none;
    line-height: 1.6;
    color: var(--el-text-color-primary);
    
    &:empty::before {
      content: attr(placeholder);
      color: var(--el-text-color-placeholder);
      pointer-events: none;
    }
    
    // 编辑器内容样式
    :deep(p) {
      margin: 0 0 8px 0;
      
      &:last-child {
        margin-bottom: 0;
      }
    }
    
    :deep(ul), :deep(ol) {
      margin: 8px 0;
      padding-left: 20px;
    }
    
    :deep(li) {
      margin: 4px 0;
    }
    
    :deep(strong) {
      font-weight: bold;
    }
    
    :deep(em) {
      font-style: italic;
    }
    
    :deep(u) {
      text-decoration: underline;
    }
  }
}
</style>
