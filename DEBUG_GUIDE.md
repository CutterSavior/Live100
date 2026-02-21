# Live100 前端错误诊断和修复指南

## 文件修改说明

本指南记录了针对 Live100 项目中出现的前端错误所做的修复。

### 修复的主要问题

1. **菜单数据为空，无法添加动态路由**
   - 位置：`lanjii-admin-ui/src/router/index.ts`
   - 原因：登录后菜单数据未正确获取或存储
   - 修复：改进了路由守卫的菜单检查逻辑

2. **用户已退出，断开WebSocket连接**
   - 位置：`lanjii-admin-ui/src/App.vue`
   - 原因：正常的日志输出（非错误）
   - 状态：无需修复，这是预期的行为

3. **获取验证码失败：Error: 登录已失效**
   - 位置：`lanjii-admin-ui/src/api/http.ts`
   - 原因：HTTP 401 错误处理不够清晰
   - 修复：改进了错误处理和日志记录

## 主要修改文件

### 1. `/lanjii-admin-ui/src/router/index.ts`

**修改内容：**
- ✅ 增强了 `addDynamicRoutes()` 函数的日志记录
- ✅ 改进了路由守卫 `router.beforeEach()` 的菜单检查逻辑
- ✅ 添加了从服务器重新获取菜单的备用方案
- ✅ 改进了错误处理和用户提示

**关键改进：**
```typescript
// 如果菜单为空，从服务器重新获取
if (!userStore.menus || userStore.menus.length === 0) {
    try {
        const { getUserMenus } = await import('@/api/modules/sys/menuApi')
        const response = await getUserMenus()
        userStore.setMenus(response.data || [])
    } catch (error) {
        console.error('从服务器获取菜单失败:', error)
    }
}
```

### 2. `/lanjii-admin-ui/src/views/login/index.vue`

**修改内容：**
- ✅ 增加了登录响应数据的验证
- ✅ 改进了详细的日志输出
- ✅ 添加了数据完整性检查
- ✅ 改进了错误信息显示

**关键改进：**
```typescript
// 验证必要的数据
if (!token) {
    throw new Error('登录失败: 未获取到Token')
}

// 设置菜单时添加空值检查
userStore.setMenus(menusTree || [])
```

### 3. `/lanjii-admin-ui/src/api/http.ts`

**修改内容：**
- ✅ 改进了请求拦截器的日志记录
- ✅ 改进了响应拦截器的错误处理
- ✅ 添加了更详细的上下文信息
- ✅ 改进了 401 错误处理的可见性

**关键改进：**
```typescript
// 请求拦截器中的日志
console.log('【HTTP】请求设置Token:', {
    url: config.url,
    tokenPrefix: `${token.substring(0, 20)}...`
})

// 响应中的日志
console.log('【HTTP】响应:', {
    url: response.config.url,
    code: res.code,
    msg: res.msg,
    dataLength: Array.isArray(res.data) ? res.data.length : 'N/A'
})
```

## 错误诊断方法

### 1. 检查浏览器控制台

打开浏览器开发者工具（F12），查看 Console 标签：

**预期看到的日志（成功流程）：**
```
【HTTP】请求设置Token: {url: "/admin/captcha", tokenPrefix: "ey..."}
【HTTP】响应: {url: "/admin/captcha", code: 200, msg: "success", dataLength: NaN}
【登录】✅ 登录成功
【登录】菜单数据: Array(n)
【登录】菜单数量: n
【路由守卫】检测到动态路由未添加，开始添加...
【路由添加】✅ 动态路由添加完成，新增子路由数: n
```

**如果出现错误日志：**
```
【路由添加】❌ 菜单数据为空，无法添加动态路由
【路由守卫】⚠️ 菜单数据为空，尝试从服务器重新获取...
```

### 2. 检查特定的问题

#### 问题：菜单数据为空

