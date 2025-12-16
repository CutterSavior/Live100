<template>
  <div class="main-content">
    <AsyncTable
        ref="noticeTableRef"
        :columns="allColumns"
        :search-items="searchItems"
        :fetch-data="fetchNotices"
    >
      <!-- 状态标签 -->
      <template #readStatus="{ row }">
        <el-tag :type="row.readStatus === 0 ? 'danger' : 'info'">
          {{ row.readStatus === 0 ? '未读' : '已读' }}
        </el-tag>
      </template>

      <template #title="{row}">
        <el-link type="primary" @click="handleViewDetail(row)">{{ row.title }}</el-link>
      </template>
    </AsyncTable>

  </div>
</template>

<script setup lang="ts">
import {ref} from 'vue'
import AsyncTable from '@/components/AsyncTable/AsyncTable.vue'
import type {Notice} from '@/types/notification/notice'
import {NOTICE_STATUS_OPTIONS} from '@/types/notification/notice'
import type {TableColumn} from '@/types/table'
import type {SearchItem} from '@/types/search'
import {useRouter} from 'vue-router'
import {getNoticeList} from "@/api";

const router = useRouter()
const noticeTableRef = ref()

const allColumns: TableColumn[] = [
  {label: '序号', type: 'index', width: '70', align: 'center'},
  {prop: 'title', label: '标题', minWidth: '200', align: 'left'},
  {prop: 'readStatus', label: '状态', minWidth: '100', align: 'center'},
  {prop: 'publisherName', label: '发布人', minWidth: '120', align: 'center'},
  {prop: 'publishTime', label: '发布时间', minWidth: '160', align: 'center'},
  {prop: 'readTime', label: '阅读时间', minWidth: '160', align: 'center'}
]

const searchItems: SearchItem[] = [
  {
    field: 'keyword',
    label: '关键词',
    type: 'input',
    placeholder: '请输入标题或内容关键词'
  },
  {
    field: 'readStatus',
    label: '状态',
    type: 'select',
    clearable: true,
    options: NOTICE_STATUS_OPTIONS
  }
]

/**
 * 获取通知列表
 */
const fetchNotices = async (params: any) => {
  try {
    const {data} = await getNoticeList(params)
    return data
  } catch (error) {
    console.error('获取通知列表失败:', error)
    throw error
  }
}

/**
 * 查看通知详情
 */
const handleViewDetail = (row: Notice) => {
  router.push(`/admin/notice/detail/${row.id}`)
}

/**
 * 刷新列表
 */
const refreshList = () => {
  noticeTableRef.value?.refreshTable()
}
</script>

<style scoped lang="scss">

</style>
