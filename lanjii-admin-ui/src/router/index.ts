// router/index.ts
import {createRouter, createWebHistory, type RouteRecordRaw} from 'vue-router'
import {useUserStore} from '@/stores/user.store'
// @ts-ignore
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import type {SysMenu} from "@/types/sys/sysMenu.ts";

NProgress.configure({showSpinner: false});

// 标记是否是页面刷新
let isPageRefreshing = true;
// 标记是否已添加动态路由
let isDynamicRoutesAdded = false;

// 基础admin路由结构
const baseAdminRoute: RouteRecordRaw = {
    path: '/admin',
    name: 'admin',
    component: () => import('@/layouts/LayoutSwitcher.vue'),
    redirect: '/admin/index',
    children: [
        {
            path: 'index',
            name: 'adminIndex',
            component: () => import('@/views/home/index.vue'),
            meta: {
                title: '首页',
                requiresAuth: true,
                icon: 'HomeFilled'
            }
        },
        {
            path: 'profile',
            name: 'adminProfile',
            component: () => import('@/views/profile/index.vue'),
            meta: {
                title: '个人信息',
                requiresAuth: true,
                icon: 'User'
            }
        },
        {
            path: 'notice/detail/:id',
            name: 'noticeDetail',
            component: () => import('@/views/notice/detail.vue'),
            meta: {
                title: '通知详情',
                requiresAuth: true,
                icon: 'Bell'
            }
        }, {
            path: '/:pathMatch(.*)*',
            name: 'NotFound',
            component: () => import('@/views/error/404.vue'),
            meta: {
                title: '页面未找到',
                requiresAuth: true
            }
        }
    ]
}

// 静态路由
const constantRoutes: RouteRecordRaw[] = [
    {
        path: '/',
        redirect: '/admin/index'
    },
    {
        path: '/admin/login',
        name: 'adminLogin',
        component: () => import('@/views/login/index.vue'),
        meta: {
            title: '登录',
            requiresAuth: false
        }
    },
    baseAdminRoute
]

// 动态导入组件
const modules = import.meta.glob('../views/**/*.vue')

// 将SysMenu转换为路由配置
const menuToRoutes = (menus: SysMenu[]): RouteRecordRaw[] => {
    const routes: RouteRecordRaw[] = []

    const processMenu = (menu: SysMenu) => {
        if (import.meta.env.DEV) console.log('处理菜单:', menu.name);

        // 只处理非外链的菜单（type=2且isExt=0）
        if (menu.type === 2 && menu.isExt === 0 && menu.path && menu.component) {
            const route: RouteRecordRaw = {
                path: menu.path,
                name: menu.name,
                component: modules[`../views/${menu.component}.vue`],
                meta: {
                    title: menu.name,
                    icon: menu.icon,
                    permission: menu.permission,
                    requiresAuth: true,
                    isKeepAlive: menu.isKeepAlive === 1
                }
            }
            routes.push(route)
            if (import.meta.env.DEV) console.log(`添加子路由: ${menu.path}`)
        }

        // 外链页面内嵌打开
        if (menu.type === 2 && menu.isExt === 1 && menu.path && menu.openMode === 0) {
            const routePath = `/admin/external/${menu.id}`
            const route: RouteRecordRaw = {
                path: routePath,
                name: menu.name || `external-${menu.id}`,
                component: () => import('@/views/iframe/IframePage.vue'),
                meta: {
                    title: menu.name,
                    icon: menu.icon,
                    permission: menu.permission,
                    requiresAuth: true,
                    url: menu.path
                }
            }
            routes.push(route)
        }

        // 递归处理子菜单
        if (menu.children && menu.children.length > 0) {
            menu.children.forEach(processMenu)
        }
    }

    menus.forEach(processMenu)
    return routes
}

const router = createRouter({
    history: createWebHistory(),
    routes: constantRoutes,
    scrollBehavior() {
        return {top: 0}
    }
})

// 动态添加路由
export const addDynamicRoutes = (): boolean => {
    if (isDynamicRoutesAdded) {
        if (import.meta.env.DEV) console.log('动态路由已添加，跳过')
        return true
    }

    const userStore = useUserStore()
    if (import.meta.env.DEV) console.log('开始添加动态路由，菜单数据:', userStore.menus);

    if (!userStore.menus || userStore.menus.length === 0) {
        console.warn('菜单数据为空，无法添加动态路由')
        return false
    }

    try {
        const dynamicRoutes = menuToRoutes(userStore.menus)
        if (import.meta.env.DEV) console.log('转换后的动态路由:', dynamicRoutes)

        // 将动态路由添加到已存在的 admin 父路由下，避免重复注册父级
        dynamicRoutes.forEach((route) => {
            router.addRoute('admin', route)
        })

        isDynamicRoutesAdded = true
        if (import.meta.env.DEV) console.log('动态路由添加完成，新增子路由数:', dynamicRoutes.length)
        return true

    } catch (error) {
        console.error('添加动态路由失败:', error)
        return false
    }
}

// 重置动态路由
export const resetDynamicRoutes = () => {
    // 获取当前所有路由
    const currentRoutes = router.getRoutes()

    const keepRouteNames = new Set<string>()

    constantRoutes.forEach(route => {
        if (route.name) {
            keepRouteNames.add(route.name as string)
        }
        if (route.children) {
            route.children.forEach(child => {
                if (child.name) {
                    keepRouteNames.add(child.name as string)
                }
            })
        }
    })

    // 移除所有动态添加的路由
    currentRoutes.forEach(route => {
        if (route.name && !keepRouteNames.has(route.name as string)) {
            router.removeRoute(route.name)
            console.log('移除动态路由:', route.name)
        }
    })

    isDynamicRoutesAdded = false
    isPageRefreshing = true
    console.log('动态路由已重置')
}

// 全局前置守卫
router.beforeEach(async (to, from, next) => {
    NProgress.start()
    const userStore = useUserStore()

    // 设置页面标题
    document.title = to.meta.title ? `${to.meta.title} - 后台管理系统` : '后台管理系统'

    // 设置页面刷新标记
    to.meta.isRefreshing = isPageRefreshing
    if (isPageRefreshing) {
        isPageRefreshing = false
    }

    // 不需要认证的页面直接放行
    if (!to.meta.requiresAuth) {
        // 如果已登录但访问登录页，跳转到首页
        if (to.name === 'adminLogin' && userStore.isLoggedIn) {
            next('/admin/index')
            return
        }
        next()
        return
    }

    // 需要认证的页面检查登录状态
    if (!userStore.token || !userStore.isLoggedIn) {
        next({name: 'adminLogin'})
        return
    }

    // 已登录用户，检查动态路由是否已添加
    if (!isDynamicRoutesAdded) {
        console.log('检测到动态路由未添加，开始添加...')
        const routesAdded = addDynamicRoutes()

        if (routesAdded) {
            // 路由添加成功，重定向到目标页面
            if (to.path === '/' || to.path === '/admin/login') {
                next('/admin/index')
            } else {
                next({...to, replace: true})
            }
            return
        } else {
            console.error('动态路由添加失败，跳转到登录页')
            userStore.clearUserData()
            next({name: 'adminLogin'})
            return
        }
    }

    next()
})

router.afterEach(() => {
    NProgress.done()
})

export default router