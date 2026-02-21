<template>
  <RouterView v-slot="{ Component }">
    <transition name="app-fade" mode="out-in" appear>
      <component :is="Component"/>
    </transition>
  </RouterView>
</template>

<script setup lang="ts">
import {onMounted, watch} from 'vue';
import {useGlobalSettingStore} from '@/stores/globalSetting.store';
import {useUserStore} from '@/stores/user.store';
import {useWebSocket} from '@/composables/useWebSocket';
import {switchTheme} from '@/utils/themeSwitch';

const globalSettingStore = useGlobalSettingStore();
const userStore = useUserStore();
const {changePrimary, switchDark, switchGrayscale, switchColorBlind} = switchTheme();

const {connect, disconnect} = useWebSocket();

// 应用主题设置
const applyThemeSettings = () => {
  // 应用主色调
  changePrimary(globalSettingStore.primary);

  // 应用暗黑模式
  if (globalSettingStore.isDark) {
    switchDark();
  }

  // 应用灰色模式
  if (globalSettingStore.isGrayscale) {
    switchGrayscale();
  }

  // 应用色弱模式
  if (globalSettingStore.isColorBlind) {
    switchColorBlind();
  }
};

// 监听用户登录状态，自动连接/断开WebSocket
watch(() => userStore.isLoggedIn, (isLoggedIn) => {
  if (isLoggedIn) {
    console.log('用户已登录，建立WebSocket连接');
    connect();
  } else {
    console.log('用户已退出，断开WebSocket连接');
    disconnect();
  }
}, {immediate: true});

// 监听窗口大小变化并初始化应用
onMounted(() => {
  // 应用保存的主题设置
  applyThemeSettings();
});

</script>

<style>
.app-fade-enter-active,
.app-fade-leave-active {
  transition: opacity 0.3s ease, transform 0.3s ease;
}

.app-fade-enter-from {
  opacity: 0;
  transform: translateX(30px);
}

.app-fade-leave-to {
  opacity: 0;
  transform: translateX(-30px);
}
</style>
