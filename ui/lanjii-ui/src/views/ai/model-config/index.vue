<template>
  <div class="main-content">
    <AsyncTable
        ref="tableRef"
        :columns="columns"
        :search-items="searchItems"
        :fetch-data="fetchModelConfigs"
        :action-column-width="310"
    >
      <!-- 是否启用 -->
      <template #isEnabled="{ row }">
        <el-switch
            v-if="row.isEnabled!=undefined"
            :model-value="row.isEnabled"
            :active-value="1"
            :inactive-value="0"
            @change="handleToggleEnabled(row, $event)"
            v-permission="'ai:model-config:toggle'"
        />
      </template>

      <!-- 是否默认 -->
      <template #isDefault="{ row }">
        <el-tag :type="row.isDefault === 1 ? 'success' : 'info'">
          {{ row.isDefault === 1 ? '是' : '否' }}
        </el-tag>
      </template>
      <!-- 操作列 -->
      <template #action-column="{ row }">
        <el-button
            v-permission="'ai:model-config:view'"
            type="info"
            link
            :icon="View"
            @click="openModal('view', row)"
        >查看
        </el-button>
        <el-button
            v-permission="'ai:model-config:update'"
            type="warning"
            link
            :icon="Edit"
            @click="openModal('edit', row)"
        >编辑
        </el-button>
        <el-button
            v-if="row.isDefault !== 1"
            v-permission="'ai:model-config:default'"
            type="success"
            link
            :icon="Star"
            @click="handleSetDefault(row)"
        >设为默认
        </el-button>
        <el-button
            v-permission="'ai:model-config:delete'"
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
            v-permission="'ai:model-config:save'"
            type="primary"
            :icon="Plus"
            @click="openModal('add')"
        >新增
        </el-button>
      </template>
    </AsyncTable>

    <ModelConfigModal
        v-if="modalVisible"
        :visible="modalVisible"
        :type="modalType"
        :model-config="currentRow"
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
import type {AiModelConfig} from '@/types/ai/aiModelConfig'
import {
  deleteModelConfig,
  getModelConfigList,
  setModelDefault,
  toggleModelEnabled
} from '@/api/modules/ai/modelConfigApi'
import {Delete, Edit, Plus, Star, View} from '@element-plus/icons-vue'
import ModelConfigModal from './ModelConfigModal.vue'
import type {ResponseData} from '@/api/http'

const tableRef = ref()
const modalVisible = ref(false)
const modalType = ref<ModalType>('add')
const currentRow = ref<AiModelConfig | null>(null)

const columns: TableColumn[] = [
  {label: '序号', type: 'index', width: '70', align: 'center'},
  {prop: 'configName', label: '配置名称', minWidth: '180', align: 'left'},
  {prop: 'apiProvider', label: '模型提供商', minWidth: '120', align: 'center'},
  {prop: 'modelId', label: '模型名称', minWidth: '200', align: 'left'},
  {prop: 'isEnabled', label: '是否启用', minWidth: '100', align: 'center'},
  {prop: 'isDefault', label: '是否默认', minWidth: '100', align: 'center'},
  {prop: 'updateTime', label: '更新时间', minWidth: '160', align: 'center'},
  {prop: 'createBy', label: '创建人员', minWidth: '120', align: 'center'}
]

const searchItems: SearchItem[] = [
  {
    field: 'configName',
    label: '配置名称',
    type: 'input',
    placeholder: '请输入配置名称'
  },
  {
    field: 'apiProvider',
    label: '模型提供商',
    type: 'input',
    placeholder: '请输入模型提供商'
  }
]

const fetchModelConfigs = async (params: any) => {
  const res = (await getModelConfigList(params)) as ResponseData
  return res.data
}

function openModal(type: ModalType, row: AiModelConfig | null = null) {
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
}

const handleDelete = (row: AiModelConfig) => {
  ElMessageBox.confirm('确认删除该模型配置吗？', '提示', {
    type: 'warning'
  })
      .then(async () => {
        await deleteModelConfig(row.id as number)
        ElMessage.success('删除成功')
        tableRef.value?.refreshTable()
      })
      .catch(() => {
      })
}

const handleToggleEnabled = async (row: AiModelConfig, newValue: number) => {
  try {
    await toggleModelEnabled(row.id as number, newValue)
    ElMessage.success(newValue === 1 ? '已启用' : '已禁用')
    tableRef.value?.refreshTable()
  } catch (error: any) {
  }
}

const handleSetDefault = async (row: AiModelConfig) => {
  try {
    await setModelDefault(row.id as number)
    ElMessage.success('已设为默认配置')
    tableRef.value?.refreshTable()
  } catch (error) {
  }
}
</script>

<style scoped>
</style>
