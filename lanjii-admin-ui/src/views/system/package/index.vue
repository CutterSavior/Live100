<template>
  <div class="package-management main-content">
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
        <el-button v-permission="'tenant:package:add'" type="primary" :icon="Plus" @click="openModal('add')">
          新增
        </el-button>
      </template>
      <template #action-column="{ row }">
        <el-button v-permission="'tenant:package:edit'" type="warning" link :icon="Edit" @click="openModal('edit', row)">
          编辑
        </el-button>
        <el-button v-permission="'tenant:package:remove'" type="danger" link :icon="Delete" @click="handleDelete(row)">
          删除
        </el-button>
      </template>
    </AsyncTable>

    <!-- 套餐弹窗 -->
    <PackageModal
        v-if="modalVisible"
        :visible="modalVisible"
        :type="modalType"
        :data="currentRow"
        :menu-tree="menuTree"
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
import PackageModal from './PackageModal.vue'
import * as api from '@/api/index'
import type { SearchItem } from '@/types/search'
import type { TableColumn } from '@/types/table'
import type { ModalType } from '@/types/modal'
import type { SysTenantPackage } from '@/types/tenant/sysTenantPackage'
import type { SysMenu } from '@/types/sys/sysMenu'

const tableRef = ref()
const modalVisible = ref(false)
const modalType = ref<ModalType>('add')
const currentRow = ref<SysTenantPackage>({})
const menuTree = ref<SysMenu[]>([])

const columns: TableColumn[] = [
  { label: '序号', type: 'index', width: '70', align: 'center' },
  { prop: 'packageName', label: '套餐名称', minWidth: '150', align: 'center' },
  { prop: 'status', label: '状态', minWidth: '80', align: 'center' },
  { prop: 'remark', label: '备注', minWidth: '200', align: 'center' },
  { prop: 'createTime', label: '创建时间', minWidth: '160', align: 'center' }
]

const searchItems: SearchItem[] = [
  { field: 'packageName', label: '套餐名称', type: 'input', placeholder: '请输入套餐名称' },
  { field: 'status', label: '状态', type: 'select', clearable: true, options: 'STATUS' }
]

const fetchData = async (params: any) => {
  const res = await api.getPackageList(params) as any
  return { list: res.data || [], total: res.data?.length || 0 }
}

onMounted(async () => {
  await loadMenuTree()
})

const loadMenuTree = async () => {
  const res = await api.getMenuTree({ scopeNe: 2 }) as any
  menuTree.value = res.data || []
}

const openModal = async (type: ModalType, row: SysTenantPackage = {}) => {
  modalType.value = type
  if (type === 'edit' && row.id) {
    // 获取详情以获取 menuIdList
    const res = await api.getPackageById(row.id) as any
    currentRow.value = res.data || row
  } else {
    currentRow.value = { ...row }
  }
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

const handleDelete = (row: SysTenantPackage) => {
  ElMessageBox.confirm(`确定要删除套餐"${row.packageName}"吗？`, '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await api.deletePackage(row.id!)
    ElMessage.success('删除成功')
    tableRef.value?.refreshTable()
  })
}
</script>

<style scoped lang="scss">
.package-management {
  height: 100%;
}
</style>
