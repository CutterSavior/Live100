<template>
  <div class="user-management main-content">
    <el-row :gutter="20" style="height: 100%;">
      <!-- 左侧部门树 -->
      <el-col :span="5" class="dept-tree-container">
        <el-card class="box-card" shadow="hover">
          <template #header>
            <div class="card-header">
              <div class="header-title">
                <el-icon>
                  <OfficeBuilding/>
                </el-icon>
                <span>部门列表</span>
                <div class="header-tools">
                  <el-tooltip content="刷新部门" placement="top">
                    <el-icon class="tool-icon" @click="loadDeptTree">
                      <Refresh/>
                    </el-icon>
                  </el-tooltip>
                  <el-tooltip v-if="!isDeptTreeExpanded" content="全部展开" placement="top">
                    <el-icon class="tool-icon" @click="expandAllDepts">
                      <ChevronDoubleDownIcon/>
                    </el-icon>
                  </el-tooltip>
                  <el-tooltip v-else content="全部折叠" placement="top">
                    <el-icon class="tool-icon" @click="collapseAllDepts">
                      <ChevronDoubleUpIcon/>
                    </el-icon>
                  </el-tooltip>
                </div>
              </div>
              <el-input
                  v-model="deptName"
                  placeholder="输入部门名称"
                  clearable
                  style="margin-top: 8px"
              >
                <template #prefix>
                  <el-icon>
                    <Search/>
                  </el-icon>
                </template>
              </el-input>
            </div>
          </template>
          <el-tree
              ref="deptTreeRef"
              :data="deptTreeData"
              :props="{ label: 'deptName', children: 'children' }"
              highlight-current
              node-key="id"
              :expand-on-click-node="false"
              @node-click="handleDeptNodeClick"
              :filter-node-method="filterDeptTree"
              default-expand-all
          >
            <template #default="{ node }">
              <div class="custom-tree-node">
                <div class="node-icon">
                  <el-icon v-if="node.expanded && node.childNodes?.length">
                    <FolderOpened/>
                  </el-icon>
                  <el-icon v-else>
                    <Folder/>
                  </el-icon>
                </div>
                <span class="node-label">{{ node.label }}</span>
              </div>
            </template>
          </el-tree>
        </el-card>
      </el-col>

      <!-- 右侧用户列表 -->
      <el-col :span="19" class="user-list-container">
        <el-scrollbar>
          <AsyncTable
              ref="userTableRef"
              :columns="allColumns"
              :search-items="searchItems"
              :fetch-data="fetchUsers"
          >
            <template #isEnabled="{ row }">
              <el-switch
                  v-model="row.isEnabled"
                  :active-value="true"
                  :inactive-value="false"
                  :disabled="row.id === userStore.userInfo.id||row.id===1"
                  @change="handleIsEnabledSwitchChange(row)"
              />
            </template>
            <template #toolbar>
              <el-button v-permission="'sys:user:save'" type="primary" :icon="Plus" @click="openModal('add')">新增
              </el-button>
              <el-button v-permission="'sys:user:export'" type="warning" :icon="Download" :loading="exportLoading"
                         @click="handleExport">导出
              </el-button>
            </template>
            <template #action-column="{row}">
              <el-button v-permission="'sys:user:view'" type="info" link :icon="View" @click="openModal('view', row)">查看
              </el-button>
              <el-button v-permission="'sys:user:update'" type="warning" link :icon="Edit"
                         @click="openModal('edit', row)">
                编辑
              </el-button>
              <MoreActions
                  :data="row"
                  :v-permission="['sys:user:reset-pwd', 'sys:user:delete']"
              >
                <template #default="{ data }">
                  <el-button
                      v-permission="'sys:user:reset-pwd'"
                      type="success"
                      link
                      :icon="Key"
                      @click="handleResetPwd(data)"
                  >
                    重置密码
                  </el-button>
                  <el-button
                      v-permission="'sys:user:delete'"
                      v-if="data.id !== 1"
                      type="danger"
                      link
                      :icon="Delete"
                      @click="handleDelete(data)"
                  >
                    删除
                  </el-button>
                </template>
              </MoreActions>

            </template>
          </AsyncTable>
        </el-scrollbar>

      </el-col>
    </el-row>

    <!-- 用户弹窗组件 -->
    <div v-loading="loadingUserDetail" style="min-height: 100px;">
      <UserModal
          v-if="modalVisible"
          :visible="modalVisible"
          :type="modalType"
          :userData="currentRow"
          :role-options="roleOptions"
          :dept-tree-options="deptTreeOptions"
          :post-options="postOptions"
          @close="closeModal"
          @success="handleModalSuccess"
      />
    </div>

  </div>
