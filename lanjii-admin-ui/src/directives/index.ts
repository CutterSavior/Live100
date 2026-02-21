/**
 * 自定义指令统一导出
 */
import type {App} from 'vue'
import {permission, permissionShow} from './permission'
// import {loading} from './loading'  // 已移除，使用 Element Plus 的 v-loading
import {debounce} from './debounce'

/**
 * 注册所有自定义指令
 * @param app Vue应用实例
 */
export function setupDirectives(app: App) {
    // 权限指令
    app.directive('permission', permission)
    app.directive('permission-show', permissionShow)

    // 防抖指令
    app.directive('debounce', debounce)
}

export {
    permission,
    permissionShow,
    debounce
}
