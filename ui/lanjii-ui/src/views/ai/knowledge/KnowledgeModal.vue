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
        <!-- 基础信息 -->
        <el-tab-pane label="基础信息" name="basic">
          <el-form-item label="知识库标题" prop="title">
            <el-input
                v-model="form.title"
                :disabled="isView"
                placeholder="请输入知识库标题"
                maxlength="200"
                show-word-limit
            />
          </el-form-item>

          <el-form-item label="知识库内容" prop="content">
            <el-input
                :disabled="isView"
                v-model="form.content"
                type="textarea"
                :rows="12"
                placeholder="请输入知识库内容..."
                maxlength="10000"
                show-word-limit
            />
          </el-form-item>
        </el-tab-pane>

        <!-- 元数据 -->
        <el-tab-pane label="元数据" name="metadata">
          <template v-if="metadataFields.length === 0">
            <el-alert
                type="info"
                show-icon
                :closable="false"
                title="当前未配置任何元数据字段"
            />
          </template>
          <template v-else>
            <el-row :gutter="16">
              <el-col
                  v-for="field in metadataFields"
                  :key="field.id"
                  :span="24"
              >
                <el-form-item :label="field.displayName" :required="field.isRequired === 1">
                  <!-- 字符串类型 -->
                  <el-input
                      v-if="field.dataType === 'string'"
                      v-model="metadataForm[field.fieldName]"
                      :placeholder="field.description || `请输入${field.displayName}`"
                      :disabled="isView"
                      clearable
                  />

                  <!-- 数字类型 -->
                  <el-input-number
                      v-else-if="field.dataType === 'number'"
                      v-model="metadataForm[field.fieldName]"
                      :placeholder="field.description || `请输入${field.displayName}`"
                      :disabled="isView"
                      :controls="true"
                      style="width: 100%;"
                  />

                  <!-- 日期 / 日期时间 -->
                  <el-date-picker
                      v-else-if="field.dataType === 'date' || field.dataType === 'datetime'"
                      v-model="metadataForm[field.fieldName]"
                      :type="field.dataType === 'date' ? 'date' : 'datetime'"
                      :placeholder="field.description || `请选择${field.displayName}`"
                      :disabled="isView"
                      :value-format="field.dataType === 'date' ? 'YYYY-MM-DD' : 'YYYY-MM-DD HH:mm:ss'"
                      style="width: 100%;"
                  />

                  <!-- 兜底使用输入框 -->
                  <el-input
                      v-else
                      v-model="metadataForm[field.fieldName]"
                      :placeholder="field.description || `请输入${field.displayName}`"
                      :disabled="isView"
                      clearable
                  />
                </el-form-item>
              </el-col>
            </el-row>
          </template>
        </el-tab-pane>
      </el-tabs>
    </el-form>

    <template #footer>
      <el-button @click="$emit('close')">取消</el-button>
      <el-button
          v-if="!isView"
          type="primary"
          @click="handleSubmit"
          :loading="loading"
      >保存
      </el-button>
      <el-button v-else type="primary" @click="$emit('close')">关闭</el-button>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import {computed, onMounted, ref} from 'vue'
import type {AiKnowledge} from '@/types/ai/aiKnowledge'
import type {AiMetadataField} from '@/types/ai/aiMetadataField'
import {createKnowledge, getKnowledgeById, updateKnowledge} from '@/api/modules/ai/knowledgeApi'
import {getMetadataFieldPage} from '@/api/modules/ai/metadataFieldApi'
import {ElMessage} from 'element-plus'
import {getModalTitle} from '@/types/modal'

const props = defineProps({
  visible: Boolean,
  type: {
    type: String,
    default: 'add' // add, edit, view
  },
  knowledge: {
    type: Object as () => AiKnowledge | null,
    default: null
  }
})

const emit = defineEmits(['close', 'success'])

const formRef = ref()
const loading = ref(false)
const activeTab = ref('basic')

const form = ref<AiKnowledge>({
  title: '',
  content: ''
})

// 元数据字段定义 & 表单值
const metadataFields = ref<AiMetadataField[]>([])
const metadataForm = ref<Record<string, any>>({})

