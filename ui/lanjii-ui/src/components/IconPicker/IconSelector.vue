<template>
  <div class="icon-selector">
    <el-input 
      :model-value="modelValue" 
      @update:model-value="handleInputChange"
      @click="handleInputClick"
      @focus="handleInputClick"
      :disabled="disabled" 
      placeholder="请选择图标" 
      readonly
      style="width: 100%;"
      class="icon-input"
    >
      <template #prepend>
        <el-icon v-if="modelValue && iconComponent" class="icon-preview">
          <component :is="iconComponent" />
        </el-icon>
      </template>
      <template #append>
        <el-button :icon="Search" />
      </template>
    </el-input>
    <IconPicker
      v-model="showPicker"
      :selected-icon="modelValue"
      @select="handleIconSelect"
    />
  </div>
</template>

<script setup lang="ts">
import { computed, ref } from 'vue';
import * as ElementPlusIconsVue from '@element-plus/icons-vue';
import IconPicker from './IconPicker.vue';
import {Search} from "@element-plus/icons-vue";

const props = defineProps<{
  modelValue: string;
  disabled?: boolean;
}>();

const emit = defineEmits<{
  'update:modelValue': [value: string];
}>();

const showPicker = ref(false);
const elementIcons = ref<Record<string, any>>({});

// 初始化图标
const initIcons = () => {
  for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    elementIcons.value[key] = component;
  }
};

initIcons();

// 当前选中的图标组件
const iconComponent = computed(() => {
  if (props.modelValue && elementIcons.value[props.modelValue]) {
    return elementIcons.value[props.modelValue];
  }
  return null;
});

// 打开选择器
const handleInputClick = () => {
  if (!props.disabled) {
    showPicker.value = true;
  }
};

// 选择图标
const handleIconSelect = (iconName: string) => {
  emit('update:modelValue', iconName);
};

// 输入框变化（保留以支持直接输入）
const handleInputChange = (value: string) => {
  emit('update:modelValue', value);
};
</script>

<style scoped lang="scss">
.icon-selector {
  width: 100%;
}

.icon-input {
  cursor: pointer;
  
  :deep(.el-input__inner) {
    cursor: pointer;
  }
  
  &:hover {
    :deep(.el-input__wrapper) {
      box-shadow: 0 0 0 1px var(--el-input-hover-border-color) inset;
    }
  }
}

.icon-preview {
  font-size: 18px;
  color: var(--el-color-primary);
}
</style>

