import { onMounted, onUnmounted } from 'vue'
import { useGlobalSettingStore } from '@/stores/globalSetting.store'

/** 小屏断点（px） */
const MOBILE_BREAKPOINT = 992

export function useResponsive() {
  const globalSettingStore = useGlobalSettingStore()

  let prevWidth = window.innerWidth

  const handleResize = () => {
    const width = window.innerWidth
    const wasMobile = prevWidth < MOBILE_BREAKPOINT
    const isMobile = width < MOBILE_BREAKPOINT

    // 进入小屏 → 自动折叠
    if (!wasMobile && isMobile) {
      globalSettingStore.isCollapse = true
    }

    // 离开小屏 → 自动展开
    if (wasMobile && !isMobile) {
      globalSettingStore.isCollapse = false
    }

    prevWidth = width
  }

  onMounted(() => {
    // 初始判断
    if (window.innerWidth < MOBILE_BREAKPOINT) {
      globalSettingStore.isCollapse = true
    }
    window.addEventListener('resize', handleResize)
  })

  onUnmounted(() => {
    window.removeEventListener('resize', handleResize)
  })
}
