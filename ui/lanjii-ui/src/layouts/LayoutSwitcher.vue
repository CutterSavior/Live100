<template>
  <div class="layout-switcher">
    <component :is="LayoutComponents[layout]"/>
    <AppearanceDrawer/>
  </div>
</template>

<script setup lang="ts">

import {useGlobalSettingStore} from "@/stores/globalSetting.store";
import {type Component, computed} from "vue";
import LayoutVertical from "@/layouts/LayoutVertical/LayoutVertical.vue"
import LayoutClassic from "@/layouts/LayoutClassic/LayoutClassic.vue"
import LayoutTransverse from "@/layouts/LayoutTransverse/LayoutTransverse.vue"
import LayoutColumns from "@/layouts/LayoutColumns/LayoutColumns.vue"
import type {LayoutModelType} from "@/types/store";
import AppearanceDrawer from "@/layouts/components/BaseHeader/components/AppearanceDrawer.vue";

const LayoutComponents: Record<LayoutModelType, Component> = {
  vertical: LayoutVertical,
  classic: LayoutClassic,
  transverse: LayoutTransverse,
  columns: LayoutColumns
};


const globalSettingStore = useGlobalSettingStore();
const layout = computed(() => globalSettingStore.layoutMode as LayoutModelType);

</script>

<style scoped>
.layout-switcher {
  height: 100%;
  width: 100%;
}
</style>