</template>

<script setup lang="ts">
import {onMounted, ref, watch} from 'vue'
import {ElMessage, ElMessageBox} from 'element-plus'
import AsyncTable from '@/components/AsyncTable/AsyncTable.vue'
import MoreActions from '@/components/MoreActions'
import * as api from '@/api/index'
import {useMultipleLoading} from '@/composables/useLoading.ts'
import {
  Delete,
  Download,
  Edit,
  Folder,
  FolderOpened,
  Key,
  OfficeBuilding,
  Plus,
  Refresh,
  Search,
  View
} from '@element-plus/icons-vue'
import {ChevronDoubleDownIcon, ChevronDoubleUpIcon} from '@heroicons/vue/24/solid'
import type {SearchItem} from '@/types/search'
import type {ModalType} from '@/types/modal'
import type {TableColumn} from '@/types/table'
import UserModal from './UserModal.vue'
import {isEnabledOptions} from '@/constants'
import {downloadFile, generateTimestampFilename} from '@/utils/download'
import {useUserStore} from '@/stores/user.store'
import type {ResponseData} from '@/api/http'
import type {SysDept} from "@/types/sys/sysDept.ts";

const userStore = useUserStore()

const {createLoading} = useMultipleLoading()
const {loading: exportLoading, withLoading: withExportLoading} = createLoading('export')

const deptTreeRef = ref()
const deptName = ref('')
const selectedDeptId = ref<number | null>(null)
const deptTreeData = ref<SysDept[]>([])
const userTableRef = ref()

const allColumns: TableColumn[] = [
  {label: '序号', type: 'index', width: '70', align: 'center'},
  {prop: 'username', label: '用户名', minWidth: '120', align: 'center'},
  {prop: 'nickname', label: '昵称', minWidth: '120', align: 'center'},
  {prop: 'deptName', label: '所属部门', minWidth: '120', align: 'center'},
  {prop: 'email', label: '邮箱', minWidth: '200', align: 'center'},
  {prop: 'phone', label: '手机号', minWidth: '120', align: 'center'},
  {prop: 'isEnabled', label: '是否启用', minWidth: '100', align: 'center'},
  {prop: 'lastLoginTime', label: '最后登录时间', minWidth: '160', align: 'center'},
  {prop: 'lastLoginIp', label: '最后登录IP', minWidth: '120', align: 'center'}
]

const fetchUsers = async (params: any) => {
  params.deptId = selectedDeptId.value
  const res = await api.getUserList(params) as any

  // 将isEnabled字段从数值转换为boolean，1转换为true，其他转换为false
  if (res.data && res.data.list) {
    res.data.list = res.data.list.map((user: any) => ({
      ...user,
      isEnabled: user.isEnabled === 1
    }))
  }

  return res.data
}

const searchItems: SearchItem[] = [
  {field: 'username', label: '用户名', type: 'input', placeholder: '请输入用户名'},
  {field: 'isEnabled', label: '是否启用', type: 'select', options: isEnabledOptions}
]

const modalVisible = ref(false)
const modalType = ref<ModalType>('add')
const currentRow = ref<any>(null)
const roleOptions = ref<any[]>([])
const deptTreeOptions = ref<any[]>([])
const postOptions = ref<any[]>([])
const loadingUserDetail = ref(false)

