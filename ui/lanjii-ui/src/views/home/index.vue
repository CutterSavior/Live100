<template>
  <div class="main-content">
    <!-- 系统统计 -->
    <el-row :gutter="20">
      <el-col :span="6" v-for="(stat, index) in systemStats" :key="index">
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
      <el-col :span="12">
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
      <el-col :span="12">
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
      <el-col :span="12">
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

      <el-col :span="12">
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
import {DataLine, Lightning, Star, TrendCharts} from '@element-plus/icons-vue'
import {computed} from 'vue'
import EChart from '@/components/EChart/index.vue'

// 系统统计数据
const systemStats = [
  {
    icon: 'User',
    value: '1,248',
    label: '用户总数',
    color: '#409EFF',
    bgColor: '#ecf5ff'
  },
  {
    icon: 'Document',
    value: '3,567',
    label: '今日访问量',
    color: '#67C23A',
    bgColor: '#f0f9ff'
  },
  {
    icon: 'TrendCharts',
    value: '892',
    label: '本月新增',
    color: '#E6A23C',
    bgColor: '#fdf6ec'
  },
  {
    icon: 'DataLine',
    value: '¥28,650',
    label: '本月收入',
    color: '#F56C6C',
    bgColor: '#fef0f0'
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
  color: #64748b;
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
    color: #1e293b;
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
  color: #1e293b;
  margin-bottom: 4px;
}

.notice-content {
  font-size: 13px;
  color: #64748b;
  line-height: 1.6;
}

</style>
