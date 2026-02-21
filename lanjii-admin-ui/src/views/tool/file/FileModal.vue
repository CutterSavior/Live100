<template>
  <el-dialog
      :model-value="visible"
      @update:model-value="$emit('close')"
      :title="modalTitle"
      width="800px"
      :close-on-click-modal="false"
      append-to-body
      destroy-on-close
  >
    <div class="file-detail">
      <el-descriptions :column="2" border>
        <el-descriptions-item label="文件名">
          {{ fileData.originalName }}
        </el-descriptions-item>
        <el-descriptions-item label="文件大小">
          {{ fileData.fileSizeLabel }}
        </el-descriptions-item>
        <el-descriptions-item label="文件类型">
          {{ fileData.fileTypeLabel }}
        </el-descriptions-item>
        <el-descriptions-item label="文件扩展名">
          {{ fileData.fileExtension }}
        </el-descriptions-item>
        <el-descriptions-item label="创建时间">
          {{ fileData.createTime }}
        </el-descriptions-item>
        <el-descriptions-item label="更新时间">
          {{ fileData.updateTime }}
        </el-descriptions-item>
      </el-descriptions>

      <!-- 文件预览区域 -->
      <div class="file-preview" v-if="showPreview">
        <h4>文件预览</h4>
        <div class="preview-content">
          <!-- 图片预览 -->
          <img v-if="isImage" :src="previewUrl" alt="文件预览" class="preview-image"/>
          <!-- 其他文件类型显示文件信息 -->
        <div v-else class="preview-info">
            <el-icon size="48" color="var(--el-color-primary)">
              <Document/>
            </el-icon>
            <p>{{ fileData.originalName }}</p>
            <p class="file-size">{{ fileData.fileSizeLabel }}</p>
          </div>
        </div>
      </div>
    </div>

    <template #footer>
      <div class="dialog-footer">
        <el-button type="primary" :icon="Download" @click="handleDownload">下载文件</el-button>
        <el-button @click="$emit('close')">关闭</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import {ref, computed, watch} from 'vue';
import {ElMessage} from 'element-plus';
import {Document, Download, View} from '@element-plus/icons-vue';
import * as fileApi from '@/api/modules/tool/fileApi';

const props = defineProps({
  visible: Boolean,
  type: {
    type: String,
    default: 'view'
  },
  fileData: {
    type: Object,
    default: () => ({})
  }
});

const emit = defineEmits(['close']);

const showPreview = ref(false);
const previewUrl = ref('');

// 计算属性
const modalTitle = computed(() => {
  return '文件详情';
});

const isImage = computed(() => {
  return props.fileData.fileType?.startsWith('image/');
});

// 下载文件
const handleDownload = async () => {
  try {
    const response = await fileApi.downloadFile(props.fileData.id);
    const blob = new Blob([response]);
    const url = window.URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = url;
    link.download = props.fileData.originalName;
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
    window.URL.revokeObjectURL(url);
    ElMessage.success('文件下载成功');
  } catch (e) {
    ElMessage.error('文件下载失败');
  }
};

// 监听文件数据变化，重置预览状态
watch(() => props.fileData, () => {
  showPreview.value = false;
  previewUrl.value = '';
}, {deep: true});
</script>

<style scoped>
.file-detail {
  margin-bottom: 20px;
}

.file-preview {
  margin-top: 20px;
  padding: 20px;
  border: 1px solid var(--el-border-color);
  border-radius: 4px;
  background-color: var(--el-fill-color-lighter);
}

.file-preview h4 {
  margin: 0 0 15px 0;
  color: var(--el-text-color-primary);
}

.preview-content {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 200px;
}

.preview-image {
  max-width: 100%;
  max-height: 300px;
  border-radius: 4px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.preview-info {
  text-align: center;
  color: var(--el-text-color-regular);
}

.preview-info p {
  margin: 10px 0;
}

.file-size {
  font-size: 12px;
  color: var(--el-text-color-secondary);
}

.dialog-footer {
  text-align: right;
}
</style>
