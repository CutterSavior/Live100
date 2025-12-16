<template>
  <el-dialog
    :model-value="visible"
    @update:model-value="$emit('close')"
    title="操作日志详情"
    width="800px"
    :close-on-click-modal="false"
    append-to-body
    destroy-on-close
  >
    <el-descriptions :column="2" border>
      <el-descriptions-item label="操作模块">
        {{ form.title }}
      </el-descriptions-item>
      <el-descriptions-item label="业务类型">
        <el-tag :type="getBusinessTypeTagType(form.businessType)">
          {{ form.businessTypeLabel }}
        </el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="方法名称" :span="2">
        {{ form.method }}
      </el-descriptions-item>
      <el-descriptions-item label="请求方式">
        <el-tag :type="getRequestMethodTagType(form.requestMethod)">
          {{ form.requestMethod }}
        </el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="操作状态">
        <el-tag :type="form.status === 1 ? 'success' : 'danger'">
          {{ form.statusLabel }}
        </el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="操作人员">
        {{ form.operName }}
      </el-descriptions-item>
      <el-descriptions-item label="部门名称">
        {{ form.deptName }}
      </el-descriptions-item>
      <el-descriptions-item label="请求URL" :span="2">
        {{ form.operUrl }}
      </el-descriptions-item>
      <el-descriptions-item label="主机地址">
        {{ form.operIp }}
      </el-descriptions-item>
      <el-descriptions-item label="操作地点">
        {{ form.operLocation }}
      </el-descriptions-item>
      <el-descriptions-item label="操作时间">
        {{ form.operTime }}
      </el-descriptions-item>
      <el-descriptions-item label="耗时">
        <span :class="getCostTimeClass(form.costTime)">
          {{ form.costTime }}ms
        </span>
      </el-descriptions-item>
      <el-descriptions-item label="创建时间" :span="2">
        {{ form.createTime }}
      </el-descriptions-item>
    </el-descriptions>
    
    <el-divider content-position="left">请求参数</el-divider>
    <el-input
      v-model="form.operParam"
      type="textarea"
      :rows="4"
      readonly
      placeholder="无请求参数"
    />
    
    <el-divider content-position="left">返回结果</el-divider>
    <el-input
      v-model="form.jsonResult"
      type="textarea"
      :rows="4"
      readonly
      placeholder="无返回结果"
    />
    
    <el-divider v-if="form.errorMsg" content-position="left">错误信息</el-divider>
    <el-input
      v-if="form.errorMsg"
      v-model="form.errorMsg"
      type="textarea"
      :rows="3"
      readonly
      placeholder="无错误信息"
    />
    
    <template #footer>
      <el-button @click="$emit('close')">关闭</el-button>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue';

const props = defineProps({
  visible: Boolean,
  type: {
    type: String,
    default: 'view'
  },
  operLogData: {
    type: Object,
    default: () => ({})
  }
});

const emit = defineEmits(['close']);

const form = ref({
  id: '',
  title: '',
  businessType: 0,
  businessTypeLabel: '',
  method: '',
  requestMethod: '',
  operName: '',
  deptName: '',
  operUrl: '',
  operIp: '',
  operLocation: '',
  operParam: '',
  jsonResult: '',
  status: 1,
  statusLabel: '',
  errorMsg: '',
  operTime: '',
  costTime: 0,
  createTime: ''
});

// 获取业务类型标签类型
const getBusinessTypeTagType = (businessType: number) => {
  switch (businessType) {
    case 0: return 'success'  // 新增
    case 1: return 'warning'  // 修改
    case 2: return 'danger'   // 删除
    default: return 'info'
  }
}

// 获取请求方式标签类型
const getRequestMethodTagType = (requestMethod: string) => {
  switch (requestMethod) {
    case 'GET': return 'success'
    case 'POST': return 'primary'
    case 'PUT': return 'warning'
    case 'DELETE': return 'danger'
    default: return 'info'
  }
}

// 获取耗时样式类
const getCostTimeClass = (costTime: number) => {
  if (costTime > 1000) return 'cost-time-high'
  if (costTime > 500) return 'cost-time-medium'
  return 'cost-time-low'
}

watch(() => props.operLogData, (val) => {
  if (val && Object.keys(val).length > 0) {
    form.value = { ...form.value, ...val };
  }
}, { immediate: true, deep: true });
</script>

<style scoped>
.cost-time-low {
  color: #67c23a;
}

.cost-time-medium {
  color: #e6a23c;
}

.cost-time-high {
  color: #f56c6c;
}
</style>

