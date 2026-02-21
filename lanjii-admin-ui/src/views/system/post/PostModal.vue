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
      <el-form-item label="岗位编码" prop="postCode">
        <el-input v-model="form.postCode" :disabled="type === 'view'" placeholder="请输入岗位编码"/>
      </el-form-item>
      <el-form-item label="岗位名称" prop="postName">
        <el-input v-model="form.postName" :disabled="type === 'view'" placeholder="请输入岗位名称"/>
      </el-form-item>
      <el-form-item label="显示顺序" prop="sortOrder">
        <el-input-number v-model="form.sortOrder" :disabled="type === 'view'" :min="0" placeholder="请输入显示顺序"
                         style="width: 100%"/>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <DictRadioGroup v-model="form.status" dict-type="IS_ENABLED" :disabled="type === 'view'"/>
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
import * as postApi from '@/api/modules/sys/postApi';
import {getModalTitle} from '@/types/modal';
import type {ResponseData} from '@/api/http.ts';

const props = defineProps({
  visible: Boolean,
  type: {
    type: String,
    default: 'add' // add, edit, view
  },
  postData: {
    type: Object,
    default: () => ({})
  }
});
const emit = defineEmits(['close', 'success']);

const formRef = ref();
const loading = ref(false);
const form = ref({
  id: undefined as number | undefined,
  postCode: '',
  postName: '',
  sortOrder: 0,
  status: 1,
  remark: ''
});

const rules = {
  postCode: [
    {required: true, message: '请输入岗位编码', trigger: 'blur'}
  ],
  postName: [
    {required: true, message: '请输入岗位名称', trigger: 'blur'}
  ]
};

const modalTitle = computed(() => {
  return getModalTitle(props.type as any, '岗位');
});

const loadDetail = async (id?: number) => {
  if (!id) return;
  try {
    loading.value = true;
    const detail = await postApi.getPostById(id) as ResponseData;
    form.value = detail.data;
  } finally {
    loading.value = false;
  }
}

onMounted(() => {
  if (props.visible && (props.type === 'edit' || props.type === 'view')) {
    loadDetail(props.postData.id);
  }
})

const handleSubmit = () => {
  formRef.value.validate(async (valid: boolean) => {
    if (!valid) return;

    if (props.type === 'add') {
      await postApi.createPost(form.value);
    } else if (props.type === 'edit') {
      await postApi.updatePost(form.value.id as number, form.value);
    }
    emit('success');
  });
};
</script> 