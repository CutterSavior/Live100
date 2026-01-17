<template>
  <el-dialog
      v-model="dialogVisible"
      :title="modalTitle"
      width="900px"
      @close="handleClose"
      :close-on-click-modal="false"
      append-to-body
      destroy-on-close
  >
    <el-form
        ref="formRef"
        :model="formData"
        :rules="formRules"
        label-width="120px"
        v-loading="submitLoading"
    >
      <el-tabs v-model="activeTab">
        <!-- 基础信息 -->
        <el-tab-pane label="基础信息" name="basic">
          <!-- 查看模式：使用 el-descriptions -->
          <template v-if="type === 'view'">
            <el-descriptions :column="2" border>
              <el-descriptions-item label="文件名">
                {{ formData.fileName }}
              </el-descriptions-item>
              <el-descriptions-item label="文件类型">
                <el-tag :type="getFileTypeTag(formData.fileType)">{{ formData.fileType }}</el-tag>
              </el-descriptions-item>
              <el-descriptions-item label="文件大小">
                {{ formData.fileSizeLabel }}
              </el-descriptions-item>
              <el-descriptions-item label="文件路径">
                {{ formData.filePath }}
              </el-descriptions-item>
              <el-descriptions-item label="创建人">
                {{ formData.createBy }}
              </el-descriptions-item>
              <el-descriptions-item label="创建时间">
                {{ formData.createTime }}
              </el-descriptions-item>
            </el-descriptions>
          </template>

          <!-- 新增/编辑模式：使用 el-form -->
          <template v-else>
            <el-form-item label="文件上传" prop="file" v-if="type === 'add' || (type === 'edit' && !fileKnowledge)">
              <el-upload
                  drag
                  ref="uploadRef"
                  :auto-upload="false"
                  :show-file-list="true"
                  :limit="1"
                  :on-change="handleFileChange"
                  :on-remove="handleFileRemove"
                  :disabled="isView"
                  accept=".pdf,.md,.markdown,.txt,.json"
              >
                <el-icon class="el-icon--upload">
                  <upload-filled/>
                </el-icon>
                <div class="el-upload__text">
                  将文件拖到此处，或<em>点击上传</em>
                </div>
                <template #tip>
                  <div class="el-upload__tip">
                    支持PDF、Markdown、TXT、JSON格式文件，单个文件不超过20MB
                  </div>
                </template>
              </el-upload>
            </el-form-item>

            <el-form-item label="文件名" v-if="type === 'edit'">
              <el-input
                  v-model="formData.fileName"
                  placeholder="请输入文件名"
                  maxlength="200"
                  show-word-limit
              />
            </el-form-item>
          </template>
        </el-tab-pane>

        <!-- 元数据 -->
        <el-tab-pane label="元数据" name="metadata">
          <template v-if="availableFields.length === 0">
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
                  v-for="field in availableFields"
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
      <el-button @click="handleClose">{{ type === 'view' ? '关闭' : '取消' }}</el-button>
      <el-button
          v-if="!isView"
          type="primary"
          @click="handleSubmit"
          :loading="submitLoading"
      >保存
      </el-button>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import {ref, reactive, computed, watch, onMounted} from 'vue'
import {ElMessage, type FormInstance, type FormRules, type UploadInstance} from 'element-plus'
import {Upload, UploadFilled} from '@element-plus/icons-vue'
import type {AiFileKnowledge} from '@/types/ai/aiFileKnowledge'
import type {ModalType} from '@/types/modal'
import {createFileKnowledge, getFileKnowledgeById, updateFileKnowledge} from '@/api/modules/ai/fileKnowledgeApi'
import {getMetadataFieldPage} from '@/api/modules/ai/metadataFieldApi'

interface Props {
  visible: boolean
  type: ModalType
  fileKnowledge?: AiFileKnowledge | null
}

const props = withDefaults(defineProps<Props>(), {
  visible: false,
  type: 'add',
  fileKnowledge: null
})

const emit = defineEmits(['close', 'success'])

const dialogVisible = ref(props.visible)
const formRef = ref<FormInstance>()
const uploadRef = ref<UploadInstance>()
const submitLoading = ref(false)
const activeTab = ref('basic')
const availableFields = ref<any[]>([])
const metadataForm = ref<Record<string, any>>({})

const formData = reactive<AiFileKnowledge>({
  fileName: '',
  fileType: '',
  filePath: '',
  fileSize: 0,
  metadata: {},
  metadataJson: '',
  file: undefined,
  createTime: '',
  createBy: ''
})

const formRules = reactive<FormRules>({
  file: [
    {required: true, message: '请选择要上传的文件', trigger: 'change'}
  ]
})

const isView = computed(() => props.type === 'view')

const modalTitle = computed(() => {
  const titleMap: Record<ModalType, string> = {
    add: '上传文件',
    edit: '编辑文件',
    view: '查看文件详情'
  }
  return titleMap[props.type]
})

