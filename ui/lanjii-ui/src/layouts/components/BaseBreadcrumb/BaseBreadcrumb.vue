<template>
  <div class="breadcrumb-container">
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/admin/index' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item
        v-for="(item, index) in breadcrumbItems"
        :key="index"
        :to="index === breadcrumbItems.length - 1 ? null : { path: item.path }"
      >
        {{ item.title }}
      </el-breadcrumb-item>
    </el-breadcrumb>
  </div>
</template>

<script setup lang="ts">
import {computed} from 'vue'
import {useRoute} from 'vue-router'
import {useUserStore} from '@/stores/user.store'
import type {SysMenu} from '@/types/sys/sysMenu'

const route = useRoute()
const userStore = useUserStore()

interface BreadcrumbItem {
  title: string
  path: string
}

// 递归查找匹配的菜单项
const findMenuByPath = (path: string, menus: SysMenu[]): SysMenu | null => {
  for (const menu of menus) {
    if (menu.path === path) {
      return menu
    }
    if (menu.children) {
      const found = findMenuByPath(path, menu.children)
      if (found) return found
    }
  }
  return null
}

// 递归查找菜单路径
const findMenuPath = (path: string, menus: SysMenu[], results: SysMenu[] = []): SysMenu[] => {
  for (const menu of menus) {
    if (menu.path === path) {
      results.push(menu)
      return results
    }
    
    if (menu.children) {
      // 尝试在子菜单中查找
      const foundInChildren = findMenuPath(path, menu.children, [...results, menu])
      if (foundInChildren.length > 0) {
        return foundInChildren
      }
    }
  }
  
  return []
}

// 计算面包屑项
const breadcrumbItems = computed(() => {
  const result: BreadcrumbItem[] = []
  const menuPath = findMenuPath(route.path, userStore.userMenus)
  
  menuPath.forEach(item => {
    result.push({
      title: item.name,
      path: item.path || ''
    })
  })
  
  return result
})
</script>

<style scoped>
.breadcrumb-container {
  margin: 8px 0;
  display: flex;
  align-items: center;
}
</style> 