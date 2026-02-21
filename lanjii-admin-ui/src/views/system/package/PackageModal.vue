<template>
  <el-dialog
      :model-value="visible"
      :title="title"
      width="800px"
      :close-on-click-modal="false"
      @close="handleClose"
  >
    <el-form
        ref="formRef"
        :model="formData"
        :rules="rules"
        label-width="100px"
        :disabled="type === 'view'"
    >
      <el-row :gutter="20">
        <el-col :span="24">
          <el-form-item label="套餐名称" prop="packageName">
            <el-input v-model="formData.packageName" placeholder="请输入套餐名称" />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="菜单权限" prop="menuIds">
            <div class="menu-tree-container">
              <div class="tree-toolbar">
                <el-checkbox v-model="menuExpand" @change="handleExpandChange">展开/折叠</el-checkbox>
                <el-checkbox v-model="menuCheckAll" @change="handleCheckAllChange">全选/全不选</el-checkbox>
                <el-checkbox v-model="menuCheckStrictly">父子联动</el-checkbox>
              </div>
              <el-scrollbar max-height="300px">
                <el-tree
                    ref="menuTreeRef"
                    :data="menuTree"
                    :props="{ label: 'name', children: 'children' }"
                    node-key="id"
                    show-checkbox
                    :check-strictly="!menuCheckStrictly"
                    :default-checked-keys="formData.menuIdList"
                    :default-expand-all="menuExpand"
                />
              </el-scrollbar>
            </div>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="状态" prop="status">
            <el-radio-group v-model="formData.status">
              <el-radio :value="1">正常</el-radio>
              <el-radio :value="0">停用</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="备注" prop="remark">
            <el-input v-model="formData.remark" type="textarea" :rows="3" placeholder="请输入备注" />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <template #footer>
      <el-button @click="handleClose">取消</el-button>
      <el-button v-if="type !== 'view'" type="primary" :loading="loading" @click="handleSubmit">
        确定
      </el-button>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref, computed, watch, nextTick } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import * as api from '@/api/index'
import type { SysTenantPackage } from '@/types/tenant/sysTenantPackage'
import type { SysMenu } from '@/types/sys/sysMenu'
import type { ModalType } from '@/types/modal'

const props = defineProps<{
  visible: boolean
  type: ModalType
  data: SysTenantPackage
  menuTree: SysMenu[]
}>()

const emit = defineEmits<{
  close: []
  success: []
}>()

const formRef = ref<FormInstance>()
const menuTreeRef = ref()
const loading = ref(false)
const menuExpand = ref(true)
const menuCheckAll = ref(false)
const menuCheckStrictly = ref(true)

const formData = ref<SysTenantPackage>({
  packageName: '',
  menuIdList: [],
  status: 1,
  remark: ''
})

const title = computed(() => {
  const titles: Record<ModalType, string> = {
    add: '新增套餐',
    edit: '编辑套餐',
    view: '查看套餐'
  }
  return titles[props.type]
})

const rules: FormRules = {
  packageName: [{ required: true, message: '请输入套餐名称', trigger: 'blur' }],
  status: [{ required: true, message: '请选择状态', trigger: 'change' }]
}

watch(
    () => props.visible,
    (val) => {
      if (val) {
        nextTick(() => {
          if (props.data.id) {
            formData.value = { ...props.data }
            // 设置已选中的菜单
            if (props.data.menuIdList && menuTreeRef.value) {
              menuTreeRef.value.setCheckedKeys(props.data.menuIdList)
            }
          } else {
            formData.value = {
              packageName: '',
              menuIdList: [],
              status: 1,
              remark: ''
            }
            if (menuTreeRef.value) {
              menuTreeRef.value.setCheckedKeys([])
            }
          }
        })
      }
    },
    { immediate: true }
)

const handleExpandChange = (val: boolean) => {
  const nodes = menuTreeRef.value?.store?.nodesMap || {}
  for (const key in nodes) {
    nodes[key].expanded = val
  }
}

const handleCheckAllChange = (val: boolean) => {
  if (val) {
    const allIds = getAllMenuIds(props.menuTree)
    menuTreeRef.value?.setCheckedKeys(allIds)
  } else {
    menuTreeRef.value?.setCheckedKeys([])
  }
}

const getAllMenuIds = (menus: SysMenu[]): number[] => {
  const ids: number[] = []
  const traverse = (items: SysMenu[]) => {
    items.forEach(item => {
      if (item.id) ids.push(item.id)
      if (item.children?.length) {
        traverse(item.children)
      }
    })
  }
  traverse(menus)
  return ids
}

const handleClose = () => {
  formRef.value?.resetFields()
  emit('close')
}

const handleSubmit = async () => {
  const valid = await formRef.value?.validate()
  if (!valid) return

  // 获取选中的菜单ID（包括半选状态的父节点）
  const checkedKeys = menuTreeRef.value?.getCheckedKeys() || []
  const halfCheckedKeys = menuTreeRef.value?.getHalfCheckedKeys() || []
  const menuIds = [...checkedKeys, ...halfCheckedKeys]

  loading.value = true
  try {
    const submitData = {
      ...formData.value,
      menuIds: menuIds.join(',')
    }
    if (props.type === 'add') {
      await api.createPackage(submitData as SysTenantPackage)
    } else {
      await api.updatePackage(formData.value.id!, submitData as SysTenantPackage)
    }
    emit('success')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped lang="scss">
.menu-tree-container {
  border: 1px solid var(--el-border-color);
  border-radius: 4px;
  padding: 10px;
  width: 100%;
  
  .tree-toolbar {
    margin-bottom: 10px;
    padding-bottom: 10px;
    border-bottom: 1px solid var(--el-border-color-lighter);
    
    .el-checkbox {
      margin-right: 20px;
    }
  }
}
</style>
