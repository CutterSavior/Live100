<template>
  <router-view v-slot="{ Component, route }">
    <transition name="animation" mode="out-in" appear>
      <keep-alive v-if="route.meta?.isKeepAlive">
        <component :is="Component" />
      </keep-alive>
      <component v-else :is="Component" />
    </transition>
  </router-view>
</template>

<script setup lang="ts">
// 页面过渡效果组件
</script>

<style scoped>
/* 过度动画配置代码 */
.animation-enter-from,
.animation-leave-to {
  transform: translateX(20px);
  opacity: 0;
}

.animation-enter-to,
.animation-leave-from {
  opacity: 1;
}

.animation-enter-active {
  transition: all 0.3s ease;
}

.animation-leave-active {
  transition: all 0.3s cubic-bezier(1, 0.6, 0.6, 1);
}
</style>