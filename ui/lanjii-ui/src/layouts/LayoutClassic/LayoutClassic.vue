<template>
  <div class="common-layout">
    <el-container>
      <el-header class="header">
        <Logo/>
        <LeftBar/>
        <RightBar/>
      </el-header>
      <el-container class="right-container scroll-area">
        <el-aside :style="{ width: globalSettingStore.isCollapse ?  '64px':'200px' }" class="aside">
          <BaseAside/>
        </el-aside>
        <el-container class="content-container">
          <TabsView/>
          <el-main>
            <Main/>
          </el-main>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import Main from "@/layouts/components/Main/Main.vue";
import BaseAside from "@/layouts/components/BaseAside/BaseAside.vue";
import Logo from "@/layouts/components/Logo/Logo.vue";
import TabsView from "@/components/TabsView/TabsView.vue";

import {useGlobalSettingStore} from '@/stores/globalSetting.store';
import LeftBar from "@/layouts/components/BaseHeader/components/LeftBar.vue";
import RightBar from "@/layouts/components/BaseHeader/components/RightBar.vue";

const globalSettingStore = useGlobalSettingStore();
</script>

<style scoped lang="scss">
@use "index.scss";

</style>
