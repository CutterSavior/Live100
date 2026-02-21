<template>
  <div class="main-content error-page error-page-1">
    <!-- 背景装饰 -->
    <div class="bg-decoration">
      <div class="bg-circle circle-1"></div>
      <div class="bg-circle circle-2"></div>
      <div class="bg-circle circle-3"></div>
    </div>

    <!-- 主要内容 -->
    <div class="error-content">
      <div class="error-number">404</div>
      <h1 class="error-title">页面走丢了</h1>
      <p class="error-description">抱歉，您访问的页面不存在或已被移除</p>
      <div class="error-actions">
        <el-button type="primary" size="large" @click="goHome">
          <el-icon>
            <HomeFilled/>
          </el-icon>
          返回首页
        </el-button>
        <el-button size="large" @click="goBack">
          <el-icon>
            <ArrowLeft/>
          </el-icon>
          返回上一页
        </el-button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import {useRouter} from 'vue-router'
import {ArrowLeft, HomeFilled} from '@element-plus/icons-vue'

const router = useRouter()

const goHome = () => {
  router.push('/admin/index')
}

const goBack = () => {
  router.go(-1)
}
</script>

<style lang="scss" scoped>
.error-page-1 {
  position: relative;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, var(--el-color-primary-light-9) 0%, var(--el-color-primary-light-8) 100%);
  overflow: hidden;
  padding: 20px;
  box-sizing: border-box;

  .bg-decoration {
    position: absolute;
    width: 100%;
    height: 100%;
    top: 0;
    left: 0;
    pointer-events: none;

    .bg-circle {
      position: absolute;
      border-radius: 50%;
      background: rgba(var(--el-color-primary-rgb), 0.1);
      animation: float 20s infinite ease-in-out;

      &.circle-1 {
        width: 400px;
        height: 400px;
        top: -150px;
        left: -150px;
        animation-delay: 0s;
      }

      &.circle-2 {
        width: 300px;
        height: 300px;
        bottom: -100px;
        right: -100px;
        animation-delay: 2s;
      }

      &.circle-3 {
        width: 200px;
        height: 200px;
        top: 50%;
        right: 10%;
        animation-delay: 4s;
      }
    }

    @keyframes float {
      0%, 100% {
        transform: translate(0, 0) scale(1);
      }
      50% {
        transform: translate(30px, 30px) scale(1.1);
      }
    }
  }

  .error-content {
    position: relative;
    z-index: 2;
    text-align: center;
    animation: fadeInUp 0.8s ease-out;

    .error-number {
      font-size: 120px;
      font-weight: 700;
      line-height: 1;
      background: linear-gradient(135deg, var(--el-color-primary) 0%, var(--el-color-primary-light-3) 100%);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      background-clip: text;
      margin-bottom: 20px;
      text-shadow: 0 10px 30px rgba(var(--el-color-primary-rgb), 0.2);
      animation: numberPulse 2s ease-in-out infinite;
    }

    @keyframes numberPulse {
      0%, 100% {
        transform: scale(1);
      }
      50% {
        transform: scale(1.05);
      }
    }

    .error-title {
      font-size: 32px;
      font-weight: 600;
      color: var(--el-text-color-primary);
      margin-bottom: 16px;
    }

    .error-description {
      font-size: 16px;
      color: var(--el-text-color-regular);
      margin-bottom: 40px;
      line-height: 1.6;
    }

    .error-actions {
      display: flex;
      gap: 16px;
      justify-content: center;
      flex-wrap: wrap;
    }
  }

  @keyframes fadeInUp {
    from {
      opacity: 0;
      transform: translateY(30px);
    }
    to {
      opacity: 1;
      transform: translateY(0);
    }
  }
}

// 暗黑模式适配
html.dark {
  .error-page-1 {
    background: linear-gradient(135deg, rgba(0, 0, 0, 0.3) 0%, rgba(0, 0, 0, 0.5) 100%);
  }
}
</style>
