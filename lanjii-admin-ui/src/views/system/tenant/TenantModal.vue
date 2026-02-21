<template>
  <el-dialog
      :model-value="visible"
      :title="title"
      width="600px"
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
      <el-form-item label="租户编码" prop="tenantCode">
        <el-input v-model="formData.tenantCode" placeholder="请输入租户编码" :disabled="type === 'edit'" />
      </el-form-item>
      <el-form-item label="租户名称" prop="tenantName">
        <el-input v-model="formData.tenantName" placeholder="请输入租户名称" />
      </el-form-item>
      <el-form-item label="套餐" prop="packageId">
        <el-select v-model="formData.packageId" placeholder="请选择套餐" style="width: 100%">
          <el-option
              v-for="item in packageOptions"
              :key="item.id"
              :label="item.packageName"
              :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="联系人" prop="contactName">
        <el-input v-model="formData.contactName" placeholder="请输入联系人" />
      </el-form-item>
      <el-form-item label="联系电话" prop="contactPhone">
        <el-input v-model="formData.contactPhone" placeholder="请输入联系电话" />
      </el-form-item>
      <el-form-item label="过期时间" prop="expireTime">
        <el-date-picker
            v-model="formData.expireTime"
            type="datetime"
            placeholder="请选择过期时间"
            format="YYYY-MM-DD HH:mm:ss"
            value-format="YYYY-MM-DD HH:mm:ss"
            style="width: 100%"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-radio-group v-model="formData.status">
          <el-radio :value="1">正常</el-radio>
          <el-radio :value="0">停用</el-radio>
        </el-radio-group>
      </el-form-item>
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
import { ref, computed, watch } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import * as api from '@/api/index'
import type { SysTenant } from '@/types/tenant/sysTenant'
import type { SysTenantPackage } from '@/types/tenant/sysTenantPackage'
import type { ModalType } from '@/types/modal'

const props = defineProps<{
  visible: boolean
  type: ModalType
  data: SysTenant
  packageOptions: SysTenantPackage[]
}>()

const emit = defineEmits<{
  close: []
  success: []
}>()

const formRef = ref<FormInstance>()
const loading = ref(false)

const formData = ref<SysTenant>({
  tenantCode: '',
  tenantName: '',
  packageId: undefined,
  contactName: '',
  contactPhone: '',
  status: 1,
  expireTime: ''
})

const title = computed(() => {
  const titles: Record<ModalType, string> = {
    add: '新增租户',
    edit: '编辑租户',
    view: '查看租户'
  }
  return titles[props.type]
})

const rules: FormRules = {
  tenantCode: [{ required: true, message: '请输入租户编码', trigger: 'blur' }],
  tenantName: [{ required: true, message: '请输入租户名称', trigger: 'blur' }],
  packageId: [{ required: true, message: '请选择套餐', trigger: 'change' }],
  status: [{ required: true, message: '请选择状态', trigger: 'change' }]
}

watch(
    () => props.visible,
    (val) => {
      if (val && props.data.id) {
        formData.value = { ...props.data }
      } else if (val) {
        formData.value = {
          tenantCode: '',
          tenantName: '',
          packageId: undefined,
          contactName: '',
          contactPhone: '',
          status: 1,
          expireTime: ''
        }
      }
    },
    { immediate: true }
)

const handleClose = () => {
  formRef.value?.resetFields()
  emit('close')
}

const handleSubmit = async () => {
  const valid = await formRef.value?.validate()
  if (!valid) return

  loading.value = true
  try {
    if (props.type === 'add') {
      await api.createTenant(formData.value)
    } else {
      await api.updateTenant(formData.value.id!, formData.value)
    }
    emit('success')
  } finally {
    loading.value = false
  }
}
</script>
