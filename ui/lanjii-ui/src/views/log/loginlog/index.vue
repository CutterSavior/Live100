<template>
  <div class="main-content">
    <AsyncTable :columns="allColumns"
                      :search-items="searchItems"
                      :fetch-data="fetchLoginLogs"
                      :query-params="{}"
                      :action-column-width="80">
      <template #loginType="{ row }">
        <el-tag :type="row.loginType === 0 ? 'primary' : 'info'">
          {{ row.loginTypeLabel }}
        </el-tag>
      </template>
      <template #status="{ row }">
        <el-tag :type="row.status === 1 ? 'success' : 'danger'">
          {{ row.statusLabel }}
        </el-tag>
      </template>
      <template #toolbar>
        <el-button 
          type="danger" 
          :icon="Delete" 
          @click="handleClean"
          v-permission="'sys:loginlog:clean'">
          清空
        </el-button>
      </template>
      <template #action-column="{row}">
        <el-button 
          type="info" 
          link 
          :icon="View" 
          @click="openModal('view', row)"
          v-permission="'sys:loginlog:view'">
          查看
        </el-button>
      </template>
    </AsyncTable>
    <LoginLogModal
      v-if="modalVisible"
      :visible="modalVisible"
      :type="modalType"
      :loginLogData="currentRow"
      @close="closeModal"
    />
  </div>
</template>

<script setup lang="ts">
import {ref} from 'vue'
import {ElMessage, ElMessageBox} from 'element-plus'
import AsyncTable from '@/components/AsyncTable/AsyncTable.vue'
import * as loginLogApi from '@/api/modules/log/loginLogApi'
import {Delete, View} from "@element-plus/icons-vue"
import type {SearchItem} from "@/types/search.ts"
import type {TableColumn} from '@/types/table';
import LoginLogModal from './LoginLogModal.vue'

const asyncTableRef = ref()
const modalVisible = ref(false)
const modalType = ref<'view'>('view')
const currentRow = ref<any>(null)

const allColumns: TableColumn[] = [
  {label: '序号', type: 'index', width: '70', align: 'center'},
  {prop: 'username', label: '用户名', minWidth: '120', align: 'center'},
  {prop: 'ipAddress', label: 'IP地址', minWidth: '120', align: 'center'},
  {prop: 'loginLocation', label: '登录地点', minWidth: '120', align: 'center'},
  {prop: 'browser', label: '浏览器', minWidth: '100', align: 'center'},
  {prop: 'os', label: '操作系统', minWidth: '120', align: 'center'},
  {prop: 'loginType', label: '登录类型', minWidth: '100', align: 'center'},
  {prop: 'status', label: '状态', minWidth: '100', align: 'center'},
  {prop: 'msg', label: '提示信息', minWidth: '120', align: 'center'},
  {prop: 'loginTime', label: '登录时间', minWidth: '160', align: 'center'},
  {prop: 'createTime', label: '创建时间', minWidth: '160', align: 'center'}
]

const fetchLoginLogs = async (params: any) => {
  const res = await loginLogApi.getLoginLogList(params)
  return res.data
}

const searchItems: SearchItem[] = [
  {
    field: 'username',
    label: '用户名',
    type: 'input',
    placeholder: '请输入用户名'
  },
  {
    field: 'ipAddress',
    label: 'IP地址',
    type: 'input',
    placeholder: '请输入IP地址'
  },
  {
    field: 'loginLocation',
    label: '登录地点',
    type: 'input',
    placeholder: '请输入登录地点'
  },
  {
    field: 'browser',
    label: '浏览器',
    type: 'input',
    placeholder: '请输入浏览器'
  },
  {
    field: 'os',
    label: '操作系统',
    type: 'input',
    placeholder: '请输入操作系统'
  },
  {
    field: 'loginType',
    label: '登录类型',
    type: 'select',
    clearable: true,
    options: [
      { label: '登录', value: 0 },
      { label: '登出', value: 1 }
    ]
  },
  {
    field: 'status',
    label: '状态',
    type: 'select',
    clearable: true,
    options: [
      { label: '失败', value: 0 },
      { label: '成功', value: 1 }
    ]
  }
]

function openModal(type: 'view', row: any = null) {
  modalType.value = type
  currentRow.value = row
  modalVisible.value = true
}

function closeModal() {
  modalVisible.value = false
  currentRow.value = null
}

// 清空登录日志
const handleClean = () => {
  ElMessageBox.confirm('确认清空15天前的登录日志数据吗？此操作不可恢复！', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await loginLogApi.cleanLoginLogs()
      ElMessage.success('清空成功')
      asyncTableRef.value?.refreshTable()
    } catch (e) {
      ElMessage.error('清空失败')
    }
  }).catch(() => {})
}
</script>

<style scoped>
</style>