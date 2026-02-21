<template>
  <div class="main-content">
    <AsyncTable
        ref="asyncTableRef"
        :columns="allColumns"
        :search-items="searchItems"
        :fetch-data="fetchDictTypes"
        :query-params="{}"
    >
      <template #isEnabled="{ row }">
        <DictTag dict-type="IS_ENABLED" :value="row.isEnabled" />
      </template>
      <template #typeCode="{ row }">
        <el-button type="primary" link @click="handleViewDictData(row)">{{ row.typeCode }}</el-button>
      </template>
      <template #toolbar>
        <el-button type="primary" :icon="Plus" @click="openModal('add')">新增</el-button>
      </template>
      <template #action-column="{row}">
        <el-button type="info" link :icon="View" @click="openModal('view', row)">查看</el-button>
        <el-button type="warning" link :icon="Edit" @click="openModal('edit', row)">编辑</el-button>
        <el-button type="danger" link :icon="Delete" @click="handleDelete(row)">删除</el-button>
      </template>
    </AsyncTable>

    <DictModal
        v-if="modalVisible"
        :visible="modalVisible"
        :type="modalType"
        :dictType="currentRow"
        @close="closeModal"
        @success="handleModalSuccess"
    />
  </div>
</template>

<script setup lang="ts">
import {ref} from 'vue'
import {ElMessage, ElMessageBox} from 'element-plus'
import AsyncTable from '@/components/AsyncTable/AsyncTable.vue'
import * as dictApi from '@/api/modules/sys/dictApi'
import {Delete, Edit, Plus, View} from "@element-plus/icons-vue"
import type {SearchItem} from "@/types/search.ts"
import {useRouter} from 'vue-router'
import DictModal from './DictModal.vue'
import type {TableColumn} from "@/types/table.ts";
import type {ResponseData} from "@/api/http.ts";
import type {ModalType} from "@/types/modal.ts";

const router = useRouter()
const asyncTableRef = ref()
const modalVisible = ref(false)
const modalType = ref<ModalType>('add')
const currentRow = ref<any>(null)

const allColumns: TableColumn[] = [
  {label: '序号', type: 'index', width: '70', align: 'center'},
  {prop: 'typeName', label: '类型名称', minWidth: '120', align: 'center'},
  {prop: 'typeCode', label: '类型编码', minWidth: '150', align: 'center'},
  {prop: 'isEnabled', label: '是否启用', minWidth: '100', align: 'center'},
  {prop: 'remark', label: '备注', minWidth: '100', align: 'center'},
  {prop: 'createTime', label: '创建时间', minWidth: '180', align: 'center'},
  {prop: 'createBy', label: '创建人', minWidth: '120', align: 'center'},
  {prop: 'updateTime', label: '更新时间', minWidth: '200', align: 'center'},
  {prop: 'updateBy', label: '更新人', minWidth: '200', align: 'center'}
]

const fetchDictTypes = async (params: any) => {
  const res = await dictApi.getDictTypeList(params) as ResponseData
  return res.data
}

const searchItems: SearchItem[] = [
  {field: 'typeCode', label: '类型编码', type: 'input', placeholder: '请输入类型编码'},
  {field: 'typeName', label: '类型名称', type: 'input', placeholder: '请输入类型名称'}
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
  ElMessageBox.confirm('确认删除该字典类型吗？', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await dictApi.deleteDictType(row.id)
    ElMessage.success('删除成功')
    asyncTableRef.value?.refreshTable()
  }).catch(() => {
  })
}

const handleViewDictData = (row: any) => {
  // 使用查询参数方式，这样所有字典数据都在同一个标签页中
  router.push({
    path: '/admin/system/dict/data',
    query: { typeCode: row.typeCode }
  })
}
</script>

<style scoped>
</style> 