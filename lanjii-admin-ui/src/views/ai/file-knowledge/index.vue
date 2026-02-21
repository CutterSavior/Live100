<template>
  <div class="main-content">
    <AsyncTable
        ref="fileKnowledgeTableRef"
        :columns="allColumns"
        :search-items="searchItems"
        :fetch-data="fetchFileKnowledgeList"
        :action-column-width="240"
    >
      <template #toolbar>
        <el-button
            v-permission="'ai:file-knowledge:rebuild'"
            type="warning"
            plain
            :icon="Operation"
            @click="handleRebuildVectors"
        >重建向量</el-button>
        <el-button v-permission="'ai:file-knowledge:save'" type="primary" :icon="Plus" @click="openModal('add')">上传文件
        </el-button>
      </template>

      <!-- 文件名链接 -->
      <template #fileName="{ row }">
        <el-link type="primary" @click="openModal('view', row)">{{ row.fileName }}</el-link>
      </template>

      <!-- 文件类型标签 -->
      <template #fileType="{ row }">
        <el-tag :type="getFileTypeTag(row.fileType)">{{ row.fileType }}</el-tag>
      </template>

      <!-- 文件大小显示 -->
      <template #fileSize="{ row }">
        {{ row.fileSizeLabel}}
      </template>

      <!-- 操作按钮 -->
      <template #action-column="{ row }">
        <el-button
            type="info"
            link
            :icon="View"
            @click="openModal('view', row)"
            v-if="checkPermission('ai:file-knowledge:view')"
        >
          查看
        </el-button>
        <el-button
            type="primary"
            link
            :icon="Edit"
            @click="openModal('edit', row)"
            v-if="checkPermission('ai:file-knowledge:update')"
        >
          编辑
        </el-button>
        
        <MoreActions
            :data="row"
            :v-permission="['ai:file-knowledge:rebuild', 'ai:file-knowledge:delete']"
        >
          <template #default="{ data }">
            <el-button
                type="warning"
                link
                :icon="Operation"
                @click="handleRebuildVector(data)"
                v-if="checkPermission('ai:file-knowledge:rebuild')"
            >
              重建向量
            </el-button>
            <el-button
                type="danger"
                link
                :icon="Delete"
                @click="handleDelete(data)"
                v-if="checkPermission('ai:file-knowledge:delete')"
            >
              删除
            </el-button>
          </template>
        </MoreActions>
      </template>
    </AsyncTable>

    <FileKnowledgeModal
        v-if="modalVisible"
        :visible="modalVisible"
        :type="modalType"
        :file-knowledge="currentRow"
        @close="closeModal"
        @success="handleModalSuccess"
    />
  </div>
</template>

<script setup lang="ts">
import {ref} from 'vue'
import {ElMessage, ElMessageBox} from 'element-plus'
import AsyncTable from '@/components/AsyncTable/AsyncTable.vue'
import type {AiFileKnowledge} from '@/types/ai/aiFileKnowledge'
import type {TableColumn} from '@/types/table'
import type {SearchItem} from '@/types/search'
import {deleteFileKnowledge, getFileKnowledgeList, rebuildFileKnowledgeVectors, rebuildFileKnowledgeVectorById} from '@/api/modules/ai/fileKnowledgeApi'
import {usePermission} from '@/utils/permission'
import {Delete, Edit, Operation, Plus, View} from "@element-plus/icons-vue"
import type {ModalType} from "@/types/modal.ts"
import MoreActions from '@/components/MoreActions'
import FileKnowledgeModal from './FileKnowledgeModal.vue'

const fileKnowledgeTableRef = ref()
const modalVisible = ref(false)
const modalType = ref<ModalType>('add')
const currentRow = ref<AiFileKnowledge | null>(null)

// 权限检查
const {hasPermission} = usePermission()
const checkPermission = hasPermission

// 表格列配置
const allColumns: TableColumn[] = [
  {label: '序号', type: 'index', width: '70', align: 'center'},
  {prop: 'fileName', label: '文件名称', minWidth: '200', align: 'left'},
  {prop: 'fileType', label: '文件类型', minWidth: '100', align: 'center'},
  {prop: 'fileSize', label: '文件大小', minWidth: '120', align: 'center'},
  {prop: 'createBy', label: '创建人', minWidth: '120', align: 'center'},
  {prop: 'createTime', label: '创建时间', minWidth: '160', align: 'center'}
]

// 搜索配置
const searchItems: SearchItem[] = [
  {
    field: 'fileName',
    label: '文件名',
    type: 'input',
    placeholder: '请输入文件名'
  }
]

/**
 * 获取文件类型标签颜色
 */
const getFileTypeTag = (fileType: string) => {
  const typeMap: Record<string, string> = {
    'pdf': 'danger',
    'md': 'success',
    'markdown': 'success',
    'txt': 'info',
    'json': 'warning'
  }
  return typeMap[fileType?.toLowerCase()] || 'primary'
}

function openModal(type: ModalType, row: AiFileKnowledge | null = null) {
  modalType.value = type
  currentRow.value = row
  modalVisible.value = true
}

function closeModal() {
  modalVisible.value = false
  currentRow.value = null
}

function handleModalSuccess() {
  closeModal()
  refreshList()
}

/**
 * 获取文件知识库列表
 */
const fetchFileKnowledgeList = async (params: any) => {
  try {
    const {data} = await getFileKnowledgeList(params)
    return data
  } catch (error) {
    console.error('获取文件知识库列表失败:', error)
    throw error
  }
}

/**
 * 删除文件知识库
 */
const handleDelete = async (row: AiFileKnowledge) => {
  try {
    await ElMessageBox.confirm(`确定要删除文件"${row.fileName}"吗？`, '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })

    await deleteFileKnowledge(row.id!)
    ElMessage.success('删除成功')
    refreshList()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除失败:', error)
    }
  }
}

/**
 * 刷新列表
 */
const refreshList = () => {
  fileKnowledgeTableRef.value?.refreshTable()
}

/**
 * 重建所有文件知识库向量
 */
const handleRebuildVectors = async () => {
  try {
    await ElMessageBox.confirm('确认要根据当前文件知识库内容与元数据，重新生成向量数据吗？', '重建向量', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })

    await rebuildFileKnowledgeVectors()
    ElMessage.success('文件知识库向量重建成功')
  } catch (error) {
    if (error !== 'cancel') {
      console.error('重建向量失败:', error)
    }
  }
}

/**
 * 重建单条文件知识库向量
 */
const handleRebuildVector = async (row: AiFileKnowledge) => {
  try {
    await ElMessageBox.confirm(`确认要重建文件「${row.fileName}」的向量数据吗？`, '重建向量', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })

    await rebuildFileKnowledgeVectorById(row.id!)
    ElMessage.success('向量数据重建成功')
  } catch (error) {
    if (error !== 'cancel') {
      console.error('重建向量失败:', error)
    }
  }
}
</script>

<style scoped lang="scss">

</style>
