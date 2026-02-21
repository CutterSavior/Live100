<script setup lang="ts">
import { computed, nextTick, onBeforeUnmount, onMounted, ref, watch } from 'vue'
import * as echarts from 'echarts'

type EChartsOption = echarts.EChartsOption

type Props = {
  option: EChartsOption
  height?: string
  width?: string
}

const props = withDefaults(defineProps<Props>(), {
  height: '320px',
  width: '100%'
})

const chartEl = ref<HTMLDivElement | null>(null)
const chartInstance = ref<echarts.ECharts | null>(null)
let resizeObserver: ResizeObserver | null = null
let darkObserver: MutationObserver | null = null

const style = computed(() => {
  return {
    height: props.height,
    width: props.width
  }
})

const isDark = () => {
  return document.documentElement.classList.contains('dark')
}

const initChart = async () => {
  if (!chartEl.value) return

  disposeChart()

  chartInstance.value = echarts.init(chartEl.value, isDark() ? 'dark' : undefined)
  chartInstance.value.setOption(props.option, { notMerge: true })

  resizeObserver = new ResizeObserver(() => {
    chartInstance.value?.resize()
  })
  resizeObserver.observe(chartEl.value)
}

const disposeChart = () => {
  resizeObserver?.disconnect()
  resizeObserver = null

  chartInstance.value?.dispose()
  chartInstance.value = null
}

watch(
  () => props.option,
  async () => {
    await nextTick()
    if (!chartInstance.value) {
      await initChart()
      return
    }
    chartInstance.value.setOption(props.option, { notMerge: true })
  },
  { deep: true }
)

onMounted(async () => {
  await nextTick()
  await initChart()

  // 监听暗黑模式切换，重新初始化图表主题
  darkObserver = new MutationObserver(() => {
    initChart()
  })
  darkObserver.observe(document.documentElement, {
    attributes: true,
    attributeFilter: ['class']
  })
})

onBeforeUnmount(() => {
  darkObserver?.disconnect()
  darkObserver = null
  disposeChart()
})
</script>

<template>
  <div ref="chartEl" :style="style" />
</template>
