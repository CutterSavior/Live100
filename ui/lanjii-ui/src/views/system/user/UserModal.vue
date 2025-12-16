<template>
  <el-dialog
      :model-value="visible"
      @close="$emit('close')"
      :title="modalTitle"
      width="600px"
      :close-on-click-modal="false"
      append-to-body
      destroy-on-close
  >
    <el-form ref="formRef" :model="form" :rules="rules" label-width="100px" v-loading="loading">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="form.username" :disabled="type === 'view' || type === 'edit'" placeholder="请输入用户名"/>
      </el-form-item>
      <el-form-item label="所属角色" prop="roleIds">
        <el-select v-model="form.roleIds" :disabled="type === 'view'" multiple filterable placeholder="请选择角色">
          <el-option v-for="role in props.roleOptions as any[]" :key="role.id" :label="role.name" :value="role.id"/>
        </el-select>
      </el-form-item>
      <el-form-item label="所属岗位" prop="postIds">
        <el-select v-model="form.postIds" :disabled="type === 'view'" multiple filterable placeholder="请选择岗位">
          <el-option v-for="post in props.postOptions as any[]" :key="post.id" :label="post.postName" :value="post.id"/>
        </el-select>
      </el-form-item>
      <el-form-item label="所属部门" prop="deptId">
        <el-tree-select
            v-model="form.deptId"
            :data="props.deptTreeOptions"
            :props="{ label: 'deptName', value: 'id', children: 'children' }"
            :disabled="type === 'view'"
            placeholder="请选择部门"
            clearable
            filterable
            check-strictly
        />
      </el-form-item>
      <el-form-item label="昵称" prop="nickname">
        <el-input v-model="form.nickname" :disabled="type === 'view'" placeholder="请输入昵称"/>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="form.email" :disabled="type === 'view'" placeholder="请输入邮箱"/>
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input v-model="form.phone" :disabled="type === 'view'" placeholder="请输入手机号"/>
      </el-form-item>
      <el-form-item label="是否启用" prop="isEnabled">
        <el-radio-group v-model="form.isEnabled" :disabled="type === 'view' || (type === 'edit' && form.id === 1)">
          <el-radio v-for="item in isEnabledOptions" :key="item.value" :label="item.value">{{ item.label }}</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item v-if="type !== 'view'">
        <el-button type="primary" :loading="submitLoading" :disabled="submitLoading" @click="handleSubmit">确 定</el-button>
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
import * as userApi from '@/api/modules/sys/userApi';
import {getModalTitle} from '@/types/modal';
import type {ResponseData} from '@/api/http.ts';
import type {SysUser} from "@/types/sys/sysUser.ts";
import {useLoading} from "@/composables/useLoading.ts";

// 扩展表单接口，包含角色和岗位ID数组
interface UserFormData extends SysUser {
  roleIds?: number[]
  postIds?: number[]
}

const props = defineProps({
  visible: Boolean,
  type: {
    type: String,
    default: 'add' // add, edit, view
  },
  userData: {
    type: Object,
    default: () => ({})
  },
  roleOptions: {
    type: Array,
    default: () => []
  },
  deptTreeOptions: {
    type: Array,
    default: () => []
  },
  postOptions: {
    type: Array,
    default: () => []
  }
});
const emit = defineEmits(['close', 'success']);

const formRef = ref();
const loading = ref(false);

// 提交按钮 loading 状态
const { loading: submitLoading, withLoading: withSubmitLoading } = useLoading();
const form = ref<UserFormData>({
  id: undefined as number | undefined,
  username: '',
  nickname: '',
  email: '',
  phone: '',
  avatar: '',
  isEnabled: 1,
  deptId: undefined as number | undefined,
  isAdmin: 0,
  roleIds: [],
  postIds: []
});

const rules = {
  username: [
    {required: true, message: '请输入用户名', trigger: 'blur'},
    {min: 2, max: 20, message: '用户名长度必须介于 2 和 20 之间', trigger: 'blur'}
  ],
  nickname: [
    {required: true, message: '请输入昵称', trigger: 'blur'}
  ],
  roleIds: [
    {required: true, message: '请选择角色', trigger: 'change'}
  ],
  deptId: [
    {required: true, message: '请选择部门', trigger: 'change'}
  ],
  email: [
    {pattern: /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/, message: '请输入正确的邮箱地址', trigger: 'blur'}
  ],
  phone: [
    {pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur'}
  ]
};

const modalTitle = computed(() => {
  return getModalTitle(props.type as any, '用户');
});

const loadDetail = async (id?: number) => {
  if (!id) return;
  try {
    loading.value = true;
    const detail = await userApi.getUserById(id) as ResponseData;
    form.value = detail.data;
  } finally {
    loading.value = false;
  }
}

onMounted(() => {
  if (props.visible && (props.type === 'edit' || props.type === 'view')) {
    loadDetail(props.userData.id);
  }
  if (props.type === 'add') {
    form.value.deptId = props.userData.deptId;
  }
})

const handleSubmit = () => {
  formRef.value.validate(async (valid: boolean) => {
    if (!valid) return;
    await withSubmitLoading(async () => {
      if (props.type === 'add') {
        await userApi.createUser(form.value);
      } else if (props.type === 'edit') {
        await userApi.updateUser(form.value.id as number, form.value);
      }
      emit('success');
    });
  });
};
</script>