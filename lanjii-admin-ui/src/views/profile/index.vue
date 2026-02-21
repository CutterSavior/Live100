<template>
  <div class="profile-layout">
    <!-- 左侧信息展示 -->
    <el-card class="info-card">
      <div class="avatar-container">
        <el-avatar :size="80" :src="userInfo.avatar">
          <img src="@/assets/img/default_avatar.png" alt=""/>
        </el-avatar>
      </div>
      <div class="info-list">
        <div class="info-item">
          <span class="label"><el-icon><User/></el-icon>用户名</span>
          <span class="value">{{ userInfo.username }}</span>
        </div>
        <div class="info-item">
          <span class="label"><el-icon><UserFilled/></el-icon>昵称</span>
          <span class="value">{{ userInfo.nickname }}</span>
        </div>
        <div class="info-item">
          <span class="label"><el-icon><Message/></el-icon>邮箱</span>
          <span class="value">{{ userInfo.email }}</span>
        </div>
        <div class="info-item">
          <span class="label"><el-icon><Iphone/></el-icon>手机号</span>
          <span class="value">{{ userInfo.phone }}</span>
        </div>
        <div class="info-item">
          <span class="label"><el-icon><Monitor/></el-icon>IP地址</span>
          <span class="value">{{ userInfo.lastLoginIp }}</span>
        </div>
        <div class="info-item">
          <span class="label"><el-icon><Clock/></el-icon>最近登录：</span>
          <span class="value">{{ userInfo.lastLoginTime }}</span>
        </div>
      </div>
    </el-card>

    <!-- 右侧编辑表单 -->
    <el-card class="form-card">
      <h3>编辑个人信息</h3>
      <el-form
          ref="formRef"
          :model="editForm"
          :rules="rules"
          label-width="100px"
          class="form"
      >
        <el-form-item label="头像" prop="avatar">
          <el-upload
              class="avatar-uploader"
              action="/api/admin/tool/files/upload"
              accept="image/*"
              :show-file-list="false"
              :headers="uploadHeaders"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload"
          >
            <img v-if="editForm.avatar" :src="editForm.avatar" class="avatar" alt=""/>
            <el-icon v-else class="avatar-uploader-icon">
              <Plus/>
            </el-icon>
          </el-upload>
        </el-form-item>

        <el-form-item label="用户名" prop="username">
          <el-input v-model="userInfo.username" disabled/>
        </el-form-item>

        <el-form-item label="昵称" prop="nickname">
          <el-input v-model="editForm.nickname"/>
        </el-form-item>

        <el-form-item label="邮箱" prop="email">
          <el-input v-model="editForm.email"/>
        </el-form-item>

        <el-form-item label="手机号" prop="phone">
          <el-input v-model="editForm.phone"/>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="submitForm(formRef)">保存修改</el-button>
          <el-button @click="resetForm(formRef)">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>

</template>

<script setup lang="ts">
import {onMounted, ref} from 'vue'
import type {FormInstance, FormRules} from 'element-plus'
import {ElMessage} from 'element-plus'
import {Plus} from '@element-plus/icons-vue'
import type {SysUser} from '@/types/sys/sysUser'
import {useUserStore} from '@/stores/user.store'
import {updateProfile} from '@/api/modules/sys/userApi'

const formRef = ref<FormInstance>()
const userStore = useUserStore()

const userInfo = ref<SysUser>(userStore.userInfo)

const uploadHeaders = {
  Authorization: `Bearer ${userStore.token}`
}

const editForm = ref<SysUser>({
  avatar: undefined,
  nickname: undefined,
  email: undefined,
  phone: undefined,
})

const rules = ref<FormRules>({
  nickname: [{required: true, message: '请输入昵称', trigger: 'blur'}],
  email: [
    {required: true, message: '请输入邮箱地址', trigger: 'blur'},
    {type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur'}
  ],
  phone: [
    {required: true, message: '请输入手机号', trigger: 'blur'},
    {pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur'}
  ]
})

const initEditForm = () => {
  userInfo.value = userStore.userInfo
  editForm.value = {
    avatar: userInfo.value.avatar,
    nickname: userInfo.value.nickname,
    email: userInfo.value.email,
    phone: userInfo.value.phone,
  }
}

const handleAvatarSuccess = (res: any) => {
  editForm.value.avatar = res.data.fullFilePath
  ElMessage.success('头像上传成功')
}

const beforeAvatarUpload = (file: File) => {
  const isJPG = file.type === 'image/jpeg' || file.type === 'image/png'
  const isLt2M = file.size / 1024 / 1024 < 2

  if (!isJPG) {
    ElMessage.error('上传头像图片只能是 JPG 或 PNG 格式!')
  }
  if (!isLt2M) {
    ElMessage.error('上传头像图片大小不能超过 2MB!')
  }
  return isJPG && isLt2M
}

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate(async (valid) => {
    if (valid) {
      await updateProfile(editForm.value)
      userStore.setUserInfo({
        ...userInfo.value,
        ...editForm.value
      })
      userInfo.value = userStore.userInfo
      ElMessage.success('个人信息更新成功')
    }
  })
}

const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  initEditForm()
}

onMounted(() => {
  initEditForm()
})
</script>

<style scoped>

.profile-layout {
  display: flex;
  align-items: flex-start;
}

.info-card {
  width: 350px;
  margin-right: 20px;
}

.form-card {
  flex: 1;
}

.avatar-container {
  text-align: center;
  margin-bottom: 15px;
}

.info-list {
  margin-top: 10px;
}

.info-item {
  display: flex;
  margin-bottom: 10px;
  line-height: 24px;
  font-size: 14px;
}

.label {
  width: 100px;
  flex-shrink: 0;
  display: flex;
  align-items: center;
  gap: 8px;
}

.value {
  flex: 1;
  text-align: right;
}

.avatar-uploader {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  width: 100px;
  height: 100px;
  transition: border-color 0.3s;
}

.avatar-uploader:hover {
  border-color: var(--el-color-primary);
}

.avatar-uploader-icon {
  font-size: 28px;
  color: var(--el-text-color-placeholder);
  width: 100px;
  height: 100px;
  line-height: 100px;
  text-align: center;
}

.avatar {
  width: 100px;
  height: 100px;
  display: block;
}

@media (max-width: 768px) {
  .profile-layout {
    flex-direction: column;
  }

  .info-card {
    width: 100%;
    margin-right: 0;
    margin-bottom: 20px;
  }
}
</style> 