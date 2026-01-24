<template>
  <el-dialog
      :model-value="visible"
      @close="$emit('close')"
      title="登录日志详情"
      width="600px"
      :loading="loading"
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
        <DictTag dict-type="LOGIN_TYPE" :value="form.loginType"/>
      </el-descriptions-item>
      <el-descriptions-item label="状态">
        <DictTag dict-type="LOGIN_STATUS" :value="form.status"/>
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
import {onMounted, ref} from 'vue';
import {getLoginLogById} from '@/api/modules/log/loginLogApi';
import type {SysLoginLog} from '@/types/log/sysLoginLog';

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

const form = ref<SysLoginLog>({
  id: undefined,
  username: '',
  ipAddress: '',
  loginLocation: '',
  browser: '',
  os: '',
  loginType: 0,
  status: 1,
  msg: '',
  loginTime: '',
  createTime: ''
});

const loadDetail = async () => {
  if (!props.id) return;
  try {
    loading.value = true;
    const res = await getLoginLogById(props.id);
    form.value = res.data;
  } finally {
    loading.value = false;
  }
}

onMounted(() => {
  if (props.visible) {
    loadDetail();
  }
});
</script>

