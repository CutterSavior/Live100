<script setup lang="ts">
import {computed, ref} from 'vue'
import {ArrowDown, ArrowUp, Refresh, Search} from '@element-plus/icons-vue'
import type {SearchItem} from '@/types/search.ts'
import {useGlobalSettingStore} from "@/stores/globalSetting.store";

// 定义props
const props = defineProps({
  // 搜索项配置
  searchItems: {
    type: Array as () => SearchItem[],
    default: () => []
  },
  // 默认展示的项目数量，超过此数量的将被折叠
  defaultShownCount: {
    type: Number,
    default: 3
  }
})

// 定义事件
const emit = defineEmits(['search'])

// 根据searchItems自动生成queryParams
const queryParams = ref<Record<string, any>>({})

// 初始化queryParams
const initQueryParams = () => {
  const params: Record<string, any> = {}
  const items: SearchItem[] = Array.isArray(props.searchItems) ? props.searchItems : [];
  items.forEach((item: SearchItem) => {
    // 根据不同类型设置初始值
    switch (item.type) {
      case 'daterange':
        params[item.field] = []
        break
      case 'select':
      case 'date':
      case 'input':
      default:
        params[item.field] = undefined
    }
  })
  return params
}

// 初始化queryParams
queryParams.value = initQueryParams()

// 折叠状态
const isExpanded = ref(false)
const toggleExpand = () => {
  isExpanded.value = !isExpanded.value
}

// 处理搜索
const handleSearch = () => {
  emit('search', queryParams.value)
}

const handleReset = () => {
  queryParams.value = initQueryParams()
  emit('search', queryParams.value)
}

// 是否需要折叠
const needCollapse = computed(() => {
  return props.searchItems.length > 4
})

// 计算属性：始终显示的搜索项
const shownItems = computed(() => {
  // 如果总数量小于等于4，全部显示
  if (props.searchItems.length <= 4) {
    return props.searchItems
  }

  // 否则显示前3个
  return props.searchItems.slice(0, props.defaultShownCount)
})

// 计算属性：被折叠的搜索项
const collapsedItems = computed(() => {
  if (props.searchItems.length <= 4) {
    return []
  }

  return props.searchItems.slice(props.defaultShownCount)
})

// 第一个折叠项（用于展开时替换搜索按钮位置）
const firstCollapsedItem = computed(() => {
  return collapsedItems.value.length > 0 ? collapsedItems.value[0] : null
})

// 其余折叠项（除了第一个之外的所有折叠项）
const remainingCollapsedItems = computed(() => {
  return collapsedItems.value.length > 1 ? collapsedItems.value.slice(1) : []
})

// 辅助计算，将剩余折叠项分成每行4个的数组
const chunkedRemainingItems = computed(() => {
  const result = []
  for (let i = 0; i < remainingCollapsedItems.value.length; i += 4) {
    result.push(remainingCollapsedItems.value.slice(i, i + 4))
  }
  return result
})

// 最后一行的项目（用于确定搜索按钮的位置）
const lastRowItems = computed(() => {
  if (!isExpanded.value) {
    return []
  }

  if (chunkedRemainingItems.value.length === 0) {
    // 如果只有替换项，没有其他折叠项
    return [firstCollapsedItem.value]
  }

  return chunkedRemainingItems.value[chunkedRemainingItems.value.length - 1]
})

// 最后一行项目的数量
const lastRowItemCount = computed(() => {
  return lastRowItems.value.length
})
</script>

