<template>
  <el-dialog
      :model-value="visible"
      @close="$emit('close')"
      :title="modalTitle"
      width="900px"
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
      <el-tabs v-model="activeTab">
        <!-- 角色信息 -->
        <el-tab-pane label="角色信息" name="basic">
          <el-row :gutter="20">
            <el-col :span="24">
              <el-form-item label="角色名称" prop="roleName">
                <el-input
                    v-model="form.roleName"
                    :disabled="isView"
                    placeholder="请输入角色名称"
                />
              </el-form-item>
            </el-col>
          </el-row>

          <el-form-item label="角色描述" prop="description">
            <el-input
                v-model="form.description"
                :disabled="isView"
                type="textarea"
                :rows="3"
                placeholder="请输入角色描述"
            />
          </el-form-item>

          <el-form-item label="是否启用" prop="isEnabled">
            <el-radio-group v-model="form.isEnabled" :disabled="isView">
              <el-radio :label="1">启用</el-radio>
              <el-radio :label="0">禁用</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-tab-pane>

        <!-- 系统提示词 -->
        <el-tab-pane label="系统提示词" name="system">
          <el-form-item label="系统提示词" prop="systemPrompt">
            <el-input
                v-model="form.systemPrompt"
                :disabled="isView"
                type="textarea"
                :rows="8"
                placeholder="请输入系统提示词"
            />
          </el-form-item>
        </el-tab-pane>

        <!-- RAG 模板配置 -->
        <el-tab-pane label="RAG 模板配置" name="rag">
          <el-form-item label="RAG 提示词模板" prop="ragTemplate">
            <el-input
                v-model="form.ragTemplate"
                :disabled="isView"
                type="textarea"
                :rows="6"
                placeholder="请输入 RAG 提示词模板"
            />
          </el-form-item>

          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="检索文档数量" prop="topK">
                <el-input-number
                    v-model="form.topK"
                    :disabled="isView"
                    :min="1"
                />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="相似度阈值" prop="similarityThreshold">
                <el-input-number
                    v-model="form.similarityThreshold"
                    :disabled="isView"
                    :min="0"
                    :max="1"
                    :step="0.1"
                />
              </el-form-item>
            </el-col>
          </el-row>

          <el-form-item prop="customFilter">
            <template #label>
              <LabelWithTooltip
                label="自定义过滤"
                tooltip="支持类 SQL 语法，例如：priority >= 5 && author == 'admin'、status IN ['active', 'pending'] || year > 2020"
              />
            </template>
            <el-input
                v-model="form.customFilter"
                :disabled="isView"
                type="textarea"
                :rows="5"
                placeholder="请输入自定义过滤表达式"
            />
          </el-form-item>

          <el-form-item label="启用 RAG" prop="isRagEnabled">
            <el-switch
                v-model="isRagEnabledSwitch"
                :disabled="isView"
                inline-prompt
                active-text="启用"
                inactive-text="关闭"
            />
          </el-form-item>
        </el-tab-pane>
      </el-tabs>
    </el-form>

    <template #footer>
      <el-button @click="$emit('close')">取消</el-button>
      <el-button
          v-if="!isView"
          type="primary"
          @click="handleSubmit"
      >保存
      </el-button>
      <el-button v-else type="primary" @click="$emit('close')">关闭</el-button>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import {computed, onMounted, ref, watch} from 'vue'
import type {AiRolePrompt} from '@/types/ai/aiRolePrompt'
import {createRolePrompt, getRolePromptById, updateRolePrompt} from '@/api/modules/ai/rolePromptApi'
import type {ResponseData} from '@/api/http'
import {ElMessage} from 'element-plus'
import {getModalTitle} from '@/types/modal'
import { LabelWithTooltip } from '@/components/LabelWithTooltip'

const props = defineProps({
  visible: Boolean,
  type: {
    type: String,
    default: 'add' // add, edit, view
  },
  rolePrompt: {
    type: Object as () => AiRolePrompt | null,
    default: null
  }
})

const emit = defineEmits(['close', 'success'])

const formRef = ref()
const loading = ref(false)
const activeTab = ref('basic')

const form = ref<AiRolePrompt>({
  roleName: '',
  description: '',
  isEnabled: 1,
  systemPrompt: '',
  isRagEnabled: 1,
  ragTemplate: '',
  topK: 5,
  similarityThreshold: 0.7,
  customFilter: ''
})

const rules = {
  roleName: [{required: true, message: '请输入角色名称', trigger: 'blur'}],
  systemPrompt: [{required: true, message: '请输入系统提示词', trigger: 'blur'}]
}

const isView = computed(() => props.type === 'view')
const modalTitle = computed(() => getModalTitle(props.type as any, '角色与提示词'))

const isRagEnabledSwitch = ref(true)

watch(
    () => isRagEnabledSwitch.value,
    (v) => {
      form.value.isRagEnabled = v ? 1 : 0
    }
)

const loadDetail = async (id?: number) => {
  if (!id) return
  try {
    loading.value = true
    const res = (await getRolePromptById(id)) as ResponseData
    form.value = res.data as AiRolePrompt
    isRagEnabledSwitch.value = (form.value.isRagEnabled ?? 1) === 1
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  if (props.visible && (props.type === 'edit' || props.type === 'view') && props.rolePrompt?.id) {
    loadDetail(props.rolePrompt.id)
  } else {
    isRagEnabledSwitch.value = (form.value.isRagEnabled ?? 1) === 1
  }
})

const handleSubmit = () => {
  formRef.value.validate(async (valid: boolean) => {
    if (!valid) return

    if (props.type === 'add') {
      await createRolePrompt(form.value)
    } else if (props.type === 'edit' && form.value.id) {
      await updateRolePrompt(form.value.id, form.value)
    }

    ElMessage.success('保存成功')
    emit('success')
  })
}
</script>

<style scoped>

</style>
