<template>
  <div class="main-content">
    <AsyncTable ref="asyncTableRef"
                :columns="columns"
                :search-items="searchItems"
                :fetch-data="fetchData">
      <template #isEnabled="{ row }">
        <el-tag :type="row.isEnabled === 1 ? 'success' : 'danger'">
          {{ row.isEnabledLabel }}
        </el-tag>
      </template>
      <template #toolbar>
        <el-button type="primary" :icon="Plus" @click="openModal('add')">新增</el-button>
      </template>
      <template #action-column="{ row }">
        <el-button type="info" link :icon="View" @click="openModal('view', row)">查看</el-button>
        <el-button type="warning" link :icon="Edit" @click="openModal('edit', row)">编辑</el-button>
        <el-button type="danger" link :icon="Delete" @click="handleDelete(row)">删除</el-button>
      </template>
    </AsyncTable>

    <DictDataModal
        v-if="modalVisible"
        :visible="modalVisible"
        :type="modalType"
        :dictData="currentRow"
        :dictType="dictType"
        @close="closeModal"
        @success="handleModalSuccess"
    />
  </div>
</template>

<script setup lang="ts">
import {useRoute} from 'vue-router'
import {ElMessage, ElMessageBox} from 'element-plus'
import AsyncTable from '@/components/AsyncTable/AsyncTable.vue'
import type {TableColumn} from '@/types/table.ts'
import type {SearchItem} from '@/types/search.ts'
import * as dictApi from '@/api/modules/sys/dictApi.ts'
import {Delete, Edit, Plus, View} from "@element-plus/icons-vue";
import {isEnabledOptions} from "@/constants";
import DictDataModal from './DictDataModal.vue'
import {ref, watch} from 'vue'
import type {ResponseData} from "@/api/http.ts";

const route = useRoute()
const asyncTableRef = ref()
const modalVisible = ref(false)
const modalType = ref<'add' | 'edit' | 'view'>('add')
const currentRow = ref<any>(null)
const dictType = ref('')

watch(() => route.query.typeCode, (newTypeCode) => {
  asyncTableRef.value?.refreshTable()
}, {immediate: true})

const searchItems: SearchItem[] = [
  {field: 'dictLabel', label: '字典标签', type: 'input', placeholder: '请输入字典标签'},
  {field: 'isEnabled', label: '是否启用', type: 'select', options: isEnabledOptions}
]

const columns: TableColumn[] = [
  {label: '序号', type: 'index', width: '70', align: 'center'},
  {prop: 'dictType', label: '类型编码', minWidth: 100, align: 'center'},
  {prop: 'dictValue', label: '字典键值', minWidth: 120},
  {prop: 'dictLabel', label: '字典标签', minWidth: 120},
  {prop: 'sortOrder', label: '排序', minWidth: 120},
  {prop: 'isEnabled', label: '是否启用', minWidth: 120},
  {prop: 'remark', label: '备注', minWidth: 100}
]

const fetchData = async (params: any) => {
  const typeCode = route.query.typeCode as string
  dictType.value = typeCode
  const res = await dictApi.getDictDataByTypeCode(typeCode, params) as ResponseData
  return res.data
}

function openModal(type: 'add' | 'edit' | 'view', row: any = null) {
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
  ElMessageBox.confirm('确认删除该字典数据吗？', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await dictApi.deleteDictData(row.id)
    ElMessage.success('删除成功')
    asyncTableRef.value?.refreshTable()
  }).catch(() => {
  })
}

</script>

<style scoped>
</style>