<template>
  <div class="file-icon">
    <!-- 图片类型 - 显示缩略图 -->
    <div v-if="isImageFile" 
         class="image-container" 
         :class="{'clickable': clickable}"
         @click.stop="handleClick">
      <el-image
        :src="fileUrl"
        fit="cover"
        class="file-thumbnail"
      >
        <template #error>
          <div class="image-error">
            <el-icon :size="iconSize"><Picture /></el-icon>
          </div>
        </template>
      </el-image>
    </div>

    <!-- 其他类型 - 显示图标 -->
    <div v-else class="file-icon-wrapper" :class="{'clickable': clickable}" @click="handleClick">
      <!-- 视频图标 -->
      <el-icon v-if="category === 'VIDEO'" :size="iconSize" :color="iconColor">
        <VideoCamera />
      </el-icon>
      
      <!-- 音频图标 -->
      <el-icon v-else-if="category === 'AUDIO'" :size="iconSize" :color="iconColor">
        <Headset />
      </el-icon>
      
      <!-- 文档图标 -->
      <el-icon v-else-if="category === 'DOCUMENT'" :size="iconSize" :color="iconColor">
        <Document />
      </el-icon>
      
      <!-- 压缩包图标 -->
      <el-icon v-else-if="category === 'ARCHIVE'" :size="iconSize" :color="iconColor">
        <FolderOpened />
      </el-icon>
      
      <!-- 文本图标 -->
      <el-icon v-else-if="category === 'TEXT'" :size="iconSize" :color="iconColor">
        <Tickets />
      </el-icon>
      
      <!-- 其他类型图标 -->
      <el-icon v-else :size="iconSize" :color="iconColor">
        <Files />
      </el-icon>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { Picture, VideoCamera, Headset, Document, FolderOpened, Tickets, Files } from '@element-plus/icons-vue'
import { getFileCategory, FileCategory } from '@/utils/fileType'

interface Props {
  fileExtension: string
  fileUrl?: string
  iconSize?: number
  clickable?: boolean
}

const props = withDefaults(defineProps<Props>(), {
  iconSize: 40,
  clickable: false
})

const emit = defineEmits<{
  click: []
}>()

// 获取文件分类
const category = computed(() => {
  return getFileCategory(props.fileExtension)
})

// 判断是否为图片
const isImageFile = computed(() => {
  return category.value === FileCategory.IMAGE
})

// 根据文件类型设置图标颜色
const iconColor = computed(() => {
  const colorMap: Record<string, string> = {
    [FileCategory.VIDEO]: '#409eff',
    [FileCategory.AUDIO]: '#67c23a',
    [FileCategory.DOCUMENT]: '#e6a23c',
    [FileCategory.ARCHIVE]: '#f56c6c',
    [FileCategory.TEXT]: '#909399',
    [FileCategory.OTHER]: '#909399'
  }
  return colorMap[category.value] || '#909399'
})

const handleClick = () => {
  if (props.clickable) {
    emit('click')
  }
}
</script>

<style scoped lang="scss">
.file-icon {
  display: flex;
  align-items: center;
  justify-content: center;
  
  .image-container {
    display: flex;
    align-items: center;
    justify-content: center;
    
    &.clickable {
      cursor: pointer;
      transition: transform 0.2s;
      
      &:hover {
        transform: scale(1.05);
      }
    }
  }
  
  .file-thumbnail {
    width: 60px;
    height: 60px;
    border-radius: 4px;
    display: block;
  }
  
  .image-error {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100%;
    height: 100%;
    background-color: var(--el-fill-color-light);
    color: var(--el-text-color-placeholder);
  }
  
  .file-icon-wrapper {
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 10px;
    
    &.clickable {
      cursor: pointer;
      transition: transform 0.2s;
      
      &:hover {
        transform: scale(1.1);
      }
    }
  }
}
</style>
