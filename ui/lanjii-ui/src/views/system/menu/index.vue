<template>
  <div class="main-content">
    <AsyncTable :columns="allColumns"
                :search-items="searchItems"
                :fetch-data="fetchMenus"
                :show-pagination="false"
                :is-tree-table="true"
                :tree-config="treeConfig"
                ref="asyncTableRef">
      <template #icon="{ row }">
        <el-icon v-if="row.icon">
          <component :is="row.icon"/>
        </el-icon>
        <span v-else>-</span>
      </template>
      <template #isEnabled="{ row }">
        <el-tag :type="row.isEnabled === 1 ? 'success' : 'danger'">
          {{ row.isEnabledLabel }}
        </el-tag>
      </template>
      <template #isVisible="{ row }">
        <el-tag :type="row.isVisible === 1 ? 'success' : 'danger'">
          {{ row.isVisibleLabel }}
        </el-tag>
      </template>
      <template #toolbar>
        <el-button v-permission="'sys:menu:save'" type="primary" :icon="Plus" @click="openModal('add')">新增</el-button>
      </template>
      <template #action-column="{row}">
        <el-button v-permission="'sys:menu:save'" type="primary" link :icon="Plus" @click="openModal('add',row)">新增
        </el-button>
        <el-button v-permission="'sys:menu:view'" type="info" link :icon="View" @click="openModal('view', row)">查看
        </el-button>
        <MoreActions
            :data="row"
            :v-permission="['sys:menu:update', 'sys:menu:delete']"
        >
          <template #default="{ data }">
            <el-button v-permission="'sys:menu:update'" type="warning" link :icon="Edit"
                       @click="openModal('edit', data)">
              编辑
            </el-button>
            <el-button v-permission="'sys:menu:delete'" type="danger" link :icon="Delete" @click="handleDelete(data)">删除
            </el-button>
          </template>
        </MoreActions>
      </template>
    </AsyncTable>
    <MenuModal
        v-if="modalVisible"
        :visible="modalVisible"
        :menu-tree-options="menuTreeData"
        :type="modalType"
        :menuData="currentRow"
        @close="closeModal"
        @success="handleModalSuccess"
    />
  </div>
</template>

<script setup lang="ts">
import {ref} from 'vue'
import {ElMessage, ElMessageBox} from 'element-plus'
import AsyncTable from '@/components/AsyncTable/AsyncTable.vue'
import * as menuApi from '@/api/modules/monitor/menuApi'
import {Delete, Edit, Key, Plus, View} from '@element-plus/icons-vue'
import type {SearchItem} from '@/types/search.ts'
import type {TableColumn} from '@/types/table';
import type {ModalType} from '@/types/modal';
import MenuModal from './MenuModal.vue'
import {isEnabledOptions, menuTypeOptions} from '@/constants'
import type {ResponseData} from '@/api/http.ts'
import MoreActions from "@/components/MoreActions";

const asyncTableRef = ref();
const modalVisible = ref(false)
const modalType = ref<ModalType>('add')
const currentRow = ref<any>(null)
const menuTreeData = ref<any[]>([])

const allColumns: TableColumn[] = [
  {prop: 'name', label: '菜单名称', minWidth: 200, align: 'center'},
  {prop: 'icon', label: '图标', minWidth: 80, align: 'center'},
  {prop: 'path', label: '路由地址', minWidth: 180, align: 'center'},
  {prop: 'permission', label: '权限标识', minWidth: 180, align: 'center'},
  {prop: 'sortOrder', label: '排序', minWidth: 80, align: 'center'},
  {prop: 'isEnabled', label: '是否启用', minWidth: 100, align: 'center'},
  {prop: 'isVisible', label: '是否可见', minWidth: 100, align: 'center'},
  {prop: 'createTime', label: '创建时间', minWidth: 180, align: 'center'},
  {prop: 'updateTime', label: '更新时间', minWidth: 180, align: 'center'},
  {prop: 'createBy', label: '创建人', minWidth: 120, align: 'center'},
  {prop: 'updateBy', label: '更新人', minWidth: 120, align: 'center'}
]

const treeConfig = {
  rowKey: 'id',
  treeProps: {
    children: 'children',
    hasChildren: 'hasChildren'
  }
}

const fetchMenus = async (params: any) => {
  const res = await menuApi.getMenuTree(params) as ResponseData
  if (Array.isArray(res.data)) {
    menuTreeData.value = res.data
    return {list: res.data, total: res.data.length}
  }
  return res.data
}

const searchItems: SearchItem[] = [
  {field: 'name', label: '菜单名称', type: 'input', placeholder: '请输入菜单名称'},
  {field: 'path', label: '路由地址', type: 'input', placeholder: '请输入路由地址'},
  {field: 'permission', label: '权限标识', type: 'input', placeholder: '请输入权限标识'},
  {field: 'type', label: '菜单类型', type: 'select', clearable: true, options: menuTypeOptions},
  {field: 'isEnabled', label: '是否启用', type: 'select', clearable: true, options: isEnabledOptions}
]

function openModal(type: ModalType, row: any = null) {
  modalType.value = type
  if (type === 'add' && row) {
    currentRow.value = {parentId: row.id}
  } else {
    currentRow.value = row
  }
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
  ElMessageBox.confirm('确认删除该菜单及所有子菜单和按钮吗？', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await menuApi.deleteMenu(row.id)
    ElMessage.success('删除成功')
    asyncTableRef.value?.refreshTable()
  }).catch(() => {
  })
}
</script>

<style scoped>
/* 添加自定义样式 */
</style>