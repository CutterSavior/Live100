import { useUserStore } from '@/stores/user.store'

/**
 * 权限控制工具类
 */
export class PermissionUtils {
  /**
   * 检查用户是否拥有指定权限
   * @param permission 权限字符，如 'sys:user:save'
   * @returns 是否拥有权限
   */
  static hasPermission(permission: string): boolean {
    const userStore = useUserStore()
    const permissions = userStore.userPermissions
    
    if (!permission || !permissions || permissions.length === 0) {
      return false
    }
    
    // 直接从权限列表中查找
    return permissions.includes(permission)
  }
  
  /**
   * 检查用户是否拥有任意一个权限
   * @param permissions 权限字符数组
   * @returns 是否拥有任意一个权限
   */
  static hasAnyPermission(permissions: string[]): boolean {
    if (!permissions || permissions.length === 0) {
      return false
    }
    
    return permissions.some(permission => this.hasPermission(permission))
  }
  
  /**
   * 检查用户是否拥有所有权限
   * @param permissions 权限字符数组
   * @returns 是否拥有所有权限
   */
  static hasAllPermissions(permissions: string[]): boolean {
    if (!permissions || permissions.length === 0) {
      return false
    }
    
    return permissions.every(permission => this.hasPermission(permission))
  }
}

/**
 * 权限控制组合式函数
 */
export function usePermission() {
  /**
   * 检查单个权限
   */
  const hasPermission = (permission: string): boolean => {
    return PermissionUtils.hasPermission(permission)
  }
  
  /**
   * 检查任意一个权限
   */
  const hasAnyPermission = (permissions: string[]): boolean => {
    return PermissionUtils.hasAnyPermission(permissions)
  }
  
  /**
   * 检查所有权限
   */
  const hasAllPermissions = (permissions: string[]): boolean => {
    return PermissionUtils.hasAllPermissions(permissions)
  }
  
  return {
    hasPermission,
    hasAnyPermission,
    hasAllPermissions
  }
}
