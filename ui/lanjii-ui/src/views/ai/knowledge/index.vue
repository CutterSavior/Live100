<template>
  <div class="main-content">
    <AsyncTable
        ref="knowledgeTableRef"
        :columns="allColumns"
        :search-items="searchItems"
        :fetch-data="fetchKnowledgeList"
        :action-column-width="240"
    >
      <template #toolbar>
        <el-button
            v-permission="'ai:knowledge:rebuild'"
            type="warning"
            plain
            :icon="Operation"
            @click="handleRebuildVectors"
        >重建向量</el-button>
        <el-button v-permission="'ai:knowledge:save'" type="primary" :icon="Plus" @click="openModal('add')">新增
        </el-button>
      </template>

      <!-- 标题链接 -->
      <template #title="{ row }">
        <el-link type="primary" @click="openModal('view', row)">{{ row.title }}</el-link>
      </template>

      <!-- 内容预览 -->
      <template #content="{ row }">
        <span>{{ row.content && row.content.length > 50 ? row.content.substring(0, 50) + '...' : row.content }}</span>
      </template>

      <!-- 操作按钮 -->
      <template #action-column="{ row }">
        <el-button
            type="info"
            link
            :icon="View"
            @click="openModal('view', row)"
            v-if="checkPermission('ai:knowledge:view')"
        >
          查看
        </el-button>
        <el-button
            type="primary"
            link
            :icon="Edit"
            @click="openModal('edit', row)"
            v-if="checkPermission('ai:knowledge:update')"
        >
          编辑
        </el-button>
        
        <MoreActions
            :data="row"
            :v-permission="['ai:knowledge:rebuild', 'ai:knowledge:delete']"
        >
          <template #default="{ data }">
            <el-button
                type="warning"
                link
                :icon="Operation"
                @click="handleRebuildVector(data)"
                v-if="checkPermission('ai:knowledge:rebuild')"
            >
              重建向量
            </el-button>
            <el-button
                type="danger"
                link
                :icon="Delete"
                @click="handleDelete(data)"
                v-if="checkPermission('ai:knowledge:delete')"
            >
              删除
            </el-button>
          </template>
        </MoreActions>
      </template>
    </AsyncTable>

    <KnowledgeModal
        v-if="modalVisible"
        :visible="modalVisible"
        :type="modalType"
        :knowledge="currentRow"
        @close="closeModal"
        @success="handleModalSuccess"
    />
  </div>
</template>

<script setup lang="ts">
import {ref} from 'vue'
import {ElMessage, ElMessageBox} from 'element-plus'
import AsyncTable from '@/components/AsyncTable/AsyncTable.vue'
import type {AiKnowledge} from '@/types/ai/aiKnowledge'
import type {TableColumn} from '@/types/table'
import type {SearchItem} from '@/types/search'
import {deleteKnowledge, getKnowledgeList, rebuildKnowledgeVectors, rebuildKnowledgeVectorById} from '@/api/modules/ai/knowledgeApi'
import {usePermission} from '@/utils/permission'
import {Delete, Edit, Operation, Plus, View} from "@element-plus/icons-vue"
import type {ModalType} from "@/types/modal.ts"
import MoreActions from '@/components/MoreActions'
import KnowledgeModal from './KnowledgeModal.vue'

const knowledgeTableRef = ref()
const modalVisible = ref(false)
const modalType = ref<ModalType>('add')
const currentRow = ref<AiKnowledge | null>(null)

// 权限检查
const {hasPermission} = usePermission()
const checkPermission = hasPermission

// 表格列配置
const allColumns: TableColumn[] = [
  {label: '序号', type: 'index', width: '70', align: 'center'},
  {prop: 'title', label: '标题', minWidth: '200', align: 'left'},
  {prop: 'content', label: '内容', minWidth: '300', align: 'left'},
  {prop: 'createBy', label: '创建人', minWidth: '120', align: 'center'},
  {prop: 'createTime', label: '创建时间', minWidth: '160', align: 'center'}
]

// 搜索配置
const searchItems: SearchItem[] = [
  {
    field: 'title',
    label: '标题',
    type: 'input',
    placeholder: '请输入标题'
  }
]

function openModal(type: ModalType, row: AiKnowledge | null = null) {
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
 * 获取知识库列表
 */
const fetchKnowledgeList = async (params: any) => {
  try {
    const {data} = await getKnowledgeList(params)
    return data
  } catch (error) {
    console.error('获取知识库列表失败:', error)
    throw error
  }
}


/**
 * 删除知识库
 */
const handleDelete = async (row: AiKnowledge) => {
  try {
    await ElMessageBox.confirm(`确定要删除知识库"${row.title}"吗？`, '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })

    await deleteKnowledge(row.id!)
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
  knowledgeTableRef.value?.refreshTable()
}

/**
 * 重建所有知识库向量
 */
const handleRebuildVectors = async () => {
  try {
    await ElMessageBox.confirm('确认要根据当前知识库内容与元数据，重新生成向量数据吗？', '重建向量', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })

    await rebuildKnowledgeVectors()
    ElMessage.success('知识库向量重建成功')
  } catch (error) {
    if (error !== 'cancel') {
      console.error('重建向量失败:', error)
    }
  }
}

/**
 * 重建单条知识库向量
 */
const handleRebuildVector = async (row: AiKnowledge) => {
  try {
    await ElMessageBox.confirm(`确认要重建知识库「${row.title}」的向量数据吗？`, '重建向量', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })

    await rebuildKnowledgeVectorById(row.id!)
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