watch(() => props.visible, (newVal) => {
  dialogVisible.value = newVal
  if (newVal) {
    initForm()
  }
})

onMounted(() => {
  loadMetadataFields()
  if (props.type === 'edit' || props.type === 'view') {
    initForm()
  }
})

/**
 * 加载元数据字段列表
 */
const loadMetadataFields = async () => {
  try {
    const {data} = await getMetadataFieldPage({pageNum: 1, pageSize: 1000})
    availableFields.value = data.list || []

    // 初始化元数据表单
    const next: Record<string, any> = {}
    availableFields.value.forEach((field) => {
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

/**
 * 解析元数据字段默认值
 */
const parseDefaultValue = (field: any) => {
  const val = field.defaultValue
  if (val == null) return ''

  switch (field.dataType) {
    case 'number':
      return Number(val)
    default:
      return val
  }
}

/**
 * 初始化表单
 */
const initForm = async () => {
  if (props.type === 'edit' || props.type === 'view') {
    if (props.fileKnowledge) {
      const data = props.fileKnowledge
      Object.assign(formData, {
        fileName: data.fileName,
        fileType: data.fileType,
        filePath: data.filePath,
        fileSize: data.fileSize,
        metadataJson: data.metadataJson,
        metadata: data.metadataJson ? JSON.parse(data.metadataJson) : {},
        createTime: data.createTime,
        createBy: data.createBy
      })

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
        console.error('解析文件知识库元数据失败:', e)
      }
    }
  } else {
    // 新增模式，重置表单
    Object.assign(formData, {
      fileName: '',
      fileType: '',
      filePath: '',
      fileSize: 0,
      metadata: {},
      metadataJson: '',
      file: undefined,
      createTime: '',
      createBy: ''
    })
  }
}

/**
 * 文件选择处理
 */
const handleFileChange = (file: any) => {
  const maxSize = 20 * 1024 * 1024 // 20MB
  if (file.size > maxSize) {
    ElMessage.error('文件大小不能超过20MB')
    uploadRef.value?.clearFiles()
    return
  }
  formData.file = file.raw
  formData.fileName = file.name
}

/**
 * 文件移除处理
 */
const handleFileRemove = () => {
  formData.file = undefined
  formData.fileName = ''
}


/**
 * 校验元数据必填字段
 */
const validateMetadata = (): boolean => {
  if (!availableFields.value.length) return true

  for (const field of availableFields.value) {
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

/**
 * 根据当前元数据表单构造 JSON 字符串
 */
const buildMetadataJson = (): string | undefined => {
  if (!availableFields.value.length) return undefined

  const cleaned: Record<string, any> = {}
  Object.keys(metadataForm.value).forEach((key) => {
    const val = metadataForm.value[key]
    if (val !== undefined && val !== null && !(typeof val === 'string' && val.trim() === '')) {
      cleaned[key] = val
    }
  })

  return Object.keys(cleaned).length ? JSON.stringify(cleaned) : undefined
}

/**
 * 获取文件类型标签颜色
 */
const getFileTypeTag = (fileType: string) => {
  const typeMap: Record<string, string> = {
    'pdf': 'danger',
    'md': 'success',
    'markdown': 'success',
    'txt': 'info',
    'json': 'warning'
  }
  return typeMap[fileType?.toLowerCase()] || 'primary'
}

/**
 * 提交表单
 */
const handleSubmit = async () => {
  if (!formRef.value) return

  try {
    await formRef.value.validate()

    // 验证元数据必填项
    if (!validateMetadata()) {
      return
    }

    // 构建FormData
    const formDataToSend = new FormData()

    if (props.type === 'add') {
      if (!formData.file) {
        ElMessage.error('请选择要上传的文件')
        return
      }
      formDataToSend.append('file', formData.file)
    } else if (props.type === 'edit') {
      if (formData.file) {
        formDataToSend.append('file', formData.file)
      }
      formDataToSend.append('fileName', formData.fileName)
    }

    const metadataJson = buildMetadataJson()
    formDataToSend.append('metadataJson', metadataJson || '')

    submitLoading.value = true

    if (props.type === 'add') {
      await createFileKnowledge(formDataToSend)
      ElMessage.success('文件上传成功')
    } else if (props.type === 'edit' && props.fileKnowledge?.id) {
      await updateFileKnowledge(props.fileKnowledge.id, formDataToSend)
      ElMessage.success('更新成功')
    }

    emit('success')
  } catch (error) {
    console.error('提交失败:', error)
  } finally {
    submitLoading.value = false
  }
}

/**
 * 关闭弹窗
 */
const handleClose = () => {
  dialogVisible.value = false
  emit('close')
}
</script>

<style scoped lang="scss">
.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}
</style>
