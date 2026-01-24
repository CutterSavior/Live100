<template>
  <div class="common-layout">
    <div class="column-menu">
      <div class="logo">
        <img alt="Logo" class="logo-img" src="@/assets/logo.svg"/>
      </div>
      <template v-for="menu in menuItems" :key="menu.id">
        <div class="column-menu-item"
             @click="handleColumnMenuClick(menu)"
             :class="{ 
               active: isActive(menu) 
             }">
          <el-icon v-if="menu.icon">
            <component :is="menu.icon"/>
          </el-icon>
          <span>{{ menu.name }}</span>
        </div>
      </template>
    </div>
    <el-aside v-if="columnChildrenMenu && columnChildrenMenu.length"
              :style="{ width: globalSettingStore.isCollapse ?  '64px':'200px' }">
      <el-scrollbar>
        <el-menu
            :default-active="activeMenu"
            :collapse="globalSettingStore.isCollapse"
            unique-opened
            @select="handleMenuSelect"
        >
          <!-- 递归渲染菜单 -->
          <template v-for="menu in columnChildrenMenu" :key="menu.id">
            <!-- 带子菜单的项 -->
            <el-sub-menu v-if="menu.children && menu.children.length" :index="menu.id">
              <template #title>
                <el-icon v-if="menu.icon">
                  <component :is="menu.icon"/>
                </el-icon>
                <span>{{ menu.name }}</span>
              </template>

              <!-- 子菜单中的分组 -->
              <template v-for="subMenu in menu.children" :key="subMenu.id">
                <el-menu-item
                    v-if="!subMenu.children"
                    :index="subMenu.isExt===1 ? `/admin/external/${subMenu.id}` : subMenu.path"
                >
                  <el-icon v-if="subMenu.icon">
                    <component :is="subMenu.icon"/>
                  </el-icon>
                  <template #title>
                    <span>{{ subMenu.name }}</span>
                  </template>
                </el-menu-item>

                <!-- 多级嵌套菜单 -->
                <el-sub-menu v-else :index="subMenu.id">
                  <template #title>
                    <el-icon v-if="subMenu.icon">
                      <component :is="subMenu.icon"/>
                    </el-icon>
                    <span>{{ subMenu.name }}</span>
                  </template>
                  <el-menu-item
                      v-for="thirdMenu in subMenu.children"
                      :key="thirdMenu.id"
                      :index="thirdMenu.isExt===1 ? `/admin/external/${thirdMenu.id}` : thirdMenu.path"
                  >
                    <el-icon v-if="thirdMenu.icon">
                      <component :is="thirdMenu.icon"/>
                    </el-icon>
                    <template #title>
                      <span>{{ thirdMenu.name }}</span>
                    </template>
                  </el-menu-item>
                </el-sub-menu>
              </template>
            </el-sub-menu>

            <!-- 无子菜单的项 -->
            <el-menu-item v-else :index="menu.path">
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
    </el-aside>
    <el-container class="right-container scroll-area" @click="closeSubMenu">
      <el-header class="header">
        <BaseHeader/>
      </el-header>
      <TabsView/>
      <el-main>
        <Main/>
      </el-main>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import BaseHeader from "@/layouts/components/BaseHeader/BaseHeader.vue";
import TabsView from "@/components/TabsView/TabsView.vue";
import Main from "@/layouts/components/Main/Main.vue";

import {useGlobalSettingStore} from '@/stores/globalSetting.store';
import {useUserStore} from '@/stores/user.store';
import {computed, onMounted, ref, watch} from "vue";
import {useRoute, useRouter} from "vue-router";
import type {SysMenu} from "@/types/sys/sysMenu.ts";

const globalSettingStore = useGlobalSettingStore();
const userStore = useUserStore()
const router = useRouter();
const route = useRoute();

// 使用用户菜单数据
const menuItems = computed(() => userStore.userMenus)

const columnChildrenMenu = ref();
const selectedMenuId = ref<number | null>(null);

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
  // 先从 columnChildrenMenu 中查找（侧边栏中显示的菜单）
  let menu = columnChildrenMenu.value ? findMenuByIndex(columnChildrenMenu.value, index) : null
  // 如果没找到，再从所有菜单中查找
  if (!menu) {
    menu = findMenuByIndex(menuItems.value, index)
  }
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

