import '@/assets/style/index.scss'
import '@wangeditor/editor/dist/css/style.css'

import {createApp} from 'vue'

import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import {createPinia} from 'pinia'
import ElementPlus from 'element-plus'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import 'element-plus/theme-chalk/dark/css-vars.css'
import 'element-plus/dist/index.css'

import App from './App.vue'
import router, {addDynamicRoutes} from './router'

// 导入自定义指令
import { setupDirectives } from '@/directives'

const app = createApp(App)

const pinia = createPinia();
pinia.use(piniaPluginPersistedstate)
app.use(pinia)

app.use(ElementPlus, {
    locale: zhCn
})

// 注册 Element Plus 图标
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

addDynamicRoutes()

app.use(router)

// 注册所有自定义指令
setupDirectives(app)

app.mount('#app')
