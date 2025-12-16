import {fileURLToPath, URL} from 'node:url'

import {defineConfig, loadEnv} from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

// https://vite.dev/config/
export default defineConfig(({mode}) => {
    // 加载环境变量
    const env = loadEnv(mode, process.cwd())
    
    return {
    // SockJS/STOMP 库内部使用了 global，而浏览器只有 window
    define: {
        global: 'window'
    },
    plugins: [
        vue(),
        vueDevTools(),
    ],
    resolve: {
        alias: {
            '@': fileURLToPath(new URL('./src', import.meta.url))
        },
    },
    css: {
        preprocessorOptions: {
            scss: {
                additionalData: `@use "@/assets/style/variables.scss";`
            }
        }
    },
    server: {
        port: 1001,
        host: '0.0.0.0',
        proxy: {
            '/api': {
                target: env.VITE_API_BASE_URL,
                changeOrigin: true,
                rewrite: (path) => path.replace(/^\/api/, '')
            }
        }
    }
    }
})
