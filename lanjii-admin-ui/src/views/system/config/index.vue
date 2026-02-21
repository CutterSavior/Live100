<template>
  <div class="main-content">
    <AsyncTable :columns="allColumns"
                :search-items="searchItems"
                :fetch-data="fetchConfigs"
                ref="asyncTableRef"
                :query-params="{}">
      <template #isEnabled="{ row }">
        <DictTag dict-type="IS_ENABLED" :value="row.isEnabled"/>
      </template>
      <template #configType="{ row }">
        <DictTag dict-type="CONFIG_TYPE" :value="row.configType"/>
      </template>
      <template #toolbar>
        <el-button v-permission="'sys:config:save'" type="primary" :icon="Plus" @click="openModal('add')">新增
        </el-button>
        <el-button v-permission="'sys:config:cache'" type="warning" :icon="Refresh" @click="handleClearCache">清除缓存
        </el-button>
      </template>
      <template #action-column="{row}">
        <el-button v-permission="'sys:config:view'" type="info" link :icon="View" @click="openModal('view', row)">查看
        </el-button>
        <el-button v-permission="'sys:config:update'" type="warning" link :icon="Edit" @click="openModal('edit', row)">
          编辑
        </el-button>
        <el-button v-permission="'sys:config:delete'" type="danger" link :icon="Delete" @click="handleDelete(row)">
          删除
        </el-button>
      </template>
    </AsyncTable>
    <ConfigModal
        v-if="modalVisible"
        :visible="modalVisible"
        :type="modalType"
        :configData="currentRow"
        @close="closeModal"
        @success="handleModalSuccess"
    />
  </div>
</template>

<script setup lang="ts">
import {ref} from 'vue'
import {ElMessage, ElMessageBox} from 'element-plus'
import AsyncTable from '@/components/AsyncTable/AsyncTable.vue'
import * as configApi from '@/api/modules/sys/configApi.ts'
import {Delete, Edit, Plus, Refresh, View} from "@element-plus/icons-vue"
import type {SearchItem} from "@/types/search.ts"
import type {TableColumn} from '@/types/table';
import ConfigModal from './ConfigModal.vue'
import type {ResponseData} from "@/api/http.ts";
import type {ModalType} from "@/types/modal";

const asyncTableRef = ref()
const modalVisible = ref(false)
const modalType = ref<ModalType>('add')
const currentRow = ref<any>(null)

const allColumns: TableColumn[] = [
  {label: '序号', type: 'index', width: '70', align: 'center'},
  {prop: 'configName', label: '配置名称', minWidth: '120', align: 'center'},
  {prop: 'configKey', label: '配置键名', minWidth: '120', align: 'center'},
  {prop: 'configValue', label: '配置键值', minWidth: '120', align: 'center'},
  {prop: 'configType', label: '配置类型', minWidth: '100', align: 'center'},
  {prop: 'isEnabled', label: '是否启用', minWidth: '100', align: 'center'},
  {prop: 'remark', label: '备注', minWidth: '120', align: 'center'},
  {prop: 'createTime', label: '创建时间', minWidth: '160', align: 'center'},
  {prop: 'createBy', label: '创建人', minWidth: '120', align: 'center'},
  {prop: 'updateTime', label: '更新时间', minWidth: '160', align: 'center'},
  {prop: 'updateBy', label: '更新人', minWidth: '120', align: 'center'}
]

const fetchConfigs = async (params: any) => {
  const res = await configApi.getConfigList(params) as ResponseData
  return res.data;
}

const searchItems: SearchItem[] = [
  {field: 'configName', label: '配置名称', type: 'input', placeholder: '请输入配置名称'},
  {field: 'configKey', label: '配置键名', type: 'input', placeholder: '请输入配置键名'},
  {field: 'configValue', label: '配置键值', type: 'input', placeholder: '请输入配置键值'},
  {field: 'configType', label: '配置类型', type: 'select', clearable: true, options: 'CONFIG_TYPE'},
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
  ElMessageBox.confirm('确认删除该配置吗？', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await configApi.deleteConfig(row.id)
    ElMessage.success('删除成功')
    asyncTableRef.value?.refreshTable()
  }).catch(() => {
  })
}

const handleClearCache = async () => {
  await configApi.clearConfigCache()
  ElMessage.success('清除配置缓存成功')
}

</script>

<style scoped>
</style> 