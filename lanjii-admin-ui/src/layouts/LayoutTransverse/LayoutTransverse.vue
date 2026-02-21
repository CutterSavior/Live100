<template>
  <div class="common-layout">
    <el-container class="right-container scroll-area">
      <el-header class="header">
        <Logo/>
        <div class="header-content">
          <BaseAside mode="horizontal"/>
          <RightBar/>
        </div>
      </el-header>
      <TabsView/>
      <el-main>
        <Main/>
      </el-main>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import Main from "@/layouts/components/Main/Main.vue";
import BaseAside from "@/layouts/components/BaseAside/BaseAside.vue";
import TabsView from "@/components/TabsView/TabsView.vue";
import RightBar from "@/layouts/components/BaseHeader/components/RightBar.vue";
import Logo from "@/layouts/components/Logo/Logo.vue";
</script>

<style scoped lang="scss">
@use "index.scss";
</style>
