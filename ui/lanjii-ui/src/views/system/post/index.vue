<template>
  <div class="main-content">
    <AsyncTable ref="asyncTableRef"
                :columns="allColumns"
                :search-items="searchItems"
                :fetch-data="fetchPosts">
      <template #isEnabled="{ row }">
        <DictTag dict-type="IS_ENABLED" :value="row.isEnabled" />
      </template>
      <template #toolbar>
        <el-button v-permission="'sys:post:save'" type="primary" :icon="Plus" @click="openModal('add')">新增</el-button>
      </template>
      <template #action-column="{row}">
        <el-button v-permission="'sys:post:view'" type="info" link :icon="View" @click="openModal('view', row)">查看
        </el-button>
        <el-button v-permission="'sys:post:update'" type="warning" link :icon="Edit" @click="openModal('edit', row)">
          编辑
        </el-button>
        <el-button v-permission="'sys:post:delete'" type="danger" link :icon="Delete" @click="handleDelete(row)">删除
        </el-button>
      </template>
    </AsyncTable>

    <PostModal
        v-if="modalVisible"
        :visible="modalVisible"
        :type="modalType"
        :postData="currentRow"
        @close="closeModal"
        @success="handleModalSuccess"
    />
  </div>
</template>

<script setup lang="ts">
import {ref} from 'vue'
import {ElMessage, ElMessageBox} from 'element-plus'
import AsyncTable from '@/components/AsyncTable/AsyncTable.vue'
import * as postApi from '@/api/modules/sys/postApi'
import {Delete, Edit, Plus, View} from '@element-plus/icons-vue'
import type {SearchItem} from '@/types/search.ts'
import type {TableColumn} from '@/types/table';
import type {ModalType} from '@/types/modal';
import {isEnabledOptions} from '@/constants'
import PostModal from './PostModal.vue'
import type {ResponseData} from '@/api/http.ts'

const asyncTableRef = ref();
const modalVisible = ref(false)
const modalType = ref<ModalType>('add')
const currentRow = ref<any>(null)

const allColumns: TableColumn[] = [
  {label: '序号', type: 'index', width: '70', align: 'center'},
  {prop: 'postCode', label: '岗位编码', minWidth: '120', align: 'center'},
  {prop: 'postName', label: '岗位名称', minWidth: '120', align: 'center'},
  {prop: 'sortOrder', label: '显示顺序', minWidth: '100', align: 'center'},
  {prop: 'isEnabled', label: '是否启用', minWidth: '100', align: 'center'},
  {prop: 'remark', label: '备注', minWidth: '120', align: 'center'},
  {prop: 'createTime', label: '创建时间', minWidth: '160', align: 'center'},
  {prop: 'createBy', label: '创建人', minWidth: '120', align: 'center'},
  {prop: 'updateTime', label: '更新时间', minWidth: '160', align: 'center'},
  {prop: 'updateBy', label: '更新人', minWidth: '120', align: 'center'}
]

const fetchPosts = async (params: any) => {
  const res = await postApi.getPostList(params) as ResponseData
  return res.data
}

const searchItems: SearchItem[] = [
  {field: 'postCode', label: '岗位编码', type: 'input', placeholder: '请输入岗位编码'},
  {field: 'postName', label: '岗位名称', type: 'input', placeholder: '请输入岗位名称'},
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

const handleDelete = (row: any) => {
  ElMessageBox.confirm('确认删除该岗位吗？', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await postApi.deletePost(row.id)
    ElMessage.success('删除成功')
    asyncTableRef.value?.refreshTable()
  }).catch(() => {
  })
}
</script>

<style scoped>
</style> 