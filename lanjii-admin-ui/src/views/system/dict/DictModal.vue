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
      <el-form-item label="类型名称" prop="typeName">
        <el-input v-model="form.typeName" :disabled="type === 'view'" placeholder="请输入类型名称"/>
      </el-form-item>
      <el-form-item label="类型编码" prop="typeCode">
        <el-input v-model="form.typeCode" :disabled="type === 'view' || type === 'edit'" placeholder="请输入类型编码"/>
      </el-form-item>
      <el-form-item label="是否启用" prop="isEnabled">
        <DictRadioGroup v-model="form.isEnabled" dict-type="IS_ENABLED" :disabled="type === 'view'" />
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
import type {SysDictType} from "@/types/sys/sysDictType";
import * as dictApi from '@/api/modules/sys/dictApi';
import type {ResponseData} from '@/api/http.ts';
import {getModalTitle} from '@/types/modal';

const props = defineProps({
  visible: Boolean,
  type: {
    type: String,
    default: 'add' // add, edit, view
  },
  dictType: {
    type: Object,
    default: () => ({})
  }
});
const emit = defineEmits(['close', 'success']);

const formRef = ref();
const loading = ref(false);
const form = ref<SysDictType>({
  id: undefined as number | undefined,
  typeName: '',
  typeCode: '',
  isEnabled: 1,
  remark: ''
});

const rules = {
  typeName: [
    {required: true, message: '请输入类型名称', trigger: 'blur'}
  ],
  typeCode: [
    {required: true, message: '请输入类型编码', trigger: 'blur'}
  ]
};

const modalTitle = computed(() => {
  return getModalTitle(props.type as any, '字典类型');
});

const loadDetail = async (id?: number) => {
  if (!id) return;
  try {
    loading.value = true;
    const detail = await dictApi.getDictTypeById(id) as ResponseData;
    form.value = detail.data;
  } finally {
    loading.value = false;
  }
}

onMounted(() => {
  if (props.visible && (props.type === 'edit' || props.type === 'view')) {
    loadDetail(props.dictType.id);
  }
})

const handleSubmit = () => {
  formRef.value.validate(async (valid: boolean) => {
    if (!valid) return;

    if (props.type === 'add') {
      await dictApi.createDictType(form.value);
    } else if (props.type === 'edit') {
      await dictApi.updateDictType(form.value.id as number, form.value as any);
    }
    emit('success');
  });
};
</script> 