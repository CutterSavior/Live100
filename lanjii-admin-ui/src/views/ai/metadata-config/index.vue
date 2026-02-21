<template>
  <div class="main-content">
    <AsyncTable
        ref="tableRef"
        :columns="columns"
        :search-items="searchItems"
        :fetch-data="fetchMetadataFields"
        :action-column-width="260"
    >
      <!-- 是否必填 -->
      <template #isRequired="{ row }">
        <el-tag :type="row.isRequired === 1 ? 'success' : 'info'">
          {{ row.isRequired === 1 ? '是' : '否' }}
        </el-tag>
      </template>

      <!-- 操作列 -->
      <template #action-column="{ row }">
        <el-button
            v-permission="'ai:metadata-field:view'"
            type="info"
            link
            :icon="View"
            @click="openModal('view', row)"
        >查看
        </el-button>
        <el-button
            v-permission="'ai:metadata-field:update'"
            type="warning"
            link
            :icon="Edit"
            @click="openModal('edit', row)"
        >编辑
        </el-button>
        <el-button
            v-permission="'ai:metadata-field:delete'"
            type="danger"
            link
            :icon="Delete"
            @click="handleDelete(row)"
        >删除
        </el-button>
      </template>

      <!-- 工具栏 -->
      <template #toolbar>
        <el-button
            v-permission="'ai:metadata-field:save'"
            type="primary"
            :icon="Plus"
            @click="openModal('add')"
        >新增
        </el-button>
      </template>
    </AsyncTable>

    <MetadataFieldModal
        v-if="modalVisible"
        :visible="modalVisible"
        :type="modalType"
        :field-data="currentRow"
        @close="closeModal"
        @success="handleModalSuccess"
    />
  </div>
</template>

<script setup lang="ts">
import {ref} from 'vue'
import {ElMessage, ElMessageBox} from 'element-plus'
import AsyncTable from '@/components/AsyncTable/AsyncTable.vue'
import type {TableColumn} from '@/types/table'
import type {SearchItem} from '@/types/search'
import type {ModalType} from '@/types/modal'
import type {AiMetadataField} from '@/types/ai/aiMetadataField'
import {
  deleteMetadataField,
  getMetadataFieldPage
} from '@/api/modules/ai/metadataFieldApi'
import {Delete, Edit, Plus, View} from '@element-plus/icons-vue'
import MetadataFieldModal from './MetadataFieldModal.vue'
import type {ResponseData} from '@/api/http'

const tableRef = ref()
const modalVisible = ref(false)
const modalType = ref<ModalType>('add')
const currentRow = ref<AiMetadataField | null>(null)

const columns: TableColumn[] = [
  {label: '序号', type: 'index', width: '70', align: 'center'},
  {prop: 'fieldName', label: '字段名', minWidth: '160', align: 'left'},
  {prop: 'displayName', label: '显示名称', minWidth: '140', align: 'left'},
  {prop: 'dataType', label: '数据类型', minWidth: '100', align: 'center'},
  {prop: 'isRequired', label: '是否必填', minWidth: '100', align: 'center'},
  {prop: 'defaultValue', label: '默认值', minWidth: '140', align: 'left'},
  {prop: 'useCount', label: '使用次数', minWidth: '100', align: 'center'},
  {prop: 'createTime', label: '创建时间', minWidth: '160', align: 'center'},
  {prop: 'createBy', label: '创建人员', minWidth: '120', align: 'center'}
]

const searchItems: SearchItem[] = [
  {
    field: 'fieldName',
    label: '字段名',
    type: 'input',
    placeholder: '请输入字段名'
  },
  {
    field: 'displayName',
    label: '显示名称',
    type: 'input',
    placeholder: '请输入显示名称'
  }
]

const fetchMetadataFields = async (params: any) => {
  const res = (await getMetadataFieldPage(params)) as ResponseData
  return res.data
}

function openModal(type: ModalType, row: AiMetadataField | null = null) {
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
  tableRef.value?.refreshTable()
  ElMessage.success(modalType.value === 'add' ? '新增成功' : '编辑成功')
}

const handleDelete = (row: AiMetadataField) => {
  ElMessageBox.confirm('确认删除该元数据字段吗？', '提示', {
    type: 'warning'
  })
      .then(async () => {
        await deleteMetadataField(row.id as number)
        ElMessage.success('删除成功')
        tableRef.value?.refreshTable()
      })
      .catch(() => {
      })
}

</script>

<style scoped>
</style>