<template>
  <div v-if="useGlobalSettingStore().isHiddenSearch && props.searchItems && props.searchItems.length > 0" class="search-card card-box">
    <el-form :model="queryParams" ref="searchFormRef" class="search-form" label-width="60px">
      <!-- 第一行：始终显示的项目 -->
      <el-row :gutter="16">
        <!-- 显示的搜索条件 -->
        <el-col
            v-for="(item, index) in shownItems"
            :key="index"
            :span="6"
        >
          <el-form-item :label="item.label" label-width="100">
            <!-- 输入框 -->
            <el-input
                v-if="item.type === 'input'"
                v-model="queryParams[item.field]"
                :placeholder="item.placeholder || `请输入${item.label}`"
                clearable
            />

            <!-- 选择框 -->
            <el-select
                v-else-if="item.type === 'select'"
                v-model="queryParams[item.field]"
                :placeholder="item.placeholder || `请选择${item.label}`"
                clearable
                style="width: 100%"
            >
              <el-option
                  v-for="option in item.options"
                  :key="option.value"
                  :label="option.label"
                  :value="option.value"
              />
            </el-select>

            <!-- 日期选择器 -->
            <el-date-picker
                v-else-if="item.type === 'date'"
                v-model="queryParams[item.field]"
                type="date"
                :placeholder="item.placeholder || `请选择${item.label}`"
                style="width: 100%"
                value-format="YYYY-MM-DD"
            />

            <!-- 日期范围选择器 -->
            <el-date-picker
                v-else-if="item.type === 'daterange'"
                v-model="queryParams[item.field]"
                type="daterange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                style="width: 100%"
                value-format="YYYY-MM-DD"
            />
          </el-form-item>
        </el-col>

        <!-- 未展开时的搜索按钮或展开时的第一个折叠项 -->
        <el-col :span="6">
          <!-- 未展开时显示搜索按钮 -->
          <template v-if="!isExpanded">
            <el-form-item class="action-buttons" label-width="">
              <el-button type="primary" :icon="Search" @click="handleSearch">搜索</el-button>
              <el-button :icon="Refresh" @click="handleReset">重置</el-button>

              <!-- 展开按钮 -->
              <el-button
                  v-if="needCollapse"
                  type="text"
                  @click="toggleExpand"
                  class="expand-btn"
              >
                <span>展开</span>
                <el-icon>
                  <ArrowDown/>
                </el-icon>
              </el-button>
            </el-form-item>
          </template>

          <!-- 展开时显示第一个折叠项 -->
          <template v-else-if="firstCollapsedItem">
            <el-form-item :label="firstCollapsedItem.label" label-width="100">
              <!-- 输入框 -->
              <el-input
                  v-if="firstCollapsedItem.type === 'input'"
                  v-model="queryParams[firstCollapsedItem.field]"
                  :placeholder="firstCollapsedItem.placeholder || `请输入${firstCollapsedItem.label}`"
                  clearable
              />

              <!-- 选择框 -->
              <el-select
                  v-else-if="firstCollapsedItem.type === 'select'"
                  v-model="queryParams[firstCollapsedItem.field]"
                  :placeholder="firstCollapsedItem.placeholder || `请选择${firstCollapsedItem.label}`"
                  clearable
                  style="width: 100%"
              >
                <el-option
                    v-for="option in firstCollapsedItem.options"
                    :key="option.value"
                    :label="option.label"
                    :value="option.value"
                />
              </el-select>

              <!-- 日期选择器 -->
              <el-date-picker
                  v-else-if="firstCollapsedItem.type === 'date'"
                  v-model="queryParams[firstCollapsedItem.field]"
                  type="date"
                  :placeholder="firstCollapsedItem.placeholder || `请选择${firstCollapsedItem.label}`"
                  style="width: 100%"
                  value-format="YYYY-MM-DD"
              />

              <!-- 日期范围选择器 -->
              <el-date-picker
                  v-else-if="firstCollapsedItem.type === 'daterange'"
                  v-model="queryParams[firstCollapsedItem.field]"
                  type="daterange"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                  style="width: 100%"
                  value-format="YYYY-MM-DD"
              />
            </el-form-item>
          </template>
        </el-col>
      </el-row>

      <!-- 展开时显示的其他折叠项 -->
      <template v-if="isExpanded && remainingCollapsedItems.length > 0">
        <el-row
            :gutter="16"
            v-for="(rowItems, rowIndex) in chunkedRemainingItems"
            :key="rowIndex"
            :class="{ 'last-row': rowIndex === chunkedRemainingItems.length - 1 }"
        >
          <!-- 展示搜索项 -->
          <template v-for="(item, colIndex) in rowItems" :key="colIndex">
            <el-col :span="6">
              <el-form-item :label="item.label" label-width="100">
                <!-- 输入框 -->
                <el-input
                    v-if="item.type === 'input'"
                    v-model="queryParams[item.field]"
                    :placeholder="item.placeholder || `请输入${item.label}`"
                    clearable
                />

                <!-- 选择框 -->
                <el-select
                    v-else-if="item.type === 'select'"
                    v-model="queryParams[item.field]"
                    :placeholder="item.placeholder || `请选择${item.label}`"
                    clearable
                    style="width: 100%"
                >
                  <el-option
                      v-for="option in item.options"
                      :key="option.value"
                      :label="option.label"
                      :value="option.value"
                  />
                </el-select>

                <!-- 日期选择器 -->
                <el-date-picker
                    v-else-if="item.type === 'date'"
                    v-model="queryParams[item.field]"
                    type="date"
                    :placeholder="item.placeholder || `请选择${item.label}`"
                    style="width: 100%"
                    value-format="YYYY-MM-DD"
                />

                <!-- 日期范围选择器 -->
                <el-date-picker
                    v-else-if="item.type === 'daterange'"
                    v-model="queryParams[item.field]"
                    type="daterange"
                    range-separator="至"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期"
                    style="width: 100%"
                    value-format="YYYY-MM-DD"
                />
              </el-form-item>
            </el-col>

            <!-- 在最后一行最后一个元素后添加搜索按钮 -->
            <template v-if="rowIndex === chunkedRemainingItems.length - 1 && colIndex === rowItems.length - 1">
              <el-col :span="6">
                <el-form-item class="action-buttons" label-width="">
                  <el-button type="primary" :icon="Search" @click="handleSearch">搜索</el-button>
                  <el-button :icon="Refresh" @click="handleReset">重置</el-button>

                  <!-- 收起按钮 -->
                  <el-button
                      type="text"
                      @click="toggleExpand"
                      class="expand-btn"
                  >
                    <span>收起</span>
                    <el-icon>
                      <ArrowUp/>
                    </el-icon>
                  </el-button>
                </el-form-item>
              </el-col>
            </template>
          </template>
        </el-row>
      </template>

      <!-- 当没有其他折叠项但有第一个折叠项时，搜索按钮紧跟在第一个折叠项后面 -->
      <template v-if="isExpanded && remainingCollapsedItems.length === 0 && firstCollapsedItem">
        <el-row :gutter="16">
          <el-col :span="6">
            <el-form-item class="action-buttons" label-width="">
              <el-button type="primary" :icon="Search" @click="handleSearch">搜索</el-button>
              <el-button :icon="Refresh" @click="handleReset">重置</el-button>

              <!-- 收起按钮 -->
              <el-button
                  type="text"
                  @click="toggleExpand"
                  class="expand-btn"
              >
                <span>收起</span>
                <el-icon>
                  <ArrowUp/>
                </el-icon>
              </el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </template>
    </el-form>
  </div>
</template>

<style scoped lang="scss">
@use "index.scss";
</style>