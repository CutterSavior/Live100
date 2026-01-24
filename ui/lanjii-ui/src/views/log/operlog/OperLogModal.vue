<template>
  <el-dialog
      :model-value="visible"
      @close="$emit('close')"
      title="操作日志详情"
      width="800px"
      :loading="loading"
      :close-on-click-modal="false"
      append-to-body
      destroy-on-close
  >
    <el-descriptions :column="2" border>
      <el-descriptions-item label="操作模块">
        {{ form.title }}
      </el-descriptions-item>
      <el-descriptions-item label="业务类型">
        <DictTag dict-type="BUSINESS_TYPE" :value="form.businessType"/>
      </el-descriptions-item>
      <el-descriptions-item label="方法名称" :span="2">
        {{ form.method }}
      </el-descriptions-item>
      <el-descriptions-item label="请求方式">
        <DictTag dict-type="REQUEST_METHOD" :value="form.requestMethod"/>
      </el-descriptions-item>
      <el-descriptions-item label="操作状态">
        <DictTag dict-type="IS_SUCCESS" :value="form.status"/>
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
import {onMounted, ref} from 'vue';
import DictTag from "@/components/DictTag";
import {getOperLogById} from '@/api/modules/log/operLogApi';
import type {SysOperLog} from '@/types/log/sysOperLog';

const props = defineProps({
  visible: Boolean,
  type: {
    type: String,
    default: 'view'
  },
  id: {
    type: Number,
    required: true
  }
});

const emit = defineEmits(['close']);

const loading = ref(false);

const form = ref<SysOperLog>({
  id: undefined,
  title: '',
  businessType: 0,
  method: '',
  requestMethod: 1,
  operName: '',
  deptName: '',
  operUrl: '',
  operParam: '',
  jsonResult: '',
  status: 1,
  errorMsg: '',
  operTime: '',
  costTime: 0,
  createTime: ''
});

const loadDetail = async () => {
  if (!props.id) return;
  try {
    loading.value = true;
    const res = await getOperLogById(props.id);
    form.value = res.data
  } finally {
    loading.value = false;
  }
};

// 获取耗时样式类
const getCostTimeClass = (costTime?: number) => {
  if (!costTime) return 'cost-time-low'
  if (costTime > 1000) return 'cost-time-high'
  if (costTime > 500) return 'cost-time-medium'
  return 'cost-time-low'
}

onMounted(() => {
  if (props.visible) {
    loadDetail();
  }
});
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

