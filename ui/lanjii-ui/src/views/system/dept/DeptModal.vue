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
      <el-form-item label="部门名称" prop="deptName">
        <el-input v-model="form.deptName" :disabled="type === 'view'" placeholder="请输入部门名称"/>
      </el-form-item>
      <el-form-item label="上级部门" prop="parentId">
        <el-tree-select
            v-model="form.parentId"
            :data="treeOptionsWithRoot"
            :props="{ label: 'deptName', value: 'id', children: 'children', disabled: (data:any) => data.id === form.id }"
            :disabled="type === 'view'||type === 'edit'"
            placeholder="请选择上级部门"
            clearable
            filterable
            check-strictly
            node-key="id"
            :default-checked-keys="[form.id]"
            style="width: 100%"
        />
      </el-form-item>
      <el-form-item label="负责人" prop="leader">
        <el-input v-model="form.leader" :disabled="type === 'view'" placeholder="请输入负责人"/>
      </el-form-item>
      <el-form-item label="联系电话" prop="phone">
        <el-input v-model="form.phone" :disabled="type === 'view'" placeholder="请输入联系电话"/>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="form.email" :disabled="type === 'view'" placeholder="请输入邮箱"/>
      </el-form-item>
      <el-form-item label="显示顺序" prop="sortOrder">
        <el-input-number v-model="form.sortOrder" :disabled="type === 'view'" :min="0" placeholder="请输入显示顺序"
                         style="width: 100%"/>
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
import {isEnabledOptions} from '@/constants';
import type {SysDept} from "@/types/sys/sysDept.ts";
import * as deptApi from '@/api/modules/sys/deptApi';
import {getModalTitle} from '@/types/modal';
import type {ResponseData} from '@/api/http.ts';

const props = defineProps({
  visible: Boolean,
  type: {
    type: String,
    default: 'add' // add, edit, view
  },
  deptTreeOptions: {
    type: Array,
  },
  deptData: {
    type: Object,
    default: () => ({})
  }
});
const emit = defineEmits(['close', 'success']);

const formRef = ref();
const loading = ref(false);
const form = ref<SysDept>({
  id: undefined as number | undefined,
  deptName: '',
  parentId: undefined as number | undefined,
  ancestors: '',
  sortOrder: 0,
  leader: '',
  phone: '',
  email: '',
  isEnabled: 1,
  remark: ''
});

const rules = {
  deptName: [
    {required: true, message: '请输入部门名称', trigger: 'blur'}
  ],
  parentId: [
    {required: true, message: '请选择上级部门', trigger: 'change'}
  ],
  leader: [
    {required: true, message: '请输入负责人', trigger: 'blur'}
  ],
  phone: [
    {required: true, message: '请输入联系电话', trigger: 'blur'}
  ],
  sortOrder: [
    {required: true, message: '请输入显示顺序', trigger: 'blur'}
  ],
  isEnabled: [
    {required: true, message: '请选择是否启用', trigger: 'change'}
  ]
};

const modalTitle = computed(() => {
  return getModalTitle(props.type as any, '部门');
});

const loadDetail = async (id?: number) => {
  if (!id) return;
  try {
    loading.value = true;
    const detail = await deptApi.getDeptById(id) as ResponseData;
    form.value = detail.data;
  } finally {
    loading.value = false;
  }
}

onMounted(() => {
  if (props.visible && (props.type === 'edit' || props.type === 'view')) {
    loadDetail(props.deptData.id);
  }
})

const treeOptionsWithRoot = computed(() => {
  const root = {id: 0, deptName: '根节点', children: props.deptTreeOptions || []};
  return [root];
});

const handleSubmit = () => {
  formRef.value.validate(async (valid: boolean) => {
    if (!valid) return;

    if (props.type === 'add') {
      await deptApi.createDept(form.value);
    } else if (props.type === 'edit') {
      await deptApi.updateDept(form.value.id as number, form.value);
    }
    emit('success');

  });
};
</script>