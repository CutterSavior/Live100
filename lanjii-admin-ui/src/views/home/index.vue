<template>
  <div class="main-content">
    <!-- 开源项目展示横幅 -->
    <div class="opensource-banner" v-if="showBanner">
      <div class="banner-content">
        <div class="banner-left">
          <div class="banner-icon">
            <el-icon :size="32" color="#409EFF">
              <Star />
            </el-icon>
          </div>
          <div class="banner-info">
            <h3 class="banner-title">🎉 <span class="project-name">岚迹</span> 后台管理系统</h3>
            <p class="banner-description">
              采用 SpringBoot + Spring AI + MyBatis Plus + Vue3 技术栈构建，功能完善、开箱即用
            </p>
            <p class="license-note">基于 MIT 开源协议</p>
            <div class="tech-stack">
              <el-tag type="primary" size="small">SpringBoot</el-tag>
              <el-tag type="success" size="small">Spring AI</el-tag>
              <el-tag type="warning" size="small">MyBatis Plus</el-tag>
              <el-tag type="info" size="small">Vue3</el-tag>
            </div>
          </div>
        </div>
        <div class="banner-right">
          <el-button type="primary" size="large" @click="openGitee" class="star-button">
            <el-icon><Star /></el-icon>
            在 Gitee 上点个 Star
          </el-button>
          <el-button type="info" size="small" text @click="closeBanner" class="close-button">
            <el-icon><Close /></el-icon>
          </el-button>
        </div>
      </div>
    </div>

    <!-- 系统统计 -->
    <el-row :gutter="20">
      <el-col :xs="12" :sm="12" :md="6" v-for="(stat, index) in systemStats" :key="index">
        <el-card class="stat-card" shadow="hover">
          <div class="stat-content">
            <div class="stat-icon" :style="{ backgroundColor: stat.bgColor }">
              <el-icon :size="32" :color="stat.color">
                <component :is="stat.icon"/>
              </el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value" :style="{ color: stat.color }">{{ stat.value }}</div>
              <div class="stat-label">{{ stat.label }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 图表展示区域 -->
    <el-row :gutter="20" class="mt-20">
      <!-- 订单销量（柱状图） -->
      <el-col :xs="24" :sm="24" :md="12">
        <el-card class="chart-card" shadow="hover">
          <template #header>
            <div class="card-header">
              <el-icon :size="20" color="#409EFF">
                <TrendCharts/>
              </el-icon>
              <h3>订单销量（柱状图）</h3>
            </div>
          </template>
          <div class="chart-content">
            <EChart :option="barOption" height="320px"/>
          </div>
        </el-card>
      </el-col>

      <!-- 订单销量趋势（折线图） -->
      <el-col :xs="24" :sm="24" :md="12">
        <el-card class="chart-card" shadow="hover">
          <template #header>
            <div class="card-header">
              <el-icon :size="20" color="#67C23A">
                <DataLine/>
              </el-icon>
              <h3>订单销量趋势（折线图）</h3>
            </div>
          </template>
          <div class="chart-content">
            <EChart :option="lineOption" height="320px"/>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" class="mt-20">
      <el-col :xs="24" :sm="24" :md="12">
        <el-card class="panel-card" shadow="hover">
          <template #header>
            <div class="card-header">
              <el-icon :size="20" color="#67C23A">
                <Lightning/>
              </el-icon>
              <h3>待办事项</h3>
            </div>
          </template>
          <el-table :data="todoList" size="small" border height="260">
            <el-table-column prop="title" label="事项" min-width="180"/>
            <el-table-column prop="priority" label="优先级" width="90">
              <template #default="scope">
                <el-tag v-if="scope.row.priority === '高'" type="danger">高</el-tag>
                <el-tag v-else-if="scope.row.priority === '中'" type="warning">中</el-tag>
                <el-tag v-else type="info">低</el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="status" label="状态" width="110">
              <template #default="scope">
                <el-tag v-if="scope.row.status === '已完成'" type="success">已完成</el-tag>
                <el-tag v-else-if="scope.row.status === '进行中'" type="primary">进行中</el-tag>
                <el-tag v-else type="info">待处理</el-tag>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>

      <el-col :xs="24" :sm="24" :md="12">
        <el-card class="panel-card" shadow="hover">
          <template #header>
            <div class="card-header">
              <el-icon :size="20" color="#E6A23C">
                <Star/>
              </el-icon>
              <h3>系统公告</h3>
            </div>
          </template>
          <el-timeline class="notice-timeline">
            <el-timeline-item v-for="item in noticeList" :key="item.title" :timestamp="item.time" placement="top">
              <div class="notice-title">{{ item.title }}</div>
              <div class="notice-content">{{ item.content }}</div>
            </el-timeline-item>
          </el-timeline>
        </el-card>
      </el-col>
    </el-row>

  </div>
</template>

<script setup lang="ts">
import {DataLine, Lightning, Star, TrendCharts, Close} from '@element-plus/icons-vue'
import {computed, ref} from 'vue'
import EChart from '@/components/EChart/index.vue'

// 横幅显示控制
const showBanner = ref(true)

// 打开 Gitee 链接
const openGitee = () => {
  window.open('https://gitee.com/leven2018/lanjii', '_blank')
}

// 关闭横幅
const closeBanner = () => {
  showBanner.value = false
}

// 系统统计数据
const systemStats = [
  {
    icon: 'User',
    value: '1,248',
    label: '用户总数',
    color: '#409EFF',
    bgColor: 'rgba(64, 158, 255, 0.1)'
  },
  {
    icon: 'Document',
    value: '3,567',
    label: '今日访问量',
    color: '#67C23A',
    bgColor: 'rgba(103, 194, 58, 0.1)'
  },
  {
    icon: 'TrendCharts',
    value: '892',
    label: '本月新增',
    color: '#E6A23C',
    bgColor: 'rgba(230, 162, 60, 0.1)'
  },
  {
    icon: 'DataLine',
    value: '¥28,650',
    label: '本月收入',
    color: '#F56C6C',
    bgColor: 'rgba(245, 108, 108, 0.1)'
  }
]

const orderSalesMonths = ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
const orderSalesData = [1200, 1560, 980, 1890, 2100, 2340, 1980, 2450, 2680, 3010, 3290, 3650]

const todoList = [
  {title: '处理待审核订单', priority: '高', status: '待处理'},
  {title: '核对今日退款记录', priority: '中', status: '进行中'},
  {title: '补齐商品基础信息', priority: '低', status: '待处理'},
  {title: '更新系统参数配置', priority: '中', status: '已完成'}
]

const noticeList = [
  {time: '10:20', title: '系统更新', content: '控制台图表模块已升级为 ECharts。'},
  {time: '09:10', title: '安全提示', content: '建议定期更换管理员密码并开启二次校验。'},
  {time: '昨日', title: '运营提醒', content: '本月订单销量趋势上升，建议关注库存预警。'}
]

const barOption = computed(() => {
  return {
    tooltip: {trigger: 'axis' as const},
    grid: {left: 40, right: 20, top: 40, bottom: 30, containLabel: true},
    xAxis: {
      type: 'category' as const,
      data: orderSalesMonths,
      axisTick: {alignWithLabel: true}
    },
    yAxis: {
      type: 'value' as const,
      name: '销量(单)'
    },
    series: [
      {
        name: '订单销量',
        type: 'bar' as const,
        data: orderSalesData,
        barMaxWidth: 28,
        itemStyle: {color: '#409EFF', borderRadius: [4, 4, 0, 0]}
      }
    ]
  }
})

const lineOption = computed(() => {
  return {
    tooltip: {trigger: 'axis' as const},
    grid: {left: 40, right: 20, top: 40, bottom: 30, containLabel: true},
    xAxis: {type: 'category' as const, data: orderSalesMonths},
    yAxis: {type: 'value' as const, name: '销量(单)'},
    series: [
      {
        name: '订单销量',
        type: 'line' as const,
        data: orderSalesData,
        smooth: true,
        symbol: 'circle',
        symbolSize: 8,
        lineStyle: {width: 3, color: '#67C23A'},
        itemStyle: {color: '#67C23A'},
        areaStyle: {color: 'rgba(103, 194, 58, 0.15)'}
      }
    ]
  }
})

</script>


<style scoped lang="scss">

.mt-20 {
  margin-top: 20px;
}

/* 开源项目横幅样式 */
.opensource-banner {
  background: linear-gradient(135deg, var(--lj-banner-bg-start) 0%, var(--lj-banner-bg-end) 100%);
  border: 1px solid var(--lj-banner-border);
  border-radius: 12px;
  margin-bottom: 20px;
  padding: 24px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.04);
  position: relative;
  overflow: hidden;

  &::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: url('data:image/svg+xml,<svg width="60" height="60" viewBox="0 0 60 60" xmlns="http://www.w3.org/2000/svg"><g fill="none" fill-rule="evenodd"><g fill="%23409eff" fill-opacity="0.02"><circle cx="30" cy="30" r="2"/></g></svg>') repeat;
    pointer-events: none;
  }
}

