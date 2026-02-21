<template>
  <!-- 头部右侧 -->
  <div class="right-header">
    <!-- 通知图标 -->
    <div class="header-icon">
      <div>
        <el-popover
            placement="bottom"
            :width="360"
            trigger="click"
            v-model:visible="noticePopoverVisible"
        >
          <template #reference>
            <el-badge
                :value="noticeStore.unreadCount"
                :max="99"
                :hidden="noticeStore.unreadCount === 0"
                class="header-badge"
            >
              <el-icon>
                <Bell/>
              </el-icon>
            </el-badge>
          </template>
          <NoticeDropdown
              :visible="noticePopoverVisible"
              @close="noticePopoverVisible = false"
              @view-detail="handleViewNoticeDetail"
          />
        </el-popover>
      </div>
      <div>
        <el-icon @click="toggleFullScreen">
          <FullScreen v-if="!isFullscreen"/>
          <Aim v-else/>
        </el-icon>
      </div>
      <div>
        <el-icon @click="openDrawer">
          <Brush/>
        </el-icon>
      </div>
    </div>
    <!-- 用户信息下拉菜单 -->
    <el-dropdown trigger="click" @command="handleCommand">
      <div class="avatar-container">
        <el-avatar :size="32"
                   :src="userStore.userInfo.avatar || '@/assets/img/default_avatar.png'"/>
        <span class="username">{{ userStore.userInfo.nickname || userStore.userInfo.username || '管理员' }}</span>
      </div>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item command="profile">
            <el-icon>
              <User/>
            </el-icon>
            <span>个人信息</span>
          </el-dropdown-item>
          <el-dropdown-item command="changePassword">
            <el-icon>
              <Lock/>
            </el-icon>
            <span>修改密码</span>
          </el-dropdown-item>
          <el-dropdown-item command="logout" divided>
            <el-icon>
              <SwitchButton/>
            </el-icon>
            <span>退出登录</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>

    <!-- 修改密码弹窗 -->
    <el-dialog
        v-model="passwordDialogVisible"
        title="修改密码"
        width="400px"
        :close-on-click-modal="false"
    >
      <el-form
          ref="passwordFormRef"
          :model="passwordForm"
          :rules="passwordRules"
          label-width="100px"
          status-icon
      >
        <el-form-item label="原密码" prop="oldPassword">
          <el-input
              v-model="passwordForm.oldPassword"
              type="password"
              show-password
              placeholder="请输入原密码"
          />
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input
              v-model="passwordForm.newPassword"
              type="password"
              show-password
              placeholder="请输入新密码"
          />
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input
              v-model="passwordForm.confirmPassword"
              type="password"
              show-password
              placeholder="请再次输入新密码"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="passwordDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitPasswordForm(passwordFormRef)">
            确认
          </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>
<script lang="ts" setup>
import {useRouter} from 'vue-router'
import type {FormInstance, FormRules} from 'element-plus'
import {ElMessage, ElMessageBox} from 'element-plus'
import {useUserStore} from '@/stores/user.store'
import {useDictStore} from '@/stores/dict.store'
import {logout as logoutApi} from '@/api/modules/sys/authApi'
import {Aim, Bell, Brush, FullScreen, Lock, SwitchButton, User} from "@element-plus/icons-vue";
import {reactive, ref} from "vue";
import {useGlobalSettingStore} from "@/stores/globalSetting.store";
import {useNoticeStore} from "@/stores/notice.store";
import NoticeDropdown from "./NoticeDropdown.vue";
import type {Notice} from "@/types/notification/notice";
import {resetDynamicRoutes} from "@/router";

const globalSettingStore = useGlobalSettingStore()
const userStore = useUserStore()
const dictStore = useDictStore()
const noticeStore = useNoticeStore()
const router = useRouter()
const isFullscreen = ref(false)

// 通知弹出框状态
const noticePopoverVisible = ref(false)

// 密码修改弹窗相关
const passwordDialogVisible = ref(false)
const passwordFormRef = ref<FormInstance>()
const passwordForm = reactive({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})

// 密码表单验证规则
const passwordRules = reactive<FormRules>({
  oldPassword: [
    {required: true, message: '请输入原密码', trigger: 'blur'},
    {min: 6, message: '密码长度不能少于6个字符', trigger: 'blur'}
  ],
  newPassword: [
    {required: true, message: '请输入新密码', trigger: 'blur'},
    {min: 6, message: '密码长度不能少于6个字符', trigger: 'blur'}
  ],
  confirmPassword: [
    {required: true, message: '请再次输入新密码', trigger: 'blur'},
    {min: 6, message: '密码长度不能少于6个字符', trigger: 'blur'},
    {
      validator: (rule, value, callback) => {
        if (value !== passwordForm.newPassword) {
          callback(new Error('两次输入的密码不一致'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ]
})

// 提交修改密码表单
const submitPasswordForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate((valid) => {
    if (valid) {
      // 这里可以调用API进行密码修改
      // 模拟API调用
      setTimeout(() => {
        ElMessage.success('密码修改成功')
        passwordDialogVisible.value = false
        // 重置表单
        passwordForm.oldPassword = ''
        passwordForm.newPassword = ''
        passwordForm.confirmPassword = ''
      }, 1000)
    }
  })
}

// 处理下拉菜单命令
const handleCommand = (command: string) => {
  switch (command) {
    case 'profile':
      router.push('/admin/profile')
      break
    case 'changePassword':
      passwordDialogVisible.value = true
      break
    case 'logout':
      ElMessageBox.confirm(
          '确定要退出登录吗?',
          '提示',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }
      ).then(async () => {
        try {
          await logoutApi()
        } catch (error) {
          console.error('登出接口调用失败:', error)
        } finally {
          // 清除用户数据
          userStore.clearUserData()
          // 清除字典缓存
          dictStore.clearDicts()
          // 清除通知状态
          noticeStore.resetState()
          // 清除动态路由
          resetDynamicRoutes()
          // 跳转到登录页
          router.push('/admin/login')
        }
      }).catch(() => {
      })
      break
  }
}

// 切换全屏
const toggleFullScreen = () => {
  if (!document.fullscreenElement) {
    document.documentElement.requestFullscreen()
    isFullscreen.value = true
  } else {
    if (document.exitFullscreen) {
      document.exitFullscreen()
      isFullscreen.value = false
    }
  }
}

const openDrawer = () => {
  globalSettingStore.isDrawerOpen = true
}

// 查看通知详情
const handleViewNoticeDetail = (notice: Notice) => {
  // 跳转到通知详情页
  router.push(`/admin/notice/detail/${notice.id}`)
  // 关闭弹出框
  noticePopoverVisible.value = false
}
</script>

<style scoped>
.dialog-footer {
  justify-content: flex-end;
  display: flex;
}
</style>