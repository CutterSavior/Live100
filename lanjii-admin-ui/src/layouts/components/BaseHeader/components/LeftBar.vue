<template>
  <!-- 头部左侧 -->
  <div class="left-header">
    <div class="collapse" @click="toggleCollapse">
      <el-icon>
        <template v-if="globalSettingStore.isCollapse">
          <Expand/>
        </template>
        <template v-else>
          <Fold/>
        </template>
      </el-icon>
    </div>
    <BaseBreadcrumb/>
  </div>
</template>
<script lang="ts" setup>
import {Expand, Fold} from "@element-plus/icons-vue";
import BaseBreadcrumb from "@/layouts/components/BaseBreadcrumb/BaseBreadcrumb.vue";
import {useGlobalSettingStore} from "@/stores/globalSetting.store";

const globalSettingStore = useGlobalSettingStore()

const toggleCollapse = () => {
  globalSettingStore.isCollapse = !globalSettingStore.isCollapse
}
</script>

<style scoped>

</style>