.banner-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
  position: relative;
  z-index: 1;
}

.banner-left {
  display: flex;
  align-items: center;
  gap: 20px;
  flex: 1;
}

.banner-icon {
  width: 64px;
  height: 64px;
  background: rgba(64, 158, 255, 0.1);
  border: 1px solid rgba(64, 158, 255, 0.2);
  border-radius: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.banner-info {
  flex: 1;
}

.banner-title {
  color: var(--lj-text-primary);
  font-size: 20px;
  font-weight: 700;
  margin: 0 0 8px 0;
  line-height: 1.3;
}

.project-name {
  color: #409EFF;
  font-size: 24px;
  font-weight: 800;
  text-shadow: 0 1px 2px rgba(64, 158, 255, 0.1);
}

.banner-description {
  color: var(--lj-text-secondary);
  font-size: 14px;
  margin: 0 0 8px 0;
  line-height: 1.5;
}

.license-note {
  color: var(--lj-text-placeholder);
  font-size: 12px;
  margin: 0 0 12px 0;
  font-style: italic;
}

.tech-stack {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
  
  .el-tag {
    font-weight: 500;
    
    &.el-tag--primary {
      background: rgba(64, 158, 255, 0.1);
      border-color: rgba(64, 158, 255, 0.3);
      color: #409EFF;
    }
    
    &.el-tag--success {
      background: rgba(103, 194, 58, 0.1);
      border-color: rgba(103, 194, 58, 0.3);
      color: #67C23A;
    }
    
    &.el-tag--warning {
      background: rgba(230, 162, 60, 0.1);
      border-color: rgba(230, 162, 60, 0.3);
      color: #E6A23C;
    }
    
    &.el-tag--info {
      background: rgba(144, 147, 153, 0.1);
      border-color: rgba(144, 147, 153, 0.3);
      color: #909399;
    }
  }
}

.banner-right {
  display: flex;
  align-items: center;
  gap: 12px;
  flex-shrink: 0;
}

.star-button {
  background: #409EFF;
  color: #ffffff;
  border: none;
  font-weight: 600;
  padding: 12px 24px;
  border-radius: 8px;
  transition: all 0.3s ease;
  
  &:hover {
    background: #337ecc;
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(64, 158, 255, 0.3);
  }
  
  .el-icon {
    margin-right: 6px;
  }
}

.close-button {
  color: var(--lj-text-placeholder);
  
  &:hover {
    color: var(--lj-text-secondary);
    background: rgba(148, 163, 184, 0.1);
  }
}

/* 统计卡片样式 */
.stat-card {
  border: none;
  transition: all 0.3s ease;

  &:hover {
    transform: translateY(-4px);
    box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
  }

  :deep(.el-card__body) {
    padding: 24px;
  }
}

.stat-content {
  display: flex;
  align-items: center;
  gap: 16px;
}

.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.stat-info {
  flex: 1;
}

.stat-value {
  font-size: 28px;
  font-weight: 700;
  margin-bottom: 4px;
  line-height: 1;
}

.stat-label {
  font-size: 14px;
  color: var(--lj-text-secondary);
  font-weight: 500;
}

/* 卡片头部样式 */
.card-header {
  display: flex;
  align-items: center;
  gap: 8px;

  h3 {
    margin: 0;
    font-size: 18px;
    font-weight: 600;
    color: var(--lj-text-primary);
  }
}

/* 图表卡片样式 */
.chart-card {
  height: 400px;

  :deep(.el-card__body) {
    height: calc(100% - 60px);
    padding: 20px;
  }
}

.chart-content {
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.panel-card {
  :deep(.el-card__body) {
    padding: 16px;
  }
}

.notice-timeline {
  padding-left: 4px;
}

.notice-title {
  font-size: 14px;
  font-weight: 600;
  color: var(--lj-text-primary);
  margin-bottom: 4px;
}

.notice-content {
  font-size: 13px;
  color: var(--lj-text-secondary);
  line-height: 1.6;
}

/* 响应式适配 */
@media (max-width: 992px) {
  .opensource-banner {
    padding: 16px;
  }

  .banner-content {
    flex-direction: column;
    align-items: flex-start;
    gap: 16px;
  }

  .banner-left {
    flex-direction: column;
    align-items: flex-start;
    gap: 12px;
  }

  .banner-right {
    width: 100%;

    .star-button {
      flex: 1;
    }
  }

  .stat-card {
    margin-bottom: 12px;
  }

  .chart-card {
    margin-bottom: 20px;
  }

  .panel-card {
    margin-bottom: 20px;
  }
}

@media (max-width: 768px) {
  .banner-icon {
    width: 48px;
    height: 48px;
    border-radius: 12px;
  }

  .banner-title {
    font-size: 16px;
  }

  .project-name {
    font-size: 20px;
  }

  .stat-value {
    font-size: 22px;
  }

  .stat-icon {
    width: 48px;
    height: 48px;
    border-radius: 10px;
  }

  .card-header h3 {
    font-size: 15px;
  }

  .chart-card {
    height: 320px;

    :deep(.el-card__body) {
      height: calc(100% - 56px);
      padding: 12px;
    }
  }
}

</style>
