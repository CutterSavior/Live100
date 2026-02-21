<template>
  <div class="main-content">
    <div class="card-box table-card">
      <el-form
          ref="formRef"
          :model="form"
          :rules="rules"
          label-width="100px"
          class="publish-form"
      >
        <el-form-item label="通知标题" prop="title">
          <el-input
              v-model="form.title"
              placeholder="请输入通知标题"
              maxlength="100"
              show-word-limit
          />
        </el-form-item>

        <el-form-item label="通知内容" prop="content">
          <WangEditor
              v-model="form.content"
              placeholder="请输入通知内容..."
              height="400px"
          />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="handleSubmit" :loading="loading">
            发布通知
          </el-button>
          <el-button @click="handleReset">
            重置
          </el-button>
          <el-button @click="handleCancel">
            取消
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup lang="ts">
import {ref} from 'vue'
import {ElMessage, ElMessageBox} from 'element-plus'
import {useRouter} from 'vue-router'
import {publishNotice} from '@/api/modules/notification/noticeApi'
import WangEditor from '@/components/WangEditor/WangEditor.vue'

const router = useRouter()
const formRef = ref()
const loading = ref(false)

// 表单数据
const form = ref({
  title: '',
  content: ''
})

// 表单验证规则
const rules = {
  title: [
    {required: true, message: '请输入通知标题', trigger: 'blur'},
    {min: 2, max: 100, message: '标题长度在 2 到 100 个字符', trigger: 'blur'}
  ],
  content: [
    {required: true, message: '请输入通知内容', trigger: 'blur'}
  ]
}


/**
 * 提交表单
 */
const handleSubmit = async () => {
  if (!formRef.value) return

  try {
    await formRef.value.validate()

    // 验证内容不为空（WangEditor空内容为 '<p><br></p>'）
    if (!form.value.content || form.value.content.trim() === '' || form.value.content.trim() === '<p><br></p>') {
      ElMessage.error('请输入通知内容')
      return
    }

    loading.value = true

    await publishNotice({
      title: form.value.title,
      content: form.value.content
    })

    ElMessage.success('通知发布成功')

    // 询问是否继续发布
    ElMessageBox.confirm('通知发布成功！是否继续发布新通知？', '发布成功', {
      confirmButtonText: '继续发布',
      cancelButtonText: '返回列表',
      type: 'success'
    }).then(() => {
      handleReset()
    }).catch(() => {
      router.push('/admin/notice')
    })

  } catch (error) {

  } finally {
    loading.value = false
  }
}

/**
 * 重置表单
 */
const handleReset = () => {
  form.value = {
    title: '',
    content: ''
  }

  formRef.value?.clearValidate()
}

/**
 * 取消发布
 */
const handleCancel = () => {
  if (form.value.title || (form.value.content && form.value.content.trim() !== '' && form.value.content.trim() !== '<p><br></p>')) {
    ElMessageBox.confirm('确定要取消发布吗？未保存的内容将丢失。', '确认取消', {
      confirmButtonText: '确定',
      cancelButtonText: '继续编辑',
      type: 'warning'
    }).then(() => {
      router.push('/admin/notice')
    })
  } else {
    router.push('/admin/notice')
  }
}

</script>

<style scoped lang="scss">
.publish-form {
  width: 100%;
  
  .el-form-item {
    margin-bottom: 24px;
    
    // 让输入框和编辑器占满可用宽度
    :deep(.el-input),
    :deep(.wang-editor-container) {
      width: 100%;
    }
  }
}
</style>
