<template>
  <div class="login-root">
    <!-- 左侧插画区域 -->
    <div class="illustration-section">
      <div class="bg-circle"></div>
      <div class="bg-circle"></div>
      <div class="bg-circle"></div>
      <div class="illustration-container">
        <!-- 主插画SVG -->
        <img class="illustration" src="/src/assets/svg/illustration.svg" alt="illustration"/>
        <img class="rocket" src="/src/assets/svg/rocket.svg" alt="rocket"/>
        <!-- 数据点动画 -->
        <div class="data-point"></div>
        <div class="data-point"></div>
        <div class="data-point"></div>
        <div class="data-point"></div>
      </div>
      <h2 class="illustration-title">欢迎使用岚迹后台管理系统</h2>
      <p class="illustration-subtitle">高效、安全、可靠的企业级管理平台</p>
    </div>
    <!-- 右侧登录区域 -->
    <div class="login-section">
      <div class="login-header">
        <h1 class="login-title">用户登录</h1>
        <p class="login-subtitle">请输入您的账号和密码</p>
      </div>
      <el-form
          ref="loginFormRef"
          :model="loginForm"
          :rules="loginRules"
          label-position="top"
          size="large"
          @keyup.enter="handleLogin"
          class="login-el-form"
      >
        <el-form-item prop="username">
          <el-input
              v-model="loginForm.username"
              placeholder="请输入用户名"
              :prefix-icon="User"
              class="form-input"
          />
        </el-form-item>
        <el-form-item prop="password">
          <el-input
              v-model="loginForm.password"
              placeholder="请输入密码"
              type="password"
              show-password
              :prefix-icon="Lock"
              class="form-input"
          />
        </el-form-item>
        <el-form-item prop="captchaCode">
          <div class="captcha-container">
            <el-input
                v-model="loginForm.captchaCode"
                placeholder="请输入验证码"
                class="captcha-input"
                maxlength="4"
            />
            <div class="captcha-image" @click="refreshCaptcha" :title="'点击刷新验证码'">
              <img :src="captchaImage" alt="验证码"/>
            </div>
          </div>
        </el-form-item>
        <el-form-item>
          <el-button
              type="primary"
              class="login-button"
              :loading="loading"
              @click="handleLogin"
          >
            登 录
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup lang="ts">
import {nextTick, reactive, ref} from 'vue'
import {useRouter} from 'vue-router'
import type {FormInstance} from 'element-plus'
import {ElMessage} from 'element-plus'
import {Lock, User} from '@element-plus/icons-vue'
import {getCaptcha, login as loginApi} from '@/api/modules/sys/authApi'
import {useUserStore} from '@/stores/user.store'
import {useGlobalSettingStore} from '@/stores/globalSetting.store'

const router = useRouter()
const userStore = useUserStore()
const globalSettingStore = useGlobalSettingStore()
const loading = ref(false)
const loginFormRef = ref<FormInstance>()
const captchaImage = ref('')
const captchaKey = ref('')

const loginForm = ref({
  username: 'admin',
  password: '123456',
  captchaCode: '',
  captchaKey: '',
})

const loginRules = {
  username: [
    {required: true, message: '请输入用户名', trigger: 'blur'},
    {min: 3, max: 20, message: '用户名长度应在3到20个字符之间', trigger: 'blur'}
  ],
  password: [
    {required: true, message: '请输入密码', trigger: 'blur'},
    {min: 6, max: 20, message: '密码长度应在6到20个字符之间', trigger: 'blur'}
  ],
  captchaCode: [
    {required: true, message: '请输入验证码', trigger: 'blur'},
    {len: 4, message: '验证码长度为4位', trigger: 'blur'}
  ]
}

// 生成验证码（调用后端接口）
const generateCaptcha = async () => {
  try {
    const response = await getCaptcha()
    loginForm.value.captchaKey = response.data.captchaKey
    captchaImage.value = response.data.imageBase64
  } catch (error) {
    ElMessage.error('获取验证码失败')
    console.error('获取验证码失败:', error)
  }
}

// 刷新验证码
const refreshCaptcha = async () => {
  await generateCaptcha()
}

// 页面加载时生成验证码
const initCaptcha = async () => {
  await generateCaptcha()
}

const handleLogin = async () => {
  if (!loginFormRef.value) return
  await loginFormRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        const response = await loginApi({
          ...loginForm.value
        })

        const {token, sysUser, menusTree, permissions, displayUuid} = response.data

        // 将数据存储到userStore中
        userStore.setToken(token)
        userStore.setUserInfo(sysUser)
        userStore.setMenus(menusTree)
        userStore.setPermissions(permissions)
        userStore.setDisplayUuid(displayUuid)

        // 登录成功后清空标签页，保留控制台
        globalSettingStore.clearVisitedTabs()

        ElMessage.success('登录成功')
        await nextTick()
        await router.replace('/admin/index')
      } catch (error: any) {
        refreshCaptcha()
        loginForm.value.captchaCode = ''
      } finally {
        loading.value = false
      }
    }
  })
}

// 初始化
initCaptcha()
</script>

<style lang="scss" scoped>

</style>
