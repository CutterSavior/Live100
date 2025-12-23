<template>

  <SearchBar v-if="searchItems && searchItems.length > 0" :search-items="searchItems" @search="handleSearch"></SearchBar>

  <div class="card-box table-card">
    <!-- 工具栏区域 -->
    <div class="table-toolbar">
      <div class="left">
        <slot name="toolbar"></slot>
      </div>
      <div class="right">
        <el-button :icon="Refresh" circle @click="refreshTable"/>
        <el-button v-if="searchItems && searchItems.length > 0"  :icon="Search" circle @click="toggleSearchBar"/>
        <el-popover placement="bottom-end" width="200" trigger="click">
          <template #reference>
            <el-button :icon="Setting" circle/>
          </template>
          <el-checkbox-group v-model="selectedColumns">
            <el-checkbox
                v-for="column in columns"
                :key="column.prop"
                :value="column.prop"
                :label="column.label"
            />
          </el-checkbox-group>
        </el-popover>
      </div>
    </div>

    <!-- 表格主体 -->
    <el-table
        :data="tableData"
        border
        style="width: 100%"
        v-bind="$attrs"
        :row-key="treeConfig.rowKey"
        :tree-props="isTreeTable ? treeConfig.treeProps : undefined"
        v-loading="loading"
        element-loading-text="加载中..."
    >
      <el-table-column
          v-for="column in visibleColumns"
          :key="column.prop || column.type"
          :type="column.type"
          :prop="column.prop"
          :label="column.label"
          :width="column.width"
          :min-width="column.minWidth"
          :fixed="column.fixed"
          :sortable="column.sortable"
          :align="column.align"
      >
        <template v-if="!column.type" #default="scope">
          <slot :name="column.prop || 'default'" :row="scope.row">
            {{ column.prop ? scope.row[column.prop] : '' }}
          </slot>
        </template>
      </el-table-column>

      <!-- 操作栏 -->
      <el-table-column
          v-if="$slots['action-column']"
          fixed="right"
          label="操作"
          :width="computedActionColumnWidth"
          align="center"
      >
        <template #default="scope">
          <slot name="action-column" :row="scope.row"/>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div v-if="showPagination" class="pagination-container">
      <el-pagination
          :current-page="currentPage"
          :page-size="pageSize"
          :page-sizes="pageSizes"
          :total="total"
          :layout="paginationLayout"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import {computed, onMounted, ref, watchEffect} from 'vue';
import {Refresh, Search, Setting} from '@element-plus/icons-vue';
import {useGlobalSettingStore} from '@/stores/globalSetting.store';
import type {TableColumn} from '@/types/table';
import SearchBar from '@/components/SearchBar/SearchBar.vue';
import type {SearchItem} from "@/types/search.ts";

interface Props {
  searchItems?: SearchItem[];
  // 获取数据的函数
  fetchData: (params: any) => Promise<{ list: any[]; total: number }>;
  // 表格列配置
  columns: TableColumn[];
  // 当前页码
  currentPage?: number;
  // 每页显示条数
  pageSize?: number;
  // 每页显示条数选项
  pageSizes?: number[];
  // 是否显示分页
  showPagination?: boolean;
  // 分页布局
  paginationLayout?: string;
  // 是否为树形表格
  isTreeTable?: boolean;
  // 树形表格配置
  treeConfig?: {
    // 行数据的Key
    rowKey: string;
    // 树形属性配置
    treeProps: {
      children: string;
      hasChildren?: string;
    };
  };
  actionColumnWidth?: number | string;
}

const props = withDefaults(defineProps<Props>(), {
  searchItems: () => [],
  currentPage: 1,
  pageSize: 10,
  pageSizes: () => [10, 20, 30, 50],
  showPagination: true,
  paginationLayout: 'total, sizes, prev, pager, next, jumper',
  isTreeTable: false,
  treeConfig: () => ({
    rowKey: 'id',
    treeProps: {
      children: 'children',
      hasChildren: 'hasChildren'
    }
  }),
  actionColumnWidth: 230
});

const emit = defineEmits<{
  (e: 'sizeChange', size: number): void;
  (e: 'currentChange', page: number): void;
  (e: 'update:currentPage', page: number): void;
  (e: 'update:pageSize', size: number): void;
}>();

const globalSettingStore = useGlobalSettingStore();
const loading = ref(false);
const total = ref(0);
const tableData = ref<any[]>([]);
const selectedColumns = ref<string[]>([]);
const currentPage = ref(props.currentPage);
const pageSize = ref(props.pageSize);
const searchParams = ref({});

// 初始化选中所有列
watchEffect(() => {
  selectedColumns.value = props.columns.map(column => column.prop || column.type || '').filter(Boolean);
});

// 计算可见列
const visibleColumns = computed(() => {
  return props.columns.filter(column => {
    const key = column.prop || column.type || '';
    return selectedColumns.value.includes(key);
  });
});

const computedActionColumnWidth = computed(() => {
  const width = typeof props.actionColumnWidth === 'string' 
    ? parseInt(props.actionColumnWidth) 
    : props.actionColumnWidth;
  
  return Math.max(80, Math.min(230, width));
});

// 搜索
const handleSearch = (params: any) => {
  searchParams.value = {...params};
  currentPage.value = 1;
  emit('update:currentPage', 1);
  refreshTable();
}

// 刷新表格数据
const refreshTable = async () => {
  try {
    loading.value = true;
    const {list, total: totalCount} = await props.fetchData({
      pageNum: currentPage.value,
      pageSize: pageSize.value,
      ...searchParams.value
    });
    tableData.value = list;
    total.value = totalCount;
  } catch (error) {
    // 可以在这里添加错误处理逻辑，比如显示错误提示
    tableData.value = [];
    total.value = 0;
  } finally {
    loading.value = false;
  }
};

// 切换搜索栏显示
const toggleSearchBar = () => {
  globalSettingStore.isHiddenSearch = !globalSettingStore.isHiddenSearch;
};

// 分页大小变化
const handleSizeChange = (size: number) => {
  pageSize.value = size;
  currentPage.value = 1; // 切换每页条数时通常回到第一页
  emit('update:pageSize', size);
  refreshTable();
};

// 当前页变化
const handleCurrentChange = (page: number) => {
  currentPage.value = page;
  emit('update:currentPage', page);
  refreshTable();
};

// 暴露方法给父组件
defineExpose({
  refreshTable,
  getSearchParams: () => searchParams.value,
});

// 加载数据
onMounted(() => {
  refreshTable()
})
</script>

<style scoped lang="scss">
@use "index";
</style>