<template>
  <el-dialog
      :model-value="visible"
      @close="$emit('close')"
      :title="modalTitle"
      width="800px"
      :close-on-click-modal="false"
      append-to-body
      destroy-on-close
  >
    <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        label-width="160px"
        v-loading="loading"
    >
      <el-tabs v-model="activeTab">
        <!-- 基本配置 -->
        <el-tab-pane label="基本配置" name="basic">
          <el-form-item label="配置名称" prop="configName">
            <el-input
                v-model="form.configName"
                :disabled="isView"
                placeholder="请输入配置名称"
            />
          </el-form-item>

          <el-form-item label="角色与提示词" prop="roleId">
            <el-select
                v-model="form.roleId"
                :disabled="isView"
                placeholder="请选择角色与提示词"
                clearable
            >
              <el-option
                  v-for="item in rolePromptOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>

          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="API 提供商" prop="apiProvider">
                <el-select
                    v-model="form.apiProvider"
                    :disabled="isView"
                    placeholder="请选择 API 提供商"
                >
                  <el-option label="OpenAI" value="openai"/>
                  <el-option label="DeepSeek" value="deepseek"/>
                  <el-option label="智谱 Zhipu" value="zhipu"/>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="模型标识" prop="modelId">
                <el-input
                    v-model="form.modelId"
                    :disabled="isView"
                    placeholder="请输入模型标识"
                />
              </el-form-item>
            </el-col>
          </el-row>

          <el-form-item label="API Key" prop="apiKey">
            <div class="api-key-container">
              <el-input
                  v-if="isEditingApiKey"
                  v-model="newApiKey"
                  type="password"
                  show-password
                  :placeholder="!isView && !isEditingApiKey && props.type === 'edit'?'输入新的 API Key':'请输入 API Key'"
                  clearable
                  :disabled="isView"
              />
              <el-input
                  v-else
                  :model-value="form.apiKey"
                  disabled
              />
              <el-button
                  v-if="!isView && !isEditingApiKey && props.type === 'edit'"
                  type="primary"
                  @click="startEditApiKey"
              >
                修改
              </el-button>
              <el-button
                  v-if="!isView && isEditingApiKey && props.type === 'edit'"
                  @click="cancelEditApiKey"
              >
                取消
              </el-button>
            </div>
          </el-form-item>

          <el-form-item label="API 端点" prop="apiEndpoint">
            <el-input
                v-model="form.apiEndpoint"
                :disabled="isView"
                placeholder="请输入API 端点"
            />
          </el-form-item>

          <el-form-item label="描述" prop="description">
            <el-input
                v-model="form.description"
                :disabled="isView"
                type="textarea"
                :rows="3"
                placeholder="请输入描述"
            />
          </el-form-item>
        </el-tab-pane>

        <!-- 参数调优 -->
        <el-tab-pane label="参数调优" name="params">
          <el-form-item>
            <template #label>
              <LabelWithTooltip
                label="温度 (Temperature)"
                tooltip="控制输出的随机性。较低的值（如0.2）使输出更加集中和确定，较高的值（如1.0）使输出更加多样和创造"
              />
            </template>
            <div class="slider-container">
              <el-slider
                  v-model="temperatureSlider"
                  :min="0"
                  :max="2"
                  :step="0.1"
                  :disabled="isView"
              />
              <span class="slider-value">{{ temperatureSlider }}</span>
            </div>
          </el-form-item>

          <el-form-item>
            <template #label>
              <LabelWithTooltip
                label="核采样 (Top P)"
                tooltip="控制输出的多样性。较低的值（如0.5）仅考虑最可能的词，较高的值（如0.9）考虑更多可能的词。"
              />
            </template>
            <div class="slider-container">
              <el-slider
                  v-model="topPSlider"
                  :min="0"
                  :max="1"
                  :step="0.1"
                  :disabled="isView"
              />
              <span class="slider-value">{{ topPSlider }}</span>
            </div>
          </el-form-item>

          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="最大 Token 数" prop="maxTokens">
                <el-input-number
                    v-model="form.maxTokens"
                    :disabled="isView"
                    :min="1"
                />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="超时时间(秒)" prop="timeoutSeconds">
                <el-input-number
                    v-model="form.timeoutSeconds"
                    :disabled="isView"
                    :min="1"
                />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="频率惩罚" prop="frequencyPenalty">
                <el-input-number
                    v-model="form.frequencyPenalty"
                    :disabled="isView"
                    :step="0.1"
                    :min="-2"
                    :max="2"
                />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="存在惩罚" prop="presencePenalty">
                <el-input-number
                    v-model="form.presencePenalty"
                    :disabled="isView"
                    :step="0.1"
                    :min="-2"
                    :max="2"
                />
              </el-form-item>
            </el-col>
          </el-row>
        </el-tab-pane>

        <!-- 高级选项 -->
        <el-tab-pane label="高级选项" name="advanced">
          <el-form-item label="停止序列" prop="stopSequences">
            <el-input
                v-model="form.stopSequences"
                :disabled="isView"
                type="textarea"
                :rows="4"
                placeholder="每行一个停止序列"
            />
          </el-form-item>

          <el-form-item label="重试次数" prop="retryCount">
            <el-input-number
                v-model="form.retryCount"
                :disabled="isView"
                :min="0"
            />
          </el-form-item>
        </el-tab-pane>
      </el-tabs>

      <el-form-item label="是否启用" prop="isEnabled">
        <el-radio-group v-model="form.isEnabled" :disabled="isView">
          <el-radio :label="1">启用</el-radio>
          <el-radio :label="0">禁止</el-radio>
        </el-radio-group>
      </el-form-item>
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
import type {AiModelConfig} from '@/types/ai/aiModelConfig'
import {
  createModelConfig,
  getModelConfigById,
  updateModelConfig
} from '@/api/modules/ai/modelConfigApi'
import {getEnabledRolePrompts} from '@/api/modules/ai/rolePromptApi'
import type {AiRolePrompt} from '@/types/ai/aiRolePrompt'
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
  modelConfig: {
    type: Object as () => AiModelConfig | null,
    default: null
  }
})

