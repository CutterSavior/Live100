<template>
  <el-container class="layout-container">
    <el-aside :style="{ width: globalSettingStore.isCollapse ?  '64px':'200px' }">
      <Logo/>
      <BaseAside/>
    </el-aside>
    <el-container class="right-container scroll-area">
      <el-header class="header">
        <BaseHeader/>
      </el-header>
      <TabsView/>
      <el-main>
        <Main/>
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
import Main from "@/layouts/components/Main/Main.vue";
import BaseAside from "@/layouts/components/BaseAside/BaseAside.vue";
import BaseHeader from "@/layouts/components/BaseHeader/BaseHeader.vue";
import Logo from "@/layouts/components/Logo/Logo.vue";
import TabsView from "@/components/TabsView/TabsView.vue";

import {useGlobalSettingStore} from '@/stores/globalSetting.store';

const globalSettingStore = useGlobalSettingStore();
</script>

<style scoped lang="scss">
@use "index.scss";

.layout-container {
  height: 100%;
}
</style>
