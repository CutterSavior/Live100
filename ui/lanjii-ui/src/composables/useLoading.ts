import { ref, readonly, computed } from 'vue'

/**
 * Loading 状态管理 Composable
 * 用于统一管理异步操作的加载状态
 */
export function useLoading(initialValue = false) {
  const loading = ref(initialValue)
  
  /**
   * 包装异步函数，自动管理 loading 状态
   * @param fn 异步函数
   * @returns Promise
   */
  const withLoading = async <T>(fn: () => Promise<T>): Promise<T | undefined> => {
    if (loading.value) {
      // 防止重复调用
      return
    }
    
    loading.value = true
    try {
      const result = await fn()
      return result
    } catch (error) {
      console.error('操作失败:', error)
      // 可以在这里添加全局错误处理
      throw error
    } finally {
      loading.value = false
    }
  }
  
  /**
   * 手动设置 loading 状态
   * @param value loading 状态
   */
  const setLoading = (value: boolean) => {
    loading.value = value
  }
  
  return {
    loading: readonly(loading), // 只读，防止外部直接修改
    withLoading,
    setLoading
  }
}

/**
 * 多个 Loading 状态管理
 * 适用于一个组件有多个独立的异步操作
 */
export function useMultipleLoading() {
  const loadingStates = ref<Record<string, boolean>>({})
  
  const createLoading = (key: string, initialValue = false) => {
    loadingStates.value[key] = initialValue
    
    const withLoading = async <T>(fn: () => Promise<T>): Promise<T | undefined> => {
      if (loadingStates.value[key]) {
        return
      }
      
      loadingStates.value[key] = true
      try {
        return await fn()
      } catch (error) {
        console.error(`操作失败 [${key}]:`, error)
        throw error
      } finally {
        loadingStates.value[key] = false
      }
    }
    
    return {
      loading: computed(() => loadingStates.value[key] || false),
      withLoading,
      setLoading: (value: boolean) => {
        loadingStates.value[key] = value
      }
    }
  }
  
  return {
    loadingStates: readonly(loadingStates),
    createLoading
  }
}