1. 打开浏览器开发者工具
2. 进行登录
3. 在 Console 中查找以下日志：
   - "【登录】菜单数据:" - 检查是否有菜单对象
   - "【路由添加】菜单数据:" - 检查 Store 中的菜单状态

**可能的原因：**
- 后端未返回菜单数据
- 网络请求失败
- 用户没有权限查看菜单

**排查步骤：**
1. 查看 Network 标签，找到 `/admin/login` 请求
2. 检查响应中的 `menusTree` 字段是否为空
3. 如果为空，检查用户权限和数据库中的菜单数据

#### 问题：登录已失效

1. 查看 Console 中的日志
2. 搜索 "【HTTP】❌ Token已过期或无效"
3. 检查以下信息：
   - Token 是否有效
   - Token 是否已过期
   - 后端是否返回 401 状态码

**排查步骤：**
1. 重新登录
2. 检查后端的 Token 生成和验证逻辑
3. 确保前后端的 Token 过期时间配置一致

## 测试验证清单

### 登录流程测试

- [ ] 1. 打开登录页面，验证验证码图片显示正常
- [ ] 2. 输入正确的用户名、密码和验证码
- [ ] 3. 检查 Console 日志中是否出现 "【登录】✅ 登录成功"
- [ ] 4. 检查菜单数据是否正确显示（菜单数量 > 0）
- [ ] 5. 等待路由守卫处理，检查是否跳转到首页
- [ ] 6. 验证菜单和导航栏正常显示

### 菜单路由测试

- [ ] 1. 登录后查看 Console 日志
- [ ] 2. 搜索 "【路由添加】✅ 动态路由添加完成"
- [ ] 3. 检查动态路由的数量是否与菜单数量匹配
- [ ] 4. 点击菜单项，验证能否正常导航
- [ ] 5. 刷新页面，验证路由保持正常

### WebSocket 连接测试

- [ ] 1. 登录后，检查 Console 查找 "✅ WebSocket 连接成功"
- [ ] 2. 查看通知功能是否正常
- [ ] 3. 用户登出后，验证 WebSocket 断开连接
- [ ] 4. 重新登录，验证 WebSocket 重新连开

## 日志解读

### 日志前缀含义

- `【HTTP】` - HTTP 请求/响应相关日志
- `【登录】` - 登录流程相关日志
- `【路由守卫】` - 路由导航守卫相关日志
- `【路由添加】` - 动态路由添加相关日志
- `【WebSocket】` - WebSocket 连接相关日志
- `❌` - 错误或失败
- `✅` - 成功或正确
- `⚠️` - 警告或需要注意

### 常见日志组合

**成功登录并加载菜单：**
```
【HTTP】响应: {url: "/admin/login", code: 200, ...}
【登录】✅ 登录成功
【登录】菜单数据: Array(10)
【路由添加】✅ 动态路由添加完成，新增子路由数: 8
```

**菜单数据为空：**
```
【路由添加】❌ 菜单数据为空，无法添加动态路由
【路由守卫】⚠️ 菜单数据为空，尝试从服务器重新获取...
【HTTP】✅ 请求成功: /admin/sys/menus/user
```

**Token 已过期：**
```
【HTTP】❌ Token已过期或无效
【HTTP】响应错误详情: {status: 401, msg: "登录已失效"}
【HTTP】❌ Token已过期或无效
```

## 后端相关检查

### 菜单查询逻辑 (SysMenuServiceImpl)

文件位置：`lanjii-modules/module-system/system-biz/src/main/java/com/lanjii/sys/service/impl/SysMenuServiceImpl.java`

关键方法：`getUserMenuTree()`

**检查项：**
1. 用户是否为管理员
2. 用户关联的菜单是否存在
3. 菜单是否启用 (`isEnabled = 1`)
4. 菜单类型是否正确

### 登录后菜单返回

文件位置：`lanjii-modules/module-system/system-biz/src/main/java/com/lanjii/sys/service/impl/LoginServiceImpl.java`

关键方法：`generateLoginInfo()`

