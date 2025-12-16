<template>
  <el-scrollbar>
    <el-menu
        :default-active="activeMenu"
        :collapse="globalSettingStore.layoutMode!= 'transverse'&&globalSettingStore.isCollapse"
        :mode="mode"
        unique-opened
        :collapse-transition="false"
        @select="handleMenuSelect"
    >
      <!-- 递归渲染菜单 -->
      <template v-for="menu in menuItems" :key="menu.id">
        <!-- 带子菜单的项 -->
        <el-sub-menu v-if="menu.children && menu.children.length && menu.isVisible === 1" :index="String(menu.id)">
          <template #title>
            <el-icon v-if="menu.icon">
              <component :is="menu.icon"/>
            </el-icon>
            <span>{{ menu.name }}</span>
          </template>

          <!-- 子菜单中的分组 -->
          <template v-for="subMenu in menu.children" :key="subMenu.id">
            <el-menu-item
                v-if="!subMenu.children && subMenu.isVisible === 1"
                :index="subMenu.isExt===1 ? `/admin/external/${subMenu.id}` : subMenu.path"
                @click="handleMenuClick(subMenu)"
            >
              <el-icon v-if="subMenu.icon">
                <component :is="subMenu.icon"/>
              </el-icon>
              <template #title>
                <span>{{ subMenu.name }}</span>
              </template>
            </el-menu-item>

            <!-- 多级嵌套菜单 -->
            <el-sub-menu v-else-if="subMenu.isVisible === 1" :index="String(subMenu.id)">
              <template #title>
                <el-icon v-if="subMenu.icon">
                  <component :is="subMenu.icon"/>
                </el-icon>
                <span>{{ subMenu.name }}</span>
              </template>
              <template v-for="thirdMenu in subMenu.children" :key="thirdMenu.id">
                <el-menu-item
                    v-if="thirdMenu.isVisible === 1"
                    :index="thirdMenu.isExt===1 ? `/admin/external/${thirdMenu.id}` : thirdMenu.path"
                    @click="handleMenuClick(thirdMenu)"
                >
                  <el-icon v-if="thirdMenu.icon">
                    <component :is="thirdMenu.icon"/>
                  </el-icon>
                  <template #title>
                    <span>{{ thirdMenu.name }}</span>
                  </template>
                </el-menu-item>
              </template>
            </el-sub-menu>
          </template>
        </el-sub-menu>

        <!-- 无子菜单的项 -->
        <el-menu-item v-else-if="menu.isVisible === 1" :index="menu.path" @click="handleMenuClick(menu)">
          <el-icon v-if="menu.icon">
            <component :is="menu.icon"/>
          </el-icon>
          <template #title>
            <span>{{ menu.name }}</span>
          </template>
        </el-menu-item>
      </template>
    </el-menu>
  </el-scrollbar>
</template>

<script lang="ts" setup>
import {computed} from 'vue'
import {useRoute, useRouter} from 'vue-router'
import {useGlobalSettingStore} from '@/stores/globalSetting.store'
import {useUserStore} from '@/stores/user.store'
import type {SysMenu} from "@/types/monitor/sysMenu.ts";

const props = defineProps({
  mode: {
    type: String,
    default: 'vertical', // 设置默认值
    validator: (value: string) => ['horizontal', 'vertical'].includes(value) // 验证只能是这两个值
  }
})

const globalSettingStore = useGlobalSettingStore()
const userStore = useUserStore()
const route = useRoute()
const router = useRouter()

// 使用用户菜单数据
const menuItems = computed(() => userStore.userMenus)

// 当前激活的菜单
const activeMenu = computed(() => {
  return route.path
})

// 查找菜单项的工具函数
const findMenuByIndex = (menus: SysMenu[], index: string): SysMenu | null => {
  for (const menu of menus) {
    // 检查是否是当前菜单项（通过ID或路径匹配）
    if (String(menu.id) === index || menu.path === index) {
      return menu
    }
    // 检查是否是外部链接路径
    if (menu.isExt === 1) {
      const externalPath = `/admin/external/${menu.id}`
      if (externalPath === index) {
        return menu
      }
    }
    // 递归查找子菜单
    if (menu.children && menu.children.length > 0) {
      const found = findMenuByIndex(menu.children, index)
      if (found) return found
    }
  }
  return null
}

// 处理菜单选择事件（手动处理路由跳转）
const handleMenuSelect = (index: string) => {
  const menu = findMenuByIndex(menuItems.value, index)
  if (!menu) return

  // 处理外部链接
  if (menu.isExt === 1 && menu.path) {
    const openMode = menu.openMode

    if (openMode === 1) {
      // 新窗口打开模式：打开新窗口，不进行路由跳转
      window.open(menu.path, '_blank')
      return
    }

    if (openMode === 0) {
      // 内嵌打开模式：跳转到外部链接路由
      const externalPath = `/admin/external/${menu.id}`
      if (route.path !== externalPath) {
        router.push(externalPath)
      }
      return
    }
  }

  // 处理普通菜单项（非外部链接）
  if (menu.path && menu.isExt !== 1) {
    if (route.path !== menu.path) {
      router.push(menu.path)
    }
  }
}

// 处理菜单点击事件（可选，用于额外的点击处理逻辑）
const handleMenuClick = (menu: SysMenu) => {
}

</script>

<style scoped lang="scss">
@use "index.scss";

</style>