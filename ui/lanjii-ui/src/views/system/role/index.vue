<template>
  <div class="main-content">
    <AsyncTable ref="asyncTableRef"
                :columns="allColumns"
                :search-items="searchItems"
                :fetch-data="fetchRoles">
      <template #isEnabled="{ row }">
        <DictTag dict-type="IS_ENABLED" :value="row.isEnabled" />
      </template>
      <template #toolbar>
        <el-button v-permission="'sys:role:save'" type="primary" :icon="Plus" @click="openModal('add')">新增</el-button>
      </template>
      <template #action-column="{row}">
        <el-button v-permission="'sys:role:view'" type="info" link :icon="View" @click="openModal('view', row)">查看
        </el-button>
        <el-button v-permission="'sys:role:update'" type="warning" link :icon="Edit" @click="openModal('edit', row)">
          编辑
        </el-button>
        <MoreActions
            :data="row"
            :v-permission="['sys:user:reset-pwd', 'sys:user:delete']"
        >
          <template #default="{data}">
            <el-button v-permission="'sys:role:permission'" type="primary" link :icon="Key"
                       @click="openPermissionModal(data)">
              权限分配
            </el-button>
            <el-button :disabled="data.id===1" v-permission="'sys:role:delete'" type="danger" link :icon="Delete"
                       @click="handleDelete(data)">删除
            </el-button>
          </template>
        </MoreActions>

      </template>
    </AsyncTable>

    <RoleModal
        v-if="modalVisible"
        :visible="modalVisible"
        :type="modalType"
        :roleData="currentRow"
        @close="closeModal"
        @success="handleModalSuccess"
    />

    <PermissionAssignModal
        v-if="permissionModalVisible"
        :visible="permissionModalVisible"
        :roleData="currentRow"
        @close="closePermissionModal"
        @success="handlePermissionSuccess"
    />
  </div>
</template>

<script setup lang="ts">
import {ref} from 'vue'
import {ElMessage, ElMessageBox} from 'element-plus'
import AsyncTable from '@/components/AsyncTable/AsyncTable.vue'
import * as roleApi from '@/api/modules/sys/roleApi'
import {Delete, Edit, Plus, View, Key} from '@element-plus/icons-vue'
import type {SearchItem} from '@/types/search.ts'
import type {TableColumn} from '@/types/table';
import type {ModalType} from '@/types/modal';
import {isEnabledOptions} from '@/constants'
import RoleModal from './RoleModal.vue'
import PermissionAssignModal from './PermissionAssignModal.vue'
import type {ResponseData} from '@/api/http.ts'
import MoreActions from "@/components/MoreActions/index.ts";

const allColumns: TableColumn[] = [
  {label: '序号', type: 'index', width: '70', align: 'center'},
  {prop: 'name', label: '角色名称', minWidth: '120', align: 'center'},
  {prop: 'code', label: '角色编码', minWidth: '120', align: 'center'},
  {prop: 'sortOrder', label: '显示顺序', minWidth: '100', align: 'center'},
  {prop: 'isEnabled', label: '是否启用', minWidth: '100', align: 'center'},
  {prop: 'remark', label: '备注', minWidth: '120', align: 'center'},
  {prop: 'createTime', label: '创建时间', minWidth: '160', align: 'center'},
  {prop: 'createBy', label: '创建人', minWidth: '120', align: 'center'},
  {prop: 'updateTime', label: '更新时间', minWidth: '160', align: 'center'},
  {prop: 'updateBy', label: '更新人', minWidth: '120', align: 'center'}
]

const modalVisible = ref(false)
const modalType = ref<ModalType>('add')
const currentRow = ref<any>(null)
const asyncTableRef = ref();
const permissionModalVisible = ref(false)

const fetchRoles = async (params: any) => {
  const res = await roleApi.getRoleList(params) as ResponseData;
  return res.data;
}

const searchItems: SearchItem[] = [
  {field: 'name', label: '角色名称', type: 'input', placeholder: '请输入角色名称'},
  {field: 'code', label: '角色编码', type: 'input', placeholder: '请输入角色编码'},
  {field: 'isEnabled', label: '是否启用', type: 'select', clearable: true, options: 'IS_ENABLED'}
]

function openModal(type: ModalType, row: any = null) {
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
  asyncTableRef.value?.refreshTable()
  ElMessage.success(modalType.value === 'add' ? '新增成功' : '编辑成功')
}

function openPermissionModal(row: any) {
  currentRow.value = row
  permissionModalVisible.value = true
}

function closePermissionModal() {
  permissionModalVisible.value = false
  currentRow.value = null
}

function handlePermissionSuccess() {
  closePermissionModal()
  ElMessage.success('权限分配成功')
}

const handleDelete = (row: any) => {
  ElMessageBox.confirm('确认删除该角色吗？', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await roleApi.deleteRole(row.id)
    ElMessage.success('删除成功')
    asyncTableRef.value?.refreshTable()
  }).catch(() => {
  })
}
</script>

<style scoped>
</style>