**检查项：**
1. Token 生成是否成功
2. 菜单树是否正确构建
3. 权限列表是否正确获取
4. 返回的 LoginInfo 对象结构是否完整

## 环境配置

### 前端环境变量

检查 `.env` 或 `.env.development` 文件：

```env
# WebSocket URL（如果为空，会使用默认值）
VITE_WS_URL=ws://localhost:8080/ws

# API 基础 URL
VITE_AXIOS_BASE_URL=http://localhost:8080/api
```

### 后端配置

检查后端的 JWT 和 WebSocket 配置：

1. JWT Token 过期时间
2. WebSocket 端点配置
3. CORS 和跨域设置

## 常见问题解决

### Q1: 登录后显示"菜单数据为空"

**A:** 
1. 检查用户是否有菜单权限
2. 登录后立即查看 Console，确认菜单数据是否存在
3. 如果为空，检查后端数据库中是否有菜单数据
4. 检查用户权限配置

### Q2: 验证码显示正常，但显示"登录已失效"

**A:**
1. 检查 Token 是否过期
2. 重新登录尝试
3. 清除浏览器缓存和 LocalStorage
4. 检查后端 Token 验证逻辑

### Q3: 菜单显示但某些超链接无法打开

**A:**
1. 检查路由配置是否正确
2. 查看 Component 字段是的组件是否存在
3. 确保菜单数据中的 `component` 字段值与实际文件路径匹配

### Q4: WebSocket 连接失败

**A:**
1. 检查 WebSocket URL 配置是否正确
2. 验证后端 WebSocket 端点是否启用
3. 查看网络请求，确认 WebSocket 握手是否成功
4. 检查 CORS 和防火墙设置

## 提交修复代码的检查清单

- [ ] 运行开发服务器，登录到系统
- [ ] 在 Console 中查看所有日志是否符合预期
- [ ] 测试菜单导航是否正常
- [ ] 测试登出和重新登录流程
- [ ] 清除浏览器 Cache，重新测试
- [ ] 检查是否有 TypeScript 错误
- [ ] 验证所有修改都有适当的日志记录

## 相关文件参考

### 前端
- `/lanjii-admin-ui/src/router/index.ts` - 路由配置
- `/lanjii-admin-ui/src/stores/user.store.ts` - 用户 Store
- `/lanjii-admin-ui/src/api/http.ts` - HTTP 客户端
- `/lanjii-admin-ui/src/views/login/index.vue` - 登录组件

### 后端
- `/lanjii-modules/module-system/system-biz/src/main/java/com/lanjii/sys/service/impl/LoginServiceImpl.java` - 登录服务
- `/lanjii-modules/module-system/system-biz/src/main/java/com/lanjii/sys/service/impl/SysMenuServiceImpl.java` - 菜单服务
- `/lanjii-modules/module-system/system-biz/src/main/java/com/lanjii/sys/controller/LoginController.java` - 登录控制器

## 需要进一步调查的问题

### "Uncaught SyntaxError: Unexpected token 'export'"

这个错误可能与以下因素有关：
1. Webpack/Vite 配置问题
2. 浏览器兼容性问题
3. 脚本加载顺序问题

**解决方案：**
- 清除构建缓存并重新构建
- 检查浏览器控制台中的具体错误位置
- 验证所有 JavaScript 模块的正确导出语法

## 修改版本信息

- 修改日期：2026-02-21
- 修改人：GitHub Copilot
- 修改版本：1.0
- 状态：已完成核心修复

## 后续改进建议

1. **增加定时检查机制**
   - 定期验证 Token 有效性
   - 发现过期时主动刷新

2. **增加重试机制**
   - 菜单获取失败时自动重试
   - WebSocket 连接失败时自动重连

3. **增加性能监控**
   - 记录路由切换时间
   - 监控菜单加载时间

4. **改进用户体验**
   - 添加加载状态提示
   - 分步加载菜单（先显示主菜单，再加载子菜单）

---

**注意：** 这个指南会随着问题的发现和解决而持续更新。
