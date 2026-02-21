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
      <el-form-item label="角色名称" prop="name">
        <el-input v-model="form.name" :disabled="type === 'view'" placeholder="请输入角色名称"/>
      </el-form-item>
      <el-form-item label="角色编码" prop="code">
        <el-input v-model="form.code" :disabled="type === 'view'" placeholder="请输入角色编码"/>
      </el-form-item>
      <el-form-item label="是否启用" prop="isEnabled">
        <DictRadioGroup v-model="form.isEnabled" dict-type="IS_ENABLED" :disabled="type === 'view'"/>
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
import * as roleApi from '@/api/modules/sys/roleApi';
import {getModalTitle} from '@/types/modal';
import type {ResponseData} from '@/api/http.ts';

const props = defineProps({
  visible: Boolean,
  type: {
    type: String,
    default: 'add' // add, edit, view
  },
  roleData: {
    type: Object,
    default: () => ({})
  }
});
const emit = defineEmits(['close', 'success']);

const formRef = ref();
const loading = ref(false);
const form = ref({
  id: undefined as number | undefined,
  name: '',
  code: '',
  sortOrder: 0,
  isEnabled: 1,
  remark: ''
});

const rules = {
  name: [
    {required: true, message: '请输入角色名称', trigger: 'blur'}
  ],
  code: [
    {required: true, message: '请输入角色编码', trigger: 'blur'}
  ],
  isEnabled: [
    {required: true, message: '请选择是否启用', trigger: 'change'}
  ]
};

const modalTitle = computed(() => {
  return getModalTitle(props.type as any, '角色');
});

const loadDetail = async (id?: number) => {
  if (!id) return;
  try {
    loading.value = true;
    const detail = await roleApi.getRoleById(id) as ResponseData;
    form.value = detail.data;
  } finally {
    loading.value = false;
  }
}

onMounted(() => {
  if (props.visible && (props.type === 'edit' || props.type === 'view')) {
    loadDetail(props.roleData.id);
  }
})

const handleSubmit = () => {
  formRef.value.validate(async (valid: boolean) => {
    if (!valid) return;

    if (props.type === 'add') {
      await roleApi.createRole(form.value);
    } else if (props.type === 'edit') {
      await roleApi.updateRole(form.value.id as number, form.value);
    }
    emit('success');
  });
};
</script> 