import type { Directive } from 'vue'
import { PermissionUtils } from '@/utils/permission'

/**
 * 权限控制指令
 * 用法：
 * v-permission="'sys:user:save'" - 单个权限
 * v-permission="['sys:user:save', 'sys:user:edit']" - 多个权限（任意一个）
 * v-permission:all="['sys:user:save', 'sys:user:edit']" - 多个权限（全部需要）
 */
export const permission: Directive = {
  mounted(el: HTMLElement, binding) {
    const { value, arg } = binding
    
    if (!value) {
      return
    }
    
    let hasPermission = false
    
    if (Array.isArray(value)) {
      // 多个权限
      if (arg === 'all') {
        // 需要拥有所有权限
        hasPermission = PermissionUtils.hasAllPermissions(value)
      } else {
        // 需要拥有任意一个权限
        hasPermission = PermissionUtils.hasAnyPermission(value)
      }
    } else {
      // 单个权限
      hasPermission = PermissionUtils.hasPermission(value)
    }
    
    if (!hasPermission) {
      // 没有权限时隐藏元素
      el.style.display = 'none'
      // 或者移除元素
      // el.parentNode?.removeChild(el)
    }
  },
  
  updated(el: HTMLElement, binding) {
    // 当权限变化时重新检查
    const { value, arg } = binding
    
    if (!value) {
      el.style.display = ''
      return
    }
    
    let hasPermission = false
    
    if (Array.isArray(value)) {
      if (arg === 'all') {
        hasPermission = PermissionUtils.hasAllPermissions(value)
      } else {
        hasPermission = PermissionUtils.hasAnyPermission(value)
      }
    } else {
      hasPermission = PermissionUtils.hasPermission(value)
    }
    
    if (!hasPermission) {
      el.style.display = 'none'
    } else {
      el.style.display = ''
    }
  }
}

/**
 * 权限控制指令（显示模式）
 * 用法：
 * v-permission-show="'sys:user:save'" - 有权限时显示
 */
export const permissionShow: Directive = {
  mounted(el: HTMLElement, binding) {
    const { value } = binding
    
    if (!value) {
      el.style.display = 'none'
      return
    }
    
    const hasPermission = Array.isArray(value) 
      ? PermissionUtils.hasAnyPermission(value)
      : PermissionUtils.hasPermission(value)
    
    if (!hasPermission) {
      el.style.display = 'none'
    }
  },
  
  updated(el: HTMLElement, binding) {
    const { value } = binding
    
    if (!value) {
      el.style.display = 'none'
      return
    }
    
    const hasPermission = Array.isArray(value) 
      ? PermissionUtils.hasAnyPermission(value)
      : PermissionUtils.hasPermission(value)
    
    if (!hasPermission) {
      el.style.display = 'none'
    } else {
      el.style.display = ''
    }
  }
}
