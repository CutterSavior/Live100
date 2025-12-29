<template>
  <div class="main-content">
    <AsyncTable ref="asyncTableRef"
                :search-items="[]"
                :columns="allColumns"
                :fetch-data="fetchDepts"
                :show-pagination="false"
                :is-tree-table="true"
                :tree-config="treeConfig"
                default-expand-all
    >
      <template #isEnabled="{ row }">
        <el-tag :type="row.isEnabled === 1 ? 'success' : 'danger'">
          {{ row.isEnabledLabel}}
        </el-tag>
      </template>
      <template #toolbar>
        <el-button v-permission="'sys:dept:save'" type="primary" :icon="Plus" @click="openModal('add')">新增</el-button>
      </template>
      <template #action-column="{row}">
        <el-button v-permission="'sys:dept:view'" type="info" link :icon="View" @click="openModal('view', row)">查看</el-button>
        <el-button v-permission="'sys:dept:update'" type="warning" link :icon="Edit" @click="openModal('edit', row)">编辑</el-button>
        <el-button v-permission="'sys:dept:delete'" type="danger" link :icon="Delete" @click="handleDelete(row)">删除</el-button>
      </template>
    </AsyncTable>

    <DeptModal
        v-if="modalVisible"
        :visible="modalVisible"
        :dept-tree-options="deptTreeData"
        :type="modalType"
        :deptData="currentRow"
        @close="closeModal"
        @success="handleModalSuccess"
    />
  </div>
</template>

<script setup lang="ts">
import {ref} from 'vue'
import {ElMessage, ElMessageBox} from 'element-plus'
import AsyncTable from '@/components/AsyncTable/AsyncTable.vue'
import * as deptApi from '@/api/modules/sys/deptApi'
import {Delete, Edit, Plus, View} from '@element-plus/icons-vue'
import type {TableColumn} from '@/types/table';
import type {ModalType} from '@/types/modal';
import DeptModal from './DeptModal.vue'
import type {ResponseData} from '@/api/http.ts'

const asyncTableRef = ref();
const modalVisible = ref(false)
const modalType = ref<ModalType>('add')
const currentRow = ref<any>(null)
const deptTreeData = ref<any>([])

const allColumns: TableColumn[] = [
  {prop: 'deptName', label: '部门名称', minWidth: '200', align: 'left'},
  {prop: 'sortOrder', label: '显示顺序', minWidth: '100', align: 'center'},
  {prop: 'isEnabled', label: '是否启用', minWidth: '100', align: 'center'},
  {prop: 'leader', label: '负责人', minWidth: '120', align: 'center'},
  {prop: 'phone', label: '联系电话', minWidth: '140', align: 'center'},
  {prop: 'email', label: '邮箱', minWidth: '180', align: 'center'},
  {prop: 'remark', label: '备注', minWidth: '200', align: 'center'},
  {prop: 'createTime', label: '创建时间', minWidth: '160', align: 'center'},
  {prop: 'createBy', label: '创建人', minWidth: '120', align: 'center'},
  {prop: 'updateTime', label: '更新时间', minWidth: '160', align: 'center'},
  {prop: 'updateBy', label: '更新人', minWidth: '120', align: 'center'}
]

const treeConfig = {
  rowKey: 'id',
  treeProps: {
    children: 'children',
    hasChildren: 'hasChildren'
  }
}

const fetchDepts = async (params: any) => {
  const res = await deptApi.getDeptsTree(params) as ResponseData
  if (Array.isArray(res.data)) {
    deptTreeData.value = res.data
    return {list: res.data, total: res.data.length}
  }
  return res.data
}

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

const handleDelete = (row: any) => {
  ElMessageBox.confirm('确认删除该部门吗？', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await deptApi.deleteDept(row.id)
    ElMessage.success('删除成功')
    asyncTableRef.value?.refreshTable()
  }).catch(() => {
  })
}

</script>

<style scoped>
</style>