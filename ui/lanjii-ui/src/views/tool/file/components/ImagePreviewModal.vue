<template>
  <el-dialog
    v-model="dialogVisible"
    title="图片预览"
    width="80%"
    :close-on-click-modal="false"
    :close-on-press-escape="true"
    append-to-body
    @close="handleClose"
  >
    <div class="image-preview-container">
      <div class="preview-toolbar">
        <el-button-group>
          <el-button :icon="ZoomIn" @click="handleZoomIn" title="放大">放大</el-button>
          <el-button :icon="ZoomOut" @click="handleZoomOut" title="缩小">缩小</el-button>
          <el-button :icon="RefreshLeft" @click="handleRotateLeft" title="左旋转">左旋转</el-button>
          <el-button :icon="RefreshRight" @click="handleRotateRight" title="右旋转">右旋转</el-button>
          <el-button :icon="FullScreen" @click="handleReset" title="重置">重置</el-button>
        </el-button-group>
        <span class="zoom-indicator">{{ zoomPercentage }}%</span>
      </div>
      
      <div class="preview-content" @wheel="handleWheel">
        <img
          ref="imageRef"
          :src="imageUrl"
          :style="imageStyle"
          @mousedown="handleMouseDown"
          @load="handleImageLoad"
          alt="预览图片"
        />
      </div>
      
      <div class="image-info">
        <span>文件名：{{ fileName }}</span>
        <span v-if="imageSize">尺寸：{{ imageSize }}</span>
      </div>
    </div>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref, computed, watch } from 'vue'
import { ZoomIn, ZoomOut, RefreshLeft, RefreshRight, FullScreen } from '@element-plus/icons-vue'

interface Props {
  visible: boolean
  imageUrl: string
  fileName?: string
}

const props = withDefaults(defineProps<Props>(), {
  fileName: '未知文件'
})

const emit = defineEmits<{
  close: []
}>()

const dialogVisible = ref(false)
const scale = ref(1)
const rotate = ref(0)
const translateX = ref(0)
const translateY = ref(0)
const isDragging = ref(false)
const dragStartX = ref(0)
const dragStartY = ref(0)
const imageRef = ref<HTMLImageElement>()
const imageSize = ref('')

const zoomPercentage = computed(() => Math.round(scale.value * 100))

const imageStyle = computed(() => ({
  transform: `scale(${scale.value}) rotate(${rotate.value}deg) translate(${translateX.value}px, ${translateY.value}px)`,
  cursor: isDragging.value ? 'grabbing' : 'grab',
  transition: isDragging.value ? 'none' : 'transform 0.3s ease',
}))

watch(() => props.visible, (val) => {
  dialogVisible.value = val
  if (val) {
    resetView()
  }
}, { immediate: true })

const handleZoomIn = () => {
  scale.value = Math.min(scale.value + 0.2, 5)
}

const handleZoomOut = () => {
  scale.value = Math.max(scale.value - 0.2, 0.2)
}

const handleRotateLeft = () => {
  rotate.value -= 90
}

const handleRotateRight = () => {
  rotate.value += 90
}

const handleReset = () => {
  scale.value = 1
  rotate.value = 0
  translateX.value = 0
  translateY.value = 0
}

const resetView = () => {
  handleReset()
  imageSize.value = ''
}

const handleWheel = (e: WheelEvent) => {
  e.preventDefault()
  if (e.deltaY < 0) {
    handleZoomIn()
  } else {
    handleZoomOut()
  }
}

const handleMouseDown = (e: MouseEvent) => {
  e.preventDefault()
  isDragging.value = true
  dragStartX.value = e.clientX - translateX.value
  dragStartY.value = e.clientY - translateY.value
  
  document.addEventListener('mousemove', handleMouseMove)
  document.addEventListener('mouseup', handleMouseUp)
}

const handleMouseMove = (e: MouseEvent) => {
  if (!isDragging.value) return
  translateX.value = e.clientX - dragStartX.value
  translateY.value = e.clientY - dragStartY.value
}

const handleMouseUp = () => {
  isDragging.value = false
  document.removeEventListener('mousemove', handleMouseMove)
  document.removeEventListener('mouseup', handleMouseUp)
}

const handleImageLoad = () => {
  if (imageRef.value) {
    const { naturalWidth, naturalHeight } = imageRef.value
    imageSize.value = `${naturalWidth} × ${naturalHeight}`
  }
}

const handleClose = () => {
  emit('close')
}
</script>

<style scoped lang="scss">
.image-preview-container {
  display: flex;
  flex-direction: column;
  height: 70vh;
  
  .preview-toolbar {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px;
    background-color: var(--el-fill-color-light);
    border-radius: 4px;
    margin-bottom: 10px;
    
    .zoom-indicator {
      font-size: 14px;
      font-weight: bold;
      color: var(--el-text-color-primary);
      margin-left: 10px;
    }
  }
  
  .preview-content {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: center;
    overflow: hidden;
    background-color: var(--el-fill-color-darker);
    border-radius: 4px;
    position: relative;
    user-select: none;
    
    img {
      max-width: 100%;
      max-height: 100%;
      object-fit: contain;
    }
  }
  
  .image-info {
    display: flex;
    justify-content: space-between;
    padding: 10px;
    margin-top: 10px;
    background-color: var(--el-fill-color-light);
    border-radius: 4px;
    font-size: 14px;
    color: var(--el-text-color-regular);
    
    span {
      &:not(:last-child) {
        margin-right: 20px;
      }
    }
  }
}
</style>
