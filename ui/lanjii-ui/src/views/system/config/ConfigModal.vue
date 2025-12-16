<template>
  <el-dialog
      :model-value="visible"
      @close="$emit('close')"
      :title="modalTitle"
      width="500px"
      :close-on-click-modal="false"
      append-to-body
      destroy-on-close
  >
    <el-form ref="formRef" :model="form" :rules="rules" label-width="100px" v-loading="loading">
      <el-form-item label="配置名称" prop="configName">
        <el-input v-model="form.configName" :disabled="type === 'view'" placeholder="请输入配置名称"/>
      </el-form-item>
      <el-form-item label="配置键名" prop="configKey">
        <el-input v-model="form.configKey" :disabled="type === 'view'" placeholder="请输入配置键名"/>
      </el-form-item>
      <el-form-item label="配置键值" prop="configValue">
        <el-input v-model="form.configValue" :disabled="type === 'view'" placeholder="请输入配置键值"/>
      </el-form-item>
      <el-form-item label="配置类型" prop="configType">
        <el-radio-group v-model="form.configType" :disabled="type === 'view'">
          <el-radio :label="1">系统配置</el-radio>
          <el-radio :label="2">业务配置</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="状态" prop="isEnabled">
        <el-radio-group v-model="form.isEnabled" :disabled="type === 'view'">
          <el-radio v-for="item in isEnabledOptions" :key="item.value" :label="item.value">{{ item.label }}</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="form.remark" :disabled="type === 'view'" type="textarea" placeholder="请输入备注"/>
      </el-form-item>
      <el-form-item v-if="type !== 'view'">
        <el-button type="primary" @click="handleSubmit">确 定</el-button>
        <el-button @click="$emit('close')">取 消</el-button>
      </el-form-item>
      <el-form-item v-else>
        <el-button @click="$emit('close')">关闭</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script setup lang="ts">
import {computed, onMounted, ref} from 'vue';
import type {SysConfig} from '@/types/sys/sysConfig.ts';
import * as configApi from '@/api/modules/sys/configApi.ts';
import {isEnabledOptions} from '@/constants';
import type {ResponseData} from "@/api/http.ts";
import {getModalTitle} from '@/types/modal';

const props = defineProps({
  visible: Boolean,
  type: {
    type: String,
    default: 'add' // add, edit, view
  },
  configData: {
    type: Object,
    default: () => ({})
  }
});
const emit = defineEmits(['close', 'success']);

const formRef = ref();
const loading = ref(false);
const form = ref<SysConfig>({
      id: undefined,
      configName: '',
      configKey: '',
      configValue: '',
      configType: 1,
      isEnabled: 1,
      remark: ''
    }
);

const rules = {
  configName: [
    {required: true, message: '请输入配置名称', trigger: 'blur'}
  ],
  configKey: [
    {required: true, message: '请输入配置键名', trigger: 'blur'}
  ],
  configValue: [
    {required: true, message: '请输入配置键值', trigger: 'blur'}
  ]
};

const modalTitle = computed(() => {
  return getModalTitle(props.type as any, '系统配置');
});

const loadDetail = async (id?: number) => {
  if (!id) return;
  try {
    loading.value = true;
    const detail = await configApi.getConfigById(id) as ResponseData;
    form.value = detail.data;
  } finally {
    loading.value = false;
  }
}

onMounted(() => {
  if (props.visible && (props.type === 'edit' || props.type === 'view')) {
    loadDetail(props.configData.id);
  }
})

const handleSubmit = () => {
  formRef.value.validate(async (valid: boolean) => {
    if (!valid) return;

    if (props.type === 'add') {
      await configApi.createConfig(form.value);
    } else if (props.type === 'edit') {
      await configApi.updateConfig(form.value.id as number, form.value);
    }
    emit('success');

  });
};
</script> 