<template>
  <div class="main-content">
    <AsyncTable ref="asyncTableRef"
                :columns="allColumns"
                :search-items="searchItems"
                :fetch-data="fetchFiles"
                :query-params="{}"
                :selection="true"
                @selection-change="handleSelectionChange">
      <template #toolbar>
        <el-button type="primary" :icon="Upload" @click="openUploadModal">上传文件</el-button>
      </template>

      <!-- 文件预览列 -->
      <template #preview="{row}">
        <FileIcon
            :file-extension="row.fileExtension"
            :file-url="row.fullFilePath"
            :clickable="isImage(row.fileExtension)"
            @click="() => handlePreviewImage(row)"
        />
      </template>

      <template #action-column="{row}">
        <el-button type="info" link :icon="View" @click="openModal('view', row)">查看</el-button>
        <el-button type="success" link :icon="Download" @click="handleDownload(row)">下载</el-button>
      </template>
    </AsyncTable>

    <!-- 文件详情模态框 -->
    <FileModal
        v-if="modalVisible"
        :visible="modalVisible"
        :type="modalType"
        :fileData="currentRow"
        @close="closeModal"
    />

    <!-- 文件上传模态框 -->
    <FileUploadModal
        v-if="uploadModalVisible"
        :visible="uploadModalVisible"
        @close="closeUploadModal"
        @success="handleUploadSuccess"
    />

    <!-- 图片预览模态框 -->
    <ImagePreviewModal
        :visible="imagePreviewVisible"
        :image-url="currentImageUrl"
        :file-name="currentImageName"
        @close="closeImagePreview"
    />
  </div>
</template>

<script setup lang="ts">
import {ref} from 'vue'
import {ElMessage} from 'element-plus'
import AsyncTable from '@/components/AsyncTable/AsyncTable.vue'
import * as fileApi from '@/api/modules/tool/fileApi'
import {Download, Upload, View} from "@element-plus/icons-vue"
import type {SearchItem} from "@/types/search.ts"
import type {TableColumn} from '@/types/table';
import FileModal from './FileModal.vue'
import FileUploadModal from './FileUploadModal.vue'
import FileIcon from './components/FileIcon.vue'
import ImagePreviewModal from './components/ImagePreviewModal.vue'
import {isImage} from '@/utils/fileType'

const asyncTableRef = ref()
const modalVisible = ref(false)
const uploadModalVisible = ref(false)
const modalType = ref<'view'>('view')
const currentRow = ref<any>(null)
const selectedRows = ref<any[]>([])
const imagePreviewVisible = ref(false)
const currentImageUrl = ref('')
const currentImageName = ref('')

const allColumns: TableColumn[] = [
  {label: '序号', type: 'index', width: '70', align: 'center'},
  {prop: 'preview', label: '预览', width: '100', align: 'center'},
  {prop: 'originalName', label: '文件名', minWidth: '200', align: 'center'},
  {prop: 'fileSizeLabel', label: '文件大小', minWidth: '120', align: 'center'},
  {prop: 'fileTypeLabel', label: '文件类型', minWidth: '120', align: 'center'},
  {prop: 'fileExtension', label: '扩展名', minWidth: '100', align: 'center'},
  {prop: 'createTime', label: '创建时间', minWidth: '180', align: 'center'},
  {prop: 'updateTime', label: '更新时间', minWidth: '180', align: 'center'}
]

const fetchFiles = async (params: any) => {
  const res = await fileApi.getFileList(params) as any
  return res.data
}

const searchItems: SearchItem[] = [
  {
    field: 'originalName',
    label: '文件名',
    type: 'input',
    placeholder: '请输入文件名'
  },
  {
    field: 'fileType',
    label: '文件类型',
    type: 'select',
    clearable: true,
    options: [
      {label: '图片', value: 'image'},
      {label: '文档', value: 'application'},
      {label: '视频', value: 'video'},
      {label: '音频', value: 'audio'},
      {label: '其他', value: 'other'}
    ]
  }
]

const handleSelectionChange = (selection: any[]) => {
  selectedRows.value = selection
}

function openModal(type: 'view', row: any = null) {
  modalType.value = type
  currentRow.value = row
  modalVisible.value = true
}

function closeModal() {
  modalVisible.value = false
  currentRow.value = null
}

function openUploadModal() {
  uploadModalVisible.value = true
}

function closeUploadModal() {
  uploadModalVisible.value = false
}

function handleUploadSuccess() {
  closeUploadModal()
  asyncTableRef.value?.refreshTable()
  ElMessage.success('文件上传成功')
}

const handlePreviewImage = (row: any) => {
  if (isImage(row.fileExtension)) {
    currentImageUrl.value = row.fullFilePath
    currentImageName.value = row.originalName
    imagePreviewVisible.value = true
  }
}

const closeImagePreview = () => {
  imagePreviewVisible.value = false
  currentImageUrl.value = ''
  currentImageName.value = ''
}

const handleDownload = async (row: any) => {
  try {
    const response = await fileApi.downloadFile(row.id) as Blob
    const blob = new Blob([response])
    const url = window.URL.createObjectURL(blob)
    const link = document.createElement('a')
    link.href = url
    link.download = row.originalName
    document.body.appendChild(link)
    link.click()
    document.body.removeChild(link)
    window.URL.revokeObjectURL(url)
    ElMessage.success('文件下载成功')
  } catch (e) {
    ElMessage.error('文件下载失败')
  }
}

</script>

<style scoped>
</style>
