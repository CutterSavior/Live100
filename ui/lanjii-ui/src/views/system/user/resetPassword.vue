<template>
  <el-dialog
    :model-value="visible"
    @update:model-value="$emit('update:visible', $event)"
    title="重置密码"
    width="500px"
    append-to-body
    destroy-on-close
    @closed="resetForm"
  >
    <el-form
      ref="formRef"
      :model="form"
      :rules="rules"
      label-width="100px"
    >
      <el-form-item label="用户名称">
        <el-input v-model="form.userName" disabled />
      </el-form-item>
      <el-form-item label="新密码" prop="password">
        <el-input
          v-model="form.password"
          type="password"
          placeholder="请输入新密码"
          show-password
        />
      </el-form-item>
      <el-form-item label="确认密码" prop="confirmPassword">
        <el-input
          v-model="form.confirmPassword"
          type="password"
          placeholder="请确认密码"
          show-password
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="handleCancel">取 消</el-button>
        <el-button type="primary" @click="handleSubmit">确 定</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref, reactive, defineProps, defineEmits, watch } from 'vue';
import { ElMessage } from 'element-plus';
import type { FormInstance, FormRules } from 'element-plus';

const props = defineProps({
  visible: {
    type: Boolean,
    default: false
  },
  userData: {
    type: Object,
    default: () => ({})
  }
});

const emit = defineEmits(['update:visible', 'success', 'cancel']);

// 表单引用
const formRef = ref<FormInstance>();

// 表单数据
const form = ref({
  userId: undefined,
  userName: '',
  password: '',
  confirmPassword: ''
});

// 表单验证规则
const rules = reactive<FormRules>({
  password: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于6个字符', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请再次输入密码', trigger: 'blur' },
    { 
      validator: (rule, value, callback) => {
        if (value !== form.value.password) {
          callback(new Error('两次输入的密码不一致'));
        } else {
          callback();
        }
      }, 
      trigger: 'blur' 
    }
  ]
});

// 监听用户数据变化
watch(() => props.userData, (newValue: any) => {
  if (newValue) {
    form.value.userId = newValue.userId;
    form.value.userName = newValue.userName;
  }
}, { immediate: true });

// 取消
const handleCancel = () => {
  emit('update:visible', false);
  emit('cancel');
};

// 重置表单
const resetForm = () => {
  form.value = {
    userId: undefined,
    userName: '',
    password: '',
    confirmPassword: ''
  };
  formRef.value?.resetFields();
};

// 提交表单
const handleSubmit = async () => {
  if (!formRef.value) return;
  
  await formRef.value.validate((valid) => {
    if (valid) {
      // 调用API提交表单
      setTimeout(() => {
        ElMessage.success('密码重置成功');
        emit('update:visible', false);
        emit('success');
      }, 300);
    }
  });
};
</script>

<style scoped>
.dialog-footer {
  display: flex;
  justify-content: flex-end;
}
</style> 