<template>
  <div class="main-content">
    <AsyncTable
        ref="knowledgeTableRef"
        :columns="allColumns"
        :search-items="searchItems"
        :fetch-data="fetchKnowledgeList"
    >
      <template #toolbar>
        <el-button v-permission="'ai:knowledge:save'" type="primary" :icon="Plus" @click="goTo('add')">新增
        </el-button>
      </template>

      <!-- 标题链接 -->
      <template #title="{ row }">
        <el-link type="primary" @click="handleViewDetail(row)">{{ row.title }}</el-link>
      </template>

      <!-- 内容预览 -->
      <template #content="{ row }">
        <span>{{ row.content && row.content.length > 50 ? row.content.substring(0, 50) + '...' : row.content }}</span>
      </template>

      <!-- 操作按钮 -->
      <template #actions="{ row }">
        <el-button
            type="primary"
            size="small"
            @click="handleEdit(row)"
            v-if="checkPermission('ai:knowledge:update')"
        >
          编辑
        </el-button>
        <el-button
            type="danger"
            size="small"
            @click="handleDelete(row)"
            v-if="checkPermission('ai:knowledge:delete')"
        >
          删除
        </el-button>
      </template>
    </AsyncTable>
  </div>
</template>

<script setup lang="ts">
import {ref} from 'vue'
import {ElMessage, ElMessageBox} from 'element-plus'
import {useRouter} from 'vue-router'
import AsyncTable from '@/components/AsyncTable/AsyncTable.vue'
import type {AiKnowledge} from '@/types/ai/aiKnowledge'
import type {TableColumn} from '@/types/table'
import type {SearchItem} from '@/types/search'
import {deleteKnowledge, getKnowledgeList} from '@/api/modules/ai/knowledgeApi'
import {usePermission} from '@/utils/permission'
import {Plus} from "@element-plus/icons-vue";
import type {ModalType} from "@/types/modal.ts";

const router = useRouter()
const knowledgeTableRef = ref()

// 权限检查
const {hasPermission} = usePermission()
const checkPermission = hasPermission

// 表格列配置
const allColumns: TableColumn[] = [
  {label: '序号', type: 'index', width: '70', align: 'center'},
  {prop: 'title', label: '标题', minWidth: '200', align: 'left'},
  {prop: 'content', label: '内容', minWidth: '300', align: 'left'},
  {prop: 'createBy', label: '创建人', minWidth: '120', align: 'center'},
  {prop: 'createTime', label: '创建时间', minWidth: '160', align: 'center'},
  {prop: 'actions', label: '操作', width: '150', align: 'center', fixed: 'right'}
]

// 搜索配置
const searchItems: SearchItem[] = [
  {
    field: 'title',
    label: '标题',
    type: 'input',
    placeholder: '请输入知识库标题'
  }
]

const goTo = (type: ModalType) => {
  if (type == 'add') {
    router.push({path: '/admin/ai/knowledge/save'})
  }
}

/**
 * 获取知识库列表
 */
const fetchKnowledgeList = async (params: any) => {
  try {
    const {data} = await getKnowledgeList(params)
    return data
  } catch (error) {
    console.error('获取知识库列表失败:', error)
    throw error
  }
}

/**
 * 查看知识库详情
 */
const handleViewDetail = (row: AiKnowledge) => {
  router.push(`/admin/ai/knowledge/detail/${row.id}`)
}

/**
 * 编辑知识库
 */
const handleEdit = (row: AiKnowledge) => {
  router.push({path: `/admin/ai/knowledge/edit/${row.id}`})
}

/**
 * 删除知识库
 */
const handleDelete = async (row: AiKnowledge) => {
  try {
    await ElMessageBox.confirm(`确定要删除知识库"${row.title}"吗？`, '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })

    await deleteKnowledge(row.id!)
    ElMessage.success('删除成功')
    refreshList()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除失败:', error)
    }
  }
}

/**
 * 刷新列表
 */
const refreshList = () => {
  knowledgeTableRef.value?.refreshTable()
}
</script>

<style scoped lang="scss">

</style>
