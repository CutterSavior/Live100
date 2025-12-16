<template>
  <el-dialog
    :model-value="visible"
    @update:model-value="$emit('close')"
    title="登录日志详情"
    width="600px"
    :close-on-click-modal="false"
    append-to-body
    destroy-on-close
  >
    <el-descriptions :column="2" border>
      <el-descriptions-item label="用户名">
        {{ form.username }}
      </el-descriptions-item>
      <el-descriptions-item label="IP地址">
        {{ form.ipAddress }}
      </el-descriptions-item>
      <el-descriptions-item label="登录地点">
        {{ form.loginLocation }}
      </el-descriptions-item>
      <el-descriptions-item label="浏览器">
        {{ form.browser }}
      </el-descriptions-item>
      <el-descriptions-item label="操作系统">
        {{ form.os }}
      </el-descriptions-item>
      <el-descriptions-item label="登录类型">
        <el-tag :type="form.loginType === 0 ? 'primary' : 'info'">
          {{ form.loginTypeLabel }}
        </el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="状态">
        <el-tag :type="form.status === 1 ? 'success' : 'danger'">
          {{ form.statusLabel }}
        </el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="提示信息">
        {{ form.msg }}
      </el-descriptions-item>
      <el-descriptions-item label="登录时间" :span="2">
        {{ form.loginTime }}
      </el-descriptions-item>
      <el-descriptions-item label="创建时间" :span="2">
        {{ form.createTime }}
      </el-descriptions-item>
    </el-descriptions>
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
  loginLogData: {
    type: Object,
    default: () => ({})
  }
});

const emit = defineEmits(['close']);

const form = ref({
  id: '',
  username: '',
  ipAddress: '',
  loginLocation: '',
  browser: '',
  os: '',
  loginType: 0,
  loginTypeLabel: '',
  status: 1,
  statusLabel: '',
  msg: '',
  loginTime: '',
  createTime: ''
});

watch(() => props.loginLogData, (val) => {
  if (val && Object.keys(val).length > 0) {
    form.value = { ...form.value, ...val };
  }
}, { immediate: true, deep: true });
</script>