const emit = defineEmits(['close', 'success'])

const formRef = ref()
const loading = ref(false)
const activeTab = ref('basic')
const rolePromptOptions = ref<{ label: string; value: number }[]>([])
const isEditingApiKey = ref(false)
const newApiKey = ref('')

const form = ref<AiModelConfig>({
  configName: '',
  apiProvider: '',
  modelId: '',
  isDefault:0,
  isEnabled: 1,
  apiKey: '',
  temperature: 0.7,
  topP: 0.9,
  maxTokens: 2048,
  timeoutSeconds: 60,
  frequencyPenalty: 0,
  presencePenalty: 0,
  retryCount: 3
})

const rules = {
  configName: [{required: true, message: '请输入配置名称', trigger: 'blur'}],
  apiProvider: [{required: true, message: '请选择 API 提供商', trigger: 'change'}],
  modelId: [{required: true, message: '请输入模型标识', trigger: 'blur'}],
  apiKey: [{required: true, message: '请输入 API Key', trigger: 'blur'}]
}

const isView = computed(() => props.type === 'view')
const modalTitle = computed(() => getModalTitle(props.type as any, '模型配置'))

// 开始编辑 API Key
const startEditApiKey = () => {
  isEditingApiKey.value = true
  newApiKey.value = ''
}

// 取消编辑 API Key
const cancelEditApiKey = () => {
  isEditingApiKey.value = false
  newApiKey.value = ''
}

// slider 对应 temperature/topP 显示
const temperatureSlider = ref(0.7)
const topPSlider = ref(0.9)

watch(
    () => temperatureSlider.value,
    v => {
      form.value.temperature = v
    }
)

watch(
    () => topPSlider.value,
    v => {
      form.value.topP = v
    }
)

// 监听 newApiKey 变化，实时同步到 form.value.apiKey 以便验证
watch(
    () => newApiKey.value,
    v => {
      if (isEditingApiKey.value) {
        form.value.apiKey = v
      }
    }
)

const loadDetail = async (id?: number) => {
  if (!id) return
  try {
    loading.value = true
    const res = (await getModelConfigById(id)) as ResponseData
    form.value = res.data as AiModelConfig
    temperatureSlider.value = form.value.temperature ?? 0.7
    topPSlider.value = form.value.topP ?? 0.9
  } finally {
    loading.value = false
  }
}

const loadRolePromptOptions = async () => {
  const res = (await getEnabledRolePrompts()) as ResponseData
  const list = res.data as AiRolePrompt[]
  rolePromptOptions.value = (list || []).map((item) => ({
    label: item.roleName,
    value: item.id as number
  }))
}

onMounted(() => {
  loadRolePromptOptions()
  if (props.visible && (props.type === 'edit' || props.type === 'view') && props.modelConfig?.id) {
    loadDetail(props.modelConfig.id)
  } else {
    // 初始化默认 slider
    temperatureSlider.value = 0.7
    topPSlider.value = 0.9
    // 新增模式下，直接显示可编辑的 API Key 输入框
    if (props.type === 'add') {
      isEditingApiKey.value = true
    }
  }
})

const handleSubmit = () => {
  formRef.value.validate(async (valid: boolean) => {
    if (!valid) return

    // 如果正在编辑 API Key 且有新值，使用新值
    if (isEditingApiKey.value && newApiKey.value) {
      form.value.apiKey = newApiKey.value
    }

    if (props.type === 'add') {
      await createModelConfig(form.value)
    } else if (props.type === 'edit' && form.value.id) {
      await updateModelConfig(form.value.id, form.value)
    }
    ElMessage.success('保存成功')
    emit('success')
  })
}
</script>

<style scoped>
.slider-container {
  display: flex;
  align-items: center;
  width: 100%;
  gap: 12px;
}

.slider-container :deep(.el-slider) {
  flex: 1;
}

.slider-value {
  min-width: 48px;
  flex-shrink: 0;
  text-align: center;
  background-color: var(--el-fill-color-light);
  border-radius: 4px;
  padding: 2px 6px;
  color: var(--el-color-primary);
}

.api-key-container {
  display: flex;
  align-items: center;
  gap: 12px;
  width: 100%;
}

.api-key-container :deep(.el-input) {
  flex: 1;
}

</style>
