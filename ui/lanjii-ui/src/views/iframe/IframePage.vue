<!-- src/views/ExternalLinkWrapper.vue -->
<template>
  <div class="main-content">
    <iframe
        v-if="url"
        :src="url"
        frameborder="0"
        class="external-iframe"
        @load="iframeLoaded = true"
    ></iframe>
    <div v-if="!iframeLoaded" class="loading-container">
      <el-icon class="loading-icon">
        <Loading/>
      </el-icon>
      <span>加载中...</span>
    </div>
  </div>
</template>

<script setup>
import {onMounted, ref, watch} from 'vue'
import {useRoute} from 'vue-router'
import {Loading} from '@element-plus/icons-vue'

const route = useRoute()
const url = ref('')
const iframeLoaded = ref(false)

onMounted(() => {
  url.value = route.meta.url
})
watch(() => route.path, () => {
  url.value = route.meta.url
});
</script>

<style scoped>

.main-content{
  height: calc(100vh - 141px);
}
.external-iframe {
  width: 100%;
  height: 100%;
  min-height: calc(100vh - 50px);
}

.loading-container {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
}

.loading-icon {
  font-size: 30px;
  animation: rotating 2s linear infinite;
}

@keyframes rotating {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
}
</style>