onMounted(() => {
  loadDeptTree()
})

const loadDeptTree = async () => {
  const res = await api.getDeptsTree({}) as ResponseData
  deptTreeData.value = res.data || []
  deptTreeRef.value?.filter(deptName.value)
}

const filterDeptTree = (value: string, data: SysDept) => {
  if (!value) return true
  return data.deptName?.includes(value) || false
}

// 监听部门名称输入，实时过滤（仅在树已加载时调用）
watch(deptName, (val) => {
  if (deptTreeRef.value) {
    deptTreeRef.value.filter(val)
  }
})

const handleDeptNodeClick = (data: any) => {
  selectedDeptId.value = data.id
  refreshList()
}

const openModal = async (type: ModalType, row: any = {}) => {
  modalType.value = type
  modalVisible.value = true
  currentRow.value = row

  if (type === 'add') {
    currentRow.value.deptId = selectedDeptId.value
  }

  loadingUserDetail.value = true
  try {
    const [rolesRes, deptsRes, postsRes] = await Promise.all([
      api.getAllRoles(),
      api.getDeptsTree({}),
      api.getAllPosts()
    ])
    roleOptions.value = (rolesRes as any).data || []
    deptTreeOptions.value = (deptsRes as any).data || []
    postOptions.value = (postsRes as any).data || []
  } finally {
    loadingUserDetail.value = false
  }
}

const handleDelete = (row: any) => {
  ElMessageBox.confirm(`确定要删除用户"${row.username}"吗？`, '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await api.deleteUser(row.id)
    ElMessage.success('删除成功')
    refreshList()
  })
}

const handleResetPwd = (row: any) => {
  ElMessageBox.confirm(`是否确认重置用户"${row.username}"的密码？`, '重置密码确认', {
    confirmButtonText: '确认重置',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await api.resetUserPassword(row.id)
    ElMessage.success(`用户"${row.username}"密码重置成功，默认密码为123456`)
  })
}

const handleIsEnabledSwitchChange = async (row: any) => {
  const newIsEnabled = row.isEnabled
  const isEnabledText = newIsEnabled ? '启用' : '禁用'
  const oldIsEnabled = !newIsEnabled

  try {
    row.isEnabled = oldIsEnabled
    await api.toggleUserStatus(row.id)
    row.isEnabled = newIsEnabled
    ElMessage.success(`用户"${row.username}"已${isEnabledText}`)
  } catch (error) {
    row.isEnabled = oldIsEnabled
  }
}

const closeModal = () => {
  modalVisible.value = false
  currentRow.value = null
}

const handleModalSuccess = () => {
  closeModal()
  refreshList()
  ElMessage.success(modalType.value === 'add' ? '新增成功' : '编辑成功')
}

const refreshList = () => {
  userTableRef.value?.refreshTable()
}

const handleExport = async () => {
  await withExportLoading(async () => {
    ElMessage.info('正在导出用户数据，请稍候...')

    const searchParams = userTableRef.value?.getSearchParams() || {}
    const params = {...searchParams}
    if (selectedDeptId.value) {
      params.deptId = selectedDeptId.value
    }

    const blob = await api.exportUsers(params) as Blob

    // 生成文件名
    const filename = generateTimestampFilename('用户数据', 'xlsx')

    // 下载文件
    downloadFile(blob, filename)

    ElMessage.success('用户数据导出成功')
  })
}

const isDeptTreeExpanded = ref(true)

const expandAllDepts = () => {
  expandHandle(true)
  isDeptTreeExpanded.value = true
}

const collapseAllDepts = () => {
  expandHandle(false)
  isDeptTreeExpanded.value = false
}

const expandHandle = function (isExpand: boolean) {
  let nodes = deptTreeRef.value.store.nodesMap;
  for (const node in nodes) {
    nodes[node].expanded = isExpand;
  }
}
</script>