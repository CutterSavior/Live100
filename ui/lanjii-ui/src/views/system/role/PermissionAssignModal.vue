<template>
  <el-dialog
      :model-value="visible"
      @close="handleClose"
      title="分配权限"
      width="600px"
      :close-on-click-modal="false"
      append-to-body
      destroy-on-close
  >
    <el-form v-loading="loading" label-width="100px">
      <el-form-item label="角色名称">
        <el-input :model-value="roleData?.name" disabled/>
      </el-form-item>
      <el-form-item label="菜单权限">
        <div class="tree-container">
          <div class="tree-toolbar">
            <el-checkbox v-model="checkAll" :indeterminate="isIndeterminate" @change="handleCheckAllChange">
              全选/全不选
            </el-checkbox>
            <el-button link type="primary" @click="expandAll">展开全部</el-button>
            <el-button link type="primary" @click="collapseAll">折叠全部</el-button>
          </div>
          <el-tree
              ref="treeRef"
              :data="menuTree"
              :props="treeProps"
              :default-expand-all="false"
              :check-strictly="false"
              show-checkbox
              node-key="id"
              :default-checked-keys="checkedKeys"
              @check="handleTreeCheck"
          >
            <template #default="{ node, data }">
              <span class="custom-tree-node">
               <span>{{ node.label }}</span>
                <DictTag dict-type="MENU_TYPE" size="small" :value="data.type"/>
              </span>
            </template>
          </el-tree>
        </div>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="handleClose">取 消</el-button>
      <el-button type="primary" @click="handleSubmit" :loading="submitting">确 定</el-button>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import {nextTick, onMounted, ref} from 'vue'
import {ElMessage} from 'element-plus'
import * as menuApi from '@/api/modules/sys/menuApi'
import * as roleApi from '@/api/modules/sys/roleApi'
import type {SysMenu} from '@/types/sys/sysMenu'
import type {ResponseData} from '@/api/http'
import DictTag from "@/components/DictTag";

const props = defineProps({
  visible: Boolean,
  roleData: {
    type: Object,
    default: () => ({})
  }
})

const emit = defineEmits(['close', 'success'])

const loading = ref(false)
const submitting = ref(false)
const menuTree = ref<SysMenu[]>([])
const checkedKeys = ref<number[]>([])
const treeRef = ref()
const checkAll = ref(false)
const isIndeterminate = ref(false)

const treeProps = {
  children: 'children',
  label: 'name'
}

// 加载菜单树
const loadMenuTree = async () => {
  try {
    loading.value = true
    const res = await menuApi.getMenuTree({} as SysMenu) as ResponseData
    menuTree.value = res.data || []
  } catch (error) {
    ElMessage.error('加载菜单树失败')
  } finally {
    loading.value = false
  }
}

// 加载角色已分配的菜单
const loadRoleMenus = async () => {
  if (!props.roleData?.id) return

  try {
    loading.value = true
    // 调用获取角色菜单的API
    const res = await roleApi.getRoleMenuIds(props.roleData.id) as ResponseData
    checkedKeys.value = res.data || []

    // 等待树渲染完成后设置选中状态
    await nextTick()
    if (treeRef.value) {
      treeRef.value.setCheckedKeys(checkedKeys.value)
      updateCheckAllStatus()
    }
  } catch (error) {
    console.error('加载角色菜单失败:', error)
    checkedKeys.value = []
  } finally {
    loading.value = false
  }
}

// 获取所有菜单ID（用于全选）
const getAllMenuIds = (menus: SysMenu[]): number[] => {
  let ids: number[] = []
  menus.forEach(menu => {
    if (menu.id) {
      ids.push(menu.id)
    }
    if (menu.children && menu.children.length > 0) {
      ids = ids.concat(getAllMenuIds(menu.children))
    }
  })
  return ids
}

// 更新全选状态
const updateCheckAllStatus = () => {
  if (!treeRef.value) return

  const checkedNodes = treeRef.value.getCheckedKeys()
  const allMenuIds = getAllMenuIds(menuTree.value)

  if (checkedNodes.length === 0) {
    checkAll.value = false
    isIndeterminate.value = false
  } else if (checkedNodes.length === allMenuIds.length) {
    checkAll.value = true
    isIndeterminate.value = false
  } else {
    checkAll.value = false
    isIndeterminate.value = true
  }
}

// 全选/全不选
const handleCheckAllChange = (val: boolean) => {
  if (!treeRef.value) return

  if (val) {
    const allMenuIds = getAllMenuIds(menuTree.value)
    treeRef.value.setCheckedKeys(allMenuIds)
  } else {
    treeRef.value.setCheckedKeys([])
  }
  isIndeterminate.value = false
}

// 树节点选中变化
const handleTreeCheck = () => {
  updateCheckAllStatus()
}

// 展开全部
const expandAll = () => {
  if (!treeRef.value) return
  const nodes = treeRef.value.store.nodesMap
  for (let key in nodes) {
    nodes[key].expanded = true
  }
}

// 折叠全部
const collapseAll = () => {
  if (!treeRef.value) return
  const nodes = treeRef.value.store.nodesMap
  for (let key in nodes) {
    nodes[key].expanded = false
  }
}

// 提交
const handleSubmit = async () => {
  if (!treeRef.value) return

  try {
    submitting.value = true
    // 获取选中的节点（包含半选中的父节点）
    const checkedKeys = treeRef.value.getCheckedKeys()
    const halfCheckedKeys = treeRef.value.getHalfCheckedKeys()
    const allKeys = [...checkedKeys, ...halfCheckedKeys]

    // 调用分配权限的API
    await roleApi.assignRoleMenus(props.roleData.id, allKeys)

    ElMessage.success('权限分配成功')
    emit('success')
  } catch (error) {
    ElMessage.error('权限分配失败')
  } finally {
    submitting.value = false
  }
}

// 关闭
const handleClose = () => {
  emit('close')
}

onMounted(async () => {
  await loadMenuTree()
  await loadRoleMenus()
})
</script>

<style scoped lang="scss">
.tree-container {
  width: 100%;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  padding: 10px;
  max-height: 400px;
  overflow-y: auto;

  .tree-toolbar {
    display: flex;
    align-items: center;
    gap: 10px;
    margin-bottom: 10px;
    padding-bottom: 10px;
    border-bottom: 1px solid #ebeef5;
  }

  .custom-tree-node {
    display: flex;
    align-items: center;
    gap: 8px;
    flex: 1;
  }
}

:deep(.el-tree) {
  background-color: transparent;
}

:deep(.el-tree-node__content) {
  height: 32px;
}
</style>
