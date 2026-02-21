<template>
  <div class="logo" :class="{ 
    'collapsed': globalSettingStore.isCollapse && globalSettingStore.layoutMode === 'vertical',
    'layout-classic': globalSettingStore.layoutMode === 'classic'
  }">
    <img alt="Logo" class="logo-img" src="@/assets/logo.svg"/>
    <span v-if="!globalSettingStore.isCollapse || globalSettingStore.layoutMode === 'classic'" class="logo-text">岚迹</span>
  </div>
</template>
<script lang="ts" setup>
import {useGlobalSettingStore} from "@/stores/globalSetting.store";

const globalSettingStore = useGlobalSettingStore()
</script>

<style scoped lang="scss">
@use "index";
</style>