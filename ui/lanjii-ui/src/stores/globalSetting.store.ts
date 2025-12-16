import {defineStore} from 'pinia'
import {DEFAULT_PRIMARY} from "@/constants";
import type {TabItem} from '@/types/store';

export const useGlobalSettingStore = defineStore('globalSetting', {
    state: () => ({
        isCollapse: false, // 菜单侧边栏是否折叠
        themeMode: "light", // 主题模式
        layoutMode: 'vertical', // 布局模式
        isDrawerOpen: false, // 后台界面配置页面是否打开
        primary: DEFAULT_PRIMARY, // 默认颜色
        isDark: false, // 是否开启暗黑模式
        isGrayscale: false, // 是否开启灰色模式，可以和暗黑模式同时开启
        isColorBlind: false, // 是否开启色弱模式，可以和暗黑模式同时开启
        isHiddenSearch: false, // 是否隐藏搜索栏
        visitedTabs: [] as TabItem[], // 已访问的标签页
    }),
    actions: {
        // 添加访问的标签页
        addVisitedTab(tab: TabItem) {
            if (!this.visitedTabs.some(item => item.path === tab.path)) {
                this.visitedTabs.push(tab);
            }
        },
        
        // 删除访问的标签页
        removeVisitedTab(path: string) {
            const index = this.visitedTabs.findIndex(item => item.path === path);
            if (index !== -1) {
                this.visitedTabs.splice(index, 1);
            }
        },
        
        // 清除所有标签页，保留控制台
        clearVisitedTabs(keepPath = '/admin/index') {
            this.visitedTabs = this.visitedTabs.filter(item => item.path === keepPath);
        }
    },
    persist: {
        key: 'global-settings', // 自定义存储键名
        storage: localStorage,  // 默认使用localStorage
    }
})
