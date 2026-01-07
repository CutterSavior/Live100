<template>
  <div class="main-content">
    <AsyncTable
        ref="tableRef"
        :columns="columns"
        :search-items="searchItems"
      :fetch-data="fetchRolePrompts"
      :action-column-width="250"
    >
      <!-- 是否启用 -->
      <template #isEnabled="{ row }">
        <el-switch
            v-if="row.isEnabled!=undefined"
            v-model="row.isEnabled"
            :active-value="1"
            :inactive-value="0"
            @change="handleToggle(row)"
            v-permission="'ai:role-prompt:toggle'"
        />
      </template>

      <!-- 提示词长度 -->
      <template #systemPromptLength="{ row }">
        <span>{{ (row.systemPrompt ? row.systemPrompt.length : 0) + ' 字' }}</span>
      </template>

      <!-- 操作列 -->
      <template #action-column="{ row }">
        <el-button
            v-permission="'ai:role-prompt:view'"
            type="info"
            link
            :icon="View"
            @click="openModal('view', row)"
        >查看
        </el-button>
        <el-button
            v-permission="'ai:role-prompt:update'"
            type="warning"
            link
            :icon="Edit"
            @click="openModal('edit', row)"
        >编辑
        </el-button>
        <el-button
            v-permission="'ai:role-prompt:delete'"
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
            v-permission="'ai:role-prompt:save'"
            type="primary"
            :icon="Plus"
            @click="openModal('add')"
        >新增
        </el-button>
      </template>
    </AsyncTable>

    <RolePromptModal
        v-if="modalVisible"
        :visible="modalVisible"
        :type="modalType"
        :role-prompt="currentRow"
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
import type {AiRolePrompt} from '@/types/ai/aiRolePrompt'
import {
  deleteRolePrompt,
  getRolePromptPage,
  toggleRolePrompt
} from '@/api/modules/ai/rolePromptApi'
import { Delete, Edit, Plus, View } from '@element-plus/icons-vue'
import RolePromptModal from './RolePromptModal.vue'
import type {ResponseData} from '@/api/http'
import {isEnabledOptions} from '@/constants'

const tableRef = ref()
const modalVisible = ref(false)
const modalType = ref<ModalType>('add')
const currentRow = ref<AiRolePrompt | null>(null)

const columns: TableColumn[] = [
  {label: '序号', type: 'index', width: '70', align: 'center'},
  {prop: 'roleName', label: '角色名称', minWidth: '180', align: 'left'},
  {prop: 'systemPromptLength', label: '提示词长度', minWidth: '120', align: 'center'},
  {prop: 'isEnabled', label: '是否启用', minWidth: '100', align: 'center'},
  {prop: 'createTime', label: '创建时间', minWidth: '160', align: 'center'},
  {prop: 'createBy', label: '创建人', minWidth: '120', align: 'center'}
]

const searchItems: SearchItem[] = [
  {
    field: 'roleName',
    label: '角色名称',
    type: 'input',
    placeholder: '请输入角色名称'
  },
  {
    field: 'isEnabled',
    label: '是否启用',
    type: 'select',
    clearable: true,
    options: isEnabledOptions
  }
]

const fetchRolePrompts = async (params: any) => {
  const res = (await getRolePromptPage(params)) as ResponseData
  return res.data
}

function openModal(type: ModalType, row: AiRolePrompt | null = null) {
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

const handleDelete = (row: AiRolePrompt) => {
  ElMessageBox.confirm('确认删除该角色与提示词配置吗？', '提示', {
    type: 'warning'
  })
      .then(async () => {
        await deleteRolePrompt(row.id as number)
        ElMessage.success('删除成功')
        tableRef.value?.refreshTable()
      })
      .catch(() => {
      })
}

const handleToggle = async (row: AiRolePrompt) => {
  try {
    await toggleRolePrompt(row.id as number)
    ElMessage.success(row.isEnabled === 1 ? '已启用' : '已停用')
  } catch (error) {
    // 切换失败，回滚状态
    row.isEnabled = row.isEnabled === 1 ? 0 : 1
  }
}

</script>

<style scoped>
</style>
