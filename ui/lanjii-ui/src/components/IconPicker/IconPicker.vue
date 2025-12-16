<template>
  <el-dialog
    v-model="dialogVisible"
    title="选择图标"
    width="800px"
    append-to-body
    @close="handleClose"
  >
    <div style="margin-bottom: 20px;">
      <el-input
        v-model="searchText"
        placeholder="搜索图标名称"
        clearable
        style="width: 100%"
      >
        <template #prefix>
          <el-icon><Search /></el-icon>
        </template>
      </el-input>
    </div>
    <div class="icon-grid-container">
      <div 
        v-for="(icon, name) in filteredIcons" 
        :key="name" 
        class="icon-item"
        :class="{ 'icon-item-selected': selectedIcon === name }"
        @click="handleSelectIcon(name)"
      >
        <div class="icon-display">
          <el-icon class="icon">
            <component :is="icon" />
          </el-icon>
        </div>
        <div class="icon-name">{{ name }}</div>
      </div>
    </div>
    <template #footer>
      <el-button @click="handleClose">关闭</el-button>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { computed, ref, watch } from 'vue';
import { Search } from '@element-plus/icons-vue';
import * as ElementPlusIconsVue from '@element-plus/icons-vue';

const props = defineProps<{
  modelValue: boolean;
  selectedIcon?: string;
}>();

const emit = defineEmits<{
  'update:modelValue': [value: boolean];
  'select': [iconName: string];
}>();

const dialogVisible = ref(false);
const searchText = ref('');
const elementIcons = ref<Record<string, any>>({});

// 初始化图标
const initIcons = () => {
  for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    elementIcons.value[key] = component;
  }
};

initIcons();

// 同步外部 visible 状态
watch(() => props.modelValue, (newVal) => {
  dialogVisible.value = newVal;
  if (!newVal) {
    searchText.value = '';
  }
}, { immediate: true });

watch(dialogVisible, (newVal) => {
  emit('update:modelValue', newVal);
});

// 过滤图标
const filteredIcons = computed(() => {
  if (!searchText.value) {
    return elementIcons.value;
  }
  const searchLower = searchText.value.toLowerCase();
  const filtered: Record<string, any> = {};
  for (const [key, component] of Object.entries(elementIcons.value)) {
    if (key.toLowerCase().includes(searchLower)) {
      filtered[key] = component;
    }
  }
  return filtered;
});

// 选择图标
const handleSelectIcon = (iconName: string) => {
  emit('select', iconName);
  dialogVisible.value = false;
  searchText.value = '';
};

// 关闭对话框
const handleClose = () => {
  dialogVisible.value = false;
  searchText.value = '';
};
</script>

<style scoped lang="scss">
.icon-grid-container {
  max-height: 500px;
  overflow-y: auto;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(120px, 1fr));
  gap: 12px;
  padding: 10px 0;
}

.icon-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 16px 8px;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.2s;
  border: 1px solid var(--el-border-color-light);
  background-color: var(--el-bg-color);

  &:hover {
    background-color: var(--el-color-primary-light-9);
    transform: translateY(-2px);
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    border-color: var(--el-color-primary-light-7);
  }

  &.icon-item-selected {
    background-color: var(--el-color-primary-light-9);
    border-color: var(--el-color-primary);
    box-shadow: 0 0 0 2px var(--el-color-primary-light-8);
  }
}

.icon-display {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 50px;
  width: 50px;
  margin-bottom: 8px;
  color: var(--el-color-primary);

  .icon {
    width: 24px;
    height: 24px;
  }
}

.icon-name {
  font-size: 12px;
  text-align: center;
  word-break: break-all;
  color: var(--el-text-color-regular);
  max-width: 100%;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>

