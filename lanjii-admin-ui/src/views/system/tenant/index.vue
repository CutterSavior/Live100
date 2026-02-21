<template>
  <div class="tenant-management main-content">
    <AsyncTable
        ref="tableRef"
        :columns="columns"
        :search-items="searchItems"
        :fetch-data="fetchData"
    >
      <template #status="{ row }">
        <el-tag :type="row.status === 1 ? 'success' : 'danger'">
          {{ row.status === 1 ? '正常' : '停用' }}
        </el-tag>
      </template>
      <template #toolbar>
        <el-button v-permission="'tenant:add'" type="primary" :icon="Plus" @click="openModal('add')">
          新增
        </el-button>
      </template>
      <template #action-column="{ row }">
        <el-button v-permission="'tenant:edit'" type="warning" link :icon="Edit" @click="openModal('edit', row)">
          编辑
        </el-button>
        <el-button v-permission="'tenant:remove'" type="danger" link :icon="Delete" @click="handleDelete(row)">
          删除
        </el-button>
      </template>
    </AsyncTable>

    <!-- 租户弹窗 -->
    <TenantModal
        v-if="modalVisible"
        :visible="modalVisible"
        :type="modalType"
        :data="currentRow"
        :package-options="packageOptions"
        @close="closeModal"
        @success="handleSuccess"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Delete, Edit, Plus } from '@element-plus/icons-vue'
import AsyncTable from '@/components/AsyncTable/AsyncTable.vue'
import TenantModal from './TenantModal.vue'
import * as api from '@/api/index'
import type { SearchItem } from '@/types/search'
import type { TableColumn } from '@/types/table'
import type { ModalType } from '@/types/modal'
import type { SysTenant } from '@/types/tenant/sysTenant'
import type { SysTenantPackage } from '@/types/tenant/sysTenantPackage'

const tableRef = ref()
const modalVisible = ref(false)
const modalType = ref<ModalType>('add')
const currentRow = ref<SysTenant>({})
const packageOptions = ref<SysTenantPackage[]>([])

const columns: TableColumn[] = [
  { label: '序号', type: 'index', width: '70', align: 'center' },
  { prop: 'tenantCode', label: '租户编码', minWidth: '120', align: 'center' },
  { prop: 'tenantName', label: '租户名称', minWidth: '150', align: 'center' },
  { prop: 'packageName', label: '套餐', minWidth: '120', align: 'center' },
  { prop: 'contactName', label: '联系人', minWidth: '100', align: 'center' },
  { prop: 'contactPhone', label: '联系电话', minWidth: '130', align: 'center' },
  { prop: 'status', label: '状态', minWidth: '80', align: 'center' },
  { prop: 'expireTime', label: '过期时间', minWidth: '160', align: 'center' },
  { prop: 'createTime', label: '创建时间', minWidth: '160', align: 'center' }
]

const searchItems: SearchItem[] = [
  { field: 'tenantCode', label: '租户编码', type: 'input', placeholder: '请输入租户编码' },
  { field: 'tenantName', label: '租户名称', type: 'input', placeholder: '请输入租户名称' },
  { field: 'status', label: '状态', type: 'select', clearable: true, options: 'STATUS' }
]

const fetchData = async (params: any) => {
  const res = await api.getTenantList(params) as any
  return { list: res.data || [], total: res.data?.length || 0 }
}

onMounted(async () => {
  await loadPackageOptions()
})

const loadPackageOptions = async () => {
  const res = await api.getPackageList({}) as any
  packageOptions.value = res.data || []
}

const openModal = (type: ModalType, row: SysTenant = {}) => {
  modalType.value = type
  currentRow.value = { ...row }
  modalVisible.value = true
}

const closeModal = () => {
  modalVisible.value = false
  currentRow.value = {}
}

const handleSuccess = () => {
  closeModal()
  tableRef.value?.refreshTable()
  ElMessage.success(modalType.value === 'add' ? '新增成功' : '编辑成功')
}

const handleDelete = (row: SysTenant) => {
  ElMessageBox.confirm(`确定要删除租户"${row.tenantName}"吗？`, '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await api.deleteTenant(row.id!)
    ElMessage.success('删除成功')
    tableRef.value?.refreshTable()
  })
}
</script>

<style scoped lang="scss">
.tenant-management {
  height: 100%;
}
</style>
