<template>
  <div class="main-content">
    <AsyncTable :columns="allColumns"
                :search-items="searchItems"
                :fetch-data="fetchOperLogs"
                :query-params="{}"
                :action-column-width="90">
      <template #businessType="{ row }">
        <el-tag :type="getBusinessTypeTagType(row.businessType)">
          {{ row.businessTypeLabel }}
        </el-tag>
      </template>
      <template #requestMethod="{ row }">
        <el-tag :type="getRequestMethodTagType(row.requestMethod)">
          {{ row.requestMethod }}
        </el-tag>
      </template>
      <template #status="{ row }">
        <el-tag :type="row.status === 1 ? 'success' : 'danger'">
          {{ row.statusLabel }}
        </el-tag>
      </template>
      <template #costTime="{ row }">
        <span :class="getCostTimeClass(row.costTime)">
          {{ row.costTime }}ms
        </span>
      </template>
      <template #toolbar>
        <el-button
            type="danger"
            :icon="Delete"
            @click="handleClean"
            v-permission="'sys:operlog:clean'">
          清空
        </el-button>
      </template>
      <template #action-column="{row}">
        <el-button
            type="info"
            link
            :icon="View"
            @click="openModal('view', row)"
            v-permission="'sys:operlog:view'">
          查看
        </el-button>
      </template>
    </AsyncTable>
    <OperLogModal
        v-if="modalVisible"
        :visible="modalVisible"
        :type="modalType"
        :operLogData="currentRow"
        @close="closeModal"
    />
  </div>
</template>

<script setup lang="ts">
import {ref} from 'vue'
import {ElMessage, ElMessageBox} from 'element-plus'
import AsyncTable from '@/components/AsyncTable/AsyncTable.vue'
import * as operLogApi from '@/api/modules/log/operLogApi'
import {Delete, View} from "@element-plus/icons-vue"
import type {SearchItem} from "@/types/search.ts"
import type {TableColumn} from '@/types/table';
import OperLogModal from './OperLogModal.vue'

const asyncTableRef = ref()
const modalVisible = ref(false)
const modalType = ref<'view'>('view')
const currentRow = ref<any>(null)

const allColumns: TableColumn[] = [
  {label: '序号', type: 'index', width: '70', align: 'center'},
  {prop: 'title', label: '操作模块', minWidth: '120', align: 'center'},
  {prop: 'businessType', label: '业务类型', minWidth: '100', align: 'center'},
  {prop: 'requestMethod', label: '请求方式', minWidth: '100', align: 'center'},
  {prop: 'method', label: '方法名称', minWidth: '200', align: 'center'},
  {prop: 'operUrl', label: '请求URL', minWidth: '200', align: 'center'},
  {prop: 'status', label: '操作状态', minWidth: '100', align: 'center'},
  {prop: 'costTime', label: '耗时', minWidth: '100', align: 'center'},
  {prop: 'operTime', label: '操作时间', minWidth: '160', align: 'center'},
  {prop: 'operName', label: '操作人员', minWidth: '120', align: 'center'},
  {prop: 'deptName', label: '部门名称', minWidth: '120', align: 'center'}
]

const fetchOperLogs = async (params: any) => {
  const res = await operLogApi.getOperLogList(params)
  return res.data
}

const searchItems: SearchItem[] = [
  {
    field: 'title',
    label: '操作模块',
    type: 'input',
    placeholder: '请输入操作模块'
  },
  {
    field: 'businessType',
    label: '业务类型',
    type: 'select',
    clearable: true,
    options: [
      {label: '新增', value: 0},
      {label: '修改', value: 1},
      {label: '删除', value: 2}
    ]
  },
  {
    field: 'method',
    label: '方法名称',
    type: 'input',
    placeholder: '请输入方法名称'
  },
  {
    field: 'requestMethod',
    label: '请求方式',
    type: 'select',
    clearable: true,
    options: [
      {label: 'GET', value: 'GET'},
      {label: 'POST', value: 'POST'},
      {label: 'PUT', value: 'PUT'},
      {label: 'DELETE', value: 'DELETE'}
    ]
  },
  {
    field: 'operName',
    label: '操作人员',
    type: 'input',
    placeholder: '请输入操作人员'
  },
  {
    field: 'deptName',
    label: '部门名称',
    type: 'input',
    placeholder: '请输入部门名称'
  },
  {
    field: 'operUrl',
    label: '请求URL',
    type: 'input',
    placeholder: '请输入请求URL'
  },
  {
    field: 'status',
    label: '操作状态',
    type: 'select',
    clearable: true,
    options: [
      {label: '失败', value: 0},
      {label: '成功', value: 1}
    ]
  }
]

// 获取业务类型标签类型
const getBusinessTypeTagType = (businessType: number) => {
  switch (businessType) {
    case 0:
      return 'success'  // 新增
    case 1:
      return 'warning'  // 修改
    case 2:
      return 'danger'   // 删除
    default:
      return 'info'
  }
}

// 获取请求方式标签类型
const getRequestMethodTagType = (requestMethod: string) => {
  switch (requestMethod) {
    case 'GET':
      return 'success'
    case 'POST':
      return 'primary'
    case 'PUT':
      return 'warning'
    case 'DELETE':
      return 'danger'
    default:
      return 'info'
  }
}

// 获取耗时样式类
const getCostTimeClass = (costTime: number) => {
  if (costTime > 1000) return 'cost-time-high'
  if (costTime > 500) return 'cost-time-medium'
  return 'cost-time-low'
}

function openModal(type: 'view', row: any = null) {
  modalType.value = type
  currentRow.value = row
  modalVisible.value = true
}

function closeModal() {
  modalVisible.value = false
  currentRow.value = null
}

// 清空操作日志
const handleClean = () => {
  ElMessageBox.confirm('确认清空15天前的操作日志数据吗？此操作不可恢复！', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await operLogApi.cleanOperLogs()
      ElMessage.success('清空成功')
      asyncTableRef.value?.refreshTable()
    } catch (e) {
      ElMessage.error('清空失败')
    }
  }).catch(() => {
  })
}
</script>

<style scoped>
.cost-time-low {
  color: #67c23a;
}

.cost-time-medium {
  color: #e6a23c;
}

.cost-time-high {
  color: #f56c6c;
}
</style>