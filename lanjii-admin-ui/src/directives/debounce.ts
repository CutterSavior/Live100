import type { Directive, DirectiveBinding } from 'vue'

/**
 * 防抖指令
 * 用法：
 * v-debounce="handleClick"  - 默认延迟 300ms
 * v-debounce:500="handleClick"  - 自定义延迟时间
 * v-debounce.immediate="handleClick"  - 立即执行
 */
export const debounce: Directive = {
  mounted(el: HTMLElement, binding: DirectiveBinding) {
    const { value, arg, modifiers } = binding
    
    if (typeof value !== 'function') {
      console.warn('v-debounce 指令的值必须是一个函数')
      return
    }
    
    const delay = arg ? parseInt(arg) : 300
    const immediate = modifiers.immediate
    
    let timer: number | null = null
    
    const debounced = function(this: any, ...args: any[]) {
      if (timer) {
        clearTimeout(timer)
      }
      
      if (immediate && !timer) {
        value.apply(this, args)
      }
      
      timer = setTimeout(() => {
        if (!immediate) {
          value.apply(this, args)
        }
        timer = null
      }, delay) as unknown as number
    }
    
    // 保存到元素上，便于后续清理
    ;(el as any).__debounce__ = debounced
    
    el.addEventListener('click', debounced)
  },
  
  unmounted(el: HTMLElement) {
    const debounced = (el as any).__debounce__
    if (debounced) {
      el.removeEventListener('click', debounced)
      delete (el as any).__debounce__
    }
  }
}