const rules = {
  title: [
    {required: true, message: '请输入知识库标题', trigger: 'blur'},
    {min: 1, max: 200, message: '标题长度在 1 到 200 个字符', trigger: 'blur'}
  ],
  content: [
    {required: true, message: '请输入知识库内容', trigger: 'blur'}
  ]
}

const isView = computed(() => props.type === 'view')
const modalTitle = computed(() => getModalTitle(props.type as any, '知识库'))

// 加载元数据字段配置
const loadMetadataFields = async () => {
  try {
    const {data} = await getMetadataFieldPage({pageNum: 1, pageSize: 1000})
    const list: AiMetadataField[] = (data && (data.list)) || []
    metadataFields.value = list

    // 初始化元数据表单
    const next: Record<string, any> = {}
    metadataFields.value.forEach((field) => {
      const key = field.fieldName
      if (field.defaultValue !== undefined && field.defaultValue !== null && field.defaultValue !== '') {
        next[key] = parseDefaultValue(field)
      } else {
        next[key] = ''
      }
    })
    metadataForm.value = next
  } catch (error) {
    console.error('加载元数据字段失败:', error)
  }
}

// 解析元数据字段默认值
const parseDefaultValue = (field: AiMetadataField) => {
  const val = field.defaultValue
  if (val == null) return ''

  switch (field.dataType) {
    case 'number':
      return Number(val)
    default:
      return val
  }
}

// 加载详情
const loadDetail = async (id?: number) => {
  if (!id) return
  try {
    loading.value = true
    const {data} = await getKnowledgeById(id)
    form.value = {
      title: data.title,
      content: data.content
    }

    // 解析已保存的元数据 JSON
    try {
      const loadedMetadata = data.metadataJson ? JSON.parse(data.metadataJson) : {}
      // 合并到 metadataForm
      Object.keys(loadedMetadata).forEach((key) => {
        if (metadataForm.value.hasOwnProperty(key)) {
          metadataForm.value[key] = loadedMetadata[key]
        }
      })
    } catch (e) {
      console.error('解析知识库元数据失败:', e)
    }
  } catch (error) {
    console.error('获取知识库详情失败:', error)
  } finally {
    loading.value = false
  }
}

// 校验元数据必填字段
const validateMetadata = (): boolean => {
  if (!metadataFields.value.length) return true

  for (const field of metadataFields.value) {
    if (field.isRequired === 1) {
      const value = metadataForm.value[field.fieldName]
      if (value === undefined || value === null || (typeof value === 'string' && value.trim() === '')) {
        ElMessage.error(`请输入元数据字段：${field.displayName}`)
        return false
      }
    }
  }

  return true
}

// 根据当前元数据表单构造 JSON 字符串
const buildMetadataJson = (): string | undefined => {
  if (!metadataFields.value.length) return undefined

  const cleaned: Record<string, any> = {}
  Object.keys(metadataForm.value).forEach((key) => {
    const val = metadataForm.value[key]
    if (val !== undefined && val !== null && !(typeof val === 'string' && val.trim() === '')) {
      cleaned[key] = val
    }
  })

  return Object.keys(cleaned).length ? JSON.stringify(cleaned) : undefined
}

onMounted(async () => {
  if (props.visible && (props.type === 'edit' || props.type === 'view') && props.knowledge?.id) {
    await loadDetail(props.knowledge.id)
  }
  await loadMetadataFields()
})

const handleSubmit = () => {
  formRef.value.validate(async (valid: boolean) => {
    if (!valid) return

    // 验证内容不为空
    if (!form.value.content || form.value.content.trim() === '') {
      ElMessage.error('请输入知识库内容')
      return
    }

    // 验证元数据必填项
    if (!validateMetadata()) {
      return
    }

    try {
      loading.value = true

      const metadataJson = buildMetadataJson()

      if (props.type === 'add') {
        await createKnowledge({
          title: form.value.title,
          content: form.value.content,
          metadataJson
        } as AiKnowledge)
      } else if (props.type === 'edit' && props.knowledge?.id) {
        await updateKnowledge(props.knowledge.id, {
          title: form.value.title,
          content: form.value.content,
          metadataJson
        } as AiKnowledge)
      }

      ElMessage.success('保存成功')
      emit('success')
    } catch (error) {
      console.error('保存失败:', error)
    } finally {
      loading.value = false
    }
  })
}
</script>

<style scoped>

</style>
