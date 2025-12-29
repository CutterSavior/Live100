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
        <el-form-item label="知识库标题" prop="title">
          <el-input
              v-model="form.title"
              placeholder="请输入知识库标题"
              maxlength="200"
              show-word-limit
          />
        </el-form-item>

        <el-form-item label="知识库内容" prop="content">
          <WangEditor
              v-model="form.content"
              placeholder="请输入知识库内容..."
              height="400px"
          />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="handleSubmit" :loading="loading">
            {{ isEdit ? '更新知识库' : '发布知识库' }}
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
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useRouter, useRoute } from 'vue-router'
import { createKnowledge, updateKnowledge, getKnowledgeById } from '@/api/modules/ai/knowledgeApi'
import type { AiKnowledge } from '@/types/ai/aiKnowledge'
import WangEditor from '@/components/WangEditor/WangEditor.vue'

const router = useRouter()
const route = useRoute()
const formRef = ref()
const loading = ref(false)

// 判断是否为编辑模式 - 通过 query 参数判断
const isEdit = ref(false)
const knowledgeId = ref<number>()

// 表单数据
const form = ref<AiKnowledge>({
  title: '',
  content: ''
})

// 表单验证规则
const rules = {
  title: [
    { required: true, message: '请输入知识库标题', trigger: 'blur' },
    { min: 1, max: 200, message: '标题长度在 1 到 200 个字符', trigger: 'blur' }
  ],
  content: [
    { required: true, message: '请输入知识库内容', trigger: 'blur' }
  ]
}

/**
 * 初始化页面数据
 */
const initPageData = async () => {
  // 通过路由路径判断是否为编辑模式
  const path = route.path as string
  isEdit.value = path.includes('/edit')

  if (isEdit.value) {
    knowledgeId.value = Number(route.params.id)

    if (knowledgeId.value) {
      await fetchKnowledgeDetail()
    }
  }
}

/**
 * 获取知识库详情（编辑模式）
 */
const fetchKnowledgeDetail = async () => {
  try {
    loading.value = true
    const { data } = await getKnowledgeById(knowledgeId.value!)
    form.value = {
      title: data.title,
      content: data.content
    }
  } catch (error) {
    console.error('获取知识库详情失败:', error)
    ElMessage.error('获取知识库详情失败')
    router.push('/admin/ai/knowledge')
  } finally {
    loading.value = false
  }
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
      ElMessage.error('请输入知识库内容')
      return
    }

    loading.value = true

    if (isEdit.value) {
      // 更新知识库
      await updateKnowledge(knowledgeId.value!, {
        title: form.value.title,
        content: form.value.content
      })
      ElMessage.success('知识库更新成功')
      router.push('/admin/ai/knowledge')
    } else {
      // 新增知识库
      await createKnowledge({
        title: form.value.title,
        content: form.value.content
      })
      ElMessage.success('知识库发布成功')

      // 询问是否继续发布
      ElMessageBox.confirm('知识库发布成功！是否继续发布新知识库？', '发布成功', {
        confirmButtonText: '继续发布',
        cancelButtonText: '返回列表',
        type: 'success'
      }).then(() => {
        handleReset()
      }).catch(() => {
        router.push('/admin/ai/knowledge')
      })
    }

  } catch (error) {
    console.error('提交失败:', error)
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
    ElMessageBox.confirm(
      `确定要取消${isEdit.value ? '编辑' : '发布'}吗？未保存的内容将丢失。`,
      '确认取消',
      {
        confirmButtonText: '确定',
        cancelButtonText: '继续编辑',
        type: 'warning'
      }
    ).then(() => {
      router.push('/admin/ai/knowledge')
    })
  } else {
    router.push('/admin/ai/knowledge')
  }
}

// 页面初始化
onMounted(() => {
  initPageData()
})
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
