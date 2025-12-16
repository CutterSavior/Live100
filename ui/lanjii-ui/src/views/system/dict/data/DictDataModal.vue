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
    <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
      <el-form-item label="字典标签" prop="dictLabel">
        <el-input v-model="form.dictLabel" :disabled="type === 'view'" placeholder="请输入字典标签"/>
      </el-form-item>
      <el-form-item label="字典键值" prop="dictValue">
        <el-input-number v-model="form.dictValue" :disabled="type === 'view'" :min="0" placeholder="请输入字典键值"/>
      </el-form-item>
      <el-form-item label="字典排序" prop="sortOrder">
        <el-input-number v-model="form.sortOrder" :disabled="type === 'view'" :min="0" placeholder="请输入排序"/>
      </el-form-item>
      <el-form-item label="是否启用" prop="isEnabled">
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
import * as dictApi from '@/api/modules/sys/dictApi.ts';
import {isEnabledOptions} from "@/constants";
import {getModalTitle} from "@/types/modal.ts";
import type {ResponseData} from "@/api/http.ts";
import type {SysDictData} from "@/types/sys/sysDictData.ts";

const props = defineProps({
  visible: Boolean,
  type: {
    type: String,
    default: 'add' // add, edit, view
  },
  dictData: {
    type: Object,
    default: () => ({})
  },
  dictType: {
    type: String,
    default: ''
  }
});

const emit = defineEmits(['close', 'success']);

const formRef = ref();
const loading = ref(false);
const form = ref<SysDictData>({
  dictLabel: '',
  dictValue: '0',
  sortOrder: 0,
  isEnabled: 1,
  remark: '',
  dictType: props.dictType
});

const rules = {
  dictLabel: [
    {required: true, message: '请输入字典标签', trigger: 'blur'}
  ],
  dictValue: [
    {required: true, message: '请输入字典键值', trigger: 'blur'}
  ],
  sortOrder: [
    {required: true, message: '请输入排序', trigger: 'blur'}
  ]
};

const modalTitle = computed(() => {
  return getModalTitle(props.type as any, '字典数据');
});

const loadDetail = async (id?: number) => {
  if (!id) return;
  try {
    loading.value = true;
    const detail = await dictApi.getDictDataById(id) as ResponseData;
    form.value = detail.data;
  } finally {
    loading.value = false;
  }
}

onMounted(() => {
  if (props.visible && (props.type === 'edit' || props.type === 'view')) {
    loadDetail(props.dictData.id);
  }
})

const handleSubmit = () => {
  formRef.value.validate(async (valid: boolean) => {
    if (!valid) return;

    if (props.type === 'add') {
      await dictApi.createDictData(form.value);
    } else if (props.type === 'edit') {
      await dictApi.updateDictData(form.value.id as number, form.value);
    }
    emit('success');

  });
};
</script>
