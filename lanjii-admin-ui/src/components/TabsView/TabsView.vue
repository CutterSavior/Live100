<template>
  <div class="tabs-view-container">
    <el-tabs
        v-model="activeTab"
        type="card"
        @tab-click="handleTabClick"
        @tab-remove="closeTab"
    >
      <el-tab-pane
          v-for="tab in tabsList"
          :key="tab.path"
          :label="tab.title"
          :name="tab.path"
          :closable="tab.path !== defaultPath"
      >
        <template #label>
          <el-dropdown
              trigger="contextmenu"
              @command="(command: string) => handleCommand(command, tab)"
          >

            <span class="custom-tabs-label">
              <el-icon>
               <component :is="tab.icon"/>
              </el-icon>
              {{ tab.title }}
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="closeTab">
                  <el-icon>
                    <Close/>
                  </el-icon>
                  关闭当前
                </el-dropdown-item>
                <el-dropdown-item command="closeLeftTabs">
                  <el-icon>
                    <ArrowLeft/>
                  </el-icon>
                  关闭左侧
                </el-dropdown-item>
                <el-dropdown-item command="closeRightTabs">
                  <el-icon>
                    <ArrowRight/>
                  </el-icon>
                  关闭右侧
                </el-dropdown-item>
                <el-dropdown-item command="closeOtherTabs">
                  <el-icon>
                    <CircleClose/>
                  </el-icon>
                  关闭其他
                </el-dropdown-item>
                <el-dropdown-item command="closeAllTabs">
                  <el-icon>
                    <FolderDelete/>
                  </el-icon>
                  关闭全部
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </template>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup lang="ts">
import {computed, onMounted, ref, watch} from 'vue'
import type {RouteLocationNormalizedLoaded} from 'vue-router';
import {useRoute, useRouter} from 'vue-router'
import {useUserStore} from '@/stores/user.store'
import {useGlobalSettingStore} from '@/stores/globalSetting.store'

interface TabItem {
  title: string
  path: string
  name: string
  icon: string
}

const defaultPath = '/admin/index'
const defaultTitle = '控制台'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()
const globalSettingStore = useGlobalSettingStore()

const activeTab = ref(route.path)
// 使用全局存储中的tabs列表
const tabsList = computed(() => {
  // 确保有默认的控制台标签
  if (!globalSettingStore.visitedTabs.some(tab => tab.path === defaultPath)) {
    globalSettingStore.addVisitedTab({
      title: defaultTitle,
      path: defaultPath,
      name: 'dashboard',
      icon: 'HomeFilled'
    })
  }
  return globalSettingStore.visitedTabs
})

// 添加标签页
const addTab = (route: RouteLocationNormalizedLoaded) => {
  // 如果已经存在，则不添加
  if (globalSettingStore.visitedTabs.some(tab => tab.path === route.path)) {
    activeTab.value = route.path
    return
  }

  const title = typeof route.meta.title === 'string' ? route.meta.title : String(route.path)
  const icon = typeof route.meta.icon === 'string' ? route.meta.icon : 'QuestionFilled'
  const name = typeof route.name === 'string' ? route.name : String(route.path)

  globalSettingStore.addVisitedTab({
    title,
    path: route.path,
    name,
    icon
  })
  activeTab.value = route.path
}

// 关闭标签页
const closeTab = (path: string) => {
  // 如果是默认路径，不能关闭
  if (path === defaultPath) return

  const index = globalSettingStore.visitedTabs.findIndex(tab => tab.path === path)
  if (index === -1) return

  // 如果关闭的是当前激活的，则需要激活其他标签
  if (path === activeTab.value) {
    // 优先激活左侧标签，如果没有则激活右侧标签
    const nextActiveIndex = index === 0 ? index + 1 : index - 1
    const nextActivePath = globalSettingStore.visitedTabs[nextActiveIndex].path
    router.push(nextActivePath)
  }

  globalSettingStore.removeVisitedTab(path)
}

// 关闭左侧标签
const closeLeftTabs = (path: string) => {
  const index = globalSettingStore.visitedTabs.findIndex(tab => tab.path === path)
  if (index <= 0) return

  // 保留当前及右侧标签
  const rightTabs = globalSettingStore.visitedTabs.slice(index)
  // 确保保留默认标签
  const defaultTab = globalSettingStore.visitedTabs.find(tab => tab.path === defaultPath)

  globalSettingStore.visitedTabs = defaultTab
      ? [defaultTab, ...rightTabs.filter(tab => tab.path !== defaultPath)]
      : rightTabs

  // 如果当前激活的标签被关闭，则激活当前标签
  if (!globalSettingStore.visitedTabs.some(tab => tab.path === activeTab.value)) {
    router.push(path)
  }
}

// 关闭右侧标签
const closeRightTabs = (path: string) => {
  const index = globalSettingStore.visitedTabs.findIndex(tab => tab.path === path)
  if (index === -1 || index === globalSettingStore.visitedTabs.length - 1) return

  globalSettingStore.visitedTabs = globalSettingStore.visitedTabs.slice(0, index + 1)

  // 如果当前激活的标签被关闭，则激活当前标签
  if (!globalSettingStore.visitedTabs.some(tab => tab.path === activeTab.value)) {
    router.push(path)
  }
}

// 关闭其他标签
const closeOtherTabs = (path: string) => {
  const currentTab = globalSettingStore.visitedTabs.find(tab => tab.path === path)
  const defaultTab = globalSettingStore.visitedTabs.find(tab => tab.path === defaultPath)

  if (currentTab) {
    globalSettingStore.visitedTabs = path === defaultPath
        ? [currentTab]
        : [defaultTab as TabItem, currentTab]
  }

  // 如果当前激活的标签被关闭，则激活当前标签
  if (!globalSettingStore.visitedTabs.some(tab => tab.path === activeTab.value)) {
    router.push(path)
  }
}

// 关闭全部标签（除了默认标签）
const closeAllTabs = () => {
  globalSettingStore.clearVisitedTabs()
  router.push(defaultPath)
}

// 处理标签点击
const handleTabClick = (tab: { props: { name: string } }) => {
  router.push(tab.props.name)
}

// 处理右键菜单命令
const handleCommand = (command: string, tab: TabItem) => {
  switch (command) {
    case 'closeTab':
      closeTab(tab.path)
      break
    case 'closeLeftTabs':
      closeLeftTabs(tab.path)
      break
    case 'closeRightTabs':
      closeRightTabs(tab.path)
      break
    case 'closeOtherTabs':
      closeOtherTabs(tab.path)
      break
    case 'closeAllTabs':
      closeAllTabs()
      break
  }
}

// 监听路由变化，自动添加标签页
watch(
    () => route.path,
    (newPath) => {
      activeTab.value = newPath
      addTab(route)
    }
)

// 组件挂载时，添加当前路由对应的标签页
onMounted(() => {
  if (route.path !== defaultPath) {
    addTab(route)
  }
})
</script>

<style lang="scss" scoped>
@use "./index.scss";
</style>