<template>
  <div class="main-content">
    <AsyncTable :columns="allColumns"
                :fetch-data="fetchSessions"
                :query-params="{}">
      <template #active="{ row }">
        <el-tag :type="row.active ? 'success' : 'danger'">
          {{ row.active ? '在线' : '离线' }}
        </el-tag>
      </template>
      <template #deviceType="{ row }">
        <el-tag :type="getDeviceTypeTagType(row.deviceType)">
          {{ row.deviceType }}
        </el-tag>
      </template>
      <template #userAgent="{ row }">
        <el-text class="user-agent-text" :title="row.userAgent">
          {{ formatUserAgent(row.userAgent) }}
        </el-text>
      </template>
      <template #action-column="{row}">
        <el-button
            v-permission="'sys:session:kick'"
            v-if="row.active && row.displayUuid !== userStore.displayUuid"
            type="danger"
            link
            :icon="Close"
            @click="handleKickSession(row)"
        >
          踢出
        </el-button>
        <el-text v-else-if="!row.active"></el-text>
        <el-text v-else type="success">当前会话</el-text>
      </template>
    </AsyncTable>
  </div>
</template>

<script setup lang="ts">
import {ref} from 'vue'
import {ElMessage, ElMessageBox} from 'element-plus'
import AsyncTable from '@/components/AsyncTable/AsyncTable.vue'
import * as sessionApi from '@/api/modules/sys/sessionApi'
import {Close} from "@element-plus/icons-vue"
import type {TableColumn} from '@/types/table'
import {useUserStore} from '@/stores/user.store';

const asyncTableRef = ref()
const userStore = useUserStore()

const allColumns: TableColumn[] = [
  {label: '序号', type: 'index', width: '70', align: 'center'},
  {prop: 'displayUuid', label: '会话标识', minWidth: '200', align: 'center'},
  {prop: 'username', label: '用户名', minWidth: '120', align: 'center'},
  {prop: 'active', label: '状态', minWidth: '100', align: 'center'},
  {prop: 'deviceType', label: '设备类型', minWidth: '100', align: 'center'},
  {prop: 'clientIp', label: 'IP地址', minWidth: '120', align: 'center'},
  {prop: 'maskedToken', label: 'Token', minWidth: '200', align: 'center'},
  {prop: 'userAgent', label: '用户代理', minWidth: '200', align: 'center'},
  {prop: 'createTime', label: '创建时间', minWidth: '160', align: 'center'},
  {prop: 'lastAccessTime', label: '最后访问', minWidth: '160', align: 'center'}
]

const fetchSessions = async (params: any) => {
  const res = await sessionApi.getSessionList(params)
  return res.data
}

// 获取设备类型标签类型
const getDeviceTypeTagType = (deviceType: string) => {
  switch (deviceType) {
    case 'PC':
      return 'primary'
    case 'Mobile':
      return 'success'
    case 'Tablet':
      return 'warning'
    case 'Unknown':
      return 'info'
    default:
      return 'info'
  }
}

// 格式化UserAgent显示
const formatUserAgent = (userAgent: string) => {
  if (!userAgent) return ''
  // 提取浏览器和操作系统信息
  const browserMatch = userAgent.match(/(Chrome|Firefox|Safari|Edge|Opera)\/[\d.]+/)
  const osMatch = userAgent.match(/(Windows|Mac|Linux|Android|iOS)/)

  let result = ''
  if (browserMatch) result += browserMatch[0]
  if (osMatch) result += ` (${osMatch[0]})`

  return result || (userAgent.length > 30 ? `${userAgent.substring(0, 30)}...` : userAgent)
}

// 踢出会话
const handleKickSession = (row: any) => {
  ElMessageBox.confirm(
      `确认踢出用户 "${row.username}" 的会话吗？该用户将被迫下线。`,
      '警告',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
  ).then(async () => {
    try {
      await sessionApi.kickSession(row.displayUuid)
      ElMessage.success('踢出成功')
      asyncTableRef.value?.refreshTable()
    } catch (e) {
      ElMessage.error('踢出失败')
    }
  }).catch(() => {
  })
}

</script>

<style scoped>
.user-agent-text {
  font-size: 12px;
}
</style>