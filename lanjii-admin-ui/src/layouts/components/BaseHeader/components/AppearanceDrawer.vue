<template>
  <!-- 换肤界面 -->
  <el-drawer v-model="globalSettingStore.isDrawerOpen" size="300px">
    <template #header="{ close, titleId, titleClass }">
      <h4 :id="titleId" :class="titleClass">布局设置</h4>
    </template>
    <el-divider>
      <el-icon>
        <star-filled/>
      </el-icon>
      界面布局
    </el-divider>
    <div class="layout-box">
      <el-tooltip
          content="纵向"
          effect="dark"
          placement="top"
      >
        <div class="layout-vertical layout-item " :class="{'active': globalSettingStore.layoutMode=='vertical'}"
             @click="selectLayout('vertical')">
          <div class="layout-aside"></div>
          <div class="layout-container">
            <div class="layout-header"></div>
            <div class="layout-content"></div>
          </div>
          <el-icon v-if="globalSettingStore.layoutMode=='vertical'" class="active-icon">
            <SuccessFilled/>
          </el-icon>
        </div>

      </el-tooltip>
      <el-tooltip
          content="经典"
          effect="dark"
          placement="top"
      >
        <div class="layout-classic layout-item" :class="{'active':globalSettingStore.layoutMode=='classic'}"
             @click="selectLayout('classic')">
          <div class="layout-header"></div>
          <div class="layout-container">
            <div class="layout-aside"></div>
            <div class="layout-content"></div>
          </div>
          <el-icon v-if="globalSettingStore.layoutMode=='classic'" class="active-icon">
            <SuccessFilled/>
          </el-icon>
        </div>
      </el-tooltip>
      <el-tooltip
          content="横向"
          effect="dark"
          placement="top"
      >
        <div class="layout-transverse layout-item" :class="{'active':globalSettingStore.layoutMode=='transverse'}"
             @click="selectLayout('transverse')">
          <div class="layout-header"></div>
          <div class="layout-content"></div>
          <el-icon v-if="globalSettingStore.layoutMode=='transverse'" class="active-icon">
            <SuccessFilled/>
          </el-icon>
        </div>
      </el-tooltip>
      <el-tooltip
          content="分栏"
          effect="dark"
          placement="top"
      >
        <div class="layout-columns layout-item" :class="{'active':globalSettingStore.layoutMode=='columns'}"
             @click="selectLayout('columns')">
          <div class="layout-aside"></div>
          <div class="layout-header"></div>
          <div class="layout-content"></div>
          <el-icon v-if="globalSettingStore.layoutMode=='columns'" class="active-icon">
            <SuccessFilled/>
          </el-icon>
        </div>
      </el-tooltip>
    </div>
    <el-divider>
      <el-icon>
        <star-filled/>
      </el-icon>
      全局主题
    </el-divider>
    <div class="setting-item">
      <span>主题颜色</span>
      <el-color-picker v-model="globalSettingStore.primary" :predefine="predefineColors" @change="changePrimary"/>
    </div>
    <div class="setting-item">
      <span>暗黑模式</span>
      <el-switch
          v-model="globalSettingStore.isDark"
          @change="switchDark"
          active-text="启用"
          inactive-text="停用"
          inline-prompt
      />
    </div>

    <div class="setting-item">
      <span>灰色模式</span>
      <el-switch
          v-model="globalSettingStore.isGrayscale"
          @change="switchGrayscale"
          active-text="启用"
          inactive-text="停用"
          inline-prompt
      />
    </div>
    <div class="setting-item">
      <span>色弱模式</span>
      <el-switch
          v-model="globalSettingStore.isColorBlind"
          @change="switchColorBlind"
          active-text="启用"
          inactive-text="停用"
          inline-prompt
      />
    </div>
  </el-drawer>
</template>
<script lang="ts" setup>
import {ref} from "vue";
import {useGlobalSettingStore} from "@/stores/globalSetting.store";
import type {LayoutModelType} from "@/types/store";
import {switchTheme} from "@/utils/themeSwitch.ts";

const globalSettingStore = useGlobalSettingStore();
const predefineColors = ref([
  '#ff4500',
  '#ff8c00',
  '#ffd700',
  '#90ee90',
  '#00ced1',
  '#1e90ff',
  '#c71585',
])

const {changePrimary, switchDark, switchGrayscale, switchColorBlind} = switchTheme();

const selectLayout = (mode: LayoutModelType) => {
  globalSettingStore.layoutMode = mode;

}

</script>

<style scoped>

</style>