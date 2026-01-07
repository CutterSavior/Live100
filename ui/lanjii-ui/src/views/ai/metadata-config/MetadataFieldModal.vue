<template>
  <el-dialog
    :model-value="visible"
    @close="$emit('close')"
    :title="modalTitle"
    width="700px"
    :close-on-click-modal="false"
    append-to-body
    destroy-on-close
  >
    <el-form
      ref="formRef"
      :model="form"
      :rules="rules"
      label-width="120px"
      v-loading="loading"
    >
      <el-form-item label="字段名" prop="fieldName">
        <el-input
          v-model="form.fieldName"
          :disabled="isView"
          placeholder="例如: author"
        />
      </el-form-item>

      <el-form-item label="显示名称" prop="displayName">
        <el-input
          v-model="form.displayName"
          :disabled="isView"
          placeholder="例如: 作者"
        />
      </el-form-item>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="数据类型" prop="dataType">
            <el-select
              v-model="form.dataType"
              :disabled="isView"
              placeholder="请选择"
            >
              <el-option label="字符串" value="string" />
              <el-option label="数字" value="number" />
              <el-option label="日期" value="date" />
              <el-option label="日期时间" value="datetime" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="默认值" prop="defaultValue">
            <el-input
              v-model="form.defaultValue"
              :disabled="isView"
              placeholder="留空表示无默认值"
            />
          </el-form-item>
        </el-col>
      </el-row>

      <el-form-item label="字段描述" prop="description">
        <el-input
          v-model="form.description"
          :disabled="isView"
          type="textarea"
          :rows="3"
          placeholder="描述该字段的用途和注意事项"
        />
      </el-form-item>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="是否必填" prop="isRequired">
            <el-switch
              v-model="requiredSwitch"
              :disabled="isView"
              inline-prompt
              active-text="是"
              inactive-text="否"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="是否可搜索" prop="isSearchable">
            <el-switch
              v-model="searchableSwitch"
              :disabled="isView"
              inline-prompt
              active-text="是"
              inactive-text="否"
            />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <template #footer>
      <el-button @click="$emit('close')">取消</el-button>
      <el-button
        v-if="!isView"
        type="primary"
        @click="handleSubmit"
      >保存</el-button>
      <el-button v-else type="primary" @click="$emit('close')">关闭</el-button>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { computed, onMounted, ref, watch } from 'vue'
import type { AiMetadataField } from '@/types/ai/aiMetadataField'
import {
  createMetadataField,
  getMetadataFieldById,
  updateMetadataField
} from '@/api/modules/ai/metadataFieldApi'
import type { ResponseData } from '@/api/http'
import { ElMessage } from 'element-plus'
import { getModalTitle } from '@/types/modal'

const props = defineProps({
  visible: Boolean,
  type: {
    type: String,
    default: 'add' // add, edit, view
  },
  fieldData: {
    type: Object as () => AiMetadataField | null,
    default: null
  }
})

const emit = defineEmits(['close', 'success'])

const formRef = ref()
const loading = ref(false)

const form = ref<AiMetadataField>({
  fieldName: '',
  displayName: '',
  dataType: '',
  defaultValue: '',
  description: '',
  isRequired: 0,
  isSearchable: 1
})

const rules = {
  fieldName: [{ required: true, message: '请输入字段名', trigger: 'blur' }],
  displayName: [{ required: true, message: '请输入显示名称', trigger: 'blur' }],
  dataType: [{ required: true, message: '请选择数据类型', trigger: 'change' }]
}

const isView = computed(() => props.type === 'view')
const modalTitle = computed(() => getModalTitle(props.type as any, '元数据字段'))

const requiredSwitch = ref(false)
const searchableSwitch = ref(true)

watch(
  () => requiredSwitch.value,
  (v) => {
    form.value.isRequired = v ? 1 : 0
  }
)

watch(
  () => searchableSwitch.value,
  (v) => {
    form.value.isSearchable = v ? 1 : 0
  }
)

const loadDetail = async (id?: number) => {
  if (!id) return
  try {
    loading.value = true
    const res = (await getMetadataFieldById(id)) as ResponseData
    form.value = res.data as AiMetadataField
    requiredSwitch.value = form.value.isRequired === 1
    searchableSwitch.value = form.value.isSearchable === 1
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  if (props.visible && (props.type === 'edit' || props.type === 'view') && props.fieldData?.id) {
    loadDetail(props.fieldData.id)
  } else {
    requiredSwitch.value = form.value.isRequired === 1
    searchableSwitch.value = form.value.isSearchable === 1
  }
})

const handleSubmit = () => {
  formRef.value.validate(async (valid: boolean) => {
    if (!valid) return

    if (props.type === 'add') {
      await createMetadataField(form.value)
    } else if (props.type === 'edit' && form.value.id) {
      await updateMetadataField(form.value.id, form.value)
    }

    ElMessage.success('保存成功')
    emit('success')
  })
}
</script>

<style scoped>
</style>