// 简化isActive方法，只基于selectedMenuId判断
const isActive = (menu: SysMenu) => {
  return selectedMenuId.value === menu.id;
};

// 清除所有选中状态，确保互斥
const clearAllMenuSelection = () => {
  selectedMenuId.value = null;
};

// 选择指定菜单，确保互斥
const selectMenu = (menuId: number | undefined) => {
  if (menuId === undefined) return;
  clearAllMenuSelection();
  selectedMenuId.value = menuId;
};

// 修改子菜单的显示控制
const showSubMenu = (children: SysMenu[] | undefined) => {
  if (children && children.length > 0) {
    columnChildrenMenu.value = children;
  }
};

// 隐藏子菜单
const hideSubMenu = () => {
  columnChildrenMenu.value = null;
};

const handleColumnMenuClick = (menu: SysMenu) => {
  // 确保互斥选中
  selectMenu(menu.id);

  // 处理外部链接
  if (menu.isExt === 1 && menu.path) {
    const openMode = menu.openMode

    if (openMode === 1) {
      // 新窗口打开模式：打开新窗口，不进行路由跳转
      window.open(menu.path, '_blank')
      // 如果有子菜单，展开子菜单
      if (menu.children && menu.children.length > 0) {
        showSubMenu(menu.children);
      } else {
        hideSubMenu();
      }
      return
    }

    if (openMode === 0) {
      // 内嵌打开模式：跳转到外部链接路由
      const externalPath = `/admin/external/${menu.id}`
      if (route.path !== externalPath) {
        router.push(externalPath)
      }
      // 如果有子菜单，展开子菜单
      if (menu.children && menu.children.length > 0) {
        showSubMenu(menu.children);
      } else {
        hideSubMenu();
      }
      return
    }
  }

  // 如果菜单有子项，展开子菜单
  if (menu.children && menu.children.length > 0) {
    showSubMenu(menu.children);
  } else if (menu.path && menu.isExt !== 1) {
    // 如果是页面（没有子菜单但有路径），直接导航
    if (route.path !== menu.path) {
      router.push(menu.path)
    }
    // 如果没有子菜单，确保侧边栏不显示
    hideSubMenu();
  }
};

// 添加关闭子菜单的方法
const closeSubMenu = () => {
  if (!columnChildrenMenu.value) {
    hideSubMenu();
  }
};

// 根据当前路径设置初始选中状态
const initActiveMenu = () => {
  const currentPath = route.path;
  clearAllMenuSelection(); // 确保清除所有选中状态

  // 查找匹配当前路径的菜单项
  for (const menu of menuItems.value) {
    // 直接匹配一级菜单
    if (menu.path && currentPath.startsWith(menu.path)) {
      selectMenu(menu.id);
      // 如果是一级菜单且没有子菜单，确保子菜单栏隐藏
      if (!menu.children || menu.children.length === 0) {
        hideSubMenu();
      }
      return;
    }

    // 检查子菜单
    if (menu.children && menu.children.length > 0) {
      const hasMatchingChild = menu.children.some(child => {
        // 匹配二级菜单
        if (child.path && currentPath.startsWith(child.path)) {
          return true;
        }

        // 匹配三级菜单
        if (child.children && child.children.length > 0) {
          return child.children.some(grandChild =>
              grandChild.path && currentPath.startsWith(grandChild.path)
          );
        }

        return false;
      });

      if (hasMatchingChild) {
        selectMenu(menu.id);
        // 可选：如果要自动展开匹配的子菜单
        showSubMenu(menu.children);
        return;
      }
    }
  }

  // 如果没有匹配的菜单，确保子菜单栏隐藏
  hideSubMenu();
};

// 组件挂载时初始化选中状态
onMounted(() => {
  initActiveMenu();
});

// 监听路由变化，更新选中状态
watch(() => route.path, () => {
  initActiveMenu();
});
</script>

<style scoped lang="scss">
@use "index.scss";

</style>
